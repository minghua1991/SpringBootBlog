package com.example.service;

import java.util.List;

import com.example.model.Like;
import com.example.model.Post;
import com.example.model.User;

public interface LikeService {

	List<Like> listByPost(Post post);

	Like getByLikeId(int likeId);

	List<Like> getByPostAndUser(Post post, User user);

	void save(Like like);

	void deleteByLikeId(int likeId);

	void deleteByPost(Post post);

	boolean isOwner(Like like, Post post, User user);
}