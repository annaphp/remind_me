package com.remind_me.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	UserRepository repository;
	
	@Autowired
	PasswordEncoder encoder;
	
	public User create(User user, String role){
		if( ! usernameTaken(user)){
			user.setRole(role);
			user.setPassword(encoder.encode(user.getPassword()));
			user = save(user);
			return user;
		}	
		return null;
	}
	
	public User save(User user){
		return repository.saveAndFlush(user);
	}

	public User findById(Long id){
		return repository.findOne(id);
	}
	
	public List<User> findAll(){
		return repository.findAll();
	}
	
	public void delete (User user){
		repository.delete(user);
	}
	
	public User findByUserName(String username){
		return repository.findByUserName(username);	
	}
	
	public User verifyEmail(String verificationCode){
		User user = repository.findOneByVerificationCode(verificationCode);
		user.setEmailVerified(LocalDateTime.now());
		return repository.saveAndFlush(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails details = findByUserName(username);
		if (details == null) throw new UsernameNotFoundException("user not found: " + username);
		return details;
	}
	//helper method, determines if the username is taken
	public boolean usernameTaken(User user) {
		return findAll().stream().map( u -> u.getUsername()).collect(Collectors.toSet()).contains(user.getUsername());
	}
	
	/**
	 * Updates user and rehashes password.
	 * @param user user with plain text password
	 */
	public void rehashPassword(User user){
		user.setPassword(encoder.encode(user.getPassword()));
		repository.saveAndFlush(user);
	}
	
	public User currentUser(){
		return findByUserName(
				SecurityContextHolder.getContext().getAuthentication().getName()
				);
	}
	
	public User findUserByEmail(String email){
		return repository.findByEmail(email);
	}
}
