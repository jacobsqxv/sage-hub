package dev.aries.sagehub.enums;

/**
 * The {@code ApplicantStatus} class represents the various stages an application can be in
 * within the application process. It is used to track the progress and current state
 * of an application through the system's workflow.
 * <p>States include:</p>
 * <ul>
 *     <li>{@code PENDING} - The application has been submitted but not yet reviewed.</li>
 *     <li>{@code UNDER_REVIEW} - The application is under review.</li>
 *     <li>{@code APPROVED} - The application has been reviewed and accepted.</li>
 *     <li>{@code REJECTED} - The application has been reviewed and not accepted.</li>
 * </ul>
 *
 * @author Jacobs Agyei
 */
public enum ApplicantStatus {
	/**
	 * This represents the pending state.
	 */
	PENDING,
	/**
	 * This represents the approved state.
	 */
	APPROVED,
	/**
	 * This represents the under review state.
	 */
	UNDER_REVIEW,
	/**
	 * This represents the rejected state.
	 */
	REJECTED;

	@Override
	public String toString() {
		return switch (this) {
			case PENDING -> "PENDING";
			case APPROVED -> "APPROVED";
			case UNDER_REVIEW -> "UNDER REVIEW";
			case REJECTED -> "REJECTED";
		};
	}
}
