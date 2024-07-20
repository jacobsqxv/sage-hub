package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.constant.Patterns;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import org.springframework.validation.annotation.Validated;

import static dev.aries.sagehub.constant.ValidationMessage.INVALID_FORMAT;
import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

@Validated
public record EmergencyContactRequest(
		@Pattern(regexp = Patterns.NAME, message = INVALID_FORMAT + "full name")
		@NotEmpty(message = "Full name" + NOT_NULL)
		String fullName,
		@NotNull(message = "Relationship" + NOT_NULL)
		String relationship,
		@Pattern(regexp = Patterns.PHONE_NUMBER, message = INVALID_FORMAT + "phone number")
		@NotEmpty(message = "Phone number" + NOT_NULL)
		String phoneNumber,
		@NotEmpty(message = "Email" + NOT_NULL)
		@Pattern(regexp = Patterns.EMAIL, message = INVALID_FORMAT + "email")
		String email,
		@NotNull(message = "Occupation" + NOT_NULL)
		String occupation,
		@Valid
		AddressUpdateRequest address
) {
}
