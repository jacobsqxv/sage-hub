package dev.aries.sagehub.enums;

/**
 * Enum representing the various roles a user can have in the system. Each role grants the
 * user a different level of access and permissions.
 * @author Aries N/A
 */
public enum RoleEnum {
	/**
	 * Represents a prospective student role.
	 * Prospective students typically have the least amount of access and
	 * permissions.
	 */
	PROSPECTIVE_STUDENT,
	/**
	 * Represents a student role. Students have more access and
	 * permissions than prospective students, but less than staff.
	 */
	STUDENT,

	/**
	 * Represents a staff role. Staff members have more access and permissions than
	 * students, but less than admins.
	 */
	STAFF,

	/**
	 * Represents an admin role. Admins have a high level of access and permissions,
	 * including some management functions.
	 */
	ADMIN,

	/**
	 * Represents a super admin role. Super admins have the highest level of access and
	 * permissions, including full management and configuration capabilities.
	 */
	SUPER_ADMIN;

	@Override
	public String toString() {
		return switch (this) {
			case PROSPECTIVE_STUDENT -> "PROSPECTIVE STUDENT";
			case STUDENT -> "STUDENT";
			case STAFF -> "STAFF";
			case ADMIN -> "ADMIN";
			case SUPER_ADMIN -> "SUPER ADMIN";
		};
	}
}
