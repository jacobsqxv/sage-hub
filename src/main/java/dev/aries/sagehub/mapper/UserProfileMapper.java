package dev.aries.sagehub.mapper;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.request.AddressRequest;
import dev.aries.sagehub.dto.request.ContactInfoRequest;
import dev.aries.sagehub.dto.request.EducationRequest;
import dev.aries.sagehub.dto.request.NameRequest;
import dev.aries.sagehub.dto.request.PersonalInfoRequest;
import dev.aries.sagehub.dto.response.UserProfileResponse;
import dev.aries.sagehub.model.UserProfile;
import dev.aries.sagehub.model.attribute.Address;
import dev.aries.sagehub.model.attribute.ContactInfo;
import dev.aries.sagehub.model.attribute.Education;
import dev.aries.sagehub.model.attribute.Name;
import dev.aries.sagehub.model.attribute.PersonalInfo;

public final class UserProfileMapper {

	public static UserProfileResponse toUserProfileResponse(UserProfile userProfile) {
		return new UserProfileResponse(
				userProfile.getPersonalInfo(),
				userProfile.getContactInfo()
		);
	}

	public static PersonalInfo toPersonalInfo(PersonalInfoRequest request) {
		return new PersonalInfo(
				request.profilePicture(),
				request.title(),
				toName(request.name()),
				request.dateOfBirth(),
				request.gender(),
				request.maritalStatus()
		);
	}

	public static ContactInfo toContactInfo(ContactInfoRequest request) {
		return new ContactInfo(
				request.secondaryEmail().value(),
				request.phoneNumber().number(),
				toAddress(request.residentialAddress()),
				request.postalAddress()
		);
	}

	public static Name toName(NameRequest request) {
		return new Name(
				request.firstName(),
				request.middleName(),
				request.lastName()
		);
	}

	public static Address toAddress(AddressRequest request) {
		return new Address(
				request.street(),
				request.city(),
				request.region(),
				request.country()
		);
	}

	public static Education toEducation(EducationRequest request) {
		return new Education(
				request.institution(),
				request.programPursued(),
				request.startDate(),
				request.endDate()
		);
	}

	private UserProfileMapper() {
		throw new IllegalStateException(ExceptionConstants.UTILITY_CLASS);
	}
}
