package dev.aries.sagehub.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record SubjectScoreRequest(
		@NotEmpty(message = "Subject" + NOT_NULL)
		@Schema(example = "Mathematics")
		String subject,
		@NotNull(message = "Grade" + NOT_NULL)
		@Pattern(regexp = "[A-F]", message = "Grade must be between A and F")
		@Schema(example = "A")
		Character grade
) {
}
