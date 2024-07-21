package dev.aries.sagehub.service.basicinfoservice;

import dev.aries.sagehub.dto.request.BasicInfoRequest;
import dev.aries.sagehub.dto.response.BasicInfoResponse;
import dev.aries.sagehub.model.BasicInfo;

public interface BasicInfoInterface {
	BasicInfoResponse getBasicInfo(Long id);

	BasicInfo addBasicInfo(BasicInfoRequest request);

	BasicInfoResponse updateBasicInfo(Long id, BasicInfoRequest request);
}
