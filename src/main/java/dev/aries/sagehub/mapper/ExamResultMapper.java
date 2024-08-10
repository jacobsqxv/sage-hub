package dev.aries.sagehub.mapper;

import dev.aries.sagehub.dto.request.ApplicantResultRequest;
import dev.aries.sagehub.dto.response.ResultResponse;
import dev.aries.sagehub.model.Application;
import dev.aries.sagehub.model.ExamResult;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicantResultsMapper {
	private final SubjectScoreMapper subjectScoreMapper;
	public ResultResponse toApplicantResultsResponse(ExamResult examResult) {
		return new ResultResponse(
				examResult.getId(),
				examResult.getSchoolName(),
				examResult.getType().toString(),
				examResult.getYear(),
				examResult.getIndexNumber(),
				examResult.getScores().stream()
						.map(subjectScoreMapper::toSubjectScoreResponse)
						.toList()
		);
	}

	public ExamResult toApplicantResults(ApplicantResultRequest applicantResult, Application application) {
		return ExamResult.builder()
				.schoolName(applicantResult.schoolName())
				.type(applicantResult.resultType())
				.year(applicantResult.year())
				.indexNumber(applicantResult.indexNumber().value())
				.scores(applicantResult.subjectScores().stream()
						.map(subjectScoreMapper::toSubjectScore)
						.toList())
				.applicant(application)
				.build();
	}
}
