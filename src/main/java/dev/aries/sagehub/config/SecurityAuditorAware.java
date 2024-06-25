package dev.aries.sagehub.config;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component("auditorProvider")
public class SecurityAuditorAware implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			return Optional.empty();
		}
		log.info("Auditor: {}", authentication.getName());
		return Optional.of((authentication.getName()));
	}
}
