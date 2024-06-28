package dev.aries.sagehub.model;

import java.time.LocalDateTime;
import java.time.LocalTime;

import dev.aries.sagehub.enums.Status;
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

	@Column(unique = true, updatable = false, nullable = false)
	private String username;
	@Column(nullable = false)
	private String hashedPassword;

	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;

	@Enumerated(EnumType.STRING)
	private Status status;

	private LocalDateTime lastLogin;

	private Integer failedLoginAttempts;

	private boolean accountLocked;

	private boolean accountEnabled;

}
