package dev.aries.sagehub.mapper;

import dev.aries.sagehub.dto.response.ApplicationResponse;
import dev.aries.sagehub.dto.response.BasicApplicationResponse;
import dev.aries.sagehub.dto.response.BasicInfoResponse;
import dev.aries.sagehub.model.Applicant;
import dev.aries.sagehub.model.BasicInfo;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationMapper {
	private final BasicInfoMapper basicInfoMapper;
	private final ApplicantResultsMapper applicantResultsMapper;
	private final ProgramMapper programMapper;
	private final EmergencyContactMapper emergencyContactMapper;
	private final ContactInfoMapper contactInfoMapper;

	public ApplicationResponse toApplicationResponse(Applicant applicant) {
		return new ApplicationResponse(
				applicant.getId(),
				applicant.getId(),
				applicant.getApplyingForYear().getYear(),
				toBasicInfoResponse(applicant),
				this.contactInfoMapper.toContactInfoResponse(applicant.getContactInfo()),
				this.emergencyContactMapper
						.toEmergencyContactResponse(applicant.getGuardianInfo()),
				applicant.getResult().stream()
						.map(this.applicantResultsMapper::toApplicantResultsResponse)
						.toList(),
				applicant.getProgramChoices().stream()
						.map(this.programMapper::toProgramResponse)
						.toList(),
				applicant.getStatus().name(),
				applicant.isSubmitted()
		);
	}

	private BasicInfoResponse toBasicInfoResponse(Applicant applicant) {
		BasicInfo basicInfo = BasicInfo.builder()
				.profilePictureUrl(applicant.getProfilePictureUrl())
				.firstName(applicant.getFirstName())
				.middleName(applicant.getMiddleName())
				.lastName(applicant.getLastName())
				.title(applicant.getTitle())
				.gender(applicant.getGender())
				.maritalStatus(applicant.getMaritalStatus())
				.dateOfBirth(applicant.getDateOfBirth())
				.build();
		return this.basicInfoMapper.toBasicInfoResponse(basicInfo);
	}

	public BasicApplicationResponse toBasicApplicationResponse(Applicant applicant) {
		return new BasicApplicationResponse(
				applicant.getId(),
				applicant.getApplyingForYear().getYear(),
				toBasicInfoResponse(applicant),
				applicant.getStatus().name(),
				applicant.isSubmitted()
		);
	}
}