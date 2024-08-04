package dev.aries.sagehub.dto.request;

import java.util.Set;

import dev.aries.sagehub.enums.ResultType;
import dev.aries.sagehub.model.attribute.IDNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record ApplicantResultRequest(
	@NotEmpty(message = "Result type" + NOT_NULL)
			@Schema(example = "WASSCE")
	ResultType resultType,
	@NotEmpty(message = "School name" + NOT_NULL)
	@Schema(example = "Aries International School")
	String schoolName,
	@NotNull(message = "Academic year" + NOT_NULL)
	@Schema(example = "2024")
	Integer year,
	@NotNull(message = "Index number" + NOT_NULL)
	@Schema(example = "1234567890")
	IDNumber indexNumber,
	Set<@Valid SubjectScoreRequest> subjectScores
) {
}
