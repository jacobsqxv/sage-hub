package dev.aries.sagehub.strategy;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.request.AddressRequest;
import dev.aries.sagehub.dto.request.ContactInfoRequest;
import dev.aries.sagehub.dto.request.EducationRequest;
import dev.aries.sagehub.dto.request.PersonalInfoRequest;
import dev.aries.sagehub.mapper.UserProfileMapper;
import dev.aries.sagehub.model.attribute.Address;
import dev.aries.sagehub.model.attribute.ContactInfo;
import dev.aries.sagehub.model.attribute.Education;
import dev.aries.sagehub.model.attribute.PersonalInfo;

public final class UpdateAttributes {

	public static Address updateAddress(Address address, AddressRequest request) {
		return new Address(
				(request.street() != null) ? request.street() : address.street(),
				(request.city() != null) ? request.city() : address.city(),
				(request.region() != null) ? request.region() : address.region(),
				(request.country() != null) ? request.country() : address.country()
		);
	}

	public static ContactInfo updateContactInfo(ContactInfo entity, ContactInfoRequest req) {
		return new ContactInfo(
				(req.secondaryEmail() != null) ?
						req.secondaryEmail().value() : entity.secondaryEmail(),
				(req.phoneNumber() != null) ?
						req.phoneNumber().number() : entity.phoneNumber(),
				(req.residentialAddress() != null) ?
						updateAddress(
								entity.residentialAddress(), req.residentialAddress())
						: entity.residentialAddress(),
				(req.postalAddress() != null) ?
						req.postalAddress() : entity.postalAddress()
		);
	}

	public static PersonalInfo updatePersonalInfo(PersonalInfo entity, PersonalInfoRequest req) {
		return new PersonalInfo(
				(req.profilePicture() != null) ?
						req.profilePicture() : entity.profilePicture(),
				(req.title() != null) ? req.title() : entity.title(),
				(req.name() != null) ?
						UserProfileMapper.toName(req.name()) : entity.name(),
				(req.dateOfBirth() != null) ? req.dateOfBirth() : entity.dateOfBirth(),
				(req.gender() != null) ? req.gender() : entity.gender(),
				(req.maritalStatus() != null) ? req.maritalStatus() : entity.maritalStatus()
		);
	}

	public static Education updateEducation(Education entity, EducationRequest request) {
		return new Education(
				((request.institution()) != null) ? request.institution() : entity.institution(),
				((request.programPursued()) != null)
						? request.programPursued() : entity.programPursued(),
				((request.startDate()) != null) ? request.startDate() : entity.startDate(),
				((request.endDate()) != null) ? request.endDate() : entity.endDate()
		);
	}
	private UpdateAttributes() {
		throw new IllegalArgumentException(ExceptionConstants.UTILITY_CLASS);
	}
}
