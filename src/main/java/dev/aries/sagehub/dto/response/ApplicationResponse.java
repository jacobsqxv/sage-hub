package dev.aries.sagehub.dto.response;

import dev.aries.sagehub.enums.ApplicantStatus;

public record ApplicantResponse(
		Long userId,
		Long applicantId,
		Integer yearOfApplication,
		UserInfoResponse basicInfo,
		ContactInfoResponse contactInfo,
		EmergencyContactResponse guardianInfo,
		ApplicantStatus status,
		boolean isSubmitted
) {
}
