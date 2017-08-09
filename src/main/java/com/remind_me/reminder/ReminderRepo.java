package com.remind_me.reminder;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.remind_me.user.User;

public interface ReminderRepo extends JpaRepository<Reminder, Long>{
	
	@Query("select r from Reminder r where r.user = :user")
	public List<Reminder> findByUser(@Param("user") User user);
	
}
