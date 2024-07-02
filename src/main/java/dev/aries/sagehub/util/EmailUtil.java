package dev.aries.sagehub.util;

import java.util.concurrent.CompletableFuture;

import dev.aries.sagehub.exception.EmailSendFailureException;
import dev.aries.sagehub.service.emailservice.EmailDetails;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailUtil {
	private final JavaMailSender mailSender;
	private final SpringTemplateEngine templateEngine;

	private static Context getContext(EmailDetails emailDetails) {
		Context context = new Context();
		context.setVariable("recipient", emailDetails.recipient());
		context.setVariable("url", emailDetails.url());
		context.setVariable("username", emailDetails.username());
		context.setVariable("password", emailDetails.password());
		return context;
	}

	@Async
	public CompletableFuture<Void> sendEmail(EmailDetails emailDetails) {
		return CompletableFuture.runAsync(() -> {
			try {
				String templateName = emailDetails.template().getName();

				MimeMessage message = this.mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(
						message,
						MULTIPART_MODE_MIXED,
						UTF_8.name());

				Context context = getContext(emailDetails);

				helper.setFrom("hbpbackend@mockinbox.com");
				helper.setTo(emailDetails.recipient());
				helper.setSubject(emailDetails.template().getSubject());

				String template = this.templateEngine.process(templateName, context);

				helper.setText(template, true);

				this.mailSender.send(message);
			}
			catch (MessagingException ex) {
				log.error("Email sending failed", ex);
				throw new EmailSendFailureException();
			}
		}).exceptionally((ex) -> {
			log.error("Exception occurred during email sending", ex);
			return null;
		});
	}
}
