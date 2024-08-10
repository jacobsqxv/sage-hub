package dev.aries.sagehub.service.departmentservice;

import dev.aries.sagehub.dto.request.DepartmentRequest;
import dev.aries.sagehub.dto.response.DepartmentResponse;
import dev.aries.sagehub.dto.search.GetDepartmentsPage;
import dev.aries.sagehub.enums.Status;
import dev.aries.sagehub.factory.ModelFactory;
import dev.aries.sagehub.mapper.DepartmentMapper;
import dev.aries.sagehub.model.Department;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.repository.DepartmentRepository;
import dev.aries.sagehub.strategy.UpdateStrategyConfig;
import dev.aries.sagehub.util.Checks;
import dev.aries.sagehub.util.DataLoader;
import dev.aries.sagehub.util.UserUtil;
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
	private final UpdateStrategyConfig updateStrategyConfig;
	private final ModelFactory modelFactory;
	private final DataLoader dataLoader;
	private final UserUtil userUtil;
	private static final String NAME = "Department";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DepartmentResponse addDepartment(DepartmentRequest request) {
		existsByName(request.name());
		Department department = modelFactory.createNewDept(request);
		departmentRepository.save(department);
		log.info("Department {} added successfully", department.getCode());
		return DepartmentMapper.toResponse(department);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DepartmentResponse getDepartment(Long departmentId) {
		Department department = dataLoader.loadDepartment(departmentId);
		User loggedInUser = userUtil.currentlyLoggedInUser();
		if (!Checks.isAdmin(loggedInUser.getRole().getName())) {
			department.setPrograms(department.getPrograms().stream()
					.filter((program) -> program.getStatus() == Status.ACTIVE)
					.toList());
		}
		return DepartmentMapper.toResponse(department);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<DepartmentResponse> getDepartments(GetDepartmentsPage request, Pageable pageable) {
		if (request.status() != null) {
			Checks.checkIfEnumExists(Status.class, request.status());
		}
		User loggedInUser = userUtil.currentlyLoggedInUser();
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
				.map(DepartmentMapper::toPageResponse);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DepartmentResponse updateDepartment(Long departmentId, DepartmentRequest request) {
		User loggedInUser = userUtil.currentlyLoggedInUser();
		Checks.checkAdmins(loggedInUser.getRole().getName());
		Department department = dataLoader.loadDepartment(departmentId);
		Department updatedDepartment = (Department) updateStrategyConfig
				.checkStrategy("Department")
				.update(department, request);
		departmentRepository.save(updatedDepartment);
		log.info("Department {} updated successfully", updatedDepartment.getCode());
		return DepartmentMapper.toResponse(updatedDepartment);
	}

	private void existsByName(String name) {
		if (departmentRepository.existsByName(name)) {
			throw new IllegalArgumentException(String.format(NAME_EXISTS, NAME, name));
		}
	}
}
