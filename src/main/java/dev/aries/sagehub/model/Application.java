package dev.aries.sagehub.model;

import java.util.List;

import dev.aries.sagehub.enums.ApplicationStatus;
import dev.aries.sagehub.model.attribute.Education;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
public class Application extends Auditing {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(nullable = false)
	private Student applicant;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ExamResult> examResults;
	@OneToMany(fetch = FetchType.LAZY)
	private List<Program> programChoices;
	@Embedded
	@Column(nullable = false)
	private Education education;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ApplicationStatus status;
	private boolean isSubmitted;
	@ManyToOne
	private AcademicYear yearOfApplication;

	@Override
	public final boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Application application)) {
			return false;
		}
		return getId() != null && getId().equals(application.getId());
	}

	@Override
	public final int hashCode() {
		return getClass().hashCode();
	}
}
