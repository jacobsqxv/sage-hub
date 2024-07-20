package dev.aries.sagehub.specification;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.enums.VoucherStatus;
import dev.aries.sagehub.model.Voucher;

import org.springframework.data.jpa.domain.Specification;

public final class VoucherSpecification {
	private VoucherSpecification() {
		throw new IllegalStateException(ExceptionConstants.UTILITY_CLASS);
	}

	public static Specification<Voucher> hasYear(Integer year) {
		return (root, query, criteriaBuilder) -> (year != null) ?
				criteriaBuilder.equal(root.get("academicYear").get("year"), year) : null;
	}

	public static Specification<Voucher> hasStatus(String status) {
		return (root, query, criteriaBuilder) -> (status == null || status.isEmpty()) ?
				null : criteriaBuilder.equal(
				root.get("status"), VoucherStatus.valueOf(status.toUpperCase()));
	}
}
