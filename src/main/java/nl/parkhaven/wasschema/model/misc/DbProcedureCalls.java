package nl.parkhaven.wasschema.model.misc;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nl.parkhaven.wasschema.model.CommonDao;
import nl.parkhaven.wasschema.model.appointment.Appointment;
import nl.parkhaven.wasschema.model.user.User;

public final class DbProcedureCalls extends CommonDao {

	private static final Logger logger = LogManager.getLogger(DbProcedureCalls.class);

	public Integer getCounter(User user) {
		String sql = "CALL check_wash_count(?, ?);";
		int counter = 0;

		try {
			conn = getConnection();
			preStmt = conn.prepareCall(sql);
			preStmt.setInt(1, user.getId());
			preStmt.setDate(2, java.sql.Date.valueOf(java.time.LocalDate.now().plusDays(7))); // Original should remove the plus method!

			rs = preStmt.executeQuery();

			rs.next();
			counter = rs.getInt(1);
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		}

		return counter;
	}
}
