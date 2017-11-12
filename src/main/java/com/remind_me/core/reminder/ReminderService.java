package com.remind_me.core.reminder;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.remind_me.core.email.EmailService;
import com.remind_me.core.user.User;
import com.remind_me.core.user.UserService;


@Service
public class ReminderService {
	
	@Autowired
	ReminderRepo reminderRepo;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	UserService userService;
	
	public Reminder save(Reminder reminder, User user) {
		Assert.notNull(user, "user cannot be null");
		Assert.isTrue(user.getId() != null, "user must have database identity");
		reminder.setUser(user);
		return reminderRepo.saveAndFlush(reminder);
	}


	public Reminder findById(Long id){
		return reminderRepo.findOne(id);
	}
	
	public List<Reminder> findAll(){
		return reminderRepo.findAll();
	}
	
	public void delete(Long id){
		reminderRepo.delete(id);
	}
	
	public List<Reminder> findByUser(User user){
		return reminderRepo.findByUser(user);
	}
	
	public Reminder update(Reminder reminder){
		return reminderRepo.saveAndFlush(reminder);
	}
	
	@Scheduled(cron="0 0 1-5 * * *")
	public void sendReminders(){
		System.out.println("1. Send reminders method begins");
		//all reminders that are due today or tomorrow
	   Set<Reminder> dueSoon = reminderRepo.findAll().stream().filter(r -> r.getDueDate().isEqual(LocalDate.now()) ||
			   						r.getDueDate().isAfter(LocalDate.now()) 
			   						&& r.getDueDate().isBefore(LocalDate.now().plusDays(2))).collect(Collectors.toSet());
	   for(Reminder r : dueSoon){
		   User u = r.getUser();
		   //email subject
		   String subject = "Friendly Reminder - " + r.getTitle() + " Due on " + r.getDueDate();
		   emailService.send(u.getEmail(), subject, r.getContent());
		   //reminder is deleted after it's sent
		   reminderRepo.delete(r);
	   }	
	}
	
	@Scheduled(fixedRate=Long.MAX_VALUE, initialDelay=300000)
	public void initialReminders(){
		sendReminders();
	}
		
	
}
	


