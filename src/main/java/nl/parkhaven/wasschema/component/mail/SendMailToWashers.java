package nl.parkhaven.wasschema.component.mail;

import java.util.List;

import nl.parkhaven.wasschema.component.user.User;
import nl.parkhaven.wasschema.util.MailSender;

final class SendMailToWashers implements Runnable {

	@Override
	public void run() {
		List<User> users = new EmailDao().getEmailAddresses();
		for (User user : users) {
			MailSender mailSender = new MailSender(user);
			mailSender.sendMailThreeHoursReminder();
		}
	}

}
