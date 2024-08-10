package dev.aries.sagehub.dto.response;

import dev.aries.sagehub.model.attribute.Education;

public record ApplicantInfoResponse(
		Long applicationId,
		Long applicantId,
		Integer yearOfApplication,
		UserProfileResponse applicantProfile,
		EmergencyInfoResponse guardianInfo,
		Education educationBackground
) {
}
