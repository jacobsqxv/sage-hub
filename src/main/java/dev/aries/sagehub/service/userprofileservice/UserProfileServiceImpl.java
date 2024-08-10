package dev.aries.sagehub.service.userprofileservice;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.request.UserProfileRequest;
import dev.aries.sagehub.dto.response.UserProfileResponse;
import dev.aries.sagehub.mapper.UserProfileMapper;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.model.UserProfile;
import dev.aries.sagehub.repository.UserProfileRepository;
import dev.aries.sagehub.strategy.UpdateStrategyConfig;
import dev.aries.sagehub.util.Checks;
import dev.aries.sagehub.util.UserUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
/**
 * Implementation of the {@code UserProfileService} interface.
 * @author Jacobs Agyei
 * @see UserProfileService
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {
	private final UserProfileRepository userProfileRepository;
	private final UpdateStrategyConfig updateStrategyConfig;
	private final UserUtil userUtil;
	private final Checks checks;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserProfileResponse getUserProfile(Long userId) {
		User loggedUser = userUtil.currentlyLoggedInUser();
		checks.isAdminOrLoggedIn(loggedUser.getUsername());
		UserProfile userProfile = loadUserProfile(userId);
		return UserProfileMapper.toUserProfileResponse(userProfile);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserProfile addUserProfile(UserProfileRequest request, Long userId) {
		UserProfile newUserProfile = UserProfile.builder()
				.personalInfo(UserProfileMapper.toPersonalInfo(request.personalInfo()))
				.contactInfo(UserProfileMapper.toContactInfo(request.contactInfo()))
				.userId(userId)
				.build();
		log.info("Saving user profile information...");
		userProfileRepository.save(newUserProfile);
		return newUserProfile;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserProfileResponse updateUserProfile(Long userId, UserProfileRequest request) {
		User loggedUser = userUtil.currentlyLoggedInUser();
		checks.isAdminOrLoggedIn(loggedUser.getUsername());
		UserProfile userProfile = loadUserProfile(userId);
		UserProfile updatedUserProfile = (UserProfile) updateStrategyConfig
				.checkStrategy("UserProfile").update(userProfile, request);
		userProfileRepository.save(updatedUserProfile);
		log.info("Profile info for user ID: {} updated", loggedUser.getId());
		return UserProfileMapper.toUserProfileResponse(updatedUserProfile);
	}

	private UserProfile loadUserProfile(Long userId) {
		return userProfileRepository.findByUserId(userId)
				.orElseThrow(() -> new EntityNotFoundException(
						String.format(ExceptionConstants.NO_INFO_FOUND, "profile")));
	}
}
