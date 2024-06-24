package dev.aries.sagehub.dto.response;

import lombok.Builder;

@Builder
public record ContactInfoResponse(
		Long id,
		String secondaryEmail,
		String phoneNumber,
		String address,
		String city,
		String region
) {
}
