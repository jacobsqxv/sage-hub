package dev.aries.sagehub.service.contactinfoservice;

import dev.aries.sagehub.dto.request.ContactInfoRequest;
import dev.aries.sagehub.dto.response.ContactInfoResponse;
import dev.aries.sagehub.model.ContactInfo;

public interface ContactInfoInterface {
	/**
	 * Retrieves the contact information of a user.
	 * @param id the ID of the contact info.
	 * @return a ContactInfoResponse containing the user's contact information.
	 */
	ContactInfoResponse getContactInfo(Long id);
	/**
	 * Adds new contact information for a user.
	 * @param request the request containing the new contact information.
	 * @param userId  the ID of the user.
	 * @return a ContactInfo containing the updated contact information.
	 */
	ContactInfo addContactInfo(ContactInfoRequest request, Long userId);
	/**
	 * Updates the contact information of a user.
	 * @param id      the ID of the contact info.
	 * @param request the request containing the new contact information.
	 * @return a ContactInfoResponse containing the updated contact information.
	 */
	ContactInfoResponse updateContactInfo(Long id, ContactInfoRequest request);
}
