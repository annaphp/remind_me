package com.remind_me.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.remind_me.user.User;
import com.remind_me.user.UserService;

@Controller
public class LoginController {

	@Autowired
	UserService userService;

	@GetMapping(path = { "/login", "/" })
	public String loginForm(Model model) {
		return "login";
	}

	@GetMapping(value = "/register")
	public String registerForm(Model model) {
		model.addAttribute("userToRegister", new User());
		return "register";

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

		userService.create(user, "ROLE_OWNER"); // this method checks if user already exists
		model.addFlashAttribute("messages", "You have been successfully registered! Please login.");

		return "redirect:/user/login";
	}
}
