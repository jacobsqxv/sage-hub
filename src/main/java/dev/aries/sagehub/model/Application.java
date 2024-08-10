package dev.aries.sagehub.model;

import java.util.List;

import dev.aries.sagehub.enums.ApplicantStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Applicant extends BaseUser {
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "applicant", orphanRemoval = true)
	private List<Result> results;
	@OneToMany(fetch = FetchType.LAZY)
	private List<Program> programChoices;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ApplicantStatus status;
	private boolean isSubmitted;
	@ManyToOne
	private AcademicYear yearOfApplication;

	@Override
	public final boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Applicant applicant)) {
			return false;
		}
		return getId() != null && getId().equals(applicant.getId());
	}

	@Override
	public final int hashCode() {
		return getClass().hashCode();
	}
}
