package database.submodules.staffRequest.features.globalstaffrequest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.submodules.staffRequest.StaffRequestDBHelper;

public class TimelineDBHelper extends StaffRequestDBHelper{

	public TimelineDBHelper() {
	}
	
	/**
	 * Get globerId to suggest
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String getGloberToSuggest() throws SQLException {
		String result = null;
		String query = "SELECT glober_view.globerid AS globerId FROM glober_view,staffing_plan WHERE \r\n" + 
				"glober_view.globerid = staffing_plan.user_fk AND \r\n" + 
				"glober_view.internalassignmenttype = 'BENCH'\r\n" + 
				"AND glober_view.benchstartdate < NOW()\r\n" + 
				"AND glober_view.assignedforsr = 'false' AND enddate IS NULL\r\n" + 
				"AND staffing_plan.plan_type <> 'High'\r\n" + 
				"AND glober_view.availability = 100\r\n" + 
				"AND lastdate IS NULL \r\n" + 
				"ORDER BY glober_view.benchstartdate DESC LIMIT 1";
		
		ResultSet rs = getResultSet(query);
		try {
			rs.next();
			result = rs.getString("globerId");
			updateStaffingPlanTypeFromGloberId(result);
		} finally {
			endConnection();
		}
		return result;
	}
	
	/**
	 * Update staffPlan planType from globerId
	 * @param globerId
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public Integer updateStaffingPlanTypeFromGloberId(String globerId) throws SQLException {
		int result = 0;
		ArrayList<String> globerActivePlans = new ArrayList<String>();
		String query1 = "SELECT plan_type FROM staffing_plan WHERE user_fk = "+globerId+" AND is_active = 'true'";
		
		String query2 = "UPDATE staffing_plan SET plan_type = 'Low' WHERE user_fk = "+globerId+" AND plan_type = 'High' AND is_active = 'true'";
		ResultSet rs = getResultSet(query1);
		try {
			while(rs.next()) {
				globerActivePlans.add(rs.getString("plan_type"));
			}
			for(String plan:globerActivePlans) {
				if(plan.equals("High"))
					result = executeUpdateQuery(query2);
			}
		} finally {
			endConnection();
		}
		return result;
	}
	/**
	 * Get random globerId to suggest
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String getRandomGloberToSuggest() throws SQLException {
		String result = null;
		String query = "SELECT glober_view.globerid AS globerId FROM glober_view,staffing_plan WHERE  \r\n" + 
				"glober_view.globerid = staffing_plan.user_fk AND  \r\n" + 
				"glober_view.internalassignmenttype = 'BENCH' \r\n" + 
				"AND glober_view.assignmentstartdate < NOW() \r\n" + 
				"AND glober_view.assignmentenddate IS NULL \r\n" +
				"AND glober_view.assignedforsr = 'false' AND enddate IS NULL \r\n" + 
				"AND staffing_plan.plan_type <> 'High'\r\n" + 
				"AND glober_view.availability = 100 \r\n" + 
				"AND lastdate IS NULL  \r\n" + 
				"ORDER BY RANDOM() LIMIT 1";
		ResultSet rs = getResultSet(query);
		try {
			rs.next();
			result = rs.getString("globerId");
		} finally {
			endConnection();
		}
		return result;
	}
}
