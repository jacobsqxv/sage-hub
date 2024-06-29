package dev.aries.sagehub.service.departmentservice;

import dev.aries.sagehub.dto.request.DepartmentRequest;
import dev.aries.sagehub.dto.response.DepartmentResponse;
import dev.aries.sagehub.dto.search.GetDepartmentsPage;
import dev.aries.sagehub.enums.Status;
import dev.aries.sagehub.mapper.DepartmentMapper;
import dev.aries.sagehub.model.Department;
import dev.aries.sagehub.repository.DepartmentRepository;
import dev.aries.sagehub.strategy.UpdateStrategy;
import dev.aries.sagehub.util.Checks;
import dev.aries.sagehub.util.Generators;
import dev.aries.sagehub.util.GlobalUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import static dev.aries.sagehub.constant.ExceptionConstants.NAME_EXISTS;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {
	private final DepartmentRepository departmentRepository;
	private final Generators generators;
	private final DepartmentMapper departmentMapper;
	private final Checks checks;
	private final GlobalUtil globalUtil;
	private static final String NAME = "Department";
	private static final Integer OFFSET = 1;

	@Override
	public DepartmentResponse addDepartment(DepartmentRequest request) {
		existsByName(request.name());
		Department department = Department.builder()
				.name(request.name())
				.code(generators.generateDeptCode())
				.status(Status.PENDING_REVIEW)
				.build();
		departmentRepository.save(department);
		log.info("INFO - Department {} added successfully", department.getCode());
		return departmentMapper.toResponse(department);
	}

	@Override
	public DepartmentResponse getDepartment(Long departmentId) {
		Department department = this.globalUtil.loadDepartment(departmentId);
		return departmentMapper.toResponse(department);
	}

	@Override
	public Page<DepartmentResponse> getDepartments(GetDepartmentsPage request) {
		if (this.checks.checkAdmin()) {
			return getAllDepartments(request);
		}
		return getActiveDepartments(request);
	}

	private Page<DepartmentResponse> getActiveDepartments(GetDepartmentsPage request) {
		return loadDepartments(request, Status.ACTIVE.name());
	}

	private Page<DepartmentResponse> getAllDepartments(GetDepartmentsPage request) {
		return loadDepartments(request, request.status());
	}

	private Page<DepartmentResponse> loadDepartments(GetDepartmentsPage request, String status) {
		Integer page = request.page() - OFFSET;
		Integer size = request.size();
		Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
		return departmentRepository.findAll(request.name(), request.code(), status, pageable)
				.map(departmentMapper::toResponse);
	}

	@Override
	public DepartmentResponse updateDepartment(Long departmentId, DepartmentRequest request) {
		this.checks.isAdmin();
		Department department = this.globalUtil.loadDepartment(departmentId);
		UpdateStrategy updateStrategy = this.globalUtil.checkStrategy("updateDepartment");
		department = (Department) updateStrategy.update(department, request);
		this.departmentRepository.save(department);
		log.info("INFO - Department {} updated successfully", department.getCode());
		return departmentMapper.toResponse(department);
	}

	private void existsByName(String name) {
		if (departmentRepository.existsByName(name)) {
			throw new IllegalArgumentException(String.format(NAME_EXISTS, NAME, name));
		}
	}
}
