package nl.parkhaven.model;

import nl.parkhaven.model.entity.Appointment;

public final class AppointmentService {

	private AppointmentDaoImpl appointmentDao;
	private Appointment appointment;
	private String errorMessage;

	public void addAppointment(Appointment appointment) {
		this.appointment = appointment;
		if (entryValid()) {
			if (dateFree()) {
				placeDate();
			}
		}
	}

	public boolean errorOccured() {
		return errorMessage == null ? false : true;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	private boolean entryValid() {
		if (appointment.getGebruiker_id() > 0) {
			return true;
		} else {
			errorMessage = "You are not logged in!";
			return false;
		}
	}

	private boolean dateFree() {
		appointmentDao = new AppointmentDaoImpl();
		if (appointmentDao.read(appointment).getGebruiker_id() != 0) {
			return true;
		} else {
			errorMessage = "Date already taken!";
			appointmentDao.releaseResources();
			return false;
		}
	}

	private void placeDate() {
		appointmentDao.update(appointment);
	}
}
