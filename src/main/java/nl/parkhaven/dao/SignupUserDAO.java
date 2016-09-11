package nl.parkhaven.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import nl.parkhaven.dao.dbUtil.JavathlonJdbcTemplate;
import nl.parkhaven.dao.dbUtil.SqlParameterValues;
import nl.parkhaven.dao.rowmappers.UserInfoRowMapper;
import nl.parkhaven.entity.User;


public class SignupUserDAO extends JavathlonJdbcTemplate<User> {

	String getAllMembersSql = "SELECT email, firstname, lastname, huisnummer, registrationDate FROM demo.kees";
	String insertMemberSql = "INSERT INTO parkhaven(voornaam, achternaam, huisnummer, email, wachtwoord, mobielnummer, registrationDate) VALUES ( :email , :firstname , :lastname , :password , :huisnummer , :registrationDate );";

	public List<User> getAllMembers() {
		List<User> allMembers = this.getList(getAllMembersSql, new SqlParameterValues(), new UserInfoRowMapper());
		return allMembers;
	}

	public Long insertMember(User member) throws Exception {
		SqlParameterValues values = new SqlParameterValues();
		values.addValue("email", member.getEmail());
		values.addValue("firstname", member.getFirstname());
		values.addValue("lastname", member.getLastname());
		values.addValue("password", member.getPassword());
		values.addValue("huisnummer", member.getHuisnummer());
		values.addValue("registrationDate", new Timestamp(new Date().getTime()));

		return this.insertItem(insertMemberSql, values);
	}

	public User getMemberByUserName(String email) throws Exception {
		String sql = getAllMembersSql + " WHERE email = :email";
		User member = this.queryForObject(sql, new SqlParameterValues().addValue("email", email), new UserInfoRowMapper());
		return member;
	}
}
