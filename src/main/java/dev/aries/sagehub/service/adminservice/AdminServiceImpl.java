package dev.aries.sagehub.service.adminservice;

import dev.aries.sagehub.dto.request.AdminRequest;
import dev.aries.sagehub.dto.response.AdminResponse;
import dev.aries.sagehub.enums.RoleEnum;
import dev.aries.sagehub.factory.ModelFactory;
import dev.aries.sagehub.mapper.UserMapper;
import dev.aries.sagehub.mapper.UserProfileMapper;
import dev.aries.sagehub.model.Admin;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.model.attribute.Name;
import dev.aries.sagehub.model.attribute.Password;
import dev.aries.sagehub.model.attribute.Username;
import dev.aries.sagehub.repository.AdminRepository;
import dev.aries.sagehub.service.emailservice.EmailService;
import dev.aries.sagehub.util.Generators;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

/**
 * Implementation of the {@code AdminService} interface.
 *
 * @author Jacobs Agyei
 * @see AdminService
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {

	private final AdminRepository adminRepository;
	private final ModelFactory modelFactory;
	private final EmailService emailService;
	private final Generators generators;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AdminResponse addAdmin(AdminRequest request) {
		Name name = UserProfileMapper.toName(request.name());
		Username username = generators.generateUsername(name.firstName(), name.lastName());
		Password password = Generators.generatePassword(8);
		User user = modelFactory.createNewUser(username, password, RoleEnum.ADMIN);
		Admin admin = Admin.builder()
				.name(name)
				.primaryEmail(request.primaryEmail().value())
				.profilePicture(request.profilePicture())
				.user(user)
				.build();
		adminRepository.save(admin);
		emailService.sendAccountCreatedEmail(username, password, request.primaryEmail());
		log.info("New admin added with ID: {}", admin.getUser().getUsername());
		return UserMapper.toAdminResponse(admin);
	}

}
