package dev.aries.sagehub.dto.response;

import dev.aries.sagehub.enums.AccountStatus;
import dev.aries.sagehub.enums.Gender;
import dev.aries.sagehub.enums.RoleEnum;
import lombok.Builder;

@Builder
public record BasicUserResponse(
		Long userId,
		Long memberId,
		String profilePicture,
		String fullName,
		String email,
		Gender gender,
		RoleEnum role,
		AccountStatus status
) {
}
