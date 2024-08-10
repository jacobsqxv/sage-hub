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
public class ICEInfo extends Auditing {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String fullName;
	@Column(nullable = false)
	private String relationship;
	@Column(nullable = false)
	private String phoneNumber;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private String occupation;
	@Embedded
	@Column(nullable = false)
	private Address address;
	@Column(nullable = false)
	private Long userId;

	@Override
	public final boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof ICEInfo ICEInfo)) {
			return false;
		}
		return getId() != null && getId().equals(ICEInfo.getId());
	}

	@Override
	public final int hashCode() {
		return getClass().hashCode();
	}
}
