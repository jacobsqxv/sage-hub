package dev.aries.sagehub.model;

import java.util.List;

import dev.aries.sagehub.enums.ApplicationStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
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
public class Applicant extends BasicInfo {
	@Id
	private Long id;
	@OneToOne
	private ContactInfo contactInfo;
	@ManyToOne
	private EmergencyContact guardianInfo;
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "applicant_results")
	private List<ApplicantResult> result;
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Program> programChoices;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ApplicationStatus status;
	private boolean isSubmitted;
	@ManyToOne
	private AcademicYear applyingForYear;

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
