package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.constant.Patterns;
import dev.aries.sagehub.constant.ValidationMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import static dev.aries.sagehub.constant.ValidationMessage.INVALID_FORMAT;

public record RefreshTokenRequest(
		@NotEmpty(message = "Token" + ValidationMessage.NOT_NULL)
		@Pattern(regexp = Patterns.JWT_PATTERN, message = INVALID_FORMAT + "token")
		@Schema(description = "JWT token")
		String token
) {
}
