package dev.aries.sagehub.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Past;

public record AddUserRequest(
		String firstname,
		String middleName,
		String lastname,
		String profilePicture,
		String secondaryEmail,
		String gender,
		@Past
		LocalDate dateOfBirth
) {
}
