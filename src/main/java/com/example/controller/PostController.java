package com.example.controller;

import java.util.List;

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
import com.example.model.Like;
import com.example.model.Post;
import com.example.model.User;
import com.example.service.CommentService;
import com.example.service.LikeService;
import com.example.service.PostService;
import com.example.service.UserService;

@RestController
public class PostController {
	@Autowired
	private UserService userService;

	@Autowired
	private PostService postService;

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private LikeService likeService;

	@RequestMapping(value = "/post/create", method = RequestMethod.GET)
	public ModelAndView createPostHandler(Model model) {
		ModelAndView modelAndView = null;

		// Check if the user is logged in
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User loggedInUser = userService.findUserByUsername(auth.getName());

		if (loggedInUser == null) {
			modelAndView = new ModelAndView("redirect:/login?error=true");
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
			modelAndView = new ModelAndView("redirect:/login?error=true");
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

	@RequestMapping(value = "/post/show/{postId}", method = RequestMethod.GET)
	public ModelAndView handlePostShowHandler(@PathVariable int postId, Model model) {
		ModelAndView modelAndView = new ModelAndView("post/show");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User loggedInUser = userService.findUserByUsername(auth.getName());

		if (loggedInUser != null) {
			modelAndView.addObject("username", loggedInUser.getUsername());
		}

		Post postInDB = postService.getById(postId);

		if (postInDB == null) {
			return new ModelAndView("redirect:/login?error=true");
		}

		List<Comment> commentList = commentService.listByPost(postInDB);
		
		for(int i = 0; i < commentList.size(); i ++) {
			modelAndView.addObject("comment" + commentList.get(i).getCommentId() + "InDB", commentList.get(i));
		}
		
		//like details
		List<Like> likesOfPost = likeService.listByPost(postInDB);
		int numberOfLike = likesOfPost == null? 0: likesOfPost.size();
		
		boolean isLikedByLoggedUser = false;
		List<Like> likesOfPostOfLoggedUser = likeService.getByPostAndUser(postInDB, loggedInUser);
		if(likesOfPostOfLoggedUser != null && likesOfPostOfLoggedUser.size() > 0) {
			isLikedByLoggedUser=true;
		}

		modelAndView.addObject("isLikedByLoggedUser", isLikedByLoggedUser);
		modelAndView.addObject("numberOfLike", numberOfLike);
		modelAndView.addObject("commentList", commentList);
		modelAndView.addObject("comment", new Comment());
		modelAndView.addObject("post", postInDB);
		return modelAndView;
	}

	@RequestMapping(value = "/post/delete/{postId}", method = RequestMethod.GET)
	public ModelAndView handlePostDeletionHandler(@PathVariable int postId, Model model) {
		ModelAndView modelAndView = null;

		// Check if the user is logged in
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User loggedInUser = userService.findUserByUsername(auth.getName());

		if (loggedInUser == null) {
			modelAndView = new ModelAndView("redirect:/login?error=true");
			return modelAndView;
		}

		boolean isOwner = false;
		Post post = postService.getById(postId);

		if (loggedInUser != null && post != null) {
			isOwner = postService.isOwner(post, loggedInUser);
		}

		if (loggedInUser == null || post == null || !isOwner) {
			modelAndView = new ModelAndView("redirect:/dashboard?error=true");
			return modelAndView;
		}

		if (isOwner) {
			postService.delete(postId);
		}

		modelAndView = new ModelAndView("redirect:/dashboard");
		return modelAndView;
	}

	@RequestMapping(value = "/post/edit/{postId}", method = RequestMethod.GET)
	public ModelAndView handlePostEditionHandler(@PathVariable int postId, Model model) {
		ModelAndView modelAndView = null;

		// Check if the user is logged in
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User loggedInUser = userService.findUserByUsername(auth.getName());

		if (loggedInUser == null) {
			modelAndView = new ModelAndView("redirect:/login?error=true");
			return modelAndView;
		}

		boolean isOwner = false;
		Post post = postService.getById(postId);

		if (loggedInUser != null && post != null) {
			isOwner = postService.isOwner(post, loggedInUser);
		}

		if (loggedInUser == null || post == null || !isOwner) {
			modelAndView = new ModelAndView("redirect:/dashboard?error=true");
			return modelAndView;
		}

		if (isOwner) {
			modelAndView = new ModelAndView("post/edit");
			modelAndView.addObject("post", post);
			System.out.println("test user " + post.getUser());
			modelAndView.addObject("username", loggedInUser.getUsername());
		}

		return modelAndView;
	}

	@RequestMapping(value = "/post/edit/{postId}", method = RequestMethod.POST)
	public ModelAndView submitPostEditionHandler(@Valid Post postFromForm, BindingResult bindingResult,
			@PathVariable int postId, Model model) {
		ModelAndView modelAndView = null;

		// Check if the user is logged in
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User loggedInUser = userService.findUserByUsername(auth.getName());

		if (loggedInUser == null) {
			modelAndView = new ModelAndView("redirect:/login?error=true");
			return modelAndView;
		}

		boolean isOwner = false;
		Post post = postService.getById(postId);

		if (loggedInUser != null && post != null) {
			isOwner = postService.isOwner(post, loggedInUser);
		}

		if (loggedInUser == null || post == null || !isOwner || postFromForm.getPostId() != postId) {
			modelAndView = new ModelAndView("redirect:/dashboard?error=true");
			return modelAndView;
		}

		postFromForm.setUser(loggedInUser);
		postFromForm.setCreatedDateTime(post.getCreatedDateTime());

		if (bindingResult.hasErrors()) {
			modelAndView = new ModelAndView("post/edit");
			modelAndView.addObject("post", postFromForm);
			modelAndView.addObject("username", loggedInUser.getUsername());
			return modelAndView;
		}

		postService.update(postFromForm);

		modelAndView = new ModelAndView("redirect:/dashboard");
		return modelAndView;
	}
}
