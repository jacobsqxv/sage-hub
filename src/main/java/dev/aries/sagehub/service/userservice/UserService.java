package dev.aries.sagehub.service.userservice;

import dev.aries.sagehub.dto.request.AddUserRequest;
import dev.aries.sagehub.dto.request.PasswordChangeRequest;
import dev.aries.sagehub.dto.response.BasicUserResponse;
import dev.aries.sagehub.dto.response.GenericResponse;


/**
 * The {@code UserService} interface provides methods for managing user-related operations.
 * It includes functionality for changing user passwords and adding faculty members.
 * <p>
 * Implementations of this interface are responsible for handling the business logic
 * related to user management within an educational institution.
 * </p>
 * @author Jacobs Agyei
 */
public interface UserService {

	/**
	 * Changes the password of an existing user.
	 * <p>
	 * This method takes a user's ID and a {@code PasswordChangeRequest} object,
	 * and updates the user's password accordingly.
	 * </p>
	 * @param id the {@code Long} ID of the user whose password is to be changed.
	 * @param request the {@code PasswordChangeRequest} containing the old and new passwords.
	 * @return a {@code GenericResponse} object containing the outcome of the operation.
	 * @throws IllegalArgumentException if the ID or request is null or invalid.
	 */
	GenericResponse changePassword(Long id, PasswordChangeRequest request);

	/**
	 * Adds a new faculty member.
	 * <p>
	 * This method takes an {@code AddUserRequest} object containing the new faculty member's information,
	 * and adds the new faculty member to the system.
	 * </p>
	 * @param request the {@code AddUserRequest} containing the user information and type of user to create.
	 * @return a {@code BasicUserResponse} containing the new faculty member's information.
	 * @throws IllegalArgumentException if the request or role is null or invalid.
	 */
	BasicUserResponse addFacultyMember(AddUserRequest request);

}
