package com.example.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
		ModelAndView modelAndView = null;
		
		// Check if the user is logged in
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User loggedInUser = userService.findUserByUsername(auth.getName());
		if(loggedInUser != null) {
			modelAndView = new ModelAndView("redirect:/dashboard");
			return modelAndView;
		}
		
		modelAndView = new ModelAndView("auth/login");
		User user = new User();
		modelAndView.addObject(user);
		return modelAndView;
	}

	@RequestMapping(value = "/register")
	public ModelAndView register() {
		ModelAndView modelAndView = null;
		
		// Check if the user is logged in
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User loggedInUser = userService.findUserByUsername(auth.getName());
		if(loggedInUser != null) {
			modelAndView = new ModelAndView("redirect:/dashboard");
			return modelAndView;
		}
		
		modelAndView = new ModelAndView("auth/register");
		User user = new User();
		modelAndView.addObject(user);
		return modelAndView;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = null;
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
			modelAndView= new ModelAndView("auth/register");
		} else {
			userService.saveUser(user);
			modelAndView = new ModelAndView("redirect:/login");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/dashboard")
	public ModelAndView dashboard() {
		ModelAndView modelAndView = new ModelAndView("auth/dashboard");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User loggedInUser = userService.findUserByUsername(auth.getName());
		String loggedInUsername = null;
		if(loggedInUser != null) {
			loggedInUsername = loggedInUser.getUsername();
		}
		modelAndView.addObject("username", loggedInUsername);
		return modelAndView;
	}

	@RequestMapping(value = "/test")
	public String test() {
		return "hello test";
	}
}
