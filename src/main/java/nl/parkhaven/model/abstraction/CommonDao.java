package nl.parkhaven.model.abstraction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CommonDao<E> implements CrudDao<E> {

	protected Connection conn;
	protected PreparedStatement preStmt;
	protected ResultSet rs;

	protected void releaseResources() {
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
}
