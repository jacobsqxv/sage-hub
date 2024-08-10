package dev.aries.sagehub.dto.request;

import java.util.List;

import dev.aries.sagehub.enums.ResultType;
import dev.aries.sagehub.model.attribute.IDNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UniqueElements;

import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record ExamResultRequest(
	@NotNull(message = "Result type" + NOT_NULL)
	@Schema(example = "WASSCE_SCHOOL")
	ResultType resultType,
	@NotNull(message = "Academic year" + NOT_NULL)
	@Schema(example = "2024")
	Integer year,
	@NotNull(message = "Index number" + NOT_NULL)
	@Schema(example = "1234567890")
	IDNumber indexNumber,
	@UniqueElements(message = "Subject scores must be unique")
	List<@Valid SubjectScoreRequest> subjectScores
) {
}
