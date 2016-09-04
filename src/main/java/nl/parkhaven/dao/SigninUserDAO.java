package nl.parkhaven.dao;

import nl.parkhaven.dao.dbUtil.JavathlonJdbcTemplate;
import nl.parkhaven.dao.dbUtil.SqlParameterValues;
import nl.parkhaven.dao.rowmappers.UserInfoRowMapper;
import nl.parkhaven.entity.User;

public class SigninUserDAO extends JavathlonJdbcTemplate<User> {

	public User getMemberByUserName(User user) throws Exception {
		String getAllMembersSQL = "SELECT email, firstname, lastname, `password`, huisnummer FROM kees WHERE email = :email AND `password` = :password";

		SqlParameterValues values = new SqlParameterValues();
		values.addValue("email", user.getEmail());
		values.addValue("password", user.getPassword());

		User member = this.queryForObject(getAllMembersSQL, values, new UserInfoRowMapper());

		return member;
	}
}
