package com.mihalypapp.redditclone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.mihalypapp.redditclone.dto.VoteRequest;
import com.mihalypapp.redditclone.model.Post;
import com.mihalypapp.redditclone.model.User;
import com.mihalypapp.redditclone.model.Vote;

@Mapper(componentModel = "spring")
public interface VoteMapper {

	@Mapping(target = "user", source = "user")
	@Mapping(target = "post", source = "post")
	@Mapping(target = "id", ignore = true)
	Vote map(VoteRequest voteRequest, User user, Post post);
	
}
