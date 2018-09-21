package com.example.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.Post;
import com.example.model.User;
import com.example.service.PostService;
import com.example.service.UserService;

@RestController
public class HomeController {
	@Autowired
	private UserService userService;

	@Autowired
	private PostService postService;

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public ModelAndView home(Model model) {
		ModelAndView modelAndView = new ModelAndView("post/index");

		List<Post> postList = postService.listAll();
		Collections.reverse(postList);

		modelAndView.addObject("postList", postList);
		
		// Check if the user is logged in
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User loggedInUser = userService.findUserByUsername(auth.getName());
		if (loggedInUser != null) {
			modelAndView.addObject("username", loggedInUser.getUsername());
		}

		return modelAndView;
	}

	// @RequestMapping(value = "/test")
	// public String test() {
	// return "hello test";
	// }
}
