package nl.parkhaven.dao.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.parkhaven.dao.dbUtil.SqlRowMapper;
import nl.parkhaven.entity.User;

public class UserInfoRowMapper implements SqlRowMapper<User> {
	public User mapSqlToObject(ResultSet resultSet) throws SQLException {
		User member = new User();
		member.setEmail(resultSet.getString(1));
		member.setFirstname(resultSet.getString(2));
		member.setLastname(resultSet.getString(3));
		member.setPassword(resultSet.getString(4));
		member.setHuisnummer(resultSet.getString(5));
		return member;
	}
}
