package nl.parkhaven.wasschema.model.user;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import nl.parkhaven.wasschema.model.CommonDao;
import nl.parkhaven.wasschema.model.Crud;

final class UserDaoImpl extends CommonDao implements Crud<User> {

	@Override
	public boolean create(User user) {
		String signupSQL = "INSERT INTO gebruiker (voornaam, achternaam, huisnummer, email, wachtwoord) VALUES (?, ?, ?, ?, ?);";
		boolean bool = false;

		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(signupSQL);
			preStmt.setString(1, user.getVoornaam());
			preStmt.setString(2, user.getAchternaam());
			preStmt.setString(3, user.getHuisnummer());
			preStmt.setString(4, user.getEmail());
			preStmt.setString(5, user.getWachtwoord());
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

		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(signinSQL);
			preStmt.setString(1, user.getEmail());
			preStmt.setString(2, user.getWachtwoord());
			rs = preStmt.executeQuery();
			if (rs.next()) {
				user.setId(rs.getInt(1));
				user.setVoornaam(rs.getString(2));
				user.setAchternaam(rs.getString(3));
				user.setHuisnummer(rs.getString(4));
				user.setEmail(rs.getString(5));
				user.setWachtwoord(rs.getString(6));
				user.setAdmin(rs.getString(7).equals("Y"));
			}
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			releaseResources();
		}

		return user;
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
			preStmt.setString(1, user.getHuisnummer());
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
		String updatePasswordSQL = "UPDATE gebruiker SET wachtwoord = ? WHERE email = ?;";
		boolean bool = false;

		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(updatePasswordSQL);
			preStmt.setString(1, user.getWachtwoord());
			preStmt.setString(2, user.getEmail());
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
			preStmt.setString(4, user.getWachtwoord());
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

}
