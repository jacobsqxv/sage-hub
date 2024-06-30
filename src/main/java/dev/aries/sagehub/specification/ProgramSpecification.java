package dev.aries.sagehub.specification;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.model.Program;

import org.springframework.data.jpa.domain.Specification;

public final class ProgramSpecification {
	public static Specification<Program> hasDepartment(String department) {
		return (root, query, criteriaBuilder) -> (department == null || department.trim().isEmpty()) ?
				null : criteriaBuilder.like(
				criteriaBuilder.lower(root.get("department").get("name")),
				"%" + department.trim().toLowerCase() + "%");
	}
	private ProgramSpecification() {
		throw new IllegalStateException(ExceptionConstants.UTILITY_CLASS);
	}
}
