package dev.aries.sagehub.service.emgcontactservice;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.request.EmergencyContactRequest;
import dev.aries.sagehub.dto.response.EmergencyContactResponse;
import dev.aries.sagehub.mapper.AddressMapper;
import dev.aries.sagehub.mapper.EmergencyContactMapper;
import dev.aries.sagehub.model.EmergencyContact;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.repository.EmergencyContactRepository;
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
	private final EmergencyContactMapper emergencyContactMapper;
	private final EmergencyContactRepository emergencyContactRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmergencyContactResponse getEmergencyContact(Long id) {
		User loggedUser = checks.currentlyLoggedInUser();
		checks.isAdminOrLoggedIn(loggedUser.getUsername());
		EmergencyContact emergencyContact = loadEmergencyContact(id);
		return emergencyContactMapper.toEmergencyContactResponse(emergencyContact);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmergencyContact addEmergencyContact(EmergencyContactRequest request, Long userId) {
		EmergencyContact emergencyContact = EmergencyContact.builder()
				.userId(userId)
				.fullName(request.fullName())
				.relationship(request.relationship())
				.phoneNumber(request.phoneNumber().number())
				.email(request.email().value())
				.occupation(request.occupation())
				.address(addressMapper.toAddress(request.address()))
				.build();
		log.info("Saving new emergency contact info...");
		return emergencyContact;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmergencyContactResponse updateEmergencyContact(Long id, EmergencyContactRequest request) {
		User loggedUser = checks.currentlyLoggedInUser();
		checks.isAdminOrLoggedIn(loggedUser.getUsername());
		EmergencyContact emergencyContact = loadEmergencyContact(id);
		UpdateStrategy strategy = globalUtil.checkStrategy("updateEmergencyContact");
		emergencyContact = (EmergencyContact) strategy.update(emergencyContact, request);
		emergencyContactRepository.save(emergencyContact);
		log.info("Emergency contact info for user ID: {} updated", loggedUser.getId());
		return emergencyContactMapper.toEmergencyContactResponse(emergencyContact);
	}

	private EmergencyContact loadEmergencyContact(Long id) {
		return emergencyContactRepository.findByUserId(id)
				.orElseThrow(() -> new EntityNotFoundException(
						String.format(ExceptionConstants.NO_INFO_FOUND, "emergency contact")));
	}
}
