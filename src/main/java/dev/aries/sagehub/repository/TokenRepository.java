package dev.aries.sagehub.repository;

import dev.aries.sagehub.enums.TokenType;
import dev.aries.sagehub.model.Token;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
	Token findByValueAndUserIdAndType(String value, Long userId, TokenType type);
}
