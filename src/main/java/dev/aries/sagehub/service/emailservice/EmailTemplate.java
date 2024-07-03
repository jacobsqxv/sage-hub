package dev.aries.sagehub.service.emailservice;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum representing different types of email templates.
 * Each enum constant is associated with a template name and a subject.
 * @author Jacobs Agyei
 */
@Getter
@RequiredArgsConstructor
public enum EmailTemplate {
	/**
	 * Template for account creation emails.
	 * <p>
	 *     <ul>
	 *         <li>Name: "account-creation"</li>
	 *         <li>Subject: "Account Creation"</li>
	 *     </ul>
	 * </p>
	 */
	ACCOUNT_CREATION("account-creation", "Account Creation"),

	/**
	 * Template for password reset request emails.
	 * <p>
	 *     <ul>
	 *         <li>Name: "password-reset-request"</li>
	 *         <li>Subject: "Password Reset Request"</li>
	 *     </ul>
	 * </p>
	 */
	PASSWORD_RESET_REQUEST("password-reset-request", "Password Reset Request");

	private final String name;
	private final String subject;
}