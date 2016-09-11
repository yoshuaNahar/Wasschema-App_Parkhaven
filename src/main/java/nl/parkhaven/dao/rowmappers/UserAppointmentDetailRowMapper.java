package nl.parkhaven.dao.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.parkhaven.dao.dbUtil.SqlRowMapper;
import nl.parkhaven.entity.Appointment;

public class UserAppointmentDetailRowMapper implements SqlRowMapper<Appointment> {

	public Appointment mapSqlToObject(ResultSet resultSet) throws SQLException {
		Appointment member = new Appointment();
		member.setHuisnummer(resultSet.getString(1));
		return member;
	}
}
