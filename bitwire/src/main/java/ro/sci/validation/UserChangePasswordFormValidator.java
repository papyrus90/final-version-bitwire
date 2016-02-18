package ro.sci.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ro.sci.domain.ChangePasswordForm;
import ro.sci.domain.UserCreateForm;
import ro.sci.service.SqlService;

@Component
public class UserChangePasswordFormValidator implements Validator {
	
	@Autowired
	SqlService service;

	@Override
	public boolean supports(Class<?> c) {
		return c.equals(ChangePasswordForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ChangePasswordForm form = (ChangePasswordForm)target;
		validatePasswords(errors,form);
		validateCode(errors,form);
		
	}
	private void validatePasswords(Errors errors,ChangePasswordForm form){
		if(!form.getPassword().equals(form.getPasswordRepeated())){
			errors.rejectValue("password","password.match");
			
		}
	}
	private void validateCode(Errors errors,ChangePasswordForm form){
		if (service.getUserBySafety(form.getSafety()) == null){
			errors.rejectValue("safety", "safety.not");
		}
	}
}
