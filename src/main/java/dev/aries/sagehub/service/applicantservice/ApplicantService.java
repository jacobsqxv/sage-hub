package dev.aries.sagehub.service.applicantservice;

import java.util.List;

import dev.aries.sagehub.dto.request.ApplicantRequest;
import dev.aries.sagehub.dto.request.ProgramChoicesRequest;
import dev.aries.sagehub.dto.response.ApplicantResponse;
import dev.aries.sagehub.dto.response.ProgramResponse;

public interface ApplicantService {

	ApplicantResponse addPersonalInfo(ApplicantRequest request);

	ApplicantResponse getApplicant(Long applicantId);

	List<ProgramResponse> updateApplicantProgramChoices(Long applicantId, ProgramChoicesRequest request);

}
