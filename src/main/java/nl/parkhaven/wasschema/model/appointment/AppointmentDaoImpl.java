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

	@Override
	public Appointment read(Appointment ap) {
		String checkFreeAndAtleast5MinPriorSQL = "CALL check_free_and_atleast_5min_prior(?, ?);";
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
			logger.info("Appointment read method. Details: Gebruiker " + ap.getGebruiker_id() + " - Week_dag_tijd_id "
					+ ap.week_dag_tijd_id() + " - Wasmachine " + ap.getWasmachine() + ". DB Result gebruiker_id: "
					+ apResult.getGebruiker_id());
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
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
			int rowsUpdated = preStmt.executeUpdate();
			if (rowsUpdated == 1) {
				bool = true;
			} else {
				throw new SQLException("More than 1 row was updated!");
			}
			logger.info("Appointment made! Details: " + ap.getGebruiker_id() + " - " + ap.week_dag_tijd_id() + " - "
					+ ap.getWasmachine());
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			releaseResources();
		}

		return bool;
	}

	// Set gebruikerId to NULL, records will never be removed!
	@Override
	public boolean delete(Appointment ap) {
		String removeAppointmentSQL = "UPDATE wasschema SET gebruiker_id = NULL WHERE gebruiker_id = ? AND week_dag_tijd_id = ? AND wasmachine_id = ?;";
		boolean bool = false;

		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(removeAppointmentSQL);
			preStmt.setInt(1, ap.getGebruiker_id());
			preStmt.setInt(2, ap.week_dag_tijd_id());
			preStmt.setInt(3, ap.getWasmachine());
			int rowsUpdated = preStmt.executeUpdate();
			if (rowsUpdated == 1) {
				bool = true;
			} else {
				throw new SQLException("More than 1 row was updated Or None!");
			}
			logger.info("Appointment removed! Details: " + ap.getGebruiker_id() + " - " + ap.week_dag_tijd_id() + " - "
					+ ap.getWasmachine());
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			releaseResources();
		}

		return bool;
	}
}
