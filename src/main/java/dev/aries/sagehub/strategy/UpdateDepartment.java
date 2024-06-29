package dev.aries.sagehub.strategy;

import java.util.List;
import java.util.Objects;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.request.DepartmentRequest;
import dev.aries.sagehub.enums.Status;
import dev.aries.sagehub.model.Department;
import dev.aries.sagehub.model.Program;
import dev.aries.sagehub.model.ProgramCourse;
import dev.aries.sagehub.repository.ProgramRepository;
import dev.aries.sagehub.repository.DepartmentRepository;
import dev.aries.sagehub.util.Checks;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateDepartment implements UpdateStrategy<Department, DepartmentRequest> {
	private final ProgramRepository programRepository;
	private final DepartmentRepository departmentRepository;
	private final UpdateStatus updateStatus;
	private final Checks checks;

	@Override
	public Department update(Department entity, DepartmentRequest request) {
		checkName(entity, request);
		this.checks.checkIfEnumExists(Status.class, request.status());
		updateStatus.updateProgramStatus(request.status(), entity.getId());
		entity.setStatus(Status.valueOf(request.status()));
		log.info("INFO - Department status updated successfully: {}", entity.getStatus());

		if (request.programIds() != null && !request.programIds().isEmpty()) {
			List<Program> newPrograms = programRepository.findAllById(request.programIds());
			if (newPrograms.size() != request.programIds().size()) {
				throw new EntityNotFoundException(ExceptionConstants.PROGRAM_FETCH_ERROR);
			}
			List<Program> programsToArchive = entity.getPrograms().stream()
					.filter(program -> !request.programIds().contains(program.getId()))
					.toList();
			for (Program program : programsToArchive) {
				program.setStatus(Status.ARCHIVED);
				for (ProgramCourse programCourse : program.getCourses()) {
					programCourse.setStatus(Status.ARCHIVED);
				}
				programRepository.save(program);
			}

			entity.getPrograms().clear();
			entity.getPrograms().addAll(newPrograms);
		}

		this.departmentRepository.save(entity);

		return entity;
	}

	private void checkName(Department entity, DepartmentRequest request) {
		if (!Objects.equals(request.name(), entity.getName())) {
			throw new IllegalArgumentException(String.format(ExceptionConstants.CANNOT_UPDATE_NAME, "Department"));
		}
	}
}
