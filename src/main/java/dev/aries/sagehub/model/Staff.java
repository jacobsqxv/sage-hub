package dev.aries.sagehub.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class Staff extends BasicInfo {

	@Id
	@Column(unique = true, updatable = false, nullable = false)
	private Long id;

	@OneToOne
	@JoinColumn(nullable = false)
	private ContactInfo contactInfo;

	@OneToOne
	private EmergencyContact emergencyContact;
}
