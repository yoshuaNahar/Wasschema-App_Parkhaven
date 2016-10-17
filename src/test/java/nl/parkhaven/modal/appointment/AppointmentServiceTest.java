package nl.parkhaven.modal.appointment;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import nl.parkhaven.model.AppointmentService;
import nl.parkhaven.model.entity.Appointment;

public final class AppointmentServiceTest {

	private AppointmentService appointmentService;
	private Appointment notLoggedinAppointment;
	private Appointment goodAppointment;
	private Appointment dateTakenAppointment;

	@Before
	public void setup() {
		notLoggedinAppointment = new Appointment();
		notLoggedinAppointment.setGebruiker_id("NotANumber");
		notLoggedinAppointment.setWasmachine("C2");
		notLoggedinAppointment.setTime("13");
		notLoggedinAppointment.setDay("7");

		goodAppointment = new Appointment();
		goodAppointment.setGebruiker_id("1");
		goodAppointment.setWasmachine("C1");
		goodAppointment.setTime("13");
		goodAppointment.setDay("7");

		dateTakenAppointment = new Appointment();
		dateTakenAppointment.setGebruiker_id("1");
		dateTakenAppointment.setWasmachine("C1");
		dateTakenAppointment.setTime("13");
		dateTakenAppointment.setDay("7");

		appointmentService = new AppointmentService();
	}

	@Test
	public void testingAddDate() {
		appointmentService.addAppointment(notLoggedinAppointment);
		Assert.assertTrue(appointmentService.errorOccured());
		Assert.assertEquals("You are not logged in!", appointmentService.getErrorMessage());

		appointmentService.addAppointment(goodAppointment);
		Assert.assertFalse("No error occurred :)", appointmentService.errorOccured());

		appointmentService.addAppointment(dateTakenAppointment);
		Assert.assertTrue(appointmentService.errorOccured());
		Assert.assertEquals("Date already taken!", appointmentService.getErrorMessage());
	}
}
