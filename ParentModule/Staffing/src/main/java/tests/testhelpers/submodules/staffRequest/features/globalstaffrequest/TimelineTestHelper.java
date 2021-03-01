package tests.testhelpers.submodules.staffRequest.features.globalstaffrequest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.testng.Assert;

import database.submodules.staffRequest.features.globalstaffrequest.TimelineDBHelper;
import endpoints.submodules.staffRequest.features.SuggestGloberEndPoints;
import endpoints.submodules.staffRequest.features.globalstaffrequest.TimelineEndPoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.staffRequest.features.globalstaffrequest.QuickSuggestAndAssignPayloadHelper;
import payloads.submodules.staffRequest.features.globalstaffrequest.TimelinePayloadHelper;
import tests.testcases.StaffingBaseTest;
import tests.testcases.submodules.openpositions.OpenPositionBaseTest;
import tests.testcases.submodules.staffRequest.StaffRequestBaseTest;
import tests.testhelpers.submodules.staffRequest.StaffRequestTestHelper;

/**
 * @author shadab.waikar
 */
public class TimelineTestHelper extends StaffRequestTestHelper{

	public String srNumber = null;
	
	TimelineDBHelper timelineDb;
	TimelinePayloadHelper timelinePayload;
	QuickSuggestAndAssignPayloadHelper suggestAndAssignHelper;
	QuickSuggestAndAssignTestHelper suggestAndAssignTestHelper;
	
	public TimelineTestHelper(String userName) throws Exception {
		super(userName);
		timelineDb = new TimelineDBHelper();
		timelinePayload = new TimelinePayloadHelper();
		suggestAndAssignHelper = new QuickSuggestAndAssignPayloadHelper(userName);
		suggestAndAssignTestHelper = new QuickSuggestAndAssignTestHelper(userName);
	}

	/**
	 * This method will perform POST to suggest glober
	 * 
	 * @param response
	 * @param username
	 * @return {@link Response}
	 * @throws Exception
	 */
	public Response suggestGlober(Response response,String username) throws Exception {
		String json;
		String globerType = "Glober";
		suggestAndAssignTestHelper.checkIfPlansPerSrMoreThanThree(response, username, globerType);
		String globerId = timelineDb.getGloberToSuggest();
		
		if(username.equalsIgnoreCase("anand.datir") || username.equalsIgnoreCase("a.mazumder")) {
			json = timelinePayload.suggestGlober(globerId,QuickSuggestAndAssignTestHelper.srNumber,"High");
		}else {
			json = timelinePayload.suggestGlober(globerId,QuickSuggestAndAssignTestHelper.srNumber,"Low");
		}
		String requestUrl = StaffingBaseTest.baseUrl + SuggestGloberEndPoints.suggestGloberUrl;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON).body(json);
		response = restUtils.executePOST(requestSpecification, requestUrl);
		
