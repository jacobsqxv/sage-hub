package dev.aries.sagehub.strategy;

import java.util.Optional;

import dev.aries.sagehub.dto.request.ProgramCourseRequest;
import dev.aries.sagehub.enums.Semester;
import dev.aries.sagehub.enums.Year;
import dev.aries.sagehub.model.AcademicPeriod;
import dev.aries.sagehub.model.Course;
import dev.aries.sagehub.model.ProgramCourse;
import dev.aries.sagehub.repository.CourseRepository;
import dev.aries.sagehub.util.Checks;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import static dev.aries.sagehub.constant.ExceptionConstants.NOT_FOUND;

@Component
@RequiredArgsConstructor
public class UpdateProgramCourse implements UpdateStrategy<ProgramCourse, ProgramCourseRequest> {
	private final CourseRepository courseRepository;

	@Override
	public ProgramCourse update(ProgramCourse entity, ProgramCourseRequest request) {
		Checks.checkIfEnumExists(Year.class, String.valueOf(request.period().year()));
		Checks.checkIfEnumExists(Semester.class, String.valueOf(request.period().semester()));
		entity.setAcademicPeriod(new AcademicPeriod(
				request.period().year(),
				request.period().semester()));
		Optional.ofNullable(request.status()).ifPresent(entity::setStatus);
		Course course = checkCourses(request.courseId());
		entity.setCourse(course);
		return entity;
	}

	private Course checkCourses(Long id) {
		return courseRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(String.format(NOT_FOUND, "Course")));
	}
}
