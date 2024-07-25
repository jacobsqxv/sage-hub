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
 * AdminServiceImpl is a service class that implements the AdminService interface. It
 * provides methods for managing admins, including adding a new admin.
 *
 * @author Jacobs Agyei
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
	 * Adds a new admin.
	 * @param request the request containing the admin information.
	 * @return a BasicUserResponse containing the new admin's information.
	 */
	@Override
	public AdminResponse addAdmin(AdminRequest request) {
		Username username = this.generators.generateUsername(request.firstname(), request.lastname());
		Password password = this.generators.generatePassword(8);
		User user = this.userFactory.createNewUser(username, password, RoleEnum.ADMIN);
		Admin admin = Admin.builder()
			.firstName(request.firstname())
			.middleName(request.middleName())
			.lastName(request.lastname())
			.primaryEmail(request.primaryEmail().value())
			.profilePictureUrl(request.profilePicture())
			.user(user)
			.build();
		this.adminRepository.save(admin);
		this.emailService.sendAccountCreatedEmail(username, password, request.primaryEmail());
		log.info("INFO - New admin added with ID: {}", admin.getUser().getUsername());
		return this.userMapper.toAdminResponse(admin);
	}

}
