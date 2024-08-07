package dev.aries.sagehub.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import dev.aries.sagehub.constant.Patterns;

public record Grade(String value) {
	public Grade {
		if (!value.matches(Patterns.GRADE)) {
			throw new IllegalArgumentException("Grade cannot be null or empty");
		}
	}

	@JsonCreator
	public static Grade of(String value) {
		return new Grade(value);
	}

	public Character getGrade() {
		return value.charAt(0);
	}
}
