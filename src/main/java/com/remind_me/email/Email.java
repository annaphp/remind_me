package com.remind_me.email;

public class Email {
	
	private String subject;
	private String content;
	private String sender;
	
	public Email(String subject, String content, String sender) {
		this.subject = subject;
		this.content = content;
		this.sender = sender;
	}
	
	

	public Email() {}



	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}



	@Override
	public String toString() {
		return "Email [subject=" + subject + ", content=" + content + ", sender=" + sender + "]";
	}

	
}
