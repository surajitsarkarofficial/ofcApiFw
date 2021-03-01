package tests.testhelpers.submodules.openpositions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.testng.SkipException;

import database.submodules.openpositions.OpenPositionsDBHelper;
import endpoints.submodules.openpositions.OpenPositionsEndPoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.openpositions.OpenPositionsPayloadHelper;
import tests.testcases.StaffingBaseTest;
import tests.testcases.submodules.openpositions.OpenPositionBaseTest;
import tests.testcases.submodules.staffRequest.StaffRequestBaseTest;
import utils.RestUtils;

/**
 *@author shadab.waikar
 */
public class OpenPositionsTestHelper extends OpenPositionBaseTestHelper{
	
	private String openPositionPayload = null,positionId = null;
	private String plan_details[] = new String[4];
	private String globerPositionRoleFromDb = null;
	OpenPositionsDBHelper dbHelper;
	RestUtils restUtils;
	OpenPositionsPayloadHelper payloadHelper;

	public OpenPositionsTestHelper(String userName) throws Exception {
		super(userName);
		dbHelper = new OpenPositionsDBHelper();
		restUtils = new RestUtils();
		payloadHelper = new OpenPositionsPayloadHelper(userName);
	}

	/**
	 * This method will perform GET to fetch total open position count
	 * 
	 * @param userName
	 * @param globalId
	 * @return ({@link Response}
	 * @throws SQLException
	 */
	public Response getTotalCountOfOpenPositions(String userName,String globalId) throws SQLException {
		int page = 0,pageSize = 30;
		String url = StaffingBaseTest.baseUrl + String.format(OpenPositionsEndPoints.getTotalOpenPositionCount,globalId,page,pageSize);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(OpenPositionBaseTest.sessionId);	
		Response totalOpenPositionResponse = restUtils.executeGET(requestSpecification,url);
		return totalOpenPositionResponse;	
	}
	
	/**
	 * This method will perform GET to fetch applied and remaining open positions count of glober
	 * 
	 * @param userName
	 * @return ({@link Map}
	 * @throws SQLException
	 */
	public Map<Object, Object> getGloberAppliedAndRemainingOpenPositionsCount(String userName) throws SQLException{
		Map<Object, Object> openPositionsCounts = new HashMap<Object, Object>();		
		String glober = dbHelper.getGloberId(userName);
		String globalId = dbHelper.getGlobalIdFromGloberId(glober);
		String appliedCount = dbHelper.getGloberAppliedOpenPositionCount(globalId);
		
		int appliedCountFromDB = Integer.parseInt(appliedCount);
		int remainingCount = 2 - appliedCountFromDB;
		int globerAppliedCount = 2 - remainingCount;
		
		openPositionsCounts.put("appliedCountFromDB", appliedCountFromDB);
		openPositionsCounts.put("remainingCount", remainingCount);
		openPositionsCounts.put("globerAppliedCount", globerAppliedCount);
		return openPositionsCounts;
	}
	
	/**
	 * This method will perform GET to fetch glober positionRole
	 * 
	 * @param userName
	 * @return ({@link Response}
	 * @throws SQLException
	 */
	public Response getMatchingOpenPositionAccordingToGloberPositionRole(String userName) throws SQLException {
		int page = 0,pageSize = 30;
		String glober = dbHelper.getGloberId(userName);
		String globalId = dbHelper.getGlobalIdFromGloberId(glober);
		globerPositionRoleFromDb = dbHelper.getGloberPositionRoleById(globalId);
		
		String url = StaffingBaseTest.baseUrl + String.format(OpenPositionsEndPoints.getTotalOpenPositionCount,globalId,page,pageSize);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(OpenPositionBaseTest.sessionId);	
		Response totalOpenPositionResponse = restUtils.executeGET(requestSpecification,url);
		return totalOpenPositionResponse;	
	}
	
	/**
	 * This method will return glober position role from db
	 * @return ({@link String}
	 */
	public String getGloberPositionRoleFromDb() {
		return globerPositionRoleFromDb;
	}
	
	/**
	 * This method will perform GET to fetch percentages of matching open positions
	 * 
	 * @param userName
	 * @return ({@link Boolean}
	 * @throws Exception 
	 */
	public boolean getGloberPositionsAccordingToPercentage(String userName) throws Exception {
		int page = 0,pageSize = 30,positions = 8;
		ArrayList<String> actualPositionScore = new ArrayList<String>();
		ArrayList<String> ExpectedPositionScore = new ArrayList<String>();
		String glober = dbHelper.getGloberId(userName);
		String globalId = dbHelper.getGlobalIdFromGloberId(glober);
		
		String url = StaffingBaseTest.baseUrl + String.format(OpenPositionsEndPoints.getTotalOpenPositionCount,globalId,page,pageSize);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(OpenPositionBaseTest.sessionId);	
		Response totalOpenPositionResponse = restUtils.executeGET(requestSpecification,url);
		validateResponseToContinueTest(totalOpenPositionResponse, 200, "Failed to load open positions", true);
		io.restassured.path.json.JsonPath js = totalOpenPositionResponse.jsonPath();
		
		for(int i = 0; i<positions ;i++) {
			String matching_position = js.getString("details.list["+i+"].score");
			actualPositionScore.add(matching_position);
			ExpectedPositionScore.add(matching_position);
			Collections.sort(ExpectedPositionScore, Collections.reverseOrder());
		}
		return actualPositionScore.equals(ExpectedPositionScore);
	}
	
