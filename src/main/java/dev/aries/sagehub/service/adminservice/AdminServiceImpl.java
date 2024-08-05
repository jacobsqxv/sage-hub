package dev.aries.sagehub.service.adminservice;

import dev.aries.sagehub.dto.request.AdminRequest;
import dev.aries.sagehub.dto.response.AdminResponse;
import dev.aries.sagehub.enums.RoleEnum;
import dev.aries.sagehub.mapper.UserMapper;
import dev.aries.sagehub.model.Admin;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.model.attribute.Password;
import dev.aries.sagehub.model.attribute.Username;
import dev.aries.sagehub.repository.AdminRepository;
import dev.aries.sagehub.service.emailservice.EmailService;
import dev.aries.sagehub.util.Generators;
import dev.aries.sagehub.util.UserFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

/**
 * Implementation of the {@code AdminService} interface.
 * @author Jacobs Agyei
 * @see AdminService
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {

	private final AdminRepository adminRepository;
	private final Generators generators;
	private final UserMapper userMapper;
	private final UserFactory userFactory;
	private final EmailService emailService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AdminResponse addAdmin(AdminRequest request) {
		Username username = generators.generateUsername(request.firstName(), request.lastName());
		Password password = generators.generatePassword(8);
		User user = userFactory.createNewUser(username, password, RoleEnum.ADMIN);
		Admin admin = Admin.builder()
			.firstName(request.firstName())
			.middleName(request.middleName())
			.lastName(request.lastName())
			.primaryEmail(request.primaryEmail().value())
			.profilePictureUrl(request.profilePicture())
			.user(user)
			.build();
		adminRepository.save(admin);
		emailService.sendAccountCreatedEmail(username, password, request.primaryEmail());
		log.info("New admin added with ID: {}", admin.getUser().getUsername());
		return userMapper.toAdminResponse(admin);
	}

}
