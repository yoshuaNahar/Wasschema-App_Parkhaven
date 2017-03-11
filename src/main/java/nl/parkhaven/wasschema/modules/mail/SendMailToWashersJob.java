package nl.parkhaven.wasschema.modules.mail;

import java.util.List;

import nl.parkhaven.wasschema.modules.user.User;
import nl.parkhaven.wasschema.modules.util.MailService;

class SendMailToWashersJob implements Runnable {

	private EmailDao emailDao;
	private MailService mailSenderService;

	SendMailToWashersJob(EmailDao emailDao, MailService mailSender) {
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
