package dev.aries.sagehub.model;

import java.time.LocalDateTime;

import dev.aries.sagehub.enums.AccountStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "_user")
public class User extends Auditing {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String username;
	@Column(nullable = false)
	private String hashedPassword;

	@ManyToOne
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private AccountStatus status;

	private LocalDateTime lastLogin;

	private Integer failedLoginAttempts;

	private LocalDateTime lockTime;

	private boolean accountEnabled;

	@Override
	public final boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof User user)) {
			return false;
		}
		return getId() != null && getId().equals(user.getId());
	}

	@Override
	public final int hashCode() {
		return getClass().hashCode();
	}
}
