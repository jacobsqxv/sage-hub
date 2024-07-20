package dev.aries.sagehub.dto.response;

import java.time.LocalDateTime;

public record AuthUserResponse(
		String username,
		String role,
		String status,
		LocalDateTime lastLogin,
		boolean isAccountEnabled
) {
}
