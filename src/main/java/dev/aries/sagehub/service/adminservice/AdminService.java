package dev.aries.sagehub.service.adminservice;

import dev.aries.sagehub.dto.request.AdminRequest;
import dev.aries.sagehub.dto.response.BasicUserResponse;

public interface AdminService {

	BasicUserResponse addAdmin(AdminRequest request);

}
