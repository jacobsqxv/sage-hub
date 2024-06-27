package dev.aries.sagehub.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.aries.sagehub.enums.Semester;
import dev.aries.sagehub.enums.Status;
import dev.aries.sagehub.enums.Year;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class ProgramCourse extends Auditing {
	@Id
	private Long id;

	@ManyToOne
	@JoinColumn(name = "program_id", nullable = false)
	@JsonBackReference
	private Program program;
	@OneToMany
	@Column(nullable = false)
	private List<Course> courses;
	@Column(nullable = false)
	private Year year;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Semester semester;
	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;
	@Enumerated(EnumType.STRING)
	private Status status;

}
