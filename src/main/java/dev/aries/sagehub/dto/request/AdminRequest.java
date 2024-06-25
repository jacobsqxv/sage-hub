package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.constant.Patterns;
import dev.aries.sagehub.constant.ValidationMessage;
import jakarta.validation.constraints.Pattern;

public record AdminRequest(
		@Pattern(regexp = Patterns.NAME, message = ValidationMessage.NAME)
		String firstname,
		@Pattern(regexp = Patterns.NAME, message = ValidationMessage.NAME)
		String middleName,
		@Pattern(regexp = Patterns.NAME, message = ValidationMessage.NAME)
		String lastname,
		@Pattern(regexp = Patterns.EMAIL, message = ValidationMessage.EMAIL)
		String primaryEmail,
		String profilePicture
) {
}
