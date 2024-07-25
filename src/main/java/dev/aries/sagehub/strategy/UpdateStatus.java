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
	public void updateProgramCoursesStatus(String status, Long programId) {
		log.info("INFO - Updating program course status: {}", status);
		if (isNotActive(status)) {
			this.programCourseRepository.updateStatusByProgramId(Status.valueOf(status), programId);
		}
	}

	@Transactional
	public void updateProgramStatus(String status, Long departmentId) {
		log.info("INFO - Updating program status: {}", status);
		if (isNotActive(status)) {
			this.programRepository.updateStatusByDepartmentId(Status.valueOf(status), departmentId);
		}
	}

	private boolean isNotActive(String status) {
		return Status.valueOf(status) == Status.INACTIVE ||
				Status.valueOf(status) == Status.ARCHIVED ||
				Status.valueOf(status) == Status.DELETED;
	}
}
