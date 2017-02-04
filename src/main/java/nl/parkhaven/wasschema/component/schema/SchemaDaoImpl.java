package nl.parkhaven.wasschema.component.schema;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import nl.parkhaven.wasschema.component.CommonDao;

final class SchemaDaoImpl extends CommonDao {

	/*
	 * The schema is the only class where I don't release the resources in the
	 * Dao classes. I do this because I recall getData multiple times after each
	 * other. I also only call this method twice so I won't forget to release
	 * the resources.
	 * Always call getTimes first, and always close connection after last getData call!
	 */

	public Map<Long, String> getTimes() {
		String sql = "SELECT id, DATE_FORMAT(tijd_default, '%H:%i') FROM tijd;";
		Map<Long, String> times = new HashMap<>();
		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(sql);
			rs = preStmt.executeQuery();
			while (rs.next()) {
				times.put((long) rs.getInt(1) - 1, rs.getString(2));
			}
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		}
		return times;
	}

	public Map<Long, String> getWashingMachines() {
		String sql = "SELECT id, name FROM wasmachine;";
		Map<Long, String> washmachines = new HashMap<>();
		try {
			preStmt = conn.prepareStatement(sql);
			rs = preStmt.executeQuery();
			while (rs.next()) {
				washmachines.put((long) rs.getInt(1), rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return washmachines;
	}

	public String[][] getWashingSchemaData(int washmachine_id) {
		String sql = "SELECT a.huisnummer FROM wasschema x LEFT JOIN gebruiker a ON x.gebruiker_id = a.id WHERE x.wasmachine_id = ? ORDER BY week_dag_tijd_id;";
		String housenumbers[][] = new String[2][91];
		try {
			preStmt = conn.prepareStatement(sql);
			preStmt.setInt(1, washmachine_id);
			rs = preStmt.executeQuery();
			for (int week = 0; week < 2; week++) {
				for (int index = 0; index < 91; index++) {
					rs.next();
					housenumbers[week][index] = rs.getString(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return housenumbers;
	}

}
