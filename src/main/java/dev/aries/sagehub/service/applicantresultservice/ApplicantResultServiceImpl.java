package dev.aries.sagehub.service.applicantresultservice;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.request.ApplicantResultRequest;
import dev.aries.sagehub.dto.response.ApplicantResultsResponse;
import dev.aries.sagehub.mapper.ApplicantResultsMapper;
import dev.aries.sagehub.model.Applicant;
import dev.aries.sagehub.model.ApplicantResult;
import dev.aries.sagehub.model.SubjectScore;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.repository.ApplicantRepository;
import dev.aries.sagehub.repository.ApplicantResultRepository;
import dev.aries.sagehub.strategy.UpdateStrategy;
import dev.aries.sagehub.util.ApplicantUtil;
import dev.aries.sagehub.util.Checks;
import dev.aries.sagehub.util.GlobalUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * Implementation of the {@code ApplicantResultService} interface.
 * @author Jacobs Agyei
 * @see dev.aries.sagehub.service.applicantresultservice.ApplicantResultService
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicantResultServiceImpl implements ApplicantResultService {
	private final ApplicantResultRepository applicantResultRepository;
	private final ApplicantRepository applicantRepository;
	private final ApplicantResultsMapper resultsMapper;
	private final ApplicantUtil applicantUtil;
	private final GlobalUtil globalUtil;
	private final Checks checks;

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ApplicantResultsResponse addApplicantResults(Long applicantId, ApplicantResultRequest request) {
		User loggedInUser = checks.currentlyLoggedInUser();
		checks.isCurrentlyLoggedInUser(loggedInUser.getId());
		applicantUtil.validApplicant(loggedInUser.getId(), applicantId);
		Applicant applicant = applicantUtil.loadApplicant(applicantId);
		ApplicantResult results = resultsMapper.toApplicantResults(request, applicant);
		for (SubjectScore score : results.getScores()) {
			score.setResult(results);
		}
		applicantResultRepository.save(results);
		applicant.getResults().add(results);
		applicantRepository.save(applicant);
		return resultsMapper.toApplicantResultsResponse(results);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ApplicantResultsResponse updateApplicantResults(Long id, Long resultId, ApplicantResultRequest request) {
		User loggedInUser = checks.currentlyLoggedInUser();
		log.info("applicant id: {} | username: {}", id, loggedInUser.getUsername());
		checkApplicant(id, loggedInUser.getUsername());
		checks.isCurrentlyLoggedInUser(loggedInUser.getId());
		ApplicantResult result = loadResults(resultId);
		applicantUtil.validApplicantResult(loggedInUser.getId(), result.getId());
		UpdateStrategy strategy = globalUtil.checkStrategy("updateApplicantResults");
		result = (ApplicantResult) strategy.update(result, request);
		applicantResultRepository.save(result);
		return resultsMapper.toApplicantResultsResponse(result);
	}

	private ApplicantResult loadResults(Long resultId) {
		return applicantResultRepository.findById(resultId)
				.orElseThrow(() -> new IllegalArgumentException(
						String.format(ExceptionConstants.NOT_FOUND, "Results")));
	}

	private void checkApplicant(Long id, String username) {
		if (!String.valueOf(id).equals(username)) {
			throw new IllegalArgumentException(ExceptionConstants.UNAUTHORIZED_ACCESS);
		}
	}
}
