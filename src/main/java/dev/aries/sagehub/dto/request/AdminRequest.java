package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.model.attribute.Email;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record AdminRequest(
		@Valid
		@Schema(implementation = NameRequest.class)
		NameRequest name,
		@NotNull(message = "Primary email" + NOT_NULL)
		@Schema(example = "johndoe@example.com")
		Email primaryEmail,
		@NotEmpty(message = "Profile picture" + NOT_NULL)
		@Schema(example = "https://example.com/johndoe.jpg")
		String profilePicture
) {
}
