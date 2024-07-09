package dev.aries.sagehub.repository;

import java.util.List;
import java.util.Optional;

import dev.aries.sagehub.model.AcademicYear;
import dev.aries.sagehub.model.Voucher;
import dev.aries.sagehub.specification.VoucherSpecification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VoucherRepository extends JpaRepository<Voucher, Long>, JpaSpecificationExecutor<Voucher> {
	Optional<Voucher> findBySerialNumberAndPin(Long serialNumber, String pin);

	boolean existsBySerialNumber(Long serialNumber);

	List<Voucher> findAllByAcademicYear(AcademicYear academicYear);

	default Page<Voucher> findAll(Integer year, String status, Pageable page) {
		return findAll(
				Specification.where(VoucherSpecification.hasStatus(status))
						.and(VoucherSpecification.hasYear(year)),
				page
		);
	}

	Optional<Voucher> findBySerialNumber(Long serialNumber);
}
