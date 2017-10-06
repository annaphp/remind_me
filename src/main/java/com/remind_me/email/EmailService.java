package com.remind_me.email;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.remind_me.reminder.Reminder;
import com.remind_me.user.User;
import com.remind_me.user.UserService;

@Service
public class EmailService {
	
	@Value("${email}")
	private String email;
		
	@Value("${password}")
	private String password;
	
	@Value("${pop3Host}")
	private String pop3Host;
	
	@Value("${storeType}")
	private String storeType;
	
	
	
	@Autowired
	UserService userService;
	

    @Async
	public void send(String to, String subject, String text) {
    	System.out.println("***email send method starts");
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
	
    @Async
	public Set<Email> fetchEmails(){
    	
    	Set<Email> emails = new HashSet<>();
		System.out.println("fetchEmails starts");
		try{
			Properties properties = new Properties();
			properties.put("mail.store.protocol", "pop3");
			properties.put("mail.pop3.host", pop3Host);
			properties.put("mail.pop3.port", "995");
			properties.put("mail.pop3.starttls.enable", "true");
			Session emailSession = Session.getDefaultInstance(properties);
			
			// create store object and connect to pop server
			Store store = emailSession.getStore("pop3s");
			store.connect(pop3Host, email, password);
			
			//create folder and open it
			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_WRITE);
			
			
			Message[] messages = emailFolder.getMessages();
			System.out.println("messages.length---" + messages.length);
			for(int i = 0; i < messages.length; i++){
				System.out.println("messages length inside loop " + messages.length);
				Message message = messages[i];
				Email email =parseEmail(message);
				emails.add(email);
				//email is deleted from the inbox folder after read
				message.setFlag(Flags.Flag.DELETED, true);
			}
			System.out.println("***Printing emails" + emails);
			
			emailFolder.close(true);
			store.close();	
		}catch(Exception e){
			e.printStackTrace();
		}
		return emails;
	}
    
    private Email parseEmail(Message message) throws Exception{
    	Email email = new Email();
    	
    	email.setSubject(message.getSubject());// to get email subject
    	email.setSender(((InternetAddress)message.getFrom()[0]).getAddress());// to get email only without sender's name
    	
    	if(message.isMimeType("text/plain")){
    		String content = (String)message.getContent();
    		email.setContent(content);
    		
    		System.out.println("1. When content is text/plain" + email.getContent());
    		
    	}else if(message.isMimeType("multipart/alternative")){
    		
    		Multipart multipart = (Multipart)message.getContent();
    		int count = multipart.getCount();
    		
    		for(int i = 0; i < count; i++){
    			BodyPart bodyPart = multipart.getBodyPart(i);
    			if(bodyPart.isMimeType("text/plain")){
    				String content = (String)bodyPart.getContent();
    				email.setContent(content);
    	    		System.out.println("2. When content is text/plain in mulipart " + email.getContent());
   			    }
    		}
    	}
    	return email;
    }
    


}
