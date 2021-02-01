package com.mihalypapp.redditclone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mihalypapp.redditclone.model.RefreshToken;
import com.mihalypapp.redditclone.model.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{
	Optional<RefreshToken> findByToken(String token);
	
	@Query("SELECT rt.user FROM RefreshToken rt WHERE rt.token = :token")
	Optional<User> findUserByToken(@Param("token") String token);
	
	void deleteByToken(String token);
}
