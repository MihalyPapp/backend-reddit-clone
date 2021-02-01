package com.mihalypapp.redditclone.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mihalypapp.redditclone.dto.SubredditRequest;
import com.mihalypapp.redditclone.exception.RedditCloneException;
import com.mihalypapp.redditclone.mapper.SubredditMapper;
import com.mihalypapp.redditclone.model.Subreddit;
import com.mihalypapp.redditclone.repository.SubredditRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubredditService {

	private final SubredditRepository subredditRepository;
	private final SubredditMapper subredditMapper;

	@Transactional
	public SubredditRequest save(SubredditRequest subredditRequest) {
		Subreddit subreddit = subredditRepository.save(subredditMapper.map(subredditRequest));
		subredditRequest.setId(subreddit.getId());
		return subredditRequest;
	}

	@Transactional(readOnly = true)
	public List<SubredditRequest> getAll() {
		return subredditRepository.findAll().stream().map(subredditMapper::mapToDto)
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public SubredditRequest get(Long id) {
		return subredditMapper.mapToDto(subredditRepository.findById(id)
				.orElseThrow(() -> new RedditCloneException("No subbreddit found with id: " + id)));
	}

}
