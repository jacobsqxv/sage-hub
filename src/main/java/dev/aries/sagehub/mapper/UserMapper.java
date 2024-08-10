package dev.aries.sagehub.mapper;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.response.AdminResponse;
import dev.aries.sagehub.dto.response.AuthUserResponse;
import dev.aries.sagehub.dto.response.EmergencyInfoResponse;
import dev.aries.sagehub.dto.response.UserResponse;
import dev.aries.sagehub.model.Admin;
import dev.aries.sagehub.model.BaseUser;
import dev.aries.sagehub.model.EmergencyInfo;
import dev.aries.sagehub.model.User;

public final class UserMapper {

	public static AuthUserResponse toAuthUserResponse(User user) {
		return new AuthUserResponse(
				user.getUsername(),
				user.getRole().getName().toString(),
				user.getStatus().toString(),
				user.getLastLogin(),
				user.isAccountEnabled()
		);
	}

	public static EmergencyInfoResponse toEmergInfoResponse(EmergencyInfo EmergencyInfo) {
		return new EmergencyInfoResponse(
				EmergencyInfo.getId(),
				EmergencyInfo.getFullName(),
				EmergencyInfo.getRelationship(),
				EmergencyInfo.getPhoneNumber(),
				EmergencyInfo.getAddress()
		);
	}

	public static UserResponse toUserResponse(BaseUser baseUser) {
		return UserResponse.builder()
				.userInfo(UserProfileMapper
						.toUserProfileResponse(baseUser.getUserProfile()))
				.emergencyInfo(UserMapper
						.toEmergInfoResponse(baseUser.getEmergencyInfo()))
				.userId(baseUser.getUser().getId())
				.role(baseUser.getUser().getRole().getName().toString())
				.status(baseUser.getUser().getStatus().toString())
				.memberId(baseUser.getId())
				.build();
	}

	public static AdminResponse toAdminResponse(Admin admin) {
		return new AdminResponse(
				admin.getUser().getId(),
				admin.getProfilePicture(),
				admin.fullName(),
				admin.getUser().getUsername(),
				admin.getPrimaryEmail(),
				admin.getUser().getRole().getName().toString(),
				admin.getUser().getStatus()
		);
	}

	private UserMapper() {
		throw new IllegalStateException(ExceptionConstants.UTILITY_CLASS);
	}
}
