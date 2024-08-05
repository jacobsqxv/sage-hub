package dev.aries.sagehub.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record ApplicantRequest(
		@NotNull(message = "Academic year" + NOT_NULL)
		@Schema(example = "2024")
		Integer yearOfApplication,
		@NotNull(message = "Basic info" + NOT_NULL)
		@Valid
		@Schema(implementation = BasicInfoRequest.class)
		BasicInfoRequest basicInfo,
		@NotNull(message =  "Contact info" + NOT_NULL)
		@Valid
		@Schema(implementation = ContactInfoRequest.class)
		ContactInfoRequest contactInfo,
		@NotNull(message = "Emergency contact" + NOT_NULL)
		@Valid
		@Schema(implementation = EmergencyContactRequest.class)
		EmergencyContactRequest guardianInfo
) {
}
