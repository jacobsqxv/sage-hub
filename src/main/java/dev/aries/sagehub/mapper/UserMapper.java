package dev.aries.sagehub.mapper;

import dev.aries.sagehub.dto.response.AuthUserResponse;
import dev.aries.sagehub.model.User;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {
	public AuthUserResponse toAuthUserResponse(User user) {
		return new AuthUserResponse(
				user.getUsername(),
				user.getRole().getName().toString(),
				user.getStatus().toString(),
				user.getLastLogin(),
				user.isAccountEnabled()
		);
	}
}
