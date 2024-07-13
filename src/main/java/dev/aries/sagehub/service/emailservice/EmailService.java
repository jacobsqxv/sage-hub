package dev.aries.sagehub.service.emailservice;

public interface EmailService {
	void sendAccountCreatedEmail(String username, String password, String recipient);

	void sendPasswordResetEmail(String recipient, String token);

	void sendPasswordResetCompleteEmail(String recipient, String url);
}
