package com.mihalypapp.redditclone.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mihalypapp.redditclone.dto.PostRequest;
import com.mihalypapp.redditclone.dto.PostResponse;
import com.mihalypapp.redditclone.exception.PostNotFoundException;
import com.mihalypapp.redditclone.exception.SubredditNotFoundException;
import com.mihalypapp.redditclone.mapper.PostMapper;
import com.mihalypapp.redditclone.model.Post;
import com.mihalypapp.redditclone.model.Subreddit;
import com.mihalypapp.redditclone.model.User;
import com.mihalypapp.redditclone.repository.PostRepository;
import com.mihalypapp.redditclone.repository.SubredditRepository;
import com.mihalypapp.redditclone.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostService {

	private final SubredditRepository subredditRepository;
	private final AuthService authService;
	private final PostMapper postMapper;
	private final PostRepository postRepository;
	private final UserRepository userRepository;

	@Transactional
	public void save(PostRequest postRequest) {
		/*Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
				.orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
		postRepository.save(postMapper.map(postRequest, subreddit, authService.getCurrentUser()));*/
		
		Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
				.orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
		subreddit.getPosts().add(postMapper.map(postRequest, subreddit, authService.getCurrentUser()));
		subredditRepository.save(subreddit);
	}

	@Transactional(readOnly = true)
	public PostResponse getPost(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id.toString()));
		return postMapper.mapToDto(post);
	}

	@Transactional(readOnly = true)
	public List<PostResponse> getAllPosts() {
		return postRepository.findAll().stream().map(postMapper::mapToDto).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<PostResponse> getPostsBySubreddit(Long id) {
		Subreddit subreddit = subredditRepository.findById(id)
				.orElseThrow(() -> new SubredditNotFoundException(id.toString()));
		List<Post> posts = postRepository.findAllBySubreddit(subreddit);
		return posts.stream().map(postMapper::mapToDto).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<PostResponse> getPostsByUsername(String username) {
		User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
		List<Post> posts = postRepository.findAllByUser(user);
		return posts.stream().map(postMapper::mapToDto).collect(Collectors.toList());
	}

}
