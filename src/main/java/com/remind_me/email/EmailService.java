package com.remind_me.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.remind_me.user.UserService;

@Service
public class EmailService {
	
	@Value("${email}")
	private String email;
		
	@Value("${password}")
	private String password;
	
	@Autowired
	UserService userService;
	

    @Async
	public void send(String to, String subject, String text) {
		    Properties props = System.getProperties();
		    props.put("mail.smtp.host", "smtp.gmail.com");
		    props.put("mail.smtp.user", email);
		    props.put("mail.smtp.password", password);
		    props.put("mail.smtp.port", "587"); 
		    props.put("mail.smtp.auth", "true");
		    props.put("mail.smtp.starttls.enable", "true");
		    
		    try{
		        Session session = Session.getDefaultInstance(props, null);
		        MimeMessage message = new MimeMessage(session);
		        message.setFrom(new InternetAddress(email));
		        message.addRecipients(Message.RecipientType.TO, to);
		        message.setSubject(subject);
		        message.setText(text);
		        Transport transport = session.getTransport("smtp");
		        transport.connect("smtp.gmail.com", email, password);
		        transport.sendMessage(message, message.getAllRecipients());
		    }catch(MessagingException e){
		        e.printStackTrace();
		    }
	}
	


}
