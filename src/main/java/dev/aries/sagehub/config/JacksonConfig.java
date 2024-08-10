package dev.aries.sagehub.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.aries.sagehub.enums.AccountStatus;
import dev.aries.sagehub.enums.Gender;
import dev.aries.sagehub.enums.MaritalStatus;
import dev.aries.sagehub.enums.ResultType;
import dev.aries.sagehub.enums.Status;
import dev.aries.sagehub.enums.Title;
import dev.aries.sagehub.util.EnumDeserializer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {
	@Bean
	public SimpleModule enumModule() {
		SimpleModule module = new SimpleModule();
		module.addDeserializer(Gender.class, new EnumDeserializer<>(Gender.class));
		module.addDeserializer(MaritalStatus.class, new EnumDeserializer<>(MaritalStatus.class));
		module.addDeserializer(Title.class, new EnumDeserializer<>(Title.class));
		module.addDeserializer(AccountStatus.class, new EnumDeserializer<>(AccountStatus.class));
		module.addDeserializer(Status.class, new EnumDeserializer<>(Status.class));
		module.addDeserializer(ResultType.class, new EnumDeserializer<>(ResultType.class));
		return module;
	}
}
