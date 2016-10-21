package nl.parkhaven.wasschema.model.appointment;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nl.parkhaven.wasschema.model.CommonDao;
import nl.parkhaven.wasschema.model.CrudDao;

final class AppointmentDaoImpl extends CommonDao implements CrudDao<Appointment> {

	private static final Logger logger = LogManager.getLogger(AppointmentDaoImpl.class);

	@Override
	public boolean create(Appointment ap) {
		// New record creation is done weekly by a MySQL Event.
		return false;
	}

	// WARNING: Always use this method with update, resources not closed.
	@Override
	public Appointment read(Appointment ap) {
		String checkFreeSQL = "SELECT gebruiker_id FROM wasschema WHERE week_dag_tijd_id = ? AND wasmachine_id = ?;";
		Appointment apResult = new Appointment();

		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(checkFreeSQL);
			preStmt.setInt(1, ap.week_dag_tijd_id());
			preStmt.setString(2, ap.getWasmachine());
			rs = preStmt.executeQuery();
			if (rs.next()) {
				apResult.setGebruiker_id(rs.getInt(1));
			}
			logger.info("Appointment read method. Details: Gebruiker " + ap.getGebruiker_id() + " - Week_dag_tijd_id " + ap.week_dag_tijd_id()
					+ " - Wasmachine " + ap.getWasmachine() + ". DB Result gebruiker_id: " + apResult.getGebruiker_id());
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		}

		return apResult;
	}

	@Override
	public void update(Appointment ap) {
		String addAppointmentSQL = "UPDATE wasschema SET gebruiker_id = ? WHERE week_dag_tijd_id = ? AND wasmachine_id = ?;";

		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(addAppointmentSQL);
			preStmt.setInt(1, ap.getGebruiker_id());
			preStmt.setInt(2, ap.week_dag_tijd_id());
			preStmt.setString(3, ap.getWasmachine());
			int rowsUpdated = preStmt.executeUpdate();
			if (rowsUpdated > 1) {
				conn.rollback();
				throw new SQLException("More than 1 row was updated!");
			}
			logger.info("Appointment made! Details: " + ap.getGebruiker_id() + " - " + ap.week_dag_tijd_id() + " - "
					+ ap.getWasmachine());
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			releaseResources();
		}
	}

	@Override
	public void delete(Appointment e) {
		// Appointment records deletion will be done in MySQL.
	}
}
