package com.example.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.Comment;
import com.example.model.Post;
import com.example.model.User;
import com.example.service.CommentService;
import com.example.service.PostService;
import com.example.service.UserService;

@RestController
public class CommentController {
	@Autowired
	private UserService userService;

	@Autowired
	private PostService postService;

	@Autowired
	private CommentService commentService;

	@RequestMapping(value = "/post/show/{postId}/comment/create", method = RequestMethod.POST)
	public ModelAndView commentCreationHandler(@Valid Comment comment, BindingResult bindingResult,
			@PathVariable int postId, Model model) {
		ModelAndView modelAndView = null;

		// Check if the user is logged in
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User loggedInUser = userService.findUserByUsername(auth.getName());

		if (loggedInUser == null) {
			modelAndView = new ModelAndView("redirect:/login?error=true");
			return modelAndView;
		}

		if (bindingResult.hasErrors()) {
			modelAndView = new ModelAndView("redirect:/post/show/{postId}?error=true");
			return modelAndView;
		}

		Post postInDB = postService.getById(postId);

		if (postInDB == null) {
			modelAndView = new ModelAndView("redirect:/login?error=true");
			return modelAndView;
		}

		comment.setPost(postInDB);
		comment.setUser(loggedInUser);
		commentService.save(comment);

		modelAndView = new ModelAndView("redirect:/post/show/{postId}");
		return modelAndView;
	}

	@RequestMapping(value = "/post/show/{postId}/comment/{commentId}/edit", method = RequestMethod.POST)
	public ModelAndView commentEditionHandler(@Valid Comment comment, BindingResult bindingResult,
			@PathVariable int commentId, Model model) {
		ModelAndView modelAndView = null;

		// Check if the user is logged in
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User loggedInUser = userService.findUserByUsername(auth.getName());

		if (loggedInUser == null) {
			modelAndView = new ModelAndView("redirect:/login?error=true");
			return modelAndView;
		}

		Comment commentInDB = commentService.getById(commentId);

		if (commentInDB == null) {
			modelAndView = new ModelAndView("redirect:/login?error=true");
			return modelAndView;
		}

		if (bindingResult.hasErrors()) {
			// this one needs updating
			modelAndView = new ModelAndView("redirect:/post/show/{postId}");
			return modelAndView;
		}

		commentInDB.setContent(comment.getContent());

		commentService.update(commentInDB);

		modelAndView = new ModelAndView("redirect:/post/show/{postId}");
		return modelAndView;
	}

	@RequestMapping(value = "/post/show/{postId}/comment/{commentId}/delete", method = RequestMethod.POST)
	public ModelAndView commentDeletionHandler(@PathVariable int commentId, Model model) {
		ModelAndView modelAndView = null;

		// Check if the user is logged in
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User loggedInUser = userService.findUserByUsername(auth.getName());

		if (loggedInUser == null) {
			modelAndView = new ModelAndView("redirect:/login?error=true");
			return modelAndView;
		}

		Comment commentInDB = commentService.getById(commentId);

		if (commentInDB == null) {
			modelAndView = new ModelAndView("redirect:/login?error=true");
			return modelAndView;
		}

		commentService.delete(commentInDB);

		modelAndView = new ModelAndView("redirect:/post/show/{postId}");
		return modelAndView;
	}
}
