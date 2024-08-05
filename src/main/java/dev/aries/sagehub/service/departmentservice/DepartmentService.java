package dev.aries.sagehub.service.departmentservice;

import dev.aries.sagehub.dto.request.DepartmentRequest;
import dev.aries.sagehub.dto.response.DepartmentResponse;
import dev.aries.sagehub.dto.search.GetDepartmentsPage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The {@code DepartmentService} interface provides methods for managing departments.
 * It includes functionality for adding, retrieving, updating, and listing departments.
 * <p>
 * Implementations of this interface are responsible for handling the business logic
 * related to department management.
 * </p>
 * @author Jacobs Agyei
 */
public interface DepartmentService {

	/**
	 * Adds a new department.
	 * <p>
	 * This method takes a {@code DepartmentRequest} object containing the details of the department to be added,
	 * and returns a {@code DepartmentResponse} object containing the details of the newly added department.
	 * </p>
	 * @param request the {@code DepartmentRequest} containing the department information.
	 * @return a {@code DepartmentResponse} containing the new department's information.
	 * @throws IllegalArgumentException if the request is null or contains invalid data.
	 */
	DepartmentResponse addDepartment(DepartmentRequest request);

	/**
	 * Retrieves the details of a department.
	 * <p>
	 * This method takes the ID of the department and returns a {@code DepartmentResponse} object
	 * containing the department's details.
	 * </p>
	 * @param departmentId the ID of the department to be retrieved.
	 * @return a {@code DepartmentResponse} containing the department's information.
	 * @throws IllegalArgumentException if the departmentId is null or invalid.
	 */
	DepartmentResponse getDepartment(Long departmentId);

	/**
	 * Retrieves a paginated list of departments.
	 * <p>
	 * This method takes a {@code GetDepartmentsPage} request object and a {@code Pageable} object,
	 * and returns a {@code Page} of {@code DepartmentResponse} objects containing the departments' details.
	 * </p>
	 * @param request the {@code GetDepartmentsPage} containing the pagination and filter information.
	 * @param pageable the {@code Pageable} object containing pagination information.
	 * @return a {@code Page} of {@code DepartmentResponse} objects containing the departments' information.
	 * @throws IllegalArgumentException if the request or pageable is null or contains invalid data.
	 */
	Page<DepartmentResponse> getDepartments(GetDepartmentsPage request, Pageable pageable);

	/**
	 * Updates the details of a department.
	 * <p>
	 * This method takes the ID of the department and a {@code DepartmentRequest} object containing the
	 * updated details, and returns a {@code DepartmentResponse} object containing the updated department details.
	 * </p>
	 * @param departmentId the ID of the department to be updated.
	 * @param request the {@code DepartmentRequest} containing the updated department information.
	 * @return a {@code DepartmentResponse} containing the updated department's information.
	 * @throws IllegalArgumentException if the departmentId or request is null or contains invalid data.
	 */
	DepartmentResponse updateDepartment(Long departmentId, DepartmentRequest request);
}
