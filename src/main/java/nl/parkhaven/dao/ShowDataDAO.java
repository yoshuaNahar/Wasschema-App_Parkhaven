package nl.parkhaven.dao;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import nl.parkhaven.util.DBConnectionUtil;

public class ShowDataDAO {

	private final String SHOW_HUISNUMMERS_MACHINE1_SQL = "SELECT huisnummer FROM calender WHERE machine=1;";
	private final String SHOW_HUISNUMMERS_MACHINE2_SQL = "SELECT huisnummer FROM calender WHERE machine=2;";

	private int huisnummersMachine1[] = new int[115];
	private int huisnummersMachine2[] = new int[115];

	public ShowDataDAO() {
		databaseHuisnummersToArray();
	}

	public void databaseHuisnummersToArray() {
		Connection con = null;
		Statement showDataStmt1 = null;
		ResultSet rs1 = null;
		Statement showDataStmt2 = null;
		ResultSet rs2 = null;

		try {
			con = DBConnectionUtil.getConnection();

			showDataStmt1 = con.createStatement();
			rs1 = showDataStmt1.executeQuery(SHOW_HUISNUMMERS_MACHINE1_SQL);
			for (int i = 0; rs1.next(); i++)
				getHuisnummersMachine1()[i] = rs1.getInt("huisnummer");

			showDataStmt2 = con.createStatement();
			rs2 = showDataStmt2.executeQuery(SHOW_HUISNUMMERS_MACHINE2_SQL);
			for (int i = 0; rs2.next(); i++)
				getHuisnummersMachine2()[i] = rs2.getInt("huisnummer");
		}

		catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		}

		finally {
			try {
				if (con != null)
					con.close();
				if (showDataStmt1 != null)
					showDataStmt1.close();
				if (rs1 != null)
					rs1.close();
				if (showDataStmt2 != null)
					showDataStmt2.close();
				if (rs2 != null)
					rs2.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public int[] getHuisnummersMachine1() {
		return huisnummersMachine1;
	}

	public int[] getHuisnummersMachine2() {
		return huisnummersMachine2;
	}
}
