package nl.parkhaven.wasschema.util;

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

import nl.parkhaven.wasschema.model.user.User;

public final class MailSender {

//	public static void main(String[] args) {
//		User user = new User();
//		user.setEmail("yosh.nahar@gmail.com");
//		user.setWachtwoord("1qwd41S");
//		new MailSender(user).sendMailContainingPassword();;
//	}

	private static final String emailSender = "straalbetaal@gmail.com";
	private static final String passwordSender = "straalBetaalOP4";
	private static final Properties PROPS;
	private static final DateFormat DATEFORMAT = new SimpleDateFormat("dd-MM-yyyy");
	private static final DateFormat TIMEFORMAT = new SimpleDateFormat("HH:mm:ss");

	private final User user;

	static {
		PROPS = System.getProperties();
		PROPS.put("mail.smtp.host", "smtp.gmail.com");
		PROPS.put("mail.smtp.auth", "true");
		PROPS.put("mail.smtp.port", "587");
		PROPS.put("mail.smtp.user", "straalbetaal@gmail.com");
		PROPS.put("mail.smtp.password", "straalBetaalOP4");
		PROPS.put("mail.smtp.starttls.enable", "true");
	}

	public MailSender(User user) {
		this.user = user;
	}

	public void sendMailContainingPassword() {
		Session session = Session.getDefaultInstance(PROPS);
		session.setDebug(false);

		Date now = new Date();

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(emailSender));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
			message.setSubject("Parkhaven Wasschema - New Password");
			message.setContent("<body> " + "<h1>Parkhaven Wasschema</h1>" + "<hr></hr>" + "<h2>Your new password is: "
					+ user.getWachtwoord() + "</h2>" + "<p>Tot ziens!</p>" + "<p>Datum: " + DATEFORMAT.format(now)
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
