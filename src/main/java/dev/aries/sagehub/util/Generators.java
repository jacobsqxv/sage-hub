package dev.aries.sagehub.util;

import java.security.SecureRandom;
import java.util.Calendar;

import dev.aries.sagehub.repository.CourseRepository;
import dev.aries.sagehub.repository.DepartmentRepository;
import dev.aries.sagehub.repository.StaffRepository;
import dev.aries.sagehub.repository.StudentRepository;
import dev.aries.sagehub.repository.UserRepository;
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
	private final StudentRepository studentRepository;
	private final StaffRepository staffRepository;
	private final DepartmentRepository departmentRepository;

	private static final SecureRandom random = new SecureRandom();
	private static final PasswordGenerator generator = new PasswordGenerator();
	private static final CharacterRule digits = new CharacterRule(EnglishCharacterData.Digit);
	private static final CharacterRule alphabetical = new CharacterRule(EnglishCharacterData.Alphabetical);
	private final CourseRepository courseRepository;

	public String generatePassword() {
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
		return passwordGenerator.generatePassword(12, digits, alphabetical, splCharRule);
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

	public String generateUsername(String firstName, String lastName) {
		lastName = lastName.replace("-", "");
		String username = String.format("%s%s", firstName.charAt(0), lastName).toLowerCase();
		int suffix = 1;
		while (this.userRepository.existsByUsername(username)) {
			username = username.replaceAll("\\d+$", "");
			username = String.format("%s%d", username, suffix++);
		}
		return username;
	}

	public String generateUserEmail(String username, String domain) {
		domain = domain.toLowerCase();
		return String.format("%s.%s%s@sagehub.xyz", username, domain.substring(0, 2), domain.charAt(3));
	}

	public Long generateUniqueId(boolean isStudent) {
		Long id = Long.valueOf(generateId(isStudent));
		if (isStudent) {
			while (this.studentRepository.existsById(id)) {
				id = Long.valueOf(generateId(true));
			}
		}
		else {
			while (this.staffRepository.existsById(id)) {
				id = Long.valueOf(generateId(false));
			}
		}
		return id;
	}

	public String generateDeptCode() {
		String code = generator.generatePassword(3, digits);
		while (this.departmentRepository.existsByCode(code)) {
			code = generator.generatePassword(3, digits);
		}
		return code;
	}

	public String generateCourseCode(String name) {
		String prefix;
		if (name.toLowerCase().startsWith("introduction to ")) {
			String[] words = name.toLowerCase().substring("introduction to ".length()).split(" ");
			prefix = words[0].length() < 4 ? words[0].toUpperCase() : words[0].substring(0, 4).toUpperCase();
		} else {
			prefix = name.substring(0, 4).toUpperCase();
		}
 		String suffix = generator.generatePassword(3, digits);
		String code = String.format("%s %s", prefix, suffix);
		while (this.courseRepository.existsByCode(code)) {
			suffix = generator.generatePassword(3, digits);
			code = String.format("%s%s", prefix, suffix);
		}
		return code;
	}
}
