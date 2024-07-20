package dev.aries.sagehub.strategy;

import java.util.Optional;

import dev.aries.sagehub.dto.request.ApplicationRequest;
import dev.aries.sagehub.enums.Gender;
import dev.aries.sagehub.enums.MaritalStatus;
import dev.aries.sagehub.enums.Title;
import dev.aries.sagehub.model.Applicant;

import org.springframework.stereotype.Component;

@Component
public class UpdateApplicantBasicInfo implements UpdateStrategy<Applicant, ApplicationRequest> {

	@Override
	public Applicant update(Applicant entity, ApplicationRequest request) {
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
