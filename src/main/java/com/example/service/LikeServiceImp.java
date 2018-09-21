package com.example.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Like;
import com.example.model.Post;
import com.example.model.User;
import com.example.repository.CommentRepository;
import com.example.repository.LikeRepository;

@Service("likeService")
public class LikeServiceImp implements LikeService {
	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private LikeRepository likeRepository;

	@Override
	public List<Like> listByPost(Post post) {
		return likeRepository.findByPost(post);
	}

	@Override
	public Like getByLikeId(int likeId) {
		return likeRepository.findByLikeId(likeId);
	}

	@Override
	public void save(Like like) {
		like.setCreatedDateTime(Calendar.getInstance());
		likeRepository.save(like);
	}

	@Override
	public void delete(Like like) {
		likeRepository.delete(like);
	}

	@Override
	public void deleteByPost(Post post) {
		List<Like> likes = likeRepository.findByPost(post);
		for (Like like : likes) {
			likeRepository.delete(like);
		}
	}

	@Override
	public boolean isOwner(Like like, Post post, User user) {
		if (like.getPost().getPostId() == post.getPostId() && like.getUser().getUserId() == user.getUserId()) {
			return true;
		}
		return false;
	}

	@Override
	public List<Like> getByPostAndUser(Post post, User user) {
		return likeRepository.findByPostAndUser(post, user);
	}
}
