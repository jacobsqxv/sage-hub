package dev.aries.sagehub.dto.request;

import java.time.Duration;
import java.time.LocalDate;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.constant.Patterns;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import static dev.aries.sagehub.constant.ValidationMessage.INVALID_FORMAT;
import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record EducationRequest(
		@NotEmpty(message = "Institution" + NOT_NULL)
		@Pattern(regexp = Patterns.NAME, message = INVALID_FORMAT + "institution")
		@Schema(example = "Aries International School")
		String institution,
		@NotEmpty(message = "Program pursued" + NOT_NULL)
		@Pattern(regexp = Patterns.NAME, message = INVALID_FORMAT + "program pursued")
		@Schema(example = "General Science")
		String programPursued,
		@NotNull(message = "Start date" + NOT_NULL)
		@Schema(example = "2020-01-01")
		LocalDate startDate,
		@NotNull(message = "End date" + NOT_NULL)
		@Schema(example = "2024-01-01")
		LocalDate endDate
) {
	public EducationRequest {
		Long years = Duration.between(startDate.atStartOfDay(), endDate.atStartOfDay()).toDays() / 365;
		Long minimumYears = 2L;
		if (years < minimumYears) {
			throw new IllegalArgumentException(ExceptionConstants.INVALID_PERIOD);
		}
	}
}