	/**
	 * This method will perform GET to apply to glober having reached to OP max.limit
	 * 
	 * @param userName
	 * @return ({@link Response}
	 * @throws Exception 
	 */
	public Response getGloberHavingReachedToApplyigOPLimit(String userName) throws Exception {
	
		int page = 0,pageSize = 30;
		int j = new Random().nextInt(15);
		String globerWithIdAndAppliedOPCount[] =  dbHelper.getGloberIdWithMaxAppliedToOP();
		String globalId = dbHelper.getGlobalIdFromGloberId(globerWithIdAndAppliedOPCount[0]);
		
		String url = StaffingBaseTest.baseUrl + String.format(OpenPositionsEndPoints.getTotalOpenPositionCount,globalId,page,pageSize);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(OpenPositionBaseTest.sessionId);	
		Response totalOpenPositionResponse = restUtils.executeGET(requestSpecification,url);
		validateResponseToContinueTest(totalOpenPositionResponse, 200, "Failed to load open positions", true);
		
		io.restassured.path.json.JsonPath js = totalOpenPositionResponse.jsonPath();
		String positionId = js.getString("details.list["+j+"].positionId");
		String score = js.getString("details.list["+j+"].score");
	    
		String openPositionPayload = payloadHelper.openPositionPayload(positionId, globerWithIdAndAppliedOPCount[0], globalId, score);
		
		String requestUrl = StaffingBaseTest.baseUrl + OpenPositionsEndPoints.applyToOpenPositions;
		RequestSpecification openPositionsRequestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON).body(openPositionPayload);
		Response openPositionResponse = restUtils.executePOST(openPositionsRequestSpecification, requestUrl);
		return openPositionResponse;
	}
	
	/**
	 * This method will perform POST to apply to open positions
	 * 
	 * @param userName
	 * @return ({@link Response}
	 * @throws Exception 
	 */
	public Response applyToOpenPosition(String userName) throws Exception {
		String chk = null;
		int page = 0,pageSize = 30,i = 0;
		String globerWithIdAndAppliedOPCount[] = dbHelper.getGloberIdWithMinAppliedToOPCount();
		String globalId = dbHelper.getGlobalIdFromGloberId(globerWithIdAndAppliedOPCount[0]);
		
		String url = StaffingBaseTest.baseUrl + String.format(OpenPositionsEndPoints.getTotalOpenPositionCount,globalId,page,pageSize);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(OpenPositionBaseTest.sessionId);	
		Response totalOpenPositionResponse = restUtils.executeGET(requestSpecification,url);
		validateResponseToContinueTest(totalOpenPositionResponse, 200, "Failed to load open positions", true);
		
		do {
			i = new Random().nextInt(15);
			positionId = restUtils.getValueFromResponse(totalOpenPositionResponse, "details.list["+i+"].positionId").toString();
			chk = dbHelper.getStatusOfPlanAbsentForGlober(globerWithIdAndAppliedOPCount[0], positionId);
		}while(!chk.equals("t"));
		
		String score = restUtils.getValueFromResponse(totalOpenPositionResponse, "details.list["+i+"].score").toString();
	    openPositionPayload = payloadHelper.openPositionPayload(positionId, globerWithIdAndAppliedOPCount[0], globalId, score);
	
		String requestUrl = StaffingBaseTest.baseUrl + OpenPositionsEndPoints.applyToOpenPositions;
		RequestSpecification openPositionsRequestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON).body(openPositionPayload);
		Response openPositionResponse = restUtils.executePOST(openPositionsRequestSpecification, requestUrl);
		
		plan_details = dbHelper.getPlanDetailsOfAppliedOpenPosition(positionId);	
		return openPositionResponse;
	
	}
	
	/**
	 * This method will return Sr plan details
	 * @return ({@link String[]}
	 * @throws SQLException 
	 */
	public String[] getPlanDetails() throws SQLException {
		dbHelper.unApplyToAppliedOpenPosition(positionId);
		return plan_details;
	}
	
	/**
	 * This method will perform POST to apply to open positions
	 * 
	 * @param userName
	 * @return ({@link Response}
	 * @throws Exception 
	 */
	public Response applyToAppliedOpenPosition(String userName) throws Exception{
		applyToOpenPosition(userName);
		
		String requestUrl = StaffingBaseTest.baseUrl + OpenPositionsEndPoints.applyToOpenPositions;
		RequestSpecification openPositionsRequestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON).body(openPositionPayload);
		Response openPositionResponse = restUtils.executePOST(openPositionsRequestSpecification, requestUrl);
		
		dbHelper.unApplyToAppliedOpenPosition(positionId);		
		return openPositionResponse;	
	}
	
	/**
	 * This method will perform GET to fetch open positions filter results
	 * 
	 * @param userName
	 * @param filterType
	 * @param filterId
	 * @return ({@link Response}
	 * @throws Exception 
	 */
	public Response applyToOpenPositionFilter(String userName,String filterType,String filterId) throws Exception {
		int page = 0,pageSize = 30;
		String skillsRequired = "False";
		
		String glober = dbHelper.getGloberId(userName);
		String globalId = dbHelper.getGlobalIdFromGloberId(glober);
		
		String requestUrl = StaffingBaseTest.baseUrl + String.format(OpenPositionsEndPoints.openPositionsFilters,globalId,page,pageSize,skillsRequired,filterType,filterId);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(OpenPositionBaseTest.sessionId);	
		Response openPositionFilterResponse = restUtils.executeGET(requestSpecification,requestUrl);
		validateResponseToContinueTest(openPositionFilterResponse, 200, "Failed to get open positions filter results", true);
	   
		int size = (int) restUtils.getValueFromResponse(openPositionFilterResponse, "details.size");
		if(size == 0)
			throw new SkipException("Not OpenPositions records found...!\nResponse : "+openPositionFilterResponse.prettyPrint());
	
		return openPositionFilterResponse;
	}
}
