package database.myTeam.features;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.testng.SkipException;

import database.myTeam.MyTeamDBHelper;

/**
 * @author Poonam.Kadam
 *
 */

public class SuggestAndAssignDBHelper extends MyTeamDBHelper {

	public SuggestAndAssignDBHelper() {
	}

	/**
	 * This method will return global id by glober id
	 * 
	 * @param globerId
	 * @return int
	 * @throws SQLException
	 */
	public int getGlobalIdByGloberId(int globerId) throws SQLException {
		int globalId = 0;
		String query = "select info_type_32 from globers where id =" + globerId;
		try {
			ResultSet rs = getResultSet(query);
			while (rs.next()) {
				globalId = rs.getInt("info_type_32");
			}
		} catch (SQLException e) {
			throw new SkipException("getGlobalIdByGloberId is not working" + e.getMessage());
		} finally {
			endConnection();
		}
		return globalId;
	}
}
