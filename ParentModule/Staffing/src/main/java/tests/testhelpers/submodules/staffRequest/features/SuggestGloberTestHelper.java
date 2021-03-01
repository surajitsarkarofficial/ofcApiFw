package tests.testhelpers.submodules.staffRequest.features;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.testng.Reporter;

import com.aventstack.extentreports.Status;

import constants.submodules.staffRequest.StaffRequestConstants;
import database.submodules.staffRequest.features.SuggestGloberDBHelper;
import endpoints.submodules.staffRequest.features.SuggestGloberEndPoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.staffRequest.features.SuggestGloberPayloadHelper;
import tests.testcases.submodules.staffRequest.StaffRequestBaseTest;
import tests.testhelpers.submodules.staffRequest.StaffRequestTestHelper;
import utils.RestUtils;
import utils.Utilities;

/**
 * 
 * @author akshata.dongare
 *
 */
public class SuggestGloberTestHelper extends StaffRequestTestHelper implements StaffRequestConstants {

	RestUtils restUtils;
	SuggestGloberDBHelper suggestGloberDBHelper;
	SuggestGloberPayloadHelper suggestGloberPayloadHelper;
	
	public SuggestGloberTestHelper(String userName) throws Exception {
		super(userName);
		suggestGloberDBHelper = new SuggestGloberDBHelper();
		suggestGloberPayloadHelper= new SuggestGloberPayloadHelper();
		restUtils = new RestUtils();
	}

