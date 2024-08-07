package dev.aries.sagehub.security;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.response.AuthToken;
import dev.aries.sagehub.enums.TokenStatus;
import dev.aries.sagehub.enums.TokenType;
import dev.aries.sagehub.model.Token;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class TokenService {
	private final JwtEncoder accessTokenEncoder;
	@Qualifier("jwtRefreshTokenEncoder")
	private final JwtEncoder refreshTokenEncoder;
	@Qualifier("jwtRefreshTokenDecoder")
	private final JwtDecoder refreshTokenDecoder;
	private final TokenRepository tokenRepository;

	private String generateAccessToken(User user) {
		return createToken(user, TokenType.ACCESS_TOKEN, accessTokenEncoder);
	}

	private String generateRefreshToken(User user) {
		return createToken(user, TokenType.REFRESH_TOKEN, refreshTokenEncoder);
	}

	private String createToken(User user, TokenType tokenType, JwtEncoder encoder) {
		Instant now = Instant.now();
		log.info("Generating {}...", tokenType);
		List<String> authorities = new UserDetailsImpl(user).getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority)
				.toList();
		Duration expirationDuration = tokenType.equals(TokenType.ACCESS_TOKEN)
				? Duration.ofMinutes(30)
				: Duration.ofDays(7);
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer("SageHub")
				.claim("type", tokenType.toString())
				.claim("clientId", user.getClientId())
				.issuedAt(now)
				.expiresAt(now.plus(expirationDuration))
				.subject(user.getUsername())
				.claim("scope", authorities)
				.build();

		return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	}

	public AuthToken generateToken(Authentication authentication, User user, boolean rememberMe) {
		if (!(authentication.getPrincipal() instanceof UserDetailsImpl
				|| authentication.getPrincipal() instanceof Jwt)) {
			log.info("Principal type: {}", authentication.getPrincipal().getClass());
			throw new IllegalArgumentException(ExceptionConstants.AUTHENTICATION_FAILED);
		}
		String accessToken = generateAccessToken(user);
		String refreshToken;
		log.info("Principal type: {}", authentication.getPrincipal().getClass());
		log.info("Authentication credentials: {}", authentication.getCredentials().getClass());
		if (rememberMe) {
			refreshToken = getRefreshToken(authentication, user);
		}
		else {
			refreshToken = null;
		}
		return new AuthToken(accessToken, refreshToken);
	}

	private String getRefreshToken(Authentication authentication, User user) {
		String refreshToken;
		if (authentication.getPrincipal() instanceof JwtAuthenticationToken jwt
		&& jwt.getToken().getClaim("type").equals(TokenType.REFRESH_TOKEN.toString())) {
			Instant now = Instant.now();
			Instant expiresAt = jwt.getToken().getExpiresAt();
			Duration duration = Duration.between(now, expiresAt);
			long daysUntilExpiration = duration.toDays();
			if (daysUntilExpiration < 3) {
				refreshToken = generateRefreshToken(user);
			}
			else {
				refreshToken = jwt.getToken().getTokenValue();
			}
		}
		else {
			refreshToken = generateRefreshToken(user);
		}
		return refreshToken;
	}

	public void blacklistOldToken(Long userId, String refreshToken) {
		if (!tokenRepository.existsByValueAndType(refreshToken, TokenType.REFRESH_TOKEN)) {
			Jwt jwt = refreshTokenDecoder.decode(refreshToken);
			LocalDateTime expiry = LocalDateTime.ofInstant(
					Objects.requireNonNull(jwt.getExpiresAt()), ZoneId.systemDefault());
			Token blacklistedToken = Token.builder()
					.type(TokenType.REFRESH_TOKEN)
					.status(TokenStatus.EXPIRED)
					.userId(userId)
					.value(refreshToken)
					.expiresAt(expiry)
					.build();
			log.info("Blacklisting old token...");
			tokenRepository.save(blacklistedToken);
		}
		else {
			log.info("Old token already blacklisted");
			validateToken(refreshToken);
		}
	}

	private void validateToken(String refreshToken) {
		if (tokenRepository.existsByValueAndType(refreshToken, TokenType.REFRESH_TOKEN)) {
			throw new IllegalArgumentException(ExceptionConstants.BLACKLISTED_TOKEN);
		}
	}
}
