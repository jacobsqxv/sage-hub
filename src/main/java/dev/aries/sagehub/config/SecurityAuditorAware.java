package dev.aries.sagehub.config;

import java.util.Optional;

import dev.aries.sagehub.constant.ExceptionConstants;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component("auditorProvider")
public class SecurityAuditorAware implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String requestId = MDC.get("requestId");
		if (authentication == null || !authentication.isAuthenticated()
				|| authentication instanceof AnonymousAuthenticationToken) {
			if (authentication == null) {
				log.info("Auditor: SYSTEM");
				return Optional.of("SYSTEM");
			}

			WebAuthenticationDetails request = (WebAuthenticationDetails) authentication.getDetails();
			if (request != null) {
				try {
					String clientId = getClientId(authentication);
					log.info("Auditor: {}", clientId);
					return Optional.of(clientId);
				}
				catch (IllegalArgumentException ex) {
					log.info("Auditor (No auth): {}, Request ID: {}",
							request.getRemoteAddress(), requestId);
					return Optional.of(request.getRemoteAddress());
				}
			}
		}

		log.info("Auditor: {}, Request ID: {}", getClientId(authentication), requestId);
		return Optional.of(getClientId(authentication));
	}

	public String getClientId(Authentication authentication) {
		if (authentication.getPrincipal() instanceof Jwt jwt) {
			return jwt.getClaim("clientId");
		}
		else {
			throw new IllegalArgumentException(ExceptionConstants.INVALID_CREDENTIALS);
		}
	}
}
