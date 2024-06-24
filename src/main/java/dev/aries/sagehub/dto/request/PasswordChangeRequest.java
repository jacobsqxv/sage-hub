package dev.aries.sagehub.dto.request;

public record PasswordChangeRequest(
		String oldPassword,
		String newPassword
) {
}
