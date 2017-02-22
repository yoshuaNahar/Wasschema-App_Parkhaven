package nl.parkhaven.wasschema.modules.misc;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

import nl.parkhaven.wasschema.modules.CommonDao;

public final class MetaDataOperations extends CommonDao {

	// used when restarting application, to save the ocunter for amount of page refreshes, and washes placed
	public void insertCounter(int counter, String type) {
		String storeMetaDataSQL = "INSERT INTO metadata (counter, counter_type) VALUES (?, ?);";
		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(storeMetaDataSQL);
			preStmt.setInt(1, counter);
			preStmt.setString(2, type);
			preStmt.executeUpdate();
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			releaseResources();
		}
	}

	// not used yet, but this is to get the old counters after a restart
	public int getCounterSum(String type) {
		String getCounterSumInMetaDataSQL = "SELECT SUM(counter) FROM metadata WHERE counter_type = ?;";
		int sum = 0;
		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(getCounterSumInMetaDataSQL);
			preStmt.setString(1, type);
			rs = preStmt.executeQuery();
			rs.next();
			sum = rs.getInt(1);
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			releaseResources();
		}
		return sum;
	}

	// method only to be used for testing
	public void deleteLastCounter() {
		String deleteSpecificMetaDataRowSQL = "DELETE FROM metadata ORDER BY id DESC LIMIT 1;";
		/*
		 *  DELETE FROM metadata WHERE id = SELECT MAX(id) FROM metadata;
		 *  In MySQL, you can't modify (INSERT, UPDATE, DELETE) the same table which you use in the SELECT part.
		 *  http://stackoverflow.com/questions/45494/mysql-error-1093-cant-specify-target-table-for-update-in-from-clause
		 */
		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(deleteSpecificMetaDataRowSQL);
			preStmt.executeUpdate();
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			releaseResources();
		}
	}

}
