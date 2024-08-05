package dev.aries.sagehub.util;

import java.security.SecureRandom;
import java.util.Calendar;

import dev.aries.sagehub.constant.Patterns;
import dev.aries.sagehub.model.attribute.Email;
import dev.aries.sagehub.model.attribute.Password;
import dev.aries.sagehub.model.attribute.Username;
import dev.aries.sagehub.repository.CourseRepository;
import dev.aries.sagehub.repository.DepartmentRepository;
import dev.aries.sagehub.repository.StaffRepository;
import dev.aries.sagehub.repository.UserRepository;
import dev.aries.sagehub.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import org.springframework.stereotype.Component;

import static dev.aries.sagehub.constant.ExceptionConstants.UNEXPECTED_VALUE;

@Component
@RequiredArgsConstructor
@Slf4j
public class Generators {

	private final UserRepository userRepository;
	private final VoucherRepository voucherRepository;
	private final StaffRepository staffRepository;
	private final DepartmentRepository departmentRepository;

	private static final SecureRandom random = new SecureRandom();
	private static final PasswordGenerator generator = new PasswordGenerator();
	private static final CharacterRule digits = new CharacterRule(EnglishCharacterData.Digit);
	private static final CharacterRule alphabetical = new CharacterRule(EnglishCharacterData.Alphabetical);
	private final CourseRepository courseRepository;

	public Password generatePassword(int length) {
		length = Math.max(length, 8);
		CharacterData specialChars = new CharacterData() {
			public String getErrorCode() {
				return UNEXPECTED_VALUE;
			}

			public String getCharacters() {
				return "&@#$%?";
			}
		};
		CharacterRule splCharRule = new CharacterRule(specialChars);
		PasswordGenerator passwordGenerator = new PasswordGenerator();
		String password;
		do {
			password = passwordGenerator.generatePassword(length, digits, alphabetical, splCharRule);
		} while (!password.matches(Patterns.PASSWORD));
		return new Password(password);
	}

	private String generateId(boolean isStudent) {
		String randomDigits = generator.generatePassword(5, digits);
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		StringBuilder idBuilder = new StringBuilder();
		if (isStudent) {
			idBuilder.append(random.nextInt(5, 10));
		}
		else {
			idBuilder.append(random.nextInt(1, 5));
		}
		idBuilder.append(randomDigits).append(String.format("%02d", currentYear % 100));
		return idBuilder.toString();
	}

	public Username generateUsername(String firstName, String lastName) {
		lastName = lastName.replace("-", "");
		String username = String.format("%s%s", firstName.charAt(0), lastName).toLowerCase();
		int suffix = 1;
		while (userRepository.existsByUsername(username)) {
			username = username.replaceAll("\\d+$", "");
			username = String.format("%s%d", username, suffix++);
		}
		return new Username(username);
	}

	public Email generateUserEmail(String username, String domain) {
		domain = domain.toLowerCase();
		String email = String.format("%s.%s%s@sagehub.xyz", username, domain.substring(0, 2), domain.charAt(3));
		return new Email(email);
	}

	public Long generateUniqueId(boolean isStudent) {
		Long id = Long.valueOf(generateId(isStudent));
		if (isStudent) {
			while (voucherRepository.existsBySerialNumber(id)) {
				id = Long.valueOf(generateId(true));
			}
		}
		else {
			while (staffRepository.existsById(id)) {
				id = Long.valueOf(generateId(false));
			}
		}
		return id;
	}

	public String generateDeptCode() {
		String code = generator.generatePassword(3, digits);
		while (departmentRepository.existsByCode(code)) {
			code = generator.generatePassword(3, digits);
		}
		return code;
	}

	public String generateCourseCode(String name) {
		String prefix;
		name = name.toLowerCase();
		if (name.startsWith("introduction to ")) {
			String[] words = name.substring("introduction to ".length()).split(" ");
			prefix = (words[0].length() < 4) ? words[0].toUpperCase() : words[0].substring(0, 4);
		}
		else {
			prefix = name.substring(0, 4);
		}
		prefix = prefix.toUpperCase();
		String suffix = generator.generatePassword(3, digits);
		String code = String.format("%s %s", prefix, suffix);
		while (courseRepository.existsByCode(code)) {
			suffix = generator.generatePassword(3, digits);
			code = String.format("%s%s", prefix, suffix);
		}
		return code;
	}

	public String generateToken(int length) {
		return generator.generatePassword(length, digits, alphabetical);
	}
}
