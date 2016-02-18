package ro.sci.domain;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class ChangePasswordForm {
	
	
	@NotEmpty(message = "please enter your code")
	private String safety;
	
	@NotEmpty(message = "please enter a new password")
	@Length(min = 6 , max = 12  ,message = "password must be between 6 and 12 characters")
	private String password;
	
	@NotEmpty(message = "please repeat the password")
	private String passwordRepeated;

	public String getSafety() {
		return safety;
	}

	public void setSafety(String safety) {
		this.safety = safety;
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
