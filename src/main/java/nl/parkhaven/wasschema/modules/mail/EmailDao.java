package nl.parkhaven.wasschema.modules.mail;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nl.parkhaven.wasschema.modules.CommonDao;
import nl.parkhaven.wasschema.modules.user.User;

public final class EmailDao extends CommonDao {

	public List<User> getEmailAddresses() {
		String sqlGetEmail = "SELECT c.email, d.name, b.tijd_default FROM parkhaven.wasschema x "
				+ "LEFT JOIN week_dag_tijd a ON x.week_dag_tijd_id = a.id LEFT JOIN tijd b ON a.tijd_id = b.id "
				+ "LEFT JOIN gebruiker c ON c.id = x.gebruiker_id LEFT JOIN wasmachine d ON d.id = x.wasmachine_id WHERE x.week_dag_tijd_id < 92 "
				+ "AND NOW() + INTERVAL 3 HOUR < tijd_default " + "AND WEEKDAY(NOW()) + 1 = a.dag_id "
				+ "ORDER BY week_dag_tijd_id LIMIT 12;";
		List<User> users = new ArrayList<>();
		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(sqlGetEmail);
			rs = preStmt.executeQuery();
			while (rs.next()) {
				String email = rs.getString(1);
				if (email != null) {
					User user = new User();
					user.setEmail(email);
					user.setPassword(rs.getString(2));
					user.setHouseNumber(rs.getString(3));
					users.add(user);
				}
			}
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			releaseResources();
		}
		return users;
	}

}
