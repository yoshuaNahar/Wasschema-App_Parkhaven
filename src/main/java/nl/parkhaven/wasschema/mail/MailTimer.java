package nl.parkhaven.wasschema.mail;

import java.util.Date;
import java.util.Timer;

import nl.parkhaven.wasschema.model.appointment.Appointment;

public final class MailTimer {

	private static final Timer TIMER = new Timer(true);

	private MailTimer() {
	}

	public static void addSchedule(String recipient, Appointment appointment, Date dateToMail) {
		TIMER.schedule(new MailTask(recipient, appointment), dateToMail);
	}
}
