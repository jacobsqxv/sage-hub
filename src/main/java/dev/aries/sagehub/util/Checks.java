package dev.aries.sagehub.util;

import java.util.Objects;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.enums.RoleEnum;
import dev.aries.sagehub.exception.UnauthorizedAccessException;
import dev.aries.sagehub.model.User;
import dev.aries.sagehub.model.attribute.Password;
import dev.aries.sagehub.model.attribute.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Checks {
	private final PasswordEncoder passwordEncoder;
	private static final PhoneNumberUtil PHONE_NUMBER_UTIL = PhoneNumberUtil.getInstance();
	private static final String DEFAULT_COUNTRY_CODE = "GH";

	public static void validateLoggedInUserId(User user, Long id) {
		log.info("loggedInUser: {}, userId: {}", user.getId(), id);
		if (!Objects.equals(user.getId(), id)) {
			throw new UnauthorizedAccessException(ExceptionConstants.UNAUTHORIZED_ACCESS);
		}
	}

	public static void validateLoggedInUser(User loggedInUser, User owner) {
		log.info("loggedInUser: {}, resourceOwner: {}", loggedInUser.getUsername(), owner.getUsername());
		if (!Objects.equals(loggedInUser, owner)) {
			throw new UnauthorizedAccessException(ExceptionConstants.UNAUTHORIZED_ACCESS);
		}
	}

	public static <E extends Enum<E>> void checkIfEnumExists(Class<E> enumClass, String request) {
		try {
			Enum.valueOf(enumClass, request.toUpperCase());
		}
		catch (IllegalArgumentException ex) {
			throw new IllegalArgumentException(
					String.format(ExceptionConstants.ENUM_VALUE_INVALID, request));
		}
	}

	public void isAdminOrLoggedIn(String username) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String authUser = authentication.getName();
		RoleEnum role = authentication.getAuthorities().stream()
				.findFirst()
				.map((authority) -> authority.getAuthority().replace("SCOPE_", ""))
				.map(RoleEnum::valueOf)
				.orElseThrow(() -> new UnauthorizedAccessException(
						ExceptionConstants.UNAUTHORIZED_ACCESS));
		if (!(isAdmin(role) || authUser.equals(username))) {
			log.info("Unauthorized access to this resource");
			throw new UnauthorizedAccessException(ExceptionConstants.UNAUTHORIZED_ACCESS);
		}
	}

	public static void checkAdmins(RoleEnum role) {
		if (!(role.equals(RoleEnum.ADMIN) ||
				role.equals(RoleEnum.SUPER_ADMIN))) {
			log.info("Unauthorized access");
			throw new UnauthorizedAccessException(ExceptionConstants.UNAUTHORIZED_ACCESS);
		}
	}

	public boolean isPasswordEqual(User user, Password password) {
		return passwordEncoder.matches(password.value(), user.getHashedPassword());
	}

	public static boolean isAdmin(RoleEnum role) {
		return role.equals(RoleEnum.ADMIN) || role.equals(RoleEnum.SUPER_ADMIN);
	}

	public static boolean isValidPhoneNumber(String number, String countryCode) {
		if (countryCode == null || countryCode.isEmpty()) {
			countryCode = DEFAULT_COUNTRY_CODE;
		}
		try {
			Phonenumber.PhoneNumber phoneNumber = PHONE_NUMBER_UTIL.parse(number, countryCode);
			return PHONE_NUMBER_UTIL.isValidNumberForRegion(phoneNumber, countryCode);
		}
		catch (NumberParseException ex) {
			return false;
		}
	}

	public static String toIntlFormat(PhoneNumber phoneNo) {
		try {
			Phonenumber.PhoneNumber intl = PHONE_NUMBER_UTIL.parse(phoneNo.number(), phoneNo.countryCode());
			return String.valueOf(intl.getCountryCode()) + intl.getNationalNumber();
		}
		catch (NumberParseException ex) {
			throw new IllegalArgumentException(ExceptionConstants.UNEXPECTED_VALUE);
		}
	}
}
