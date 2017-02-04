package nl.parkhaven.wasschema.mail;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.TimerTask;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import nl.parkhaven.wasschema.model.appointment.Appointment;
import nl.parkhaven.wasschema.model.misc.DbProcedureCalls;

final class MailTask extends TimerTask {

	private static final String emailSender = "straalbetaal@gmail.com";
	private static final String passwordSender = "straalBetaalOP4";
	private static final Properties PROPS;
	private static final DateFormat DATEFORMAT = new SimpleDateFormat("dd-MM-yyyy");
	private static final DateFormat TIMEFORMAT = new SimpleDateFormat("HH:mm:ss");

	private final String emailReceiver;
	private final Appointment appointment;

	static {
		PROPS = System.getProperties();
		PROPS.put("mail.smtp.host", "smtp.gmail.com");
		PROPS.put("mail.smtp.auth", "true");
		PROPS.put("mail.smtp.port", "587");
		PROPS.put("mail.smtp.user", "straalbetaal@gmail.com");
		PROPS.put("mail.smtp.password", "straalBetaalOP4");
		PROPS.put("mail.smtp.starttls.enable", "true");
	}

	MailTask(String emailReceiver, Appointment ap) {
		this.emailReceiver = emailReceiver;
		this.appointment = ap;
	}

	void sendMailContainingTransactiebon() {
		Session session = Session.getDefaultInstance(PROPS);
		session.setDebug(false);

		Date now = new Date();

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(emailSender));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailReceiver));
			message.setSubject("Parkhaven Wasschema - 15 Minuten");
			message.setContent("<body> " + "<h1>Parkhaven Wasschema</h1>" + "<hr></hr>"
					+ "<h2>Over 15 minuten moet u uw was doen!</h2>" + "<p>Tot ziens!</p>" + "<p>Datum: "
					+ DATEFORMAT.format(now) + "<br>Tijd: " + TIMEFORMAT.format(now) + "<p>========================</p>"
					+ "<p>Dit is een automatisch gegenereerde email. Reageer aub niet hierop :)</p>" + "</body>",
					"text/html; charset=utf-8");
			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", emailSender, passwordSender);
			transport.sendMessage(message, message.getAllRecipients());
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		int gebruikerId = new MailDaoImpl().getGebruikerIdForEmail(appointment);
		if (gebruikerId == appointment.getGebruiker_id()) {
			sendMailContainingTransactiebon();
		}
	}
}
