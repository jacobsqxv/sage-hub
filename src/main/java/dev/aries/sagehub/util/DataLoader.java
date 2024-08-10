package dev.aries.sagehub.util;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.model.Application;
import dev.aries.sagehub.model.Course;
import dev.aries.sagehub.model.Department;
import dev.aries.sagehub.model.EmergencyInfo;
import dev.aries.sagehub.model.Program;
import dev.aries.sagehub.model.Voucher;
import dev.aries.sagehub.repository.ApplicationRepository;
import dev.aries.sagehub.repository.CourseRepository;
import dev.aries.sagehub.repository.DepartmentRepository;
import dev.aries.sagehub.repository.EmergInfoRepository;
import dev.aries.sagehub.repository.ProgramRepository;
import dev.aries.sagehub.repository.VoucherRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import static dev.aries.sagehub.constant.ExceptionConstants.NOT_FOUND;

@Component
@RequiredArgsConstructor
public class DataLoader {
	private final EmergInfoRepository emergInfoRepository;
	private final ApplicationRepository applicationRepository;
	private final DepartmentRepository departmentRepository;
	private final VoucherRepository voucherRepository;
	private final ProgramRepository programRepository;
	private final CourseRepository courseRepository;

	public Department loadDepartment(Long id) {
		return departmentRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(
						String.format(NOT_FOUND, "Department")));
	}

	public Program loadProgram(Long programId) {
		return programRepository.findById(programId)
				.orElseThrow(() -> new EntityNotFoundException(
						String.format(ExceptionConstants.NOT_FOUND, "Program")));
	}

	public Course loadCourse(Long courseId) {
		return courseRepository.findById(courseId)
				.orElseThrow(() -> new IllegalArgumentException(
						String.format(ExceptionConstants.NOT_FOUND, "Course")));
	}

	public Voucher loadVoucher(Long serialNumber) {
		return voucherRepository.findBySerialNumber(serialNumber)
				.orElseThrow(() -> new IllegalArgumentException(
						String.format(ExceptionConstants.NOT_FOUND, "Voucher")));
	}

	public EmergencyInfo loadEmergencyInfo(Long id) {
		return emergInfoRepository.findByUserId(id)
				.orElseThrow(() -> new EntityNotFoundException(
						String.format(ExceptionConstants.NO_INFO_FOUND, "emergency contact")));
	}

	public Application loadApplicationById(Long applicationId) {
		return applicationRepository.findById(applicationId)
				.orElseThrow(() -> new IllegalArgumentException(
						String.format(ExceptionConstants.NOT_FOUND, "Applicant")));
	}

	public Application loadApplicationByUserId(Long userId) {
		return applicationRepository.findByStudentUserId(userId)
				.orElseThrow(() -> new IllegalArgumentException(
						String.format(ExceptionConstants.NOT_FOUND, "Applicant")));
	}
}
