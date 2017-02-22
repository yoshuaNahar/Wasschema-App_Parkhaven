package nl.parkhaven.wasschema.modules.appointment;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class AppointmentService {

	private static final Logger logger = LogManager.getLogger(AppointmentService.class);

	private AppointmentDaoImpl appointmentDao = new AppointmentDaoImpl();
	private Appointment appointment;
	private String errorMessage;

	public AppointmentService(Appointment appointment) {
		this.appointment = appointment;
	}

	public void addAppointment() {
		if (entryValid()) {
			if (dateFree()) {
				placeDate();
			}
		}
	}

	public void removeAppointment() {
		boolean removed = appointmentDao.delete(appointment);
		if (!removed) {
			errorMessage = "Failed to remove appointment. Check if given info is correct or if appointment isnt within 30 minutes!";
			logger.warn("Wrong date Or in the passed (remove appointment). GebruikerId: " + appointment.getUserId());
		}
	}

	public boolean errorOccured() {
		return errorMessage != null;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	private boolean entryValid() {
		if (appointment.getUserId() > 0) {
			return true;
		} else {
			errorMessage = "You are not logged in!";
			logger.warn("= Bypassed Javascript, entering appointment=");
			return false;
		}
	}

	private boolean dateFree() {
		if (appointmentDao.read(appointment).getUserId() == 0) {
			return true;
		} else {
			errorMessage = "Passed date or date already taken!";
			logger.warn("Date taken. GebruikerId: " + appointment.getUserId());
			return false;
		}
	}

	private void placeDate() {
		appointmentDao.update(appointment);
	}

}
