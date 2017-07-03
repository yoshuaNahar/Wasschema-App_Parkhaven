package nl.parkhaven.wasschema.modules.appointment;

import java.sql.ResultSet;
import java.sql.SQLException;
import nl.parkhaven.wasschema.modules.Crud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class AppointmentDaoImpl implements Crud<Appointment> {

  private static final String CHECK_FREE = "SELECT x.gebruiker_id FROM wasschema x WHERE x.week_dag_tijd_id = ? AND x.wasmachine_id = ?;";
  private static final String ADD_APPOINTMENT = "UPDATE wasschema SET gebruiker_id = ? WHERE week_dag_tijd_id = ? AND wasmachine_id = ?;";
  private static final String REMOVE_APPOINTMENT = "UPDATE wasschema x SET gebruiker_id = NULL WHERE x.week_dag_tijd_id = ? AND x.wasmachine_id = ?;";
  private static final String CHECK_APPOINTMENT_IS_YOURS = "SELECT gebruiker_id FROM wasschema WHERE week_dag_tijd_id = ? AND wasmachine_id = ?;";
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
      return template.queryForObject(CHECK_FREE,
          new Object[]{ap.week_day_time_id(), ap.getMachine()}, new AppointmentRowMapper());
    } catch (EmptyResultDataAccessException e) {
      e.printStackTrace();
      return new Appointment();
    }
  }

  @Override
  public boolean update(Appointment ap) {
    this.template.update(ADD_APPOINTMENT, ap.getUserId(), ap.week_day_time_id(), ap.getMachine());
    return true;
  }

  public Integer checkBeforeDelete(Appointment ap) {
    try {
      return this.template.queryForObject(CHECK_APPOINTMENT_IS_YOURS,
          new Object[]{ap.week_day_time_id(), ap.getMachine()}, Integer.class);
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  // Sets userId in wasschema table to NULL
  @Override
  public boolean delete(Appointment ap) {
    try {
      int rowsAffected = this.template.update(REMOVE_APPOINTMENT,
          ap.week_day_time_id(), ap.getMachine());
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
