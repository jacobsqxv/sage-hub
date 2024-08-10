package dev.aries.sagehub.mapper;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.request.ExamResultRequest;
import dev.aries.sagehub.dto.request.SubjectScoreRequest;
import dev.aries.sagehub.dto.response.ExamResultResponse;
import dev.aries.sagehub.dto.response.SubjectScoreResponse;
import dev.aries.sagehub.model.ExamResult;
import dev.aries.sagehub.model.SubjectScore;
import dev.aries.sagehub.model.User;

public final class ExamResultMapper {

	public static ExamResultResponse toResultResponse(ExamResult examResult) {
		return new ExamResultResponse(
				examResult.getId(),
				examResult.getType().toString(),
				examResult.getYear(),
				examResult.getIndexNumber(),
				examResult.getResults().stream()
						.map(ExamResultMapper::toScoreResponse)
						.toList()
		);
	}

	public static ExamResult toExamResult(ExamResultRequest request, User user) {
		return ExamResult.builder()
				.type(request.resultType())
				.year(request.year())
				.indexNumber(request.indexNumber().value())
				.results(request.subjectScores().stream()
						.map(ExamResultMapper::toSubjectScore)
						.toList())
				.user(user)
				.build();
	}

	public static SubjectScoreResponse toScoreResponse(SubjectScore subjectScore) {
		return SubjectScoreResponse.builder()
				.subject(subjectScore.getSubject())
				.grade(subjectScore.getGrade())
				.build();
	}

	public static SubjectScore toSubjectScore(SubjectScoreRequest subjectScore) {
		return SubjectScore.builder()
				.subject(subjectScore.subject())
				.grade(subjectScore.grade().getGrade())
				.build();
	}

	private ExamResultMapper() {
		throw new IllegalStateException(ExceptionConstants.UTILITY_CLASS);
	}
}
