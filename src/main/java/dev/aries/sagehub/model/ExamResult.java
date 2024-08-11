package dev.aries.sagehub.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.aries.sagehub.enums.ResultType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
public class ExamResult extends Auditing {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ResultType type;
	@Column(nullable = false)
	private Integer year;
	@Column(nullable = false, unique = true)
	private String indexNumber;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "examResult", orphanRemoval = true)
	@JsonManagedReference
	private List<SubjectScore> results;
	@Column(nullable = false)
	private Long studentId;

	@Override
	public final boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof ExamResult examResult)) {
			return false;
		}
		return getId() != null && getId().equals(examResult.getId());
	}

	@Override
	public final int hashCode() {
		return getClass().hashCode();
	}
}
