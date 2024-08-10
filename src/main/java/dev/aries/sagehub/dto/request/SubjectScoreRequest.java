package dev.aries.sagehub.dto.request;

import java.util.Objects;

import dev.aries.sagehub.model.attribute.Grade;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import static dev.aries.sagehub.constant.ValidationMessage.NOT_NULL;

public record SubjectScoreRequest(
		@NotEmpty(message = "Subject" + NOT_NULL)
		@Schema(example = "Mathematics")
		String subject,
		@NotNull(message = "Grade" + NOT_NULL)
		@Schema(example = "A")
		Grade grade
) {
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		SubjectScoreRequest that = (SubjectScoreRequest) o;
		return Objects.equals(subject, that.subject);
	}

	@Override
	public int hashCode() {
		return Objects.hash(subject);
	}
}
