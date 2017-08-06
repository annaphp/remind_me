package remind_me;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.remind_me.reminder.Reminder;
import com.remind_me.reminder.ReminderService;
import com.remind_me.user.User;
import com.remind_me.user.UserService;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=com.remind_me.RemindMeApplication.class)
public class UserServiceTest {
	
//	@Autowired
//	UserService userService;
//	
//	@Autowired
//	ReminderService reminderService;
//	User savedUser;
	


//	@Before
//	public void setup(){
//		User user = new User("Anna", "1234", "anna@gmail.com");	
//		Reminder reminder = new Reminder("Do", "do something", LocalDate.now());
//		user.link(reminder);
//	    User savedUser = userService.save(user);
//	}
	
	
	@Test
	public void contextLoads() throws Exception {}
	
//	@Test
//	public void shouldSave(){
//		assertNotNull(userService.findById(savedUser.getId()));
//	}

	
	
	

}
