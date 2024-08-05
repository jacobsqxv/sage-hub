package dev.aries.sagehub.service.authservice;

import dev.aries.sagehub.dto.request.AuthRequest;
import dev.aries.sagehub.dto.request.RefreshTokenRequest;
import dev.aries.sagehub.dto.request.ResetPassword;
import dev.aries.sagehub.dto.request.ResetPasswordRequest;
import dev.aries.sagehub.dto.request.VoucherRequest;
import dev.aries.sagehub.dto.response.AuthResponse;
import dev.aries.sagehub.dto.response.GenericResponse;

/**
 * The {@code AuthService} interface provides methods for authentication and authorization.
 * It includes functionality for user authentication, voucher-based login, password reset,
 * and token renewal.
 * <p>
 * Implementations of this interface are responsible for handling the business logic
 * related to authentication and authorization.
 * </p>
 * @author  Jacobs Agyei
 */
public interface AuthService {

	/**
	 * Authenticates a user.
	 * <p>
	 * This method takes an {@code AuthRequest} object containing the username and password,
	 * and returns an {@code AuthResponse} object containing the authentication tokens and user information.
	 * </p>
	 * @param request the {@code AuthRequest} containing the username and password.
	 * @return an {@code AuthResponse} containing the authentication tokens and user information.
	 * @throws IllegalArgumentException if the request is null or contains invalid data.
	 */
	AuthResponse authenticateUser(AuthRequest request);

	/**
	 * Authenticates an applicant using a voucher.
	 * <p>
	 * This method takes a {@code VoucherRequest} object containing the serial number and pin,
	 * and returns an {@code AuthResponse} object containing the authentication tokens and user information.
	 * </p>
	 * @param request the {@code VoucherRequest} containing the serial number and pin.
	 * @return an {@code AuthResponse} containing the authentication tokens and user information.
	 * @throws IllegalArgumentException if the request is null or contains invalid data.
	 */
	AuthResponse loginWithVoucher(VoucherRequest request);

	/**
	 * Requests a password reset.
	 * <p>
	 * This method takes a {@code ResetPasswordRequest} object containing the email address,
	 * and sends a password reset link to the specified email.
	 * </p>
	 * @param request the {@code ResetPasswordRequest} containing the email address.
	 * @return a {@code GenericResponse} indicating the result of the password reset request.
	 * @throws IllegalArgumentException if the request is null or contains invalid data.
	 */
	GenericResponse resetPasswordRequest(ResetPasswordRequest request);

	/**
	 * Resets the password.
	 * <p>
	 * This method takes a token and a {@code ResetPassword} object containing the new password,
	 * and updates the user's password.
	 * </p>
	 * @param token   the token for password reset.
	 * @param request the {@code ResetPassword} containing the new password.
	 * @return a {@code GenericResponse} indicating the result of the password reset.
	 * @throws IllegalArgumentException if the token or request is null or contains invalid data.
	 */
	GenericResponse resetPassword(String token, ResetPassword request);

	/**
	 * Renews the refresh token.
	 * <p>
	 * This method takes a {@code RefreshTokenRequest} object containing the refresh token,
	 * and returns a new {@code AuthResponse} object containing the new authentication tokens.
	 * </p>
	 * @param request the {@code RefreshTokenRequest} containing the refresh token.
	 * @return an {@code AuthResponse} containing the new authentication tokens.
	 * @throws IllegalArgumentException if the request is null or contains invalid data.
	 */
	AuthResponse renewRefreshToken(RefreshTokenRequest request);
}
