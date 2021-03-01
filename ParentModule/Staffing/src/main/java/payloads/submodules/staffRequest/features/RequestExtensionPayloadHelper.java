package payloads.submodules.staffRequest.features;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import database.submodules.staffRequest.StaffRequestDBHelper;
import dto.submodules.staffRequest.features.RequestExtensionDTO;
import payloads.submodules.staffRequest.StaffRequestPayloadHelper;
import utils.Utilities;

/**
 * 
 * @author akshata.dongare
 *
 */
public class RequestExtensionPayloadHelper extends StaffRequestPayloadHelper{
	
	static StaffRequestDBHelper staffRequestDBHelper = new StaffRequestDBHelper();
	Map<Object, Object> requestExtensionMap = new HashMap<Object, Object>();
	Map<Object, Object> assignMentorMap = new HashMap<Object, Object>();
	String json = null;
	
	
	/**
	 * Get request extensionDto List
	 * @return requestExtensionDto
	 * @throws SQLException
	 */
	public static RequestExtensionDTO getRequestExtensionDtoList() throws SQLException {
		RequestExtensionDTO requestExtensionDto = new RequestExtensionDTO();
		
		requestExtensionDto.setExtensionDate(Utilities.getFutureDate("dd/MM/yyyy", 30));
		requestExtensionDto.setExtensionReason(staffRequestDBHelper.getRandomRequestExtensionReason());
		requestExtensionDto.setComment("Request extension done");		
		
		return requestExtensionDto;
	}
	
	/**
	 * This method will create payload
	 * @param requestExtensionData
	 * @return String
	 */
	public String requestExtensionPayload(Map<Object, Object> requestExtensionData) {
		requestExtensionMap.put("staffPlanId",requestExtensionData.get("staffPlanId"));
		requestExtensionMap.put("requestExtensionFlow", "EDITED");
		requestExtensionMap.put("type", requestExtensionData.get("type"));
		requestExtensionMap.put("requestExtensionDto", requestExtensionData.get("requestExtensionDto"));
		json = Utilities.createJsonBodyFromMap(requestExtensionMap);
		return json;
	}
	
	/**
	 * This method will create map of positive values
	 * @param staffPlanId
	 * @param type
	 * @return map
	 * @throws SQLException
	 */
	public Map<Object, Object> getRequestExtensionData(String staffPlanId, String type) throws SQLException{
		RequestExtensionDTO requestExtensionDto = getRequestExtensionDtoList();
		
		requestExtensionMap.put("staffPlanId",staffPlanId);
		requestExtensionMap.put("type", type);
		requestExtensionMap.put("requestExtensionDto",requestExtensionDto);
		return requestExtensionMap;
	}
	
	/**
	 * This method will create payload of positive values
	 * @param staffPlanId
	 * @param type
	 * @return String
	 * @throws SQLException
	 */
	public String requestExtensionValidPayload(String staffPlanId, String type) throws SQLException {
		requestExtensionMap = getRequestExtensionData(staffPlanId,type);
		return requestExtensionPayload(requestExtensionMap);
	}
	
	/**
	 * This method will create payload for assigning mentor to mentee
	 * @param menteeId
	 * @param mentorId
	 * @return String
	 */
	public String assignMentorToMenteePayload(String menteeId, String mentorId) {
		assignMentorMap.put("menteeId", menteeId);
		assignMentorMap.put("mentorId", mentorId);
		assignMentorMap.put("updateByResume", mentorId);
		
		json = Utilities.createJsonBodyFromMap(assignMentorMap);
		return json;
	}
	
	/**
	 * Valid values payload for assign mentor to mentee
	 * @param userName
	 * @return String
	 * @throws SQLException
	 */
	public String assignMentorToMenteeValidPayload(String userName) throws SQLException {
		String menteeId = staffRequestDBHelper.getGloberIdWithNoMentor();
		String mentorId = staffRequestDBHelper.getGloberId(userName);
		return assignMentorToMenteePayload(menteeId,mentorId);
	}
	
	/**
	 * Assign mentor to mentee payload with mentor id as 0
	 * @param userName
	 * @return String
	 * @throws SQLException
	 */
	public String assignMentorToMenteeNullMentee(String userName) throws SQLException {
		String menteeId = null;
		String mentorId = staffRequestDBHelper.getGloberId(userName);
		return assignMentorToMenteePayload(menteeId,mentorId);
	}
	
	/**
	 * Assign invalid mentor to mentee payload
	 * @param userName
	 * @return String
	 * @throws SQLException
	 */
	public String assignMentorToMenteeNullMentor(String userName) throws SQLException {
		String menteeId = staffRequestDBHelper.getGloberIdWithNoMentor();;
		String mentorId = null;
		return assignMentorToMenteePayload(menteeId,mentorId);
	}
	
	/**
	 * Assign mentor to mentee having mentor payload
	 * @param userName
	 * @return String
	 * @throws SQLException
	 */
	public String assignMentorToMenteeHavingMentorPayload(String userName) throws SQLException {
		String menteeId = staffRequestDBHelper.getGloberIdHavingMentor();
		String mentorId = staffRequestDBHelper.getGloberId(userName);
		return assignMentorToMenteePayload(menteeId,mentorId);
	}
	
	/**
	 * Assign mentor to mentee having mentor payload
	 * @param userName
	 * @return String
	 * @throws SQLException
	 */
	public String assignMentorToMenteeSameIds(String userName) throws SQLException {
		String menteeId = staffRequestDBHelper.getGloberIdHavingMentor();
		String mentorId = menteeId;
		return assignMentorToMenteePayload(menteeId,mentorId);
	}
	
	/**
	 * Assign inactive mentor to mentee payload
	 * @param userName
	 * @return String
	 * @throws SQLException
	 */
	public String assignInactiveMentorToMenteePayload(String userName) throws SQLException {
		String menteeId = staffRequestDBHelper.getGloberIdHavingMentor();
		String mentorId = "826403427";
		return assignMentorToMenteePayload(menteeId,mentorId);
	}
}
