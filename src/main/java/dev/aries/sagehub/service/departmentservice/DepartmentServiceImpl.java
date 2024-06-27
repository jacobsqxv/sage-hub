package dev.aries.sagehub.service.departmentservice;

import java.util.List;
import java.util.Map;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.request.DepartmentRequest;
import dev.aries.sagehub.dto.response.DepartmentResponse;
import dev.aries.sagehub.enums.Status;
import dev.aries.sagehub.mapper.DepartmentMapper;
import dev.aries.sagehub.model.Department;
import dev.aries.sagehub.repository.DepartmentRepository;
import dev.aries.sagehub.repository.ProgramCourseRepository;
import dev.aries.sagehub.strategy.UpdateStrategy;
import dev.aries.sagehub.util.Generators;
import dev.aries.sagehub.util.UserUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {
	private final DepartmentRepository departmentRepository;
	private final ProgramCourseRepository programCourseRepository;
	private final Generators generators;
	private final DepartmentMapper departmentMapper;
	private final Map<String, UpdateStrategy> updateStrategies;
	private final UserUtil userUtil;

	@Override
	public DepartmentResponse addDepartment(DepartmentRequest request) {
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
		Department department = loadDepartment(departmentId);
		return departmentMapper.toResponse(department);
	}

	@Override
	public DepartmentResponse getAllDepartments() {
		List<Department> departments = departmentRepository.findAll();
		return departments.stream().map(departmentMapper::toResponse).findFirst()
				.orElseThrow(() -> new EntityNotFoundException(ExceptionConstants.NO_DEPARTMENT_FOUND));
	}

	@Override
	public DepartmentResponse updateDepartment(Long departmentId, DepartmentRequest request) {
		this.userUtil.isAdmin();
		Department department = loadDepartment(departmentId);
		UpdateStrategy updateStrategy = updateStrategies.get("Department");
		department = (Department) updateStrategy.update(department, request);
		this.departmentRepository.save(department);
		log.info("INFO - Department {} updated successfully", department.getCode());
		return departmentMapper.toResponse(department);
	}

	@Override
	public DepartmentResponse archiveDepartment(Long departmentId) {
		this.userUtil.isAdmin();
		Department department = loadDepartment(departmentId);
		department.setStatus(Status.INACTIVE);
		this.departmentRepository.save(department);
		log.info("INFO - Department {} archived successfully", department.getCode());
		this.programCourseRepository.updateStatusByDepartmentId(Status.INACTIVE, departmentId);
		return departmentMapper.toResponse(department);
	}

	private Department loadDepartment(Long id) {
		return departmentRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(ExceptionConstants.NO_DEPARTMENT_FOUND));
	}
}
