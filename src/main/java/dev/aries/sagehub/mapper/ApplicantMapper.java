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
		response.userId(applicant.getUser().getId())
				.applicantId(applicant.getId())
				.yearOfApplication(applicant.getYearOfApplication().getYear())
				.basicInfo(basicInfoMapper.toBasicInfoResponse(applicant.getBasicInfo()))
				.status(applicant.getStatus().toString())
				.isSubmitted(applicant.isSubmitted());
		response.contactInfo(contactInfoMapper.toContactInfoResponse(applicant.getContactInfo()));
		response.guardianInfo(emergencyContactMapper
				.toEmergencyContactResponse(applicant.getEmergencyContact()));
		if (applicant.getProgramChoices() != null) {
			response.programs(applicant.getProgramChoices().stream()
					.map(programMapper::toBasicProgramResponse)
					.toList());
		}
		if (applicant.getResults() != null) {
			response.results(applicant.getResults().stream()
					.map(applicantResultsMapper::toApplicantResultsResponse)
					.toList());
		}
		return response.build();
	}

}
