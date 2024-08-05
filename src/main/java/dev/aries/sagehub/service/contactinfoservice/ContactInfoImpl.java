package dev.aries.sagehub.service.contactinfoservice;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.request.ContactInfoRequest;
import dev.aries.sagehub.dto.response.ContactInfoResponse;
import dev.aries.sagehub.mapper.AddressMapper;
import dev.aries.sagehub.mapper.ContactInfoMapper;
import dev.aries.sagehub.model.ContactInfo;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.repository.ContactInfoRepository;
import dev.aries.sagehub.strategy.UpdateStrategy;
import dev.aries.sagehub.util.Checks;
import dev.aries.sagehub.util.GlobalUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
/**
 * Implementation of the {@code ContactInfoInterface} interface.
 * @author Jacobs Agyei
 * @see dev.aries.sagehub.service.contactinfoservice.ContactInfoInterface
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ContactInfoImpl implements ContactInfoInterface {
	private final Checks checks;
	private final GlobalUtil globalUtil;
	private final AddressMapper addressMapper;
	private final ContactInfoMapper contactInfoMapper;
	private final ContactInfoRepository contactInfoRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ContactInfoResponse getContactInfo(Long id) {
		User loggedUser = checks.currentlyLoggedInUser();
		checks.isAdminOrLoggedIn(loggedUser.getUsername());
		ContactInfo contactInfo = loadContactInfo(id);
		return contactInfoMapper.toContactInfoResponse(contactInfo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ContactInfo addContactInfo(ContactInfoRequest request, Long userId) {
		ContactInfo contactInfo = ContactInfo.builder()
				.userId(userId)
				.secondaryEmail(request.secondaryEmail().value())
				.phoneNumber(request.phoneNumber().number())
				.address(addressMapper.toAddress(request.address()))
				.postalAddress(request.postalAddress())
				.build();
		log.info("Saving new contact info...");
		return contactInfo;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ContactInfoResponse updateContactInfo(Long id, ContactInfoRequest request) {
		User loggedUser = checks.currentlyLoggedInUser();
		checks.isAdminOrLoggedIn(loggedUser.getUsername());
		ContactInfo contactInfo = loadContactInfo(id);
		UpdateStrategy strategy = globalUtil.checkStrategy("updateContactInfo");
		contactInfo = (ContactInfo) strategy.update(contactInfo, request);
		contactInfoRepository.save(contactInfo);
		log.info("Contact info for user ID: {} updated", loggedUser.getId());
		return contactInfoMapper.toContactInfoResponse(contactInfo);
	}

	private ContactInfo loadContactInfo(Long id) {
		return contactInfoRepository.findByUserId(id)
				.orElseThrow(() -> new EntityNotFoundException(
						String.format(ExceptionConstants.NOT_FOUND, "Contact info")));
	}
}
