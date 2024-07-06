package dev.aries.sagehub.enums;

/**
 * Enum representing the possible states of a user account within the system.
 * <ul>
 *     <li>{@code ACTIVE} - The account is currently active and the user can access all the system's features.</li>
 *     <li>{@code INACTIVE} - The account is inactive, possibly due to user inaction or administrative decision.
 *     Access to the system is restricted.</li>
 *     <li>{@code DELETED} - The account has been deleted.
 *     This is typically irreversible and means all user data associated with the account may have been removed.</li>
 * </ul>
 * @author Jacobs Agyei
 */
public enum AccountStatus {
	/**
	 * This represents the active state.
	 */
	ACTIVE,
	/**
	 * This represents the inactive state.
	 */
	INACTIVE,
	/**
	 * This represents the deleted state.
	 */
	DELETED
}
