package com.mihalypapp.redditclone.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mihalypapp.redditclone.dto.CommentDto;
import com.mihalypapp.redditclone.exception.PostNotFoundException;
import com.mihalypapp.redditclone.mapper.CommentMapper;
import com.mihalypapp.redditclone.model.NotificationEmail;
import com.mihalypapp.redditclone.model.Post;
import com.mihalypapp.redditclone.model.User;
import com.mihalypapp.redditclone.repository.CommentRepository;
import com.mihalypapp.redditclone.repository.PostRepository;
import com.mihalypapp.redditclone.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentService {

	// TODO: Construct POST_URL
	private static final String POST_URL = "TODO";

	private final PostRepository postRepository;
	private final AuthService authService;
	private final CommentMapper commentMapper;
	private final CommentRepository commentRepository;
	private final MailContentBuilder mailContentBuilder;
	private final MailService mailService;
	private final UserRepository UserRepository;

	@Transactional
	public void save(CommentDto commentDto) {
		Post post = postRepository.findById(commentDto.getPostId())
				.orElseThrow(() -> new PostNotFoundException(commentDto.getPostId().toString()));
		commentRepository.save(commentMapper.map(commentDto, post, authService.getCurrentUser()));
		sendCommentNotification(post.getUser());
	}

	private void sendCommentNotification(User user) {
		String message = mailContentBuilder
				.build(authService.getCurrentUser().getUsername() + " posted a comment on your post: " + POST_URL);
		mailService.sendMail(
				new NotificationEmail(user.getUsername() + " commented on your post", user.getEmail(), message));
	}

	@Transactional(readOnly = true)
	public List<CommentDto> getAllCommentsForPost(Long postId) {
		Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
		return commentRepository.findAllByPost(post).stream().map(commentMapper::mapToDto).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<CommentDto> getAllCommentsForUser(String username) {
		User user = UserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
		return commentRepository.findAllByUser(user).stream().map(commentMapper::mapToDto).collect(Collectors.toList());
	}

}
