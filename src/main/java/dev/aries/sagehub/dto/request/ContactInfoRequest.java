package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.constant.Patterns;
import dev.aries.sagehub.constant.ValidationMessage;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import org.springframework.validation.annotation.Validated;

@Validated
public record ContactInfoRequest(
		@NotEmpty(message = ValidationMessage.NOT_NULL)
		@Pattern(regexp = Patterns.EMAIL, message = ValidationMessage.EMAIL)
		String secondaryEmail,
		@NotEmpty(message = ValidationMessage.NOT_NULL)
		@Pattern(regexp = Patterns.PHONE_NUMBER, message = ValidationMessage.PHONE_NUMBER)
		String phoneNumber,
		@Valid
		AddressUpdateRequest address,
		@Pattern(regexp = Patterns.POSTAL_ADDRESS, message = ValidationMessage.POSTAL_ADDRESS)
		String postalAddress
) {
}
