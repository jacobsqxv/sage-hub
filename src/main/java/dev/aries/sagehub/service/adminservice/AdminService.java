package dev.aries.sagehub.service.adminservice;

import dev.aries.sagehub.dto.request.AdminRequest;
import dev.aries.sagehub.dto.response.AdminResponse;

public interface AdminService {

	AdminResponse addAdmin(AdminRequest request);

}
