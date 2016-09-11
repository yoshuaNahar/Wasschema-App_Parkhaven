package nl.parkhaven.dao.abstraction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import nl.parkhaven.entity.User;
import nl.parkhaven.util.Database;

public abstract class UserDaoAbst extends CommonDbActions implements UserDao {

	public User signin(User user) {
		String sql = "SELECT voornaam, achternaam, huisnummer, email, wachtwoord, mobielnummer FROM gebruiker WHERE email = ? AND wachtwoord = ?;";
		first(sql);
		setSigninParameters();

		return user;
	}

	abstract protected void setSigninParameters();

	protected void first(String sql) {
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;

		try {
			conn = Database.getConnection();
			preStmt = conn.prepareStatement(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
