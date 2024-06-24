package dev.aries.sagehub.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TokenService {
	private final JwtEncoder encoder;

	public String generateToken(Authentication authentication) {
		Instant now = Instant.now();
		List<String> authorities = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.toList();
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer("SageHub")
				.issuedAt(now)
				.expiresAt(now.plus(1, ChronoUnit.HOURS))
				.subject(authentication.getName())
				.claim("scope", authorities)
				.build();

		return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	}
}
