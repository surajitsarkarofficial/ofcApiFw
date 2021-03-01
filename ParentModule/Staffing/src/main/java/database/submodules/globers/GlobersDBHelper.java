package database.submodules.globers;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.StaffingDBHelper;

public class GlobersDBHelper extends StaffingDBHelper {

	public GlobersDBHelper() {
	}

	/**
	 * This method will return glober type (Status)
	 * 
	 * @param globerId
	 * @return
	 * @throws SQLException
	 */
	public String getGloberType(String globerId) throws SQLException {
		try {
			String query = "Select status from globers  where  id ='" + globerId + "'";
			ResultSet rs = getResultSet(query);
			rs.next();
			return rs.getString("status");
		} finally {
			endConnection();
		}
	}

	/**
	 * This method will return Glober Id
	 * 
	 * @return {@link String}
	 * @throws SQLException
	 */
	public String getGloberIdFromGlobalId(String globalId) throws SQLException {
		try {
			String id = null;
			String query = "SELECT globerid AS globerId FROM glober_view WHERE globerid IS NOT NULL AND globalid='"
					+ globalId + "'";
			ResultSet rs = getResultSet(query);
			while (rs.next()) {
				id = rs.getString("globerId");
			}
			return id;
		} finally {
			endConnection();
		}
	}

	/**
	 * This method will return TDC Id
	 * 
	 * @return
	 * @throws SQLException
	 */
	public String[] getStgTdcId() throws SQLException {
		try {
			String[] arr = new String[2];
			String query = "select global_id,tdc_fk from globers_tdc  "
					+ "Where global_id in (select global_id from globers_tdc group by global_id having count(*) >1) "
					+ "AND end_date IS null ";
			ResultSet rs = getResultSet(query);
			rs.next();
			arr[0] = rs.getString("global_id");
			arr[1] = rs.getString("tdc_fk");
			return arr;
		} finally {
			endConnection();
		}
	}

	/**
	 * This method will return Global Id
	 */
	public String getGlobalId(String type) throws SQLException {
		try {
			String query1 = "Select globalid from glober_view WHERE type='" + type
					+ "' AND globalid NOT IN  (Select globalid from glober_view where internalassignmenttype = 'OUT_OF_COMPANY') ";
			ResultSet rs1 = getResultSet(query1);
			rs1.next();
			return rs1.getString("globalid");
		} finally {
			endConnection();
		}
	}
}
