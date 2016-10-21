package nl.parkhaven.wasschema.mail;

import java.util.Date;
import java.util.Timer;

public final class MailTimer {

	private static final Timer TIMER = new Timer(true);

	private MailTimer() {
	}

	public static void addSchedule(String recipient, Date dateToMail) {
		TIMER.schedule(new MailService(recipient), dateToMail);
	}
}
