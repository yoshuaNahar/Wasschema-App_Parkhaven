package nl.parkhaven.model;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

import nl.parkhaven.model.abstraction.AppointmentDao;
import nl.parkhaven.model.abstraction.CommonDao;
import nl.parkhaven.model.entity.Appointment;
import nl.parkhaven.model.util.Database;

public class AppointmentDaoImpl extends CommonDao<Appointment> implements AppointmentDao {

//	private static final Logger logger = LogManager.getLogger(AppointmentDaoImpl.class);

	@Override
	public int[] getAllData(String wasmachine_id) {
		String showHuisnummersWasmachineSQL = "SELECT huisnummer FROM wasschema WHERE wasmachine_id = ?;";
		int huisnummers[] = new int[70];

		try {
			conn = Database.getConnection();
			preStmt = conn.prepareStatement(showHuisnummersWasmachineSQL);
			preStmt.setString(1, wasmachine_id);
			rs = preStmt.executeQuery();
			for (int i = 0; rs.next(); i++) {
				huisnummers[i] = rs.getInt("huisnummer");
			}
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			releaseResources();
		}

		return huisnummers;
	}

	@Override
	public void create(Appointment appointment) {
		String addAppointmentSQL = "UPDATE wasschema SET huisnummer = ? WHERE tijd_id = ? AND wasmachine_id = ?";

		try {
			preStmt = conn.prepareStatement(addAppointmentSQL);
			preStmt.setInt(1, appointment.getHuisnummer());
			preStmt.setInt(2, appointment.calculateRowNumber());
			preStmt.setString(3, appointment.getMachinenummer());
			int rowsUpdated = preStmt.executeUpdate();
			if (rowsUpdated != 1) {
				throw new SQLException("More than 1 row was updated!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			releaseResources();
		}
	}

	@Override
	public Appointment read(Appointment appointment) {
		String checkTimeAvailableSQL = "SELECT huisnummer FROM wasschema WHERE tijd_id = ? AND wasmachine_id = ?;";
		Appointment appointmentPlaced = new Appointment();

		try {
			conn = Database.getConnection();
			preStmt = conn.prepareStatement(checkTimeAvailableSQL);
			preStmt.setInt(1, appointment.calculateRowNumber());
			preStmt.setString(2, appointment.getMachinenummer());
			rs = preStmt.executeQuery();
			rs.next();
			appointmentPlaced.setHuisnummer(Integer.toString(rs.getInt(0)));
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			releaseResources();
		}

		return appointment;
	}

	@Override
	public void update(Appointment e) {
	}

	@Override
	public void delete(Appointment e) {
	}
}
