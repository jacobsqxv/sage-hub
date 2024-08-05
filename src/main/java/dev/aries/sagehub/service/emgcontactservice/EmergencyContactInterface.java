package dev.aries.sagehub.service.emgcontactservice;

import dev.aries.sagehub.dto.request.EmergencyContactRequest;
import dev.aries.sagehub.dto.response.EmergencyContactResponse;
import dev.aries.sagehub.model.EmergencyContact;

/**
 * The {@code EmergencyContactInterface} provides methods for managing emergency contact information.
 * It includes functionality for retrieving, adding, and updating emergency contact information.
 * <p>
 * Implementations of this interface are responsible for handling the business logic
 * related to emergency contact information management.
 * </p>
 * @author Jacobs Agyei
 */
public interface EmergencyContactInterface {

	/**
	 * Retrieves the emergency contact information of a user.
	 * <p>
	 * This method takes the ID of the emergency contact info and returns a {@code EmergencyContactResponse} object
	 * containing the user's emergency contact information.
	 * </p>
	 * @param id the ID of the emergency contact info.
	 * @return a {@code EmergencyContactResponse} containing the user's emergency contact information.
	 * @throws IllegalArgumentException if the id is null or invalid.
	 */
	EmergencyContactResponse getEmergencyContact(Long id);

	/**
	 * Adds new emergency contact information for a user.
	 * <p>
	 * This method takes an {@code EmergencyContactRequest} object containing the new emergency contact information,
	 * and the ID of the user, and returns an {@code EmergencyContact} object
	 * containing the updated emergency contact information.
	 * </p>
	 * @param request the {@code EmergencyContactRequest} containing the new emergency contact information.
	 * @param userId the ID of the user.
	 * @return an {@code EmergencyContact} containing the updated emergency contact information.
	 * @throws IllegalArgumentException if the request or userId is null or contains invalid data.
	 */
	EmergencyContact addEmergencyContact(EmergencyContactRequest request, Long userId);

	/**
	 * Updates the emergency contact information of a user.
	 * <p>
	 * This method takes the ID of the emergency contact info and an {@code EmergencyContactRequest} object
	 * containing the new emergency contact information,
	 * and returns an {@code EmergencyContactResponse} object containing the updated emergency contact information.
	 * </p>
	 * @param id the ID of the emergency contact info.
	 * @param request the {@code EmergencyContactRequest} containing the new emergency contact information.
	 * @return an {@code EmergencyContactResponse} containing the updated emergency contact information.
	 * @throws IllegalArgumentException if the id or request is null or contains invalid data.
	 */
	EmergencyContactResponse updateEmergencyContact(Long id, EmergencyContactRequest request);
}
