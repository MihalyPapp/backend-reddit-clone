package com.mihalypapp.redditclone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.mihalypapp.redditclone.dto.CommentDto;
import com.mihalypapp.redditclone.model.Comment;
import com.mihalypapp.redditclone.model.Post;
import com.mihalypapp.redditclone.model.User;

@Mapper(componentModel = "spring")
public interface CommentMapper {
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "post", source = "post")
	@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
	@Mapping(target = "user", source = "user")
	Comment map(CommentDto commentDto, Post post, User user);
	
	@Mapping(target = "postId", source = "comment.post.id")
	@Mapping(target = "userName", source = "comment.user.username")
	CommentDto mapToDto(Comment comment);

}
