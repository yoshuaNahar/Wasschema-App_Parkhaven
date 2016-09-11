package nl.parkhaven.dao.abstraction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CommonDbActions {

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
