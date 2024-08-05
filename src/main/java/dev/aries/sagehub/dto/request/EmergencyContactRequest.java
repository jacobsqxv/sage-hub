package dev.aries.sagehub.dto.request;

import dev.aries.sagehub.constant.Patterns;
import dev.aries.sagehub.model.attribute.Email;
import dev.aries.sagehub.model.attribute.PhoneNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import static dev.aries.sagehub.constant.ValidationMessage.INVALID_FORMAT;
import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record EmergencyContactRequest(
		@Pattern(regexp = Patterns.NAME, message = INVALID_FORMAT + "full name")
		@NotEmpty(message = "Full name" + NOT_NULL)
				@Schema(example = "Jane Doe")
		String fullName,
		@NotEmpty(message = "Relationship" + NOT_NULL)
		@Schema(example = "Mother")
		String relationship,
		@NotNull(message = "Phone number" + NOT_NULL)
		@Schema(example = "{\"countryCode\":\"GH\",\"number\":\"500000000\"}")
		PhoneNumber phoneNumber,
		@NotNull(message = "Email" + NOT_NULL)
		@Schema(example = "jane.doe@example.com")
		Email email,
		@NotEmpty(message = "Occupation" + NOT_NULL)
		@Schema(example = "Teacher")
		String occupation,
		@Valid
		@Schema(implementation = AddressUpdateRequest.class)
		AddressUpdateRequest address
) {
}
