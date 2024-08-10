package dev.aries.sagehub.model;

import dev.aries.sagehub.model.attribute.ContactInfo;
import dev.aries.sagehub.model.attribute.PersonalInfo;
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
public class UserProfile extends Auditing {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Embedded
	@Column(nullable = false)
	private PersonalInfo personalInfo;
	@Embedded
	@Column(nullable = false)
	private ContactInfo contactInfo;
	private Long userId;

	public String fullName() {
		return personalInfo.name().fullName();
	}

	@Override
	public final boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof UserProfile userProfile)) {
			return false;
		}
		return getId() != null && getId().equals(userProfile.getId());
	}

	@Override
	public final int hashCode() {
		return getClass().hashCode();
	}
}
