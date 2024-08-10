package dev.aries.sagehub.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record ApplicantInfoRequest(
		@NotNull(message = "Applicant information" + NOT_NULL)
		@Valid
		@Schema(implementation = UserProfileRequest.class)
		UserProfileRequest applicantInfo,
		@NotNull(message = "Emergency contact" + NOT_NULL)
		@Valid
		@Schema(implementation = EmergencyInfoRequest.class)
		EmergencyInfoRequest guardianInfo,
		@NotNull(message = "Education background" + NOT_NULL)
		@Valid
		@Schema(implementation = EducationRequest.class)
		EducationRequest educationBackground
) {
}
