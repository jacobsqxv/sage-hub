package dev.aries.sagehub.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum {@code ResultType} represents the different types of examination results
 * that can be processed. This is particularly used to distinguish between
 * various examination formats within the application.
 *
 * @author Jacobs Agyei
 */
@Getter
@RequiredArgsConstructor
public enum ResultType {
	/**
	 * Represents results from the West African Senior School Certificate Examination taken in school.
	 */
	WASSCE_SCHOOL("WASSCE School"),
	/**
	 * Represents results from the private candidate version of the West African Senior School Certificate Examination.
	 */
	WASSCE_PRIVATE("WASSCE Private");
	private final String value;
}
