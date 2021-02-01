package com.mihalypapp.redditclone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;

import com.mihalypapp.redditclone.dto.PostRequest;
import com.mihalypapp.redditclone.dto.PostResponse;
import com.mihalypapp.redditclone.model.Post;
import com.mihalypapp.redditclone.model.Subreddit;
import com.mihalypapp.redditclone.model.User;
import com.mihalypapp.redditclone.repository.CommentRepository;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

	@Autowired
	private CommentRepository commentRepository;
	
	@Mapping(target = "description", source = "postRequest.description")
	@Mapping(target = "name", source = "postRequest.name")
	@Mapping(target = "id", source = "postRequest.id")
	@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
	@Mapping(target = "subreddit", source = "subreddit")
	@Mapping(target = "voteCount", constant = "0")
	@Mapping(target = "user", source = "user")
	public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);

	@Mapping(target = "subredditName", source = "post.subreddit.name")
	@Mapping(target = "userName", source = "post.user.username")
	@Mapping(target = "commentCount", expression = "java(commentCount(post))")
	@Mapping(target = "duration", expression = "java(getDuration(post))")
	public abstract PostResponse mapToDto(Post post);
	
	Integer commentCount(Post post) {
		return commentRepository.countByPost(post);
	}
	
	String getDuration(Post post) {
		return new PrettyTime().format(post.getCreatedDate());
	}
}
