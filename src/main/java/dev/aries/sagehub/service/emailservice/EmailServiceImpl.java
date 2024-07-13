package dev.aries.sagehub.service.emailservice;

import dev.aries.sagehub.util.EmailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
	private final EmailUtil emailUtil;
	@Value("${application.base-url}")
	private String baseUrl;

	@Override
	public void sendAccountCreatedEmail(String username, String password, String recipient) {
		EmailDetails emailDetails = EmailDetails
				.builder()
				.username(username)
				.password(password)
				.recipient(recipient)
				.template(EmailTemplate.ACCOUNT_CREATION)
				.build();
		this.emailUtil.sendEmail(emailDetails);
	}

	@Override
	public void sendPasswordResetEmail(String recipient, String token) {
		String passwordResetUrl = baseUrl + "auth/reset-password?token=";
		EmailDetails emailDetails = EmailDetails
				.builder()
				.recipient(recipient)
				.url(passwordResetUrl + token)
				.template(EmailTemplate.PASSWORD_RESET_REQUEST)
				.build();
		this.emailUtil.sendEmail(emailDetails);
	}

	@Override
	public void sendPasswordResetCompleteEmail(String recipient, String url) {
		EmailDetails emailDetails = EmailDetails
				.builder()
				.recipient(recipient)
				.url(url)
				.template(EmailTemplate.PASSWORD_RESET_COMPLETE)
				.build();
		this.emailUtil.sendEmail(emailDetails);
	}
}
