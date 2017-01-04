package nl.parkhaven.wasschema.model.prikbord;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import nl.parkhaven.wasschema.model.CommonDao;
import nl.parkhaven.wasschema.model.Crud;

final class PrikbordDaoImpl extends CommonDao implements Crud<PrikbordMessage> {

	@Override
	public boolean create(PrikbordMessage message) {
		String sql = "INSERT INTO prikbord_bericht (gebruiker_id, title_input, body_input, title_output, body_output) VALUES (?, ?, ?, ?, ?);";
		boolean bool = false;

		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(sql);
			preStmt.setInt(1, message.getGebruikerId());
			preStmt.setString(2, message.getTitleInput());
			preStmt.setString(3, message.getBodyInput());
			preStmt.setString(4, message.getTitleOutput());
			preStmt.setString(5, message.getBodyOutput());
			preStmt.executeUpdate();
			bool = true;
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			releaseResources();
		}

		return bool;
	}

	@Override
	public PrikbordMessage read(PrikbordMessage message) {
		// I return the entire list of messages from the database, not
		// indiviually.
		new RuntimeException("Not implemented!");
		return null;
	}

	// Activate message (by admin)
	@Override
	public boolean update(PrikbordMessage message) {
		String sql = "UPDATE prikbord_bericht SET actief_beheerder='Y' WHERE id=?;";
		boolean bool = false;

		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(sql);
			preStmt.setInt(1, message.getId());
			preStmt.executeUpdate();
			bool = true;
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			releaseResources();
		}

		return bool;
	}

	// Deactivate message (by user or admin)
	@Override
	public boolean delete(PrikbordMessage message) {
		String sql = "UPDATE prikbord_bericht SET actief_gebruiker='N' WHERE id=?;";
		boolean bool = false;

		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(sql);
			preStmt.setInt(1, message.getId());
			preStmt.executeUpdate();
			bool = true;

		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			releaseResources();
		}

		return bool;
	}

	public Map<Long, PrikbordMessage> getAllMessages() {
		String sql = "SELECT id, gebruiker_id, title_output, body_output FROM prikbord_bericht WHERE actief_gebruiker='Y' AND actief_beheerder='Y' ORDER BY id DESC LIMIT 5;";
		Map<Long, PrikbordMessage> messages = new HashMap<>();

		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(sql);
			rs = preStmt.executeQuery();
			while (rs.next()) {
				PrikbordMessage message = new PrikbordMessage();
				message.setGebruikerId(rs.getInt(2));
				message.setTitleOutput(rs.getString(3));
				message.setBodyOutput(rs.getString(4));
				messages.put(rs.getLong(1), message);
			}
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			releaseResources();
		}

		return messages;
	}

	public Map<Long, PrikbordMessage> getPendingMessagesAdmin() {
		String sql = "SELECT id, gebruiker_id, title_output, body_output FROM prikbord_bericht WHERE actief_beheerder = 'N' AND actief_gebruiker = 'Y';";
		Map<Long, PrikbordMessage> messages = new HashMap<>();

		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(sql);
			rs = preStmt.executeQuery(sql);
			for (long l = 0; rs.next(); l++) {
				PrikbordMessage message = new PrikbordMessage();
				message.setId(rs.getInt(1));
				message.setGebruikerId(rs.getInt(2));
				message.setTitleOutput(rs.getString(3));
				message.setBodyOutput(rs.getString(4));
				messages.put(l, message);
			}
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			releaseResources();
		}

		return messages;
	}

}
