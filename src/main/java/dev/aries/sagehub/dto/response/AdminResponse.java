package dev.aries.sagehub.dto.response;

import dev.aries.sagehub.enums.AccountStatus;

public record AdminResponse(
		Long id,
		String profilePicture,
		String fullName,
		String username,
		String primaryEmail,
		String role,
		AccountStatus status
) {
}
