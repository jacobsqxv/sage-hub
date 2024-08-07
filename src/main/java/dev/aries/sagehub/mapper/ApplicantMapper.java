package dev.aries.sagehub.mapper;

import dev.aries.sagehub.dto.response.ApplicantResponse;
import dev.aries.sagehub.dto.response.BasicApplicantResponse;
import dev.aries.sagehub.model.Applicant;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicantMapper {
	private final BasicInfoMapper basicInfoMapper;
	private final EmergencyContactMapper emergencyContactMapper;
	private final ContactInfoMapper contactInfoMapper;

	public ApplicantResponse toApplicantResponse(Applicant applicant) {
		return new ApplicantResponse(
				applicant.getUser().getId(),
				applicant.getId(),
				applicant.getYearOfApplication().getYear(),
				basicInfoMapper.toBasicInfoResponse(applicant.getBasicInfo()),
				contactInfoMapper.toContactInfoResponse(applicant.getContactInfo()),
				emergencyContactMapper.toEmergencyContactResponse(
						applicant.getEmergencyContact()),
				applicant.getStatus(),
				applicant.isSubmitted()
		);
	}

	public BasicApplicantResponse toBasicApplicantResponse(Applicant applicant) {
		return new BasicApplicantResponse(
				applicant.getUser().getId(),
				applicant.getId(),
				applicant.getYearOfApplication().getYear(),
				applicant.getBasicInfo().getProfilePictureUrl(),
				applicant.getBasicInfo().fullName(),
				applicant.getContactInfo().getSecondaryEmail(),
				applicant.getStatus(),
				applicant.isSubmitted()
		);
	}
}
