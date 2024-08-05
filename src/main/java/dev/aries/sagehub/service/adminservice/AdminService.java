package dev.aries.sagehub.service.adminservice;

import dev.aries.sagehub.dto.request.AdminRequest;
import dev.aries.sagehub.dto.response.AdminResponse;
/**
 The {@code AdminService} interface provides methods for managing admin users.
 * It includes functionality for adding new admins.
 * <p>
 * Implementations of this interface are responsible for handling the business logic
 * related to admin management.
 * </p>
 * @author Jacobs Agyei
 */
public interface AdminService {
	/**
	 * Adds a new admin.
	 * <p>
	 * This method takes an {@code AdminRequest} object containing the details of the admin to be added,
	 * and returns an {@code AdminResponse} object containing the details of the newly added admin.
	 * </p>
	 * @param request the {@code AdminRequest} containing the admin information.
	 * @return an {@code AdminResponse} containing the new admin's information.
	 * @throws IllegalArgumentException if the request is null or contains invalid data.
	 */
	AdminResponse addAdmin(AdminRequest request);

}
