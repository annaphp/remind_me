package remind_me;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.remind_me.email.EmailService;
import com.remind_me.reminder.Reminder;
import com.remind_me.reminder.ReminderService;
import com.remind_me.user.User;
import com.remind_me.user.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=com.remind_me.RemindMeApplication.class)
public class EmailServiceTest {
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	ReminderService reminderService;
	
	@Autowired
	UserService userService;
	
	User user;
	Reminder reminder1;
	Reminder reminder2;

	@Before
	public void setup(){
	    user = new User("John", "1234", "john@gmail.com");	
	    
	    System.out.println(user);
	    user = userService.save(user);
	    
	    reminder1 = new Reminder("Do", "do something", LocalDate.now());
	    reminder2 = new Reminder("Laundry", "do something", LocalDate.now());
	    reminder1 = reminderService.save(reminder1, user);  
	    reminder2 = reminderService.save(reminder2, user);
	}
	
	@Test
	public void shouldSendEmail(){
		String to = "";
		String subject = "friendly Reminder";
		String text = "test";
		String email = "friendlyreminderrr@gmail.com";
		String password = "";
		emailService.send(to, subject, text, email, password);;
	}
	
	
}
