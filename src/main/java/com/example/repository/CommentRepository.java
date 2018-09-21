package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Post;
import com.example.model.User;
import com.example.model.Comment;

@Repository("commentRepository")
public interface CommentRepository extends JpaRepository<Comment, Integer> {
	Comment findByCommentId(int commentId);

	List<Comment> findByUser(User user);

	List<Comment> findByPost(Post post);
}
