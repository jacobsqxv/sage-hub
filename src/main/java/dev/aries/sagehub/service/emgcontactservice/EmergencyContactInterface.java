package dev.aries.sagehub.service.emgcontactservice;

import dev.aries.sagehub.dto.request.EmergencyContactRequest;
import dev.aries.sagehub.dto.response.EmergencyContactResponse;
import dev.aries.sagehub.model.EmergencyContact;

public interface EmergencyContactInterface {
	/**
	 * Retrieves the emergency contact information of a user.
	 * @param id the ID of the emergency contact info.
	 * @return a EmergencyContactResponse containing the user's emergency contact information.
	 */
	EmergencyContactResponse getEmergencyContact(Long id);

	/**
	 * Adds new emergency contact information for a user.
	 * @param request the request containing the new emergency contact information.
	 * @return a EmergencyContact containing the updated emergency contact information.
	 */
	EmergencyContact addEmergencyContact(EmergencyContactRequest request);

	/**
	 * Updates the emergency contact information of a user.
	 * @param id      the ID of the emergency contact info.
	 * @param request the request containing the new emergency contact information.
	 * @return a EmergencyContact containing the updated emergency contact information.
	 */
	EmergencyContactResponse updateEmergencyContact(Long id, EmergencyContactRequest request);
}
