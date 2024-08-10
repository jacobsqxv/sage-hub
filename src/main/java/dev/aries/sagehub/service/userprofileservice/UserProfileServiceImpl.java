package dev.aries.sagehub.service.userinfoservice;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.dto.request.UserInfoRequest;
import dev.aries.sagehub.dto.response.UserInfoResponse;
import dev.aries.sagehub.mapper.UserInfoMapper;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.model.UserProfile;
import dev.aries.sagehub.repository.UserProfileRepository;
import dev.aries.sagehub.strategy.UpdateStrategy;
import dev.aries.sagehub.strategy.UpdateStrategyConfig;
import dev.aries.sagehub.util.Checks;
import dev.aries.sagehub.util.UserUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
/**
 * Implementation of the {@code BasicInfoInterface} interface.
 * @author Jacobs Agyei
 * @see UserInfoService
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {
	private final UserProfileRepository userProfileRepository;
	private final UpdateStrategyConfig strategyConfig;
	private final UserUtil userUtil;
	private final Checks checks;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserInfoResponse getUserInfo(Long userId) {
		User loggedUser = userUtil.currentlyLoggedInUser();
		checks.isAdminOrLoggedIn(loggedUser.getUsername());
		UserProfile userProfile = loadUserInfo(userId);
		return UserInfoMapper.toUserInfoResponse(userProfile);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserProfile addUserInfo(UserInfoRequest request, Long userId) {
		UserProfile newUserProfile = UserProfile.builder()
				.personalInfo(UserInfoMapper.toPersonalInfo(request.personalInfo()))
				.contactInfo(UserInfoMapper.toContactInfo(request.contactInfo()))
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
	public UserInfoResponse updateUserInfo(Long userId, UserInfoRequest request) {
		User loggedUser = userUtil.currentlyLoggedInUser();
		checks.isAdminOrLoggedIn(loggedUser.getUsername());
		UserProfile userProfile = loadUserInfo(userId);
		UpdateStrategy strategy = strategyConfig.checkStrategy("updateUserInfo");
		userProfile = (UserProfile) strategy.update(userProfile, request);
		userProfileRepository.save(userProfile);
		log.info("Profile info for user ID: {} updated", loggedUser.getId());
		return UserInfoMapper.toUserInfoResponse(userProfile);
	}

	private UserProfile loadUserInfo(Long userId) {
		return userProfileRepository.findByUserId(userId)
				.orElseThrow(() -> new EntityNotFoundException(
						String.format(ExceptionConstants.NO_INFO_FOUND, "profile")));
	}
}
