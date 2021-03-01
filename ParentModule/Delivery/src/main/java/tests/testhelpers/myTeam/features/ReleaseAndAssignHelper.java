package tests.testhelpers.myTeam.features;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

import com.aventstack.extentreports.Status;

import database.myTeam.MyTeamDBHelper;
import endpoints.myTeam.features.ReleaseAndAssignEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.myTeam.MyTeamBaseTest;
import tests.testhelpers.myTeam.MyTeamTestHelper;
import utils.ExtentHelper;
import utils.RestUtils;
import utils.Utilities;

/**
 * @author pooja.kakade
 *
 */
public class ReleaseAndAssignHelper extends MyTeamTestHelper {

	RequestSpecification requestSpecification;
	RestUtils restUtils = new RestUtils();

	public ReleaseAndAssignHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * @author pooja.kakade
	 * 
	 *         This method will return required test data to release and assign
	 * 
	 * @param userName
	 * @param userId
	 * @return String
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getRequiredTestDataForReleaseAndAssign(String userName, String userId) throws Exception {

		List<Object> paramValaues = null;
		List<Integer> projectIds = new MyTeamProjectHelper(userName).getProjectIdsOfGlober(userName, userId);

		int sourceProjectId = 0, globerId = 0, existingAssignmentId = 0, globerPositionId = 0, srNumber = 0,
				destinationProjectId = 0, positionId = 0;

		String globerName = null, globerEmail = null, existingAssignmentEndDate = null, globerPositionName = null,
				globerCity = null, globerCountry = null;
		String destinationProjectName = null, submitterId = null, newAssignmentEndDate = null, positionName = null,
				staffRequestStartDate = null, staffRequestEndDate = null, srHandlerEmail = null, location = null;

		for (int projectId : projectIds) {

			requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
			String globerDetailsUrl = MyTeamBaseTest.baseUrl
					+ String.format(ReleaseAndAssignEndpoints.getGloberDetails, projectId, true, true, true);
			ExtentHelper.test.log(Status.INFO, "Request URL : " + globerDetailsUrl);
			Response shadowResponse = restUtils.executeGET(requestSpecification, globerDetailsUrl);
			new MyTeamBaseTest().validateResponseToContinueTest(shadowResponse, 200, "Unable to fetch glober details",
					true);
			List<Integer> globerPositionIds = (List<Integer>) restUtils.getValueFromResponse(shadowResponse,
					"$.details[?(@.positionAssignmentType =='CONFIRMED' && @.assignmentPercentage == 100)].positionId");

			if (!globerPositionIds.isEmpty()) {

				sourceProjectId = projectId;

				globerPositionId = Utilities.getRandomIdFromList(globerPositionIds);
				List<Integer> globerIds = (List<Integer>) restUtils.getValueFromResponse(shadowResponse,
						"$.details[?(@.positionId == " + globerPositionId + ")].globerId");
				globerId = globerIds.get(0);

				List<Integer> existingAssignmentIds = (List<Integer>) restUtils.getValueFromResponse(shadowResponse,
						"$.details[?(@.positionId == " + globerPositionId + ")].assignmentId");
				existingAssignmentId = existingAssignmentIds.get(0);

				requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
				String validateGloberUrl = MyTeamBaseTest.baseUrl
						+ String.format(ReleaseAndAssignEndpoints.validateGlober, globerId, existingAssignmentId);
				ExtentHelper.test.log(Status.INFO, "Request URL : " + validateGloberUrl);
				Response validateResponse = restUtils.executeGET(requestSpecification, validateGloberUrl);

				if (validateResponse.getStatusCode() == 200) {

					List<String> globerFirstNames = (List<String>) restUtils.getValueFromResponse(shadowResponse,
							"$.details[?(@.positionId == " + globerPositionId + ")].globerName[0]");
					List<String> globerLastNames = (List<String>) restUtils.getValueFromResponse(shadowResponse,
							"$.details[?(@.positionId == " + globerPositionId + ")].globerName[1]");
					globerName = globerFirstNames.get(0) + " " + globerLastNames.get(0);

					List<String> globerEmails = (List<String>) restUtils.getValueFromResponse(shadowResponse,
							"$.details[?(@.positionId == " + globerPositionId + ")].email");
					globerEmail = globerEmails.get(0);

					MyTeamDBHelper dbHelper = new MyTeamDBHelper();
					existingAssignmentEndDate = dbHelper.getExistingAssignmentDate(existingAssignmentId);

					List<String> globerPositionNames = (List<String>) restUtils.getValueFromResponse(shadowResponse,
							"$.details[?(@.positionId == " + globerPositionId + ")].position");
					globerPositionName = globerPositionNames.get(0);

					List<String> globerCities = (List<String>) restUtils.getValueFromResponse(shadowResponse,
							"$.details[?(@.positionId == " + globerPositionId + ")].city");
					globerCity = globerCities.get(0);

					List<String> globerCountries = (List<String>) restUtils.getValueFromResponse(shadowResponse,
							"$.details[?(@.positionId == " + globerPositionId + ")].country");
					globerCountry = globerCountries.get(0);

					break;
				}
			}
		}

		for (int desId : projectIds) {

			if (desId != sourceProjectId) {

				int clientId = getClientIdByProjectId(userId, desId);

				requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);
				String url = MyTeamBaseTest.baseUrl
						+ String.format(ReleaseAndAssignEndpoints.getSrDetails, userId, "6", "6", desId, clientId);
				ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
				Response response = restUtils.executeGET(requestSpecification, url);
				new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch SR details", true);
				ExtentHelper.test.log(Status.INFO, "get SR details method executed successfully");

				List<Integer> positionIds = (List<Integer>) restUtils.getValueFromResponse(response,
						"$.details.positionDTOList[*].positionId");

				if (!positionIds.isEmpty()) {

					destinationProjectId = desId;

					positionId = Utilities.getRandomIdFromList(positionIds);

					List<String> destinationProjectNames = (List<String>) restUtils.getValueFromResponse(response,
							"$.details.positionDTOList[?(@.positionId == " + positionId + ")].projectName");
					destinationProjectName = destinationProjectNames.get(0);

					List<String> submitterIds = (List<String>) restUtils.getValueFromResponse(response,
							"$.details.positionDTOList[?(@.positionId == " + positionId + ")].submitterId");
					submitterId = submitterIds.get(0);

					newAssignmentEndDate = Utilities.getFutureDate("yyyy-MM-dd", 2);

					List<String> positionNames = (List<String>) restUtils.getValueFromResponse(response,
							"$.details.positionDTOList[?(@.positionId == " + positionId + ")].srPosition");
					positionName = positionNames.get(0);

					staffRequestStartDate = Utilities.getTodayDate("yyyy-MM-dd");

					staffRequestEndDate = Utilities.getFutureDate("yyyy-MM-dd", 30);

					List<Integer> srNumbers = (List<Integer>) restUtils.getValueFromResponse(response,
							"$.details.positionDTOList[?(@.positionId == " + positionId + ")].srNumber");
					srNumber = srNumbers.get(0);

					List<String> srHandlerEmails = (List<String>) restUtils.getValueFromResponse(response,
							"$.details.positionDTOList[?(@.positionId == " + positionId + ")].handlerEmail");
					srHandlerEmail = srHandlerEmails.get(0);

					List<String> locations = (List<String>) restUtils.getValueFromResponse(response,
							"$.details.positionDTOList[?(@.positionId == " + positionId + ")].location");
					location = locations.get(0);

					break;
				}
			}
		}
		paramValaues = Arrays.asList(sourceProjectId, globerId, globerName, globerEmail, existingAssignmentId,
				existingAssignmentEndDate, globerPositionName, globerPositionId, globerCity, globerCountry,
				destinationProjectId, destinationProjectName, submitterId, newAssignmentEndDate, positionId,
				positionName, staffRequestStartDate, staffRequestEndDate, srNumber, srHandlerEmail, location);
		return paramValaues;
	}

	/**
	 * @author pooja.kakade
	 * 
	 *         This method will initiate release and assign
	 * 
	 * @param requestParams
	 * @return response
	 * @throws Exception
	 */
	public Response postReleaseAndAssign(JSONObject requestParams) throws Exception {
		RestUtils restUtils = new RestUtils();
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(MyTeamBaseTest.sessionId).contentType(ContentType.JSON).body(requestParams.toString());

		String url = MyTeamBaseTest.baseUrl + ReleaseAndAssignEndpoints.releaseAndAssignGlober;
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executePOST(requestSpecification, url);
		ExtentHelper.test.log(Status.INFO, "post release and assign method executed successfully");
		return response;
	}

	/**
	 * @author pooja.kakade
	 * 
	 *         This method will initiate R & A for already existing R & A for a
	 *         glober
	 * 
	 * @param globerId, assignmentId
	 * @return response
	 * @throws Exception
	 */
	public Response getAlreadyExistingReleaseAndAssignGlober(int globerId, int assignmentId) throws Exception {

		RestUtils restUtils = new RestUtils();
		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);

		String validateGloberUrl = MyTeamBaseTest.baseUrl
				+ String.format(ReleaseAndAssignEndpoints.validateGlober, globerId, assignmentId);
		ExtentHelper.test.log(Status.INFO, "Request URL : " + validateGloberUrl);
		Response validateResponse = restUtils.executeGET(requestSpecification, validateGloberUrl);

		return validateResponse;
	}

	/**
	 * @author pooja.kakade
	 * 
	 *         This method will get Pending Notifications in R & A wizard
	 * 
	 * @param projectId
	 * @return response
	 * @throws Exception
	 */
	public Response getPendingNotificationReleaseAndAssign(int projectId) throws Exception {

		RestUtils restUtils = new RestUtils();
		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId);

		String url = MyTeamBaseTest.baseUrl
				+ String.format(ReleaseAndAssignEndpoints.pendingNotificationReleaseAndAssign, projectId, "INITIATED");
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);

		return response;
	}

	/**
	 * @author dnyaneshwar.waghmare
	 * 
	 *         This method will approve release and assign
	 * 
	 * @param requestParams
	 * @return response
	 * @throws Exception
	 */
	public Response approveReleaseAndAssign(JSONObject requestParams, int id) throws Exception {
		requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(MyTeamBaseTest.sessionId)
				.contentType(ContentType.JSON).body(requestParams.toString());
		String url = MyTeamBaseTest.baseUrl + String.format(ReleaseAndAssignEndpoints.approveReleaseAndAssign, id);
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executePUT(requestSpecification, url);
		validateResponseToContinueTest(response, 200, "Unable to approve R & A request", true);
		ExtentHelper.test.log(Status.INFO, "approve Release And Assign method executed successfully");
		return response;
	}
}