package dev.aries.sagehub.strategy.response;

import dev.aries.sagehub.dto.response.BasicUserResponse;
import dev.aries.sagehub.model.Student;
import dev.aries.sagehub.util.UserUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentUserResponse implements UserResponseStrategy {
	private final ApplicationContext context;

	@Override
	public BasicUserResponse getUserResponse(Object user) {
		UserUtil userUtil = this.context.getBean(UserUtil.class);
		return userUtil.getUserResponse((Student) user);
	}
}
