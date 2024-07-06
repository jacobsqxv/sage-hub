package dev.aries.sagehub.model;

import java.util.List;

import dev.aries.sagehub.enums.ApplicationStatus;
import dev.aries.sagehub.enums.Status;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne
	private ContactInfo contactInfo;
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "applicant_results")
	private List<ApplicantResult> result;
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Program> programs;
	@Enumerated(EnumType.STRING)
	private ApplicationStatus status;
	private boolean isSubmitted;
}
