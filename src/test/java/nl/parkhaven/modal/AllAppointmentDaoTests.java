package nl.parkhaven.modal;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import nl.parkhaven.modal.appointment.AppointmentDaoAddDateTest;
import nl.parkhaven.modal.appointment.AppointmentDaoShowAllDatesTest;

@RunWith(Suite.class)
@SuiteClasses({ AppointmentDaoShowAllDatesTest.class, AppointmentDaoAddDateTest.class })
public class AllAppointmentDaoTests {

}
