package dev.aries.sagehub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SageHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(SageHubApplication.class, args);
	}

}
