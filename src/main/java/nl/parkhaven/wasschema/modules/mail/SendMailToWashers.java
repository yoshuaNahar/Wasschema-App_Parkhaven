package nl.parkhaven.wasschema.modules.mail;

import java.util.List;

import nl.parkhaven.wasschema.modules.user.User;
import nl.parkhaven.wasschema.modules.util.MailSender;

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
