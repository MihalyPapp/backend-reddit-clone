package com.mihalypapp.redditclone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mihalypapp.redditclone.model.Post;
import com.mihalypapp.redditclone.model.User;
import com.mihalypapp.redditclone.model.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long>{
	Optional<Vote> findTopByPostAndUserOrderByIdDesc(Post post, User currentUser);
}
