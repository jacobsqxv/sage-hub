package dev.aries.sagehub.dto.response;

import dev.aries.sagehub.enums.ApplicationStatus;
import dev.aries.sagehub.model.attribute.Education;

public record ApplicationResponse(
		Long applicationId,
		Long applicantId,
		Integer yearOfApplication,
		UserProfileResponse applicantProfile,
		EmergencyInfoResponse guardianInfo,
		Education educationBackground,
		ApplicationStatus status,
		boolean isSubmitted
) {
}
