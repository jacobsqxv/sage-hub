package dev.aries.sagehub.dto.response;

import lombok.Builder;

@Builder
public record AuthToken(
		String accessToken,
		String refreshToken
) {
}
