package nl.parkhaven.modal.appointment;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import nl.parkhaven.model.AppointmentService;
import nl.parkhaven.model.entity.Appointment;

public class AppointmentServiceTest {

	private AppointmentService appointmentService;
	private Appointment badAppointment;
	private Appointment goodAppointment;
	private Appointment badAppointmentAfterGoodAppointment;

	@Before
	public void setup() {
		badAppointment = new Appointment();
		badAppointment.setGebruiker_id("NotANumber");
		badAppointment.setWasmachine("A1");
		badAppointment.setTime("1");
		badAppointment.setDay("1");

		goodAppointment = new Appointment();
		goodAppointment.setGebruiker_id("1");
		goodAppointment.setWasmachine("A1");
		goodAppointment.setTime("1");
		goodAppointment.setDay("1");

		badAppointmentAfterGoodAppointment = new Appointment();
		badAppointmentAfterGoodAppointment.setGebruiker_id("1");
		badAppointmentAfterGoodAppointment.setWasmachine("A1");
		badAppointmentAfterGoodAppointment.setTime("1");
		badAppointmentAfterGoodAppointment.setDay("1");

		appointmentService = new AppointmentService();
	}

	@Test
	public void testingAddDate() {
		appointmentService.addAppointment(badAppointment);
		Assert.assertTrue(appointmentService.errorOccured());
		Assert.assertEquals("You are not logged in!", appointmentService.getErrorMessage());

		appointmentService.addAppointment(goodAppointment);
		Assert.assertFalse("No error occurred :)", appointmentService.errorOccured());

		appointmentService.addAppointment(badAppointmentAfterGoodAppointment);
		Assert.assertTrue(appointmentService.errorOccured());
		Assert.assertEquals("Date already taken!", appointmentService.getErrorMessage());
	}
}
