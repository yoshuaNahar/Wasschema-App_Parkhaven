package nl.parkhaven.wasschema.modules.appointment;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

import nl.parkhaven.wasschema.modules.CommonDao;
import nl.parkhaven.wasschema.modules.Crud;

final class AppointmentDaoImpl extends CommonDao implements Crud<Appointment> {

	@Override
	public boolean create(Appointment ap) {
		// New record creation is done weekly by a MySQL Event.
		new RuntimeException("Not implemented!");
		return false;
	}

	@Override
	public Appointment read(Appointment ap) {
		String checkFreeAndAtleast5MinPriorSQL = "CALL check_free_and_atleast_5min_prior(?, ?);";
		Appointment apResult = new Appointment();
		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(checkFreeAndAtleast5MinPriorSQL);
			preStmt.setInt(1, ap.week_day_time_id());
			preStmt.setInt(2, ap.getWashingMachine());
			rs = preStmt.executeQuery();
			if (rs.next()) {
				apResult.setUserId(rs.getInt(1));
			} else {
				apResult.setUserId(-1); // ResultSet was empty, so data was in the past
			}
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			releaseResources();
		}
		return apResult;
	}

	@Override
	public boolean update(Appointment ap) {
		String addAppointmentSQL = "UPDATE wasschema SET gebruiker_id = ? WHERE week_dag_tijd_id = ? AND wasmachine_id = ?;";
		boolean bool = false;
		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(addAppointmentSQL);
			preStmt.setInt(1, ap.getUserId());
			preStmt.setInt(2, ap.week_day_time_id());
			preStmt.setInt(3, ap.getWashingMachine());
			preStmt.executeUpdate();
			bool = true;
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			releaseResources();
		}
		return bool;
	}

	// Sets userId in wasschema table to NULL
	@Override
	public boolean delete(Appointment ap) {
		String removeAppointmentSQL = "CALL remove_appointment_if_atleast30min_in_future(?, ?);";
		boolean bool = false;
		try {
			conn = getConnection();
			preStmt = conn.prepareCall(removeAppointmentSQL);
			preStmt.setInt(1, ap.week_day_time_id());
			preStmt.setInt(2, ap.getWashingMachine());
			if (preStmt.executeUpdate() == 1) { // A row was deleted
				bool = true;
			} // if executeUpdate == 0, the wasmachine or time and date were
				// incorrect or appointment was less than 30 min in the future
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			releaseResources();
		}
		return bool;
	}

}
