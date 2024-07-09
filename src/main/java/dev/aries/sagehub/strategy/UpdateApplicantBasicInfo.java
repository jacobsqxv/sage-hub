package dev.aries.sagehub.strategy;

import dev.aries.sagehub.dto.request.ApplicationRequest;
import dev.aries.sagehub.enums.Gender;
import dev.aries.sagehub.enums.MaritalStatus;
import dev.aries.sagehub.enums.Title;
import dev.aries.sagehub.model.Application;

import org.springframework.stereotype.Component;

@Component
public class UpdateApplicantBasicInfo implements UpdateStrategy<Application, ApplicationRequest> {

	@Override
	public Application update(Application entity, ApplicationRequest request) {
		entity.setProfilePictureUrl((request.profilePicture() != null) ?
				request.profilePicture() : entity.getProfilePictureUrl());
		entity.setTitle((request.title() != null) ?
				Title.valueOf(request.title()) : entity.getTitle());
		entity.setFirstName((request.firstname() != null) ?
				request.firstname() : entity.getFirstName());
		entity.setLastName((request.lastname() != null) ?
				request.lastname() : entity.getLastName());
		entity.setMiddleName((request.middleName() != null) ?
				request.middleName() : entity.getMiddleName());
		entity.setMaritalStatus((request.maritalStatus() != null) ?
				MaritalStatus.valueOf(request.maritalStatus()) : entity.getMaritalStatus());
		entity.setGender((request.gender() != null) ?
				Gender.valueOf(request.gender()) : entity.getGender());
		entity.setDateOfBirth((request.dateOfBirth() != null) ?
				request.dateOfBirth() : entity.getDateOfBirth());
		return entity;
	}
}
