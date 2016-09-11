package nl.parkhaven.dao.dbUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SqlRowMapper<T>  {

	T  mapSqlToObject(ResultSet resultSet) throws SQLException;
}
