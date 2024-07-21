package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.constant.Patterns;
import dev.aries.sagehub.model.attribute.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import static dev.aries.sagehub.constant.ValidationMessage.INVALID_FORMAT;
import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record AdminRequest(
		@Pattern(regexp = Patterns.NAME, message = INVALID_FORMAT + "first name")
		@NotEmpty(message = "First name" + NOT_NULL)
		String firstname,
		@Pattern(regexp = Patterns.NAME, message = INVALID_FORMAT + "middle name")
		String middleName,
		@Pattern(regexp = Patterns.NAME, message = INVALID_FORMAT + "last name")
		@NotEmpty(message = "Last name" + NOT_NULL)
		String lastname,
		@NotNull(message = "Primary email" + NOT_NULL)
		Email primaryEmail,
		@NotEmpty(message = "Profile picture" + NOT_NULL)
		String profilePicture
) {
}
