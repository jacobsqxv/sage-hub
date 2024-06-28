package dev.aries.sagehub.mapper;

import dev.aries.sagehub.dto.response.wrapper.BasicDepartmentResponse;
import dev.aries.sagehub.dto.response.wrapper.DepartmentProgramResponse;
import dev.aries.sagehub.dto.response.DepartmentResponse;
import dev.aries.sagehub.model.Department;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DepartmentMapper {
	private final ProgramCourseMapper programCourseMapper;

	public DepartmentResponse toResponse(Department department) {
		if (department.getPrograms() == null){
			return new DepartmentResponse(toBasicDepartmentResponse(department));
		}
		return new DepartmentResponse(toDepartmentProgramResponse(department));
	}

	private DepartmentProgramResponse toDepartmentProgramResponse(Department department) {
		return new DepartmentProgramResponse(
				department.getId(),
				department.getCode(),
				department.getName(),
				department.getPrograms().stream()
						.map(programCourseMapper::toProgramCourseResponse)
						.toList(),
				department.getStatus().getValue()
		);
	}

	public BasicDepartmentResponse toBasicDepartmentResponse(Department department) {
		return new BasicDepartmentResponse(
				department.getId(),
				department.getCode(),
				department.getName(),
				"No programs offered in this department",
				department.getStatus().getValue()
		);
	}
}
