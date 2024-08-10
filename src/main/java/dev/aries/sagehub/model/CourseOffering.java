package dev.aries.sagehub.model;

import dev.aries.sagehub.enums.Status;
import dev.aries.sagehub.model.attribute.AcademicPeriod;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class CourseOffering extends Auditing {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "program_id", nullable = false)
	private Program program;
	@ManyToOne
	@JoinColumn(name = "course_id", nullable = false)
	private Course course;
	@Embedded
	@Column(nullable = false)
	private AcademicPeriod academicPeriod;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status;

	@Override
	public final boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof CourseOffering courseOffering)) {
			return false;
		}
		return getId() != null && getId().equals(courseOffering.getId());
	}

	@Override
	public final int hashCode() {
		return getClass().hashCode();
	}
}
