package dev.aries.sagehub.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@Entity
public class Student extends BaseUser {

	@Override
	public final boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Student student)) {
			return false;
		}
		return getId() != null && getId().equals(student.getId());
	}

	@Override
	public final int hashCode() {
		return getClass().hashCode();
	}
}
