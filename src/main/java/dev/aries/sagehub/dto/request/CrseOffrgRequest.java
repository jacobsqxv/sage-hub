package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.enums.Status;
import dev.aries.sagehub.model.attribute.AcademicPeriod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record CrseOffrgRequest(
		@NotNull(message = "Course ID" + NOT_NULL)
		Long courseId,
		@NotNull(message = "Academic period" + NOT_NULL)
		@Schema(implementation = AcademicPeriod.class)
		AcademicPeriod period,
		@Schema(example = "ACTIVE")
		Status status
) {
}
