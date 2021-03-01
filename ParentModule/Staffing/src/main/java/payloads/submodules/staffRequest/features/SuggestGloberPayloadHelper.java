package payloads.submodules.staffRequest.features;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.Status;

import constants.submodules.staffRequest.StaffRequestConstants;
import database.submodules.staffRequest.features.SuggestGloberDBHelper;
import payloads.submodules.staffRequest.StaffRequestPayloadHelper;
import tests.testcases.submodules.staffRequest.StaffRequestBaseTest;
import utils.Utilities;

/**
 * 
 * @author akshata.dongare
 *
 */
public class SuggestGloberPayloadHelper extends StaffRequestPayloadHelper implements StaffRequestConstants{
	SuggestGloberDBHelper suggestGloberDBHelper = new SuggestGloberDBHelper();
	Map<Object, Object> suggesGloberMap = new HashMap<Object, Object>();
	Map<Object, Object> assignGloberMap = new HashMap<Object, Object>();
	Map<Object, Object> rejectByPositionMap = new HashMap<Object, Object>();
	String json = null;
	
	/**
	 * This method will create payload for suggest glober
	 * @param data
	 * @return String
	 */
	public String getSuggestGloberPayload(Map<Object, Object> data) {
		suggesGloberMap.put("positionId", data.get("positionId"));
		suggesGloberMap.put("globalId", data.get("globalId"));
		suggesGloberMap.put("globerId", data.get("globerId"));
		suggesGloberMap.put("planType", data.get("planType"));
		suggesGloberMap.put("planStartDate", data.get("planStartDate"));
		suggesGloberMap.put("suggestionType", "MANUAL");
		suggesGloberMap.put("comments", "SUGGEST NEW GLOBER");
		suggesGloberMap.put("srGridPage", true);
		suggesGloberMap.put("type", data.get("type"));
		json = Utilities.createJsonBodyFromMap(suggesGloberMap);
		return json;
	}
	
	/**
	 * Thie method will get valid values for suggest glober
	 * @param globerType
	 * @param planType
	 * @return map
	 * @throws SQLException
	 * @throws ParseException
	 */
	public Map<Object,Object> suggestGloberValidValues(String globerType, String planType) throws SQLException, ParseException {
		String globalId = null;

		if(globerType=="Glober") {
			globalId = suggestGloberDBHelper.getGlobalIdOfTPGlober(globerType);
		}else {
			globalId = suggestGloberDBHelper.getGlobalIdForSrAssignedFalseSTG(globerType);
		}
		if(globalId == null) {
			StaffRequestBaseTest.test.log(Status.INFO, "Global id null for this STG, hence cannot suggest");
		}else {
			String globerId = suggestGloberDBHelper.getGloberIdFromGlobalId(globalId);
			String startDate = Utilities.getTodayDate("yyyy-MM-dd");
			String positionId = suggestGloberDBHelper.getInProgressPositionId(startDate);
			String planStartDateFromPositionId = suggestGloberDBHelper.getStartDateFromPositionId(positionId);
			String planStartDate = Utilities.changeDateFormat(planStartDateFromPositionId);
			
			suggesGloberMap.put("positionId", positionId);
			suggesGloberMap.put("globalId", globalId);
			suggesGloberMap.put("globerId", globerId);
			suggesGloberMap.put("planType", planType);
			suggesGloberMap.put("planStartDate", planStartDate);
			suggesGloberMap.put("suggestionType", "MANUAL");
			suggesGloberMap.put("comments", "SUGGEST NEW GLOBER");
			suggesGloberMap.put("srGridPage", true);
			suggesGloberMap.put("type", globerType);
		}
		return suggesGloberMap;
	}

	/**
	 * This method will create payload for suggest glober with valid values
	 * @param globerType
	 * @param planType
	 * @return String
	 * @throws SQLException
	 * @throws ParseException
	 */
	public String getSuggestGloberValidPayload(String globerType, String planType) throws SQLException, ParseException {
		suggesGloberMap= suggestGloberValidValues(globerType, planType);
		return getSuggestGloberPayload(suggesGloberMap);
	}
	/**
	 * This method created payload to assign suggested glober
	 * @param suggestedGloberData
	 * @param endDate
	 * @param globerType
	 * @return string
	 */
	public String getGloberAssignPayload(Map<Object, Object> suggestedGloberData,String endDate, String globerType) {
		
		assignGloberMap.put("comments", "Glober assigned");
		assignGloberMap.put("positionId", suggestedGloberData.get("positionId"));
		assignGloberMap.put("load", 100);
		assignGloberMap.put("globerType", globerType);
		assignGloberMap.put("globerId", suggestedGloberData.get("globerId"));
		assignGloberMap.put("globalId", suggestedGloberData.get("globalId"));
		assignGloberMap.put("assignmentStartDate", suggestedGloberData.get("assignmentStartDate"));
		assignGloberMap.put("assignmentEndDate", endDate);
		
		json = Utilities.createJsonBodyFromMap(assignGloberMap);
		return json;
	}
	
