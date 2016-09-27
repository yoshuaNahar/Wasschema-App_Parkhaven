package nl.parkhaven.modal.appointment;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import nl.parkhaven.model.AppointmentDaoImpl;
import nl.parkhaven.model.abstraction.AppointmentDao;
import nl.parkhaven.model.entity.Appointment;

@Ignore("Data Creating Test")
public class AppointmentDaoAddDateTest {

	private AppointmentDao appointmentDao;
	private Appointment badAppointment;
	private Appointment goodAppointment;
	private Appointment badAppointmentAfterGoodAppointment;

	@Before
	public void setUp() {
		badAppointment = new Appointment();
		badAppointment.setHuisnummer("230");
		badAppointment.setMachinenummer("A1");
		badAppointment.setTime("1");
		badAppointment.setDay("0");

		goodAppointment = new Appointment();
		goodAppointment.setHuisnummer("230");
		goodAppointment.setMachinenummer("A1");
		goodAppointment.setTime("1");
		goodAppointment.setDay("1");

		badAppointmentAfterGoodAppointment = new Appointment();
		badAppointmentAfterGoodAppointment.setHuisnummer("230");
		badAppointmentAfterGoodAppointment.setMachinenummer("A1");
		badAppointmentAfterGoodAppointment.setTime("1");
		badAppointmentAfterGoodAppointment.setDay("1");

		appointmentDao = new AppointmentDaoImpl();
	}

	@Test
	public void testingAddDate() {
		boolean appointmentMade = false;

		appointmentMade = appointmentDao.addDate(badAppointment);
		Assert.assertFalse("Is the appointment made for specific time and wasmachine?", appointmentMade);
		
		appointmentMade = appointmentDao.addDate(goodAppointment);
		Assert.assertTrue("Is the appointment made for specific time and wasmachine?", appointmentMade);

		appointmentMade = appointmentDao.addDate(badAppointmentAfterGoodAppointment);
		Assert.assertFalse("Is the appointment made for specific time and wasmachine?", appointmentMade);
	}
	
	@AfterClass
	public static void removingAddDate() {
		// TODO Implement remove huisnummer added in the above test!
		// for now -> UPDATE `parkhaven`.`wasschema` SET `huisnummer`= NULL WHERE  `wasmachine_id`='A1' AND `tijd_id`=2 LIMIT 1;
	}
}
