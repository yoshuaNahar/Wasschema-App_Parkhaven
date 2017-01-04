package nl.parkhaven.wasschema.util;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Misc {

	private static final Logger logger = LogManager.getLogger(Misc.class);

	private Misc() {}

	public static String generateNewPassword() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&()=+[{]}<>/?";
		String password = "";
		for (int i = 0; i < 9; i++) {
			int randomNum = ThreadLocalRandom.current().nextInt(0, 79 + 1);
			String c = Character.toString(characters.charAt(randomNum));
			password += c;
		}
		return password;
	}

	public static boolean isHuisnummerValid(String huisnummer) {
		if (huisnummer == null) {
			return false;
		}
		/*
		 * 1 number between 1 - 9, Atleast 0 Max 2 numbers between 0 - 9, Once
		 * or none letters between A and F
		 */
		if (huisnummer.matches("[1-9]{1}[0-9]{0,2}[A-F]?")) {
			return true;
		}
		logger.warn("= User Bypassed Javascript Regex =");
		return false;
	}

}
