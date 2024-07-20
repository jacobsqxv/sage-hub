package dev.aries.sagehub.strategy.response;

import dev.aries.sagehub.dto.response.BasicUserResponse;

public interface UserResponseStrategy {
	BasicUserResponse getUserResponse(Object user);
}
