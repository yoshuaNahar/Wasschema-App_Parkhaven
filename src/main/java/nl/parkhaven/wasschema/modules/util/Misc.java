package nl.parkhaven.wasschema.modules.util;

import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Misc {

	private static final Logger logger = LoggerFactory.getLogger(Misc.class);

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

	public static boolean isHouseNumberValid(String houseNummer) {
		if (houseNummer == null) {
			return false;
		}
		/*
		 * 1 number between 1 - 9, Atleast 0 Max 2 numbers between 0 - 9, Once
		 * or none letters between A and F
		 */
		if (houseNummer.matches("[1-9]{1}[0-9]{0,2}[A-F]?")) {
			return true;
		}
		logger.warn("User Bypassed Javascript Regex");
		return false;
	}

}
