package nl.parkhaven.wasschema.modules.mail;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.parkhaven.wasschema.modules.user.User;
import nl.parkhaven.wasschema.modules.util.MailSender;

@Service
final class SendMailToWashersService implements Runnable {

	@Autowired
	private EmailDao emailDao;
	
	@Override
	public void run() {
		List<User> users = emailDao.getEmailAddresses();
		for (User user : users) {
			MailSender mailSender = new MailSender(user);
			mailSender.sendMailThreeHoursReminder();
		}
	}

}
