package dev.aries.sagehub.model.attribute;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;

@Embeddable
@Builder
public record Education(
		@Column(nullable = false)
		String institution,
		@Column(nullable = false)
		String programPursued,
		@Column(nullable = false)
		LocalDate startDate,
		@Column(nullable = false)
		LocalDate endDate
) {
}
