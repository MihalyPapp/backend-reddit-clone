package com.mihalypapp.redditclone.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum VoteType {

	UPVOTE(1), DOWNVOTE(-1);

	private int direction;
	
}
