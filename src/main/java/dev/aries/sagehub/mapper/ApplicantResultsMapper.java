package dev.aries.sagehub.mapper;

import dev.aries.sagehub.dto.request.ApplicantResultRequest;
import dev.aries.sagehub.dto.response.ApplicantResultsResponse;
import dev.aries.sagehub.model.Applicant;
import dev.aries.sagehub.model.ApplicantResult;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicantResultsMapper {
	private final SubjectScoreMapper subjectScoreMapper;
	public ApplicantResultsResponse toApplicantResultsResponse(ApplicantResult applicantResult) {
		return new ApplicantResultsResponse(
				applicantResult.getId(),
				applicantResult.getSchoolName(),
				applicantResult.getType().toString(),
				applicantResult.getYear(),
				applicantResult.getIndexNumber(),
				applicantResult.getScores().stream()
						.map(subjectScoreMapper::toSubjectScoreResponse)
						.toList()
		);
	}

	public ApplicantResult toApplicantResults(ApplicantResultRequest applicantResult, Applicant applicant) {
		return ApplicantResult.builder()
				.schoolName(applicantResult.schoolName())
				.type(applicantResult.resultType())
				.year(applicantResult.year())
				.indexNumber(applicantResult.indexNumber().value())
				.scores(applicantResult.subjectScores().stream()
						.map(subjectScoreMapper::toSubjectScore)
						.toList())
				.applicant(applicant)
				.build();
	}
}
