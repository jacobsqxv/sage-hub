package dev.aries.sagehub.model;

import dev.aries.sagehub.model.attribute.Name;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class Admin extends Auditing {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String profilePicture;

	@Embedded
	@Column(nullable = false)
	private Name name;

	@Column(nullable = false)
	private String primaryEmail;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(nullable = false)
	private User user;

	public String fullName() {
		return name.fullName();
	}

	@Override
	public final boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Admin admin)) {
			return false;
		}
		return getId() != null && getId().equals(admin.getId());
	}

	@Override
	public final int hashCode() {
		return getClass().hashCode();
	}
}
