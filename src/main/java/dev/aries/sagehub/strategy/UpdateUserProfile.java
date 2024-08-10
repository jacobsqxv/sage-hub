package dev.aries.sagehub.strategy;

import dev.aries.sagehub.dto.request.UserProfileRequest;
import dev.aries.sagehub.model.UserProfile;

import org.springframework.stereotype.Component;

@Component
public class UpdateUserProfile implements UpdateStrategy<UserProfile, UserProfileRequest> {

	@Override
	public UserProfile update(UserProfile entity, UserProfileRequest request) {
		entity.setPersonalInfo(UpdateAttributes.updatePersonalInfo(
				entity.getPersonalInfo(), request.personalInfo()));
		entity.setContactInfo(UpdateAttributes.updateContactInfo(
				entity.getContactInfo(), request.contactInfo()));
		return entity;
	}
}
