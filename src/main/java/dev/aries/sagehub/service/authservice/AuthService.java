package dev.aries.sagehub.service.authservice;

import dev.aries.sagehub.dto.request.AuthRequest;
import dev.aries.sagehub.dto.request.ResetPassword;
import dev.aries.sagehub.dto.request.ResetPasswordRequest;
import dev.aries.sagehub.dto.request.VoucherRequest;
import dev.aries.sagehub.dto.response.AuthResponse;
import dev.aries.sagehub.dto.response.GenericResponse;

public interface AuthService {
	/**
	 * Authenticates a user.
	 * @param request the request containing the username and password.
	 * @return an AuthResponse containing the authentication tokens and user information.
	 */
	AuthResponse authenticateUser(AuthRequest request);
	/**
	 * Authenticates an applicant using the voucher.
	 * @param request the request containing the serial number and pin.
	 * @return an AuthResponse containing the authentication tokens and user information.
	 */
	AuthResponse loginWithVoucher(VoucherRequest request);

	GenericResponse resetPasswordRequest(ResetPasswordRequest request);

	GenericResponse resetPassword(String token, ResetPassword request);

	AuthResponse renewRefreshToken(String token);
}
