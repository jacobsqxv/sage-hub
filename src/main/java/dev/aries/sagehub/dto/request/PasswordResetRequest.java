package dev.aries.sagehub.dto.request;

public record PasswordResetRequest(
		String username,
		String password,
		String token
) {
}
