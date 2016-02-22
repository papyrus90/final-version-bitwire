package ro.sci.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/**
 * This class is used to configure the security,it handles the authorization and the authentification.
 * 
 * @author Popa Alex
 *
 */


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter  {
	
	@Autowired
	private UserDetailsService userDetailsService;
	/**
	 * This method configures the security of the application.
	 * It says what the login page is , and which pages are available for everybody and wich are forbidden.
	 *
	 */

	
	protected void configure(HttpSecurity http)throws Exception{
		http
			.authorizeRequests()
			.antMatchers("/","/register","/email","/forgot","/change_password","/resources/**","/webjars/**").permitAll()
			.antMatchers("/admin/dashboard").hasAuthority("ADMIN")
			.antMatchers("/schedule/**").authenticated()
				.anyRequest().authenticated()
				.and()
					.formLogin()
						.loginPage("/login")
						.usernameParameter("email")	
						.permitAll()
						.defaultSuccessUrl("/congrats")
						.and()
					.logout()
						.permitAll();
				
		http.csrf().disable();

	}
	
		
	/**
	 * This method configures the authentification by using a userDetailsService.
	 *
	 */	
	 protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		 auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	 }
	
}
