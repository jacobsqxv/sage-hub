package dev.aries.sagehub.util;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.exception.EmailSendFailureException;
import dev.aries.sagehub.model.attribute.Email;
import dev.aries.sagehub.service.emailservice.EmailDetails;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailUtil {
	@Value("${mailgun.api-key}")
	private String mailgunApiKey;
	@Value("${mailgun.from}")
	private String mailgunFrom;
	@Value("${mailgun.domain}")
	private String mailgunDomain;
	private final UserUtil userUtil;

	public Email getRecipient(Long userId) {
		String recipient = userUtil.getUserEmail(userId);
		if (recipient == null) {
			throw new IllegalArgumentException(
					String.format(ExceptionConstants.NOT_FOUND, "Email"));
		}
		return new Email(recipient);
	}

	/**
	 * Send an email using the Mailgun API.
	 * @param emailDetails - The email details to send.
	 */
	public void sendEmail(EmailDetails emailDetails) {
		ClientConfig clientConfig = new ClientConfig();
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("api", mailgunApiKey);
		clientConfig.register(feature);

		Form formData = new Form();
		String variables = createDynamicVariables(emailDetails);
		formData.param("from", "SageHub <" + mailgunFrom + ">");
		formData.param("to", emailDetails.recipient().value());
		formData.param("subject", emailDetails.template().getSubject());
		formData.param("template", emailDetails.template().getName());
		formData.param("h:X-Mailgun-Variables", variables);

		try (Client client = ClientBuilder.newClient(clientConfig)) {
			WebTarget webResource = client.target(mailgunDomain);
			log.debug("Sending email to {}", emailDetails.recipient().value());
			try (Response response = webResource.request(MediaType.APPLICATION_FORM_URLENCODED)
					.post(Entity.entity(formData, MediaType.APPLICATION_FORM_URLENCODED))) {
				if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
					log.debug("Email sent successfully to {}",
							emailDetails.recipient().value());
				}
				else {
					// Log error response from Mailgun
					log.error("Failed to send email:: Cause: {}",
							response.readEntity(String.class));
					throw new EmailSendFailureException();
				}
			}
		}
	}

	/**
	 * Create dynamic variables for the email template.
	 * @param emailDetails - The email details to send.
	 * @return - The dynamic variables as a JSON string.
	 */
	private String createDynamicVariables(EmailDetails emailDetails) {
		Map<String, Object> variables = new HashMap<>();

		if (emailDetails.username() != null) {
			variables.put("username", emailDetails.username().value());
		}
		if (emailDetails.password() != null) {
			variables.put("password", emailDetails.password().value());
		}
		if (emailDetails.url() != null) {
			variables.put("url", emailDetails.url());
		}
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(variables);
		}
		catch (Exception ex) {
			return "{}";
		}
	}
}
