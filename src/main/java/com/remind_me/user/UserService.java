package com.remind_me.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
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
}
