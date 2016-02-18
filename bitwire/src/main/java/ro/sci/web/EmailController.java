package ro.sci.web;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ro.sci.email.EmailSender;

@Controller
public class EmailController {
	
	@Autowired
	private EmailSender emailSender;
	
	//@RequestMapping("/email")
	public void sendMail() throws MessagingException{
		emailSender.send("papyrus_20005@yahoo.com", "bitwire", "hello");
	}
}
