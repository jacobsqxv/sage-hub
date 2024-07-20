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
	private final UpdateApplicantBasicInfo updateApplicantBasicInfo;

	@Bean
	public Map<String, UpdateStrategy> updateStrategies() {
		Map<String, UpdateStrategy> strategies = new HashMap<>();
		strategies.put("EmergencyContact", this.updateEmergencyContact);
		strategies.put("ContactInfo", this.updateContactInfo);
		strategies.put("Department", this.updateDepartment);
		strategies.put("Program", this.updateProgram);
		strategies.put("Course", this.updateCourse);
		strategies.put("ProgramCourse", this.updateProgramCourse);
		strategies.put("ApplicantBasicInfo", this.updateApplicantBasicInfo);
		return strategies;
	}
}
