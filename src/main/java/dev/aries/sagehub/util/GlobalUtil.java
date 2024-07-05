package dev.aries.sagehub.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.model.AcademicPeriod;
import dev.aries.sagehub.model.Course;
import dev.aries.sagehub.model.Department;
import dev.aries.sagehub.model.Program;
import dev.aries.sagehub.model.ProgramCourse;
import dev.aries.sagehub.repository.CourseRepository;
import dev.aries.sagehub.repository.DepartmentRepository;
import dev.aries.sagehub.repository.ProgramCourseRepository;
import dev.aries.sagehub.repository.ProgramRepository;
import dev.aries.sagehub.strategy.UpdateStrategy;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import static dev.aries.sagehub.constant.ExceptionConstants.NOT_FOUND;
import static dev.aries.sagehub.constant.ExceptionConstants.NO_UPDATE_STRATEGY;

@Component
@RequiredArgsConstructor
@Slf4j
public class GlobalUtil {

	private final Map<String, UpdateStrategy> updateStrategies;
	private final DepartmentRepository departmentRepository;
	private final ProgramRepository programRepository;
	private final ProgramCourseRepository programCourseRepository;
	private final CourseRepository courseRepository;

	public UpdateStrategy checkStrategy(String type) {
		if (this.updateStrategies.get(type) == null) {
			throw new IllegalArgumentException(String.format(NO_UPDATE_STRATEGY, type));
		}
		return this.updateStrategies.get(type);
	}

	public Department loadDepartment(Long id) {
		return this.departmentRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(
						String.format(NOT_FOUND, "Department")));
	}

	public Program loadProgram(Long programId) {
		return this.programRepository.findById(programId)
				.orElseThrow(() -> new EntityNotFoundException(
						String.format(ExceptionConstants.NOT_FOUND, "Program")));
	}

	public ProgramCourse loadProgramCourses(Long programId, Long courseId, AcademicPeriod period) {
		return this.programCourseRepository
				.findByProgramIdAndCourseIdAndAcademicPeriod(programId, courseId, period);
	}

	public Course loadCourse(Long courseId) {
		return this.courseRepository.findById(courseId)
				.orElseThrow(() -> new IllegalArgumentException(
						String.format(ExceptionConstants.NOT_FOUND, "Course")));
	}

	public String formatDateTime(LocalDateTime dateTime) {
		int day = dateTime.getDayOfMonth();
		String ordinalIndicator = getOrdinalIndicator(day);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
				"d'" + ordinalIndicator + "' MMMM, uuuu hh:mma", Locale.ENGLISH);
		return dateTime.format(formatter);
	}

	private static String getOrdinalIndicator(int day) {
		if (day >= 11 && day <= 13) {
			return "th";
		}
		return switch (day % 10) {
			case 1 -> "st";
			case 2 -> "nd";
			case 3 -> "rd";
			default -> "th";
		};
	}
}
