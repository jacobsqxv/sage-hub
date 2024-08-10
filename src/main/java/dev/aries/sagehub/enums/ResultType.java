package dev.aries.sagehub.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The {@code ResultType} class represents the different types of examination results
 * that can be processed. This is particularly used to distinguish between
 * various examination formats within the application.
 * <ul>
 *     <li>{@code WASSCE_SCHOOL} - Represents results from the West African
 *     Senior School Certificate Examination taken in school.</li>
 *     <li>{@code WASSCE_PRIVATE} - Represents results from the private candidate version
 *     of the West African Senior School Certificate Examination.</li>
 * </ul>
 *
 * @author Jacobs Agyei
 */
@Getter
@RequiredArgsConstructor
public enum ResultType {
	/**
	 * Represents results from the West African Senior School Certificate Examination taken in school.
	 */
	WASSCE_SCHOOL,
	/**
	 * Represents results from the private candidate version
	 * of the West African Senior School Certificate Examination.
	 */
	WASSCE_PRIVATE;

	@Override
	public String toString() {
		return switch (this) {
			case WASSCE_SCHOOL -> "WASSCE (School)";
			case WASSCE_PRIVATE -> "WASSCE (Private)";
		};
	}
}
