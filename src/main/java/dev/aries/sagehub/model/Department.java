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
public class Department extends Auditing {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true, nullable = false, updatable = false)
	private String code;
	private String name;
	@OneToMany(mappedBy = "department", orphanRemoval = true)
	@JsonManagedReference
	private List<ProgramCourse> programs;
	@Enumerated(EnumType.STRING)
	private Status status;
}
