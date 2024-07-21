package dev.aries.sagehub.dto.response;

import java.time.LocalDate;

import lombok.Builder;

@Builder
public record BasicInfoResponse(
		Long id,
		String profilePictureUrl,
		String title,
		String fullName,
		String gender,
		String maritalStatus,
		LocalDate dateOfBirth
) {
}
