package nl.parkhaven.wasschema.modules.util;

import java.time.LocalTime;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Misc {

	private static final Logger logger = LoggerFactory.getLogger(Misc.class);

	private Misc() {}

	public static final LocalTime[] TIMES_ARRAY = { LocalTime.of(5, 30, 0), LocalTime.of(7, 0, 0), LocalTime.of(8, 30, 0),
			LocalTime.of(10, 0, 0), LocalTime.of(11, 30, 0), LocalTime.of(13, 0, 0), LocalTime.of(14, 30, 0),
			LocalTime.of(16, 0, 0), LocalTime.of(17, 30, 0), LocalTime.of(19, 0, 0), LocalTime.of(20, 30, 0),
			LocalTime.of(22, 0, 0), LocalTime.of(23, 30, 0) };

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

}
