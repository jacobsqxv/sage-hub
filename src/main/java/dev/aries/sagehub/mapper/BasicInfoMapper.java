package dev.aries.sagehub.mapper;

import dev.aries.sagehub.dto.response.BasicInfoResponse;
import dev.aries.sagehub.model.BasicInfo;

import org.springframework.stereotype.Component;

@Component
public class BasicInfoMapper {
	public BasicInfoResponse toBasicInfoResponse(BasicInfo basicInfo) {
		return new BasicInfoResponse(
				basicInfo.getId(),
				basicInfo.getProfilePictureUrl(),
				basicInfo.getTitle().toString(),
				basicInfo.fullName(),
				basicInfo.getMaritalStatus().toString(),
				basicInfo.getGender().toString(),
				basicInfo.getDateOfBirth()
		);
	}

}
