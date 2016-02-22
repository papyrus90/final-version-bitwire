package ro.sci.domain;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
/**
 * This class is used before the change password ,and it is used so that an email with an unique code will be sent if the email enterd is valid and in the db.
 * 
 * @author Popa Alex
 *
 */


public class ForgotPasswordForm {
	
	@Pattern(regexp = "^[A-Za-z]*$" , message = "not a valid name ,only letters allowed")
	@NotEmpty(message = "enter a first name")
	private String firstName;
	
	@Pattern(regexp = "^[A-Za-z]*$" , message = "not a valid name ,only letters allowed")
	@NotEmpty(message = "enter a last name")
	private String lastName;
	
	@Email(message = "not a valid email")
	@NotEmpty(message = "enter an email")
	private String email;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
