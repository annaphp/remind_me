package com.remind_me.reminder;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.remind_me.user.User;

public interface ReminderRepo extends JpaRepository<Reminder, Long>{
	
	public List<Reminder> findByUser(User user);

}
