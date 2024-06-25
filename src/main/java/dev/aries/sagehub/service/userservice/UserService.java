package dev.aries.sagehub.service.userservice;

import dev.aries.sagehub.dto.request.AddUserRequest;
import dev.aries.sagehub.dto.request.ContactInfoRequest;
import dev.aries.sagehub.dto.request.EmergencyContactRequest;
import dev.aries.sagehub.dto.request.PasswordChangeRequest;
import dev.aries.sagehub.dto.response.BasicUserResponse;
import dev.aries.sagehub.dto.response.ContactInfoResponse;
import dev.aries.sagehub.dto.response.EmergencyContactResponse;

public interface UserService {

	BasicUserResponse changePassword(Long id, PasswordChangeRequest request);

	BasicUserResponse addFacultyMember(AddUserRequest request, String role);

	ContactInfoResponse getContactInfo(Long id);

	ContactInfoResponse updateContactInfo(Long id, ContactInfoRequest request);

	EmergencyContactResponse getEmergencyContact(Long id);

	EmergencyContactResponse updateEmergencyContact(Long id, EmergencyContactRequest request);
}
