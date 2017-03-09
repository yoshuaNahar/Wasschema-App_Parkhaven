package nl.parkhaven.wasschema.modules.appointment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

	private static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);

	public static final String WASH_LIMIT_MET = "You have already met the wash limit of this week.";
	public static final String DATE_PASSED_OR_TAKEN = "Sorry, the date you entered already passed or has been taken.";
	public static final String REMOVE_APPOINTMENT_FAILED = "Failed to remove appointment. Check if given info is correct or if appointment isnt within 30 minutes!";

	private AppointmentDaoImpl appointmentDao;

	@Autowired
	public AppointmentService(AppointmentDaoImpl appointmentDao) {
		this.appointmentDao = appointmentDao;
	}

	public boolean dateFree(Appointment ap) {
		if (appointmentDao.read(ap).getUserId() == 0) {
			return true;
		} else {
			logger.warn("Date taken. GebruikerId: " + ap.getUserId());
			return false;
		}
	}

	public void addAppointment(Appointment ap) {
		appointmentDao.update(ap);
	}

	public boolean removeAppointment(Appointment ap) {
		return appointmentDao.delete(ap);
	}

}
