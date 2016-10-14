package nl.parkhaven.modal.appointment;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import nl.parkhaven.model.abstraction.SchemaDao;

public class AppointmentDatesTest {

	private SchemaDao appointmentDao;
	private String[] huisnummers;

	@Before
	public void setup() {
		huisnummers = new String[91];
	}

	@Test
	public void testingShowSpecificDates() {
		huisnummers = appointmentDao.getWasschemaData(1, 91, "A1");
		for (int i = 1; i < huisnummers.length; i++) {
			Assert.assertTrue("Are the values of the other rows equal to 0?", huisnummers[i] == null);
		}
	}
}
