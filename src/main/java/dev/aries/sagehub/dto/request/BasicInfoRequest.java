package dev.aries.sagehub.dto.request;

import java.time.LocalDate;

import dev.aries.sagehub.constant.Patterns;
import dev.aries.sagehub.enums.Gender;
import dev.aries.sagehub.enums.MaritalStatus;
import dev.aries.sagehub.enums.Title;
import io.swagger.v3.oas.annotations.media.Schema;
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
		@Schema(example = "John")
		String firstname,
		@Pattern(regexp = Patterns.NAME, message = INVALID_FORMAT + "middle name")
		@Schema(example = "Mike")
		String middleName,
		@Pattern(regexp = Patterns.NAME, message = INVALID_FORMAT + "last name")
		@NotEmpty(message = "Last name" + NOT_NULL)
		@Schema(example = "Doe")
		String lastname,
		@Schema(example = "https://example.com/profile.jpg")
		String profilePicture,
		@NotNull(message = "Title" + NOT_NULL)
		@Schema(example = "MR")
		Title title,
		@NotNull(message = "Marital status" + NOT_NULL)
		@Schema(example = "SINGLE")
		MaritalStatus maritalStatus,
		@NotNull(message = "Gender" + NOT_NULL)
		@Schema(example = "MALE")
		Gender gender,
		@Past(message = DATE_OF_BIRTH)
		@NotNull(message = "Date of birth" + NOT_NULL)
		@Schema(example = "1990-01-01")
		LocalDate dateOfBirth
) {
}
