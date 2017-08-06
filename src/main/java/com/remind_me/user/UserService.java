package com.remind_me.user;

import java.util.List;

public class UserService {
	
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
