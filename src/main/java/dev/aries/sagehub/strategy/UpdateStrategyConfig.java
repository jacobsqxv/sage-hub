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

	@Bean
	public Map<String, UpdateStrategy> updateStrategies() {
		Map<String, UpdateStrategy> strategies = new HashMap<>();
		strategies.put("EmergencyContact", updateEmergencyContact);
		strategies.put("ContactInfo", updateContactInfo);
		strategies.put("Department", updateDepartment);
		return strategies;
	}
}
