package dev.aries.sagehub.service.userinfoservice;

import dev.aries.sagehub.dto.request.UserInfoRequest;
import dev.aries.sagehub.dto.response.UserInfoResponse;
import dev.aries.sagehub.model.UserProfile;

/**
 * The {@code UserInfoService} provides methods for managing user information.
 * It includes functionality for retrieving, adding, and updating user information.
 * <p>
 * Implementations of this interface are responsible for handling the business logic
 * related to user information management.
 * </p>
 * @author Jacobs Agyei
 */
public interface UserInfoService {

	/**
	 * Retrieves the profile information of a user.
	 * <p>
	 * This method takes the ID of the user and returns a {@code UserInfoResponse}
	 * object containing the user's profile information.
	 * </p>
	 * @param userId the ID of the user.
	 * @return a {@code UserInfoResponse} containing the user's profile information.
	 * @throws IllegalArgumentException if the id is null or invalid.
	 */
	UserInfoResponse getUserInfo(Long userId);

	/**
	 * Adds new personal and emergency contact information for a user.
	 * <p>
	 * This method takes a {@code UserInfoRequest} object containing the new information,
	 * and the ID of the user, and returns a {@code UserInfoResponse} object containing the updated information.
	 * </p>
	 * @param request the {@code UserInfoRequest} object containing the new information.
	 * @param userId  the ID of the user.
	 * @return a {@code UserInfo} object containing the saved user information.
	 * @throws IllegalArgumentException if the request or userId is null or contains invalid data.
	 */
	UserProfile addUserInfo(UserInfoRequest request, Long userId);

	/**
	 * Updates the personal and emergency contact information of a user.
	 * <p>
	 * This method takes the ID of the user and a {@code UserInfoRequest} object containing the new information,
	 * and returns a {@code UserInfoResponse} object containing the updated information.
	 * </p>
	 * @param userId      the ID of the user.
	 * @param request the {@code UserInfoRequest} containing the new information.
	 * @return a {@code UserInfoResponse} containing the updated information.
	 * @throws IllegalArgumentException if the id or request is null or contains invalid data.
	 */
	UserInfoResponse updateUserInfo(Long userId, UserInfoRequest request);
}
