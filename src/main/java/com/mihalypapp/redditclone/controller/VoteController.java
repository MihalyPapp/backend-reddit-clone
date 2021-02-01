package com.mihalypapp.redditclone.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mihalypapp.redditclone.dto.VoteRequest;
import com.mihalypapp.redditclone.service.VoteService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/vote")
@AllArgsConstructor
public class VoteController {

	private final VoteService voteService;
	
	@PostMapping
	public ResponseEntity<Object> vote(@RequestBody VoteRequest voteRequest) {
		voteService.vote(voteRequest);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