	/**
	 * This method will return response of suggest glober to SR 
	 * @param baseUrl
	 * @param session
	 * @param globerType
	 * @param planType
	 * @return respone
	 * @throws Exception
	 */
	public Response suggestGloberToStaffRequest(String globerType, String planType)
			throws Exception {
		RestUtils restUtils = new RestUtils();
		Response suggestGloberResponse = null;
		String jsonBody = new SuggestGloberPayloadHelper().getSuggestGloberValidPayload(globerType, planType);
		if (jsonBody == null) {
			StaffRequestBaseTest.test.log(Status.SKIP, "Json body is null hence can not suggest glober to a SR");
		} else {
			RequestSpecification reqSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON)
					.body(jsonBody);
			String suggestGloberUrl = StaffRequestBaseTest.baseUrl + SuggestGloberEndPoints.suggestGloberUrl;
			suggestGloberResponse = restUtils.executePOST(reqSpec, suggestGloberUrl);
		}
		return suggestGloberResponse;
	}
	
	/**
	 * This method will return response after suggesting and assigning a glober
	 * @param globerType
	 * @param planType
	 * @return Response
	 * @throws Exception
	 */
	public Response suggestAndAssignGlober(String globerType, String planType) throws Exception {
		Response assignGloberResponse = null;
		Map<Object, Object> suggestedGloberData = new HashMap<Object, Object>();
		
		String suggestGloberJsonBody = new SuggestGloberPayloadHelper().getSuggestGloberValidPayload(globerType, planType);
		JSONObject suggestGloberJo = new JSONObject(suggestGloberJsonBody);
		
		RequestSpecification reqSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON).body(suggestGloberJsonBody);
		String suggestGloberUrl = StaffRequestBaseTest.baseUrl + SuggestGloberEndPoints.suggestGloberUrl;
		Response suggestGloberResponse = restUtils.executePOST(reqSpec, suggestGloberUrl);
		Reporter.log("suggestGloberResponse :"+suggestGloberResponse.asString(),true);
		String message = (String) restUtils.getValueFromResponse(suggestGloberResponse, "message");
		
		if(message.contains("Oh snap!")) {
			validateResponseToContinueTest(suggestGloberResponse, 400, "Status is not 400 even though glober already has high plan", true);
			StaffRequestBaseTest.test.log(Status.SKIP, "Glober already has high plan somewhere hence can't suggest and assign glober");
		}else if(message.contains("Success.")){
			validateResponseToContinueTest(suggestGloberResponse, 201, "Suggest Glober API call was not successful", true);
			suggestedGloberData.put("globerId",suggestGloberJo.getString("globerId"));
			suggestedGloberData.put("globalId",suggestGloberJo.getString("globalId"));
			suggestedGloberData.put("positionId",suggestGloberJo.getString("positionId"));
			String planStartDate = suggestGloberJo.getString("planStartDate");
			suggestedGloberData.put("assignmentStartDate",planStartDate);	
		}else if(message.contains("Sorry, max 3 Suggestions per SR")) {
			validateResponseToContinueTest(suggestGloberResponse, 400, "Status is not 400 even though 3 globers are already suggested", true);
			String positionId = suggestGloberJo.getString("positionId");
			String globalId = suggestGloberDBHelper.getSuggestedGlobalId(positionId);
			String globerId = suggestGloberDBHelper.getGloberIdFromGlobalId(globalId);
			suggestedGloberData.put("globerId",globerId);
			suggestedGloberData.put("globalId",globalId);
			suggestedGloberData.put("positionId",positionId);
			String planStartDate = suggestGloberJo.getString("planStartDate");
			suggestedGloberData.put("assignmentStartDate",planStartDate);
		}
		String assignGloberJsonbody = suggestGloberPayloadHelper.assignGloberValidValues(suggestedGloberData,globerType);
		Reporter.log("assignGloberJsonbody : "+assignGloberJsonbody,true);
		RequestSpecification requestSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON).body(assignGloberJsonbody);
		String assignGloberUrl = StaffRequestBaseTest.baseUrl + SuggestGloberEndPoints.assignGlober;
		assignGloberResponse = restUtils.executePOST(requestSpec, assignGloberUrl);;
		
		return assignGloberResponse;
	}
	
	/**
	 * This method will return response after suggesting and assigning a STG
	 * @param globerType
	 * @param planType
	 * @return response
	 * @throws Exception
	 */
	public Response suggestAndAssignSTG(String globerType, String planType, boolean pastDate) throws Exception {
		Response assignGloberResponse = null;
		String planStartDate = null;
		Map<Object, Object> suggestedGloberData = new HashMap<Object, Object>();
		
		String suggestGloberJsonBody = new SuggestGloberPayloadHelper().getSuggestGloberValidPayload(globerType, planType);
		JSONObject suggestGloberJo = new JSONObject(suggestGloberJsonBody);
		
		RequestSpecification reqSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON).body(suggestGloberJsonBody);
		String suggestGloberUrl = StaffRequestBaseTest.baseUrl + SuggestGloberEndPoints.suggestGloberUrl;
		Response suggestGloberResponse = restUtils.executePOST(reqSpec, suggestGloberUrl);
		Reporter.log("suggestGloberResponse :"+suggestGloberResponse.asString(),true);
		String message = (String) restUtils.getValueFromResponse(suggestGloberResponse, "message");
		
		if(message.contains("Oh snap!")) {
			validateResponseToContinueTest(suggestGloberResponse, 400, "Status is not 400 even though glober already has high plan", true);
			StaffRequestBaseTest.test.log(Status.SKIP, "Glober already has high plan somewhere hence can't suggest and assign glober");
		}else if(message.contains("Success.")){
			validateResponseToContinueTest(suggestGloberResponse, 201, "Suggest Glober API call was not successful", true);
			suggestedGloberData.put("globerId",suggestGloberJo.getString("globerId"));
			suggestedGloberData.put("globalId",suggestGloberJo.getString("globalId"));
			suggestedGloberData.put("positionId",suggestGloberJo.getString("positionId"));
			if(pastDate == true) {
				planStartDate = Utilities.getDateInPast("dd-MM-YYYY", 5);
			}else{
				planStartDate = suggestGloberJo.getString("planStartDate");
			}
			suggestedGloberData.put("assignmentStartDate",planStartDate);	
		}else if(message.contains("Sorry, max 3 Suggestions per SR")) {
			validateResponseToContinueTest(suggestGloberResponse, 400, "Status is not 400 even though 3 globers are already suggested", true);
			String positionId = suggestGloberJo.getString("positionId");
			String globalId = suggestGloberDBHelper.getSuggestedGlobalId(positionId);
			String globerId = suggestGloberDBHelper.getGloberIdFromGlobalId(globalId);
			suggestedGloberData.put("globerId",globerId);
			suggestedGloberData.put("globalId",globalId);
			suggestedGloberData.put("positionId",positionId);
			if(pastDate == true) {
				planStartDate = Utilities.getDateInPast("dd-MM-YYYY", 5);
			}else{
				planStartDate = suggestGloberJo.getString("planStartDate");
			}
			suggestedGloberData.put("assignmentStartDate",planStartDate);
		}
		String assignGloberJsonbody = suggestGloberPayloadHelper.assignSTGValidValues(suggestedGloberData,globerType);
		Reporter.log("assignGloberJsonbody : "+assignGloberJsonbody,true);
		RequestSpecification requestSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON).body(assignGloberJsonbody);
		String assignGloberUrl = StaffRequestBaseTest.baseUrl + SuggestGloberEndPoints.assignGlober;
		assignGloberResponse = restUtils.executePOST(requestSpec, assignGloberUrl);;
		
		return assignGloberResponse;
	}
	
	/**
	 * This method will return response of request assignment for STG
	 * @param globerType
	 * @param planType
	 * @param userName
	 * @return response
	 * @throws Exception
	 */
	public Response requestAssignmentSTG(String globerType, String planType, String userName) throws Exception {
		Response requestAssignmentResponse = null;
		Map<Object, Object> suggestedGloberData = new HashMap<Object, Object>();
		
		String suggestGloberJsonBody = new SuggestGloberPayloadHelper().getSuggestGloberValidPayload(globerType, planType);
		JSONObject suggestGloberJo = new JSONObject(suggestGloberJsonBody);
		
		RequestSpecification reqSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON).body(suggestGloberJsonBody);
		String suggestGloberUrl = StaffRequestBaseTest.baseUrl + SuggestGloberEndPoints.suggestGloberUrl;
		Response suggestGloberResponse = restUtils.executePOST(reqSpec, suggestGloberUrl);
		String message = (String) restUtils.getValueFromResponse(suggestGloberResponse, "message");
		
		if(message.contains("Oh snap!")) {
			validateResponseToContinueTest(suggestGloberResponse, 400, "Status is not 400 even though glober already has high plan", true);
			StaffRequestBaseTest.test.log(Status.SKIP, "Glober already has high plan somewhere hence can't suggest and assign glober");
		}else if(message.contains("Success.")){
			validateResponseToContinueTest(suggestGloberResponse, 201, "Suggest Glober API call was not successful", true);
			suggestedGloberData.put("positionId",suggestGloberJo.getString("positionId"));
			suggestedGloberData.put("userCompleteName",userName);
			suggestedGloberData.put("type",globerType);
		}else if(message.contains("Sorry, max 3 Suggestions per SR")) {
			validateResponseToContinueTest(suggestGloberResponse, 400, "Status is not 400 even though 3 globers are already suggested", true);
			suggestedGloberData.put("positionId",suggestGloberJo.getString("positionId"));
			suggestedGloberData.put("userCompleteName",userName);
			suggestedGloberData.put("type",globerType);
		}
		String requestAssignmentJsonbody = suggestGloberPayloadHelper.requestAssignmentSTGValidValuesPayload(suggestedGloberData);
		RequestSpecification requestSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON).body(requestAssignmentJsonbody);
		String requestAssignmentSTGUrl = StaffRequestBaseTest.baseUrl + SuggestGloberEndPoints.requestAssignmentSTGUrl;
		requestAssignmentResponse = restUtils.executePOST(requestSpec, requestAssignmentSTGUrl);;
		
		return requestAssignmentResponse;
	}
}

