package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.submodules.interview.TimezoneDTO;

public class StaffingDBHelper extends GlowDBHelper {

	public StaffingDBHelper() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Get list of location names
	 * 
	 * @param totalLocationNames
	 * @return {@link List}
	 * @throws SQLException
	 * @author shadab.waikar
	 */

	public List<String> getLocationNames(int totalLocationNames) throws SQLException {
		try {
			String query = "SELECT * FROM sites WHERE site_details_fk!=0 OR site_details_fk IS null ORDER BY random() LIMIT "
					+ totalLocationNames;
			ResultSet resultSet = getResultSet(query);
			List<String> locations = new ArrayList<String>();
			while (resultSet.next()) {
				locations.add(resultSet.getString("name"));
			}
			return locations;
		} finally {
			endConnection();
		}
	}
	
	/**
	 * Get location name which has not site details
	 * 
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String getInvalidLocationName() throws SQLException {
		try {
			String query = "SELECT name FROM sites WHERE name NOT LIKE '%&%' AND site_details_fk=0 ORDER BY random() LIMIT 1";
			ResultSet resultSet = getResultSet(query);
			resultSet.next();
			return resultSet.getString("name");
		} finally {
			endConnection();
		}
	}

	/**
	 * Get seniority id for given positionId
	 * 
	 * @param positionId
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String getSeniorityId(String positionId, boolean... random) throws SQLException {
		String seniorityId = "Seniority id is not available for position id = " + positionId;
		try {
			String query = "SELECT pr.name AS position_name, ps.name AS seniority, ps.id AS seniority_id, ps.value AS senirity_value FROM position_roles pr, position_roles_seniorities prs, position_seniorities ps "
					+ "WHERE pr.id=prs.position_role_fk AND prs.seniority_fk=ps.id AND pr.id=" + positionId
					+ " ORDER BY random() LIMIT 1";
			ResultSet resultSet = getResultSet(query);
			if (resultSet.next()) {
				seniorityId = resultSet.getString("seniority_id");
			}
			return seniorityId;
		} finally {
			endConnection();
		}
	}

	/**
	 * Get list of Position Ids
	 * Enter the numeric value for specific records or String 'ALL' for all records
	 * @param totalPostionIds
	 * @return {@link List}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public List<String> getPositionIds(Object totalPostionIds) throws SQLException {
		try {
			String query = "SELECT * FROM position_roles WHERE id IN (SELECT pr.id FROM position_roles pr, position_roles_seniorities prs, position_seniorities ps WHERE pr.id = prs.position_role_fk AND pr.filtered = false AND prs.seniority_fk = ps.id GROUP BY pr.id) ORDER BY random() LIMIT " + totalPostionIds;
			ResultSet resultSet = getResultSet(query);
			List<String> positionIds = new ArrayList<String>();
			while (resultSet.next()) {
				positionIds.add(resultSet.getString("id"));
			}
			return positionIds;
		} finally {
			endConnection();
		}
	}

	/**
	 * This method will get Sr number from position ID
	 * 
	 * @param positionId
	 * @return sRNumber
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String getSRNumberFromPositionId(String positionId) throws SQLException {
		ResultSet resultSet = null;
		String query = "SELECT issue_tracker_number as sRNumber FROM positions where id=" + positionId;
		try {
			resultSet = getResultSet(query);
			resultSet.next();
			return resultSet.getString("sRNumber");
		} finally {
			endConnection();
		}
	}

	/**
	 * This method will get Position id of ongoing project
	 * 
	 * @return positionId
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String getPositionId() throws SQLException {
		ResultSet resultSet = null;
		String positionId = null;
		String query = "SELECT p.id AS positionId FROM positions p JOIN projects "
				+ "pr on p.project_fk=pr.id WHERE p.state='In Progress' AND "
				+ "pr.project_state='ON_GOING' GROUP BY p.id ORDER BY RANDOM() limit 1";
		try {
			resultSet = getResultSet(query);
			resultSet.next();
			positionId = resultSet.getString("positionId");
			return positionId;
		} finally {
			endConnection();
		}
	}

	/**
	 * Get random skill level values
	 * 
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String getSkillLevel() throws SQLException {
		try {
			String query = "SELECT * FROM scale_values WHERE scale_fk = (SELECT id FROM scales WHERE name = 'Skills Scale') ORDER BY random() LIMIT 1";
			ResultSet rs = getResultSet(query);
			rs.next();
			return rs.getString("id");
		} finally {
			endConnection();
		}
	}

	/**
	 * Get random interview type value
	 * 
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String getInterviewType() throws SQLException {
		try {
			String query = "SELECT * FROM scale_values WHERE scale_fk = (SELECT id FROM scales WHERE name LIKE '%Interview Type%') ORDER BY random() LIMIT 1";
			ResultSet rs = getResultSet(query);
			rs.next();
			return rs.getString("id");
		} finally {
			endConnection();
		}
	}

	/**
	 * This method will return the Seniority list based on positionId, seniorityId
	 * 
	 * @param positionId
	 * @param seniorityId
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String getSeniorityForSpecifiedPostion(String positionId, String seniorityId) throws SQLException {
		String values = "(";
		String query = "SELECT pr.name AS position_name, ps.name AS seniority, ps.id AS seniority_id, ps.value AS senirity_value FROM position_roles pr, position_roles_seniorities prs, position_seniorities ps "
				+ "WHERE pr.id=prs.position_role_fk AND prs.seniority_fk=ps.id AND pr.id=" + positionId
				+ "ORDER BY ps.value";
		ResultSet resultSet = getResultSet(query);
		boolean flag = false;
		try {
			while (resultSet.next()) {
				String seniorityIdFromDb = resultSet.getString("seniority_id");

				if (flag) {
					values = values + resultSet.getString("senirity_value") + ",";
				}
				if (seniorityIdFromDb.equals(seniorityId)) {
					flag = true;
				}
			}
			return values.substring(0, values.length() - 1) + ")";

		} finally {
			endConnection();
		}
	}

	/**
	 * Get Glober Id having on bench
	 * 
	 * @param dt
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String getGloberIdHavingOnBench(String dt) throws SQLException {
		String result = null;
		String query = "SELECT globerid FROM glober_view WHERE assignmentstartdate < '" + dt + "' "
				+ "AND status = 'New' AND type = 'Glober' " + "AND  internalassignmenttype='BENCH' AND "
				+ "assignmentenddate IS NULL " + "AND lastdate IS NULL "
				+ "AND enddate IS NULL AND availability BETWEEN 0 AND 100 ORDER BY RANDOM() LIMIT 1";
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
	 * Get Glober Id having On ongoing assignment
	 * 
	 * @param dt
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String getGloberIdHavingOnGoingAssignment(String dt) throws SQLException {
		String result = null;
		String query = "SELECT globerid FROM glober_view WHERE " + "assignmentstartdate < '" + dt + "' "
				+ "AND assignmentenddate >  '" + dt + "' " + "AND status = 'New' AND type = 'Glober' "
				+ "AND lastdate IS NULL " + "AND enddate IS NULL " + "ORDER BY RANDOM() LIMIT 1";
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
	 * Get assignment start date AND assignment end date
	 * 
	 * @param globalId
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String[] getSrStartDtEndDtUsingGlobalId(String globalId) throws SQLException {
		String result[] = new String[2];
		String query = "SELECT assignmentstartdate,assignmentenddate FROM glober_view WHERE globalid = '" + globalId
				+ "' " + "ORDER BY assignmentenddate DESC LIMIT 1 OFFSET 1";
		ResultSet rs = getResultSet(query);
		try {
			rs.next();
			result[0] = rs.getString("assignmentstartdate");
			result[1] = rs.getString("assignmentenddate");
		} finally {
			endConnection();
		}
		return result;
	}

	/**
	 * Get no. of plans per Sr by SrNumber
	 * 
	 * @param SrNumber
	 * @return {@link Integer}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public Integer getNoOfPlansPerSr(String SrNumber) throws SQLException {
		int result = 0;
		String query = "SELECT COUNT(id) as plans FROM staffing_plan WHERE position_fk = (SELECT id FROM positions WHERE issue_tracker_number = "
				+ SrNumber + " LIMIT 1) " + "AND is_active = 't'";
		ResultSet rs = getResultSet(query);
		try {
			rs.next();
			result = rs.getInt("plans");
		} finally {
			endConnection();
		}
		return result;
	}

	/**
	 * Get handlerName from handlerId
	 * 
	 * @param handlerId
	 * @return {@link String}
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public String getHandlerName(String handlerId) throws SQLException {
		try {
			String query = " SELECT DISTINCT (CASE WHEN handlerfirstname IS NULL THEN 'No Handler' ELSE concat(handlerfirstname,' ',handlerlastname) END) AS handlerName FROM glober_view WHERE handlerid='"
					+ handlerId + "'";
			String result = "";
			ResultSet rs = getResultSet(query);
			while (rs.next()) {
				result = rs.getString("handlerName");
			}
			return result;
		} finally {
			endConnection();
		}
	}

	/**
	 * This method is used to get ranodm request extension reason
	 * 
	 * @return String
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public String getRandomRequestExtensionReason() throws SQLException {
		String reason = null;
		String query = "SELECT extension_reason as reason FROM staffplan_extension ORDER by RANDOM() limit 1";
		try {
			ResultSet rs = getResultSet(query);
			rs.next();
			reason = rs.getString("reason");
		} finally {
			endConnection();
		}
		return reason;
	}

	/**
	 * Get random Interview Id who's type is Soon to be Glober Candidate
	 * 
	 * @param candidateID
	 * @return
	 * @throws SQLException
	 */
	public String getCandidateInterviewId(String candidateId) throws SQLException {
		try {
			String id = null;
			String query = " SELECT id FROM technical_interviews WHERE candidate_id ='" + candidateId
					+ "' ORDER BY RANDOM() LIMIT 1";
			ResultSet rs = getResultSet(query);

			while (rs.next()) {
				id = rs.getString("id");
			}
			return id;
		} finally {
			endConnection();
		}
	}

