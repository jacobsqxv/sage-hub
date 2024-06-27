package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.constant.Patterns;
import dev.aries.sagehub.constant.ValidationMessage;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CourseRequest(
		@NotEmpty(message = ValidationMessage.NOT_NULL)
		@Pattern(regexp = Patterns.COURSE_CODE, message = ValidationMessage.CODE)
		String code,
		@NotEmpty(message = ValidationMessage.NOT_NULL)
		@Pattern(regexp = Patterns.NAME, message = ValidationMessage.NAME)
		String name,
		String description,
		@Positive(message = ValidationMessage.INVALID_NUMBER)
		Integer creditUnits
) {
}
