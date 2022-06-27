package it.akademija.security;

import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

public class PBKDF2PasswordEncoder extends Pbkdf2PasswordEncoder {
	
	private final static String PEPPER = "Red$1&Hot*5^Chilly&VI1CyDbqRrlA3dUu282";
	private final static int SALT_LENGTH = 32;
	private final static int ITERATIONS = 50000;
	private final static int HASH_WIDTH = 512;

	/**
	 * Constructs a standard password encoder with a secret value as well as salt length, iterations and hash width.
	 */
	public PBKDF2PasswordEncoder() {
		super(PEPPER, SALT_LENGTH, ITERATIONS, HASH_WIDTH);
		super.setAlgorithm(SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
	}
	
	/**
	 * Encodes the raw password.
	 * 
	 * @param rawPassword - The original password.
	 * @return Encoded password.
	 */
	@Override
	public String encode(CharSequence rawPassword) {
		return super.encode(rawPassword);
	}
	
	/**
	 * Pbkdf2PasswordEncoder encodes the raw password and checks, whether it matches the already given encoded password.
	 *
	 * @param rawPassword     The original password.
	 * @param encodedPassword The encoded password.
	 * @return Whether passwords match.
	 */
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return super.matches(rawPassword, encodedPassword);
	}
}
