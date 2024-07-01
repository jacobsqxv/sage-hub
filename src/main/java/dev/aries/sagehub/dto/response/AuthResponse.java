package dev.aries.sagehub.dto.response;

import java.time.LocalDateTime;

public record AuthResponse(
		String token,
		BasicUserResponse user,
		LocalDateTime lastLogin,
		String status,
		boolean isAccountEnabled
) {
}
