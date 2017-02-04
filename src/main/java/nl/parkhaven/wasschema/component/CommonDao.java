package nl.parkhaven.wasschema.component;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import nl.parkhaven.wasschema.util.Database;

public abstract class CommonDao {

	protected Connection conn;
	protected PreparedStatement preStmt;
	protected ResultSet rs;

	public void releaseResources() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (preStmt != null) {
				preStmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected Connection getConnection() throws SQLException, PropertyVetoException {
		return conn = Database.getConnection();
	}

}