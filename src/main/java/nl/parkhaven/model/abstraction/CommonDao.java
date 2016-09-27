package nl.parkhaven.model.abstraction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CommonDao {

	protected Connection conn = null;
	protected PreparedStatement preStmt = null;
	protected ResultSet rs = null;

	protected void releaseResources(Connection conn, PreparedStatement preStmt, ResultSet rs) {
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
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
