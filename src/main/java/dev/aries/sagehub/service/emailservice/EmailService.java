package dev.aries.sagehub.service.emailservice;

import dev.aries.sagehub.model.attribute.Email;
import dev.aries.sagehub.model.attribute.Password;
import dev.aries.sagehub.model.attribute.Username;

/**
 * The {@code EmailService} interface provides methods for sending various types of emails.
 * It includes functionality for sending account creation emails, password reset emails,
 * and password reset completion emails.
 * <p>
 * Implementations of this interface are responsible for handling the business logic
 * related to email notifications.
 * </p>
 * @author Jacobs Agyei
 */
public interface EmailService {

	/**
	 * Sends an account creation email.
	 * <p>
	 * This method takes a {@code Username}, {@code Password}, and {@code Email} object,
	 * and sends an email to the recipient with the account creation details.
	 * </p>
	 * @param username the {@code Username} of the new account.
	 * @param password the {@code Password} of the new account.
	 * @param recipient the {@code Email} of the recipient.
	 * @throws IllegalArgumentException if any of the parameters are null or invalid.
	 */
	void sendAccountCreatedEmail(Username username, Password password, Email recipient);

	/**
	 * Sends a password reset email.
	 * <p>
	 * This method takes an {@code Email} object and a token,
	 * and sends an email to the recipient with the password reset token.
	 * </p>
	 * @param recipient the {@code Email} of the recipient.
	 * @param token the password reset token.
	 * @throws IllegalArgumentException if the recipient or token is null or invalid.
	 */
	void sendPasswordResetEmail(Email recipient, String token);

	/**
	 * Sends a password reset completion email.
	 * <p>
	 * This method takes an {@code Email} object and a URL,
	 * and sends an email to the recipient with the password reset completion details.
	 * </p>
	 * @param recipient the {@code Email} of the recipient.
	 * @param url the URL for password reset completion.
	 * @throws IllegalArgumentException if the recipient or url is null or invalid.
	 */
	void sendPasswordResetCompleteEmail(Email recipient, String url);
}
