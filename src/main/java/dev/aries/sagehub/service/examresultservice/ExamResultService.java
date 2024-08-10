package dev.aries.sagehub.service.examresultservice;

import java.util.List;

import dev.aries.sagehub.dto.request.ExamResultRequest;
import dev.aries.sagehub.dto.response.ExamResultResponse;

/**
 * The {@code ApplicantResultService} interface provides methods for managing applicant results.
 * It includes functionality for adding and updating applicant results.
 * <p>
 * Implementations of this interface are responsible for handling the business logic
 * related to applicant result management.
 * </p>
 *
 * @author Jacobs Agyei
 */
public interface ApplicantResultService {
	/**
	 * Adds new applicant results.
	 * <p>
	 * This method takes the {@code applicantId} and an {@code ApplicantResultRequest} object
	 * containing the details of the applicant results to be added,
	 * and returns an {@code ApplicantResultsResponse} object containing the details of the newly added results.
	 * </p>
	 * @param applicantId the ID of the applicant.
	 * @param request the {@code ApplicantResultRequest} containing the applicant results information.
	 * @return an {@code ApplicantResultsResponse} containing the new applicant results information.
	 * @throws IllegalArgumentException if the request is null or contains invalid data.
	 */
	ExamResultResponse addApplicantResults(Long applicantId, ExamResultRequest request);

	/**
	 * Retrieves all the applicant's results.
	 * <p>
	 * This method takes the {@code applicantId} and returns a list of {@code ApplicantResultsResponse}
	 * objects containing the details of the applicant results.
	 * </p>
	 * @param applicantId the ID of the applicant.
	 * @return a list of {@code ApplicantResultsResponse} containing the applicant results information.
	 */
	List<ExamResultResponse> getApplicantResults(Long applicantId);

	/**
	 * Retrieves applicant results by id.
	 * <p>
	 * This method takes the {@code applicantId} and {@code resultId} and returns an
	 * {@code ApplicantResultsResponse} object containing the details of the applicant results.
	 * </p>
	 * @param applicantId the ID of the applicant.
	 * @param resultId the ID of the applicant results.
	 * @return an {@code ApplicantResultsResponse} containing the applicant results information.
	 */
	ExamResultResponse getApplicantResult(Long applicantId, Long resultId);

	/**
	 * Updates existing applicant results.
	 * <p>
	 * This method takes the id of the applicant, the id of the result and an {@code ApplicantResultRequest}
	 * object containing the updated details of the
	 * applicant results, and returns an {@code ApplicantResultsResponse} object containing the updated results.
	 * </p>
	 * @param id the ID of the applicant to be updated.
	 * @param resultId the ID of the applicant results to be updated.
	 * @param request the {@code ApplicantResultRequest} containing the updated applicant results information.
	 * @return an {@code ApplicantResultsResponse} containing the updated applicant results information.
	 * @throws IllegalArgumentException if the request is null or contains invalid data.
	 */
	ExamResultResponse updateApplicantResults(Long id, Long resultId, ExamResultRequest request);

	/**
	 * Deletes applicant results.
	 * <p>
	 * This method takes the {@code applicantId} and {@code resultId} and deletes the applicant results.
	 * </p>
	 * @param applicantId the ID of the applicant.
	 * @param resultId the ID of the applicant results.
	 * @throws IllegalArgumentException if the applicant or result does not exist.
	 */
	void deleteApplicantResults(Long applicantId, Long resultId);
}
