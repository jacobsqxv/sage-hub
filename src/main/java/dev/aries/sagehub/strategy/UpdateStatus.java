package dev.aries.sagehub.strategy;

import dev.aries.sagehub.enums.Status;
import dev.aries.sagehub.repository.ProgramCourseRepository;
import dev.aries.sagehub.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateStatus {
	private final ProgramCourseRepository programCourseRepository;
	private final ProgramRepository programRepository;

	@Transactional
	public void updateProgramCoursesStatus(Status status, Long programId) {
		log.info("Updating program course status: {}", status);
		if (isNotActive(status)) {
			programCourseRepository.updateStatusByProgramId(status, programId);
		}
	}

	@Transactional
	public void updateProgramStatus(Status status, Long departmentId) {
		log.info("Updating program status: {}", status);
		if (isNotActive(status)) {
			programRepository.updateStatusByDepartmentId(status, departmentId);
		}
	}

	private boolean isNotActive(Status status) {
		return status == Status.INACTIVE ||
				status == Status.ARCHIVED ||
				status == Status.DELETED;
	}
}
