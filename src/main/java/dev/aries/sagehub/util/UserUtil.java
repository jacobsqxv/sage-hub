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
		log.info("INFO - Getting user with username: {}", username.value());
		return this.userRepository.findByUsername(username.value())
				.orElseThrow(
						() -> new EntityNotFoundException(String.format(NOT_FOUND, USER)));
	}

	public User getUser(Long id) {
		log.info("INFO - Getting user with user ID: {}", id);
		return this.userRepository.findById(id).orElseThrow(
				() -> new EntityNotFoundException(String.format(NOT_FOUND, USER)));
	}

	public boolean userExists(Username username) {
		return this.userRepository
				.existsByUsername(username.value());
	}

	public Object getUserInfo(Long id) {
		Role userRole = getUser(id).getRole();
		switch (userRole.getName()) {
			case SUPER_ADMIN, ADMIN -> {
				return this.adminRepository.findByUserId(id);
			}
			case STAFF -> {
				return this.staffRepository.findByUserId(id);
			}
			case STUDENT -> {
				return this.studentRepository.findByUserId(id);
			}
			case APPLICANT -> {
				return this.applicantRepository.findByUserId(id);
			}
			default -> throw new IllegalArgumentException(String.format(NOT_FOUND, USER));
		}
	}
}
