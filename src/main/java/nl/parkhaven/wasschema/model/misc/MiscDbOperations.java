package nl.parkhaven.wasschema.model.misc;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

import nl.parkhaven.wasschema.model.CommonDao;
import nl.parkhaven.wasschema.model.user.User;

public final class MiscDbOperations extends CommonDao {

	public int[] getWashCounter(User user) {
		String sql = "SELECT COUNT(gebruiker_id) FROM wasschema x LEFT JOIN week_dag_tijd a ON x.week_dag_tijd_id = a.id WHERE x.gebruiker_id = ? GROUP BY a.week_id;";
		int[] wascounter = new int[2];

		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(sql);
			preStmt.setInt(1, user.getId());
			rs = preStmt.executeQuery();
			for (int i = 0; rs.next(); i++) {
				wascounter[i] = rs.getInt(1);
			}
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			releaseResources();
		}

		return wascounter;
	}

	public void storeHitCounter(int hitcounter) {
		String sql = "INSERT INTO metadata (hitcounter) VALUES (?);";

		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(sql);
			preStmt.setInt(1, hitcounter);
			preStmt.executeUpdate();
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			releaseResources();
		}
	}

	public void storeTotalWashCounter(int wascounter) {
		String storeInMetaDataSQL = "INSERT INTO metadata (hitcounter, counter_type) VALUES (?, 'washcounter');";

		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(storeInMetaDataSQL);
			preStmt.setInt(1, wascounter);
			preStmt.executeUpdate();
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			releaseResources();
		}
	}

}
