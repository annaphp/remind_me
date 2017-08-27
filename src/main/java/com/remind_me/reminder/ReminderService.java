package com.remind_me.reminder;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.remind_me.user.User;


@Service
public class ReminderService {
	
	@Autowired
	ReminderRepo repository;
	
	public Reminder save(Reminder reminder, User user) {
		Assert.notNull(user, "user cannot be null");
		Assert.isTrue(user.getId() != null, "user must have database identity");
		reminder.setUser(user);
		return repository.saveAndFlush(reminder);
	}

	

	public Reminder findById(Long id){
		return repository.findOne(id);
	}
	
	public List<Reminder> findAll(){
		return repository.findAll();
	}
	
	public void delete(Long id){
		repository.delete(id);
	}
	
	public List<Reminder> findByUser(User user){
		return repository.findByUser(user);
	}
	
	public Reminder update(Reminder reminder){
		return repository.saveAndFlush(reminder);
	}
}

