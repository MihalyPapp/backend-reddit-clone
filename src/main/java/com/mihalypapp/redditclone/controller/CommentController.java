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

import com.mihalypapp.redditclone.dto.CommentDto;
import com.mihalypapp.redditclone.service.CommentService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/comment")
@AllArgsConstructor
public class CommentController {
	
	private final CommentService commentService;
	
	@PostMapping
	public ResponseEntity<Object> createComment(@RequestBody CommentDto commentDto) {
		commentService.save(commentDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping("/by-post/{postId}")
	public ResponseEntity<List<CommentDto>> getAllCommentsForPost(@PathVariable Long postId) {
		return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsForPost(postId));
	}
	
	@GetMapping("/by-user/{username}")
	public ResponseEntity<List<CommentDto>> getAllCommentsForUser(@PathVariable String username) {
		return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsForUser(username));
	}
	
}