package dev.aries.sagehub.service.emailservice;

import dev.aries.sagehub.model.attribute.Email;
import dev.aries.sagehub.model.attribute.Password;
import dev.aries.sagehub.model.attribute.Username;
import lombok.Builder;

@Builder
public record EmailDetails(
		EmailTemplate template,
		Email recipient,
		Username username,
		Password password,
		String url
) {
}
