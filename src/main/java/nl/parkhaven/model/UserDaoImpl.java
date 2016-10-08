package nl.parkhaven.model;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nl.parkhaven.model.abstraction.CommonDao;
import nl.parkhaven.model.entity.User;
import nl.parkhaven.model.util.Database;

public final class UserDaoImpl extends CommonDao<User> {

	String getAllMembersSql = "SELECT email, firstname, lastname, huisnummer, registrationDate FROM demo.kees";

	/*
	 * One DAO class per Entity and Table DAO needs to perform CRUD actions
	 * Create = signup Retrieve = Signin TODO Update = Change
	 * password/email/huisnummer etc TODO Delete = Admin remove user
	 * 
	 * TODO Extra -- Retrieve, based on huisnummer or email or voor/achternaam
	 * or number of washes per week maybe
	 */

	private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

	@Override
	public User read(User user) {
		String signinSQL = "SELECT voornaam, achternaam, huisnummer, email, wachtwoord, mobielnummer FROM gebruiker WHERE email = ? AND wachtwoord = ?;";
		User signedinUser = new User();

		try {
			conn = Database.getConnection();
			preStmt = conn.prepareStatement(signinSQL);
			preStmt.setString(1, user.getEmail());
			preStmt.setString(2, user.getWachtwoord());
			rs = preStmt.executeQuery();
			rs.next();
			try {
				signedinUser.setVoornaam(rs.getString(1));
				signedinUser.setAchternaam(rs.getString(2));
				signedinUser.setHuisnummer(rs.getString(3));
				signedinUser.setEmail(rs.getString(4));
				signedinUser.setWachtwoord(rs.getString(5));
				signedinUser.setMobielnummer(rs.getString(6));
			} catch (SQLException e) {
				logger.info("Wrong email or password was used! Email: " + user.getEmail() + " - Password: "
						+ user.getWachtwoord());
			}
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			releaseResources();
		}

		return signedinUser;
	}

	@Override
	public void create(User user) {
		String signupSQL = "INSERT INTO gebruiker (voornaam, achternaam, huisnummer, email, wachtwoord, mobielnummer) VALUES (?, ? ,? ,? ,? ,?);";
// Original		String signupSQL = "INSERT INTO gebruiker (voornaam, achternaam, huisnummer, email, wachtwoord, mobielnummer, registratieDatum) VALUES (?, ? ,? ,? ,? ,?, ?);";

		try {
			conn = Database.getConnection();
			preStmt = conn.prepareStatement(signupSQL);
			preStmt.setString(1, user.getVoornaam());
			preStmt.setString(2, user.getAchternaam());
			preStmt.setString(3, user.getHuisnummer());
			preStmt.setString(4, user.getEmail());
			preStmt.setString(5, user.getWachtwoord());
			preStmt.setString(6, user.getMobielnummer());
//			preStmt.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now())); // This removed so that the db can enter this value itself!
			int succes = preStmt.executeUpdate();
			if (succes != 1) {
				throw new SQLException();
			}
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
			logger.error("More than 1 row was inserted!");
		} finally {
			releaseResources();
		}
		// This user will be inserted inside the singin to sign the
		// user in directly!
	}

	@Override
	public void update(User e) {
	}

	@Override
	public void delete(User e) {
	}
}
