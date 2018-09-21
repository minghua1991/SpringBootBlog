package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Like;
import com.example.model.Post;
import com.example.model.User;

@Repository("likeRepository")
public interface LikeRepository extends JpaRepository<Like, Integer> {
	Like findByLikeId(int likeId);

	List<Like> findByPost(Post post);

	List<Like> findByPostAndUser(Post post, User user);
}
