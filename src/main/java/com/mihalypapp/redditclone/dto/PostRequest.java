package com.mihalypapp.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostRequest {

	private Long id;
	private String subredditName;
	private String name;
	private String url;
	private String description;
	
}
