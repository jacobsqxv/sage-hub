package dev.aries.sagehub.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.aries.sagehub.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Course extends Auditing {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true, nullable = false)
	private String code;
	@Column(nullable = false)
	private String name;
	private String description;
	@Column(nullable = false)
	private Integer creditUnits;
	@Enumerated(EnumType.STRING)
	private Status status;
}
