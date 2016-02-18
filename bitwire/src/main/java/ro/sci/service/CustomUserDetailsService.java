package ro.sci.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ro.sci.domain.Role;
import ro.sci.domain.User;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	SqlService dao;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = dao.getUserByEmail(email);
		if (user ==null){
			 throw new UsernameNotFoundException("could not find user with email : " + email);
		}
			
		return new CustomUserDetails(user);
	}
	
	public class CustomUserDetails extends User implements UserDetails{
		private User user;
		
		private CustomUserDetails(User user){
			super(user);
			this.user = user;
		}
		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			List<SimpleGrantedAuthority> roles = new ArrayList<>();
			roles.add(new SimpleGrantedAuthority(user.getRole().name()));
			return roles;
		}

		@Override
		public String getUsername() {
			return getEmail();
		}

		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}
		@Override
		public boolean isEnabled() {
			return true;
		}
		
	}
	
}
