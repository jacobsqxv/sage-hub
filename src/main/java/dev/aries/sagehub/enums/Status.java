package dev.aries.sagehub.enums;

/**
 * Enum {@code Status} represents the various states an entity can be in within the application.
 * It is designed to be used across different entities to maintain a consistent status tracking mechanism.
 *
 * <p>States include:</p>
 * <ul>
 *     <li>{@code ACTIVE} - Indicates the entity is currently active and operational within the system.</li>
 *     <li>{@code PENDING} - Represents a state where the entity is awaiting further action or review.</li>
 *     <li>{@code UNDER_REVIEW} - Signifies that the entity is currently under review by the relevant authorities.</li>
 *     <li>{@code INACTIVE} - Denotes that the entity is not active, possibly due to suspension or other reasons.</li>
 *     <li>{@code ARCHIVED} - Used when the entity is no longer actively used but is kept for historical reference.</li>
 *     <li>{@code DELETED} - Indicates that the entity has been removed from the system, typically irreversibly.</li>
 * </ul>
 *
 * @author Jacobs Agyei
 */
public enum Status {
	/**
	 * This represents the active state.
	 */
	ACTIVE,
	/**
	 * This represents the pending state.
	 */
	PENDING,
	/**
	 * This represents the under review state.
	 */
	UNDER_REVIEW,
	/**
	 * This represents the inactive state.
	 */
	INACTIVE,
	/**
	 * This represents the archived state.
	 */
	ARCHIVED,
	/**
	 * This represents the deleted state.
	 */
	DELETED;

	@Override
	public String toString() {
		return switch (this) {
			case ACTIVE -> "ACTIVE";
			case PENDING -> "PENDING";
			case UNDER_REVIEW -> "UNDER REVIEW";
			case INACTIVE -> "INACTIVE";
			case ARCHIVED -> "ARCHIVED";
			case DELETED -> "DELETED";
		};
	}
}
