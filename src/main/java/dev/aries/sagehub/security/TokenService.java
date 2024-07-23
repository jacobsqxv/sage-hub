package dev.aries.sagehub.security;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.response.AuthToken;
import dev.aries.sagehub.enums.TokenType;
import dev.aries.sagehub.model.Token;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.model.attribute.Username;
import dev.aries.sagehub.repository.TokenRepository;
import dev.aries.sagehub.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class TokenService {
	private final JwtEncoder accessTokenEncoder;
	@Qualifier("jwtRefreshTokenEncoder")
	private final JwtEncoder refreshTokenEncoder;
	private final TokenRepository tokenRepository;
	private final UserUtil userUtil;

	private String generateAccessToken(Authentication authentication) {
		return createToken(authentication, TokenType.ACCESS_TOKEN, this.accessTokenEncoder);
	}

	private String generateRefreshToken(Authentication authentication) {
		return createToken(authentication, TokenType.REFRESH_TOKEN, this.refreshTokenEncoder);
	}

	private String createToken(Authentication authentication, TokenType tokenType, JwtEncoder encoder) {
		Instant now = Instant.now();
		List<String> authorities = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.toList();
		User user = this.userUtil.getUser(new Username((authentication.getName())));
		Duration expirationDuration = tokenType.equals(TokenType.ACCESS_TOKEN)
				? Duration.ofMinutes(15)
				: Duration.ofDays(30);
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer("SageHub")
				.claim("type", tokenType.toString())
				.claim("clientId", user.getClientId())
				.issuedAt(now)
				.expiresAt(now.plus(expirationDuration))
				.subject(authentication.getName())
				.claim("scope", authorities)
				.build();

		return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	}

	public AuthToken generateToken(Authentication authentication) {
		if (!(authentication.getPrincipal() instanceof UserDetailsImpl
				|| authentication.getPrincipal() instanceof Jwt)) {
			log.info("Principal type: {}", authentication.getPrincipal().getClass());
			throw new IllegalArgumentException(ExceptionConstants.INVALID_CREDENTIALS);
		}
		String accessToken = generateAccessToken(authentication);
		String refreshToken;
		log.info("INFO - Principal type: {}", authentication.getPrincipal().getClass());
		log.info("INFO - Authentication credentials: {}", authentication.getCredentials().getClass());
		if (authentication.getCredentials() instanceof Jwt jwt) {
			Instant now = Instant.now();
			Instant expiresAt = jwt.getExpiresAt();
			Duration duration = Duration.between(now, expiresAt);
			long daysUntilExpiration = duration.toDays();
			if (daysUntilExpiration < 7) {
				refreshToken = generateRefreshToken(authentication);
			}
			else {
				refreshToken = jwt.getTokenValue();
			}
		}
		else {
			refreshToken = generateRefreshToken(authentication);
		}
		User user = this.userUtil.getUser(new Username((authentication.getName())));
		AuthToken token = new AuthToken(accessToken, refreshToken);
		if (!refreshTokenExists(user.getId(), refreshToken)) {
			Token newToken = Token.builder()
					.value(token.refreshToken())
					.userId(user.getId())
					.expiresAt(LocalDateTime.ofInstant(
							Instant.now().plus(30, ChronoUnit.DAYS),
							ZoneId.systemDefault()))
					.type(TokenType.REFRESH_TOKEN)
					.build();
			this.tokenRepository.save(newToken);
		}
		return token;
	}

	public void updateRefreshToken(Long userId, String refreshToken, String newRefreshToken) {
		Token token = this.tokenRepository
				.findByValueAndUserIdAndType(refreshToken, userId, TokenType.REFRESH_TOKEN);
		if (token == null) {
			throw new IllegalArgumentException(String.format(ExceptionConstants.NOT_FOUND, "Token"));
		}
		token.setValue(newRefreshToken);
		token.setExpiresAt(LocalDateTime.ofInstant(
				Instant.now().plus(30, ChronoUnit.DAYS),
				ZoneId.systemDefault()));
		this.tokenRepository.save(token);
	}

	private boolean refreshTokenExists(Long userId, String refreshToken) {
		return this.tokenRepository.findByValueAndUserIdAndType(
				refreshToken, userId, TokenType.REFRESH_TOKEN) != null;
	}
}
