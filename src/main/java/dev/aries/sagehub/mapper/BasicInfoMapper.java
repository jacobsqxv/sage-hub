package dev.aries.sagehub.mapper;

import dev.aries.sagehub.dto.request.BasicInfoRequest;
import dev.aries.sagehub.dto.response.BasicInfoResponse;
import dev.aries.sagehub.enums.Gender;
import dev.aries.sagehub.enums.MaritalStatus;
import dev.aries.sagehub.enums.Title;
import dev.aries.sagehub.model.BasicInfo;

import org.springframework.stereotype.Component;

@Component
public class BasicInfoMapper {
	public BasicInfoResponse toBasicInfoResponse(BasicInfo basicInfo) {
		return new BasicInfoResponse(
				basicInfo.getProfilePictureUrl(),
				basicInfo.getTitle().getValue(),
				basicInfo.fullName(),
				basicInfo.getMaritalStatus().toString(),
				basicInfo.getGender().toString(),
				basicInfo.getDateOfBirth()
		);
	}

	public BasicInfo toBasicInfo(BasicInfoRequest basicInfo) {
		return BasicInfo.builder()
				.profilePictureUrl(basicInfo.profilePicture())
				.title(Title.valueOf(basicInfo.title().toUpperCase()))
				.firstName(basicInfo.firstname())
				.lastName(basicInfo.lastname())
				.middleName(basicInfo.middleName())
				.maritalStatus(MaritalStatus.valueOf(
						basicInfo.maritalStatus().toUpperCase()))
				.gender(Gender.valueOf(basicInfo.gender().toUpperCase()))
				.dateOfBirth(basicInfo.dateOfBirth())
				.build();
	}
}
