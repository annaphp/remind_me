package remind_me;

import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.remind_me.core.reminder.Reminder;
import com.remind_me.core.reminder.ReminderService;
import com.remind_me.core.user.User;
import com.remind_me.core.user.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=com.remind_me.RemindMeApplication.class)
public class ReminderServiceTest {
	
	@Autowired
	UserService userService;
	
	@Autowired
	ReminderService reminderService;
	
	User user1;
	Reminder reminder1;
	Reminder reminder2;
	
	@Before
	public void setup(){
	    user1 = new User("John", "1234", "john@gmail.com");	
	    user1 = userService.save(user1);
	    
	    reminder1 = new Reminder("Do", "do something", LocalDate.now());
	    reminder2 = new Reminder("Laundry", "do something", LocalDate.now());
	    reminder1 = reminderService.save(reminder1, user1);  
	    reminder2 = reminderService.save(reminder2, user1);
	}
	
	@Test
	public void shouldSave(){
		assertNotNull(reminderService.findById(reminder1.getId()));
		assertNotNull(reminderService.findById(reminder2.getId()));
	}
	
	@Test
	public void shouldSaveUserInfo(){
		System.out.println(reminderService.findById(reminder1.getId()).getUser());
	}
	
	@Test
	public void shouldFindByUser(){
		assertNotNull(reminderService.findByUser(user1));
	}
	
	

}
