package database.submodules.staffRequest.features.globalstaffrequest;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.submodules.staffRequest.StaffRequestDBHelper;

public class CancelSrDBHelper extends StaffRequestDBHelper {

	public CancelSrDBHelper() {
	}
	
	/**
	 * Get position state from position
	 * @param srNumber 
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String getPositionStateFromPosition(String srNumber) throws SQLException {
		String result = null;
		String query = "SELECT state FROM positions WHERE issue_tracker_number = "+srNumber;
		ResultSet rs = getResultSet(query);
		try {
			rs.next();
			result = rs.getString("state");
		} finally {
			endConnection();
		}
		return result;
	}
	
	/**
	 * Update position state 
	 * @param positionState 
	 * @param srNumber 
	 * @return {@link Integer}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public Integer updatePositionState(String positionState,String srNumber) throws SQLException {
		int rs;
		String query = "UPDATE positions SET state = '"+positionState+"' WHERE issue_tracker_number ="+srNumber;
		try {
		    rs = executeUpdateQuery(query);
		} finally {
			endConnection();
		}
		return rs;
	}
	
	/**
	 * Get cancel Sr reason from Id
	 * @param reasonId 
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String getPositionCancelReasonById(int reasonId) throws SQLException {
		String result = null;
		String query = "SELECT name FROM cancel_reasons WHERE id  ="+reasonId;
		ResultSet rs = getResultSet(query);
		try {
			rs.next();
			result = rs.getString("name");
		} finally {
			endConnection();
		}
		return result;
	}
}
