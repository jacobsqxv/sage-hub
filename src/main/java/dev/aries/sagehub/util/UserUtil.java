package dev.aries.sagehub.util;


import dev.aries.sagehub.model.Role;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.model.attribute.Username;
import dev.aries.sagehub.repository.AdminRepository;
import dev.aries.sagehub.repository.ApplicantRepository;
import dev.aries.sagehub.repository.StaffRepository;
import dev.aries.sagehub.repository.StudentRepository;
import dev.aries.sagehub.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
	private final ApplicantRepository applicantRepository;

	public User getUser(Username username) {
		log.info("Getting user with username: {}", username.value());
		return userRepository.findByUsername(username.value())
				.orElseThrow(
						() -> new EntityNotFoundException(String.format(NOT_FOUND, USER)));
	}

	public User getUser(Long id) {
		log.info("Getting user with user ID: {}", id);
		return userRepository.findById(id).orElseThrow(
				() -> new EntityNotFoundException(String.format(NOT_FOUND, USER)));
	}

	public boolean userExists(Username username) {
		return userRepository
				.existsByUsername(username.value());
	}

	public String getUserEmail(Long id) {
		Role userRole = getUser(id).getRole();
		switch (userRole.getName()) {
			case SUPER_ADMIN, ADMIN -> {
				return adminRepository.findPrimaryEmailByUserId(id)
						.orElse(null);
			}
			case STAFF -> {
				return staffRepository.findSecondaryEmailByUserId(id)
						.orElse(null);
			}
			case STUDENT -> {
				return studentRepository.findSecondaryEmailByUserId(id)
						.orElse(null);
			}
			case APPLICANT -> {
				return applicantRepository.findSecondaryEmailByUserId(id)
						.orElse(null);
			}
			default -> throw new IllegalArgumentException(String.format(NOT_FOUND, USER));
		}
	}
}
