package com.mihalypapp.redditclone.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mihalypapp.redditclone.exception.RedditCloneException;
import com.mihalypapp.redditclone.model.RefreshToken;
import com.mihalypapp.redditclone.model.User;
import com.mihalypapp.redditclone.repository.RefreshTokenRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RefreshTokenService {

	private final RefreshTokenRepository refreshTokenRepository;
	
	@Transactional
	public RefreshToken generateRefreshToken(User user) {
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setToken(UUID.randomUUID()
				.toString());
		refreshToken.setCreatedDate(Instant.now());
		refreshToken.setUser(user);
		return refreshTokenRepository.save(refreshToken);
	}
	
	@Transactional(readOnly = true)
	public User validateRefreshToken(String token) {
		return refreshTokenRepository.findUserByToken(token)
			.orElseThrow(() -> new RedditCloneException("Invalid refresh token"));
	}

	@Transactional
	public void deleteRefreshToken(String token) {
		refreshTokenRepository.deleteByToken(token);
	}
}
