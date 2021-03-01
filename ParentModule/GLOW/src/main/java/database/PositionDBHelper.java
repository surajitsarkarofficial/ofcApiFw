package database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface PositionDBHelper {
	
	/**
	 * This method will get seniority name by Id
	 * 
	 * @param seniorityId 
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public default String getSeniorityNameById(String seniorityId) throws SQLException {
		String query = "SELECT name FROM position_seniorities WHERE id =" + seniorityId;
		String result = null;
		ResultSet rs = new BaseDBHelper().getResultSet(query);
		try {
			rs.next();
			result = rs.getString("name");
		} finally {
			new BaseDBHelper().endConnection();
		}
		return result;
	}
	
	/**
	 * This method will get seniority Id by name
	 * 
	 * @param seniorityName
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public default String getSeniorityIdByName(String seniorityName) throws SQLException {
		String query = "SELECT id FROM position_seniorities WHERE name ='" + seniorityName+"'";
		String result = null;
		ResultSet rs = new BaseDBHelper().getResultSet(query);
		try {
			rs.next();
			result = rs.getString("id");
		} finally {
			new BaseDBHelper().endConnection();
		}
		return result;
	}
	
	/**
	 * This method will return SR start date from srNumber
	 * 
	 * @param SrNumber
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public default String getStartDateFromPosition(String srNumber) throws SQLException {
		String query = "SELECT start_date :: Date FROM positions WHERE issue_tracker_number =" + srNumber;
		String result = null;
		ResultSet rs = new BaseDBHelper().getResultSet(query);
		try {
			rs.next();
			result = rs.getString("start_date");
		} finally {
			new BaseDBHelper().endConnection();
		}
		return result;
	}
	
	/**
	 * This method will return SR start date from srNumber
	 * 
	 * @param SrNumber
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public default String getEndDateFromPosition(String srNumber) throws SQLException {
		String query = "SELECT assignment_end_date :: Date FROM positions WHERE issue_tracker_number =" + srNumber;
		String result = null;
		ResultSet rs = new BaseDBHelper().getResultSet(query);
		try {
			rs.next();
			result = rs.getString("assignment_end_date");
		} finally {
			new BaseDBHelper().endConnection();
		}
		return result;
	}

	/**
	 * This method will return SR load FROM srNumber
	 * 
	 * @param srNumber
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public default String getLoadFromPosition(String srNumber) throws SQLException {
		String query = "SELECT load FROM positions WHERE issue_tracker_number =" + srNumber;
		String result = null;
		ResultSet rs = new BaseDBHelper().getResultSet(query);
		try {
			rs.next();
			result = rs.getString("load");
		} finally {
			new BaseDBHelper().endConnection();
		}
		return result;
	}
	
	/**
	 * This method will return SR locations FROM srNumber
	 * 
	 * @param srNumber
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public default String getLocationFromPosition(String srNumber) throws SQLException {
		String query = "SELECT location_fk FROM positions WHERE issue_tracker_number =" + srNumber;
		String result = null;
		ResultSet rs = new BaseDBHelper().getResultSet(query);
		try {
			rs.next();
			result = rs.getString("location_fk");
		} finally {
			new BaseDBHelper().endConnection();
		}
		return result;
	}
	
	/**
	 * This method will return SR location Id not equal to Anywhere location
	 * 
	 * @param srNumber
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public default String getLocationIdExceptAnywhereLocationFromPosition(String srNumber) throws SQLException {
		String query = "SELECT location_fk FROM positions WHERE issue_tracker_number =" + srNumber
				+ " AND location_fk !=(select id from location_office where location = 'Anywhere')";
		String result = null;
		ResultSet rs = new BaseDBHelper().getResultSet(query);
		try {
			rs.next();
			result = rs.getString("location_fk");
		} finally {
			new BaseDBHelper().endConnection();
		}
		return result;
	}
	
	/**
	 * This method will return position id
	 * 
	 * @param SrNumber
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public default String getPositionId(String SrNumber) throws SQLException {
		String query = "SELECT id FROM positions WHERE issue_tracker_number =" + SrNumber;
		String result = null;
		ResultSet rs = new BaseDBHelper().getResultSet(query);
		try {
			rs.next();
			result = rs.getString("id");
		} finally {
			new BaseDBHelper().endConnection();
		}
		return result;
	}
	
	/**
	 * This method will return position role Id
	 * 
	 * @param SrNumber
	 * @return {@link String}
	 * @throws SQLException
	 */
	public default String getPositionRoleIdByPosition(String srNumber) throws SQLException {
		String query = "SELECT position_role_fk FROM positions WHERE issue_tracker_number =" + srNumber;
		String result = null;
		ResultSet rs = new BaseDBHelper().getResultSet(query);
		try {
			rs.next();
			result = rs.getString("position_role_fk");
		} finally {
			new BaseDBHelper().endConnection();
		}
		return result;
	}
	
	/**
	 * Get position role Id
	 * 
	 * @param positionRoleName
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public default String getPositionRoleIdAccordingToPositionRolName(String positionRoleName) throws SQLException {
		String result = "";
		String query = "SELECT id FROM position_roles WHERE name = '" + positionRoleName + "'";
		ResultSet resultSet = new BaseDBHelper().getResultSet(query);
		try {
			resultSet.next();
			result = resultSet.getString("id");
		} finally {
			new BaseDBHelper().endConnection();
		}
		return result;
	}

	/**
	 * Get position role name
	 * 
	 * @param positionName
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public default String getPositionRolNameExceptGivenPositionRol(String positionName) throws SQLException {
		String result = "";
		String query = "SELECT name FROM position_roles WHERE name != '" + positionName + "' AND "
				+ "name IN ('Visual Designer','Project Manager','Senior Web UI Developer','Net Engineer','Java Developer','Software Designer',"
				+ "'Phyton Developer','IOS Mobile Developer','NodeJS Developer','Subject Matter Expert','ANDroid Developer','Game Developer',"
				+ "'Salesforce Developer','Python Developer','Recruiting Lead','HTML Designer','SQL Developer','Drupal Developer','DevOps Engineer',"
				+ "'Sharepoint Developer','QC Analyst','Data Scientist','Web UI Developer','.Net Developer','PHP Developer','Test Automation Engineer','User Experience Designer')\r\n"
				+ "ORDER BY RANDOM() LIMIT 1";
		ResultSet resultSet = new BaseDBHelper().getResultSet(query);
		try {
			resultSet.next();
			result = resultSet.getString("name");
		} finally {
			new BaseDBHelper().endConnection();
		}
		return result;
	}
	/**
	 * This method will return seniority Id by srNumber
	 * 
	 * @param SrNumber
	 * @return {@link String}
	 * @throws SQLException
	 */
	public default String getSeniorityIdByPosition(String srNumber) throws SQLException {
		String query = "SELECT position_seniority_fk FROM positions WHERE issue_tracker_number =" + srNumber;
		String result = null;
		ResultSet rs = new BaseDBHelper().getResultSet(query);
		try {
			rs.next();
			result = rs.getString("position_seniority_fk");
		} finally {
			new BaseDBHelper().endConnection();
		}
		return result;
	}
	
	/**
	 * Get position role name from id
	 * 
	 * @param positionRoleName
	 * @return {@link String}
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public default String getPositionRoleNameAccordingToPositionRolId(String positionRoleId) throws SQLException {
		String positionName = null;
		String query = "SELECT name as positionName FROM position_roles where id = '" + positionRoleId + "'";
		ResultSet resultSet = new BaseDBHelper().getResultSet(query);
		try {
			resultSet.next();
			positionName = resultSet.getString("positionName");
		} finally {
			new BaseDBHelper().endConnection();
		}
		return positionName;
	}
	
	/**
	 * This method will get Position id of ongoing project
	 * @param startDate
	 * @return positionId
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public default String getInProgressPositionId(String startDate) throws SQLException {
		ResultSet resultSet = null;
		String positionId = null;
		String query = "SELECT p.id AS positionId FROM positions p JOIN projects "
				+ "pr on p.project_fk=pr.id WHERE p.state='In Progress' AND "
				+ "pr.project_state='ON_GOING' AND p.start_date > '"+startDate+"'  GROUP BY p.id ORDER BY RANDOM() limit 1";
		try {
			resultSet = new BaseDBHelper().getResultSet(query);
			resultSet.next();
			positionId = resultSet.getString("positionId");
			return positionId;
		} finally {
			new BaseDBHelper().endConnection();
		}
	}
	
	/**
	 * This method will get Position id of ongoing project for given position role
	 * @param positionRoleId
	 * @param startDate
	 * @return String
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public default String getInProgressPositionIdForGivenPositionRole(String positionRoleId, String startDate) throws SQLException {
		ResultSet resultSet = null;
		String positionId = null;
		String query = "SELECT p.id AS positionId FROM positions p JOIN projects "
				+ "pr on p.project_fk=pr.id WHERE p.state='In Progress' AND position_role_fk = "+positionRoleId +
				" AND pr.project_state='ON_GOING' AND p.start_date > '"+startDate+"'  GROUP BY p.id ORDER BY RANDOM() limit 1";
		try {
			resultSet = new BaseDBHelper().getResultSet(query);
			resultSet.next();
			positionId = resultSet.getString("positionId");
			return positionId;
		} finally {
			new BaseDBHelper().endConnection();
		}
	}
	
	/**
	 * This method will get location id from position id
	 * @param positionId
	 * @return String
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public default String getLocationIdFromPositionId(String positionId) throws SQLException {
		ResultSet resultSet = null;
		String locationId = null;
		String query = "SELECT location_fk as locationId FROM positions WHERE id = "+positionId;
		try {
			resultSet = new BaseDBHelper().getResultSet(query);
			resultSet.next();
			locationId = resultSet.getString("locationId");
			return locationId;
		} finally {
			new BaseDBHelper().endConnection();
		}
	}
	
	/**
	 * This method will get staff plan id from position id
	 * @param positionId
	 * @return String
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public default String getStaffPlanId(String positionId) throws SQLException {
		String staffPlanId = null;
		String query = "SELECT id AS staffPlanId FROM staffing_plan WHERE position_fk = "+positionId;
		try {
			ResultSet resultSet = new BaseDBHelper().getResultSet(query);
			resultSet.next();
			staffPlanId = resultSet.getString("staffPlanId");
			return staffPlanId;
		} finally {
			new BaseDBHelper().endConnection();
		}
	}
}
