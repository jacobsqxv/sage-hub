package dev.aries.sagehub.service.programservice;

import java.util.List;

import dev.aries.sagehub.dto.request.ProgramCourseRequest;
import dev.aries.sagehub.dto.request.ProgramRequest;
import dev.aries.sagehub.dto.response.ProgramCourseResponse;
import dev.aries.sagehub.dto.response.ProgramResponse;

public interface ProgramService {
	ProgramResponse addProgram(ProgramRequest request);

	List<ProgramResponse> getPrograms();

	ProgramResponse getProgram(Long programId);

	ProgramResponse updateProgram(Long programId, ProgramRequest request);

	ProgramCourseResponse updateProgramCourses(Long programId, ProgramCourseRequest request);

}
