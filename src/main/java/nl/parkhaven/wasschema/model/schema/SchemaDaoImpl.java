package nl.parkhaven.wasschema.model.schema;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import nl.parkhaven.wasschema.model.CommonDao;

final class SchemaDaoImpl extends CommonDao {

	/*
	 * The schema is the only class where I don't release the resources in the
	 * Dao classes. I do this because I recall getData multiple times after each
	 * other. I also only call this method twice so I won't forget to release
	 * the resources.
	 * Always call getTimes first, and always close connection after last getData call!
	 */
	public Map<Long, String> getTimes() {
		String sql = "SELECT DATE_FORMAT(tijd_default, '%H:%i') FROM tijd;";
		Map<Long, String> times = new HashMap<>();

		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(sql);
			rs = preStmt.executeQuery();
			for (long i = 0; rs.next(); i++) {
				times.put(i, rs.getString(1));
			}
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		}

		return times;
	}

	public Map<Long, String> getWasmachines() {
		String sql = "SELECT id, name FROM wasmachine;";
		Map<Long, String> wasmachines = new HashMap<>();

		try {
			preStmt = conn.prepareStatement(sql);
			rs = preStmt.executeQuery();
			while (rs.next()) {
				wasmachines.put((long) rs.getInt(1), rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return wasmachines;
	}

	public String[][] getWasschemaData(int wasmachine_id) {
		String sql = "SELECT a.huisnummer FROM wasschema x LEFT JOIN gebruiker a ON x.gebruiker_id = a.id WHERE x.wasmachine_id = ? ORDER BY week_dag_tijd_id;";
		String huisnummers[][] = new String[2][91];

		try {
			preStmt = conn.prepareStatement(sql);
			preStmt.setInt(1, wasmachine_id);
			rs = preStmt.executeQuery();
			for (int week = 0; week < 2; week++) {
				for (int index = 0; index < 91; index++) {
					rs.next();
					huisnummers[week][index] = rs.getString(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return huisnummers;
	}

}
