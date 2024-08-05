package dev.aries.sagehub.service.courseservice;

import dev.aries.sagehub.dto.request.CourseRequest;
import dev.aries.sagehub.dto.response.CourseResponse;
import dev.aries.sagehub.dto.search.GetCoursesPage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The {@code CourseService} interface provides methods for managing courses.
 * It includes functionality for adding, retrieving, updating, and listing courses.
 * <p>
 * Implementations of this interface are responsible for handling the business logic
 * related to course management.
 * </p>
 * @author Jacobs Agyei
 */
public interface CourseService {

	/**
	 * Adds a new course.
	 * <p>
	 * This method takes a {@code CourseRequest} object containing the details of the course to be added,
	 * and returns a {@code CourseResponse} object containing the details of the newly added course.
	 * </p>
	 * @param request the {@code CourseRequest} containing the course information.
	 * @return a {@code CourseResponse} containing the new course's information.
	 * @throws IllegalArgumentException if the request is null or contains invalid data.
	 */
	CourseResponse addCourse(CourseRequest request);

	/**
	 * Retrieves the details of a course.
	 * <p>
	 * This method takes the ID of the course and returns a {@code CourseResponse} object containing the course's
	 * details.
	 * </p>
	 * @param courseId the ID of the course to be retrieved.
	 * @return a {@code CourseResponse} containing the course's information.
	 * @throws IllegalArgumentException if the courseId is null or invalid.
	 */
	CourseResponse getCourse(Long courseId);

	/**
	 * Retrieves a paginated list of courses.
	 * <p>
	 * This method takes a {@code GetCoursesPage} request object and a {@code Pageable} object,
	 * and returns a {@code Page} of {@code CourseResponse} objects containing the courses' details.
	 * </p>
	 * @param request the {@code GetCoursesPage} containing the pagination and filter information.
	 * @param pageable the {@code Pageable} object containing pagination information.
	 * @return a {@code Page} of {@code CourseResponse} objects containing the courses' information.
	 * @throws IllegalArgumentException if the request or pageable is null or contains invalid data.
	 */
	Page<CourseResponse> getCourses(GetCoursesPage request, Pageable pageable);

	/**
	 * Updates the details of a course.
	 * <p>
	 * This method takes the ID of the course and a {@code CourseRequest} object containing the updated details,
	 * and returns a {@code CourseResponse} object containing the updated course details.
	 * </p>
	 * @param id the ID of the course to be updated.
	 * @param request the {@code CourseRequest} containing the updated course information.
	 * @return a {@code CourseResponse} containing the updated course's information.
	 * @throws IllegalArgumentException if the id or request is null or contains invalid data.
	 */
	CourseResponse updateCourse(Long id, CourseRequest request);
}
