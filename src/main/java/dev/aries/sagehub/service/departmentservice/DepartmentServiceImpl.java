package dev.aries.sagehub.service.departmentservice;

import java.util.List;

import dev.aries.sagehub.dto.request.DepartmentRequest;
import dev.aries.sagehub.dto.response.DepartmentResponse;
import dev.aries.sagehub.enums.Status;
import dev.aries.sagehub.mapper.DepartmentMapper;
import dev.aries.sagehub.model.Department;
import dev.aries.sagehub.repository.DepartmentRepository;
import dev.aries.sagehub.repository.ProgramRepository;
import dev.aries.sagehub.strategy.UpdateStrategy;
import dev.aries.sagehub.util.Checks;
import dev.aries.sagehub.util.Generators;
import dev.aries.sagehub.util.GlobalUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import static dev.aries.sagehub.constant.ExceptionConstants.NAME_EXISTS;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {
	private final DepartmentRepository departmentRepository;
	private final ProgramRepository programRepository;
	private final Generators generators;
	private final DepartmentMapper departmentMapper;
	private final Checks checks;
	private final GlobalUtil globalUtil;
	private static final String NAME = "Department";

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
	public List<DepartmentResponse> getAllDepartments() {
		List<Department> departments = departmentRepository.findAll();
		return departments.stream().map(departmentMapper::toResponse).toList();
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
