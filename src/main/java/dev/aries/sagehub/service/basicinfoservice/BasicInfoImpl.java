package dev.aries.sagehub.service.basicinfoservice;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.request.BasicInfoRequest;
import dev.aries.sagehub.dto.response.BasicInfoResponse;
import dev.aries.sagehub.enums.Gender;
import dev.aries.sagehub.enums.MaritalStatus;
import dev.aries.sagehub.enums.Title;
import dev.aries.sagehub.mapper.BasicInfoMapper;
import dev.aries.sagehub.model.BasicInfo;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.repository.BasicInfoRepository;
import dev.aries.sagehub.strategy.UpdateStrategy;
import dev.aries.sagehub.util.Checks;
import dev.aries.sagehub.util.GlobalUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BasicInfoImpl implements BasicInfoInterface {
	private final Checks checks;
	private final GlobalUtil globalUtil;
	private final BasicInfoMapper basicInfoMapper;
	private final BasicInfoRepository basicInfoRepository;

	@Override
	public BasicInfoResponse getBasicInfo(Long id) {
		User loggedUser = this.checks.currentlyLoggedInUser();
		this.checks.isAdminOrLoggedIn(loggedUser.getUsername());
		BasicInfo basicInfo = loadBasicInfo(id);
		return this.basicInfoMapper.toBasicInfoResponse(basicInfo);
	}

	@Override
	public BasicInfo addBasicInfo(BasicInfoRequest request) {
		BasicInfo basicInfo = BasicInfo.builder()
				.profilePictureUrl(request.profilePicture())
				.title(Title.valueOf(request.title().toUpperCase()))
				.firstName(request.firstname())
				.lastName(request.lastname())
				.middleName(request.middleName())
				.maritalStatus(MaritalStatus.valueOf(
						request.maritalStatus().toUpperCase()))
				.gender(Gender.valueOf(request.gender().toUpperCase()))
				.dateOfBirth(request.dateOfBirth())
				.build();
		log.info("INFO - Saving new basic info...");
		return basicInfo;
	}

	@Override
	public BasicInfoResponse updateBasicInfo(Long id, BasicInfoRequest request) {
		User loggedUser = this.checks.currentlyLoggedInUser();
		this.checks.isAdminOrLoggedIn(loggedUser.getUsername());
		BasicInfo basicInfo = loadBasicInfo(id);
		UpdateStrategy strategy = this.globalUtil.checkStrategy("updateBasicInfo");
		basicInfo = (BasicInfo) strategy.update(basicInfo, request);
		this.basicInfoRepository.save(basicInfo);
		log.info("INFO - Basic info for user ID: {} updated", loggedUser.getId());
		return this.basicInfoMapper.toBasicInfoResponse(basicInfo);
	}

	private BasicInfo loadBasicInfo(Long id) {
		return this.basicInfoRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(
						String.format(ExceptionConstants.NO_INFO_FOUND, "basic")));
	}
}
