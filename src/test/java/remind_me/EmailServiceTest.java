package remind_me;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.remind_me.core.email.EmailServiceGmail;
import com.remind_me.core.reminder.Reminder;
import com.remind_me.core.reminder.ReminderService;
import com.remind_me.core.user.User;
import com.remind_me.core.user.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=com.remind_me.RemindMeApplication.class)
public class EmailServiceTest {
	
	@Autowired
	EmailServiceGmail emailService;
	
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
	

	
}
