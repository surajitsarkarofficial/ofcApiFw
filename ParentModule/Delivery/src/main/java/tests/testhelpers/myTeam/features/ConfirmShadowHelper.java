package tests.testhelpers.myTeam.features;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

import com.aventstack.extentreports.Status;

import database.myTeam.features.ConfirmShadowDBHelper;
import dto.myTeam.features.ConfirmShadowPositionDTOList;
import endpoints.myTeam.features.ConfirmShadowEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.GlowBaseTest;
import tests.testcases.myTeam.MyTeamBaseTest;
import tests.testhelpers.myTeam.MyTeamTestHelper;
import utils.ExtentHelper;
import utils.RestUtils;
import utils.Utilities;

/**
 * @author pooja.kakade
 *
 */

public class ConfirmShadowHelper extends MyTeamTestHelper{
	
	RequestSpecification requestSpecification;
	RestUtils restUtils = new RestUtils();
	ConfirmShadowDBHelper confirmShadowDBHelper;
	
	public ConfirmShadowHelper(String userName) throws Exception {
		super(userName);
		confirmShadowDBHelper = new ConfirmShadowDBHelper();
	}
	
	/**
	 * @author pooja.kakade
	 * 
	 * This method will return required test data to confirm shadow
	 * 
	 * @param userName
	 * @param userId
	 * @return String 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getRequiredTestDataForConfirmShadow(String userName, String userId) throws Exception {
		
		List<Object> paramValaues = null;
		
		List<Integer> projectIds = new MyTeamProjectHelper(userName).getProjectIdsOfGlober(userName,userId);
		
		for(int projectId : projectIds){
			
			requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
			String shadowDetailsUrl = MyTeamBaseTest.baseUrl + String.format(ConfirmShadowEndpoints.getShadowDetails, projectId, true, true, true);
			
			ExtentHelper.test.log(Status.INFO, "Request URL : " + shadowDetailsUrl);
			Response shadowResponse = restUtils.executeGET(requestSpecification, shadowDetailsUrl);
			new MyTeamBaseTest().validateResponseToContinueTest(shadowResponse, 200, "Unable to fetch shadow resource details",
					true);
			List<Integer> positionIds = (List<Integer>) restUtils.getValueFromResponse(shadowResponse, "$.details[?(@.positionAssignmentType =='SHADOW')].positionId");
			
			if(!positionIds.isEmpty()){ 
				
				int positionId = Utilities.getRandomIdFromList(positionIds);
				List<Integer> srNumbers =  (List<Integer>) restUtils.getValueFromResponse(shadowResponse, "$.details[?(@.positionId == "+positionId+")].srNumber");
				int srNumber = srNumbers.get(0);
				int clientId = getClientIdByProjectId(userId,projectId);
				
				requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
				String url = MyTeamBaseTest.baseUrl + String.format(ConfirmShadowEndpoints.getOpenPositionDetails, "50", "0", clientId, projectId);
				ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
				Response response = restUtils.executeGET(requestSpecification, url);
				new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch Open positions results",
						true);
				ExtentHelper.test.log(Status.INFO, "get open position method executed successfully");
				
				List<Integer> openingIds = (List<Integer>) restUtils.getValueFromResponse(response, "$.details.sfdcOpeningDTOList[*].sfdcOpeningId");
				// If sfdcOpeningDTOList empty search openings in other projects
				if (!openingIds.isEmpty()) {
					int openingId = Utilities.getRandomIdFromList(openingIds);
					List<String> assignmentStartDates = (List<String>) restUtils.getValueFromResponse(response,
							"$.details.sfdcOpeningDTOList[?(@.sfdcOpeningId == " + openingId + ")].startDate");
					String assignmentStartDate = assignmentStartDates.get(0);
					String assignmentEndDate = Utilities.getFutureDate("MM-dd-yyyy", 90);
					paramValaues = Arrays.asList(positionId, openingId, srNumber, assignmentStartDate,
							assignmentEndDate);
					break;
				} 
			}
		}
		
		return paramValaues;
	}
	
	/**
	 * @author pooja.kakade
	 * 
	 * This method will confirm shadow resource
	 * 
	 * @param requestParams
	 * @return response 
	 * @throws Exception
	 */
	public Response postConfirmShadow(JSONObject requestParams) throws Exception {
		RestUtils restUtils = new RestUtils();
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(MyTeamBaseTest.sessionId).contentType(ContentType.JSON).body(requestParams.toString());
		
		String url = MyTeamBaseTest.baseUrl+ConfirmShadowEndpoints.confirmShadow;
		
		ExtentHelper.test.log(Status.INFO, "Request URL : "+url);
		Response response = restUtils.executePOST(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 201, "Unable to confirm shadow resource.", true);
		ExtentHelper.test.log(Status.INFO, "post confirm shadow method executed successfully");
		return response;
	}
	
	/**
	 * This method will fetch Position list for confirming shadow through SFDC Opening without creating SR
	 * 
	 * @param positionId
	 * @return Response
	 * @throws Exception
	 */
	public Response getPositionListForConfirmShadowThroughSfdcWithoutSr(int positionId) throws Exception {
		RestUtils restUtils = new RestUtils();
		
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId);
		String url = GlowBaseTest.baseUrl + String.format(ConfirmShadowEndpoints.getPostionListDetails, positionId);
		Response getPostionListResponse = restUtils.executeGET(requestSpecification, url);
		ExtentHelper.test.log(Status.INFO, "Request URL : "+url);
		new GlowBaseTest().validateResponseToContinueTest(getPostionListResponse, 200, "Unable to fetch Position List for Confirm Shadow for Sfdc openings.", true);
		
		ExtentHelper.test.log(Status.INFO, "get Position list for confirm shadow Sfdc without SR with PostionId method executed successfully");
		return getPostionListResponse;
	}	
	
	/**
	 * This method will return the required position list from DB for validating response of confirming shadow glober through sfdc opening without SR
	 * 
	 * @param postionId
	 * @return List<ConfirmShadowPositionDTOList>
	 * @throws Exception
	 */
	public List<ConfirmShadowPositionDTOList> getPositionListFromDbForConfirmShadow(int postionId) throws Exception {
		List<ConfirmShadowPositionDTOList> positionList = confirmShadowDBHelper.getPositionListByPositionId(postionId);
		ExtentHelper.test.log(Status.INFO, "Successfully fetched Position List from DB for confirming shadow for sfdc opening without SR");
		return positionList;
	}
	
	/**
	 * This method will replicate shadow for confirming shadow with SR
	 * 
	 * @param requestParams
	 * @return Response
	 * @throws Exception
	 */
	public Response replicateShadow(JSONObject requestParams) throws Exception {
		RestUtils restUtils = new RestUtils();
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(MyTeamBaseTest.sessionId).contentType(ContentType.JSON).body(requestParams.toString());
		String url = MyTeamBaseTest.baseUrl+ConfirmShadowEndpoints.replicateShadow;
		ExtentHelper.test.log(Status.INFO, "Request URL : "+url);
		Response response = restUtils.executePOST(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Unable to replicate shadow.", true);
		ExtentHelper.test.log(Status.INFO, "Replicate Shadow method executed successfully");
		return response;
	}
}