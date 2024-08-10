package dev.aries.sagehub.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public record ContactInfo(
		@Column(nullable = false)
		String secondaryEmail,
		@Column(nullable = false)
		String phoneNumber,
		@Embedded
		@Column(nullable = false)
		Address residentialAddress,
		String postalAddress
){
}
