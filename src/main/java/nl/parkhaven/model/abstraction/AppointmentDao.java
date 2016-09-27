package nl.parkhaven.model.abstraction;

import nl.parkhaven.model.entity.Appointment;

public interface AppointmentDao {

	int[] getAllData(String wasmachine_id);

	boolean addDate(Appointment appointment);

	Appointment updateDate();

	Appointment removeDate();

}
