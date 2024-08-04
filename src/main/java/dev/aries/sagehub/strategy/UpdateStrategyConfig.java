package dev.aries.sagehub.strategy;

import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UpdateStrategyConfig {

	private final UpdateEmergencyContact updateEmergencyContact;
	private final UpdateContactInfo updateContactInfo;
	private final UpdateDepartment updateDepartment;
	private final UpdateProgram updateProgram;
	private final UpdateCourse updateCourse;
	private final UpdateProgramCourse updateProgramCourse;
	private final UpdateBasicInfo updateBasicInfo;

	@Bean
	public Map<String, UpdateStrategy> updateStrategies() {
		Map<String, UpdateStrategy> strategies = new HashMap<>();
		strategies.put("BasicInfo", updateBasicInfo);
		strategies.put("ContactInfo", updateContactInfo);
		strategies.put("EmergencyContact", updateEmergencyContact);
		strategies.put("Department", updateDepartment);
		strategies.put("Program", updateProgram);
		strategies.put("Course", updateCourse);
		strategies.put("ProgramCourse", updateProgramCourse);
		return strategies;
	}
}
