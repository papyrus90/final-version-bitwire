package ro.sci.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ro.sci.domain.ForgotPasswordForm;
import ro.sci.service.SqlService;

@Component
public class UserForgetPasswordFormValidator implements Validator {

	@Autowired
	SqlService service;

	@Override
	public boolean supports(Class<?> c) {
		return c.equals(ForgotPasswordForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ForgotPasswordForm form = (ForgotPasswordForm) target;
		validateEmail(errors, form);

	}

	private void validateEmail(Errors errors, ForgotPasswordForm form) {

		if (service.getUserByEmail(form.getEmail()) == null) {
			errors.rejectValue("email", "email.does.not.exists");
		}
	}
	
}
