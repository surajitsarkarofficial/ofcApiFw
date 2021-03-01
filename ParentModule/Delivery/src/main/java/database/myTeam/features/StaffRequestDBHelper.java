package database.myTeam.features;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.testng.SkipException;

import database.myTeam.MyTeamDBHelper;

/**
 * @author Poonam.Kadam
 *
 */

public class StaffRequestDBHelper extends MyTeamDBHelper {

	public StaffRequestDBHelper() {
	}

	/**
	 * This method will return id's and name's by Position Role id
	 * 
	 * @param postionRoleId
	 * @return Map<Integer, String>
	 * @throws SQLException
	 */
	public Map<Integer, String> getIdNamesByPositionRoleId(int postionRoleId) throws SQLException {
		Map<Integer, String> idNamesMap = new HashMap<Integer, String>();
		String query = "select id, name from position_seniorities where id in (select seniority_fk from\r\n"
				+ "position_roles_seniorities where activated = true and filtered = false and position_role_fk in (select id from position_roles \r\n"
				+ "where id =" + postionRoleId + "))";

		try {
			ResultSet rs = getResultSet(query);
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				idNamesMap.put(id, name);
			}
		} catch (SQLException e) {
			throw new SkipException("getIdNamesByPositionRoleId is not working" + e.getMessage());
		}
		finally {
			endConnection();
		}
		return idNamesMap;
	}
}
