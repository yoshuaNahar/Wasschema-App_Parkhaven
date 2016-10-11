package nl.parkhaven.model.util;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public final class Database {

	private static ComboPooledDataSource dataSource;

	public static void initializeDataSource() throws PropertyVetoException {
		if (dataSource == null) {
			dataSource = new ComboPooledDataSource();
			dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
			dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/parkhaven?serverTimezone=UTC&useSSL=false");
			dataSource.setUser("root");
			dataSource.setPassword("geheim");
			dataSource.setMinPoolSize(3);
			dataSource.setMaxPoolSize(20);
		}
	}

	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	public static void closeDataSource() {
		if (dataSource != null) {
			dataSource.close();
		}
	}
}
