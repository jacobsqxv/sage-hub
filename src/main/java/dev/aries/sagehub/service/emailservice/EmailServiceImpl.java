package dev.aries.sagehub.service.emailservice;

import dev.aries.sagehub.util.EmailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
	private final EmailUtil emailUtil;

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
}
