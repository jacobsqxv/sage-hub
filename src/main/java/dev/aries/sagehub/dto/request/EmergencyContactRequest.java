package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.constant.Patterns;
import dev.aries.sagehub.model.attribute.Email;
import dev.aries.sagehub.model.attribute.PhoneNumber;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import static dev.aries.sagehub.constant.ValidationMessage.INVALID_FORMAT;
import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record EmergencyContactRequest(
		@Pattern(regexp = Patterns.NAME, message = INVALID_FORMAT + "full name")
		@NotEmpty(message = "Full name" + NOT_NULL)
		String fullName,
		@NotEmpty(message = "Relationship" + NOT_NULL)
		String relationship,
		@NotNull(message = "Phone number" + NOT_NULL)
		PhoneNumber phoneNumber,
		@NotNull(message = "Email" + NOT_NULL)
		Email email,
		@NotEmpty(message = "Occupation" + NOT_NULL)
		String occupation,
		@Valid
		AddressUpdateRequest address
) {
}
