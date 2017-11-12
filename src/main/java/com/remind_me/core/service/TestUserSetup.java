package com.remind_me.core.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.remind_me.core.user.Role;
import com.remind_me.core.user.User;
import com.remind_me.core.user.UserService;






@Component
@Profile({"dev"})
public class TestUserSetup {
	
	
	@Autowired
	UserService userService;
	
	@Scheduled(fixedRate = Long.MAX_VALUE)
	public void setUpAdmin(){
   		User u = userService.create(new User("asdf", "asdfasdf", "a@a.a"), Role.ROLE_USER);
   		u.setEmailVerified(LocalDateTime.now());
   		userService.save(u);

		System.out.println("admin account set up: asdf : asdfasdf");
	}
}
