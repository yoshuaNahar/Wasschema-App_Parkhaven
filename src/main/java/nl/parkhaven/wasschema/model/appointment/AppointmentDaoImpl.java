package nl.parkhaven.wasschema.model.appointment;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

import nl.parkhaven.wasschema.model.CommonDao;
import nl.parkhaven.wasschema.model.Crud;

final class AppointmentDaoImpl extends CommonDao implements Crud<Appointment> {

	@Override
	public boolean create(Appointment ap) {
		// New record creation is done weekly by a MySQL Event.
		new RuntimeException("Not implemented!");
		return false;
	}

	@Override
	public Appointment read(Appointment ap) {
		String checkFreeAndAtleast5MinPriorSQL = "CALL check_appointment_atleast_5min_in_future(?, ?);";
		Appointment apResult = new Appointment();

		try {
			conn = getConnection();
			preStmt = conn.prepareCall(checkFreeAndAtleast5MinPriorSQL);
			preStmt.setInt(1, ap.week_dag_tijd_id());
			preStmt.setInt(2, ap.getWasmachine());
			rs = preStmt.executeQuery();
			if (rs.next()) {
				apResult.setGebruiker_id(rs.getInt(1));
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
			preStmt.setInt(1, ap.getGebruiker_id());
			preStmt.setInt(2, ap.week_dag_tijd_id());
			preStmt.setInt(3, ap.getWasmachine());
			preStmt.executeUpdate();
			bool = true;
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			releaseResources();
		}

		return bool;
	}

	// Set gebruikerId to NULL, records will not be removed!
	@Override
	public boolean delete(Appointment ap) {
		String removeAppointmentSQL = "CALL remove_appointment_if_atleast30min_in_future(?, ?, ?);";
		boolean bool = false;

		try {
			conn = getConnection();
			preStmt = conn.prepareCall(removeAppointmentSQL);
			preStmt.setInt(1, ap.getGebruiker_id());
			preStmt.setInt(2, ap.week_dag_tijd_id());
			preStmt.setInt(3, ap.getWasmachine());
			if (preStmt.executeUpdate() == 1) { // A row was deleted
				bool = true;
			} // if executeUpdate == 0, the wasmachine or time and date were
				// incorrect or appointment wasnt 30 min in the future
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			releaseResources();
		}

		return bool;
	}

}
