package dev.aries.sagehub.repository;

import java.util.Optional;

import dev.aries.sagehub.model.UserProfile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
	Optional<UserProfile> findByUserId(Long userId);
}
