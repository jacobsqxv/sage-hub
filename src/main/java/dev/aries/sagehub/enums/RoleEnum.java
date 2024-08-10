package dev.aries.sagehub.enums;

/**
 * The {@code RoleEnum} class represents the various roles a user can have in the system.
 * Each role grants the user a different level of access and permissions.
 * <p>The roles are listed in order of increasing access and permissions.</p>
 * <ul>
 *     <li>{@code APPLICANT} - Represents a prospective student role.</li>
 *     <li>{@code STUDENT} - Represents a student role.</li>
 *     <li>{@code STAFF} - Represents a staff role.</li>
 *     <li>{@code ADMIN} - Represents an admin role. Admins have a high level of
 *     access and permissions, including some management functions.</li>
 *     <li>{@code SUPER_ADMIN} - Represents a super admin role. Super admins have the highest level of access and
 * 	   permissions, including full management and configuration capabilities.</li>
 * </ul>
 *
 * @author Jacobs Agyei
 */
public enum RoleEnum {
	/**
	 * Represents a prospective student role.
	 */
	APPLICANT,
	/**
	 * Represents a student role.
	 */
	STUDENT,

	/**
	 * Represents a staff role.
	 */
	STAFF,

	/**
	 * Represents an admin role.
	 */
	ADMIN,

	/**
	 * Represents a super admin role.
	 */
	SUPER_ADMIN;

	@Override
	public String toString() {
		return switch (this) {
			case APPLICANT -> "APPLICANT";
			case STUDENT -> "STUDENT";
			case STAFF -> "STAFF";
			case ADMIN -> "ADMIN";
			case SUPER_ADMIN -> "SUPER_ADMIN";
		};
	}
}
