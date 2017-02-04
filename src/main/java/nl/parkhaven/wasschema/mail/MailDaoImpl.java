package nl.parkhaven.wasschema.mail;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nl.parkhaven.wasschema.model.CommonDao;
import nl.parkhaven.wasschema.model.appointment.Appointment;

final class MailDaoImpl extends CommonDao {

	private static final Logger logger = LogManager.getLogger(MailDaoImpl.class);

	public boolean enableMail(boolean enable, int userId) {
		String sql = null;
		boolean bool = false;

		if (enable) {
			sql = "UPDATE gebruiker_instellingen SET receive_mail='Y' WHERE gebruiker_id=?;";
		} else {
			sql = "UPDATE gebruiker_instellingen SET receive_mail='N' WHERE gebruiker_id=?;";
		}

		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(sql);
			preStmt.setInt(1, userId);
			int succes = preStmt.executeUpdate();
			if (succes == 1) {
				bool = true;
			} else {
				bool = false;
				throw new SQLException();
			}
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		}

		return bool;
	}

	public Date getDateForEmail(Appointment ap) {
		String sql = "CALL get_week_dag_en_tijd(?, ?);";
		Date date = null;

		logger.debug("Gebruiker_id: " + ap.getGebruiker_id() + " - Week_dag_tijd_id: " + ap.week_dag_tijd_id() + " - Wasmachine_id: " + ap.getWasmachine());
		try {
			conn = getConnection();
			preStmt = conn.prepareCall(sql);
			preStmt.setInt(1, ap.week_dag_tijd_id());
			preStmt.setInt(2, ap.getWasmachine());
			rs = preStmt.executeQuery();
			rs.next();
			date = rs.getTimestamp(1);
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		}

		return date;
	}

	public int getGebruikerIdForEmail(Appointment ap) {
		String checkFreeSQL = "SELECT gebruiker_id FROM wasschema WHERE week_dag_tijd_id = ? AND wasmachine_id = ?;";
		int gebruikerId = 0;

		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(checkFreeSQL);
			preStmt.setInt(1, ap.week_dag_tijd_id());
			preStmt.setInt(2, ap.getWasmachine());
			rs = preStmt.executeQuery();
			if (rs.next()) {
				gebruikerId = rs.getInt(1);
			}
			logger.info("Appointment read method. Details: Gebruiker " + ap.getGebruiker_id() + " - Week_dag_tijd_id "
					+ ap.week_dag_tijd_id() + " - Wasmachine " + ap.getWasmachine() + ". DB Result gebruiker_id: "
					+ gebruikerId);
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		}

		return gebruikerId;
	}
}
