package com.remind_me.core.email;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("dev")
@Service
public class EmailServiceSysout implements EmailService {

	@Override
	public void send(String to, String subject, String text) {
		System.out.println(to + " > " + subject + " > " + text);
	}

}
