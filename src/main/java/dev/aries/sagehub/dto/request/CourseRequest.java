package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.constant.Patterns;
import dev.aries.sagehub.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import static dev.aries.sagehub.constant.ValidationMessage.GREATER_THAN;
import static dev.aries.sagehub.constant.ValidationMessage.INVALID_FORMAT;
import static dev.aries.sagehub.constant.ValidationMessage.LESS_THAN;
import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record CourseRequest(
		@Pattern(regexp = Patterns.NAME, message = INVALID_FORMAT + "course name")
		@NotEmpty(message = "Course name" + NOT_NULL)
		@Schema(example = "Introduction to Computer Science")
		String name,
		@Schema(example = "An introductory course to computer science")
		String description,
		@NotNull(message = "Course credits" + NOT_NULL)
		@Max(value = 10, message = "Course credits" + LESS_THAN + "10")
		@Min(value = 1, message = "Course credits" + GREATER_THAN + "1")
		@Schema(example = "3")
		Integer creditUnits,
		@Schema(example = "ACTIVE")
		Status status
) {
}
