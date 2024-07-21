package dev.aries.sagehub.service.applicantresultservice;

import dev.aries.sagehub.dto.request.ApplicantResultRequest;
import dev.aries.sagehub.dto.response.ApplicantResultsResponse;

public interface ApplicantResultService {

	ApplicantResultsResponse addApplicantResults(Long applicantId, ApplicantResultRequest request);

	ApplicantResultsResponse updateApplicantResults(Long id, ApplicantResultRequest request);
}
