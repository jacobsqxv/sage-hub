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

@Service
@Slf4j
@RequiredArgsConstructor
public class EmergencyContactImpl implements EmergencyContactInterface {
	private final Checks checks;
	private final GlobalUtil globalUtil;
	private final AddressMapper addressMapper;
	private final EmergencyContactMapper emergencyContactMapper;
	private final EmergencyContactRepository emergencyContactRepository;

	@Override
	public EmergencyContactResponse getEmergencyContact(Long id) {
		User loggedUser = this.checks.currentlyLoggedInUser();
		this.checks.isAdminOrLoggedIn(loggedUser.getUsername());
		EmergencyContact emergencyContact = loadEmergencyContact(id);
		return this.emergencyContactMapper.toEmergencyContactResponse(emergencyContact);
	}

	@Override
	public EmergencyContact addEmergencyContact(EmergencyContactRequest request) {
		EmergencyContact emergencyContact = EmergencyContact.builder()
				.fullName(request.fullName())
				.relationship(request.relationship())
				.phoneNumber(request.phoneNumber().value())
				.email(request.email().value())
				.occupation(request.occupation())
				.address(this.addressMapper.toAddress(request.address()))
				.build();
		log.info("INFO - Saving new emergency contact info...");
		return emergencyContact;
	}

	@Override
	public EmergencyContactResponse updateEmergencyContact(Long id, EmergencyContactRequest request) {
		User loggedUser = this.checks.currentlyLoggedInUser();
		this.checks.isAdminOrLoggedIn(loggedUser.getUsername());
		EmergencyContact emergencyContact = loadEmergencyContact(id);
		UpdateStrategy strategy = this.globalUtil.checkStrategy("updateEmergencyContact");
		emergencyContact = (EmergencyContact) strategy.update(emergencyContact, request);
		this.emergencyContactRepository.save(emergencyContact);
		log.info("INFO - Emergency contact info for user ID: {} updated", loggedUser.getId());
		return this.emergencyContactMapper.toEmergencyContactResponse(emergencyContact);
	}

	private EmergencyContact loadEmergencyContact(Long id) {
		return this.emergencyContactRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(
						String.format(ExceptionConstants.NO_INFO_FOUND, "emergency contact")));
	}
}
