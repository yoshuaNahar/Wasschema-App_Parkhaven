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

	public Date getDateForEmail(Appointment ap) {
		String sql = "CALL get_week_dag_en_tijd(?, ?);";
		Date date = null;

		try {
			conn = getConnection();
			preStmt = conn.prepareCall(sql);
			preStmt.setInt(1, ap.week_dag_tijd_id());
			preStmt.setString(2, ap.getWasmachine());

			rs = preStmt.executeQuery();

			rs.next();
			date = rs.getTimestamp(1);
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		}

		return date;
	}

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

//	private static void testGetCounter() {
//		User user = new User();
//		user.setId(1);
//
//		System.out.println(new DbProcedureCalls().getCounter(user));
//	}
//
//	private static void testGetDateForEmail() {
//		Appointment ap = new Appointment();
//		ap.setTime("7");
//		ap.setDay("1");
//		ap.setWasmachine("C1");
//		System.out.println(ap.week_dag_tijd_id());
//
//		System.out.println(new DbProcedureCalls().getDateForEmail(ap).toString());
//	}
//
//	// This should be in a JUnit test. NOT in the maven build but as personal tests!
//	public static void main(String[] args) {
////		testGetDateForEmail();
//
//		testGetCounter();
//	}
}
