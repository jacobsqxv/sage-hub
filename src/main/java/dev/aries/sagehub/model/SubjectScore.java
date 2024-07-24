package dev.aries.sagehub.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class SubjectScore extends Auditing {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String subjectName;
	@Column(nullable = false)
	private Character grade;
	@ManyToOne
	@JoinColumn(name = "result_id", nullable = false)
	private ApplicantResult result;

	@Override
	public final boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof SubjectScore subjectScore)) {
			return false;
		}
		return getId() != null && getId().equals(subjectScore.getId());
	}

	@Override
	public final int hashCode() {
		return getClass().hashCode();
	}
}
