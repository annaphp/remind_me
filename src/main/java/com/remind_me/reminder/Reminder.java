package com.remind_me.reminder;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.remind_me.user.User;

@Entity
public class Reminder {

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private User user;
	
	private String title;
	private String content;
	private LocalDate dueDate;
	
	
	public Reminder(String title, String content, LocalDate dueDate) {
		this.title = title;
		this.content = content;
		this.dueDate = dueDate;
	}
	
	public Reminder(){}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public LocalDate getDueDate() {
		return dueDate;
	}


	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
		
}
