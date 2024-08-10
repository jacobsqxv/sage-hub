package dev.aries.sagehub.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import dev.aries.sagehub.constant.ExceptionConstants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class GlobalUtil {

	public static String formatDateTime(LocalDateTime dateTime) {
		int day = dateTime.getDayOfMonth();
		String ordinalIndicator = getOrdinalIndicator(day);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
				"d'" + ordinalIndicator + "' MMMM, uuuu hh:mma", Locale.ENGLISH);
		return dateTime.format(formatter);
	}

	/**
	 * Get the ordinal indicator for a given day ('st','nd','rd','th') .
	 * @param day - The day to get the ordinal indicator for.
	 * @return the ordinal indicator.
	 */
	private static String getOrdinalIndicator(int day) {
		if (day >= 11 && day <= 13) {
			return "th";
		}
		return switch (day % 10) {
			case 1 -> "st";
			case 2 -> "nd";
			case 3 -> "rd";
			default -> "th";
		};
	}

	private GlobalUtil() {
		throw new IllegalStateException(ExceptionConstants.UTILITY_CLASS);
	}
}
