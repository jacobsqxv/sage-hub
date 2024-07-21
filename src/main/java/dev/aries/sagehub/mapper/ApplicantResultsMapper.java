package dev.aries.sagehub.mapper;

import java.util.stream.Collectors;

import dev.aries.sagehub.dto.request.ApplicantResultRequest;
import dev.aries.sagehub.dto.response.ApplicantResultsResponse;
import dev.aries.sagehub.enums.ResultType;
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
						.map(this.subjectScoreMapper::toSubjectScoreResponse)
						.collect(Collectors.toSet())
		);
	}

	public ApplicantResult toApplicantResults(ApplicantResultRequest applicantResult) {
		return ApplicantResult.builder()
				.schoolName(applicantResult.schoolName())
				.type(ResultType.valueOf(applicantResult.resultType().toUpperCase()))
				.year(applicantResult.year())
				.indexNumber(applicantResult.indexNumber().value())
				.scores(applicantResult.subjectScores().stream()
						.map(this.subjectScoreMapper::toSubjectScore)
						.collect(Collectors.toSet()))
				.build();
	}
}
