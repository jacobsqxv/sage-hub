package dev.aries.sagehub.strategy;

import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static dev.aries.sagehub.constant.ExceptionConstants.NO_UPDATE_STRATEGY;

@Configuration
@RequiredArgsConstructor
public class UpdateStrategyConfig {

	private final UpdateEmergencyInfo updateEmergencyInfo;
	private final UpdateDepartment updateDepartment;
	private final UpdateProgram updateProgram;
	private final UpdateCourse updateCourse;
	private final UpdateUserProfile updateUserProfile;
	private final UpdateExamResult updateExamResult;

	@Bean
	public Map<String, UpdateStrategy> updateStrategies() {
		Map<String, UpdateStrategy> strategies = new HashMap<>();
		strategies.put("UserProfile", updateUserProfile);
		strategies.put("EmergencyInfo", updateEmergencyInfo);
		strategies.put("Department", updateDepartment);
		strategies.put("Program", updateProgram);
		strategies.put("Course", updateCourse);
		strategies.put("ExamResult", updateExamResult);
		return strategies;
	}

	public UpdateStrategy checkStrategy(String type) {
		Map<String, UpdateStrategy> updateStrategies = updateStrategies();
		if (updateStrategies.get(type) == null) {
			throw new IllegalArgumentException(String.format(NO_UPDATE_STRATEGY, type));
		}
		return updateStrategies.get(type);
	}
}
