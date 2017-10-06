package com.remind_me.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReminderController {

	
	@RequestMapping(value="/")
	public String showMainPage(){
		return "login";
	}
}
