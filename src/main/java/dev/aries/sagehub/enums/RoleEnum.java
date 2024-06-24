package dev.aries.sagehub.enums;

/**
 * Enum representing the various roles a user can have in the system. Each role grants the
 * user a different level of access and permissions.
 * @author Aries N/A
 */
public enum RoleEnum {

	/**
	 * Represents a student role. Students typically have the least amount of access and
	 * permissions.
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
	SUPER_ADMIN

}
