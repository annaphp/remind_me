package remind_me;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.remind_me.core.reminder.ReminderService;
import com.remind_me.core.user.User;
import com.remind_me.core.user.UserService;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=com.remind_me.RemindMeApplication.class)
public class UserServiceTest {
	
	@Autowired
	UserService userService;
	
	@Autowired
	ReminderService reminderService;
	
	User user;
	
	@Before
	public void setup(){
	    user = new User("John", "1234", "john@gmail.com");	
	    user = userService.save(user);
	}
	
	
	@Test
	public void contextLoads() throws Exception {}
	
	@Test
	public void shouldSave(){
		assertNotNull(userService.findById(user.getId()));
	}
	
	@Test
	public void shouldFindById(){
		assertEquals(userService.findById(user.getId()),user);
	}
	
	@Test
	public void shouldUpdate(){
		user.setEmail("email@gmail.com");
		userService.save(user);
		assertTrue(userService.findById(user.getId()).getEmail().equals("email@gmail.com"));	
	}
	
	@Test
	public void shouldDelete(){
		userService.delete(user);
		assertNull(userService.findById(user.getId()));
	}

}
