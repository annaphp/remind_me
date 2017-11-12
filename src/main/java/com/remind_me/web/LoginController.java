package com.remind_me.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.remind_me.core.email.EmailService;
import com.remind_me.core.user.Role;
import com.remind_me.core.user.User;
import com.remind_me.core.user.UserService;

@Controller
public class LoginController {

	@Autowired
	UserService userService;
	
	@Autowired
	EmailService emailService;

	@GetMapping(path = { "/login", "/" })
	public String loginForm(Model model) {
		return "login";
	}

	@GetMapping(value = "/register")
	public String registerForm(Model model) {
		model.addAttribute("userToRegister", new User());
		return "register";

	}
	
	@GetMapping(value ="/verifyEmail/{verificationCode}")
	public String verifyEmail(	@PathVariable("verificationCode") String verificationCode, RedirectAttributes model){ 
		userService.verifyEmail(verificationCode);
		model.addFlashAttribute("messages", "Thank you for verifying your email, please log in");
		return "redirect:/login";
		
	}

	@PostMapping(value = "/register")
	public String register(@Valid @ModelAttribute("userToRegister") User user, Errors errors,
			RedirectAttributes model) {
		if (errors.hasErrors()) {
			model.addAttribute("userToRegister", user);
			return "register";
		}

		if (userService.usernameTaken(user)) {
			errors.rejectValue("username", "Match", "This username is taken.");
			model.addAttribute("userToRegister", user);
			return "register";
		}

		user = userService.create(user, Role.ROLE_USER); // this method checks if user already exists
		model.addFlashAttribute("messages", "We send you an email, please follow a link in that email to verify your email address, please also check spam folder");
		emailService.send(user.getEmail(), 
				"Welcome to remindeme app", 
				"Please follow this link: "
				+ "http://45.77.115.43:8080/verifyEmail/"+user.getVerificationCode()
				+ " this link will expire in 7 days.");
		return "redirect:/login";
	}
}
