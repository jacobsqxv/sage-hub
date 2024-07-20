package dev.aries.sagehub.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record SubjectScoreRequest(
		@NotEmpty(message = "Subject" + NOT_NULL)
		String subject,
		@NotNull(message = "Grade" + NOT_NULL)
		Character grade
) {
}
