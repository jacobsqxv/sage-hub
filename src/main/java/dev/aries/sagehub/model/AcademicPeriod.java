package dev.aries.sagehub.model;

import dev.aries.sagehub.enums.Semester;
import dev.aries.sagehub.enums.Year;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public record AcademicPeriod(
		@Column(nullable = false)
		Year year,
		@Column(nullable = false)
		@Enumerated(EnumType.STRING)
		Semester semester
) {
}
