package dev.aries.sagehub.util;

import dev.aries.sagehub.enums.AccountStatus;
import dev.aries.sagehub.enums.RoleEnum;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.model.attribute.Username;
import dev.aries.sagehub.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFactory {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final RoleUtil roleUtil;

	public User createNewUser(Username username, String password, RoleEnum roleEnum) {
		User user = User.builder()
				.username(username.value())
				.hashedPassword(this.passwordEncoder.encode(password))
				.accountEnabled(true)
				.role(this.roleUtil.getRole(roleEnum))
				.failedLoginAttempts(0)
				.status(AccountStatus.ACTIVE)
				.build();
		return this.userRepository.save(user);
	}
}
