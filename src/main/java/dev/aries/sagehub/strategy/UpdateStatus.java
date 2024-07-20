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
		log.info("INFO - Updating program course status: INACTIVE");
		if (isValidStatus(status)) {
			this.programCourseRepository.updateStatusByProgramId(Status.valueOf(status), programId);
			log.info("INFO - Program course status updated successfully");
		}
	}

	@Transactional
	public void updateProgramStatus(String status, Long departmentId) {
		log.info("INFO - Updating program status: INACTIVE");
		if (isValidStatus(status)) {
			this.programRepository.updateStatusByDepartmentId(Status.valueOf(status), departmentId);
			log.info("INFO - Program status updated successfully");
		}
	}

	private boolean isValidStatus(String status) {
		return Status.valueOf(status) == Status.INACTIVE ||
				Status.valueOf(status) == Status.ARCHIVED ||
				Status.valueOf(status) == Status.ACTIVE;
	}
}
