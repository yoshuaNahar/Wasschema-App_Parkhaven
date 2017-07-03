package nl.parkhaven.wasschema.modules.mail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import nl.parkhaven.wasschema.modules.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class EmailDao {

  private static final String GET_USERS_EMAIL_WASMACHINE_TIME =
      "SELECT c.email, d.name FROM wasschema x " // , b.tijd_default (the actual time)
          + "LEFT JOIN week_dag_tijd a ON x.week_dag_tijd_id = a.id LEFT JOIN tijd b ON a.tijd_id = b.id "
          + "LEFT JOIN gebruiker c ON c.id = x.gebruiker_id LEFT JOIN wasmachine d ON d.id = x.wasmachine_id WHERE x.week_dag_tijd_id < 92 "
          + "AND TIME(NOW() + INTERVAL 3 HOUR) BETWEEN TIME(tijd_default - INTERVAL 5 MINUTE) AND TIME(tijd_default + INTERVAL 5 MINUTE) "
          + "AND WEEKDAY(NOW()) + 1 = a.dag_id "
          + "ORDER BY week_dag_tijd_id LIMIT 12;";

  private JdbcTemplate template;

  @Autowired
  EmailDao(JdbcTemplate template) {
    this.template = template;
  }

  public List<User> getUsersThatHaveAnAppointmentIn3Hours() {
    return this.template.query(GET_USERS_EMAIL_WASMACHINE_TIME, new UserRowMapper());
  }

  private static class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
      User user = new User();
      user.setEmail(rs.getString("email"));
      user.setPassword(rs.getString("name")); // (name = wasmachine) just look away...
      return user;
    }

  }

}
