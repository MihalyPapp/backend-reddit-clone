package com.mihalypapp.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostResponse {

	private Long id;
	private String subredditName;
	private String name;
	private String url;
	private String description;
	private String userName;
	private Integer voteCount;
	private Integer commentCount;
	private String duration;
}