package nl.parkhaven.wasschema.modules.util;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public final class Database {

	private static final ComboPooledDataSource DATA_SOURCE = createDataSource();

	private Database() {
	}

	public static Connection getConnection() throws SQLException, PropertyVetoException {
		return DATA_SOURCE.getConnection();
	}

	public static void closeDataSource() {
		DATA_SOURCE.close();
	}

	private static ComboPooledDataSource createDataSource() {
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
		dataSource.setMaxConnectionAge(14400); // 4 hours
		dataSource.setMaxIdleTimeExcessConnections(300);
		dataSource.setCheckoutTimeout(1000);
		return dataSource;
	}

}
