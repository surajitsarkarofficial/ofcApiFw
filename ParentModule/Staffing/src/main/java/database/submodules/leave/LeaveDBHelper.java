package database.submodules.leave;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Reporter;

import constants.submodules.interview.InterviewConstants;
import database.StaffingDBHelper;
import dto.submodules.leave.LeaveDTO;

/**
 * 
 * @author deepakkumar.hadiya
 *
 */

public class LeaveDBHelper extends StaffingDBHelper implements InterviewConstants{

	/**
	 * This method returns leave details
	 * @param globerId
	 * @return {@link LeaveDTO}
	 * @throws Exception 
	 * @author deepakkumar.hadiya
	 */
	
	public LeaveDTO getLeaveDetails(String globerId) throws Exception {
		LeaveDTO dto=new LeaveDTO();
		String query=String.format("SELECT * FROM time_off t, globers g WHERE t.global_id = CAST(g.info_type_32 AS BIGINT) AND g.id=%s",globerId);
		try {
			ResultSet resultSet = getResultSet(query);
			if(resultSet.next()) {
				dto.setLeaveId(resultSet.getString("t.id"));
				dto.setGlobalId(resultSet.getString("t.global_id"));
				dto.setDateFrom(resultSet.getString("t.date_from"));
				dto.setDateTo(resultSet.getString("t.date_to"));
				dto.setLabel(resultSet.getString("t.label"));
				dto.setStatus(resultSet.getString("status"));
			}else {
				throw new Exception("Record is not found for glober id = "+globerId+" in 'time_off' table");
			}
			return dto;
		}finally {
			endConnection();
		}
	}
	
	/**
	 * This method returns all leave details
	 * @param globerId
	 * @return {@link List}
	 * @throws Exception 
	 * @author deepakkumar.hadiya
	 */
	
	public List<LeaveDTO> getAllLeaveDetails() throws Exception {
		List<LeaveDTO> leaves=new ArrayList<LeaveDTO>();
		String query=String.format("SELECT * FROM time_off");
		try {
			ResultSet resultSet = getResultSet(query);
			boolean isRecordFound=false;
			while(resultSet.next()) {
				LeaveDTO dto=new LeaveDTO();
				dto.setLeaveId(resultSet.getString("t.id"));
				dto.setGlobalId(resultSet.getString("t.global_id"));
				dto.setDateFrom(resultSet.getString("t.date_from"));
				dto.setDateTo(resultSet.getString("t.date_to"));
				dto.setLabel(resultSet.getString("t.label"));
				dto.setStatus(resultSet.getString("status"));
				leaves.add(dto);
				isRecordFound=true;
			}
			if(!isRecordFound) {
				throw new Exception("Record is not found in 'time_off' table");
			}
			return leaves;
		}finally {
			endConnection();
		}
	}
	
	/**
	 * This method to delete all records contain label 'Automation'
	 * @throws Exception 
	 * @author deepakkumar.hadiya
	 * @throws SQLException 
	 */
	
	public void deleteAllAutomationLeaves() throws SQLException  {
		String query="DELETE FROM time_off WHERE LABEL LIKE 'Automation%'";
		try {
			int records=executeUpdateQuery(query);
			Reporter.log("Total '"+records+"' records contain label = 'Automation' are deleted from time_off table",true);
		}finally {
			endConnection();
		}
	}
}
