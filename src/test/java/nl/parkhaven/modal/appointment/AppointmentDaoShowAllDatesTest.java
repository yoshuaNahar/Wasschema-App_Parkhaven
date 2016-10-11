package nl.parkhaven.modal.appointment;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import nl.parkhaven.model.abstraction.AppointmentDao;

public class AppointmentDaoShowAllDatesTest {

	private AppointmentDao appointmentDao;
	private int[] huisnummers;

	@Before
	public void setUp() {
		huisnummers = new int[70];
//		appointmentDao = new AppointmentDaoImpl();
	}

	@Test
	public void testingShowSpecificDates() {
		huisnummers = appointmentDao.getWasschemaData(1, 92, "A1");
		Assert.assertTrue("Is the value of the first row equal to 230?", huisnummers[0] == 230);
		for (int i = 1; i < huisnummers.length; i++) {
			Assert.assertTrue("Are the values of the other rows equal to 0?", huisnummers[i] == 0);
		}
	}
}
