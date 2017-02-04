package nl.parkhaven.wasschema.component.bulletinboard;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import nl.parkhaven.wasschema.component.CommonDao;
import nl.parkhaven.wasschema.component.Crud;

final class BulletinBoardDaoImpl extends CommonDao implements Crud<Message> {

	@Override
	public boolean create(Message message) {
		String sql = "INSERT INTO prikbord_bericht (gebruiker_id, title_input, body_input, title_output, body_output) VALUES (?, ?, ?, ?, ?);";
		boolean bool = false;
		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(sql);
			preStmt.setInt(1, message.getUserId());
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
	public Message read(Message message) {
		// I return the entire list of messages from the database, not indiviually.
		new RuntimeException("Not implemented!");
		return null;
	}

	// activate message (by admin)
	@Override
	public boolean update(Message message) {
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

	// deactivate message (by user or admin)
	@Override
	public boolean delete(Message message) {
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

	public Map<Long, Message> getAllMessages() {
		String sql = "SELECT id, gebruiker_id, title_output, body_output FROM prikbord_bericht WHERE actief_gebruiker='Y' AND actief_beheerder='Y' ORDER BY id DESC LIMIT 5;";
		Map<Long, Message> messages = new HashMap<>();
		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(sql);
			rs = preStmt.executeQuery();
			for (long l = 0; rs.next(); l++) {
				Message message = new Message();
				message.setId(rs.getInt(1));
				message.setUserId(rs.getInt(2));
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

	public Map<Long, Message> getPendingMessagesAdmin() {
		String sql = "SELECT id, gebruiker_id, title_output, body_output FROM prikbord_bericht WHERE actief_beheerder = 'N' AND actief_gebruiker = 'Y';";
		Map<Long, Message> messages = new HashMap<>();
		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(sql);
			rs = preStmt.executeQuery(sql);
			for (long l = 0; rs.next(); l++) {
				Message message = new Message();
				message.setId(rs.getInt(1));
				message.setUserId(rs.getInt(2));
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

	// method only to be used for testing
	public void deleteCompletely(int messageId) {
		String deleteCompletelySQL = "DELETE FROM prikbord_bericht WHERE id = ?;";
		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(deleteCompletelySQL);
			preStmt.setInt(1, messageId);
			preStmt.executeUpdate();
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			releaseResources();
		}
	}

}
