package nl.parkhaven.wasschema.modules.appointment;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import nl.parkhaven.wasschema.modules.Crud;

@Repository
public class AppointmentDaoImpl implements Crud<Appointment> {

	private static final String CHECK_FREE_AND_5MIN_BEFORE_ACTUAL_TIME = "CALL check_free_and_atleast_5min_prior(?, ?);";
	private static final String ADD_APPOINTMENT = "UPDATE wasschema SET gebruiker_id = ? WHERE week_dag_tijd_id = ? AND wasmachine_id = ?;";
	private static final String REMOVE_APPOINTMENT = "CALL remove_appointment_if_atleast30min_in_future(?, ?);";

	private JdbcTemplate template;

	@Autowired
	AppointmentDaoImpl(JdbcTemplate template) {
		this.template = template;
	}

	@Override
	public boolean create(Appointment ap) {
		// New record creation is done weekly by a MySQL Event.
		throw new RuntimeException("Not implemented!");
	}

	@Override
	public Appointment read(Appointment ap) {
		try {
			return template.queryForObject(CHECK_FREE_AND_5MIN_BEFORE_ACTUAL_TIME,
					new Object[] { ap.week_day_time_id(), ap.getMachine() }, new AppointmentRowMapper());
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return new Appointment();
		}
	}

	@Override
	public boolean update(Appointment ap) {
		this.template.update(ADD_APPOINTMENT, new Object[] { ap.getUserId(), ap.week_day_time_id(), ap.getMachine() });
		return true;
	}

	// Sets userId in wasschema table to NULL
	@Override
	public boolean delete(Appointment ap) {
		try {
			int rowsAffected = this.template.update(REMOVE_APPOINTMENT,
					new Object[] { ap.week_day_time_id(), ap.getMachine() });
			if (rowsAffected != 1) {
				// if executeUpdate == 0, the wasmachine or time and date were
				// incorrect or appointment was less than 30 min in the future
				return false;
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static class AppointmentRowMapper implements RowMapper<Appointment> {
		@Override
		public Appointment mapRow(ResultSet rs, int rowNum) throws SQLException {
			Appointment ap = new Appointment();
			ap.setUserId(rs.getInt(1));
			return ap;
		}
	}

}
