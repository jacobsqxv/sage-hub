package dev.aries.sagehub.mapper;

import dev.aries.sagehub.dto.response.ApplicationResponse;
import dev.aries.sagehub.dto.response.BasicApplicationResponse;
import dev.aries.sagehub.model.Application;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicantMapper {
	private final UserInfoMapper userInfoMapper;
	private final EmergencyInfoMapper emergencyInfoMapper;
	private final ContactInfoMapper contactInfoMapper;

	public ApplicationResponse toApplicantResponse(Application application) {
		return new ApplicationResponse(
				application.getUser().getId(),
				application.getId(),
				application.getYearOfApplication().getYear(),
				userInfoMapper.toUserInfoResponse(application.getBasicInfo()),
				contactInfoMapper.toContactInfoResponse(application.getContactInfo()),
				emergencyInfoMapper.toEmergencyContactResponse(
						application.getEmergencyContact()),
				application.getStatus(),
				application.isSubmitted()
		);
	}

	public BasicApplicationResponse toBasicApplicantResponse(Application application) {
		return new BasicApplicationResponse(
				application.getUser().getId(),
				application.getId(),
				application.getYearOfApplication().getYear(),
				application.getBasicInfo().getProfilePictureUrl(),
				application.getBasicInfo().fullName(),
				application.getContactInfo().getSecondaryEmail(),
				application.getStatus(),
				application.isSubmitted()
		);
	}
}
