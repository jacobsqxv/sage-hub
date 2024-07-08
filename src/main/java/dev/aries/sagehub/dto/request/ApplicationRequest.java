package dev.aries.sagehub.dto.request;

import java.time.LocalDate;

import dev.aries.sagehub.constant.Patterns;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

import org.springframework.validation.annotation.Validated;

import static dev.aries.sagehub.constant.ValidationMessage.DATE_OF_BIRTH;
import static dev.aries.sagehub.constant.ValidationMessage.INVALID_FORMAT;
import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

@Validated
public record ApplicationRequest(
		Long serialNumber,
		@Pattern(regexp = Patterns.NAME, message = INVALID_FORMAT + "first name")
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
		LocalDate dateOfBirth,
		String status,
		boolean isSubmitted,
		Long applyingForYearId
) {
}
