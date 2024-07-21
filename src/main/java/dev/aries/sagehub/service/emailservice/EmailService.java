package dev.aries.sagehub.service.emailservice;

import dev.aries.sagehub.model.attribute.Email;
import dev.aries.sagehub.model.attribute.Password;
import dev.aries.sagehub.model.attribute.Username;

public interface EmailService {
	void sendAccountCreatedEmail(Username username, Password password, Email recipient);

	void sendPasswordResetEmail(Email recipient, String token);

	void sendPasswordResetCompleteEmail(Email recipient, String url);
}
