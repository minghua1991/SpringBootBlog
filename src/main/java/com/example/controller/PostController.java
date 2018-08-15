package com.example.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.Post;
import com.example.model.User;
import com.example.service.PostService;
import com.example.service.UserService;

@RestController
public class PostController {
	@Autowired
	private UserService userService;

	@Autowired
	private PostService postService;

	@RequestMapping(value = "/post/create", method = RequestMethod.GET)
	public ModelAndView createPostHandler(Model model) {
		ModelAndView modelAndView = null;

		// Check if the user is logged in
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User loggedInUser = userService.findUserByUsername(auth.getName());

		if (loggedInUser == null) {
			modelAndView = new ModelAndView("redirect:/login");
		} else {
			modelAndView = new ModelAndView("post/create");
			Post post = new Post();
			modelAndView.addObject(post);
			modelAndView.addObject("username", loggedInUser.getUsername());
		}
		return modelAndView;
	}

	@RequestMapping(value = "/post/create", method = RequestMethod.POST)
	public ModelAndView submitPostCreationHandler(@Valid Post post, BindingResult bindingResult) {
		ModelAndView modelAndView = null;

		// Check if the user is logged in
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User loggedInUser = userService.findUserByUsername(auth.getName());

		if (loggedInUser == null) {
			modelAndView = new ModelAndView("redirect:/login");
			return modelAndView;
		}
		
		if (bindingResult.hasErrors()) {
			modelAndView = new ModelAndView("post/create");
			return modelAndView;
		}
		
		
		post.setUser(loggedInUser);
		postService.save(post);
		
		modelAndView = new ModelAndView("redirect:/dashboard");
		return modelAndView;
	}
}
