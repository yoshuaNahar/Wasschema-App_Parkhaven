package nl.parkhaven.service;


import nl.parkhaven.dao.AppointmentDAO;
import nl.parkhaven.entity.AppointmentPlacer;

public class AppointmentService {

	private AppointmentDAO appointmentDAO = new AppointmentDAO();

	private AppointmentPlacer ap;

	private String appointmentErrorMessage;

	public AppointmentService(AppointmentPlacer ap) {
		this.ap = ap;
	}

	public boolean validEntry() {
		if(ap.getMachinenummer() > 0 && ap.getHuisnummer() > 0)
			return true;
		else {
			appointmentErrorMessage = "You are not logged in!";
			return false;
		}
	}

	public boolean dateFree() throws Exception {
		if(appointmentDAO.checkIfFree(ap.calculateRowNumber(), ap.getMachinenummer()))
			return true;
		else {
			appointmentErrorMessage = "Date already taken!";
			return false;
		}
	}

	public void addAppointment() throws Exception {
			appointmentDAO.enterNumberOnDate(ap.calculateRowNumber(), ap.getMachinenummer(), ap.getHuisnummer());
	}

	public String getAppointmentErrorMessage() {
		return appointmentErrorMessage;
	}

	// Debug code!
	/* 
	public static void main(String[] args) {
		AppointmentPlacer ap = new AppointmentPlacer();
		ap.setDay("0");
		ap.setTime("12");
		ap.setHuisnummer("230");
		ap.setMachinenummer("2");

		try {
			addAppointment(ap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
}
