package com.remind_me.reminder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.remind_me.email.Email;
import com.remind_me.email.EmailService;
import com.remind_me.user.User;
import com.remind_me.user.UserService;


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
	
	@Scheduled(fixedRate = 60000, initialDelay = 5000)
	public void sendReminders(){
	  System.out.println("Reminder service emailer runs");
	  System.out.println(reminderRepo.findAll());
	   Set<Reminder> dueSoon = reminderRepo.findAll().stream().filter(r -> r.getDueDate().isEqual(LocalDate.now()) ||
			   						r.getDueDate().isAfter(LocalDate.now()) 
			   						&& r.getDueDate().isBefore(LocalDate.now().plusDays(2))).collect(Collectors.toSet());
	   System.out.println("Due soon" + dueSoon.toString());
	   for(Reminder r : dueSoon){
		   User u = r.getUser();
		   String title = "Your reminder from Friendly Reminder - " + r.getContent() + " Due on " + r.getDueDate();
		   emailService.send(u.getEmail(), title, r.getContent());
		   reminderRepo.delete(r);
	   }
		
	}
	
	@Scheduled(fixedRate = 60000, initialDelay = 5000)
	public void createRemidersFromEmails(){
		Set<Email> emails = emailService.fetchEmails();
		for(Email e : emails){
			User user = userService.findUserByEmail(e.getSender());
			String title = e.getSubject();
			String content = e.getContent();
			//converting string to local date
			String reminderDuedate = content.substring(0, content.indexOf("\n")-1);
			LocalDate date =  LocalDate.parse(reminderDuedate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
			
			String reminderContent = content.substring(reminderDuedate.length());
			Reminder reminder = new Reminder(title,reminderContent,date);
			save(reminder, user);
		}
	}
	
}