	/**
	 * This method will create payload of assign glober with valid values
	 * @param data
	 * @param globerType
	 * @return String
	 * @throws ParseException
	 */
	public String assignGloberValidValues(Map<Object, Object> data, String globerType) throws ParseException {
		String assignmentEndDate = Utilities.getFutureDate("dd-MM-yyyy", 0, 0, 1);
		return getGloberAssignPayload(data,assignmentEndDate,globerType);
	}
	
	/**
	 * This method creates payload to assign STG glober
	 * @param suggestedGloberData
	 * @param endDate
	 * @param globerType
	 * @return String
	 * @throws SQLException
	 */
	public String getSTGAssignPayload(Map<Object, Object> suggestedGloberData,String endDate, String globerType) throws SQLException {
		String globerId = suggestedGloberData.get("globerId").toString();
		String candidateName = suggestGloberDBHelper.getGloberFirstNameFromId(globerId);
		assignGloberMap.put("assignmentStartDate", suggestedGloberData.get("assignmentStartDate"));
		assignGloberMap.put("assignmentEndDate", endDate);
		assignGloberMap.put("effectiveHiringDate", suggestedGloberData.get("assignmentStartDate"));
		assignGloberMap.put("candidateName", candidateName);
		assignGloberMap.put("action", "hired");
		assignGloberMap.put("comments", candidateName+" is assigned");
		assignGloberMap.put("load", 100);
		assignGloberMap.put("positionId", suggestedGloberData.get("positionId"));
		assignGloberMap.put("globerType", globerType);
		assignGloberMap.put("globerId", globerId);
		
		json = Utilities.createJsonBodyFromMap(assignGloberMap);
		return json;
	}
	
	/**
	 * This method will create payload of assign STG with valid values
	 * @param data
	 * @param globerType
	 * @return String
	 * @throws SQLException
	 */
	public String assignSTGValidValues(Map<Object, Object> data, String globerType) throws SQLException {
		String assignmentEndDate = Utilities.getFutureDate("dd-MM-yyyy", 0, 0, 1);
		return getSTGAssignPayload(data,assignmentEndDate,globerType);
	}
	
	/**
	 * This method will create payload for suggest glober except given position
	 * @param globerType
	 * @param planType
	 * @return String
	 * @throws SQLException
	 * @throws ParseException
	 */
	public String suggestGloberExceptGivenPositionPayload(String globerType, String planType) throws SQLException, ParseException {
		suggesGloberMap= suggestGloberValidValues(globerType, planType);
		String startDate = Utilities.getTodayDate("yyyy-MM-dd");
		String positionId = suggestGloberDBHelper.getInProgressPositionIdForGivenPositionRole("1", startDate);
		String positionName = suggestGloberDBHelper.getPositionRoleNameAccordingToPositionRolId("1");
		String globalId = suggestGloberDBHelper.getTPGlobalIdExceptGivenPosition(globerType,positionName);
		suggesGloberMap.put("positionId", positionId);
		suggesGloberMap.put("globalId", globalId);
		return getSuggestGloberPayload(suggesGloberMap);
	}
	
	/**
	 * This method will create payload for suggest glober except given location
	 * @param globerType
	 * @param planType
	 * @return String
	 * @throws SQLException
	 * @throws ParseException
	 */
	public String suggestGloberExceptGivenLocationPayload(String globerType, String planType) throws SQLException, ParseException {
		suggesGloberMap= suggestGloberValidValues(globerType, planType);
		String positionId = suggesGloberMap.get("positionId").toString();
		String positionLocationId = suggestGloberDBHelper.getLocationIdFromPositionId(positionId);
		String globalId = suggestGloberDBHelper.getTPGlobalIdExceptGivenLocation(globerType, positionLocationId);
		suggesGloberMap.put("globalId", globalId);
		return getSuggestGloberPayload(suggesGloberMap);
	}
	
	/**
	 * This method creates payload to request assignment of STG
	 * @param suggestGloberData
	 * @return String
	 * @throws SQLException
	 */
	public String requestAssignmentSTGValidValuesPayload(Map<Object, Object> suggestGloberData) throws SQLException {
		String staffPlanId = suggestGloberDBHelper.getStaffPlanId(suggestGloberData.get("positionId").toString());
		assignGloberMap.put("bookGloberFlow", true);
		assignGloberMap.put("staffPlanId", staffPlanId);
		assignGloberMap.put("userCompleteName", suggestGloberData.get("userCompleteName"));
		assignGloberMap.put("userEmail", suggestGloberData.get("userCompleteName")+"@globant.com");
		assignGloberMap.put("type", suggestGloberData.get("type"));
		
		json = Utilities.createJsonBodyFromMap(assignGloberMap);
		return json;
	}
	
}