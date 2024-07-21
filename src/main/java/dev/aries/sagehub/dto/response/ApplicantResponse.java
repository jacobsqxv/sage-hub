package dev.aries.sagehub.dto.response;

import java.util.List;

import lombok.Builder;

@Builder
public record ApplicantResponse(
		Long id,
		Long applicantId,
		Integer applyingForYear,
		BasicInfoResponse basicInfo,
		ContactInfoResponse contactInfo,
		EmergencyContactResponse guardianInfo,
		List<ApplicantResultsResponse> results,
		List<ProgramResponse> programs,
		String status,
		boolean isSubmitted
) {
}
