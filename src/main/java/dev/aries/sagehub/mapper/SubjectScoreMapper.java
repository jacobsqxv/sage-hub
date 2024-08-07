package dev.aries.sagehub.mapper;

import dev.aries.sagehub.dto.request.SubjectScoreRequest;
import dev.aries.sagehub.dto.response.SubjectScoreResponse;
import dev.aries.sagehub.model.SubjectScore;

import org.springframework.stereotype.Component;

@Component
public class SubjectScoreMapper {
	public SubjectScoreResponse toSubjectScoreResponse(SubjectScore subjectScore) {
		return SubjectScoreResponse.builder()
				.subject(subjectScore.getSubjectName())
				.grade(subjectScore.getGrade())
				.build();
	}

	public SubjectScore toSubjectScore(SubjectScoreRequest subjectScore) {
		return SubjectScore.builder()
				.subjectName(subjectScore.subject())
				.grade(subjectScore.grade().getGrade())
				.build();
	}
}
