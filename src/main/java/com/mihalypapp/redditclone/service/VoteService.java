package com.mihalypapp.redditclone.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.mihalypapp.redditclone.dto.VoteRequest;
import com.mihalypapp.redditclone.exception.PostNotFoundException;
import com.mihalypapp.redditclone.exception.RedditCloneException;
import com.mihalypapp.redditclone.mapper.VoteMapper;
import com.mihalypapp.redditclone.model.Post;
import com.mihalypapp.redditclone.model.Vote;
import com.mihalypapp.redditclone.repository.PostRepository;
import com.mihalypapp.redditclone.repository.VoteRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VoteService {

	private final VoteRepository voteRepository;
	private final PostRepository postRepository;
	private final AuthService authService;
	private final VoteMapper voteMapper;

	@Transactional
	public void vote(VoteRequest voteRequest) {
		Post post = postRepository.findById(voteRequest.getPostId())
				.orElseThrow(() -> new PostNotFoundException(voteRequest.getPostId().toString()));
		Optional<Vote> latestVote = voteRepository.findTopByPostAndUserOrderByIdDesc(post,
			authService.getCurrentUser());
		
		if (latestVote.isPresent() && latestVote.get().getType().equals(voteRequest.getType())) {
			throw new RedditCloneException("You've already " + voteRequest.getType() + " this post.");
		}
		
		if (latestVote.isPresent()) 
			post.setVoteCount(post.getVoteCount() + voteRequest.getType().getDirection());
		post.setVoteCount(post.getVoteCount() + voteRequest.getType().getDirection());
		
		voteRepository.save(voteMapper.map(voteRequest, authService.getCurrentUser(), post));
		postRepository.save(post);
	}

}
