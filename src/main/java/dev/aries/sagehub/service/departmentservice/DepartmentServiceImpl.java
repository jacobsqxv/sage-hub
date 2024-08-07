package dev.aries.sagehub.service.departmentservice;

import dev.aries.sagehub.dto.request.DepartmentRequest;
import dev.aries.sagehub.dto.response.DepartmentResponse;
import dev.aries.sagehub.dto.search.GetDepartmentsPage;
import dev.aries.sagehub.enums.Status;
import dev.aries.sagehub.mapper.DepartmentMapper;
import dev.aries.sagehub.model.Department;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.repository.DepartmentRepository;
import dev.aries.sagehub.strategy.UpdateStrategy;
import dev.aries.sagehub.util.Checks;
import dev.aries.sagehub.util.Generators;
import dev.aries.sagehub.util.GlobalUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static dev.aries.sagehub.constant.ExceptionConstants.NAME_EXISTS;
/**
 * Implementation of the {@code DepartmentService} interface.
 * @author Jacobs Agyei
 * @see dev.aries.sagehub.service.departmentservice.DepartmentService
 */
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DepartmentResponse addDepartment(DepartmentRequest request) {
		existsByName(request.name());
		Department department = Department.builder()
				.name(request.name())
				.code(generators.generateDeptCode())
				.status(Status.PENDING)
				.build();
		departmentRepository.save(department);
		log.info("Department {} added successfully", department.getCode());
		return departmentMapper.toResponse(department);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DepartmentResponse getDepartment(Long departmentId) {
		Department department = globalUtil.loadDepartment(departmentId);
		User loggedInUser = checks.currentlyLoggedInUser();
		if (!Checks.isAdmin(loggedInUser.getRole().getName())) {
			department.setPrograms(department.getPrograms().stream()
					.filter((program) -> program.getStatus() == Status.ACTIVE)
					.toList());
		}
		return departmentMapper.toResponse(department);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<DepartmentResponse> getDepartments(GetDepartmentsPage request, Pageable pageable) {
		User loggedInUser = checks.currentlyLoggedInUser();
		if (Checks.isAdmin(loggedInUser.getRole().getName())) {
			return getAllDepartments(request, pageable);
		}
		return getActiveDepartments(request, pageable);
	}

	private Page<DepartmentResponse> getActiveDepartments(GetDepartmentsPage request, Pageable pageable) {
		return loadDepartments(request, Status.ACTIVE.name(), pageable);
	}

	private Page<DepartmentResponse> getAllDepartments(GetDepartmentsPage request, Pageable pageable) {
		return loadDepartments(request, request.status(), pageable);
	}

	private Page<DepartmentResponse> loadDepartments(GetDepartmentsPage request, String status, Pageable pageable) {
		return departmentRepository.findAll(request, status, pageable)
				.map(departmentMapper::toPageResponse);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DepartmentResponse updateDepartment(Long departmentId, DepartmentRequest request) {
		User loggedInUser = checks.currentlyLoggedInUser();
		checks.checkAdmins(loggedInUser.getRole().getName());
		Department department = globalUtil.loadDepartment(departmentId);
		UpdateStrategy updateStrategy = globalUtil.checkStrategy("updateDepartment");
		department = (Department) updateStrategy.update(department, request);
		departmentRepository.save(department);
		log.info("Department {} updated successfully", department.getCode());
		return departmentMapper.toResponse(department);
	}

	private void existsByName(String name) {
		if (departmentRepository.existsByName(name)) {
			throw new IllegalArgumentException(String.format(NAME_EXISTS, NAME, name));
		}
	}
}
