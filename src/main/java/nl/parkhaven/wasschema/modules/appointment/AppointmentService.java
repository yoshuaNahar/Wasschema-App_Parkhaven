package nl.parkhaven.wasschema.modules.appointment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class AppointmentService {

	private static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);

	private AppointmentDaoImpl appointmentDao;
	private String errorMessage;

	@Autowired
	public AppointmentService(AppointmentDaoImpl appointmentDao) {
		this.appointmentDao = appointmentDao;
	}
	
	public void addAppointment(Appointment ap) {
		if (entryValid(ap)) {
			if (dateFree(ap)) {
				placeDate(ap);
			}
		}
	}

	public void removeAppointment(Appointment ap) {
		boolean removed = appointmentDao.delete(ap);
		if (!removed) {
			errorMessage = "Failed to remove appointment. Check if given info is correct or if appointment isnt within 30 minutes!";
			logger.warn("Wrong date Or in the passed (remove appointment). GebruikerId: " + ap.getUserId());
		}
	}

	public boolean errorOccured() {
		return errorMessage != null;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	private boolean entryValid(Appointment ap) {
		if (ap.getUserId() > 0) {
			return true;
		} else {
			errorMessage = "You are not logged in!";
			logger.warn("= Bypassed Javascript, entering appointment=");
			return false;
		}
	}

	private boolean dateFree(Appointment ap) {
		if (appointmentDao.read(ap).getUserId() == 0) {
			return true;
		} else {
			errorMessage = "Passed date or date already taken!";
			logger.warn("Date taken. GebruikerId: " + ap.getUserId());
			return false;
		}
	}

	private void placeDate(Appointment ap) {
		appointmentDao.update(ap);
	}

}
