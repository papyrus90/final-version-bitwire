package ro.sci.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ro.sci.domain.User;
import ro.sci.service.SqlService;

@Controller

public class AdminController {

	@Autowired
	SqlService service;
	
	
	public ModelAndView getAdminLogINPage(User user){
		return new ModelAndView ("admin","user",user);
	}
	
	
	
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping("/admin/dashboard")
	public ModelAndView getUsersPage(){
		return new ModelAndView("user_list" ,"users" ,service.getAllUsers());
	}
}
