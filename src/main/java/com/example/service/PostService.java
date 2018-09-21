package com.example.service;

import java.util.List;

import com.example.model.Post;
import com.example.model.User;

public interface PostService {
	List<Post> listAll();

	List<Post> listByUser(User user);

	Post getById(int postId);

	void save(Post post);

	void update(Post post);

	void delete(Post post);

	boolean isOwner(Post post, User user);
}
