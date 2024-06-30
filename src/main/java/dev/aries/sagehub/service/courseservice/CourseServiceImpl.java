package dev.aries.sagehub.service.courseservice;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.request.CourseRequest;
import dev.aries.sagehub.dto.response.CourseResponse;
import dev.aries.sagehub.dto.search.GetCoursesPage;
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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
	private static final String NAME = "Course";
	private static final Integer OFFSET = 1;

	@Override
	public CourseResponse addCourse(CourseRequest request) {
		existsByName(request.name().toUpperCase());
		Course course = Course.builder()
				.name(request.name().toUpperCase())
				.code(this.generators.generateCourseCode(request.name()))
				.description(request.description())
				.creditUnits(request.creditUnits())
				.status(Status.PENDING_REVIEW)
				.build();
		this.courseRepository.save(course);
		return this.courseMapper.toCourseResponse(course);
	}

	@Override
	public CourseResponse getCourse(Long courseId) {
		Course course = this.globalUtil.loadCourse(courseId);
		return this.courseMapper.toCourseResponse(course);
	}

	@Override
	public Page<CourseResponse> getCourses(GetCoursesPage request) {
		if (this.checks.checkAdmin()) {
			return getAllCourses(request);
		}
		return getActiveCourses(request);
	}

	private Page<CourseResponse> getAllCourses(GetCoursesPage request) {
		return loadCourses(request, request.status());
	}

	private Page<CourseResponse> getActiveCourses(GetCoursesPage request) {
		return loadCourses(request, Status.ACTIVE.name());
	}

	private Page<CourseResponse> loadCourses(GetCoursesPage request, String status) {
		Integer page = request.page() - OFFSET;
		Integer size = request.size();
		Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
		return this.courseRepository.findAll(request.name(), request.code(), status, pageable)
				.map(this.courseMapper::toCourseResponse);
	}

	@Override
	public CourseResponse updateCourse(Long id, CourseRequest request) {
		this.checks.isAdmin();
		Course course = this.globalUtil.loadCourse(id);
		UpdateStrategy updateStrategy = this.globalUtil.checkStrategy("updateCourse");
		course = (Course) updateStrategy.update(course, request);
		this.courseRepository.save(course);
		log.info("INFO - Course {} updated successfully", course.getCode());
		return this.courseMapper.toCourseResponse(course);
	}

	private void existsByName(String name) {
		if (this.courseRepository.existsByName(name)) {
			throw new IllegalArgumentException(
					String.format(ExceptionConstants.NAME_EXISTS, NAME, name));
		}
	}
}
