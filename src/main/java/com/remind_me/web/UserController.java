package com.remind_me.web;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.remind_me.reminder.Category;
import com.remind_me.reminder.Reminder;
import com.remind_me.reminder.ReminderService;
import com.remind_me.reminder.Status;
import com.remind_me.user.User;
import com.remind_me.user.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	ReminderService reminderService;
	
	@RequestMapping(value="/login")
	public String loginForm(Model model){
		return "login";
	}
	
   @RequestMapping(value="/register")
	public String registerForm(Model model){
	   model.addAttribute("userToRegister", new User());
	   return "register";
		
	}
   
   @RequestMapping(value="/register", method=RequestMethod.POST)
   public String register(@Valid @ModelAttribute("userToRegister") User user, Errors errors, RedirectAttributes model){
	   if(errors.hasErrors()){
		   model.addAttribute("userToRegister", user);
		   return "register";
	   }
			
	   if(userService.usernameTaken(user)){
		   errors.rejectValue("username", "Match", "This username is taken.");
		   model.addAttribute("userToRegister", user);
		   return "register";
	   }
	   
	   userService.create(user, "ROLE_OWNER");	//this method checks if user already exists	
	   model.addFlashAttribute("messages","You have been successfully registered! Please login.");

	   return "redirect:/user/login";
   }
   
   @RequestMapping(value="/home")
   public String home(){
	   return "home";
   }
   
   @RequestMapping(value="/add_reminder")
   public String addReminderForm(Model model){
	   model.addAttribute("reminder", new Reminder());
	   model.addAttribute("status", Status.values());
	   model.addAttribute("category", Category.values());
	   return "add_form";
   }
   
   @RequestMapping(value="/save_reminder", method = RequestMethod.POST)
   public String saveReminder(@ModelAttribute Reminder reminder, 
								Principal principal ){
	   reminderService.save(reminder,userService.findByUserName(principal.getName()));
	   return "redirect:/user/home"; 
   }
   
}
