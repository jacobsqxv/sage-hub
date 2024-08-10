package dev.aries.sagehub.strategy;

import dev.aries.sagehub.dto.request.UserProfileRequest;
import dev.aries.sagehub.model.UserProfile;

import org.springframework.stereotype.Component;

@Component
public class UpdateUserInfo implements UpdateStrategy<UserProfile, UserProfileRequest> {

	@Override
	public UserProfile update(UserProfile entity, UserProfileRequest request) {
		entity.setPersonalInfo(UpdatePersonalInfo.update(entity.getPersonalInfo(), request.personalInfo()));
		entity.setContactInfo(UpdateContactInfo.update(entity.getContactInfo(), request.contactInfo()));
		return entity;
	}
}
