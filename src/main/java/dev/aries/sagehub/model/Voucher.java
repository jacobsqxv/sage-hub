package dev.aries.sagehub.model;

import dev.aries.sagehub.enums.VoucherStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class Voucher extends Auditing {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true, nullable = false)
	private Long serialNumber;
	@Column(nullable = false)
	private String pin;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private VoucherStatus status;
	@ManyToOne
	private AcademicYear academicYear;

	@Override
	public final boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Voucher voucher)) {
			return false;
		}
		return getId() != null && getId().equals(voucher.getId());
	}

	@Override
	public final int hashCode() {
		return getClass().hashCode();
	}
}