	/**
	 * Get Global Id
	 * 
	 * @return {@link String}
	 * @throws SQLException
	 */
	public String getGlobalIdForAddingComment(String type) throws SQLException {
		try {
			String query = "Select  globalid from glober_view gv WHERE gv.type='" + type + "' "
					+ "AND gv.assignedforsr='false' " + "AND gv.globalid IS NOT NULL ORDER BY RANDOM()";

			ResultSet rs = getResultSet(query);
			rs.next();
			return rs.getString("globalid");
		} finally {
			endConnection();
		}
	}

	/**
	 * Get SR number
	 * 
	 * @param date
	 * @return
	 * @throws SQLException
	 */
	public String getSrNumber(String date) throws SQLException {
		try {
			String query = "SELECT issue_tracker_number FROM positions WHERE issue_tracker_number IS NOT NULL AND state ='In Progress' AND start_date <'"
					+ date + "' ORDER BY RANDOM() Limit 1";
			ResultSet rs = getResultSet(query);
			rs.next();
			return rs.getString("issue_tracker_number");
		} finally {
			endConnection();
		}
	}

	/**
	 * Get handler id
	 * 
	 * @param globerId
	 * @return String
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public String getHandlerId(String globerId) throws SQLException {
		try {
			String query = "SELECT handlerid FROM glober_view WHERE globerid = '" + globerId + "'";
			ResultSet rs = getResultSet(query);
			rs.next();
			return rs.getString("handlerid");
		} finally {
			endConnection();
		}
	}
	
	/**
	 * Get gatekeeper name
	 * 
	 * @param candidateId
	 * @return String 
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public String getGateKeeperName(String candidateId) throws SQLException {
		try {
			String interviewerId = getGateKeeperId(candidateId);
			String query1 = "SELECT first_name FROM globers WHERE id ='" + interviewerId + "'";
			String query2 = "SELECT last_name FROM globers  WHERE id ='" + interviewerId + "'";
			ResultSet rs1 = getResultSet(query1);
			rs1.next();
			ResultSet rs2 = getResultSet(query2);
			rs2.next();
			String fName = rs1.getString("first_name").trim();
			String lName = rs2.getString("last_name").trim();
			String fullName = fName + " " + lName;
			return fullName;
		} finally {
			endConnection();
		}
	}
	
	/**
	 * Get gatekeeper id
	 * 
	 * @param candidateId
	 * @return String
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public String getGateKeeperId(String candidateId) throws SQLException {
		try {
			String query = "SELECT interviewer_id FROM technical_interviews WHERE candidate_id = '" + candidateId + "'";
			ResultSet rs = getResultSet(query);
			try {
				rs.next();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return rs.getString("interviewer_id");
		} finally {
			endConnection();
		}
	}
	
	/**
	 * Get recruiter name
	 * 
	 * @param candidateId
	 * @return String
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public String getRecruiterName(String candidateId) throws SQLException {
		try {
			String interviewerId = getRecruiterId(candidateId);
			String query1 = "SELECT first_name FROM globers WHERE id ='" + interviewerId + "'";
			String query2 = "SELECT last_name FROM globers  WHERE id ='" + interviewerId + "'";
			ResultSet rs1 = getResultSet(query1);
			rs1.next();
			ResultSet rs2 = getResultSet(query2);
			rs2.next();
			String fName = rs1.getString("first_name").trim();
			String lName = rs2.getString("last_name").trim();
			String fullName = fName + " " + lName;
			return fullName;
		} finally {
			endConnection();
		}
	}
	
	/**
	 * Get recruiter id
	 * 
	 * @param candidateId
	 * @return String
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public String getRecruiterId(String candidateId) throws SQLException {
		String query = "SELECT recruiter_id FROM technical_interviews WHERE candidate_id = '" + candidateId + "'";
		ResultSet rs = getResultSet(query);
		try {
			rs.next();
			return rs.getString("recruiter_id");
		} finally {
			endConnection();
		}
	}
	
	/**
	 * Get random candidateId
	 * 
	 * @return {@link String}
	 * @throws SQLException
	 */
	public String getCandidateId() throws SQLException {
		String query = " SELECT  candidateid AS candidateId FROM glober_view WHERE ((type<>'Glober' AND assignedforsr='false')) "
				+ "GROUP BY candidateid " + " ORDER BY RANDOM() " + " LIMIT 1";
		ResultSet rs = getResultSet(query);
		rs.next();
		return rs.getString("candidateId");
	}
	
