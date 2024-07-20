package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.constant.Patterns;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import static dev.aries.sagehub.constant.ValidationMessage.INVALID_FORMAT;
import static dev.aries.sagehub.constant.ValidationMessage.INVALID_NUMBER;
import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record CourseRequest(
		@Pattern(regexp = Patterns.NAME, message = INVALID_FORMAT + "course name")
		@NotEmpty(message = "Course name" + NOT_NULL)
		String name,
		String description,
		@Positive(message = "Credit units" + INVALID_NUMBER)
		Integer creditUnits,
		String status
) {
}
