package dev.aries.sagehub.service.userprofileservice;

import dev.aries.sagehub.dto.request.UserProfileRequest;
import dev.aries.sagehub.dto.response.UserProfileResponse;
import dev.aries.sagehub.model.UserProfile;

/**
 * The {@code UserProfileService} provides methods for managing user profile information.
 * It includes functionality for retrieving, adding, and updating user profile information.
 * <p>
 * Implementations of this interface are responsible for handling the business logic
 * related to user information management.
 * </p>
 * @author Jacobs Agyei
 */
public interface UserProfileService {

	/**
	 * Retrieves the profile information of a user.
	 * <p>
	 * This method takes the ID of the user and returns a {@code UserProfileResponse}
	 * object containing the user's profile information.
	 * </p>
	 * @param userId the ID of the user.
	 * @return a {@code UserProfileResponse} containing the user's profile information.
	 * @throws IllegalArgumentException if the id is null or invalid.
	 */
	UserProfileResponse getUserProfile(Long userId);

	/**
	 * Adds new personal and emergency contact information for a user.
	 * <p>
	 * This method takes a {@code UserProfileRequest} object containing the new information,
	 * and the ID of the user, and returns a {@code UserProfileResponse} object containing the updated information.
	 * </p>
	 * @param request the {@code UserProfileRequest} object containing the new information.
	 * @param userId  the ID of the user.
	 * @return a {@code UserInfo} object containing the saved user information.
	 * @throws IllegalArgumentException if the request or userId is null or contains invalid data.
	 */
	UserProfile addUserProfile(UserProfileRequest request, Long userId);

	/**
	 * Updates the personal and emergency contact information of a user.
	 * <p>
	 * This method takes the ID of the user and a {@code UserProfileRequest} object containing the new information,
	 * and returns a {@code UserProfileResponse} object containing the updated information.
	 * </p>
	 * @param userId      the ID of the user.
	 * @param request the {@code UserProfileRequest} containing the new information.
	 * @return a {@code UserProfileResponse} containing the updated information.
	 * @throws IllegalArgumentException if the id or request is null or contains invalid data.
	 */
	UserProfileResponse updateUserProfile(Long userId, UserProfileRequest request);
}
