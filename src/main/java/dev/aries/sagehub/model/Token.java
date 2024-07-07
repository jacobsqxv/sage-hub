package dev.aries.sagehub.model;

import java.time.LocalDateTime;

import dev.aries.sagehub.enums.TokenType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table
public class Token extends Auditing {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String value;
	@Column(nullable = false)
	private Long userId;
	@Column(nullable = false)
	private LocalDateTime expiresAt;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TokenType type;

	@Override
	public final boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Token token)) {
			return false;
		}
		return getId() != null && getId().equals(token.getId());
	}

	@Override
	public final int hashCode() {
		return getClass().hashCode();
	}
}
