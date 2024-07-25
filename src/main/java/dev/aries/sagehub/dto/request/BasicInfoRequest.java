package dev.aries.sagehub.dto.request;

import java.time.LocalDate;

import dev.aries.sagehub.constant.Patterns;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

import static dev.aries.sagehub.constant.ValidationMessage.DATE_OF_BIRTH;
import static dev.aries.sagehub.constant.ValidationMessage.INVALID_FORMAT;
import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record BasicInfoRequest(
		@Pattern(regexp = Patterns.NAME, message = INVALID_FORMAT + "first name")
		@NotEmpty(message = "First name" + NOT_NULL)
		String firstname,
		@Pattern(regexp = Patterns.NAME, message = INVALID_FORMAT + "middle name")
		String middleName,
		@Pattern(regexp = Patterns.NAME, message = INVALID_FORMAT + "last name")
		@NotEmpty(message = "Last name" + NOT_NULL)
		String lastname,
		String profilePicture,
		@NotEmpty(message = "Title" + NOT_NULL)
		String title,
		@NotEmpty(message = "Marital status" + NOT_NULL)
		String maritalStatus,
		@NotEmpty(message = "Gender" + NOT_NULL)
		String gender,
		@Past(message = DATE_OF_BIRTH)
		@NotNull(message = "Date of birth" + NOT_NULL)
		LocalDate dateOfBirth
) {
}
