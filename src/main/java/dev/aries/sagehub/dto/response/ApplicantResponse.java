package dev.aries.sagehub.dto.response;

import java.util.List;

import lombok.Builder;

@Builder
public record ApplicantResponse(
		Long userId,
		Long applicantId,
		Integer yearOfApplication,
		BasicInfoResponse basicInfo,
		ContactInfoResponse contactInfo,
		EmergencyContactResponse guardianInfo,
		List<ApplicantResultsResponse> results,
		List<ProgramResponse> programs,
		String status,
		boolean isSubmitted
) {
}
