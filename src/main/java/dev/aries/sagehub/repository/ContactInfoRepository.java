package dev.aries.sagehub.repository;

import dev.aries.sagehub.model.ContactInfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactInfoRepository extends JpaRepository<ContactInfo, Long> {

}
