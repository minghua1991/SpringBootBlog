package com.example.service;

import java.util.List;

import com.example.model.Post;
import com.example.model.User;
import com.example.model.Comment;

public interface CommentService {
	// List<Comment> listAll();

	// List<Comment> listByUser(User user);

	List<Comment> listByPost(Post post);

	Comment getById(int commentId);

	void save(Comment comment);

	void update(Comment comment);

	void delete(int id);

	boolean isOwner(Comment comment, User user);
}
