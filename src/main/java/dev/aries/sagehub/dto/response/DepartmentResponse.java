package dev.aries.sagehub.dto.response;

import dev.aries.sagehub.dto.response.wrapper.BasicDepartmentResponse;
import dev.aries.sagehub.dto.response.wrapper.DepartmentProgramResponse;
import lombok.Getter;


@Getter
public class DepartmentResponse {
	private final Object department;

	public DepartmentResponse(BasicDepartmentResponse basicDepartmentResponse) {
		this.department = basicDepartmentResponse;
	}

	public DepartmentResponse(DepartmentProgramResponse departmentProgramResponse) {
		this.department = departmentProgramResponse;
	}
}
