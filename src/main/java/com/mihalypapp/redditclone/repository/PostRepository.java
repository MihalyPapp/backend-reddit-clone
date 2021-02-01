package com.mihalypapp.redditclone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mihalypapp.redditclone.model.Post;
import com.mihalypapp.redditclone.model.Subreddit;
import com.mihalypapp.redditclone.model.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findAllBySubreddit(Subreddit subreddit);

	List<Post> findAllByUser(User user);
}
