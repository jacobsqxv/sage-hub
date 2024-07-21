package dev.aries.sagehub.service.userservice;

import dev.aries.sagehub.dto.request.AddUserRequest;
import dev.aries.sagehub.dto.request.PasswordChangeRequest;
import dev.aries.sagehub.dto.response.GenericResponse;
import dev.aries.sagehub.dto.response.UserResponse;

/**
 * UserService is an interface class that provides the contract for user management.
 * It provides methods for user actions, including changing passwords,
 * adding faculty members, and managing contact information.
 * @author Jacobs Agyei
 */
public interface UserService {
	/**
	 * Changes the password of a user.
	 * @param id      the ID of the user.
	 * @param request the request containing the old and new passwords.
	 * @return a BasicUserResponse containing the updated user information.
	 */
	GenericResponse changePassword(Long id, PasswordChangeRequest request);
	/**
	 * Adds a new faculty member.
	 * @param request the request containing the user information.
	 * @param role    the role of the new faculty member (either "STUDENT" or "STAFF").
	 * @return a BasicUserResponse containing the new faculty member's information.
	 */
	UserResponse addFacultyMember(AddUserRequest request, String role);

}
