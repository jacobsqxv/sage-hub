package dev.aries.sagehub.service.emailservice;

import dev.aries.sagehub.model.attribute.Email;
import dev.aries.sagehub.model.attribute.Password;
import dev.aries.sagehub.model.attribute.Username;
import dev.aries.sagehub.util.EmailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
/**
 * Implementation of the {@code EmailService} interface.
 * @author Jacobs Agyei
 * @see dev.aries.sagehub.service.emailservice.EmailService
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
	private final EmailUtil emailUtil;
	@Value("${application.base-url}")
	private String baseUrl;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sendAccountCreatedEmail(Username username, Password password, Email recipient) {
		EmailDetails emailDetails = EmailDetails
				.builder()
				.username(username)
				.password(password)
				.recipient(recipient)
				.template(EmailTemplate.ACCOUNT_CREATION)
				.build();
		emailUtil.sendEmail(emailDetails);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sendPasswordResetEmail(Email recipient, String token) {
		String passwordResetUrl = baseUrl + "auth/reset-password?token=";
		EmailDetails emailDetails = EmailDetails
				.builder()
				.recipient(recipient)
				.url(passwordResetUrl + token)
				.template(EmailTemplate.PASSWORD_RESET_REQUEST)
				.build();
		emailUtil.sendEmail(emailDetails);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sendPasswordResetCompleteEmail(Email recipient, String url) {
		EmailDetails emailDetails = EmailDetails
				.builder()
				.recipient(recipient)
				.url(url)
				.template(EmailTemplate.PASSWORD_RESET_COMPLETE)
				.build();
		emailUtil.sendEmail(emailDetails);
	}
}
