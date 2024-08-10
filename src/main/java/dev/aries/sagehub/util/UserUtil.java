package dev.aries.sagehub.util;


import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.model.Role;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.model.attribute.Email;
import dev.aries.sagehub.model.attribute.Username;
import dev.aries.sagehub.repository.AdminRepository;
import dev.aries.sagehub.repository.StaffRepository;
import dev.aries.sagehub.repository.StudentRepository;
import dev.aries.sagehub.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static dev.aries.sagehub.constant.ExceptionConstants.NOT_FOUND;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserUtil {

	private static final String USER = "User";
	private final UserRepository userRepository;
	private final AdminRepository adminRepository;
	private final StaffRepository staffRepository;
	private final StudentRepository studentRepository;

	public User getUser(Username username) {
		log.info("Getting user with username: {}", username.value());
		return userRepository.findByUsername(username.value()).orElseThrow(
				() -> new EntityNotFoundException(String.format(NOT_FOUND, USER)));
	}

	public User getUser(Long id) {
		log.info("Getting user with user ID: {}", id);
		return userRepository.findById(id).orElseThrow(
				() -> new EntityNotFoundException(String.format(NOT_FOUND, USER)));
	}

	public User currentlyLoggedInUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Username username = new Username(authentication.getName());
		return getUser(username);
	}

	public boolean userExists(Username username) {
		return userRepository
				.existsByUsername(username.value());
	}

	public String getUserEmail(User user) {
		Role userRole = user.getRole();
		switch (userRole.getName()) {
			case SUPER_ADMIN, ADMIN -> {
				return adminRepository.findPrimaryEmailByUserId(user.getId())
						.orElse(null);
			}
			case STAFF -> {
				return staffRepository.findSecondaryEmailByUserId(user.getId())
						.orElse(null);
			}
			case APPLICANT, STUDENT -> {
				return studentRepository.findSecondaryEmailByUserId(user.getId())
						.orElse(null);
			}
			default -> throw new IllegalArgumentException(String.format(NOT_FOUND, USER));
		}
	}

	public Email getRecipient(User user) {
		String recipient = getUserEmail(user);
		if (recipient == null) {
			throw new IllegalArgumentException(
					String.format(ExceptionConstants.NOT_FOUND, "Email"));
		}
		return new Email(recipient);
	}
}
