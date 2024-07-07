package dev.aries.sagehub.model;

import java.time.LocalDate;

import dev.aries.sagehub.enums.Semester;
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
public class AcademicPeriod {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private Integer year;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Semester semester;
	@Column(nullable = false, unique = true)
	private LocalDate startDate;
	@Column(nullable = false, unique = true)
	private LocalDate endDate;
}
