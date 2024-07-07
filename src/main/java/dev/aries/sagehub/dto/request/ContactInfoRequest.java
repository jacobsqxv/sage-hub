package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.constant.Patterns;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import org.springframework.validation.annotation.Validated;

import static dev.aries.sagehub.constant.ValidationMessage.INVALID_FORMAT;
import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

@Validated
public record ContactInfoRequest(
		@NotEmpty(message = "Email" + NOT_NULL)
		@Pattern(regexp = Patterns.EMAIL, message = INVALID_FORMAT + "email")
		String secondaryEmail,
		@NotEmpty(message = "Phone number" + NOT_NULL)
		@Pattern(regexp = Patterns.PHONE_NUMBER, message = INVALID_FORMAT + "phone number")
		String phoneNumber,
		@Valid
		AddressUpdateRequest address,
		@Pattern(regexp = Patterns.POSTAL_ADDRESS, message = INVALID_FORMAT + "postal address")
		String postalAddress
) {
}
