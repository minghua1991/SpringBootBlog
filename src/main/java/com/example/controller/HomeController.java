package com.example.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.User;
import com.example.service.UserService;

@RestController
public class HomeController {
	@Autowired
	private UserService userService;

	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public ModelAndView login2(Model model) {
		ModelAndView modelAndView = new ModelAndView("auth/login");
		User user = new User();
		modelAndView.addObject(user);
		return modelAndView;
	}

	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.POST)
	public ModelAndView login(Model model) {
		ModelAndView modelAndView = new ModelAndView("auth/dashboard");
		User user = new User();
		modelAndView.addObject(user);
		return modelAndView;
	}

	@RequestMapping(value = "/register")
	public ModelAndView register() {
		ModelAndView modelAndView = new ModelAndView("auth/register");
		User user = new User();
		modelAndView.addObject(user);
		return modelAndView;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User usernameExists = userService.findUserByUsername(user.getUsername());
		User emailExists = userService.findUserByEmail(user.getEmail());
		if (usernameExists != null) {
			bindingResult.rejectValue("username", "error.user",
					"There is already a user registered with the username provided");
		}

		if (emailExists != null) {
			bindingResult.rejectValue("email", "error.user2",
					"There is already a user registered with the email provided");
		}

		if (!user.getPassword().equals(user.getConfirmedPassword())) {
			bindingResult.rejectValue("confirmedPassword", "error.password", "Please confirm your password");
		}

		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("auth/register");
		} else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("auth/register");

		}
		return modelAndView;
	}

	@RequestMapping(value = "/dashboard")
	public ModelAndView dashboard() {
		ModelAndView modelAndView = new ModelAndView("auth/dashboard");
		return modelAndView;
	}
	
	@RequestMapping(value = "/test")
	public String test() {
		return "hello test";
	}
}
