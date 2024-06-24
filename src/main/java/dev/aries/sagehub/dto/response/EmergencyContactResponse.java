package dev.aries.sagehub.dto.response;

public record EmergencyContactResponse(
		Long id,
		String fullName,
		String relationship,
		String phoneNumber,
		String address
) {
}
