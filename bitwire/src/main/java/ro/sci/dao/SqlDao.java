package ro.sci.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import ro.sci.domain.User;


@Transactional
public interface SqlDao extends CrudRepository<User, Long> {
	
	public User findOneByEmail(String email) throws NullPointerException;
	public User findOneBySafety(String safety) throws NullPointerException;
		
	
}
