package dev.aries.sagehub.service.basicinfoservice;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.request.BasicInfoRequest;
import dev.aries.sagehub.dto.response.BasicInfoResponse;
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
/**
 * Implementation of the {@code BasicInfoInterface} interface.
 * @author Jacobs Agyei
 * @see dev.aries.sagehub.service.basicinfoservice.BasicInfoInterface
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class BasicInfoImpl implements BasicInfoInterface {
	private final Checks checks;
	private final GlobalUtil globalUtil;
	private final BasicInfoMapper basicInfoMapper;
	private final BasicInfoRepository basicInfoRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BasicInfoResponse getBasicInfo(Long id) {
		User loggedUser = checks.currentlyLoggedInUser();
		checks.isAdminOrLoggedIn(loggedUser.getUsername());
		BasicInfo basicInfo = loadBasicInfo(id);
		return basicInfoMapper.toBasicInfoResponse(basicInfo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BasicInfo addBasicInfo(BasicInfoRequest request, Long userId) {
		BasicInfo basicInfo = BasicInfo.builder()
				.userId(userId)
				.profilePictureUrl(request.profilePicture())
				.title(request.title())
				.firstName(request.firstName())
				.lastName(request.lastName())
				.middleName(request.middleName())
				.maritalStatus(request.maritalStatus())
				.gender(request.gender())
				.dateOfBirth(request.dateOfBirth())
				.build();
		log.info("Saving new basic info...");
		return basicInfo;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BasicInfoResponse updateBasicInfo(Long id, BasicInfoRequest request) {
		User loggedUser = checks.currentlyLoggedInUser();
		checks.isAdminOrLoggedIn(loggedUser.getUsername());
		BasicInfo basicInfo = loadBasicInfo(id);
		UpdateStrategy strategy = globalUtil.checkStrategy("updateBasicInfo");
		basicInfo = (BasicInfo) strategy.update(basicInfo, request);
		basicInfoRepository.save(basicInfo);
		log.info(" Basic info for user ID: {} updated", loggedUser.getId());
		return basicInfoMapper.toBasicInfoResponse(basicInfo);
	}

	private BasicInfo loadBasicInfo(Long id) {
		return basicInfoRepository.findByUserId(id)
				.orElseThrow(() -> new EntityNotFoundException(
						String.format(ExceptionConstants.NO_INFO_FOUND, "basic")));
	}
}
