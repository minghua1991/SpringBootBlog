package com.example.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.Like;
import com.example.model.Post;
import com.example.model.User;
import com.example.service.LikeService;
import com.example.service.PostService;
import com.example.service.UserService;

@RestController
public class HomeController {
	@Autowired
	private UserService userService;

	@Autowired
	private PostService postService;

	@Autowired
	private LikeService likeService;

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

		HashMap<Integer, int[]> likeDetails = new HashMap<Integer, int[]>();
		for (Post post : postList) {
			int isLikedByLoggedUser = 0;
			int[] isLikedByLoggedUserAndNumberOfLike = new int[2];
			if (loggedInUser != null) {
				List<Like> likeListInDB = likeService.getByPostAndUser(post, loggedInUser);
				if (likeListInDB != null && likeListInDB.size() > 0) {
					isLikedByLoggedUser = 1;
				}
			}
			List<Like> likeList = likeService.listByPost(post);
			int numberOfLike = likeList == null ? 0 : likeList.size();
			isLikedByLoggedUserAndNumberOfLike[0] = isLikedByLoggedUser;
			isLikedByLoggedUserAndNumberOfLike[1] = numberOfLike;

			likeDetails.put(post.getPostId(), isLikedByLoggedUserAndNumberOfLike);
		}

		modelAndView.addObject("likeDetails", likeDetails);

		return modelAndView;
	}
}
