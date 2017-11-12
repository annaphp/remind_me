package com.remind_me.core.email;

public interface EmailService {
	void send(String to, String subject, String text);
}
