package dev.aries.sagehub.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	private String profilePictureUrl;

	private String firstName;

	private String lastName;

	private String middleName;

	@Column(nullable = false)
	private String primaryEmail;

	@OneToOne
	private User user;

	public String fullName() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.firstName).append(" ");
		if (this.middleName == null || this.middleName.isEmpty()) {
			return builder.append(this.lastName).toString().trim();
		}
		builder.append(this.middleName).append(" ");
		builder.append(this.lastName);
		return builder.toString().trim();
	}
}
