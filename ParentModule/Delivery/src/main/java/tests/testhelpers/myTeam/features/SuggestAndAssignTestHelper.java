package tests.testhelpers.myTeam.features;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

import com.aventstack.extentreports.Status;

import database.myTeam.features.SuggestAndAssignDBHelper;
import endpoints.myTeam.features.ReleaseAndAssignEndpoints;
import endpoints.myTeam.features.StaffRequestEndpoints;
import endpoints.myTeam.features.SuggestAndAssignEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.myTeam.features.EditAssignmentPayloadHelper;
import tests.testcases.myTeam.MyTeamBaseTest;
import tests.testhelpers.myTeam.MyTeamTestHelper;
import utils.ExtentHelper;
import utils.RestUtils;
import utils.Utilities;

/**
 * @author poonam.kadam
 *
 */
public class SuggestAndAssignTestHelper extends MyTeamTestHelper {

	int projectId, resultCode, globerId;
	String startDate = null;
	String endDate = null;
	String message = null;

	RequestSpecification requestSpecification;
	RestUtils restUtils = new RestUtils();
	SuggestAndAssignDBHelper suggestAssignDbHelper;

	public SuggestAndAssignTestHelper(String userName) throws Exception {
		super(userName);
		suggestAssignDbHelper = new SuggestAndAssignDBHelper();
	}

	/**
	 * This method will return required test data to perform quick suggest and assign
	 * 
	 * @param userName
	 * @param userId
	 * @return List<Object>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getRequiredTestDataForSuggestAndAssign(String userName, String userId) throws Exception {
		String planStartDate = null, assignmentStartDate = null, assignmentEndDate = null, tempAssignmentEndDate = null;
		int globalId = 0, positionId = 0, clientId = 0;
		List<Integer> positionIds = null;
		List<Integer> globerPositionIds = null;

		MyTeamProjectHelper myTeamProjectHelper = new MyTeamProjectHelper(userName);
		Response response = myTeamProjectHelper.getProjectListUnderPM(userId);
		List<Integer> projectIds = response.jsonPath().get("details.projectId");
		List<Integer> clientIds = response.jsonPath().get("details.clientId");

		// Search for open positions for all projects under PM
		for (int i = 0; i < projectIds.size(); i++) {
			projectId = projectIds.get(i);
			clientId = clientIds.get(0);

			response = getPositionDTOList(userId, projectId, clientId);
			positionIds = (List<Integer>) restUtils.getValueFromResponse(response,
					"$.details.positionDTOList[*].positionId");

			if (!positionIds.isEmpty())
				break;
		}

		if (!positionIds.isEmpty()) {
			positionId = positionIds.get(0);

			requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
			String globerDetailsUrl = MyTeamBaseTest.baseUrl
					+ String.format(ReleaseAndAssignEndpoints.getGloberDetails, projectId, true, true, true);
			ExtentHelper.test.log(Status.INFO, "Request URL : " + globerDetailsUrl);
			Response shadowResponse = restUtils.executeGET(requestSpecification, globerDetailsUrl);
			new MyTeamBaseTest().validateResponseToContinueTest(shadowResponse, 200, "Unable to fetch glober details",
					true);

			// Fetch Glober id : Find glober having future assigment end date, end his assignment today
			tempAssignmentEndDate = Utilities.getFutureDate("yyyy-MM-dd", 20);
			globerPositionIds = (List<Integer>) restUtils.getValueFromResponse(shadowResponse,
					"$.details[?(@.positionAssignmentType =='CONFIRMED' && @.tempAssignmentEndDate >= '"
							+ tempAssignmentEndDate + "')].positionId");

			int globerPositionId = Utilities.getRandomIdFromList(globerPositionIds);
			List<Integer> globerIds = (List<Integer>) restUtils.getValueFromResponse(shadowResponse,
					"$.details[?(@.positionId == " + globerPositionId + ")].globerId");

			globerId = globerIds.get(0);

			// Edit Assignment date
			EditAssignmentHelper editAssignmentHelper = new EditAssignmentHelper(userName);
			List<Object> dataForEditAssignment = Arrays.asList(projectId, globerId,
					Utilities.getTodayDate("dd-MM-yyyy"));
			JSONObject requestParams = new EditAssignmentPayloadHelper().editAssignmentPayload(dataForEditAssignment);
			response = editAssignmentHelper.editAssignment(requestParams);

			// Validate Response
			validateResponseToContinueTest(response, 201, "Unable to edit assignment.", true);
			
			planStartDate = Utilities.getTodayDate("dd-MM-yyyy");
			assignmentStartDate = Utilities.getFutureDate("dd-MM-yyyy", 0, 0, 1);
			assignmentEndDate = Utilities.getFutureDate("dd-MM-yyyy", 0, 6, 1);

			// Fetch Global id
			globalId = getGlobalIdFromDbForSuggestAssign(globerId);
		}

		List<Object> paramValaues = Arrays.asList("Glober", "AUTO", true, 2, globalId, positionId, globerId, "High",
				planStartDate, assignmentStartDate, assignmentEndDate, 100, "null");
		return paramValaues;

	}

	/**
	 * This method will return global id for quick suggest and assign
	 * 
	 * @param globerId
	 * @return int
	 * @throws Exception
	 */
	public int getGlobalIdFromDbForSuggestAssign(int globerId) throws Exception {
		int globalId = suggestAssignDbHelper.getGlobalIdByGloberId(globerId);
		ExtentHelper.test.log(Status.INFO, "Successfully fetched global id for quick suggest and assign");
		return globalId;
	}

	/***
	 * This method will return the position dto list for glober
	 * 
	 * @param userId
	 * @param projectId
	 * @param clientId
	 * @return Response
	 * @throws Exception
	 */
	public Response getPositionDTOList(String userId, int projectId, int clientId) throws Exception {
		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl
				+ String.format(StaffRequestEndpoints.getPositionDTOList, userId, 6, 6, projectId, clientId);
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200,
				"Unable to fetch Position DTO List list results", true);
		ExtentHelper.test.log(Status.INFO, "get Position DTO List method executed successfully");
		return response;
	}

	/**
	 * This method will initiate quick suggest and assign
	 * 
	 * @param requestParams
	 * @return Response
	 * @throws Exception
	 */
	public Response postSuggestAndAssign(JSONObject requestParams) throws Exception {
		RestUtils restUtils = new RestUtils();
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(MyTeamBaseTest.sessionId).contentType(ContentType.JSON).body(requestParams.toString());
		String url = MyTeamBaseTest.baseUrl + SuggestAndAssignEndpoints.quickSuggestAssign;
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executePOST(requestSpecification, url);
		ExtentHelper.test.log(Status.INFO, "post suggest and assign method executed successfully");
		return response;
	}
}