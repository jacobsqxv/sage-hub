package dev.aries.sagehub.service.courseservice;

import java.util.List;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.request.CourseRequest;
import dev.aries.sagehub.dto.response.CourseResponse;
import dev.aries.sagehub.enums.Status;
import dev.aries.sagehub.mapper.CourseMapper;
import dev.aries.sagehub.model.Course;
import dev.aries.sagehub.repository.CourseRepository;
import dev.aries.sagehub.strategy.UpdateStrategy;
import dev.aries.sagehub.util.Checks;
import dev.aries.sagehub.util.Generators;
import dev.aries.sagehub.util.GlobalUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
	private final Generators generators;
	private final CourseRepository courseRepository;
	private final CourseMapper courseMapper;
	private final GlobalUtil globalUtil;
	private final Checks checks;
	public static final String NAME = "Course";

	@Override
	public CourseResponse addCourse(CourseRequest request) {
		existsByName(request.name().toUpperCase());
		Course course = Course.builder()
				.name(request.name().toUpperCase())
				.code(generators.generateCourseCode(request.name()))
				.description(request.description())
				.creditUnits(request.creditUnits())
				.status(Status.PENDING_REVIEW)
				.build();
		this.courseRepository.save(course);
		return this.courseMapper.toCourseResponse(course);
	}

	@Override
	public CourseResponse getCourse(Long courseId) {
		Course course = loadCourse(courseId);
		return this.courseMapper.toCourseResponse(course);
	}

	@Override
	public List<CourseResponse> getCourses() {
		List<Course> courses = this.courseRepository.findAll();
		return courses.stream().map(this.courseMapper::toCourseResponse).toList();
	}

	@Override
	public CourseResponse updateCourse(Long id, CourseRequest request) {
		this.checks.isAdmin();
		Course course = loadCourse(id);
		UpdateStrategy updateStrategy = globalUtil.checkStrategy("updateCourse");
		course = (Course) updateStrategy.update(course, request);
		this.courseRepository.save(course);
		log.info("INFO - Course {} updated successfully", course.getCode());
		return this.courseMapper.toCourseResponse(course);
	}

	@Override
	public CourseResponse archiveCourse(Long courseId) {
		this.checks.isAdmin();
		Course course = loadCourse(courseId);
		course.setStatus(Status.ARCHIVED);
		this.courseRepository.save(course);
		return this.courseMapper.toCourseResponse(course);
	}

	private Course loadCourse(Long courseId) {
		return this.courseRepository.findById(courseId)
				.orElseThrow(() -> new IllegalArgumentException(
						String.format(ExceptionConstants.NOT_FOUND, NAME)));
	}

	private void existsByName(String name) {
		if (courseRepository.existsByName(name)) {
			throw new IllegalArgumentException(
					String.format(ExceptionConstants.NAME_EXISTS, NAME, name));
		}
	}
}
