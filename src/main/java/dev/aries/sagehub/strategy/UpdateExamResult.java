package dev.aries.sagehub.strategy;

import java.util.Objects;

import dev.aries.sagehub.dto.request.ApplicantResultRequest;
import dev.aries.sagehub.model.ExamResult;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UpdateApplicantResults implements UpdateStrategy<ExamResult, ApplicantResultRequest> {

	@Override
	public ExamResult update(ExamResult entity, ApplicantResultRequest request) {
		log.info("Updating ApplicantResult: {}", entity.getId());
		if (!Objects.equals(request.indexNumber().value(), entity.getIndexNumber())) {
			entity.setIndexNumber(request.indexNumber().value());
		}
		if (!Objects.equals(request.resultType(), entity.getType())) {
			entity.setType(request.resultType());
		}
		if (!Objects.equals(request.schoolName(), entity.getSchoolName())) {
			entity.setSchoolName(request.schoolName());
		}
		if (!Objects.equals(request.year(), entity.getYear())) {
			entity.setYear(request.year());
		}
		return entity;
	}
}
