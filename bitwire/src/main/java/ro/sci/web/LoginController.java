package ro.sci.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ro.sci.domain.User;
import ro.sci.service.SqlService;

@Controller
public class LoginController {
	
	@Autowired
	 private SqlService service;
	
	@RequestMapping(value = "/login" , method = RequestMethod.GET )
		public ModelAndView getLoginPage(User user ){
			return new ModelAndView("login" ,"user", user);
	}
	
	
	@RequestMapping("/congrats")
	public ModelAndView succesfullLogIn(User user){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	      
		return new ModelAndView ("congrats", "user" , user);
	}

}
