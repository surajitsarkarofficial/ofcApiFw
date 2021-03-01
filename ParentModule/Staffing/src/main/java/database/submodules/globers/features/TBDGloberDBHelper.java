package database.submodules.globers.features;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.submodules.globers.GlobersDBHelper;

/**
 * @author shadab.waikar
 * */
public class TBDGloberDBHelper extends GlobersDBHelper {

	public TBDGloberDBHelper() {
	}

	/**
	 * This method will return active TBD glober
	 * 
	 * @return {@link String}
	 * @throws SQLException
	 */
	public String getActiveTBDGlober() throws SQLException {
		String res = null;
		String query = "SELECT g.id as globerid FROM globers g JOIN contracts_information c\r\n"
				+ "ON g.contract_information_fk = c.id\r\n"
				+ "JOIN contracts_data cd ON c.id = cd.contract_information_fk\r\n"
				+ "JOIN glober_statuses gs ON gs.glober_fk = g.id\r\n" + "WHERE c.last_date IS NULL\r\n"
				+ "AND cd.end_date IS NULL\r\n"
				+ "AND gs.is_Active = true AND gs.staffing_plantype_fk = 4 ORDER BY RANDOM() LIMIT 1";
		ResultSet rs = getResultSet(query);
		try {
			rs.next();
			res = rs.getString("globerid");		
		} finally {
			endConnection();
		}
		return res;
	} 
	
	/**
	 * This method will return active TBD glober data gor dynamic search
	 * @param globerId
	 * @return {@link String[]}
	 * @throws SQLException
	 */
	public String[] getTBDGloberDataForDynamicSearchById(String globerId) throws SQLException {
		String res[] = new String[2];
		String query = "SELECT position,seniority FROM glober_view WHERE globerid = "+globerId+" LIMIT 1";
		ResultSet rs = getResultSet(query);
		try {
			rs.next();
			res[0] = rs.getString("position");	
			res[1] = rs.getString("seniority");	
		} finally {
			endConnection();
		}
		return res;
	}
	
	/**
	 * This method will return active TBD glober
	 * 
	 * @return {@link String}
	 * @throws SQLException
	 */
	public Integer updateTbdGloberStatus(String globerId) throws SQLException {
		int rs;
		String query = "UPDATE glober_statuses SET is_active = false WHERE glober_fk = "+globerId;
		try {
			rs = executeUpdateQuery(query);
		} finally {
			endConnection();
		}
		return rs;
	}
}
