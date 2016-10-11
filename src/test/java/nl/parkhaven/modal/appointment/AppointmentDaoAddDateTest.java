package nl.parkhaven.modal.appointment;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import nl.parkhaven.model.abstraction.CrudDao;
import nl.parkhaven.model.entity.Appointment;

@Ignore("Data Creating Test")
public class AppointmentDaoAddDateTest {

	private CrudDao<Appointment> appointmentDao;
	private Appointment badAppointment;
	private Appointment goodAppointment;
	private Appointment badAppointmentAfterGoodAppointment;

	@Before
	public void setUp() {
		badAppointment = new Appointment();
		badAppointment.setGebruiker_id("230");
		badAppointment.setWasmachine("A1");
		badAppointment.setTime("1");
		badAppointment.setDay("0");

		goodAppointment = new Appointment();
		goodAppointment.setGebruiker_id("230");
		goodAppointment.setWasmachine("A1");
		goodAppointment.setTime("1");
		goodAppointment.setDay("1");

		badAppointmentAfterGoodAppointment = new Appointment();
		badAppointmentAfterGoodAppointment.setGebruiker_id("230");
		badAppointmentAfterGoodAppointment.setWasmachine("A1");
		badAppointmentAfterGoodAppointment.setTime("1");
		badAppointmentAfterGoodAppointment.setDay("1");

//		appointmentDao = new AppointmentDaoImpl();
	}

	@Test
	public void testingAddDate() {
		boolean appointmentMade;

		if (appointmentDao.read(badAppointment).getGebruiker_id() != 0) {
			appointmentDao.create(badAppointment);
			appointmentMade = true;
		} else {
			appointmentMade = false;
		}
		Assert.assertFalse("Is the appointment made for specific time and wasmachine?", appointmentMade);

		if (appointmentDao.read(goodAppointment).getGebruiker_id() != 0) {
			appointmentDao.create(goodAppointment);
			appointmentMade = true;
		} else {
			appointmentMade = false;
		}
		Assert.assertTrue("Is the appointment made for specific time and wasmachine?", appointmentMade);

		if (appointmentDao.read(badAppointmentAfterGoodAppointment).getGebruiker_id() != 0) {
			appointmentDao.create(badAppointmentAfterGoodAppointment);
			appointmentMade = true;
		} else {
			appointmentMade = false;
		}
		Assert.assertFalse("Is the appointment made for specific time and wasmachine?", appointmentMade);
	}
	
	@AfterClass
	public static void removingAddDate() {
		// TODO Implement remove huisnummer added in the above test!
		// for now -> UPDATE `parkhaven`.`wasschema` SET `huisnummer`= NULL WHERE  `wasmachine_id`='A1' AND `tijd_id`=2 LIMIT 1;
	}
}
