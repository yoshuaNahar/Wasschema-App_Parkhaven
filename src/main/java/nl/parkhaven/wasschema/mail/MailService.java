package nl.parkhaven.wasschema.mail;

import java.util.Date;

import nl.parkhaven.wasschema.model.appointment.Appointment;

public final class MailService {

	private MailDaoImpl mailDao;

	public MailService() {
		mailDao = new MailDaoImpl();
	}

	public Date getDateForEmail(Appointment ap) {
		return mailDao.getDateForEmail(ap);
	}

	public void enableMail(boolean enable, int userId) {
		mailDao.enableMail(enable, userId);
	}
}
