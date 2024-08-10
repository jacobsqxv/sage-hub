package dev.aries.sagehub.mapper;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.response.ApplicantInfoResponse;
import dev.aries.sagehub.dto.response.ApplicationResponse;
import dev.aries.sagehub.dto.response.BasicApplicationResponse;
import dev.aries.sagehub.model.Application;

public final class ApplicationMapper {

	public static ApplicationResponse toApplicationResponse(Application application) {
		return new ApplicationResponse(
				application.getId(),
				application.getStudent().getId(),
				application.getYearOfApplication().getYear(),
				UserProfileMapper.toUserProfileResponse(application.getStudent().getUserProfile()),
				EmergencyInfoMapper.toResponse(
						application.getStudent().getEmergencyInfo()),
				application.getEducation(),
				application.getStatus(),
				application.isSubmitted()
		);
	}

	public static ApplicantInfoResponse toApplicantInfoResponse(Application application) {
		return new ApplicantInfoResponse(
				application.getId(),
				application.getStudent().getId(),
				application.getYearOfApplication().getYear(),
				UserProfileMapper.toUserProfileResponse(application.getStudent().getUserProfile()),
				EmergencyInfoMapper.toResponse(
						application.getStudent().getEmergencyInfo()),
				application.getEducation()
		);
	}

	public static BasicApplicationResponse toBasicResponse(Application application) {
		return new BasicApplicationResponse(
				application.getId(),
				application.getStudent().getId(),
				application.getYearOfApplication().getYear(),
				application.getStudent().getUserProfile().getPersonalInfo().profilePicture(),
				application.getStudent().getUserProfile().fullName(),
				application.getStudent().getUserProfile().getContactInfo().secondaryEmail(),
				application.getStatus(),
				application.isSubmitted()
		);
	}

	private ApplicationMapper() {
		throw new IllegalStateException(ExceptionConstants.UTILITY_CLASS);
	}
}
