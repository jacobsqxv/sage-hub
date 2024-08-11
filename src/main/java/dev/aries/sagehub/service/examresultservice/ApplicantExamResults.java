package dev.aries.sagehub.service.examresultservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.request.ExamResultRequest;
import dev.aries.sagehub.dto.request.SubjectScoreRequest;
import dev.aries.sagehub.dto.response.ExamResultResponse;
import dev.aries.sagehub.mapper.ExamResultMapper;
import dev.aries.sagehub.model.Application;
import dev.aries.sagehub.model.ExamResult;
import dev.aries.sagehub.model.SubjectScore;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.model.attribute.IDNumber;
import dev.aries.sagehub.repository.ApplicationRepository;
import dev.aries.sagehub.repository.ExamResultRepository;
import dev.aries.sagehub.strategy.UpdateStrategyConfig;
import dev.aries.sagehub.util.DataLoader;
import dev.aries.sagehub.util.UserUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the {@code ExamResultService} interface.
 *
 * @author Jacobs Agyei
 * @see ExamResultService
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicantExamResults implements ExamResultService {
	private final ApplicationRepository applicationRepository;
	private final ExamResultRepository examResultRepository;
	private final UpdateStrategyConfig updateStrategyConfig;
	private final DataLoader dataLoader;
	private final UserUtil userUtil;

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ExamResultResponse addExamResults(Long applicationId, ExamResultRequest request) {
		User loggedInUser = userUtil.currentlyLoggedInUser();
		Application application = dataLoader.loadApplicationById(applicationId);
		checkLoggedInApplicant(applicationId, loggedInUser.getId());
		checkExistingResults(request.indexNumber());
		ExamResult results = ExamResultMapper.toExamResult(request, application.getStudent().getId());
		for (SubjectScore score : results.getResults()) {
			score.setExamResult(results);
		}
		examResultRepository.save(results);
		List<ExamResult> resultList = application.getExamResults();
		examResultList(resultList).add(results);
		applicationRepository.save(application);
		return ExamResultMapper.toResultResponse(results);
	}

	private List<ExamResult> examResultList(List<ExamResult> examResults) {
		if (examResults.isEmpty()) {
			return new ArrayList<>();
		}
		else {
			return examResults;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ExamResultResponse> getExamResults(Long applicationId) {
		User loggedInUser = userUtil.currentlyLoggedInUser();
		checkLoggedInApplicant(applicationId, loggedInUser.getId());
		List<ExamResult> examResults = loadApplicantResults(loggedInUser.getId());
		if (examResults.isEmpty()) {
			throw new EntityNotFoundException(String.format(ExceptionConstants.NOT_FOUND, "Results"));
		}
		return examResults.stream().map(ExamResultMapper::toResultResponse)
				.toList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExamResultResponse getExamResult(Long applicationId, Long resultId) {
		User loggedInUser = userUtil.currentlyLoggedInUser();
		checkLoggedInApplicant(applicationId, loggedInUser.getId());
		ExamResult examResult = loadApplicantResultById(resultId, loggedInUser.getId());
		return ExamResultMapper.toResultResponse(examResult);
	}

	private void checkExistingResults(IDNumber indexNumber) {
		if (examResultRepository.existsByIndexNumber(indexNumber.value())) {
			throw new IllegalArgumentException(ExceptionConstants.EXISTING_RESULTS);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ExamResultResponse updateExamResults(Long applicationId, Long resultId, ExamResultRequest request) {
		User loggedInUser = userUtil.currentlyLoggedInUser();
		checkLoggedInApplicant(applicationId, loggedInUser.getId());
		ExamResult examResult = loadApplicantResultById(resultId, loggedInUser.getId());
		IDNumber oldID = IDNumber.of(examResult.getIndexNumber());
		if (!Objects.equals(request.indexNumber().value(), oldID.value())) {
			checkExistingResults(request.indexNumber());
		}
		ExamResult updateExamResult = (ExamResult) updateStrategyConfig
				.checkStrategy("ExamResult").update(examResult, request);
		List<SubjectScore> newScores = updateScores(request.subjectScores(), updateExamResult);
		updateExamResult.getResults().clear();
		updateExamResult.getResults().addAll(newScores);
		examResultRepository.save(updateExamResult);
		return ExamResultMapper.toResultResponse(updateExamResult);
	}

	@Override
	@Transactional
	public void deleteExamResults(Long applicationId, Long resultId) {
		User loggedInUser = userUtil.currentlyLoggedInUser();
		checkLoggedInApplicant(applicationId, loggedInUser.getId());
		Application application = dataLoader.loadApplicationById(applicationId);
		ExamResult examResult = loadApplicantResultById(resultId, loggedInUser.getId());
		application.getExamResults().remove(examResult);
		applicationRepository.save(application);
		examResultRepository.delete(examResult);
	}

	private ExamResult loadApplicantResultById(Long resultId, Long userId) {
		return examResultRepository.findByIdAndStudentId(resultId, userId)
				.orElseThrow(() -> new IllegalArgumentException(
						String.format(ExceptionConstants.NOT_FOUND, "Results")));
	}

	private List<ExamResult> loadApplicantResults(Long userId) {
		return examResultRepository.findAllByStudentId(userId);
	}

	private List<SubjectScore> updateScores(List<SubjectScoreRequest> scores, ExamResult examResult) {
		return scores.stream().map((req) -> toSubjectScore(req, examResult)).toList();
	}

	private SubjectScore toSubjectScore(SubjectScoreRequest request, ExamResult examResult) {
		return SubjectScore.builder()
				.examResult(examResult)
				.subject(request.subject())
				.grade(request.grade().getGrade())
				.build();
	}

	private void checkLoggedInApplicant(Long applicationId, Long userId) {
		if (!applicationRepository.existsByIdAndStudentUserId(applicationId, userId)) {
			throw new IllegalArgumentException(ExceptionConstants.UNAUTHORIZED_ACCESS);
		}
	}
}
