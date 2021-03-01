package database.submodules.staffRequest.features.globalstaffrequest;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.BaseDBHelper;
import properties.GlowProperties;

/**
 * 
 * @author akshata.dongare
 *
 */

public class MatchingSummaryDBHelper extends BaseDBHelper {

	public MatchingSummaryDBHelper() {
		dbName = GlowProperties.alberthaOpenPositionDB;
		userName = GlowProperties.dbUser;
		password = GlowProperties.dbPassword;
	}
	
	/**
	 * Get SR
	 * @param srNumber
	 * @return String
	 * @throws SQLException
	 */
	public String getSrRecordForNAResponse(String srNumber) throws SQLException {
		String query = "SELECT ticket_id as sr FROM matching_summary WHERE ticket_id="+srNumber;
		try {
			ResultSet rs = getResultSet(query);
			rs.next();
			String sr= rs.getString("sr");
			return sr;
		}finally {
			endConnection();
		}	
		
	}
}
