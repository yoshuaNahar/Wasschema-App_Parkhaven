package nl.parkhaven.model;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nl.parkhaven.model.abstraction.AppointmentDao;
import nl.parkhaven.model.abstraction.CommonDao;
import nl.parkhaven.model.entity.Appointment;

final class AppointmentDaoImpl extends CommonDao<Appointment> implements AppointmentDao {

	private static final Logger logger = LogManager.getLogger(AppointmentDaoImpl.class);

	@Override
	public int[] getWasschemaData(int startingRange, int endingRange, String wasmachine_id) {
		String sql = "SELECT gebruiker_id FROM wasschema WHERE (week_dag_tijd_id BETWEEN ? AND ?) AND wasmachine_id = ?;";
		int huisnummers[] = new int[91];

		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(sql);
			preStmt.setInt(1, startingRange);
			preStmt.setInt(2, endingRange);
			preStmt.setString(3, wasmachine_id);
			rs = preStmt.executeQuery();
			for (int i = 0; rs.next(); i++) {
				huisnummers[i] = rs.getInt(1);
			}
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		}

		return huisnummers;
	}

	@Override
	public void create(Appointment ap) {
		// New record creation is done weekly by a MySQL Event.
	}

	/*
	 * WARNING: Always use this method with update, resources not closed.
	 */
	@Override
	public Appointment read(Appointment ap) {
		String sql = "SELECT gebruiker_id FROM wasschema WHERE week_dag_tijd_id = ? AND wasmachine_id = ?;";
		Appointment apResult = new Appointment();

		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(sql);
			preStmt.setInt(1, ap.week_dag_tijd_id());
			preStmt.setString(2, ap.getWasmachine());
			rs = preStmt.executeQuery();
			if (rs.next()) {
				apResult.setGebruiker_id(rs.getInt(0));
			}
			logger.info("Appointment read method. Details: " + ap.getGebruiker_id() + " - " + ap.week_dag_tijd_id()
					+ " - " + ap.getWasmachine() + ". DB Result gebruiker_id:" + apResult.getGebruiker_id());
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		}

		return apResult;
	}

	@Override
	public void update(Appointment ap) {
		String sql = "UPDATE wasschema SET gebruiker_id = ? WHERE week_dag_tijd_id = ? AND wasmachine_id = '?';";

		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(sql);
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
		// Appointment records will not be deleted.
	}
}
