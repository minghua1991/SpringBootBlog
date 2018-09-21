package com.example.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.Like;
import com.example.model.Post;
import com.example.model.User;
import com.example.service.CommentService;
import com.example.service.LikeService;
import com.example.service.PostService;
import com.example.service.UserService;

@RestController
public class LikeController {
	@Autowired
	private UserService userService;

	@Autowired
	private PostService postService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private LikeService likeService;

	@RequestMapping(value = "post/show/{postId}/upvote", method = POST)
	public ModelAndView upvote(Model model, @PathVariable int postId) {
		ModelAndView modelAndView = null;

		// Check if the user is logged in
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User loggedInUser = userService.findUserByUsername(auth.getName());
		Post postInDB = postService.getById(postId);

		if (loggedInUser == null || postInDB == null) {
			return new ModelAndView("redirect:/login?error=true");
		}

		List<Like> likesOfPostAndUser = likeService.getByPostAndUser(postInDB, loggedInUser);

		if (likesOfPostAndUser == null || likesOfPostAndUser.size() == 0) {
			Like like = new Like();
			like.setUser(loggedInUser);
			like.setPost(postInDB);
			likeService.save(like);
		}

		modelAndView = new ModelAndView("redirect:/post/show/{postId}");
		return modelAndView;
	}

	@RequestMapping(value = "post/show/{postId}/downvote", method = POST)
	public ModelAndView downvote(Model model, @PathVariable int postId) {
		ModelAndView modelAndView = null;

		// Check if the user is logged in
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User loggedInUser = userService.findUserByUsername(auth.getName());
		Post postInDB = postService.getById(postId);

		if (loggedInUser == null || postInDB == null) {
			return new ModelAndView("redirect:/login?error=true");
		}

		List<Like> likesOfPostAndUser = likeService.getByPostAndUser(postInDB, loggedInUser);

		if (likesOfPostAndUser != null && likesOfPostAndUser.size() != 0) {
			for (Like like : likesOfPostAndUser) {
				if (likeService.isOwner(like, postInDB, loggedInUser)) {
					likeService.deleteByLikeId(like.getLikeId());
				}
			}
		}

		modelAndView = new ModelAndView("redirect:/post/show/{postId}");
		return modelAndView;
	}
}