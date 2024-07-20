package dev.aries.sagehub.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import dev.aries.sagehub.security.KeyUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

	private static final String[] WHITELIST = {
			"/api/v1/auth/**",
			"/api/v1/applications/login",
	};
	private final UserDetailsService userDetailsService;
	private final KeyUtils keyUtils;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests((auth) -> {
					auth.requestMatchers(WHITELIST).permitAll();
					auth.anyRequest().authenticated();
				})
				.sessionManagement((session) ->
						session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
				.exceptionHandling((exceptions) -> exceptions
						.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
						.accessDeniedHandler(new BearerTokenAccessDeniedHandler()))
				.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	@Primary
	JwtDecoder jwtAccessTokenDecoder() {
		return NimbusJwtDecoder.withPublicKey(this.keyUtils.getAccessTokenPublicKey()).build();
	}

	@Bean
	@Primary
	JwtEncoder jwtAccessTokenEncoder() {
		JWK jwk = new RSAKey
				.Builder(this.keyUtils.getAccessTokenPublicKey())
				.privateKey(this.keyUtils.getAccessTokenPrivateKey())
				.build();
		JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
		return new NimbusJwtEncoder(jwks);
	}

	@Bean
	JwtDecoder jwtRefreshTokenDecoder() {
		return NimbusJwtDecoder.withPublicKey(this.keyUtils.getRefreshTokenPublicKey()).build();
	}

	@Bean
	JwtEncoder jwtRefreshTokenEncoder() {
		JWK jwk = new RSAKey
				.Builder(this.keyUtils.getRefreshTokenPublicKey())
				.privateKey(this.keyUtils.getRefreshTokenPrivateKey())
				.build();
		JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
		return new NimbusJwtEncoder(jwks);
	}

	@Bean
	DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(this.userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	@Bean
	JwtAuthenticationProvider jwtRefreshTokenAuthProvider(JwtDecoder jwtRefreshTokenDecoder) {
		return new JwtAuthenticationProvider(jwtRefreshTokenDecoder);
	}
}
