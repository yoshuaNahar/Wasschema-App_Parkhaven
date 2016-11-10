package nl.parkhaven.wasschema.model.user;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nl.parkhaven.wasschema.model.CommonDao;
import nl.parkhaven.wasschema.model.CrudDao;

final class UserDaoImpl extends CommonDao implements CrudDao<User> {

	private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

	@Override
	public User read(User user) {
		String signinSQL = "SELECT id, voornaam, achternaam, huisnummer, email, wachtwoord, mobielnummer, admin FROM gebruiker WHERE email = ? AND wachtwoord = ?;";

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
				user.setMobielnummer(rs.getString(7));
				user.setAdmin(rs.getString(8).equals("Y"));
			} else {
				logger.info("Wrong email or password was used! Email: " + user.getEmail() + " - Password: "
						+ user.getWachtwoord());
			}
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			releaseResources();
		}

		return user;
	}

	@Override
	public boolean create(User user) {
		String signupSQL = "INSERT INTO gebruiker (voornaam, achternaam, huisnummer, email, wachtwoord, mobielnummer) VALUES (?, ?, ?, ?, ?, ?);";
		boolean bool = false;

		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(signupSQL);
			preStmt.setString(1, user.getVoornaam());
			preStmt.setString(2, user.getAchternaam());
			preStmt.setString(3, user.getHuisnummer());
			preStmt.setString(4, user.getEmail());
			preStmt.setString(5, user.getWachtwoord());
			preStmt.setString(6, user.getMobielnummer());
			int succes = preStmt.executeUpdate();
			if (succes == 1) {
				bool = true;
			} else {
				logger.error("More than 1 row was inserted!");
				throw new SQLException("More than 1 row was inserted!");
			}
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
			logger.error("Huisnummer already taken!");
		}

		return bool;
	}

	@Override
	public boolean update(User e) {
		return false;
	}

	@Override
	public boolean delete(User e) {
		return false;
	}
}
