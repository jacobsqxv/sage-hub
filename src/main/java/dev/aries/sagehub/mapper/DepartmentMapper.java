package dev.aries.sagehub.mapper;

import java.util.ArrayList;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.response.DepartmentResponse;
import dev.aries.sagehub.model.Department;

public final class DepartmentMapper {

	public static DepartmentResponse toResponse(Department department) {
		DepartmentResponse.DepartmentResponseBuilder response = DepartmentResponse.builder();
		response.id(department.getId())
				.code(department.getCode())
				.name(department.getName())
				.status(department.getStatus().toString())
				.programs(new ArrayList<>());
		if (department.getPrograms() != null) {
			response.programs(department.getPrograms().stream()
						.map(ProgramMapper::toResponse)
						.toList());
		}
		return response.build();
	}

	public static DepartmentResponse toPageResponse(Department department) {
		return DepartmentResponse.builder()
				.id(department.getId())
				.code(department.getCode())
				.name(department.getName())
				.status(department.getStatus().toString())
				.build();
	}

	private DepartmentMapper() {
		throw new IllegalStateException(ExceptionConstants.UTILITY_CLASS);
	}
}
