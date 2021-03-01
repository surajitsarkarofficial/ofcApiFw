package tests.testhelpers.submodules.staffRequest.features;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.testng.Reporter;

import com.aventstack.extentreports.Status;

import constants.submodules.staffRequest.StaffRequestConstants;
import database.submodules.staffRequest.StaffRequestDBHelper;
import dto.submodules.staffRequest.features.MarkGloberFitUnfitSkillDTO;
import endpoints.submodules.staffRequest.features.MarkGloberFitEndPoints;
import endpoints.submodules.staffRequest.features.SuggestGloberEndPoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.staffRequest.features.MarkGloberFitPayloadHelper;
import payloads.submodules.staffRequest.features.SuggestGloberPayloadHelper;
import properties.StaffingProperties;
import tests.testcases.submodules.staffRequest.StaffRequestBaseTest;
import tests.testhelpers.submodules.staffRequest.StaffRequestTestHelper;
import utils.RestUtils;

public class MarkGloberFitTestHelper extends StaffRequestTestHelper implements StaffRequestConstants {
	MarkGloberFitPayloadHelper markGloberFitPayloadHelper;
	RestUtils restUtils = new RestUtils();
	StaffRequestDBHelper staffRequestDBHelper;
	Response markGloberResponse;
	
	public MarkGloberFitTestHelper(String userName) throws Exception {
		super(userName);
		markGloberFitPayloadHelper = new MarkGloberFitPayloadHelper();
		staffRequestDBHelper = new StaffRequestDBHelper();
	}
	
	/**
	 * This method will create map for fit interview
	 * @param reasonWithStatusAndFeedback
	 * @param staffPlanId
	 * @param userName
	 * @param globerType
	 * @param interviewConducted
	 * @return map
	 */
	public Map<Object,Object> fitInterviewMap(List<String> reasonWithStatusAndFeedback, String staffPlanId,String userName, String globerType, String interviewConducted){
		Map<Object, Object> mapData = new HashMap<Object, Object>();
		mapData.put("status", reasonWithStatusAndFeedback.get(0));
		mapData.put("fitInterviewReason", reasonWithStatusAndFeedback.get(1));
		mapData.put("fitInterviewGloberFeedback", reasonWithStatusAndFeedback.get(2));
		mapData.put("staffPlanId", staffPlanId);
		mapData.put("userName", userName);
		mapData.put("type",globerType);
		mapData.put("fitInterviewConducted", interviewConducted);
		return mapData;
	}
	/**
	 * This method will return reason and feedback based on fit or unfit status
	 * @param fitUnfitValue
	 * @return List
	 * @throws Exception
	 */
	public List<String> getFitUnfitReasonWithStatusAndFeedback(String fitUnfitValue) throws Exception {
		List<String> reasonWithStatusAndFeedback = new ArrayList<String>();
		String reason,globerFeedback=null;
		String reasonUrl = StaffRequestBaseTest.baseUrl + String.format(MarkGloberFitEndPoints.markGloberReasons, fitUnfitValue);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(StaffRequestBaseTest.sessionId);
		
		Response response = restUtils.executeGET(requestSpecification, reasonUrl);
		new StaffRequestBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch reason of fit/unfit", true);
		
		if(fitUnfitValue == "FIT") {
			reason = "Fit interview approved";
			globerFeedback = "";
		}else {
			String resonJsonPath = "details.[1].reason";
			reason = restUtils.getValueFromResponse(response, resonJsonPath).toString();
			globerFeedback = "Reject reason for a glober is added";
		}
		reasonWithStatusAndFeedback.add(fitUnfitValue);
		reasonWithStatusAndFeedback.add(reason);
		reasonWithStatusAndFeedback.add(globerFeedback);
		
		return reasonWithStatusAndFeedback;
	}
	
	/**
	 * Add skills to mark glober fit 
	 * @return list
	 */
	public List<MarkGloberFitUnfitSkillDTO> getSkillList(){
		MarkGloberFitUnfitSkillDTO markGloberFitUnfitSkillDTO;
		List<MarkGloberFitUnfitSkillDTO> listOfSkills = new ArrayList<MarkGloberFitUnfitSkillDTO>();
		
		int alberthaId = 4018;
		String competencyId = "52128025";
		String competencyName = "clear thinker";
		int evidenceValue = 3;
		String importance = "2";
		String importanceName = "REQUIRED";
		boolean skillMatching = false;
		markGloberFitUnfitSkillDTO = new MarkGloberFitUnfitSkillDTO(alberthaId, competencyId, competencyName, evidenceValue, importance, importanceName, skillMatching);
		listOfSkills.add(markGloberFitUnfitSkillDTO);
		
		return listOfSkills;
	}
	
