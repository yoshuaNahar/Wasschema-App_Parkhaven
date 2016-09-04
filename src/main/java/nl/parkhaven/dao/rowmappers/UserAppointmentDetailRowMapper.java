package nl.parkhaven.dao.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.parkhaven.dao.dbUtil.SqlRowMapper;
import nl.parkhaven.entity.AppointmentPlacer;

public class UserAppointmentDetailRowMapper implements SqlRowMapper<AppointmentPlacer> {
	public AppointmentPlacer mapSqlToObject(ResultSet resultSet) throws SQLException {
		AppointmentPlacer member = new AppointmentPlacer();
		member.setHuisnummer(resultSet.getString(1));
		return member;
	}
}
