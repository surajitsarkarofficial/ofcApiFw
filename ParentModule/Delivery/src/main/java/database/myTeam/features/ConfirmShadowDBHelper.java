package database.myTeam.features;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.testng.SkipException;

import database.myTeam.MyTeamDBHelper;
import dto.myTeam.features.ConfirmShadowPositionDTOList;

/**
 * @author poonam.kadam
 *
 */

public class ConfirmShadowDBHelper extends MyTeamDBHelper {

	public ConfirmShadowDBHelper() {
	}

	/**
	 * This method will return Position list for confirming shadow by Position id
	 * 
	 * @param postionId
	 * @return List<ConfirmShadowPositionDTOList>
	 * @throws SQLException
	 */
	public List<ConfirmShadowPositionDTOList> getPositionListByPositionId(int postionId) throws SQLException {
		List<ConfirmShadowPositionDTOList> positionList = new ArrayList<ConfirmShadowPositionDTOList>();
		ConfirmShadowPositionDTOList positionListDto = new ConfirmShadowPositionDTOList();

		String query = "select pos.*, proj.name as projectName,\r\n" + 
				"cl.id as clientId, cl.name as clientName, cl.business_unit_fk as clientBuId,\r\n" + 
				"st.name as positionStudio,\r\n" + 
				"glb.work_email as handlerEmail,\r\n" + 
				"ps.name as seniority,\r\n" + 
				"loc.id as locationId, loc.location as location from positions pos\r\n" + 
				"INNER JOIN projects proj ON pos.project_fk = proj.id\r\n" + 
				"INNER JOIN studios st ON pos.studio_fk = st.id\r\n" + 
				"INNER JOIN globers glb ON pos.handler_fk = glb.id\r\n" + 
				"INNER JOIN position_seniorities ps ON pos.position_seniority_fk = ps.id\r\n" + 
				"INNER JOIN location_office loc ON pos.location_fk = loc.id\r\n" + 
				"INNER JOIN clients cl ON proj.client_fk = cl.id\r\n" + 
				" where pos.id=" + postionId ;

		try {
			ResultSet rs = getResultSet(query);
			while (rs.next()) {
				positionListDto = new ConfirmShadowPositionDTOList();
				positionListDto.setPositionId(rs.getInt("id"));
				positionListDto.setPositionName(rs.getString("name"));
				positionListDto.setProjectId(rs.getInt("project_fk"));
				positionListDto.setStartDate(rs.getDate("start_date"));
				positionListDto.setAssignmentEndDate(rs.getDate("assignment_end_date"));
				positionListDto.setSrType(rs.getString("assignment_type"));
				positionListDto.setSubmitterId(rs.getString("submitter"));
				if(rs.getBoolean("interview_required"))
					positionListDto.setClientInterviewRequired("Yes");
				else if(!rs.getBoolean("interview_required"))
					positionListDto.setClientInterviewRequired("No");
				positionListDto.setReplacementFlag(rs.getBoolean("replacement"));
				positionListDto.setHandlerTeamId(rs.getInt("handler_team_fk"));
				positionListDto.setLastUpdatedDate(rs.getDate("last_modified"));
				positionListDto.setPositionState(rs.getString("state"));
				positionListDto.setSrNumber(rs.getInt("issue_tracker_number"));
				positionListDto.setPositionStudioId(rs.getInt("studio_fk"));
				if(rs.getBoolean("english_required"))
					positionListDto.setEnglishRequired("Yes");
				else if(!rs.getBoolean("english_required"))
					positionListDto.setEnglishRequired("No");
				positionListDto.setCandidateType(rs.getString("candidate_type"));
				positionListDto.setHiredOrStaffedDate(rs.getDate("hire_or_staffed_date"));
				positionListDto.setStaffRequestSubmitDate(rs.getDate("submit_date"));
				positionListDto.setSowId(rs.getInt("sow_fk"));
				positionListDto.setPositionRoleId(rs.getInt("position_role_fk"));
				positionListDto.setPositionSeniorityId(rs.getInt("position_seniority_fk"));		
				positionListDto.setProjectName(rs.getString("projectName"));
				positionListDto.setClientId(rs.getInt("clientId"));
				positionListDto.setClientName(rs.getString("clientName"));
				positionListDto.setClientBuId(rs.getInt("clientBuId"));
				positionListDto.setPositionStudio(rs.getString("positionStudio"));
				positionListDto.setHandlerEmail(rs.getString("handlerEmail"));
				positionListDto.setSeniority(rs.getString("seniority"));
				positionListDto.setLocationId(rs.getInt("locationId"));
				positionListDto.setLocation(rs.getString("location"));
				positionList.add(positionListDto);
			}
		} catch (SQLException e) {
			throw new SkipException("getPositionListByPositionId is not working" + e.getMessage());
		}
		finally {
			endConnection();
		}
		return positionList;
	}
}
