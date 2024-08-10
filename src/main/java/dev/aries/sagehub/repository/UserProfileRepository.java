package dev.aries.sagehub.repository;

import java.util.Optional;

import dev.aries.sagehub.model.UserInfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
	Optional<UserInfo> findByUserId(Long userId);
}
