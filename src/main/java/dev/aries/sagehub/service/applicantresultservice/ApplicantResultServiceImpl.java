package dev.aries.sagehub.service.applicantresultservice;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.request.ApplicantResultRequest;
import dev.aries.sagehub.dto.response.ApplicantResultsResponse;
import dev.aries.sagehub.mapper.ApplicantResultsMapper;
import dev.aries.sagehub.model.Applicant;
import dev.aries.sagehub.model.ApplicantResult;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.repository.ApplicantRepository;
import dev.aries.sagehub.repository.ApplicantResultRepository;
import dev.aries.sagehub.strategy.UpdateStrategy;
import dev.aries.sagehub.util.ApplicantUtil;
import dev.aries.sagehub.util.Checks;
import dev.aries.sagehub.util.GlobalUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicantResultServiceImpl implements ApplicantResultService {
	private final ApplicantResultRepository applicantResultRepository;
	private final ApplicantRepository applicantRepository;
	private final ApplicantResultsMapper resultsMapper;
	private final ApplicantUtil applicantUtil;
	private final GlobalUtil globalUtil;
	private final Checks checks;

	@Override
	public ApplicantResultsResponse addApplicantResults(Long applicantId, ApplicantResultRequest request) {
		User loggedInUser = this.checks.currentlyLoggedInUser();
		this.checks.isCurrentlyLoggedInUser(loggedInUser.getId());
		this.applicantUtil.validApplicant(loggedInUser.getId(), applicantId);
		Applicant applicant = this.applicantUtil.loadApplicant(applicantId);
		ApplicantResult results = this.resultsMapper.toApplicantResults(request);
		this.applicantResultRepository.save(results);
		applicant.getResults().add(results);
		this.applicantRepository.save(applicant);
		return this.resultsMapper.toApplicantResultsResponse(results);
	}

	@Override
	public ApplicantResultsResponse updateApplicantResults(Long id, ApplicantResultRequest request) {
		User loggedInUser = this.checks.currentlyLoggedInUser();
		this.checks.isCurrentlyLoggedInUser(loggedInUser.getId());
		ApplicantResult result = loadResults(id);
		this.applicantUtil.validApplicantResult(loggedInUser.getId(), result.getId());
		UpdateStrategy strategy = this.globalUtil.checkStrategy("updateApplicantResults");
		result = (ApplicantResult) strategy.update(result, request);
		this.applicantResultRepository.save(result);
		return this.resultsMapper.toApplicantResultsResponse(result);
	}

	private ApplicantResult loadResults(Long id) {
		return this.applicantResultRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(
						String.format(ExceptionConstants.NOT_FOUND, "Results")));
	}
}
