package dev.aries.sagehub.service.courseservice;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.request.CourseRequest;
import dev.aries.sagehub.dto.response.CourseResponse;
import dev.aries.sagehub.dto.search.GetCoursesPage;
import dev.aries.sagehub.enums.Status;
import dev.aries.sagehub.mapper.CourseMapper;
import dev.aries.sagehub.model.Course;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.repository.CourseRepository;
import dev.aries.sagehub.strategy.UpdateStrategyConfig;
import dev.aries.sagehub.util.Checks;
import dev.aries.sagehub.util.DataLoader;
import dev.aries.sagehub.util.Generators;
import dev.aries.sagehub.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
/**
 * Implementation of the {@code CourseService} interface.
 * @author Jacobs Agyei
 * @see dev.aries.sagehub.service.courseservice.CourseService
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
	private final UpdateStrategyConfig updateStrategyConfig;
	private final CourseRepository courseRepository;
	private final Generators generators;
	private final DataLoader dataLoader;
	private final UserUtil userUtil;
	private static final String NAME = "Course";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CourseResponse addCourse(CourseRequest request) {
		existsByName(request.name().toUpperCase());
		Course course = Course.builder()
				.name(request.name().toUpperCase())
				.code(generators.generateCourseCode(request.name()))
				.description(request.description())
				.creditUnits(request.creditUnits())
				.status(Status.PENDING)
				.build();
		courseRepository.save(course);
		return CourseMapper.toCourseResponse(course);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CourseResponse getCourse(Long courseId) {
		Course course = dataLoader.loadCourse(courseId);
		return CourseMapper.toCourseResponse(course);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<CourseResponse> getCourses(GetCoursesPage request, Pageable pageable) {
		if (request.status() != null) {
			Checks.checkIfEnumExists(Status.class, request.status());
		}
		User loggedInUser = userUtil.currentlyLoggedInUser();
		if (Checks.isAdmin(loggedInUser.getRole().getName())) {
			return getAllCourses(request, pageable);
		}
		return getActiveCourses(request, pageable);
	}

	private Page<CourseResponse> getAllCourses(GetCoursesPage request, Pageable pageable) {
		return loadCourses(request, request.status(), pageable);
	}

	private Page<CourseResponse> getActiveCourses(GetCoursesPage request, Pageable pageable) {
		return loadCourses(request, Status.ACTIVE.name(), pageable);
	}

	private Page<CourseResponse> loadCourses(GetCoursesPage request, String status, Pageable pageable) {
		return courseRepository.findAll(request, status, pageable)
				.map(CourseMapper::toCourseResponse);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CourseResponse updateCourse(Long id, CourseRequest request) {
		User loggedInUser = userUtil.currentlyLoggedInUser();
		Checks.checkAdmins(loggedInUser.getRole().getName());
		Course course = dataLoader.loadCourse(id);
		Course updatedCourse = (Course) updateStrategyConfig
				.checkStrategy("Course")
				.update(course, request);
		courseRepository.save(updatedCourse);
		log.info(" Course {} updated successfully", updatedCourse.getCode());
		return CourseMapper.toCourseResponse(updatedCourse);
	}

	private void existsByName(String name) {
		if (courseRepository.existsByName(name)) {
			throw new IllegalArgumentException(
					String.format(ExceptionConstants.NAME_EXISTS, NAME, name));
		}
	}
}
