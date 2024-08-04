package dev.aries.sagehub.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
	@Value("${application.base-url}")
	private String devUrl;

	@Bean
	public OpenAPI openAPI() {
		Server devServer = new Server();
		devServer.setUrl(devUrl);
		devServer.setDescription("Development Server");

		Info info = new Info()
				.title("SageHub API")
				.version("1.0")
				.description("This API exposes the endpoints for SageHub.");

		SecurityScheme securityScheme = new SecurityScheme()
				.type(SecurityScheme.Type.HTTP)
				.scheme("bearer")
				.bearerFormat("JWT")
				.in(SecurityScheme.In.HEADER)
				.name("Authorization");

		SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");
		return new OpenAPI()
				.addServersItem(devServer)
				.info(info)
				.components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
				.addSecurityItem(securityRequirement);
	}
}
