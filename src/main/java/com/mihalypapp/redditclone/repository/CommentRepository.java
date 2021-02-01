package com.mihalypapp.redditclone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mihalypapp.redditclone.model.Comment;
import com.mihalypapp.redditclone.model.Post;
import com.mihalypapp.redditclone.model.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findAllByPost(Post post);

	List<Comment> findAllByUser(User user);
	
	Integer countByPost(Post post);
}
