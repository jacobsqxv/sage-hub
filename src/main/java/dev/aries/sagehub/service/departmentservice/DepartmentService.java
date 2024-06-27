package dev.aries.sagehub.service.departmentservice;

import dev.aries.sagehub.dto.request.DepartmentRequest;
import dev.aries.sagehub.dto.response.DepartmentResponse;

public interface DepartmentService {
	DepartmentResponse addDepartment(DepartmentRequest request);

	DepartmentResponse getDepartment(Long departmentId);

	DepartmentResponse getAllDepartments();

	DepartmentResponse updateDepartment(Long departmentId, DepartmentRequest request);

	DepartmentResponse archiveDepartment(Long departmentId);
}
