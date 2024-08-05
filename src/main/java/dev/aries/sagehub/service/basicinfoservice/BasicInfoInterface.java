package dev.aries.sagehub.service.basicinfoservice;

import dev.aries.sagehub.dto.request.BasicInfoRequest;
import dev.aries.sagehub.dto.response.BasicInfoResponse;
import dev.aries.sagehub.model.BasicInfo;

/**
 * The {@code BasicInfoInterface} provides methods for managing basic user information.
 * It includes functionality for retrieving, adding, and updating basic information.
 * <p>
 * Implementations of this interface are responsible for handling the business logic
 * related to basic user information management.
 * </p>
 * @author Jacobs Agyei
 */
public interface BasicInfoInterface {

	/**
	 * Retrieves the basic information of a user.
	 * <p>
	 * This method takes the ID of the user and returns a {@code BasicInfoResponse}
	 * object containing the user's basic information.
	 * </p>
	 * @param id the ID of the user.
	 * @return a {@code BasicInfoResponse} containing the user's basic information.
	 * @throws IllegalArgumentException if the id is null or invalid.
	 */
	BasicInfoResponse getBasicInfo(Long id);

	/**
	 * Adds new basic information for a user.
	 * <p>
	 * This method takes a {@code BasicInfoRequest} object containing the new information,
	 * and the ID of the user, and returns a {@code BasicInfo} object containing the updated information.
	 * </p>
	 * @param request the {@code BasicInfoRequest} containing the new information.
	 * @param userId  the ID of the user.
	 * @return a {@code BasicInfo} containing the updated information.
	 * @throws IllegalArgumentException if the request or userId is null or contains invalid data.
	 */
	BasicInfo addBasicInfo(BasicInfoRequest request, Long userId);

	/**
	 * Updates the basic information of a user.
	 * <p>
	 * This method takes the ID of the user and a {@code BasicInfoRequest} object containing the new information,
	 * and returns a {@code BasicInfoResponse} object containing the updated basic information.
	 * </p>
	 * @param id      the ID of the user.
	 * @param request the {@code BasicInfoRequest} containing the new information.
	 * @return a {@code BasicInfoResponse} containing the updated basic information.
	 * @throws IllegalArgumentException if the id or request is null or contains invalid data.
	 */
	BasicInfoResponse updateBasicInfo(Long id, BasicInfoRequest request);
}
