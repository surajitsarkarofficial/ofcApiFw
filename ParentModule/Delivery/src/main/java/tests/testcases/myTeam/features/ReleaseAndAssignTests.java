package tests.testcases.myTeam.features;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.testng.Assert.assertEquals;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.jayway.jsonpath.JsonPath;

import dataproviders.myTeam.MyTeamDataProviders;
import io.restassured.response.Response;
import payloads.myTeam.features.ReleaseAndAssignPayloadHelper;
import tests.testcases.myTeam.MyTeamBaseTest;
import tests.testhelpers.myTeam.features.ReleaseAndAssignHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;
import utils.RestUtils;

/**
 * @author pooja.kakade
 *
 */

public class ReleaseAndAssignTests extends MyTeamBaseTest {

	RestUtils restUtils = new RestUtils();
	String message, status = null;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("RealeaseAndAssignTests");
	}

	/***
	 * @author pooja.kakade
	 * 
	 * This test will release and assign glober
	 * 
	 * @param userName
	 * @param userId
	 * @throws Exception
	 *
	 */

	@Test(dataProvider = "pmRolDetailsJayantP", dataProviderClass = MyTeamDataProviders.class, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void releaseAndAssign(String userName, String userId) throws Exception {

		SoftAssert softAssert = new SoftAssert();
		ReleaseAndAssignHelper realeaseAndAssignHelper = new ReleaseAndAssignHelper(userName);
		List<Object> dataForReleaseAndAssign = realeaseAndAssignHelper.getRequiredTestDataForReleaseAndAssign(userName,
				userId);

		JSONObject requestParams = new ReleaseAndAssignPayloadHelper()
				.createReleaseAndAssignPayload(dataForReleaseAndAssign);
		Response response = realeaseAndAssignHelper.postReleaseAndAssign(requestParams);

		new MyTeamBaseTest().validateResponseToContinueTest(response, 201, "Unable to post release and assign .", true);

		String message = (String) restUtils.getValueFromResponse(response, "message");
		String statusCode = (String) restUtils.getValueFromResponse(response, "statusCode");
		String globerPositionName = (String) restUtils.getValueFromResponse(response, "$.details.globePositionName");
		String globerEmail = (String) restUtils.getValueFromResponse(response, "$.details.globerEmail");
		int globerPositionId = (Integer) restUtils.getValueFromResponse(response, "$.details.globerPositionId");
		int destinationProjectId = (Integer) restUtils.getValueFromResponse(response, "$.details.destinationProjectId");
		String destinationProjectName = (String) restUtils.getValueFromResponse(response,
				"$.details.destinationProjectName");
		int srNumber = (Integer) restUtils.getValueFromResponse(response, "$.details.srNumber");
		String status = (String) restUtils.getValueFromResponse(response, "$.details.status");

		softAssert.assertEquals(message, "success","Response message is not success");
		softAssert.assertEquals(statusCode, "CREATED","Response statusCode is not CREATED");
		softAssert.assertEquals(status, "INITIATED","Response status is not INITIATED");
		softAssert.assertEquals(globerPositionName,
				(String) JsonPath.read(requestParams.toString(), "globerPositionName"));
		softAssert.assertEquals(globerEmail, (String) JsonPath.read(requestParams.toString(), "globerEmail"));
		softAssert.assertEquals(globerPositionId, (int) JsonPath.read(requestParams.toString(), "globerPositionId"));
		softAssert.assertEquals(destinationProjectId,
				(int) JsonPath.read(requestParams.toString(), "destinationProjectId"));
		softAssert.assertEquals(destinationProjectName,
				(String) JsonPath.read(requestParams.toString(), "destinationProjectName"));
		softAssert.assertEquals(srNumber, (int) JsonPath.read(requestParams.toString(), "srNumber"));
		softAssert.assertAll();
	}

	/***
	 * @author pooja.kakade
	 * 
	 * This test will initiate R & A for a already existing R & A for a glober
	 * 
	 * @param userName
	 * @param userId
	 * @throws Exception
	 *
	 */

	@Test(dataProvider = "pmRolDetailsJayantP", dataProviderClass = MyTeamDataProviders.class, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void alreadyExistingReleaseAndAssignGlober(String userName, String userId) throws Exception {

		SoftAssert softAssert = new SoftAssert();
		ReleaseAndAssignHelper realeaseAndAssignHelper = new ReleaseAndAssignHelper(userName);
		List<Object> dataForReleaseAndAssign = realeaseAndAssignHelper.getRequiredTestDataForReleaseAndAssign(userName,
				userId);

		JSONObject requestParams = new ReleaseAndAssignPayloadHelper()
				.createReleaseAndAssignPayload(dataForReleaseAndAssign);
		Response response = realeaseAndAssignHelper.postReleaseAndAssign(requestParams);
		validateResponseToContinueTest(response, 201, "Unable to post release and assign.", true);

		int globerId = (Integer) restUtils.getValueFromResponse(response, "$.details.globerId");
		int assignmentId = (Integer) restUtils.getValueFromResponse(response, "$.details.existingAssignmentId");

		Response validateResponse = realeaseAndAssignHelper.getAlreadyExistingReleaseAndAssignGlober(globerId,
				assignmentId);
		validateResponseToContinueTest(validateResponse, 500, "release and assign not already initilised", true);

		String statusCode = (String) restUtils.getValueFromResponse(validateResponse, "statusCode");
		String status = (String) restUtils.getValueFromResponse(validateResponse, "status");
		String message = (String) restUtils.getValueFromResponse(validateResponse, "message");
		String details = (String) restUtils.getValueFromResponse(validateResponse, "details");

		softAssert.assertEquals(statusCode, "INTERNAL_SERVER_ERROR","statusCode is not matching with INTERNAL_SERVER_ERROR");
		softAssert.assertEquals(status, "fail","Response status is not fail");
		softAssert.assertEquals(message, "fail","Response message is not fail");
		softAssert.assertEquals(details, "EXISTS_PENDING_RA_REQUEST","Response details is not EXISTS_PENDING_RA_REQUEST");
		softAssert.assertAll();
	}

	/***
	 * @author pooja.kakade
	 * 
	 * This test will get Pending Notifications in R & A wizard
	 * 
	 * @param userName
	 * @param userId
	 * @throws Exception
	 *
	 */

	@SuppressWarnings("unchecked")
	@Test(dataProvider = "pmRolDetailsJayantP", dataProviderClass = MyTeamDataProviders.class, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void pendingNotificationInReleaseAndAssign(String userName, String userId) throws Exception {

		SoftAssert softAssert = new SoftAssert();
		ReleaseAndAssignHelper realeaseAndAssignHelper = new ReleaseAndAssignHelper(userName);
		List<Object> dataForReleaseAndAssign = realeaseAndAssignHelper.getRequiredTestDataForReleaseAndAssign(userName,
				userId);

		JSONObject requestParams = new ReleaseAndAssignPayloadHelper()
				.createReleaseAndAssignPayload(dataForReleaseAndAssign);
		Response response = realeaseAndAssignHelper.postReleaseAndAssign(requestParams);
		validateResponseToContinueTest(response, 201, "Unable to post release and assign .", true);

		int globerId = (Integer) restUtils.getValueFromResponse(response, "$.details.globerId");
		int destinationProjectId = (Integer) restUtils.getValueFromResponse(response, "$.details.destinationProjectId");

		Response notificationResponse = realeaseAndAssignHelper
				.getPendingNotificationReleaseAndAssign(destinationProjectId);
		validateResponseToContinueTest(notificationResponse, 200, "Failed to get pending notification", true);

		String statusCode = (String) restUtils.getValueFromResponse(notificationResponse, "statusCode");
		String status = (String) restUtils.getValueFromResponse(notificationResponse, "status");
		String message = (String) restUtils.getValueFromResponse(notificationResponse, "message");

		List<String> globePositionNames = (List<String>) restUtils.getValueFromResponse(notificationResponse,
				"$.details[?(@.globerId == " + globerId + ")].globePositionName");
		String globePositionName = globePositionNames.get(0);

		List<String> globerNames = (List<String>) restUtils.getValueFromResponse(notificationResponse,
				"$.details[?(@.globerId == " + globerId + ")].globerName");
		String globerName = globerNames.get(0);

		List<String> globerEmails = (List<String>) restUtils.getValueFromResponse(notificationResponse,
				"$.details[?(@.globerId == " + globerId + ")].globerEmail");
		String globerEmail = globerEmails.get(0);

		List<String> submitterNames = (List<String>) restUtils.getValueFromResponse(notificationResponse,
				"$.details[?(@.globerId == " + globerId + ")].submitterName");
		String submitterName = submitterNames.get(0);

		List<String> statuses = (List<String>) restUtils.getValueFromResponse(notificationResponse,
				"$.details[?(@.globerId == " + globerId + ")].status");
		String requestStatus = statuses.get(0);

		List<Integer> srNumbers = (List<Integer>) restUtils.getValueFromResponse(notificationResponse,
				"$.details[?(@.globerId == " + globerId + ")].srNumber");
		int srNumber = srNumbers.get(0);

		softAssert.assertEquals(statusCode, "OK","Response statusCode is not OK");
		softAssert.assertEquals(status, "success","Response status is not success");
		softAssert.assertEquals(message, "success","Response message is not success");
		softAssert.assertEquals(requestStatus, "INITIATED","Response requestStatus is not INITIATED");
		softAssert.assertEquals((String) restUtils.getValueFromResponse(response, "$.details.globePositionName"),
				globePositionName);
		softAssert.assertEquals((String) restUtils.getValueFromResponse(response, "$.details.globerName"), globerName);
		softAssert.assertEquals((String) restUtils.getValueFromResponse(response, "$.details.globerEmail"),
				globerEmail);
		softAssert.assertEquals((String) restUtils.getValueFromResponse(response, "$.details.submitterName"),
				submitterName);
		softAssert.assertEquals((Integer) restUtils.getValueFromResponse(response, "$.details.srNumber"), srNumber);
		softAssert.assertAll();
	}

	/***
	 * @author dnyaneshwar.waghmare
	 * 
	 * This test will approve release and assign through release and assign wizard
	 * 
	 * @param userName
	 * @param userId
	 * @throws Exception
	 *
	 */
	@Test(dataProvider = "pmRolDetailsJayantP", dataProviderClass = MyTeamDataProviders.class, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void approveReleaseAndAssignThroughWizard(String userName, String userId) throws Exception {

		ReleaseAndAssignHelper realeaseAndAssignHelper = new ReleaseAndAssignHelper(userName);
		List<Object> dataForReleaseAndAssign = realeaseAndAssignHelper.getRequiredTestDataForReleaseAndAssign(userName,
				userId);

		JSONObject requestParams = new ReleaseAndAssignPayloadHelper()
				.createReleaseAndAssignPayload(dataForReleaseAndAssign);
		Response response = realeaseAndAssignHelper.postReleaseAndAssign(requestParams);
		validateResponseToContinueTest(response, 201, "Unable to post release and assign .", true);

		int id = (Integer) restUtils.getValueFromResponse(response, "$.details.id");

		Map<String, Object> approveRnAParam = new HashMap<String, Object>();
		approveRnAParam.put("status", "APPROVED");
		approveRnAParam.put("channel", "WEB");
		JSONObject josnApproveReleaseAndAssign = new JSONObject(approveRnAParam);
		Response approveResponse = realeaseAndAssignHelper.approveReleaseAndAssign(josnApproveReleaseAndAssign, id);
		message = (String) restUtils.getValueFromResponse(approveResponse, "message");
		status = (String) restUtils.getValueFromResponse(approveResponse, "status");
		assertEquals(message, "success","Response message is not success");
		assertEquals(status, "success","Response status is not success");
	}
}