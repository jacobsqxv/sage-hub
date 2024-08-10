package dev.aries.sagehub.service.emgcontactservice;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.request.EmergencyInfoRequest;
import dev.aries.sagehub.dto.response.EmergencyInfoResponse;
import dev.aries.sagehub.mapper.AddressMapper;
import dev.aries.sagehub.mapper.EmergencyInfoMapper;
import dev.aries.sagehub.model.EmergencyInfo;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.repository.EmergencyInfoRepository;
import dev.aries.sagehub.strategy.UpdateStrategy;
import dev.aries.sagehub.util.Checks;
import dev.aries.sagehub.util.GlobalUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
/**
 * Implementation of the {@code EmergencyContactInterface} interface.
 * @author Jacobs Agyei
 * @see dev.aries.sagehub.service.emgcontactservice.EmergencyContactInterface
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class EmergencyContactImpl implements EmergencyContactInterface {
	private final Checks checks;
	private final GlobalUtil globalUtil;
	private final AddressMapper addressMapper;
	private final EmergencyInfoMapper emergencyInfoMapper;
	private final EmergencyInfoRepository emergencyInfoRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmergencyInfoResponse getEmergencyContact(Long id) {
		User loggedUser = checks.currentlyLoggedInUser();
		checks.isAdminOrLoggedIn(loggedUser.getUsername());
		EmergencyInfo EmergencyInfo = loadEmergencyContact(id);
		return emergencyInfoMapper.toEmergencyContactResponse(EmergencyInfo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmergencyInfo addEmergencyContact(EmergencyInfoRequest request, Long userId) {
		EmergencyInfo EmergencyInfo = EmergencyInfo.builder()
				.userId(userId)
				.fullName(request.fullName())
				.relationship(request.relationship())
				.phoneNumber(request.phoneNumber().number())
				.email(request.email().value())
				.occupation(request.occupation())
				.address(addressMapper.toAddress(request.address()))
				.build();
		log.info("Saving new emergency contact info...");
		return EmergencyInfo;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmergencyInfoResponse updateEmergencyContact(Long id, EmergencyInfoRequest request) {
		User loggedUser = checks.currentlyLoggedInUser();
		checks.isAdminOrLoggedIn(loggedUser.getUsername());
		EmergencyInfo EmergencyInfo = loadEmergencyContact(id);
		UpdateStrategy strategy = globalUtil.checkStrategy("updateEmergencyContact");
		EmergencyInfo = (EmergencyInfo) strategy.update(EmergencyInfo, request);
		emergencyInfoRepository.save(EmergencyInfo);
		log.info("Emergency contact info for user ID: {} updated", loggedUser.getId());
		return emergencyInfoMapper.toEmergencyContactResponse(EmergencyInfo);
	}

	private EmergencyInfo loadEmergencyContact(Long id) {
		return emergencyInfoRepository.findByUserId(id)
				.orElseThrow(() -> new EntityNotFoundException(
						String.format(ExceptionConstants.NO_INFO_FOUND, "emergency contact")));
	}
}
