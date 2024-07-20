package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.model.AcademicPeriod;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record ProgramCourseRequest(
		@NotNull(message = "Course ID" + NOT_NULL)
		Long courseId,
		@NotNull(message = "Academic period" + NOT_NULL)
		AcademicPeriod period,
		String status
) {
}
