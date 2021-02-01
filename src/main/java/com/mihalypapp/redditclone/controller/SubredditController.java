package com.mihalypapp.redditclone.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mihalypapp.redditclone.dto.SubredditRequest;
import com.mihalypapp.redditclone.service.SubredditService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
public class SubredditController {

	private final SubredditService subredditService;
	
	@PostMapping
	public ResponseEntity<SubredditRequest> createSubreddit(@RequestBody SubredditRequest subredditRequest) {
		return ResponseEntity.status(HttpStatus.CREATED).body(subredditService.save(subredditRequest));
	}
	
	@GetMapping
	public ResponseEntity<List<SubredditRequest>> getAllSubreddits() {
		return ResponseEntity.status(HttpStatus.OK).body(subredditService.getAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<SubredditRequest> getSubreddit(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(subredditService.get(id));
	}
	
}
