package nl.parkhaven.wasschema.modules.mail;

import java.util.List;

import nl.parkhaven.wasschema.modules.user.User;
import nl.parkhaven.wasschema.modules.util.MailSenderService;

final class SendMailToWashersService implements Runnable {

	private EmailDao emailDao;
	private MailSenderService mailSenderService;

	public SendMailToWashersService(EmailDao emailDao, MailSenderService mailSender) {
		this.emailDao = emailDao;
		this.mailSenderService = mailSender;
	}
	
	@Override
	public void run() {
		List<User> users = emailDao.getEmailAddresses();
		for (User user : users) {
			mailSenderService.sendMailThreeHoursReminder(user);
		}
	}

}
