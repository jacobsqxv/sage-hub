package dev.aries.sagehub.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import dev.aries.sagehub.constant.Patterns;
import dev.aries.sagehub.constant.ValidationMessage;
import jakarta.persistence.Embeddable;

@Embeddable
public record Password(String value) implements CharSequence {
	public Password {
		if (!value.matches(Patterns.PASSWORD)) {
			throw new IllegalArgumentException(ValidationMessage.INVALID_FORMAT + "password");
		}
	}

	@JsonCreator
	public static Password of(String value) {
		return new Password(value);
	}

	@Override
	public int length() {
		return this.value.length();
	}

	@Override
	public char charAt(int index) {
		return this.value.charAt(index);
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return this.value.subSequence(start, end);
	}

	@Override
	public String toString() {
		return this.value;
	}
}
