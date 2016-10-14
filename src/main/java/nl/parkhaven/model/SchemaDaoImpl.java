package nl.parkhaven.model;

import java.beans.PropertyVetoException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nl.parkhaven.model.abstraction.CommonDao;
import nl.parkhaven.model.abstraction.SchemaDao;

final class SchemaDaoImpl extends CommonDao implements SchemaDao {

	private static final Logger logger = LogManager.getLogger(SchemaDaoImpl.class);

	@Override
	public NavigableMap<Long, Date> getDates() {
		String sql = "SELECT id, week_dag FROM week_dag ORDER BY id DESC LIMIT 21;";
		NavigableMap<Long, Date> dates = new TreeMap<>();

		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(sql);
			rs = preStmt.executeQuery();
			while (rs.next()) {
				dates.put((long) rs.getInt(1), rs.getDate(2));
			}
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		}

		return dates;
	}

	@Override
	public ArrayList<Time> getTimes() {
		String sql = "SELECT tijd FROM tijd;";
		ArrayList<Time> times = new ArrayList<>();

		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(sql);
			rs = preStmt.executeQuery();
			while (rs.next()) {
				times.add(rs.getTime(1));
			}
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		}

		return times;
	}

	@Override
	public String[] getWasschemaData(int startingRange, int endingRange, String wasmachine_id) {
		String sql = "SELECT a.huisnummer FROM wasschema LEFT JOIN gebruiker a ON gebruiker_id = a.id WHERE (week_dag_tijd_id BETWEEN ? AND ?) AND wasmachine_id = ?;";
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
