package nl.parkhaven.model.util;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class Database {

	private static ComboPooledDataSource dataSource;

	private static ComboPooledDataSource getInstance() throws PropertyVetoException {
		if (dataSource == null) {
			dataSource = new ComboPooledDataSource();
			dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
			dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/parkhaven?serverTimezone=UTC&useSSL=false");
			dataSource.setUser("root");
			dataSource.setPassword("geheim");
			dataSource.setMinPoolSize(3);
			dataSource.setMaxPoolSize(20);
		}

		return dataSource;
	}

	public static Connection getConnection() throws SQLException, PropertyVetoException {
		return getInstance().getConnection();
	}
}
