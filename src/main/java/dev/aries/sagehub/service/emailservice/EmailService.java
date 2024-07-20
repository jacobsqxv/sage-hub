package dev.aries.sagehub.service.emailservice;

import dev.aries.sagehub.model.attribute.Username;

public interface EmailService {
	void sendAccountCreatedEmail(Username username, String password, String recipient);

	void sendPasswordResetEmail(String recipient, String token);

	void sendPasswordResetCompleteEmail(String recipient, String url);
}
