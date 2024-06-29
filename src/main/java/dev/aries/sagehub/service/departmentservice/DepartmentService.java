package dev.aries.sagehub.service.departmentservice;

import dev.aries.sagehub.dto.request.DepartmentRequest;
import dev.aries.sagehub.dto.response.DepartmentResponse;
import dev.aries.sagehub.dto.search.GetDepartmentsPage;

import org.springframework.data.domain.Page;

public interface DepartmentService {
	DepartmentResponse addDepartment(DepartmentRequest request);

	DepartmentResponse getDepartment(Long departmentId);

	Page<DepartmentResponse> getDepartments(GetDepartmentsPage request);

	DepartmentResponse updateDepartment(Long departmentId, DepartmentRequest request);

}
