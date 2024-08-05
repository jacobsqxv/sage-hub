package dev.aries.sagehub.mapper;

import dev.aries.sagehub.dto.response.ProgramCourseResponse;
import dev.aries.sagehub.model.ProgramCourse;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProgramCourseMapper {
	private final CourseMapper courseMapper;

	public ProgramCourseResponse toProgramCourseResponse(ProgramCourse programCourse) {
		return new ProgramCourseResponse(
				programCourse.getId(),
				courseMapper.toCourseResponse(programCourse.getCourse()),
				programCourse.getAcademicPeriod().year().toString(),
				programCourse.getAcademicPeriod().semester().toString(),
				programCourse.getStatus().toString()
		);
	}
}
