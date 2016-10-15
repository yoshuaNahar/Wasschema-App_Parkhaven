package nl.parkhaven.model;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nl.parkhaven.model.abstraction.CommonDao;
import nl.parkhaven.model.abstraction.SchemaDao;

final class SchemaDaoImpl extends CommonDao implements SchemaDao {

	private static final Logger logger = LogManager.getLogger(SchemaDaoImpl.class);

	@Override
	public NavigableMap<Long, String> getDates() {
		String sql = "SELECT id, DATE_FORMAT(week_dag, '%a, %d %b') FROM week_dag ORDER BY id DESC LIMIT 21;";
		NavigableMap<Long, String> dates = new TreeMap<>();

		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(sql);
			rs = preStmt.executeQuery();
			while (rs.next()) {
				dates.put((long) rs.getInt(1), rs.getString(2));
			}
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		}

		return dates;
	}

	@Override
	public Map<Long, String> getTimes() {
		String sql = "SELECT DATE_FORMAT(tijd, '%H:%i') FROM tijd;";
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

	@Override
	public Map<Long, String> getWasmachines() {
		String sql = "SELECT id FROM wasmachine;";
		Map<Long, String> wasmachines = new HashMap<>();

		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(sql);
			rs = preStmt.executeQuery();
			for (long i = 0; rs.next(); i++) {
				wasmachines.put(i, rs.getString(1));
			}
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		}
		return wasmachines;
	}

	@Override
	public String[] getWasschemaData(int startingRange, int endingRange, String wasmachine_id) {
		String sql = "SELECT a.huisnummer FROM wasschema x LEFT JOIN gebruiker a ON x.gebruiker_id = a.id WHERE (x.week_dag_tijd_id BETWEEN ? AND ?) AND x.wasmachine_id = ?;";
		int arraySize = endingRange - startingRange + 1;
		String huisnummers[] = new String[arraySize];

		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(sql);
			preStmt.setInt(1, startingRange);
			preStmt.setInt(2, endingRange);
			preStmt.setString(3, wasmachine_id);
			rs = preStmt.executeQuery();
			for (int i = 0; rs.next(); i++) {
				huisnummers[i] = rs.getString(1);
			}
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		}

		return huisnummers;
	}
}
