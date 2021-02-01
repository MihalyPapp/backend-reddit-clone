package com.mihalypapp.redditclone.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.mihalypapp.redditclone.dto.SubredditRequest;
import com.mihalypapp.redditclone.model.Post;
import com.mihalypapp.redditclone.model.Subreddit;

@Mapper(componentModel = "spring")
public interface SubredditMapper {

	@Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
	SubredditRequest mapToDto(Subreddit subreddit);

	default Integer mapPosts(List<Post> posts) {
		return posts.size();
	}
	
	@InheritInverseConfiguration
	@Mapping(target = "posts", ignore = true)
	Subreddit map(SubredditRequest subredditRequest);
}
