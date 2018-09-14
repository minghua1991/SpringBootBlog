package com.example.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Comment;
import com.example.model.Post;
import com.example.model.User;
import com.example.repository.CommentRepository;

@Service("commentService")
public class CommentServiceImp implements CommentService {
	@Autowired
	private CommentRepository commentRepository;

	@Override
	public List<Comment> listByPost(Post post) {
		return commentRepository.findByPost(post);
	}

	@Override
	public Comment getById(int commentId) {
		return commentRepository.findByCommentId(commentId);
	}

	@Override
	public void save(Comment comment) {
		comment.setCreatedDateTime(Calendar.getInstance());
		commentRepository.save(comment);
	}

	@Override
	public void update(Comment comment) {
		comment.setModifiedDateTime(Calendar.getInstance());
		commentRepository.save(comment);
	}

	@Override
	public void delete(int id) {
		commentRepository.deleteById(id);
	}

	@Override
	public boolean isOwner(Comment comment, User user) {
		return comment.getUser().getUserId() == user.getUserId();
	}

}
