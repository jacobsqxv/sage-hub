package dev.aries.sagehub.repository;

import java.util.Optional;

import dev.aries.sagehub.dto.search.GetApplsPage;
import dev.aries.sagehub.model.Application;
import dev.aries.sagehub.specification.GeneralSpecification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ApplicationRepository extends JpaRepository<Application, Long>,
		JpaSpecificationExecutor<Application> {

	boolean existsByApplicantUserId(Long userId);

	default Page<Application> findAll(GetApplsPage request, Pageable page) {
		GeneralSpecification<Application> spec = new GeneralSpecification<>();
		return findAll(
				Specification.where(spec.hasYear(request.year(), "yearOfApplication")),
				page
		);
	}


	boolean existsByIdAndApplicantUserId(Long id, Long userId);

	Optional<Application> findByApplicantUserId(Long userId);
}
