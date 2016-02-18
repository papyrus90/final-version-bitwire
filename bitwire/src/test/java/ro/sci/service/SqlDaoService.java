package ro.sci.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import ro.sci.BitwireApplication;
import ro.sci.domain.User;
import ro.sci.domain.UserCreateForm;
import ro.sci.service.SqlService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BitwireApplication.class)
@WebAppConfiguration
public class SqlDaoService {
	
	@Autowired
	 SqlService service;
	
	@After
	public void tearDown(){
		for (User user:service.getAllUsers()){
			service.delete(user.getId());
		}
	}

	
	
	@Test
	public void testCreateNewUser(){
		UserCreateForm form = new UserCreateForm();
		form.setEmail("alex");
		form.setFirstName("ion");
		form.setLastName("popa");
		form.setPassword("password");
		form.setPasswordRepeated("password");
		User saved = service.create(form);
		Assert.assertEquals(saved.getEmail(),"alex");
	}
	//
	
	
	@Test
	public void checkFindUserById(){
		UserCreateForm form = new UserCreateForm();
		form.setEmail("alexa");
		form.setFirstName("ion");
		form.setLastName("popa");
		form.setPassword("password");
		form.setPasswordRepeated("password");
		User saved = service.create(form);
		User found = service.getUserById(saved.getId());
		Assert.assertEquals(found.getEmail(), saved.getEmail());
	}
	@Test(expected = NullPointerException.class)
	public void checkFindUnexistingUserById(){
		User found = service.getUserById(13);
		Assert.assertEquals(found.getEmail(), null);
	}
	
	@Test 
	public void checkFindUserByEmail(){
		UserCreateForm form = new UserCreateForm();
		form.setEmail("alexa");
		form.setFirstName("ion");
		form.setLastName("popa");
		form.setPassword("password");
		form.setPasswordRepeated("password");
		User saved = service.create(form);
		User found = service.getUserByEmail(saved.getEmail());
		Assert.assertEquals(found.getLastName(), saved.getLastName());
	}
	
	@Test(expected = NullPointerException.class)
	public void checkFindUserWithUnexistingEmail(){
		UserCreateForm form = new UserCreateForm();	
		form.setEmail("alexa");
		form.setFirstName("ion");
		form.setLastName("popa");
		form.setPassword("password");
		form.setPasswordRepeated("password");
		User saved = service.create(form);
		User found = service.getUserByEmail("not found");
		Assert.assertNotEquals(found.getLastName(), saved.getLastName());
	}
	@Test
	public void checkDeleteExistingUser(){
		UserCreateForm form = new UserCreateForm();	
		form.setEmail("alexa");
		form.setFirstName("ion");
		form.setLastName("popa");
		form.setPassword("password");
		form.setPasswordRepeated("password");
		User saved = service.create(form);
		boolean result = service.delete(saved.getId());
		Assert.assertTrue(result);
	}
	@Test
	public void checkDeleteNonExistingUser(){
		boolean result = service.delete(12);
		Assert.assertFalse(result);
	}
	@Test 
	public void testEmptyGetAll(){
		Assert.assertTrue(service.getAllUsers().isEmpty());
	}
	@Test
	public void checkGetAllAfterUserCreation(){
		UserCreateForm form = new UserCreateForm();	
		form.setEmail("alexa");
		form.setFirstName("ion");
		form.setLastName("popa");
		form.setPassword("password");
		form.setPasswordRepeated("password");
		service.create(form);
		Assert.assertFalse(service.getAllUsers().isEmpty());
	}
}	
