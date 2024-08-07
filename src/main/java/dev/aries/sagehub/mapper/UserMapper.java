package dev.aries.sagehub.mapper;

import dev.aries.sagehub.dto.response.AdminResponse;
import dev.aries.sagehub.dto.response.AuthUserResponse;
import dev.aries.sagehub.dto.response.BasicUserResponse;
import dev.aries.sagehub.dto.response.UserResponse;
import dev.aries.sagehub.model.Admin;
import dev.aries.sagehub.model.BaseUser;
import dev.aries.sagehub.model.User;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
	private final BasicInfoMapper basicInfoMapper;
	private final ContactInfoMapper contactInfoMapper;
	private final EmergencyContactMapper emergencyContactMapper;

	public AuthUserResponse toAuthUserResponse(User user) {
		return new AuthUserResponse(
				user.getUsername(),
				user.getRole().getName().toString(),
				user.getStatus().toString(),
				user.getLastLogin(),
				user.isAccountEnabled()
		);
	}

	public UserResponse toUserResponse(BaseUser baseUser) {
		return UserResponse.builder()
				.basicInfo(basicInfoMapper
						.toBasicInfoResponse(baseUser.getBasicInfo()))
				.contactInfo(contactInfoMapper
						.toContactInfoResponse(baseUser.getContactInfo()))
				.emergencyContact(emergencyContactMapper
						.toEmergencyContactResponse(baseUser.getEmergencyContact()))
				.userId(baseUser.getUser().getId())
				.role(baseUser.getUser().getRole().getName().toString())
				.status(baseUser.getUser().getStatus().toString())
				.memberId(baseUser.getId())
				.build();
	}

	public BasicUserResponse toBasicUserResponse(BaseUser baseUser) {
		return BasicUserResponse.builder()
				.userId(baseUser.getUser().getId())
				.memberId(baseUser.getId())
				.profilePicture(baseUser.getBasicInfo().getProfilePictureUrl())
				.fullName(baseUser.getBasicInfo().fullName())
				.email(baseUser.getContactInfo().getSecondaryEmail())
				.role(baseUser.getUser().getRole().getName())
				.gender(baseUser.getBasicInfo().getGender())
				.status(baseUser.getUser().getStatus())
				.build();
	}

	public AdminResponse toAdminResponse(Admin admin) {
		return new AdminResponse(
				admin.getUser().getId(),
				admin.getProfilePictureUrl(),
				admin.fullName(),
				admin.getUser().getUsername(),
				admin.getPrimaryEmail(),
				admin.getUser().getRole().getName().toString(),
				admin.getUser().getStatus()
		);
	}
}
