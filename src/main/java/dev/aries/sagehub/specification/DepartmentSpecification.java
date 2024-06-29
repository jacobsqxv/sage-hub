package dev.aries.sagehub.specification;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.enums.Status;
import dev.aries.sagehub.model.Department;

import org.springframework.data.jpa.domain.Specification;

public class DepartmentSpecification {
	public static Specification<Department> hasName(String name) {
		return (root, query, criteriaBuilder) -> name == null || name.trim().isEmpty() ? null : criteriaBuilder.like(
				criteriaBuilder.lower(root.get("name")), "%" + name.trim().toLowerCase() + "%");
	}

	public static Specification<Department> hasCode(String code) {
		return (root, query, criteriaBuilder) -> code == null || code.isEmpty() ? null : criteriaBuilder.like(
				root.get("code"), "%" + code + "%");
	}

	public static Specification<Department> hasStatus(String status) {
		return (root, query, criteriaBuilder) -> status == null || status.isEmpty() ? null : criteriaBuilder.equal(
				root.get("status"), Status.valueOf(status.toUpperCase()));
	}

	private DepartmentSpecification() {
		throw new IllegalStateException(ExceptionConstants.UTILITY_CLASS);
	}
}
