package dev.aries.sagehub.service.authservice;

import dev.aries.sagehub.dto.request.AuthRequest;
import dev.aries.sagehub.dto.response.AuthResponse;

public interface AuthService {

	AuthResponse authenticateUser(AuthRequest request);

}
