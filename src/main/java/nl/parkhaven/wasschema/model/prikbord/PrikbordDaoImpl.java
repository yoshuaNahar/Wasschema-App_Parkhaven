package nl.parkhaven.wasschema.model.prikbord;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nl.parkhaven.wasschema.model.CommonDao;
import nl.parkhaven.wasschema.model.CrudDao;

final class PrikbordDaoImpl extends CommonDao implements CrudDao<PrikbordMessage> {

	private static final Logger logger = LogManager.getLogger(PrikbordDaoImpl.class);

	@Override
	public boolean create(PrikbordMessage message) {
		String sql = "INSERT INTO prikbord_bericht (gebruiker_id, title, body) VALUES (?, ?, ?);";
		boolean bool = false;

		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(sql);
			preStmt.setInt(1, message.getGebruikerId());
			preStmt.setString(2, message.getTitleOutput());
			preStmt.setString(3, message.getBodyOutput());
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

	public List<PrikbordMessage> getAllMessages() {
		String sql = "SELECT title, body FROM prikbord_bericht WHERE actief='Y' LIMIT 5;"; // ORDER
																							// BY
																							// id
																							// DESC
		List<PrikbordMessage> messages = new ArrayList<>();

		try {
			conn = getConnection();
			preStmt = conn.prepareStatement(sql);
			rs = preStmt.executeQuery();
			while (rs.next()) {
				PrikbordMessage message = new PrikbordMessage();
				// message.setId(rs.getInt(1)); This should be added, but isnt
				// yet!
				message.setTitleOutput(rs.getString(1));
				message.setBodyOutput(rs.getString(2));
				messages.add(message);
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

	@Override
	public void update(PrikbordMessage e) {
	}

	@Override
	public void delete(PrikbordMessage e) {
	}

}
