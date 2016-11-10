package nl.parkhaven.wasschema.model.prikbord;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nl.parkhaven.wasschema.model.CommonDao;
import nl.parkhaven.wasschema.model.CrudDao;

final class PrikbordDaoImpl extends CommonDao implements CrudDao<PrikbordMessage> {

	private static final Logger logger = LogManager.getLogger(PrikbordDaoImpl.class);

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
			int succes = preStmt.executeUpdate();
			if (succes == 1) {
				bool = true;
			} else {
				logger.error("More than 1 row was inserted!!!");
				throw new SQLException("More than 1 rw was inserted!");
			}
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
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
		}

		return messages;
	}

	@Override
	public PrikbordMessage read(PrikbordMessage message) {
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
			int succes = preStmt.executeUpdate();
			if (succes == 1) {
				bool = true;
			} else {
				logger.error("More than 1 row was inserted!!!");
				throw new SQLException("More than 1 rw was inserted!");
			}
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
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
			int succes = preStmt.executeUpdate();
			if (succes == 1) {
				bool = true;
			} else {
				logger.error("More than 1 row was inserted!!!");
				throw new SQLException("More than 1 rw was inserted!");
			}
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		}

		return bool;
	}

}
