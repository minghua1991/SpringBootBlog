package com.example.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Post;
import com.example.model.User;
import com.example.repository.PostRepository;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;

@Service("postService")
public class PostServiceImp implements PostService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PostRepository postRepository;

	@Override
	public List<Post> listAll() {
		return postRepository.findAll();
	}

	@Override
	public Post getById(int id) {
		return postRepository.findByPostId(id);
	}

	@Override
	public void save(Post post) {
		post.setCreatedDateTime(Calendar.getInstance());
		postRepository.save(post);
	}

	@Override
	public void delete(int id) {
		postRepository.deleteById(id);
	}

	@Override
	public void update(Post post) {
		post.setModifiedDateTime(Calendar.getInstance());
		postRepository.save(post);
	}

	@Override
	public List<Post> listByUser(User user) {
		return postRepository.findByUser(user);
	}

	@Override
	public boolean isOwner(Post post, User user) {
		return post.getUser() == user;
	}

}
