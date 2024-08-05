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
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

import dev.aries.sagehub.constant.ExceptionConstants;
import dev.aries.sagehub.exception.KeyUtilException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KeyUtils {
	private static final String DIRECTORY_PATH = "jwt-token-keys";

	@Value("${application.jwt.access-token.private}")
	private String accessTokenPrivateKeyPath;

	@Value("${application.jwt.access-token.public}")
	private String accessTokenPublicKeyPath;

	@Value("${application.jwt.refresh-token.private}")
	private String refreshTokenPrivateKeyPath;

	@Value("${application.jwt.refresh-token.public}")
	private String refreshTokenPublicKeyPath;

	private KeyPair accessTokenKeyPair;
	private KeyPair refreshTokenKeyPair;

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

	private KeyPair getAccessTokenKeyPair() {
		if (Objects.isNull(accessTokenKeyPair)) {
			accessTokenKeyPair = loadOrCreateKeyPair(
					accessTokenPublicKeyPath, accessTokenPrivateKeyPath);
		}
		return accessTokenKeyPair;
	}

	private KeyPair getRefreshTokenKeyPair() {
		if (Objects.isNull(refreshTokenKeyPair)) {
			refreshTokenKeyPair = loadOrCreateKeyPair(
					refreshTokenPublicKeyPath, refreshTokenPrivateKeyPath);
		}
		return refreshTokenKeyPair;
	}

	private KeyPair loadOrCreateKeyPair(String publicKeyPath, String privateKeyPath) {
		if (keyFilesExist(publicKeyPath, privateKeyPath)) {
			return loadKeyPair(publicKeyPath, privateKeyPath);
		}
		else {
			ensureDirectoryExists(DIRECTORY_PATH);
			return generateKeyPair(publicKeyPath, privateKeyPath);
		}
	}

	private boolean keyFilesExist(String publicKeyPath, String privateKeyPath) {
		return new File(publicKeyPath).exists() && new File(privateKeyPath).exists();
	}

	private KeyPair loadKeyPair(String publicKeyPath, String privateKeyPath) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");

			byte[] publicKeyBytes = Files.readAllBytes(new File(publicKeyPath).toPath());
			PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));

			byte[] privateKeyBytes = Files.readAllBytes(new File(privateKeyPath).toPath());
			PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));

			return new KeyPair(publicKey, privateKey);
		}
		catch (NoSuchAlgorithmException | IOException | InvalidKeySpecException ex) {
			throw new KeyUtilException(
					String.format(ExceptionConstants.NOT_FOUND, "Key pair"));
		}
	}

	private void ensureDirectoryExists(String directoryPath) {
		File directory = new File(directoryPath);
		if (!directory.exists()) {
			directory.mkdirs();
		}
	}

	private KeyPair generateKeyPair(String publicKeyPath, String privateKeyPath) {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048);
			KeyPair keyPair = keyPairGenerator.generateKeyPair();

			saveKeyToFile(publicKeyPath, keyPair.getPublic().getEncoded());
			saveKeyToFile(privateKeyPath, keyPair.getPrivate().getEncoded());

			return keyPair;
		}
		catch (NoSuchAlgorithmException | IOException ex) {
			throw new KeyUtilException(ExceptionConstants.KEY_GENERATION_FAILED);
		}
	}

	private void saveKeyToFile(String path, byte[] key) throws IOException {
		try (FileOutputStream fos = new FileOutputStream(path)) {
			fos.write(key);
		}
	}
}
