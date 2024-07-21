package dev.aries.sagehub.strategy;

import java.util.Optional;

import dev.aries.sagehub.dto.request.BasicInfoRequest;
import dev.aries.sagehub.enums.Gender;
import dev.aries.sagehub.enums.MaritalStatus;
import dev.aries.sagehub.enums.Title;
import dev.aries.sagehub.model.BasicInfo;

import org.springframework.stereotype.Component;

@Component
public class UpdateBasicInfo implements UpdateStrategy<BasicInfo, BasicInfoRequest> {

	@Override
	public BasicInfo update(BasicInfo entity, BasicInfoRequest request) {
		Optional.ofNullable(request.profilePicture()).ifPresent(entity::setProfilePictureUrl);
		Optional.ofNullable(request.title()).map(Title::valueOf).ifPresent(entity::setTitle);
		Optional.ofNullable(request.firstname()).ifPresent(entity::setFirstName);
		Optional.ofNullable(request.lastname()).ifPresent(entity::setLastName);
		Optional.ofNullable(request.middleName()).ifPresent(entity::setMiddleName);
		Optional.ofNullable(request.maritalStatus()).map(MaritalStatus::valueOf)
				.ifPresent(entity::setMaritalStatus);
		Optional.ofNullable(request.gender()).map(Gender::valueOf).ifPresent(entity::setGender);
		Optional.ofNullable(request.dateOfBirth()).ifPresent(entity::setDateOfBirth);
		return entity;
	}
}
