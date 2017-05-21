package nl.parkhaven.wasschema.modules.appointment;

import nl.parkhaven.wasschema.config.TestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class AppointmentServiceTest {

    @Autowired private AppointmentService appointmentService;
    private Appointment appointment;

    @Before
    public void setup() {
        appointment = new Appointment();
        appointment.setUserId("1");
        appointment.setMachine("4");
        appointment.setTime("12");
        appointment.setDay("14");
    }

    @Test
    public void testAddAppointment() {
        boolean freeDate = appointmentService.dateFree(appointment);

        assertThat(freeDate, is(true));

        appointmentService.addAppointment(appointment);

        freeDate = appointmentService.dateFree(appointment);

        assertThat(freeDate, is(false));
    }

    @Test
    public void testRemoveAppointment() {
        appointmentService.addAppointment(appointment);
        boolean appointmentRemoved = appointmentService.removeAppointment(appointment);

        assertThat(appointmentRemoved, is(true));
    }

}
