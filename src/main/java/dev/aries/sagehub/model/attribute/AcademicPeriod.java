package dev.aries.sagehub.model;

import dev.aries.sagehub.enums.Semester;
import dev.aries.sagehub.enums.Year;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public record AcademicPeriod(
		@Column(nullable = false)
		@Enumerated(EnumType.STRING)
		@Schema(example = "FIRST")
		Year year,
		@Column(nullable = false)
		@Enumerated(EnumType.STRING)
		@Schema(example = "FIRST")
		Semester semester
) {
}
