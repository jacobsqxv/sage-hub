package dev.aries.sagehub.model;

import java.time.LocalDate;

import dev.aries.sagehub.enums.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
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
@MappedSuperclass
public class BasicInfo extends Auditing {
	@Column(nullable = false)
	private String profilePictureUrl;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	private String middleName;

	@Column(nullable = false)
	private Gender gender;

	@Column(nullable = false)
	private LocalDate dateOfBirth;

	@Column(unique = true, updatable = false, nullable = false)
	private String primaryEmail;

	@OneToOne
	private User user;

	public String fullName() {
		return getFullName(this.firstName, this.middleName, this.lastName);
	}

	static String getFullName(String firstName, String middleName, String lastName) {
		StringBuilder builder = new StringBuilder();
		builder.append(firstName).append(" ");
		if (middleName == null || middleName.isEmpty()) {
			return builder.append(lastName).toString().trim();
		}
		builder.append(middleName).append(" ");
		builder.append(lastName);
		return builder.toString().trim();
	}
}
