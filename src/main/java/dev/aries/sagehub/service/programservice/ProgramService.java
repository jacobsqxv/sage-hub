package dev.aries.sagehub.service.programservice;

import dev.aries.sagehub.dto.request.ProgramCourseRequest;
import dev.aries.sagehub.dto.request.ProgramRequest;
import dev.aries.sagehub.dto.response.ProgramCourseResponse;
import dev.aries.sagehub.dto.response.ProgramResponse;
import dev.aries.sagehub.dto.search.GetProgramsPage;

import org.springframework.data.domain.Page;

public interface ProgramService {
	ProgramResponse addProgram(ProgramRequest request);

	Page<ProgramResponse> getPrograms(GetProgramsPage request);

	ProgramResponse getProgram(Long programId);

	ProgramResponse updateProgram(Long programId, ProgramRequest request);

	ProgramCourseResponse updateProgramCourses(Long programId, ProgramCourseRequest request);

}
