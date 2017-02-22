package nl.parkhaven.wasschema.test.component.appointment;

import org.junit.Assert;
import org.junit.Test;

import nl.parkhaven.wasschema.modules.appointment.Appointment;
import nl.parkhaven.wasschema.modules.appointment.AppointmentService;

public class AppointmentServiceTest {

	private AppointmentService appointmentService;

	@Test
	public void testAddDate() {
		appointmentService = new AppointmentService(createNotLoggedInAppointment());
		appointmentService.addAppointment();
		Assert.assertTrue(appointmentService.errorOccured());
		Assert.assertEquals("You are not logged in!", appointmentService.getErrorMessage());

		appointmentService = new AppointmentService(createAppointment());
		appointmentService.addAppointment();
		Assert.assertFalse(appointmentService.errorOccured());

		// check if appointment can be overriden
		appointmentService = new AppointmentService(createAppointment());
		appointmentService.addAppointment();
		Assert.assertTrue(appointmentService.errorOccured());
		Assert.assertEquals("Passed date or date already taken!", appointmentService.getErrorMessage());
	
		testRemoveAppointment();
	}

	public void testRemoveAppointment() {
		appointmentService = new AppointmentService(createAppointment());
		appointmentService.removeAppointment();
		Assert.assertFalse(appointmentService.errorOccured());
	}

	public Appointment createAppointment() {
		Appointment appointment = new Appointment();
		appointment.setUserId("1");
		appointment.setWashingMachine("1");
		appointment.setTime("12");
		appointment.setDay("14");
		return appointment;
	}

	public Appointment createNotLoggedInAppointment() {
		Appointment appointment = new Appointment();
		appointment.setUserId("NotANumber");
		appointment.setWashingMachine("C1");
		appointment.setTime("12");
		appointment.setDay("14");
		return appointment;
	}

}
