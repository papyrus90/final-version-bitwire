package ro.sci.service;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ro.sci.dao.SqlDao;
import ro.sci.domain.ForgotPasswordForm;
import ro.sci.domain.Role;
import ro.sci.domain.User;
import ro.sci.domain.UserCreateForm;

@Service
public class SqlServiceImpl implements SqlService {
	
	@Autowired
	SqlDao dao;
	
	@Override
	public User getUserById(long id) throws NullPointerException {
		User user = dao.findOne(id);
		if(user != null){
		return user;
		}
		throw new NullPointerException("user doesn't exist");
	}
	
	@Override
	public User getUserByEmail(String email) throws NullPointerException {
		User user = dao.findOneByEmail(email);
		if (user != null){
			return user;
		}
		return null;
	}
	

	@Override
	public Collection<User> getAllUsers() {
		return (Collection<User>) dao.findAll();
		
	}
		
	@Override
	public User create(UserCreateForm form) {
		User user = new User();
		Calendar cal = Calendar.getInstance();
		Date utilDate = cal.getTime();
		java.sql.Date sqlDate =   new java.sql.Date(utilDate.getTime());
		
		/*if(!(form.getPassword().equals(form.getPasswordRepeated()))){
			throw new IllegalArgumentException("passwords don't match");
		}
		if(dao.findOneByEmail(form.getEmail())!= null ){
			throw new IllegalArgumentException("email already exists!");
		}*/
		user.setEmail(form.getEmail());
		user.setFirstName(form.getFirstName());
		user.setLastName(form.getLastName());
		user.setPassword(new BCryptPasswordEncoder().encode(form.getPassword()));
		user.setDate(sqlDate);
		user.setRole(Role.USER);
		String safe = Long.toHexString(Double.doubleToLongBits(Math.random()));
		user.setSafety(safe);
		//user.setActivated(false);
		return dao.save(user);
	
	}

	@Override
	public boolean delete(long id) {
		if(dao.findOne(id) == null){
			return false;
			}
		dao.delete(id);
		return true;		
		}

	@Override
	public String forgotPassword(ForgotPasswordForm form) {
		if (dao.findOneByEmail(form.getEmail())!=null){
			User user = dao.findOneByEmail(form.getEmail());
			
			if ((!form.getFirstName().equals(user.getFirstName())) ||(!form.getLastName().equals(user.getLastName()))) {
				throw new IllegalArgumentException ("not a valid first or last name !");
			}
			
		}
		return null;
	}

	@Override
	public User getUserBySafety(String safety) throws NullPointerException {
		User user = dao.findOneBySafety(safety);
		if (user != null){
			return user;
		}
		return null;
	}

	@Override
	public void update(long id) throws NullPointerException {
		User user = dao.findOne(id);
		dao.save(user);
		
	}
		
	}

