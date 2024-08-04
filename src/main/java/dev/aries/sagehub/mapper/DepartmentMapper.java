package dev.aries.sagehub.mapper;

import java.util.ArrayList;

import dev.aries.sagehub.dto.response.DepartmentResponse;
import dev.aries.sagehub.model.Department;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DepartmentMapper {
	private final ProgramMapper programMapper;

	public DepartmentResponse toResponse(Department department) {
		DepartmentResponse.DepartmentResponseBuilder response = DepartmentResponse.builder();
		response.id(department.getId())
				.code(department.getCode())
				.name(department.getName())
				.status(department.getStatus().toString())
				.programs(new ArrayList<>());
		if (department.getPrograms() != null) {
			response.programs(department.getPrograms().stream()
						.map(programMapper::toProgramResponse)
						.toList());
		}
		return response.build();
	}

	public DepartmentResponse toPageResponse(Department department) {
		return DepartmentResponse.builder()
				.id(department.getId())
				.code(department.getCode())
				.name(department.getName())
				.status(department.getStatus().toString())
				.build();
	}

}
