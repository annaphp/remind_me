package com.remind_me.reminder;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Reminder {

	@Id
	@GeneratedValue
	private Long id;
	private String title;
	private String content;
	private LocalDate dateCreated;
	private LocalDate dueDate;
	
	
	public Reminder(String title, String content, LocalDate dateCreated, LocalDate dueDate) {
		this.title = title;
		this.content = content;
		this.dateCreated = dateCreated;
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


	public LocalDate getDateCreated() {
		return dateCreated;
	}


	public void setDateCreated(LocalDate dateCreated) {
		this.dateCreated = dateCreated;
	}


	public LocalDate getDueDate() {
		return dueDate;
	}


	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
		
}
