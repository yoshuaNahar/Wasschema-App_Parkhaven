package nl.parkhaven.wasschema.util;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public final class Database {

	private static ComboPooledDataSource dataSource = configureDataSource();

	private Database() {
	}

	public static Connection getConnection() throws SQLException, PropertyVetoException {
		return dataSource.getConnection();
	}

	public static void closeDataSource() {
		dataSource.close();
	}

	private static ComboPooledDataSource configureDataSource() {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		try {
			dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/parkhaven?serverTimezone=CET&useSSL=false");
		dataSource.setUser("root");
		dataSource.setPassword("geheim");
		dataSource.setMinPoolSize(3);
		dataSource.setMaxPoolSize(3); // 20
		dataSource.setAcquireIncrement(3);
		dataSource.setMaxConnectionAge(14400); // 4 hours 14400
		dataSource.setMaxIdleTimeExcessConnections(300);
		dataSource.setCheckoutTimeout(1000);
		return dataSource;
	}

}
