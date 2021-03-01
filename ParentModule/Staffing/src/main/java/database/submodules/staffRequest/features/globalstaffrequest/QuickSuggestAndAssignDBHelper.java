package database.submodules.staffRequest.features.globalstaffrequest;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.submodules.staffRequest.StaffRequestDBHelper;

public class QuickSuggestAndAssignDBHelper extends StaffRequestDBHelper{

	public QuickSuggestAndAssignDBHelper() {
	}
	
	/**
	 * Get Glober Id Having No Plan
	 * @param dt 
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String getGloberNothavingAnyPlanOneWay(String dt) throws SQLException {
		String result = null;
		String query = "SELECT globerid FROM glober_view "
				+ "WHERE globerid NOT IN (SELECT dIStinct user_fk FROM staffing_plan WHERE user_fk IS NOT null AND is_active !='f') AND "
				+ "internalassignmenttype !='BENCH'  AND assignmentenddate > '" + dt + "' AND assignmentstartdate < '"
				+ dt + "' AND" + " enddate IS NULL AND lastdate IS NULL ORDER BY RANDOM() LIMIT 1";
		ResultSet rs = getResultSet(query);
		try {
			rs.next();
			result = rs.getString("globerid");
		} finally {
			endConnection();
		}
		return result;
	}
	
	/**
	 * Get Glober Id Having No Plan Using SrNumber
	 * @param dt 
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String getGloberNotHavingPlanSecondWay(String dt) throws SQLException {
		String result = null;
		String query = "SELECT globerid FROM glober_view "
				+ "WHERE globerid NOT IN (SELECT dIStinct user_fk FROM staffing_plan WHERE user_fk IS NOT NULL AND is_active !='f') AND "
				+ "internalassignmenttype ='BENCH'  AND assignmentstartdate < '" + dt + "' AND assignmentenddate > '"
				+ dt + "' AND enddate IS NULL " + "AND lastdate IS NULL ORDER BY RANDOM() LIMIT 1";	
		ResultSet rs = getResultSet(query);
		try {
			rs.next();
			result = rs.getString("globerid");
		} finally {
			endConnection();
		}
		return result;
	}
	
	/**
	 * Update StaffPlan Status to False
	 * @param StaffPlanId
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public void updateCreatedPlanStatus(String StaffPlanId) throws SQLException {
		String query = "UPDATE staffing_plan SET is_active = 'f' WHERE id =" + StaffPlanId;
		executeUpdateQuery(query);		
	}
	
	/**
	 * Get Glober Id Having No Plan Using given date
	 * @param dt 
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String getTalentPoolGloberOneWay(String dt) throws SQLException {
		String result = null;
		String query = "SELECT globerid FROM glober_view "
				+ "WHERE globerid NOT IN (SELECT dIStinct user_fk FROM staffing_plan WHERE user_fk IS NOT NULL AND is_active !='f') AND "
				+ "internalassignmenttype ='BENCH'  AND assignmentstartdate < '" + dt + "' AND assignmentenddate IS NULL"
				+ " AND enddate IS NULL " + "AND lastdate IS NULL ORDER BY RANDOM() LIMIT 1";		
		ResultSet rs = getResultSet(query);
		try {
			rs.next();
			result = rs.getString("globerid");
		} finally {
			endConnection();
		}
		return result;
	}
	
	/**
	 * Get Glober Id having high plan someWHERE
	 * @param dt 
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String getTalentPoolGloberHavingHighPlan(String dt) throws SQLException {
		String result = null;
		String query = "SELECT user_fk FROM staffing_plan WHERE plan_type = 'High' AND user_fk IS NOT NULL AND\r\n" + 
				"is_active = 't' ORDER BY RANDOM() LIMIT 1";	
		ResultSet rs = getResultSet(query);
		try {
			rs.next();
			result = rs.getString("user_fk");
		} finally {
			endConnection();
		}
		return result;
	}
            
	/**
	 * Get globerId and STGs canidateName
	 * @param GloberType 
	 * @return {@link String[]}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String[] getTalentPoolStgs(String globerType) throws SQLException {
		String result[] = new String[2];
		String query = "SELECT globerid,candidatefullname FROM glober_view WHERE type='" + globerType
				+ "' AND status = 'New' ORDER BY RANDOM() LIMIT 1";	
		ResultSet rs = getResultSet(query);
		try {
			rs.next();
			result[0] = rs.getString("globerid");
			result[1] = rs.getString("candidatefullname");
		} finally {
			endConnection();
		}
		return result;
	}
}
