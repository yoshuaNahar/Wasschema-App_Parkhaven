package nl.parkhaven.controller.service;

import nl.parkhaven.model.AppointmentDaoImpl;
import nl.parkhaven.model.abstraction.CrudDao;
import nl.parkhaven.model.entity.Appointment;

public class AppointmentService {

	private CrudDao<Appointment> appointmentDAO;
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
		return errorMessage == null ? true : false;
	}

	private boolean entryValid() {
		if (appointment.getHuisnummer() > 0) {
			return true;
		} else {
			errorMessage = "You are not logged in!";
			return false;
		}
	}

	private boolean dateFree() {
		appointmentDAO = new AppointmentDaoImpl();
		if (appointmentDAO.read(appointment).getHuisnummer() != 0) {
			return true;
		} else {
			errorMessage = "Date already taken!";
			return false;
		}
	}

	private void placeDate() {
		appointmentDAO.create(appointment);
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
