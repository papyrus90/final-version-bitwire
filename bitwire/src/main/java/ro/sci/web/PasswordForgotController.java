package ro.sci.web;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ro.sci.domain.ChangePasswordForm;
import ro.sci.domain.ForgotPasswordForm;
import ro.sci.domain.User;
import ro.sci.email.EmailSender;
import ro.sci.service.SqlService;
import ro.sci.validation.UserChangePasswordFormValidator;
import ro.sci.validation.UserForgetPasswordFormValidator;

@Controller
public class PasswordForgotController {
	
	@Autowired
	UserForgetPasswordFormValidator validator;
	
	@Autowired
	UserChangePasswordFormValidator passwordValidator;
	
	@Autowired
	SqlService service;
	
	@Autowired
	private EmailSender emailSender;
	
	@RequestMapping(value = "/forgot",method = RequestMethod.GET)
	public ModelAndView getPasswordForgotPage(ForgotPasswordForm form){
		return new ModelAndView ("forgot" , "form" , form);
	}
	
	@RequestMapping(value = "/forgot" ,method = RequestMethod.POST )
	public String sendEmail(@ModelAttribute("form") @Valid ForgotPasswordForm form,BindingResult bindingResult) throws MessagingException{
		
		validator.validate(form, bindingResult);
		if (bindingResult.hasErrors()){
			return ("forgot");
			}
		
		User user = service.getUserByEmail(form.getEmail());
		
		if (user == null) {
			throw new NullPointerException("not a valid email!");
		}
		if((user.getFirstName().equals(form.getFirstName()))&&(user.getLastName().equals(form.getLastName()))){
			emailSender.send(form.getEmail(), "bitwire forgot your password", "your code is " + user.getSafety());
		}
		else {
			throw new IllegalArgumentException("first name or last name is not valid ! ");
		}
		
		return ( "redirect:/change_password");
	}
	
	@RequestMapping(value =  "/change_password" ,method = RequestMethod.GET)
	public ModelAndView viewChangePasswordPage(ChangePasswordForm form){
		return new ModelAndView ("change_password", "form" , form);
	}
	
	@RequestMapping(value = "/change_password" , method = RequestMethod.POST )
	public String changePassword (@ModelAttribute("form") @Valid ChangePasswordForm form,BindingResult bindingResult){
		
		passwordValidator.validate(form, bindingResult);
		
		if (bindingResult.hasErrors()){
			return ("change_password");
			}
		User user = service.getUserBySafety(form.getSafety());
		if (user == null){
			throw new IllegalArgumentException("not a valid code! ");
		}
		
		if (form.getPassword().equals(form.getPasswordRepeated())){
			user.setPassword(new BCryptPasswordEncoder().encode(form.getPassword()));
			service.update(user.getId());
		}
		else {	throw new IllegalArgumentException("passwords don't match");
		}
		
		
		return("redirect:/");
	}
}
