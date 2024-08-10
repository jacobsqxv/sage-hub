package dev.aries.sagehub.service.emergencyinfoservice;

import dev.aries.sagehub.dto.request.EmergencyInfoRequest;
import dev.aries.sagehub.dto.response.EmergencyInfoResponse;
import dev.aries.sagehub.mapper.UserMapper;
import dev.aries.sagehub.mapper.UserProfileMapper;
import dev.aries.sagehub.model.EmergencyInfo;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.repository.EmergInfoRepository;
import dev.aries.sagehub.strategy.UpdateStrategyConfig;
import dev.aries.sagehub.util.Checks;
import dev.aries.sagehub.util.DataLoader;
import dev.aries.sagehub.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
/**
 * Implementation of the {@code EmergencyInfoService} interface.
 * @author Jacobs Agyei
 * @see EmergencyInfoService
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class EmergencyInfoServiceImpl implements EmergencyInfoService {
	private final UpdateStrategyConfig updateStrategyConfig;
	private final EmergInfoRepository emergInfoRepository;
	private final DataLoader dataLoader;
	private final UserUtil userUtil;
	private final Checks checks;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmergencyInfoResponse getEmergencyInfo(Long id) {
		User loggedUser = userUtil.currentlyLoggedInUser();
		checks.isAdminOrLoggedIn(loggedUser.getUsername());
		EmergencyInfo EmergencyInfo = dataLoader.loadEmergencyInfo(id);
		return UserMapper.toEmergInfoResponse(EmergencyInfo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmergencyInfo addEmergencyInfo(EmergencyInfoRequest request, Long userId) {
		EmergencyInfo emergencyInfo = EmergencyInfo.builder()
				.userId(userId)
				.fullName(request.fullName())
				.relationship(request.relationship())
				.phoneNumber(request.phoneNumber().number())
				.email(request.email().value())
				.occupation(request.occupation())
				.address(UserProfileMapper.toAddress(request.address()))
				.build();
		log.info("Saving emergency contact info...");
		return emergencyInfo;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmergencyInfoResponse updateEmergencyInfo(Long id, EmergencyInfoRequest request) {
		User loggedUser = userUtil.currentlyLoggedInUser();
		checks.isAdminOrLoggedIn(loggedUser.getUsername());
		EmergencyInfo emergencyInfo = dataLoader.loadEmergencyInfo(id);
		EmergencyInfo updatedEmergencyInfo = (EmergencyInfo) updateStrategyConfig
				.checkStrategy("EmergencyInfo")
				.update(emergencyInfo, request);
		emergInfoRepository.save(updatedEmergencyInfo);
		log.info("Emergency contact info for user ID: {} updated", loggedUser.getId());
		return UserMapper.toEmergInfoResponse(updatedEmergencyInfo);
	}

}
