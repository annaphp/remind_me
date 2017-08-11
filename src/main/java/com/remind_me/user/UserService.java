package com.remind_me.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	UserRepository repository;
	
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

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails details = findByUserName(username);
		if (details == null) throw new UsernameNotFoundException("user not found: " + username);
		return details;
	}
}
