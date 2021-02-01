package com.mihalypapp.redditclone.service;

import static com.mihalypapp.redditclone.util.Constants.ACTIVATION_EMAIL;

import java.time.Instant;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mihalypapp.redditclone.dto.AuthenticationResponse;
import com.mihalypapp.redditclone.dto.LoginRequest;
import com.mihalypapp.redditclone.dto.RefreshTokenRequest;
import com.mihalypapp.redditclone.dto.RegisterRequest;
import com.mihalypapp.redditclone.exception.RedditCloneException;
import com.mihalypapp.redditclone.model.NotificationEmail;
import com.mihalypapp.redditclone.model.User;
import com.mihalypapp.redditclone.model.VerificationToken;
import com.mihalypapp.redditclone.repository.UserRepository;
import com.mihalypapp.redditclone.repository.VerificationTokenRepository;
import com.mihalypapp.redditclone.security.JwtProvider;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

	private final PasswordEncoder encoder;
	private final UserRepository userRepository;
	private final VerificationTokenRepository verificationTokenRepository;
	private final MailService mailService;
	private final AuthenticationManager authenticationManager;
	private final JwtProvider jwtProvider;
	private final RefreshTokenService refreshTokenService;

	@Transactional
	public void signUp(RegisterRequest request) {
		User user = new User();
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		user.setPassword(encoder.encode(request.getPassword()));
		user.setCreated(Instant.now());
		user.setEnabled(false);
		userRepository.save(user);
		mailService.sendMail(new NotificationEmail("Please activate your account", request.getEmail(),
				"Thank you for sign up to Reddit Clone, plase click on the link below to activate your account: "
						+ ACTIVATION_EMAIL + "/" + generateVerificationToken(user)));

	}

	private String generateVerificationToken(User user) {
		String token = UUID.randomUUID()
				.toString();
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);
		verificationTokenRepository.save(verificationToken);
		return token;
	}

	@Transactional
	public void verifyAccount(String token) {
		fetchUserAndEnable(verificationTokenRepository.findByToken(token)
				.orElseThrow(() -> new RedditCloneException("Invalid Token")));
	}

	private void fetchUserAndEnable(VerificationToken verificationToken) {
		String username = verificationToken.getUser()
				.getUsername();
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new RedditCloneException("User not found with name: " + username));
		user.setEnabled(true);
		userRepository.save(user);
	}

	public AuthenticationResponse login(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext()
				.setAuthentication(authentication);
		String token = jwtProvider.generateToken(authentication);
		return AuthenticationResponse.builder()
				.authenticationToken(token)
				.refreshToken(refreshTokenService.generateRefreshToken(getCurrentUser()).getToken())
				.expiresAt(Instant.now()
						.plusMillis(jwtProvider.getJwtExpirationInMillis()))
				.username(loginRequest.getUsername())
				.build();
	}

	@Transactional(readOnly = true)
	public User getCurrentUser() {
		String username = SecurityContextHolder.getContext()
				.getAuthentication()
				.getName();
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
	}

	public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
		User user = refreshTokenService.validateRefreshToken(refreshTokenRequest.getToken());
		String token = jwtProvider.generateTokenWithUsername(user.getUsername());
		return AuthenticationResponse.builder()
				.authenticationToken(token)
				.refreshToken(refreshTokenRequest.getToken())
				.expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
				.username(refreshTokenRequest.getUsername())
				.build();
	}

}
