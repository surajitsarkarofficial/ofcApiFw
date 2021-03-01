package database.submodules.staffRequest;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.StaffingDBHelper;
import dto.submodules.staffRequest.features.PositionSeniorityMappingDTO;

public class StaffRequestDBHelper extends StaffingDBHelper{

	public StaffRequestDBHelper() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * This method will return position role id , position name and corresponding
	 * seniority Id, seniority name
	 * 
	 * @return positionSeniorityMappingDTO
	 * @throws SQLException
	 */
	public PositionSeniorityMappingDTO getPositionAndSeniority() throws SQLException {
		PositionSeniorityMappingDTO positionSeniorityMappingDTO = null;
		ResultSet resultSet = null;
		String query = "SELECT  pr.id as positionRoleId,pr.name as positionRoleName, ps.name seniorityName, ps.id as seniorityId "
				+ "FROM position_templates pt "
				+ "INNER JOIN position_seniorities ps on pt.position_seniority_fk = ps.id "
				+ "INNER JOIN position_roles pr on pr.id = pt.position_role_fk "
				+ "INNER JOIN position_roles_seniorities psr on ps.id = psr.seniority_fk and pr.id = psr.position_role_fk "
				+ "WHERE pr.name not in ('VP Technology','VP Delivery' ,'Technology Evangelist','Solutions Delivery VP','Delivery Manager','Tech Director' "
				+ ", 'Delivery Director', 'Technical Leader','QC Game Analyst','Counselor','Guru','Tech Master','Staff Manager','Security','Program Manage', "
				+ "'Project Manager','Account Manager','Project Analyst','Business Analyst') "
				+ "ORDER by random() limit 1;";
		try {
			resultSet = getResultSet(query);
			while (resultSet.next()) {
				positionSeniorityMappingDTO = new PositionSeniorityMappingDTO(resultSet.getInt("positionRoleId"),
						resultSet.getString("positionRoleName"), resultSet.getString("seniorityName"),
						resultSet.getInt("seniorityId"));
			}
			return positionSeniorityMappingDTO;

		} finally {
			endConnection();
		}
	}
	 
	/**
	 * Get work mail from gloebrId
	 * @param globerId
	 * @return String
	 * @throws SQLException
	 */
	public String getWorkMail(String globerId) throws SQLException {
		String workMail = null;
		String query = "select work_email from globers where id ='"+globerId+"'";
		try {
			ResultSet rs = getResultSet(query);
			rs.next();
			workMail = rs.getString("work_email");
			return workMail;
		}finally {
			endConnection();
		}
	}
	
	/**
	 * Get global id from staff plan id
	 * @param staffPlanId
	 * @return String
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public String getGlobalIdFromStaffplanId(String staffPlanId) throws SQLException {
		String globalId = null;
		String query = "SELECT global_id as globalId FROM staffing_plan WHERE id = "+staffPlanId;
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
	 * Get glober response after confirming fit interview as yes or no
	 * @param staffPlanId
	 * @return String
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public String getGloberResponseForFitInterviewConfirmation(String staffPlanId) throws SQLException {
		String query = "SELECT glober_response FROM staffing_fit_interview_confirmation WHERE staffing_plan_fk = "+staffPlanId;
		try {
			ResultSet rs = getResultSet(query);
			rs.next();
			return rs.getString("glober_response");
		}finally {
			endConnection();
		}
	}
}