		return response;
	}
	
	/**
	 * This method will perform POST to dismiss glober
	 * 
	 * @param response
	 * @return {@link Response}
	 * @throws Exception
	 */
	public Response dismissGlober(Response response) throws Exception {
		
		int i = new Random().nextInt(10);
		String srNumber = restUtils.getValueFromResponse(response, "details.positionDTOList[" + i + "].srNumber").toString();	
		String globerId = timelineDb.getGloberToSuggest();	
		String paylaod = timelinePayload.dismissGloberPayload(globerId, srNumber);
		
		String requestUrl = StaffingBaseTest.baseUrl + TimelineEndPoints.dismissGlober;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON).body(paylaod);
		response = restUtils.executePOST(requestSpecification, requestUrl);
		
		return response;
	}
	
	/**
	 * This method will perform GET to get all open positions
	 * 
	 * @param username
	 * @return {@link Response}
	 * @throws SQLException 
	 */
	public Response getOpenPositions(String username) throws SQLException {
		int page = 0,pageSize = 8;
		String skillsRequired = "false";
		String globalId = timelineDb.getGlobalIdFromGloberId(timelineDb.getGloberId(username));
		String requestUrl = StaffingBaseTest.baseUrl + String.format(TimelineEndPoints.getOpenPositionsUrl,globalId,page,pageSize,skillsRequired);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(OpenPositionBaseTest.sessionId);	
		Response openPositionFilterResponse = restUtils.executeGET(requestSpecification,requestUrl);
		return openPositionFilterResponse;
	}
	
	/**
	 * This method will perform POST to suggest Glober 3 times
	 * 
	 * @param username
	 * @return {@link Response}
	 * @throws Exception 
	 */
	public Response suggestGloberThreeTimesToVerifyWarnings(String username) throws Exception {
		String json;
		ArrayList<String> staffPlanIds = new ArrayList<String>();
		Response response;
		Response openPositionsResponse = getOpenPositions(username);
	    validateResponseToContinueTest(openPositionsResponse, 200, "Unable to Fetch Open Positions", true);
	    
	    String positionId = restUtils.getValueFromResponse(openPositionsResponse, "details.list[0].positionId").toString();
	    String srNumber = timelineDb.getSRNumberFromPositionId(positionId);
	    
	    for(int i = 0;i<3;i++) {
	    	String globerId = timelineDb.getRandomGloberToSuggest();
			if(username.equalsIgnoreCase("anand.datir") || username.equalsIgnoreCase("a.mazumder")) {
				json = timelinePayload.suggestGlober(globerId,srNumber,"High");
			}else {
				json = timelinePayload.suggestGlober(globerId,srNumber,"Low");
			}
			
			String requestUrl = StaffingBaseTest.baseUrl + SuggestGloberEndPoints.suggestGloberUrl;
			RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
					.sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON).body(json);
			response = restUtils.executePOST(requestSpecification, requestUrl);
			validateResponseToContinueTest(response, 201, "Unable to Suggest Glober.", true);
			
			String msg = restUtils.getValueFromResponse(response, "message").toString();
			Assert.assertTrue(msg.contains("Success"), "Failed to create plan.");
			
			String staffPlanId = restUtils.getValueFromResponse(response, "details[0].staffPlanId").toString();
			staffPlanIds.add(staffPlanId);
	    }
	
	    String globerId = timelineDb.getRandomGloberToSuggest();
		if(username.equalsIgnoreCase("anand.datir") || username.equalsIgnoreCase("a.mazumder")) {
			json = timelinePayload.suggestGlober(globerId,srNumber,"High");
		}else {
			json = timelinePayload.suggestGlober(globerId,srNumber,"Low");
		}
		
		String requestUrl = StaffingBaseTest.baseUrl + SuggestGloberEndPoints.suggestGloberUrl;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON).body(json);
		response = restUtils.executePOST(requestSpecification, requestUrl);
		updateCreatedStaffPlanStatus(staffPlanIds);
		return response;
		
	}
	
	/**
	 * This method will update created/suggested plans status
	 * 
	 * @param staffPlanIds
	 * @return {@link Class}
	 * @throws SQLException 
	 */
	public TimelineTestHelper updateCreatedStaffPlanStatus(ArrayList<String> staffPlanIds) throws SQLException {
		for(String staffPlanId : staffPlanIds) {
			suggestAndAssignHelper.updateCreatedPlanStatus(staffPlanId);
		}
		return this;
	}
	
	/**
	 * This method will perform GET to fetch top available globers on timeline
	 * 
	 * @param response
	 * @return {@link Response}
	 * @throws Exception 
	 * @throws SQLException 
	 */
	public Response getTopAvailableGlobersOnTimeline(Response response) throws Exception {
		int i = new Random().nextInt(30);
		String availableGlober = "true";
		String positionId = restUtils.getValueFromResponse(response, "details.positionDTOList["+i+"].positionId").toString();
		srNumber = restUtils.getValueFromResponse(response, "details.positionDTOList["+i+"].srNumber").toString();
		String requestUrl = StaffingBaseTest.baseUrl + String.format(TimelineEndPoints.getAvailableGlobersUrl,positionId,availableGlober);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(StaffRequestBaseTest.sessionId);	
		Response availableGloberResponse = restUtils.executeGET(requestSpecification,requestUrl);
		return availableGloberResponse;
		
	}
	
	/**
	 * This method will get date from response and convert it in date format.
	 * 
	 * @param response
	 * @return {@link List}
	 * @throws Exception 
	 */
	public List<String> getResponseDateInDateForamt(Response response) throws Exception{
		ArrayList<String> globerBenchSrtDts = new ArrayList<String>();
		io.restassured.path.json.JsonPath js = response.jsonPath();
		String globerCount = restUtils.getValueFromResponse(response, "details.totalCount").toString();
		if(globerCount == "0")
			globerBenchSrtDts = null;
		else
			globerBenchSrtDts = js.get("details.asGloberList.benchDate");
		return globerBenchSrtDts;
	}
	
	/**
	 * This method will perform GET to fetch top available globers on timeline
	 * 
	 * @param response
	 * @param no_of_days
	 * @return {@link Response}
	 * @throws Exception 
	 */
	public Response getTopAvailableGlobersOnTimelineWithFilters(Response response,String no_of_days) throws Exception {
		int i = new Random().nextInt(30);
		String positionId = restUtils.getValueFromResponse(response, "details.positionDTOList["+i+"].positionId").toString();
		srNumber = restUtils.getValueFromResponse(response, "details.positionDTOList["+i+"].srNumber").toString();
		String requestUrl = StaffingBaseTest.baseUrl + String.format(TimelineEndPoints.getSrSkillsMatchingTopThrityGlobers,positionId,no_of_days);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(StaffRequestBaseTest.sessionId);	
		Response top30GlobersGloberResponse = restUtils.executeGET(requestSpecification,requestUrl);
		return top30GlobersGloberResponse;
		
	}
}
