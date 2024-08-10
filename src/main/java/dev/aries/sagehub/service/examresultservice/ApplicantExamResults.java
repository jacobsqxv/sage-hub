package dev.aries.sagehub.service.examresultservice;

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
import dev.aries.sagehub.strategy.UpdateStrategy;
import dev.aries.sagehub.strategy.UpdateStrategyConfig;
import dev.aries.sagehub.util.DataLoader;
import dev.aries.sagehub.util.UserUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * Implementation of the {@code ApplicantResultService} interface.
 * @author Jacobs Agyei
 * @see ExamResultService
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ExamResultServiceImpl implements ExamResultService {
	private final ExamResultRepository examResultRepository;
	private final ApplicationRepository applicationRepository;
	private final UpdateStrategyConfig strategyConfig;
	private final DataLoader dataLoader;
	private final UserUtil userUtil;
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ExamResultResponse addExamResults(Long userId, ExamResultRequest request) {
		User loggedInUser = userUtil.currentlyLoggedInUser();
		Application application = dataLoader.loadApplicationByUserId(userId);
		checkLoggedInApplicant(userId, loggedInUser.getId());
		checkExistingResults(request.indexNumber());
		ExamResult results = ExamResultMapper.toExamResult(request, loggedInUser);
		for (SubjectScore score : results.getResults()) {
			score.setExam(results);
		}
		examResultRepository.save(results);
		application.getExamResults().add(results);
		applicationRepository.save(application);
		return ExamResultMapper.toResponse(results);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ExamResultResponse> getExamResults(Long userId) {
		User loggedInUser = userUtil.currentlyLoggedInUser();
		checkLoggedInApplicant(userId, loggedInUser.getId());
		List<ExamResult> examResults = loadApplicantResults(loggedInUser.getId());
		if (examResults.isEmpty()) {
			throw new EntityNotFoundException(String.format(ExceptionConstants.NOT_FOUND, "Results"));
		}
		return examResults.stream().map(ExamResultMapper::toResponse)
				.toList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExamResultResponse getExamResult(Long applicationId, Long resultId) {
		User loggedInUser = userUtil.currentlyLoggedInUser();
		checkLoggedInApplicant(applicationId, loggedInUser.getId());
		ExamResult examResult = loadApplicantResult(resultId, loggedInUser.getId());
		return ExamResultMapper.toResponse(examResult);
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
	public ExamResultResponse updateExamResults(Long userId, Long resultId, ExamResultRequest request) {
		User loggedInUser = userUtil.currentlyLoggedInUser();
		checkLoggedInApplicant(userId, loggedInUser.getId());
		ExamResult examResult = loadApplicantResult(resultId, userId);
		IDNumber oldID = IDNumber.of(examResult.getIndexNumber());
		if (!Objects.equals(request.indexNumber().value(), oldID.value())) {
			checkExistingResults(request.indexNumber());
		}
		UpdateStrategy strategy = strategyConfig.checkStrategy("updateExamResult");
		examResult = (ExamResult) strategy.update(examResult, request);
		List<SubjectScore> newScores = updateScores(request.subjectScores(), examResult);
		examResult.getResults().clear();
		examResult.getResults().addAll(newScores);
		examResultRepository.save(examResult);
		return ExamResultMapper.toResponse(examResult);
	}

	@Override
	@Transactional
	public void deleteExamResults(Long userId, Long resultId) {
		User loggedInUser = userUtil.currentlyLoggedInUser();
		checkLoggedInApplicant(userId, loggedInUser.getId());
		ExamResult examResult = loadApplicantResult(resultId, userId);
		examResultRepository.delete(examResult);
	}

	private ExamResult loadApplicantResult(Long resultId, Long userId) {
		return examResultRepository.findByIdAndUserId(resultId, userId)
				.orElseThrow(() -> new IllegalArgumentException(
						String.format(ExceptionConstants.NOT_FOUND, "Results")));
	}

	private List<ExamResult> loadApplicantResults(Long userId) {
		return examResultRepository.findAllByUserId(userId);
	}

	private List<SubjectScore> updateScores(List<SubjectScoreRequest> scores, ExamResult examResult) {
		return scores.stream().map((req) -> toSubjectScore(req, examResult)).toList();
	}

	private SubjectScore toSubjectScore(SubjectScoreRequest request, ExamResult examResult) {
		return SubjectScore.builder()
				.exam(examResult)
				.subject(request.subject())
				.grade(request.grade().getGrade())
				.build();
	}

	private void checkLoggedInApplicant(Long application, Long loggedInUser) {
		if (!applicationRepository.existsByIdAndStudentUserId(application, loggedInUser)) {
			throw new IllegalArgumentException(ExceptionConstants.UNAUTHORIZED_ACCESS);
		}
	}
}
