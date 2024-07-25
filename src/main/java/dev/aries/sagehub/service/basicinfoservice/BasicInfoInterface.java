package dev.aries.sagehub.service.basicinfoservice;

import dev.aries.sagehub.dto.request.BasicInfoRequest;
import dev.aries.sagehub.dto.response.BasicInfoResponse;
import dev.aries.sagehub.model.BasicInfo;

public interface BasicInfoInterface {
	/**
	 * Retrieves the basic information of a user.
	 * @param id the ID of the user.
	 * @return a BasicInfoResponse containing the user's basic information.
	 */
	BasicInfoResponse getBasicInfo(Long id);
	/**
	 * Adds new basic information for a user.
	 * @param request the request containing the new information.
	 * @param userId  the ID of the user.
	 * @return a BasicInfo containing the updated information.
	 */
	BasicInfo addBasicInfo(BasicInfoRequest request, Long userId);
	/**
	 * Updates the basic information of a user.
	 * @param id      the ID of the user.
	 * @param request the request containing the new information.
	 * @return a BasicInfoResponse containing the updated basic information.
	 */
	BasicInfoResponse updateBasicInfo(Long id, BasicInfoRequest request);
}
