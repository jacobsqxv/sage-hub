package dev.aries.sagehub.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.aries.sagehub.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class Program extends Auditing {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String name;
	private String description;
	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;
	@OneToMany(orphanRemoval = true)
	@JsonManagedReference
	private List<ProgramCourse> courses;
	@Enumerated(EnumType.STRING)
	private Status status;
}
