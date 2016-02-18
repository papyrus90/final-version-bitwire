package ro.sci.service;

import java.util.Collection;

import ro.sci.domain.ForgotPasswordForm;
import ro.sci.domain.User;
import ro.sci.domain.UserCreateForm;

public interface SqlService {
	
	public User getUserById(long id) throws NullPointerException;
	public User getUserByEmail(String email) throws NullPointerException;
	public Collection<User> getAllUsers();
	public User create(UserCreateForm form);
	public boolean delete(long id);
	public String forgotPassword(ForgotPasswordForm form);
	public User getUserBySafety(String safety) throws NullPointerException;
	public void update(long id) throws NullPointerException;
}
