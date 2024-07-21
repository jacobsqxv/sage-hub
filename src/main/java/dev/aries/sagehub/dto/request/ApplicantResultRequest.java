package dev.aries.sagehub.dto.request;

import java.util.Set;

import dev.aries.sagehub.model.attribute.IDNumber;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record ApplicantResultRequest(
	@NotEmpty(message = "Result type" + NOT_NULL)
	String resultType,
	@NotEmpty(message = "School name" + NOT_NULL)
	String schoolName,
	@NotNull(message = "Academic year" + NOT_NULL)
	Integer year,
	@NotNull(message = "Index number" + NOT_NULL)
	IDNumber indexNumber,
	Set<@Valid SubjectScoreRequest> subjectScores
) {
}
