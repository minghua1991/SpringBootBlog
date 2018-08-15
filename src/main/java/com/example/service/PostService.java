package com.example.service;

import java.util.List;

import com.example.model.Post;
import com.example.model.User;

public interface PostService {
	List<Post> listAll();

	List<Post> listByUser(User user);
	
	Post getById(Long id);

	void save(Post post);
	
	void update(Post post);

	void delete(Long id);
}
