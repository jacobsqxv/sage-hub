package dev.aries.sagehub.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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
public class ContactInfo extends Auditing {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String secondaryEmail;
	@Column(nullable = false)
	private String phoneNumber;
	@Embedded
	@Column(nullable = false)
	private Address address;
	@Column(nullable = false)
	private String postalAddress;

	@Override
	public final boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof ContactInfo contactInfo)) {
			return false;
		}
		return getId() != null && getId().equals(contactInfo.getId());
	}

	@Override
	public final int hashCode() {
		return getClass().hashCode();
	}
}
