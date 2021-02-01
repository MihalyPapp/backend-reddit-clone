package com.mihalypapp.redditclone.dto;

import com.mihalypapp.redditclone.model.VoteType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VoteRequest {
	
	private VoteType type;
	private Long postId;
	
}
