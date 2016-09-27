package nl.parkhaven.controller.service;

import nl.parkhaven.model.AppointmentDaoImpl;
import nl.parkhaven.model.abstraction.AppointmentDao;
import nl.parkhaven.model.entity.Appointment;

public class AppointmentService {

	private AppointmentDao appointmentDAO = new AppointmentDaoImpl();
	private Appointment appointment;
	private String errorMessage;

	public AppointmentService(Appointment appointment) {
		this.appointment = appointment;
	}

	public boolean validEntry() {
		if (appointment.getHuisnummer() > 0) {
			return true;
		} else {
			errorMessage = "You are not logged in!";
			return false;
		}
	}

	public boolean datePlaced() {
		if (appointmentDAO.addDate(appointment)) {
			return true;
		} else {
			errorMessage = "Date already taken!";
			return false;
		}
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