	/**
	 * Get time zone based on location of the glober, day light saving start date and end date
	 * @param globerId
	 * @return {@link TimezoneDTO}
	 * @throws Exception
	 * @author deepakkumar.hadiya
	 */
	public TimezoneDTO getTimeZoneAndDSTForGloberLocation(String globerId) throws Exception {
		String query = "SELECT g.username, st.site_fk,s.name as sitename, timezone, st.daylight_saving_start_date AS dstStartDate, st.daylight_saving_end_date AS dstEndDate FROM sites s, globers g, contracts_data c, site_timezones st WHERE g.id = "
				+ globerId
				+ " AND g.contract_information_fk = c.contract_information_fk AND c.site_fk = st.site_fk AND st.site_fk=s.id AND c.end_date IS NULL";
		try {
			TimezoneDTO dto=new TimezoneDTO();
			ResultSet rs = getResultSet(query);
			if (rs.next()) {
				dto.setTimeZone(rs.getString("timezone"));
				dto.setDstStartDate(rs.getString("dstStartDate"));
				dto.setDstEndDate(rs.getString("dstEndDate"));
				dto.setSiteName(rs.getString("sitename"));
				return dto;
			} else {
				throw new Exception("Record is not found for glober id = " + globerId + " in data base tables");
			}
		} finally {
			endConnection();
		}
	}

}
