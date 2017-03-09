package nl.parkhaven.wasschema.modules.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import nl.parkhaven.wasschema.modules.user.User;

@Service
public class MailService {

	private static final String emailSender = "straalbetaal@gmail.com";
	private static final String passwordSender = "straalBetaalOP4";
	private static final Properties PROPS;
	private static final DateFormat DATEFORMAT = new SimpleDateFormat("dd-MM-yyyy");
	private static final DateFormat TIMEFORMAT = new SimpleDateFormat("HH:mm:ss");

	static {
		PROPS = System.getProperties();
		PROPS.put("mail.smtp.host", "smtp.gmail.com");
		PROPS.put("mail.smtp.auth", "true");
		PROPS.put("mail.smtp.port", "587");
		PROPS.put("mail.smtp.user", "straalbetaal@gmail.com");
		PROPS.put("mail.smtp.password", "straalBetaalOP4");
		PROPS.put("mail.smtp.starttls.enable", "true");
	}

	public void sendMailThreeHoursReminder(User user) {
		Session session = Session.getDefaultInstance(PROPS);
		session.setDebug(false);

		Date now = new Date();
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(emailSender));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
			message.setSubject("Parkhaven Wasschema - Reminder");
			message.setContent("<body> <h2>This mail is to remind you that you made an appointment.</h2>"
					+ "<h4>The appointment is at: " + user.getHouseNumber() + ", wasruimte: "
					+ user.getPassword().charAt(0) + ", wasmachine: " + user.getPassword() + ".</h4>"
					+ "<p>Tot ziens!</p>" + "<p>Datum: " + DATEFORMAT.format(now) + "<p>========================</p>"
					+ "<p>Dit is een automatisch gegenereerde email. Reageer aub niet hierop.</p>" + "</body>",
					"text/html; charset=utf-8");
			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", emailSender, passwordSender);
			transport.sendMessage(message, message.getAllRecipients());
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void sendMailContainingPasswordTo(User user) {
		Session session = Session.getDefaultInstance(PROPS);
		session.setDebug(false);

		Date now = new Date();

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(emailSender));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
			message.setSubject("Parkhaven Wasschema - New Password");
			message.setContent(
					"<body> " + "<h1>Parkhaven Wasschema</h1>" + "<hr></hr>" + "<h2>Your new password is: "
							+ user.getPassword() + "</h2>" + "<p>Tot ziens!</p>" + "<p>Datum: " + DATEFORMAT.format(now)
							+ "<br>Tijd: " + TIMEFORMAT.format(now) + "<p>========================</p>"
							+ "<p>Dit is een automatisch gegenereerde email. Reageer aub niet hierop.</p>" + "</body>",
					"text/html; charset=utf-8");
			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", emailSender, passwordSender);
			transport.sendMessage(message, message.getAllRecipients());
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
