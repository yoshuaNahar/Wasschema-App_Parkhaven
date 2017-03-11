package nl.parkhaven.wasschema.modules.appointment;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import nl.parkhaven.wasschema.config.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class AppointmentServiceTest {

	@Autowired
	private AppointmentService appointmentService;

	private Appointment appointment;

	@Before
	public void setup() {
		appointment = new Appointment();
		appointment.setUserId("1");
		appointment.setMachine("1");
		appointment.setTime("12");
		appointment.setDay("14");
	}

	@Test
	public void testAddAppointment() {
		assertThat(appointmentService.dateFree(appointment), is(true));
		appointmentService.addAppointment(appointment);
	}

	@Test
	public void testRemoveAppointment() {
		appointmentService.addAppointment(appointment);
		assertThat(appointmentService.removeAppointment(appointment), is(true));
	}

}
