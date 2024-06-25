package dev.aries.sagehub.service.adminservice;

import dev.aries.sagehub.dto.request.AdminRequest;
import dev.aries.sagehub.dto.response.BasicUserResponse;
import dev.aries.sagehub.enums.RoleEnum;
import dev.aries.sagehub.model.Admin;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.repository.AdminRepository;
import dev.aries.sagehub.util.GlobalUtil;
import dev.aries.sagehub.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

/**
 * AdminServiceImpl is a service class that implements the AdminService interface. It
 * provides methods for managing admins, including adding a new admin.
 *
 * @author Jacobs
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {

	private final AdminRepository adminRepository;

	private final UserUtil userUtil;

	private final GlobalUtil globalUtil;

	/**
	 * Adds a new admin.
	 * @param request the request containing the admin information.
	 * @return a BasicUserResponse containing the new admin's information.
	 */
	@Override
	public BasicUserResponse addAdmin(AdminRequest request) {
		String firstname = request.firstname();
		String lastname = request.lastname();
		User user = this.userUtil.createNewUser(firstname, lastname, RoleEnum.ADMIN);
		Admin admin = Admin.builder()
			.firstName(firstname)
			.middleName(request.middleName())
			.lastName(lastname)
			.primaryEmail(request.primaryEmail())
			.profilePictureUrl(request.profilePicture())
			.user(user)
			.build();
		this.adminRepository.save(admin);
		log.info("INFO - New admin added with ID: {}", admin.getUser().getUsername());
		return this.globalUtil.getUserResponse(admin);
	}

}
