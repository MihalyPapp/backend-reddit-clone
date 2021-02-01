package com.mihalypapp.redditclone.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentDto {

	private Long id;
	private String text;
	private Long postId;
	private String userName;
	private Instant createdDate;
	
}
