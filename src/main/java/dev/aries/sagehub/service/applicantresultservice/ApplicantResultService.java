package dev.aries.sagehub.service.applicantresultservice;

import dev.aries.sagehub.dto.request.ApplicantResultRequest;
import dev.aries.sagehub.dto.response.ApplicantResultsResponse;
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
	ApplicantResultsResponse addApplicantResults(Long applicantId, ApplicantResultRequest request);
	/**
	 * Updates existing applicant results.
	 * <p>
	 * This method takes the {@code id} of the applicant's result and an {@code ApplicantResultRequest}
	 * object containing the updated details of the
	 * applicant results, and returns an {@code ApplicantResultsResponse} object containing the updated results.
	 * </p>
	 * @param id the ID of the applicant results to be updated.
	 * @param request the {@code ApplicantResultRequest} containing the updated applicant results information.
	 * @return an {@code ApplicantResultsResponse} containing the updated applicant results information.
	 * @throws IllegalArgumentException if the request is null or contains invalid data.
	 */
	ApplicantResultsResponse updateApplicantResults(Long id, ApplicantResultRequest request);
}
