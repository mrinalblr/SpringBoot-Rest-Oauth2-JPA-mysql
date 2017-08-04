package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.config.CustomUserDetails;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repo.UserRepository;
import com.example.demo.service.UserService;

@SpringBootApplication
public class Application {
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
//	@Autowired
//	public void authenticationManager(AuthenticationManagerBuilder builder,UserRepository repo) throws Exception{
//		System.out.println("count :"+ repo.count());
//		List<User> li = new ArrayList<User>();
//		li = (List<User>) repo.findAll();
//		System.out.println(li);
//		if(repo.count() == 0){
//			User u = new User("mrinal","mrinal",Arrays.asList(new Role("ADMIN")));
//			repo.save(u);
//			
//		}
//		builder.userDetailsService(new UserDetailsService() {
//			
//			@Override
//			public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//				System.out.println("USERNAME: "+s);
//				// TODO Auto-generated method stub
//				return new CustomeUserDetails(repo.findByUserName(s));
//			}
//		});
//	}
	
	@Autowired
	public void authenticationManager(AuthenticationManagerBuilder builder, UserRepository repository, UserService service) throws Exception {
		//Setup a default user if db is empty
		if (repository.count()==0)
			service.save(new User("user", "user", Arrays.asList(new Role("ADMIN"), new Role("ACTUATOR"))));
		builder.userDetailsService(userDetailsService(repository));
	}

	/**
	 * We return an istance of our CustomUserDetails.
	 * @param repository
	 * @return
     */
	private UserDetailsService userDetailsService(final UserRepository repository) {
		
		User u = (User)repository.findByUserName("user");
		System.out.println("uname : "+u.getUserName());
		System.out.println("password : "+u.getPassword());
		
		return username -> new CustomUserDetails(repository.findByUserName(username));
	}
}
