package dev.aries.sagehub.security;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KeyUtils {
	private final Environment environment;

	@Value("${access-token.private}")
	private String accessTokenPrivateKeyPath;

	@Value("${access-token.public}")
	private String accessTokenPublicKeyPath;

	@Value("${refresh-token.private}")
	private String refreshTokenPrivateKeyPath;

	@Value("${refresh-token.public}")
	private String refreshTokenPublicKeyPath;

	private KeyPair accessTokenKeyPair;
	private KeyPair refreshTokenKeyPair;

	private KeyPair getAccessTokenKeyPair() {
		if (Objects.isNull(this.accessTokenKeyPair)) {
			this.accessTokenKeyPair = getKeyPair(
					this.accessTokenPublicKeyPath, this.accessTokenPrivateKeyPath);
		}
		return this.accessTokenKeyPair;
	}

	private KeyPair getRefreshTokenKeyPair() {
		if (Objects.isNull(this.refreshTokenKeyPair)) {
			this.refreshTokenKeyPair = getKeyPair(
					this.refreshTokenPublicKeyPath, this.refreshTokenPrivateKeyPath);
		}
		return this.refreshTokenKeyPair;
	}

	private KeyPair getKeyPair(String publicKeyPath, String privateKeyPath) {
		KeyPair keyPair;

		File publicKeyFile = new File(publicKeyPath);
		File privateKeyFile = new File(privateKeyPath);

		if (publicKeyFile.exists() && privateKeyFile.exists()) {
			log.info("Loading keys from file: {}, {}", publicKeyPath, privateKeyPath);
			try {
				KeyFactory keyFactory = KeyFactory.getInstance("RSA");

				byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
				EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
				PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

				byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());
				PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
				PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

				keyPair = new KeyPair(publicKey, privateKey);
				return keyPair;
			}
			catch (NoSuchAlgorithmException | IOException | InvalidKeySpecException ex) {
				throw new RuntimeException(ex);
			}
		}
		else {
			if (Arrays.asList(this.environment.getActiveProfiles()).contains("prod")) {
				throw new RuntimeException("public and private keys don't exist");
			}
		}

		File directory = new File("jwt-token-keys");
		if (!directory.exists()) {
			directory.mkdirs();
		}
		try {
			log.info("Generating new public and private keys: {}, {}", publicKeyPath, privateKeyPath);
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048);
			keyPair = keyPairGenerator.generateKeyPair();
			try (FileOutputStream fos = new FileOutputStream(publicKeyPath)) {
				X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyPair.getPublic().getEncoded());
				fos.write(keySpec.getEncoded());
			}

			try (FileOutputStream fos = new FileOutputStream(privateKeyPath)) {
				PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyPair.getPrivate().getEncoded());
				fos.write(keySpec.getEncoded());
			}
		}
		catch (NoSuchAlgorithmException | IOException ex) {
			throw new RuntimeException(ex);
		}

		return keyPair;
	}


	public RSAPublicKey getAccessTokenPublicKey() {
		return (RSAPublicKey) getAccessTokenKeyPair().getPublic();
	}

	public RSAPrivateKey getAccessTokenPrivateKey() {
		return (RSAPrivateKey) getAccessTokenKeyPair().getPrivate();
	}

	public RSAPublicKey getRefreshTokenPublicKey() {
		return (RSAPublicKey) getRefreshTokenKeyPair().getPublic();
	}

	public RSAPrivateKey getRefreshTokenPrivateKey() {
		return (RSAPrivateKey) getRefreshTokenKeyPair().getPrivate();
	}
}
