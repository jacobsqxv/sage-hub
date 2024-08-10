package dev.aries.sagehub.service.emergencyinfoservice;

import dev.aries.sagehub.dto.request.EmergencyInfoRequest;
import dev.aries.sagehub.dto.response.EmergencyInfoResponse;
import dev.aries.sagehub.model.EmergencyInfo;

/**
 * The {@code EmergencyInfoService} provides methods for managing emergency contact information.
 * It includes functionality for retrieving, adding, and updating emergency contact information.
 * <p>
 * Implementations of this interface are responsible for handling the business logic
 * related to emergency contact information management.
 * </p>
 * @author Jacobs Agyei
 */
public interface EmergencyInfoService {

	/**
	 * Retrieves the emergency contact information of a user.
	 * <p>
	 * This method takes the ID of the emergency contact info and returns a {@code EmergencyInfoResponse} object
	 * containing the user's emergency contact information.
	 * </p>
	 * @param id the ID of the emergency contact info.
	 * @return a {@code EmergencyInfoResponse} containing the user's emergency contact information.
	 * @throws IllegalArgumentException if the id is null or invalid.
	 */
	EmergencyInfoResponse getEmergencyInfo(Long id);

	/**
	 * Adds new emergency contact information for a user.
	 * <p>
	 * This method takes an {@code EmergencyInfoRequest} object containing the new emergency contact information,
	 * and the ID of the user, and returns an {@code EmergencyInfo} object
	 * containing the updated emergency contact information.
	 * </p>
	 * @param request the {@code EmergencyInfoRequest} containing the new emergency contact information.
	 * @param userId the ID of the user.
	 * @return an {@code EmergencyInfo} containing the saved emergency contact information.
	 * @throws IllegalArgumentException if the request or userId is null or contains invalid data.
	 */
	EmergencyInfo addEmergencyInfo(EmergencyInfoRequest request, Long userId);

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
	EmergencyInfoResponse updateEmergencyInfo(Long id, EmergencyInfoRequest request);
}
