package dev.aries.sagehub.strategy.response;

import java.util.HashMap;
import java.util.Map;

import dev.aries.sagehub.model.Admin;
import dev.aries.sagehub.model.Applicant;
import dev.aries.sagehub.model.Staff;
import dev.aries.sagehub.model.Student;
import lombok.RequiredArgsConstructor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UserResponseStrategyConfig {
	private final ApplicationContext context;

	@Bean
	public Map<Class<?>, UserResponseStrategy> responseStrategies() {
		Map<Class<?>, UserResponseStrategy> strategies = new HashMap<>();
		strategies.put(Admin.class, new AdminUserResponse(this.context));
		strategies.put(Staff.class, new StaffUserResponse(this.context));
		strategies.put(Student.class, new StudentUserResponse(this.context));
		strategies.put(Applicant.class, new ApplicantUserResponse(this.context));
		return strategies;
	}
}
