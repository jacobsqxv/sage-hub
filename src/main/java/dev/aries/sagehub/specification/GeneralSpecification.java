package dev.aries.sagehub.specification;

import dev.aries.sagehub.enums.Status;

import org.springframework.data.jpa.domain.Specification;

public class GeneralSpecification<T> {
	public Specification<T> hasName(String name) {
		return (root, query, criteriaBuilder) -> (name == null || name.trim().isEmpty()) ?
				null : criteriaBuilder.like(
				criteriaBuilder.lower(root.get("name")), "%" + name.trim().toLowerCase() + "%");
	}

	public Specification<T> hasCode(String code) {
		return (root, query, criteriaBuilder) -> (code == null || code.isEmpty()) ?
				null : criteriaBuilder.like(
				root.get("code"), "%" + code + "%");
	}

	public Specification<T> hasStatus(String status) {
		return (root, query, criteriaBuilder) -> (status == null || status.isEmpty()) ?
				null : criteriaBuilder.equal(
				root.get("status"), Status.valueOf(status.toUpperCase()));
	}

}
