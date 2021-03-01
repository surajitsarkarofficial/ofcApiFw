package database.submodules.staffRequest.features;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.StaffingDBHelper;

/**
 * 
 * @author akshata.dongare
 *
 */
public class SuggestGloberDBHelper extends StaffingDBHelper{
	public SuggestGloberDBHelper() {
	}

	/**
	 * This method will return global id of a glober who is in talent pool
	 * @param type
	 * @return
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public String getGlobalIdOfTPGlober(String type) throws SQLException{
		String globalId = null;
		ResultSet resultSet = null;
		String query = "SELECT globalid AS globalId FROM glober_view "
				+ "WHERE "	
				+ "((((assignmentstartdate BETWEEN CURRENT_DATE and (CURRENT_DATE+ INTEGER '30')) "
				+ "OR (assignmentstartdate < (CURRENT_DATE+ INTEGER '30') AND assignmentenddate IS NULL) "
				+ "OR (assignmentstartdate<(CURRENT_DATE+ INTEGER '30') AND assignmentenddate>(CURRENT_DATE+ INTEGER '30'))) "
				+ "AND type='"+type+"' "
				+ "AND availability BETWEEN 0 AND 100  "
				+ "AND internalassignmenttype='BENCH' "
				+ "AND lastdate IS NULL "
				+ "AND enddate IS NULL ))order by Random() limit 1";
		try {
			resultSet = getResultSet(query);
			resultSet.next();
			globalId = resultSet.getString("globalId");;
			return globalId;
		}finally {
			endConnection();
		}
	}
	
	/**
	 * This method will return global id for STG for which assignedforsr fiels is false
	 * @param type
	 * @return globalId
	 * @throws SQLException
	 */
	public String getGlobalIdForSrAssignedFalseSTG(String type) throws SQLException{
		String globalId = null;
		String query = " SELECT globalid AS globalId FROM glober_view WHERE type='"+type+"' AND assignedforsr='false' ORDER by RANDOM() LIMIT 1";
		try {
			ResultSet rs = getResultSet(query);
			rs.next();
			globalId = rs.getString("globalId");
			return globalId;
		}finally {
			endConnection();
		}
	}
	
	/**
	 * Get start date from position id
	 * @param positionId
	 * @return String
	 * @throws SQLException
	 */
	public String getStartDateFromPositionId(String positionId) throws SQLException {
		String startDate = null;
		String query = "SELECT start_date as startDate from positions where id = "+positionId;
		try {
			ResultSet rs = getResultSet(query);
			rs.next();
			startDate = rs.getString("startDate");
			return startDate;
		}finally {
			endConnection();
		}
	}
	
	/**
	 * This method will return global id of a glober except mentioned position who is in talent pool
	 * @param type
	 * @param position
	 * @return String
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public String getTPGlobalIdExceptGivenPosition(String type, String position) throws SQLException{
		String globalId = null;
		ResultSet resultSet = null;
		String query = "SELECT globalid AS globalId FROM glober_view "
				+ "WHERE "	
				+ "((((assignmentstartdate BETWEEN CURRENT_DATE and (CURRENT_DATE+ INTEGER '30')) "
				+ "OR (assignmentstartdate < (CURRENT_DATE+ INTEGER '30') AND assignmentenddate IS NULL) "
				+ "OR (assignmentstartdate<(CURRENT_DATE+ INTEGER '30') AND assignmentenddate>(CURRENT_DATE+ INTEGER '30'))) "
				+ "AND type='"+type+"' "
				+ "AND position !='"+position+"' "
				+ "AND availability BETWEEN 0 AND 100  "
				+ "AND internalassignmenttype='BENCH' "
				+ "AND lastdate IS NULL "
				+ "AND enddate IS NULL ))order by Random() limit 1";
		try {
			resultSet = getResultSet(query);
			resultSet.next();
			globalId = resultSet.getString("globalId");;
			return globalId;
		}finally {
			endConnection();
		}
	}
	
	/**
	 * This method will return global id of a glober except mentioned location who is in talent pool
	 * @param type
	 * @param locationId
	 * @return String
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public String getTPGlobalIdExceptGivenLocation(String type, String locationId) throws SQLException {
		String globalId = null;
		ResultSet resultSet = null;
		String query = "SELECT globalid AS globalId FROM glober_view "
				+ "WHERE "	
				+ "((((assignmentstartdate BETWEEN CURRENT_DATE and (CURRENT_DATE+ INTEGER '30')) "
				+ "OR (assignmentstartdate < (CURRENT_DATE+ INTEGER '30') AND assignmentenddate IS NULL) "
				+ "OR (assignmentstartdate<(CURRENT_DATE+ INTEGER '30') AND assignmentenddate>(CURRENT_DATE+ INTEGER '30'))) "
				+ "AND type='"+type+"' "
				+ "AND siteid !='"+locationId+"' "
				+ "AND availability BETWEEN 0 AND 100  "
				+ "AND internalassignmenttype='BENCH' "
				+ "AND lastdate IS NULL "
				+ "AND enddate IS NULL ))order by Random() limit 1";
		try {
			resultSet = getResultSet(query);
			resultSet.next();
			globalId = resultSet.getString("globalId");;
			return globalId;
		}finally {
			endConnection();
		}
	}
	
}
