package nl.parkhaven.dao;

import nl.parkhaven.dao.dbUtil.JavathlonJdbcTemplate;
import nl.parkhaven.dao.dbUtil.SqlParameterValues;
import nl.parkhaven.dao.rowmappers.UserAppointmentDetailRowMapper;
import nl.parkhaven.entity.AppointmentPlacer;

public class AppointmentDAO extends JavathlonJdbcTemplate<AppointmentPlacer> {

	private String checkIfFreeSQL = "SELECT huisnummer FROM calender WHERE calenderID = :calenderID AND machine = :machine";
	private String enterValueSQL = "UPDATE calender SET huisnummer = :huisnummer WHERE calenderID = :calenderID AND machine = :machine";

	public boolean checkIfFree(int row, int machinenummer) throws Exception {
		SqlParameterValues values = new SqlParameterValues();
		values.addValue("calenderID", row);
		values.addValue("machine", machinenummer);
		AppointmentPlacer member = this.queryForObject(checkIfFreeSQL, values, new UserAppointmentDetailRowMapper());
		if(member.getHuisnummer() == 0)
			return true;
		else 
			return false;
	}

	public void enterNumberOnDate(int row, int machine, int huisnummer) throws Exception {
		SqlParameterValues values = new SqlParameterValues();
		values.addValue("calenderID", row);
		values.addValue("machine", machine);
		values.addValue("huisnummer", huisnummer);

		this.insertItem(enterValueSQL, values);
	}
}
