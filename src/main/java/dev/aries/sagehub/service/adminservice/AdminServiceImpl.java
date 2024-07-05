package dev.aries.sagehub.service.adminservice;

import dev.aries.sagehub.dto.request.AdminRequest;
import dev.aries.sagehub.dto.response.BasicUserResponse;
import dev.aries.sagehub.enums.RoleEnum;
import dev.aries.sagehub.model.Admin;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.repository.AdminRepository;
import dev.aries.sagehub.service.emailservice.EmailService;
import dev.aries.sagehub.util.Generators;
import dev.aries.sagehub.util.UserUtil;
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
	private final UserUtil userUtil;
	private final EmailService emailService;

	/**
	 * Adds a new admin.
	 * @param request the request containing the admin information.
	 * @return a BasicUserResponse containing the new admin's information.
	 */
	@Override
	public BasicUserResponse addAdmin(AdminRequest request) {
		String firstname = request.firstname();
		String lastname = request.lastname();
		String username = this.generators.generateUsername(firstname, lastname);
		String password = this.generators.generatePassword();
		User user = this.userUtil.createNewUser(username, password, RoleEnum.ADMIN);
		Admin admin = Admin.builder()
			.firstName(firstname)
			.middleName(request.middleName())
			.lastName(lastname)
			.primaryEmail(request.primaryEmail())
			.profilePictureUrl(request.profilePicture())
			.user(user)
			.build();
		this.adminRepository.save(admin);
		this.emailService.sendAccountCreatedEmail(username, password, request.primaryEmail());
		log.info("INFO - New admin added with ID: {}", admin.getUser().getUsername());
		return this.userUtil.getUserResponse(admin);
	}

}
