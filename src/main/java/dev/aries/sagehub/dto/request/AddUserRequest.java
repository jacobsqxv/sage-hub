package dev.aries.sagehub.dto.request;

import java.time.LocalDate;

import dev.aries.sagehub.constant.Patterns;
import dev.aries.sagehub.constant.ValidationMessage;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

import org.springframework.validation.annotation.Validated;

@Validated
public record AddUserRequest(
		@Pattern(regexp = Patterns.NAME, message = ValidationMessage.NAME)
		String firstname,
		@Pattern(regexp = Patterns.NAME, message = ValidationMessage.NAME)
		String middleName,
		@Pattern(regexp = Patterns.NAME, message = ValidationMessage.NAME)
		String lastname,
		String profilePicture,
		@Valid
		ContactInfoRequest contactInfo,
		String gender,
		@Past(message = ValidationMessage.DATE_OF_BIRTH)
		LocalDate dateOfBirth
) {
}
