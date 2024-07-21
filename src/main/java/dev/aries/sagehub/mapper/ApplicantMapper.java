package dev.aries.sagehub.mapper;

import dev.aries.sagehub.dto.response.ApplicantResponse;
import dev.aries.sagehub.model.Applicant;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicantMapper {
	private final BasicInfoMapper basicInfoMapper;
	private final ApplicantResultsMapper applicantResultsMapper;
	private final ProgramMapper programMapper;
	private final EmergencyContactMapper emergencyContactMapper;
	private final ContactInfoMapper contactInfoMapper;

	public ApplicantResponse toApplicantResponse(Applicant applicant) {
		ApplicantResponse.ApplicantResponseBuilder response = ApplicantResponse.builder();
		response.id(applicant.getId())
				.applyingForYear(applicant.getApplyingForYear().getYear())
				.basicInfo(this.basicInfoMapper.toBasicInfoResponse(applicant.getBasicInfo()))
				.status(applicant.getStatus().toString())
				.isSubmitted(applicant.isSubmitted());
		response.contactInfo(this.contactInfoMapper.toContactInfoResponse(applicant.getContactInfo()));
		response.guardianInfo(this.emergencyContactMapper
				.toEmergencyContactResponse(applicant.getEmergencyContact()));
		if (applicant.getProgramChoices() != null) {
			response.programs(applicant.getProgramChoices().stream()
					.map(this.programMapper::toBasicProgramResponse)
					.toList());
		}
		if (applicant.getResults() != null) {
			response.results(applicant.getResults().stream()
					.map(this.applicantResultsMapper::toApplicantResultsResponse)
					.toList());
		}
		return response.build();
	}

}
