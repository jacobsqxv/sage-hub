package dev.aries.sagehub.service.emailservice;

import lombok.Builder;

@Builder
public record EmailDetails(
		EmailTemplate template,
		String recipient,
		String username,
		String password,
		String url
) {
}
