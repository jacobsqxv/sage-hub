package dev.aries.sagehub.service.programservice;

import dev.aries.sagehub.dto.request.ProgramCourseRequest;
import dev.aries.sagehub.dto.response.ProgramCourseResponse;
import dev.aries.sagehub.dto.search.GetPrgCoursesPage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The {@code ProgramCourseService} interface provides methods for managing courses offered
 * in a program in a given period.
 * It includes functionality for adding, retrieving, and deleting courses in a period.
 * <p>
 * Implementations of this interface are responsible for handling the business logic
 * related to program course management.
 * </p>
 * @author Jacobs Agyei
 */
public interface ProgramCourseService {
	/**
	 * Retrieves a paginated list of programs courses.
	 * <p>
	 * This method takes the ID of the program, a {@code GetPrgCoursesPage} request object and
	 * a {@code Pageable} object, and returns a {@code Page} of {@code ProgramCourseResponse} objects
	 * containing the programs courses' details.
	 * </p>
	 * @param programId the ID of the program whose courses are to be retrieved.
	 * @param request the {@code GetPrgCoursesPage} containing the pagination and filter information.
	 * @param pageable the {@code Pageable} object containing pagination information.
	 * @return a {@code Page} of {@code ProgramCourseResponse} objects containing the programs courses' information.
	 * @throws IllegalArgumentException if the request or pageable is null or contains invalid data.
	 */
	Page<ProgramCourseResponse> getProgramCourses(Long programId, GetPrgCoursesPage request, Pageable pageable);

	/**
	 * Adds a new course configuration to a program
	 * <p>
	 * This method takes the ID of the program and a {@code ProgramCourseRequest} object containing the academic
	 * period the course is taken in, and returns a {@code ProgramCourseResponse} object containing
	 * the updated program courses.
	 * </p>
	 * @param programId the ID of the program whose courses are to be updated.
	 * @param request the {@code ProgramCourseRequest} containing the academic period the course is taken in.
	 * @return a {@code ProgramCourseResponse} containing the updated program courses.
	 * @throws IllegalArgumentException if the programId or request is null or contains invalid data.
	 */
	ProgramCourseResponse addProgramCourse(Long programId, ProgramCourseRequest request);

	/**
	 * Deletes a course configuration for a program
	 * <p>
	 * This method takes the ID of the program and the ID of the course configuration
	 * to be deleted, and returns a {@code 204} response code signaling
	 * the success of the operation.
	 * </p>
	 * @param programId the ID of the program whose courses are to be updated.
	 * @param id the id if the program course configuration to be deleted.
	 * @throws IllegalArgumentException if the programId or request is null or contains invalid data.
	 */
	void deleteCourseConfig(Long programId, Long id);
}
