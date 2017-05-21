package nl.parkhaven.wasschema.modules.util;

import nl.parkhaven.wasschema.modules.user.User;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Properties;

@Service
public class MailService {

    private static final String emailSender = "straalbetaal@gmail.com";
    private static final String passwordSender = "straalgeheim";
    private static final Properties PROPS;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.US);
    private static final DateTimeFormatter TIME_FORMATTER_WITHOUT_SECONDS = DateTimeFormatter.ofPattern("HH:mm", Locale.US);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.US);

    static {
        PROPS = System.getProperties();
        PROPS.put("mail.smtp.host", "smtp.gmail.com");
        PROPS.put("mail.smtp.auth", "true");
        PROPS.put("mail.smtp.port", "587");
        PROPS.put("mail.smtp.user", "straalbetaal@gmail.com");
        PROPS.put("mail.smtp.password", "straalgeheim");
        PROPS.put("mail.smtp.starttls.enable", "true");
    }

    public void sendMailThreeHoursReminder(User user) {
        Session session = Session.getDefaultInstance(PROPS);
        session.setDebug(false);

        LocalDateTime now = LocalDateTime.now();
        Duration threeHours = Duration.ofHours(3);
        LocalDateTime afterThreeHours = now.plus(threeHours);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailSender));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
            message.setSubject("Parkhaven WasSchema - Reminder");
            message.setContent("<body> "
                            + "<h2>Parkhaven WasSchema</h2>" + "<hr></hr>"
                            + "<h4>This mail is to remind you that you have a laundry appointment at. " + TIME_FORMATTER_WITHOUT_SECONDS.format(afterThreeHours) + "</h4>"
                            + "Laundry room: " + user.getPassword().charAt(0) + ", laundry machine: " + user.getPassword() + ".</h4>"
                            + "<p>Kind regards!</p>"
                            + "<p>Date: " + DATE_FORMATTER.format(now)
                            + "<br>Time: " + TIME_FORMATTER.format(now)
                            + "<p>========================</p>"
                            + "<p>This is an automatically generated email. Please do not send a reply.</p>"
                            + "</body>",
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

        LocalDateTime now = LocalDateTime.now();

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailSender));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
            message.setSubject("Parkhaven WasSchema - New Password");
            message.setContent("<body> "
                            + "<h2>Parkhaven WasSchema</h2>" + "<hr></hr>"
                            + "<p>You have just requested a new password.</p>"
                            + "<h4>Your new password is: " + user.getPassword() + "<h4>"
                            + "<p>Kind regards!</p>"
                            + "<p>Date: " + DATE_FORMATTER.format(now)
                            + "<br>Time: " + TIME_FORMATTER.format(now)
                            + "<p>========================</p>"
                            + "<p>This is an automatically generated email. Please do not send a reply.</p>"
                            + "</body>",
                    "text/html; charset=utf-8");
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", emailSender, passwordSender);
            transport.sendMessage(message, message.getAllRecipients());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
