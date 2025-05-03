package co.edu.unbosque.util;

import java.security.SecureRandom;

public class MFAGenerator {
	
	public static String generateCode(int length) {
		SecureRandom random = new SecureRandom();
		StringBuilder code = new StringBuilder();

		for (int i = 0; i < length; i++) {
			code.append(random.nextInt(10));
		}

		return code.toString();
	}
	
}
