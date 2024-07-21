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

@Service
@Slf4j
@RequiredArgsConstructor
public class ContactInfoImpl implements ContactInfoInterface {
	private final Checks checks;
	private final GlobalUtil globalUtil;
	private final AddressMapper addressMapper;
	private final ContactInfoMapper contactInfoMapper;
	private final ContactInfoRepository contactInfoRepository;

	@Override
	public ContactInfoResponse getContactInfo(Long id) {
		User loggedUser = this.checks.currentlyLoggedInUser();
		this.checks.isAdminOrLoggedIn(loggedUser.getUsername());
		ContactInfo contactInfo = loadContactInfo(id);
		return this.contactInfoMapper.toContactInfoResponse(contactInfo);
	}

	@Override
	public ContactInfo addContactInfo(ContactInfoRequest request) {
		ContactInfo contactInfo = ContactInfo.builder()
				.secondaryEmail(request.secondaryEmail().value())
				.phoneNumber(request.phoneNumber().value())
				.address(this.addressMapper.toAddress(request.address()))
				.postalAddress(request.postalAddress())
				.build();
		log.info("INFO - Saving new contact info...");
		return contactInfo;
	}

	@Override
	public ContactInfoResponse updateContactInfo(Long id, ContactInfoRequest request) {
		User loggedUser = this.checks.currentlyLoggedInUser();
		this.checks.isAdminOrLoggedIn(loggedUser.getUsername());
		ContactInfo contactInfo = loadContactInfo(id);
		UpdateStrategy strategy = this.globalUtil.checkStrategy("updateContactInfo");
		contactInfo = (ContactInfo) strategy.update(contactInfo, request);
		this.contactInfoRepository.save(contactInfo);
		log.info("INFO - Contact info for user ID: {} updated", loggedUser.getId());
		return this.contactInfoMapper.toContactInfoResponse(contactInfo);
	}

	private ContactInfo loadContactInfo(Long id) {
		return this.contactInfoRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(
						String.format(ExceptionConstants.NOT_FOUND, "Contact info")));
	}
}
