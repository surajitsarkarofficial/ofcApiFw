package database.submodules.staffRequest.features.globalstaffrequest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import database.submodules.staffRequest.StaffRequestDBHelper;

public class Top3MatchingGloberDBHelper extends StaffRequestDBHelper{

	public Top3MatchingGloberDBHelper() {
	}
	
	/**
	 * Check and get Top matching glober active plan
	 * @param positionId
	 * @return {@link Map}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public boolean getTopMatchingGloberPlans(String positionId) throws SQLException {
		boolean plan = false;
		String query = "SELECT user_fk FROM staffing_plan WHERE position_fk = "+positionId+" \r\n" + 
				       "AND is_active = 'true'";
		ResultSet rs = getResultSet(query);
		try {
			while(rs.next()) {
			 plan = true;
			}	
		}
		catch(Exception e) {
			plan = false;
		}
		finally {

		}
		return plan;	
	}
	
	/**
	 * Get globerIds w.r.t Sr plan
	 * @param positionId
	 * @return {@link ArrayList}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public ArrayList<Integer> getGloberIds(String positionId) throws SQLException {
		String result = null;
		ArrayList<Integer> globerIds = new ArrayList<Integer>();
		String query = "SELECT user_fk FROM staffing_plan WHERE position_fk = "+positionId+" \r\n" + 
				       "AND is_active = 'true'";
		ResultSet rs = getResultSet(query);
		try {
			rs.next();
			result = rs.getString("user_fk");
			globerIds.add(Integer.parseInt(result));
		} finally {
			endConnection();
		}
		return globerIds;
	}
	
	/**
	 * Get active plans of glober
	 * @param globerId
	 * @return {@link Map}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public Integer getCountOfGloberActivePlans(String globerId) throws SQLException {
		int result;
		String query = "SELECT COUNT(plan_type) AS total_plans FROM staffing_plan WHERE user_fk = "+globerId+" AND is_active = 'true'";
		ResultSet rs = getResultSet(query);
		try {
			rs.next();
			result = rs.getInt("total_plans");
		} finally {
			endConnection();
		}
		return result;	
	}
}
