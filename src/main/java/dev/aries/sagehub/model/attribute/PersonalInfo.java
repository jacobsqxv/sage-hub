package dev.aries.sagehub.model.attribute;

import java.time.LocalDate;

import dev.aries.sagehub.enums.Gender;
import dev.aries.sagehub.enums.MaritalStatus;
import dev.aries.sagehub.enums.Title;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public record PersonalInfo(
		@Column(nullable = false)
		String profilePicture,
		@Column(nullable = false)
		@Enumerated(EnumType.STRING)
		Title title,
		@Embedded
		@Column(nullable = false)
		Name name,
		@Column(nullable = false)
		LocalDate dateOfBirth,
		@Column(nullable = false)
		@Enumerated(EnumType.STRING)
		Gender gender,
		@Column(nullable = false)
		@Enumerated(EnumType.STRING)
		MaritalStatus maritalStatus
) {
}
