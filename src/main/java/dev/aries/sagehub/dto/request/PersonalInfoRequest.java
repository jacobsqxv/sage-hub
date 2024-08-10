package dev.aries.sagehub.dto.request;

import java.time.LocalDate;

import dev.aries.sagehub.enums.Gender;
import dev.aries.sagehub.enums.MaritalStatus;
import dev.aries.sagehub.enums.Title;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import static dev.aries.sagehub.constant.ValidationMessage.DATE_OF_BIRTH;
import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record PersonalInfoRequest(
		@Schema(example = "https://example.com/profile.jpg")
		String profilePicture,
		@NotNull(message = "Title" + NOT_NULL)
		@Schema(example = "MR")
		Title title,
		@Valid
		@Schema(implementation = NameRequest.class)
		NameRequest name,
		@Past(message = DATE_OF_BIRTH)
		@NotNull(message = "Date of birth" + NOT_NULL)
		@Schema(example = "1990-01-01")
		LocalDate dateOfBirth,
		@NotNull(message = "Gender" + NOT_NULL)
		@Schema(example = "MALE")
		Gender gender,
		@NotNull(message = "Marital status" + NOT_NULL)
		@Schema(example = "SINGLE")
		MaritalStatus maritalStatus
) {
}
