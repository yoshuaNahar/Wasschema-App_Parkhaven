package nl.parkhaven.dao.dbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.mysql.cj.api.jdbc.Statement;

import nl.parkhaven.util.Database;

public abstract class JavathlonJdbcTemplate<T> {

	protected List<T> getList(String sql, SqlParameterValues values, SqlRowMapper<T> mapper) {
		List<T> resultList = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			HashMap<String, Integer> sqlBindings = (HashMap<String, Integer>) SqlBindingUtil.getSqlParameters(sql);

			sql = SqlBindingUtil.convertToStandartBindings(sql);

			connection = Database.getConnection();

			preparedStatement = connection.prepareStatement(sql);

			putBindingsToPreparedStatement(preparedStatement, values, sqlBindings);

			resultSet = preparedStatement.executeQuery();

			resultList = convertResultSetToObjectList(mapper, resultSet);

			releaseResources(connection, preparedStatement, resultSet);
		} catch (Exception e) {

		}

		return resultList;
	}

	private void putBindingsToPreparedStatement(PreparedStatement ps, SqlParameterValues values,
			HashMap<String, Integer> sqlBindings) throws Exception {
		for (Entry<String, Object> entry : values.getValues().entrySet()) {
			Object bindingValue = entry.getValue();
			String bindingKey = entry.getKey();
			Integer bindingOrder = null;
			if (sqlBindings.containsKey(bindingKey))
				bindingOrder = sqlBindings.get(bindingKey);
			else
				throw new Exception(bindingKey + " parameter is not bound");

			if (bindingValue instanceof String)
				ps.setString(bindingOrder, (String) bindingValue);
			else if (bindingValue instanceof Long)
				ps.setLong(bindingOrder, (Long) bindingValue);
			else if (bindingValue instanceof Integer)
				ps.setInt(bindingOrder, (Integer) bindingValue);
			else if (bindingValue instanceof Boolean)
				ps.setBoolean(bindingOrder, (Boolean) bindingValue);
			else if (bindingValue instanceof Date)
				ps.setDate(bindingOrder, new java.sql.Date(((Date) bindingValue).getTime()));
		}
	}

	protected T queryForObject(String sql, SqlParameterValues values, SqlRowMapper<T> mapper) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		HashMap<String, Integer> sqlBindings = (HashMap<String, Integer>) SqlBindingUtil.getSqlParameters(sql);

		sql = SqlBindingUtil.convertToStandartBindings(sql);

		connection = Database.getConnection();

		preparedStatement = connection.prepareStatement(sql);

		putBindingsToPreparedStatement(preparedStatement, values, sqlBindings);

		resultSet = preparedStatement.executeQuery();

		List<T> objectList = convertResultSetToObjectList(mapper, resultSet);

		return objectList != null && objectList.size() > 0 ? objectList.get(0) : null;
	}

	protected void update(String sql, SqlParameterValues values) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		HashMap<String, Integer> sqlBindings = (HashMap<String, Integer>) SqlBindingUtil.getSqlParameters(sql);

		sql = SqlBindingUtil.convertToStandartBindings(sql);

		connection = Database.getConnection();

		preparedStatement = connection.prepareStatement(sql);

		putBindingsToPreparedStatement(preparedStatement, values, sqlBindings);

		preparedStatement.executeUpdate();
	}

	protected Long insertItem(String sql, SqlParameterValues values) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		HashMap<String, Integer> sqlBindings = (HashMap<String, Integer>) SqlBindingUtil.getSqlParameters(sql);

		sql = SqlBindingUtil.convertToStandartBindings(sql);

		connection = Database.getConnection();

		preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		putBindingsToPreparedStatement(preparedStatement, values, sqlBindings);

		connection.setAutoCommit(false);

		preparedStatement.executeUpdate();

		ResultSet autoIncrementResultSet = preparedStatement.getGeneratedKeys();
		int autoIncrementedUserId = 0;
		if (autoIncrementResultSet.next()) {
			autoIncrementedUserId = autoIncrementResultSet.getInt(1);
		}
		releaseResources(connection, preparedStatement, autoIncrementResultSet);

		return Long.valueOf(autoIncrementedUserId);
	}

	protected T getObject(String sql, SqlRowMapper<T> mapper, ResultSet resultSet) {
		T item = null;

		if (mapper == null) {
			System.out.println("No rowmapper assigned.");
			return null;
		}

		try {
			if (resultSet.next()) {
				item = mapper.mapSqlToObject(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return item;
	}

	private List<T> convertResultSetToObjectList(SqlRowMapper<T> mapper, ResultSet resultSet) {
		List<T> resultList = new ArrayList<T>();

		if (mapper == null) {
			System.out.println("No rowmapper assigned.");
			return null;
		}

		try {
			while (resultSet.next()) {
				T item = mapper.mapSqlToObject(resultSet);
				resultList.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultList;
	}

	private void releaseResources(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet)
			throws SQLException {
		if (resultSet != null)
			resultSet.close();
		if (preparedStatement != null)
			preparedStatement.close();
		if (connection != null) {
			if (!connection.getAutoCommit())
				connection.commit();
			connection.close();
		}
	}
}
