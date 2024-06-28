package dev.aries.sagehub.mapper;

import dev.aries.sagehub.dto.response.ProgramCourseResponse;
import dev.aries.sagehub.model.ProgramCourse;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProgramCourseMapper {
	private final ProgramMapper programMapper;
	private final CourseMapper courseMapper;

	public ProgramCourseResponse toProgramCourseResponse(ProgramCourse programCourse) {
		return new ProgramCourseResponse(
				programMapper.toProgramResponse(programCourse.getProgram()),
				programCourse.getCourses().stream()
						.map(courseMapper::toCourseResponse)
						.toList(),
				programCourse.getYear().toString(),
				programCourse.getSemester().toString(),
				programCourse.getStatus().getValue()
		);
	}
}
