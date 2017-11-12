package com.remind_me.web;

import java.security.Principal;
import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.remind_me.core.reminder.Category;
import com.remind_me.core.reminder.Reminder;
import com.remind_me.core.reminder.ReminderService;
import com.remind_me.core.reminder.Status;
import com.remind_me.core.user.User;
import com.remind_me.core.user.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	ReminderService reminderService;

	@RequestMapping(value = "/home")
	public String home(Model model, Principal principal) {
		User currentUser = userService.findByUserName(principal.getName());
		model.addAttribute("reminders", reminderService.findByUser(currentUser));
		return "home";
	}

	@RequestMapping(value = "/add_reminder")
	public String addReminderForm(Model model) {
		model.addAttribute("reminder", new Reminder());
		model.addAttribute("status", Status.values());
		model.addAttribute("category", Category.values());
		return "add_form";
	}

	@RequestMapping(value = "/save_reminder", method = RequestMethod.POST)
	public String saveReminder(@Valid @ModelAttribute Reminder reminder, Errors errors, Principal principal,
			Model model) {
		if (errors.hasErrors()) {
			model.addAttribute("reminder", reminder);
			model.addAttribute("status", Status.values());
			model.addAttribute("category", Category.values());
			return "add_form";
		}

		// validating date if selected due date is in the past the input date is
		// rejected
		LocalDate dueDate = reminder.getDueDate();
		LocalDate todayDate = LocalDate.now();

		if (dueDate.isBefore(todayDate)) {
			errors.rejectValue("dueDate", "pastDueDate", "Due date cannot be in the past!");
			model.addAttribute("reminder", reminder);
			model.addAttribute("status", Status.values());
			model.addAttribute("category", Category.values());
			return "add_form";
		}
		reminderService.save(reminder, userService.findByUserName(principal.getName()));
		model.addAttribute("messages", "Reminder has been added!");
		return "redirect:/user/home";
	}

	@RequestMapping(value = "edit_reminder/{id}")
	public String editReminderForm(@PathVariable("id") Long id, Model model) {
		Reminder reminder = reminderService.findById(id);
		model.addAttribute("reminder", reminder);
		model.addAttribute("status", Status.values());
		model.addAttribute("category", Category.values());
		return "edit_form";
	}

	@RequestMapping(value = "/update_reminder", method = RequestMethod.POST)
	public String updateReminder(@ModelAttribute Reminder reminder, RedirectAttributes model) {
		reminderService.update(reminder);
		model.addFlashAttribute("messages", "Reminder has been updated!");
		return "redirect:/user/home";
	}

	@RequestMapping(value = "/delete_reminder/{id}")
	public String deleteReminder(@PathVariable("id") Long id, RedirectAttributes model) {
		reminderService.delete(id);
		model.addFlashAttribute("messages", "Reminder has been deleted!");
		return "redirect:/user/home";
	}

}
