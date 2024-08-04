package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.constant.Patterns;
import dev.aries.sagehub.model.attribute.Email;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import static dev.aries.sagehub.constant.ValidationMessage.INVALID_FORMAT;
import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record AdminRequest(
		@Pattern(regexp = Patterns.NAME, message = INVALID_FORMAT + "first name")
		@NotEmpty(message = "First name" + NOT_NULL)
		@Schema(example = "John")
		String firstname,
		@Pattern(regexp = Patterns.NAME, message = INVALID_FORMAT + "middle name")
		@Schema(example = "Mike")
		String middleName,
		@Pattern(regexp = Patterns.NAME, message = INVALID_FORMAT + "last name")
		@NotEmpty(message = "Last name" + NOT_NULL)
		@Schema(example = "Doe")
		String lastname,
		@NotNull(message = "Primary email" + NOT_NULL)
		@Schema(example = "johndoe@example.com")
		Email primaryEmail,
		@NotEmpty(message = "Profile picture" + NOT_NULL)
		@Schema(example = "https://example.com/johndoe.jpg")
		String profilePicture
) {
}
