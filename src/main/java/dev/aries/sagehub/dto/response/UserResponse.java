package dev.aries.sagehub.dto.response;

public record UserResponse(
		BasicUserResponse userInfo,
		ContactInfoResponse contactInfo,
		EmergencyContactResponse emergencyContact
) {
}
