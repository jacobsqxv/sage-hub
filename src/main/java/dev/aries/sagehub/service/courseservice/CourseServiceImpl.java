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
import dev.aries.sagehub.strategy.UpdateStrategy;
import dev.aries.sagehub.util.Checks;
import dev.aries.sagehub.util.Generators;
import dev.aries.sagehub.util.GlobalUtil;
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
	private final Generators generators;
	private final CourseRepository courseRepository;
	private final CourseMapper courseMapper;
	private final GlobalUtil globalUtil;
	private final Checks checks;
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
		return courseMapper.toCourseResponse(course);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CourseResponse getCourse(Long courseId) {
		Course course = globalUtil.loadCourse(courseId);
		return courseMapper.toCourseResponse(course);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<CourseResponse> getCourses(GetCoursesPage request, Pageable pageable) {
		User loggedInUser = checks.currentlyLoggedInUser();
		if (checks.isAdmin(loggedInUser.getRole().getName())) {
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
		return courseRepository.findAll(request.name(), request.code(), status, pageable)
				.map(courseMapper::toCourseResponse);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CourseResponse updateCourse(Long id, CourseRequest request) {
		User loggedInUser = checks.currentlyLoggedInUser();
		checks.checkAdmins(loggedInUser.getRole().getName());
		Course course = globalUtil.loadCourse(id);
		UpdateStrategy updateStrategy = globalUtil.checkStrategy("updateCourse");
		course = (Course) updateStrategy.update(course, request);
		courseRepository.save(course);
		log.info(" Course {} updated successfully", course.getCode());
		return courseMapper.toCourseResponse(course);
	}

	private void existsByName(String name) {
		if (courseRepository.existsByName(name)) {
			throw new IllegalArgumentException(
					String.format(ExceptionConstants.NAME_EXISTS, NAME, name));
		}
	}
}
