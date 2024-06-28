package dev.aries.sagehub.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
	ACTIVE("Active"),
	PENDING_REVIEW("Pending Review"),
	UNDER_REVIEW("Under Review"),
	INACTIVE("Inactive"),
	ARCHIVED("Archived");

	private final String value;
}
