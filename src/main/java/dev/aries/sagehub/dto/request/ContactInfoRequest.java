package dev.aries.sagehub.dto.request;

public record ContactInfoRequest(
		String secondaryEmail,
		String phoneNumber,
		String address,
		String city,
		String region
) {
}
