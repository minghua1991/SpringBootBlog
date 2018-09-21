package com.example.service;

import java.util.List;

import com.example.model.Like;
import com.example.model.Post;
import com.example.model.User;

public interface LikeService {
	Like getByLikeId(int likeId);

	List<Like> listByPost(Post post);

	List<Like> getByPostAndUser(Post post, User user);

	void save(Like like);

	void delete(Like like);

	void deleteByPost(Post post);

	boolean isOwner(Like like, Post post, User user);
}