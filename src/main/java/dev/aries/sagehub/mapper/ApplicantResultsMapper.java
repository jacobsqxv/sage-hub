package dev.aries.sagehub.mapper;

import java.util.stream.Collectors;

import dev.aries.sagehub.dto.response.ApplicantResultsResponse;
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
				applicantResult.getType().getValue(),
				applicantResult.getYear(),
				applicantResult.getIndexNumber(),
				applicantResult.getScores().stream()
						.map(this.subjectScoreMapper::toSubjectScoreResponse)
						.collect(Collectors.toSet())
		);
	}
}
