package dev.aries.sagehub.util;

import java.util.UUID;

import dev.aries.sagehub.enums.AccountStatus;
import dev.aries.sagehub.enums.RoleEnum;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.model.attribute.Password;
import dev.aries.sagehub.model.attribute.Username;
import dev.aries.sagehub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserFactory {
	private final PasswordEncoder passwordEncoder;
	private final RoleUtil roleUtil;
	private final UserRepository userRepository;

	public User createNewUser(Username username, Password password, RoleEnum roleEnum) {
		User user = User.builder()
				.username(username.value())
				.clientId(generateClientId())
				.hashedPassword(this.passwordEncoder.encode(password.value()))
				.accountEnabled(true)
				.role(this.roleUtil.getRole(roleEnum))
				.failedLoginAttempts(0)
				.status(AccountStatus.ACTIVE)
				.build();
		log.info("INFO: Saving new user: {}...", user.getUsername());
		return user;
	}

	private UUID generateClientId() {
		UUID clientId = UUID.randomUUID();
		while (this.userRepository.existsByClientId(clientId)) {
			clientId = UUID.randomUUID();
		}
		return clientId;
	}
}