	/**
	 * This method will return response of mark fit glober
	 * @param userName
	 * @param fitUnfitValue
	 * @param globerType
	 * @param interviewConducted
	 * @return response
	 * @throws Exception
	 */
	public Response markFitUnfitGlober(String userName, String fitUnfitValue, String globerType, String interviewConducted) throws Exception {
		//Get staffplanId after suggestion
		String staffPlanId = getStaffPlanId(globerType);
		
		//Get reason
		List<String> fitUnfitReasonWithFeedback = getFitUnfitReasonWithStatusAndFeedback(fitUnfitValue);
		
		//Map the values
		Map<Object, Object> data = fitInterviewMap(fitUnfitReasonWithFeedback, staffPlanId, userName, globerType, interviewConducted);
		
		String jsonBody = markGloberFitPayloadHelper.markGloberFitUnfitValidPayload(data);
		Reporter.log("Json body : "+jsonBody,true);
		
		if(jsonBody==null) {
			StaffRequestBaseTest.test.log(Status.SKIP, "Json body is null hence can not mark glober as fit or unfit");
		}else {
			RequestSpecification reqSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId)
					.contentType(ContentType.JSON).body(jsonBody);
			String postMarkFitGloberUrl = StaffRequestBaseTest.baseUrl +MarkGloberFitEndPoints.postMarkFitGlober;
			markGloberResponse =restUtils.executePOST(reqSpec, postMarkFitGloberUrl);
		}
		return markGloberResponse;	
	}
	
	/**
	 * This method will return response for view fit interview feedback
	 * @param staffplanId
	 * @param globerType
	 * @return response
	 * @throws SQLException
	 */
	public Response getViewFitInterviewFeedback(String staffplanId, String globerType) throws SQLException {
		String globalId = staffRequestDBHelper.getGlobalIdFromStaffplanId(staffplanId);
		String globerId = staffRequestDBHelper.getGloberIdFromGlobalId(globalId);
		
		if(globerType == "In Pipe" || globerType == "New Hire") {
			globerType= globerType.trim();
			globerType = globerType.replaceAll("\\s", "%20"); 
		}
		String getViewFitInterviewFeedbackUrl = StaffRequestBaseTest.baseUrl + String.format(MarkGloberFitEndPoints.getViewFitInterviewFeedback, staffplanId, globerId, globerType, "true");
		Reporter.log("getViewFitInterviewFeedbackUrl :" +getViewFitInterviewFeedbackUrl,true);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(StaffRequestBaseTest.sessionId);
		Response response = restUtils.executeGET(requestSpecification, getViewFitInterviewFeedbackUrl);
		return response;
	}
	
	/**
	 * This method will return response of fit interview confirmation 
	 * @param userName
	 * @param confirmation
	 * @return response
	 * @throws Exception
	 */
	public Response confirmFitInterview(String staffPlanId, String confirmation) throws Exception {
		String postFitInterviewConfirmationUrl = StaffingProperties.staffingorchestraBaseURL + String.format(MarkGloberFitEndPoints.postFitInterviewConfirmation, staffPlanId, confirmation);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(StaffRequestBaseTest.sessionId);
		Response fitInterviewConfirmationResponse = restUtils.executePOST(requestSpecification, postFitInterviewConfirmationUrl);
		Reporter.log("fitInterviewConfirmationResponse : "+fitInterviewConfirmationResponse.asString(),true);
		return fitInterviewConfirmationResponse;
	}
	
	/**
	 * This method will get fit interview confirmation value for a particular staff plan id
	 * @param staffPlanId
	 * @return String
	 * @throws SQLException
	 */
	public String getFitInterviewConfirmationFromDB(String staffPlanId) throws SQLException {
		String confirmationValueDB = staffRequestDBHelper.getGloberResponseForFitInterviewConfirmation(staffPlanId);
		return confirmationValueDB;
	}
	/**
	 * This method will return position id from the json which is used to suggest glober 
	 * @param globerType
	 * @param planType
	 * @param rejectScenario
	 * @return String
	 * @throws Exception
	 */
	public String getPositionIdFromGloberSuggestionForRejectScenario(String globerType, String planType, String rejectScenario)
			throws Exception {
		String positionIdForStaffPlanId,jsonBody = null;
		if(rejectScenario.contains(REJECTBYPOSITION)) {
			jsonBody = new SuggestGloberPayloadHelper().suggestGloberExceptGivenPositionPayload(globerType, planType);
		}else if(rejectScenario.contains(REJECTBYLOCATION)) {
			jsonBody = new SuggestGloberPayloadHelper().suggestGloberExceptGivenLocationPayload(globerType, planType);
		}
		if (jsonBody == null) {
			StaffRequestBaseTest.test.log(Status.SKIP, "Json body is null hence can not suggest glober to a SR");
		} else {
			RequestSpecification reqSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON)
					.body(jsonBody);
			String suggestGloberUrl = StaffRequestBaseTest.baseUrl + SuggestGloberEndPoints.suggestGloberUrl;
			restUtils.executePOST(reqSpec, suggestGloberUrl);
		}
		JSONObject jo = new JSONObject(jsonBody);
		positionIdForStaffPlanId = jo.getString("positionId");
		return positionIdForStaffPlanId;
	}
	
	/**
	 * Get staff plan id from position id
	 * @param globerType
	 * @param scenario
	 * @return String
	 * @throws Exception
	 */
	public String getStaffPlanIdExceptGivenPosition(String globerType, String scenario) throws Exception {
		String positionIdForStaffPlanId = getPositionIdFromGloberSuggestionForRejectScenario(globerType,"Low",scenario);
		
		String staffPlanIdUrl = StaffRequestBaseTest.baseUrl + String.format(MarkGloberFitEndPoints.getStaffPlanId, positionIdForStaffPlanId,true);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(StaffRequestBaseTest.sessionId);
		
		Response response = restUtils.executeGET(requestSpecification, staffPlanIdUrl);
		new StaffRequestBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch staff plan Id", true);
		
		String staffPlanIdJsonPath = "details[0].staffPlanId";
		String staffPlanId = restUtils.getValueFromResponse(response, staffPlanIdJsonPath).toString();
		return staffPlanId;
	}
	
	/**
	 * This method will return response of reject by position or reject by location
	 * @param userName
	 * @param globerType
	 * @param scenario
	 * @param fitUnfitValue
	 * @param interviewConducted
	 * @return Response
	 * @throws Exception
	 */
	public Response rejectByPositionAndLocation(String userName, String globerType, String scenario, String fitUnfitValue, String interviewConducted) throws Exception {
		String staffPlanId = getStaffPlanIdExceptGivenPosition(globerType,scenario);
		
		//Get reject Reason
		String rejectReason = null, globerFeedback = null;
		String reasonUrl = StaffRequestBaseTest.baseUrl + String.format(MarkGloberFitEndPoints.markGloberReasons, fitUnfitValue);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(StaffRequestBaseTest.sessionId);
		
		Response response = restUtils.executeGET(requestSpecification, reasonUrl);
		new StaffRequestBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch reason of fit/unfit", true);
		
		if(scenario.contains(REJECTBYPOSITION)) {
			String resonJsonPath = "details.[4].reason";
			rejectReason = restUtils.getValueFromResponse(response, resonJsonPath).toString();
			String globerFeedbackJsonPath = "details.[4].reasonFeedback";
			globerFeedback = restUtils.getValueFromResponse(response, globerFeedbackJsonPath).toString();
		}else if(scenario.contains(REJECTBYLOCATION)) {
			String resonJsonPath = "details.[3].reason";
			rejectReason = restUtils.getValueFromResponse(response, resonJsonPath).toString();
			String globerFeedbackJsonPath = "details.[3].reasonFeedback";
			globerFeedback = restUtils.getValueFromResponse(response, globerFeedbackJsonPath).toString();
		}
		List<String> reasonWithStatusAndFeedback = new ArrayList<String>();
		reasonWithStatusAndFeedback.add(fitUnfitValue);
		reasonWithStatusAndFeedback.add(rejectReason);
		reasonWithStatusAndFeedback.add(globerFeedback);
		
		//Map the values
		Map<Object, Object> data = fitInterviewMap(reasonWithStatusAndFeedback, staffPlanId, userName, globerType, interviewConducted);
		
		String jsonBody = markGloberFitPayloadHelper.markGloberFitUnfitValidPayload(data);
		Reporter.log("jsonBody :"+jsonBody,true);
		if(jsonBody==null) {
			StaffRequestBaseTest.test.log(Status.SKIP, "Json body is null hence can not mark glober as fit or unfit");
		}else {
			RequestSpecification reqSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId)
					.contentType(ContentType.JSON).body(jsonBody);
			String postMarkFitGloberUrl = StaffRequestBaseTest.baseUrl +MarkGloberFitEndPoints.postMarkFitGlober;
			markGloberResponse =restUtils.executePOST(reqSpec, postMarkFitGloberUrl);
		}
		return markGloberResponse;
	}
}
