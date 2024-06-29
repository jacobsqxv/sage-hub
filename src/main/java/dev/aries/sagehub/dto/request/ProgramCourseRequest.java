package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.constant.ValidationMessage;
import dev.aries.sagehub.model.AcademicPeriod;
import jakarta.validation.constraints.NotNull;

public record ProgramCourseRequest(
		@NotNull(message = ValidationMessage.NOT_NULL)
		Long courseId,
		AcademicPeriod period,
		String status
) {
}
