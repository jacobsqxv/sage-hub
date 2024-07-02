package dev.aries.sagehub.service.authservice;

import dev.aries.sagehub.dto.request.AuthRequest;
import dev.aries.sagehub.dto.request.ResetPassword;
import dev.aries.sagehub.dto.request.ResetPasswordRequest;
import dev.aries.sagehub.dto.response.AuthResponse;
import dev.aries.sagehub.dto.response.GenericResponse;

public interface AuthService {

	AuthResponse authenticateUser(AuthRequest request);

	GenericResponse resetPasswordRequest(ResetPasswordRequest request);

	GenericResponse resetPassword(String token, ResetPassword request);
}
