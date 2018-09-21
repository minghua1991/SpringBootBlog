package com.example.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Comment;
import com.example.model.Like;
import com.example.model.Post;
import com.example.model.User;
import com.example.repository.CommentRepository;
import com.example.repository.LikeRepository;
import com.example.repository.PostRepository;

@Service("postService")
public class PostServiceImp implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private LikeRepository likeRepository;

	@Override
	public List<Post> listAll() {
		return postRepository.findAll();
	}

	@Override
	public Post getById(int id) {
		return postRepository.findByPostId(id);
	}

	@Override
	public void save(Post post) {
		post.setCreatedDateTime(Calendar.getInstance());
		postRepository.save(post);
	}

	@Override
	public void delete(Post post) {
		List<Like> likes = likeRepository.findByPost(post);
		for (Like like : likes) {
			likeRepository.delete(like);
		}

		List<Comment> comments = commentRepository.findByPost(post);
		for (Comment comment : comments) {
			commentRepository.delete(comment);
		}

		postRepository.delete(post);
	}

	@Override
	public void update(Post post) {
		post.setModifiedDateTime(Calendar.getInstance());
		postRepository.save(post);
	}

	@Override
	public List<Post> listByUser(User user) {
		return postRepository.findByUser(user);
	}

	@Override
	public boolean isOwner(Post post, User user) {
		return post.getUser().getUserId() == user.getUserId();
	}

}
