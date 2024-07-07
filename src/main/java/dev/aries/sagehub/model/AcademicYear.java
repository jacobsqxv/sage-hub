package dev.aries.sagehub.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class AcademicYear extends Auditing {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, unique = true)
	private Integer year;
	@Column(nullable = false, unique = true)
	private LocalDate startDate;
	@Column(nullable = false, unique = true)
	private LocalDate endDate;

	@Override
	public final boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof AcademicYear academicYear)) {
			return false;
		}
		return getId() != null && getId().equals(academicYear.getId());
	}

	@Override
	public final int hashCode() {
		return getClass().hashCode();
	}
}
