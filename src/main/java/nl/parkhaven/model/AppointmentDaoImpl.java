package nl.parkhaven.model;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nl.parkhaven.model.abstraction.AppointmentDao;
import nl.parkhaven.model.abstraction.CommonDao;
import nl.parkhaven.model.entity.Appointment;
import nl.parkhaven.model.util.Database;

public class AppointmentDaoImpl extends CommonDao implements AppointmentDao {

	/*
	 * One DAO class per Entity and Table DAO needs to perform CRUD actions For
	 * Sprint 2: - Get all Dates
	 */

	// private static final Logger logger =
	// LogManager.getLogger(UserDaoImpl.class);
	private static final Logger logger = LogManager.getLogger(AppointmentDaoImpl.class);

	@Override
	public int[] getAllData(String wasmachine_id) {
		String showHuisnummersWasmachineSQL = "SELECT huisnummer FROM wasschema WHERE wasmachine_id = ?;";
		int huisnummers[] = new int[70];

		try {
			conn = Database.getConnection();
			preStmt = conn.prepareStatement(showHuisnummersWasmachineSQL);
			preStmt.setString(1, wasmachine_id);
			rs = preStmt.executeQuery();
			for (int i = 0; rs.next(); i++) {
				huisnummers[i] = rs.getInt("huisnummer");
			}
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			releaseResources(conn, preStmt, rs);
		}

		return huisnummers;
	}

	@Override
	public boolean addDate(Appointment appointment) {
		String checkTimeAvailableSQL = "SELECT huisnummer FROM wasschema WHERE tijd_id = ? AND wasmachine_id = ?;";
		String addAppointmentSQL = "UPDATE wasschema SET huisnummer = ? WHERE tijd_id = ? AND wasmachine_id = ?";
		boolean appointmentMade = false;

		try {
			conn = Database.getConnection();
			preStmt = conn.prepareStatement(checkTimeAvailableSQL);
			preStmt.setInt(1, appointment.calculateRowNumber());
			preStmt.setString(2, appointment.getMachinenummer());
			rs = preStmt.executeQuery();
			rs.next();
			int dateTaken = rs.getInt(1);
			if (dateTaken == 0) {
				preStmt = conn.prepareStatement(addAppointmentSQL);
				preStmt.setInt(1, appointment.getHuisnummer());
				preStmt.setInt(2, appointment.calculateRowNumber());
				preStmt.setString(3, appointment.getMachinenummer());
				int rowsUpdated = preStmt.executeUpdate();
				if (rowsUpdated != 1) {
					throw new SQLException("More than 1 row was updated!");
				}
				appointmentMade = true;
			}
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			releaseResources(conn, preStmt, rs);
		}

		return appointmentMade;
	}

	@Override
	public Appointment updateDate() {
		return null;
	}

	@Override
	public Appointment removeDate() {
		return null;
	}
}
