package nl.parkhaven.wasschema.component.user;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import nl.parkhaven.wasschema.component.CommonDao;
import nl.parkhaven.wasschema.component.Crud;

final class UserDaoImpl extends CommonDao implements Crud<User> {

	@Override
	public boolean create(User user) {
		String signupSQL = "INSERT INTO gebruiker (voornaam, achternaam, huisnummer, email, wachtwoord) VALUES (?, ?, ?, ?, ?);";
		boolean bool = false;
		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(signupSQL);
			preStmt.setString(1, user.getFirstName());
			preStmt.setString(2, user.getLastName());
			preStmt.setString(3, user.getHouseNumber());
			preStmt.setString(4, user.getEmail());
			preStmt.setString(5, user.getPassword());
			preStmt.executeUpdate();
			bool = true;
		} catch (SQLException | PropertyVetoException e) {
			if (e instanceof SQLIntegrityConstraintViolationException) {
				bool = false;
			} else {
				e.printStackTrace();
			}
		} finally {
			releaseResources();
		}
		return bool;
	}

	@Override
	public User read(User user) {
		String signinSQL = "SELECT id, voornaam, achternaam, huisnummer, email, wachtwoord, admin FROM gebruiker WHERE email = ? AND wachtwoord = ?;";
		User outputUser = null;
		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(signinSQL);
			preStmt.setString(1, user.getEmail());
			preStmt.setString(2, user.getPassword());
			rs = preStmt.executeQuery();
			if (rs.next()) {
				outputUser = new User();
				outputUser.setId(rs.getInt(1));
				outputUser.setFirstName(rs.getString(2));
				outputUser.setLastName(rs.getString(3));
				outputUser.setHouseNumber(rs.getString(4));
				outputUser.setEmail(rs.getString(5));
				outputUser.setPassword(rs.getString(6));
				outputUser.setAdmin(rs.getString(7).equals("Y"));
			}
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			releaseResources();
		}
		return outputUser;
	}

	/*
	 * Three methods for changing user settings. The changeable settings are
	 * huisnummer, email, password. I created two other methods for email and
	 * password!
	 */

	@Override
	public boolean update(User user) {
		String updateHousenumberSQL = "UPDATE gebruiker SET huisnummer = ? WHERE id = ?;";
		boolean bool = false;
		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(updateHousenumberSQL);
			preStmt.setString(1, user.getHouseNumber());
			preStmt.setInt(2, user.getId());
			int rowsUpdated = preStmt.executeUpdate();
			if (rowsUpdated == 1) {
				bool = true;
			}
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			releaseResources();
		}
		return bool;
	}

	public boolean updatePassword(User user) {
		String updatePasswordSQL = "UPDATE gebruiker SET wachtwoord = ? WHERE id = ?;";
		boolean bool = false;
		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(updatePasswordSQL);
			preStmt.setString(1, user.getPassword());
			preStmt.setInt(2, user.getId());
			int rowsUpdated = preStmt.executeUpdate();
			if (rowsUpdated == 1) {
				bool = true;
			}
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			releaseResources();
		}
		return bool;
	}

	// Not implemented in website yet
	public boolean updateEmail(User user) {
		String updatePasswordSQL = "UPDATE gebruiker SET email = ? WHERE id = ?;";
		boolean bool = false;
		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(updatePasswordSQL);
			preStmt.setString(1, user.getEmail());
			preStmt.setInt(2, user.getId());
			int rowsUpdated = preStmt.executeUpdate();
			if (rowsUpdated == 1) {
				bool = true;
			}
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			releaseResources();
		}
		return bool;
	}

	@Override
	public boolean delete(User user) {
		// CONCAT with _ because Db columns are unique
		String deactiveUserSQL = "UPDATE gebruiker SET actief = 'N', email = CONCAT(email, ?), huisnummer = CONCAT(huisnummer, ?) WHERE id = ? AND wachtwoord = ?;";
		boolean bool = false;
		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(deactiveUserSQL);
			preStmt.setInt(1, user.getId());
			preStmt.setInt(2, user.getId());
			preStmt.setInt(3, user.getId());
			preStmt.setString(4, user.getPassword());
			int rowsUpdated = preStmt.executeUpdate();
			if (rowsUpdated == 1) {
				deleteAllAppointmentsFromDeletedUser(user);
				bool = true;
			}
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			releaseResources();
		}
		return bool;
	}

	private void deleteAllAppointmentsFromDeletedUser(User user) {
		String deleteAppointmentsSQL = "UPDATE wasschema SET gebruiker_id = NULL WHERE gebruiker_id = ?;";
		try {
			preStmt = conn.prepareStatement(deleteAppointmentsSQL);
			preStmt.setInt(1, user.getId());
			preStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Washcounter to check if user didn't wash more than 3 times
	public int[] getWashCounter(User user) {
		String sql = "SELECT COUNT(gebruiker_id) FROM wasschema x LEFT JOIN week_dag_tijd a ON x.week_dag_tijd_id = a.id WHERE x.gebruiker_id = ? GROUP BY a.week_id;";
		int[] wascounter = new int[2];
		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(sql);
			preStmt.setInt(1, user.getId());
			rs = preStmt.executeQuery();
			for (int i = 0; rs.next(); i++) {
				wascounter[i] = rs.getInt(1);
			}
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			releaseResources();
		}
		return wascounter;
	}

	// only to be used in tests to delete dummy accounts
	public void deleteCompletely(User user) {
		String removeUserSQL = "DELETE FROM gebruiker WHERE id = ?;";
		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(removeUserSQL);
			preStmt.setInt(1, user.getId());
			preStmt.executeUpdate();
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			releaseResources();
		}
	}

}
