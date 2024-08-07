package dev.aries.sagehub.repository;

import dev.aries.sagehub.enums.TokenType;
import dev.aries.sagehub.model.Token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, Long> {
	Token findByValueAndUserIdAndType(String value, Long userId, TokenType type);

	@Modifying
	@Query("DELETE FROM Token t WHERE t.expiresAt < CURRENT_TIMESTAMP")
	void deleteAllExpiredTokens();

	boolean existsByValueAndType(String refreshToken, TokenType tokenType);
}
