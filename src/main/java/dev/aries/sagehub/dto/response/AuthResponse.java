package dev.aries.sagehub.dto.response;

public record AuthResponse(
		String accessToken,
		String refreshToken,
		AuthUserResponse user
) {
}
