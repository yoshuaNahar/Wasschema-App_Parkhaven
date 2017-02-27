package nl.parkhaven.wasschema.modules.mail;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import nl.parkhaven.wasschema.modules.user.User;
import nl.parkhaven.wasschema.modules.user.UserDaoImpl.UserRowMapper;

@Repository
public final class EmailDao {

	private static final String GET_USERS_EMAIL = "SELECT c.email, d.name, b.tijd_default FROM parkhaven.wasschema x "
			+ "LEFT JOIN week_dag_tijd a ON x.week_dag_tijd_id = a.id LEFT JOIN tijd b ON a.tijd_id = b.id "
			+ "LEFT JOIN gebruiker c ON c.id = x.gebruiker_id LEFT JOIN wasmachine d ON d.id = x.wasmachine_id WHERE x.week_dag_tijd_id < 92 "
			+ "AND NOW() + INTERVAL 3 HOUR < tijd_default " + "AND WEEKDAY(NOW()) + 1 = a.dag_id "
			+ "ORDER BY week_dag_tijd_id LIMIT 12;";
	private JdbcTemplate template;

	@Autowired
	public EmailDao(JdbcTemplate template) {
		this.template = template;
	}

	public List<User> getEmailAddresses() {
		return this.template.query(GET_USERS_EMAIL, new UserRowMapper());
	}

}
