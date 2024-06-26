package dev.aries.sagehub.config;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component("auditorProvider")
public class SecurityAuditorAware implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String requestId = MDC.get("requestId");
		if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
			if (authentication == null) {
				log.info("Auditor: SYSTEM");
				return Optional.of("SYSTEM");
			}
			WebAuthenticationDetails request = (WebAuthenticationDetails) authentication.getDetails();
			if (request != null) {
				log.info("Auditor (Unauthenticated): {}, Request ID: {}", request.getRemoteAddress(), requestId);
				return Optional.of(request.getRemoteAddress());
			}
		}

		log.info("Auditor: {}, Request ID: {}", authentication.getName(), requestId);
		return Optional.of((authentication.getName()));
	}
}
