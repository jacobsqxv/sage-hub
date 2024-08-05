package dev.aries.sagehub.service.contactinfoservice;

import dev.aries.sagehub.dto.request.ContactInfoRequest;
import dev.aries.sagehub.dto.response.ContactInfoResponse;
import dev.aries.sagehub.model.ContactInfo;

/**
 * The {@code ContactInfoInterface} provides methods for managing user contact information.
 * It includes functionality for retrieving, adding, and updating contact information.
 * <p>
 * Implementations of this interface are responsible for handling the business logic
 * related to contact information management.
 * </p>
 * @author Jacobs Agyei
 */
public interface ContactInfoInterface {

	/**
	 * Retrieves the contact information of a user.
	 * <p>
	 * This method takes the ID of the contact info and returns a {@code ContactInfoResponse} object
	 * containing the user's contact information.
	 * </p>
	 * @param id the ID of the contact info.
	 * @return a {@code ContactInfoResponse} containing the user's contact information.
	 * @throws IllegalArgumentException if the id is null or invalid.
	 */
	ContactInfoResponse getContactInfo(Long id);

	/**
	 * Adds new contact information for a user.
	 * <p>
	 * This method takes a {@code ContactInfoRequest} object containing the new contact information,
	 * and the ID of the user, and returns a {@code ContactInfo} object containing the updated contact information.
	 * </p>
	 * @param request the {@code ContactInfoRequest} containing the new contact information.
	 * @param userId  the ID of the user.
	 * @return a {@code ContactInfo} containing the updated contact information.
	 * @throws IllegalArgumentException if the request or userId is null or contains invalid data.
	 */
	ContactInfo addContactInfo(ContactInfoRequest request, Long userId);

	/**
	 * Updates the contact information of a user.
	 * <p>
	 * This method takes the ID of the contact info and a {@code ContactInfoRequest} object
	 * containing the new contact information,
	 * and returns a {@code ContactInfoResponse} object containing the updated contact information.
	 * </p>
	 * @param id the ID of the contact info.
	 * @param request the {@code ContactInfoRequest} containing the new contact information.
	 * @return a {@code ContactInfoResponse} containing the updated contact information.
	 * @throws IllegalArgumentException if the id or request is null or contains invalid data.
	 */
	ContactInfoResponse updateContactInfo(Long id, ContactInfoRequest request);
}
