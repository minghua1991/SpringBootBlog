package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Post;
import com.example.model.User;

@Repository("postRepository")
public interface PostRepository extends JpaRepository<Post, Integer> {
	 Post findByPostId(int postId);
	 
	 List<Post> findByUser(User user);
}
