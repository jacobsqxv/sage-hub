package dev.aries.sagehub.model;

import java.time.LocalDate;

import dev.aries.sagehub.enums.Gender;
import dev.aries.sagehub.enums.MaritalStatus;
import dev.aries.sagehub.enums.Title;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Entity
public class BasicInfo extends Auditing {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String profilePictureUrl;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	private String middleName;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private MaritalStatus maritalStatus;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Title title;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Column(nullable = false)
	private LocalDate dateOfBirth;

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
