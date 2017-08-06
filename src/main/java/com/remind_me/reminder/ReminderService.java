package com.remind_me.reminder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.remind_me.user.User;

@Service
public class ReminderService {
	
	@Autowired
	ReminderRepo repository;
	
	public Reminder create(Reminder reminder){
		return repository.saveAndFlush(reminder);
	}

	public Reminder findById(Long id){
		return repository.findOne(id);
	}
	
	public List<Reminder> findAll(){
		return repository.findAll();
	}
	
	public void delete(Reminder reminder){
		repository.delete(reminder);
	}
	
	public void findByUser(User user){
		repository.findByUser(user);
	}
}

