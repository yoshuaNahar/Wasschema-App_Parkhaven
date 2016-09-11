package nl.parkhaven.dao;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import nl.parkhaven.dao.abstraction.UserDaoAbst;
import nl.parkhaven.dao.abstraction.UserDao;
import nl.parkhaven.entity.User;
import nl.parkhaven.util.Database;

public final class UserDaoImpl extends UserDaoAbst implements UserDao {

	@Override
	public User signin(User user) {
		String sql = "SELECT voornaam, achternaam, huisnummer, email, wachtwoord, mobielnummer FROM gebruiker WHERE email = ? AND wachtwoord = ?;";
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		User signedinUser = new User();

		try {
			conn = Database.getConnection();
			preStmt = conn.prepareStatement(sql);
			preStmt.setString(1, user.getEmail());
			preStmt.setString(2, user.getPassword());
			rs = preStmt.executeQuery();
			rs.next();
			signedinUser.setFirstname(rs.getString(1));
			signedinUser.setLastname(rs.getString(2));
			signedinUser.setHuisnummer(rs.getString(3));
			signedinUser.setEmail(rs.getString(4));
			signedinUser.setPassword(rs.getString(5));
			signedinUser.setMobielnummer(rs.getString(6));
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			releaseResources(conn, preStmt, rs);
		}

		return signedinUser;
	}

	@Override
	public User signup(User user) {
		String sql = "INSERT INTO parkhaven (voornaam, achternaam, huisnummer, email, wachtwoord, mobielnummer, registrationDate) VALUES (?, ? ,? ,? ,? ,?, ?);";

		return null;
	}
}
