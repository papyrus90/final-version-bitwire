package ro.sci.domain;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;




public class UserCreateForm {
	
	@Pattern(regexp = "^[A-Za-z]*$" , message = "not a valid name ,only letters allowed")
	@NotEmpty(message = "please enter a first name")
	private String firstName ;
	
	@Pattern(regexp = "^[A-Za-z]*$" , message = "not a valid name ,only letters allowed")
	@NotEmpty(message = "please enter a last name")
	private String lastName ;
	
	@Email(message = "not a valid email")
	@NotEmpty(message = "please enter an email")
	private String email ;
	
	@Length(min = 6 , max = 12  ,message = "password must be between 6 and 12 characters")
	@NotEmpty(message = "please enter a password")
	private String password ;
	
	@NotEmpty(message = "please enter your password again")
	private String passwordRepeated ;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordRepeated() {
		return passwordRepeated;
	}

	public void setPasswordRepeated(String passwordRepeated) {
		this.passwordRepeated = passwordRepeated;
	}
	
	
}
