package dev.aries.sagehub.repository;

import java.util.Optional;

import dev.aries.sagehub.model.BasicInfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BasicInfoRepository extends JpaRepository<BasicInfo, Long> {
	Optional<BasicInfo> findByUserId(Long userId);
}
