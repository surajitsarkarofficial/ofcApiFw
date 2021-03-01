package tests.testcases.myTeam.features;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import dataproviders.myTeam.features.StaffRequestDataProviders;
import io.restassured.response.Response;
import payloads.myTeam.features.StaffRequestPayloadHelper;
import tests.testcases.myTeam.MyTeamBaseTest;
import tests.testhelpers.myTeam.features.StaffRequestTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;
import utils.RestUtils;

/**
 * @author dnyaneshwar.waghmare
 *
 */

public class StaffRequestTests extends MyTeamBaseTest {
	String message, status, statusCode = null;
	int srNumber;
	boolean validData = false;
	Multimap<Integer,String> detailsArrayMapFromResponse = ArrayListMultimap.create();
	RestUtils restUtils = new RestUtils();

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("StaffRequestTests");
	}
	
	/**
	 * This test will create a staff request
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = StaffRequestDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void createStaffRequest(String userName, String userId) throws Exception {

		StaffRequestTestHelper staffRequestTestHelper = new StaffRequestTestHelper(userName);
		List<Object> dataForCheckoutGlober = staffRequestTestHelper.getRequiredTestDataForStaffRequest(userName, userId);
		JSONObject requestParams = new StaffRequestPayloadHelper().createStaffRequestPayload(dataForCheckoutGlober);
		Response response = staffRequestTestHelper.createStaffRequest(requestParams);
		validateResponseToContinueTest(response, 201, "Staff Request not created successfully", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		status = (String) restUtils.getValueFromResponse(response, "status");
		
		assertEquals(status, "success");
		assertTrue(message.contains("Success! You have successfully created Staff Request"),
				"The message is not success");
		test.log(Status.PASS, "Staff Request Request created successfully");
	}

	/**
	 * This test will create a staff request for Globant internal Positions
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = StaffRequestDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void createStaffRequestForInternalPositions(String userName, String userId) throws Exception {

		StaffRequestTestHelper staffRequestTestHelper = new StaffRequestTestHelper(userName);
		List<Object> dataForCheckoutGlober = staffRequestTestHelper.getRequiredTestDataForStaffRequestInternalPositions(userName, userId);
		JSONObject requestParams = new StaffRequestPayloadHelper().createStaffRequestPayloadInternalPositions(dataForCheckoutGlober);
		Response response = staffRequestTestHelper.createStaffRequest(requestParams);
		validateResponseToContinueTest(response, 201, "Staff Request not created successfully", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		status = (String) restUtils.getValueFromResponse(response, "status");
		srNumber = (int) restUtils.getValueFromResponse(response, "$.details.positionDTOs[0].srNumber");
		
		assertEquals(status, "success", "Failed in validating Status");
		assertTrue(message.contains("Success! You have successfully created Staff Request"),
				"The message is not success");		
		assertTrue(message.contains(String.valueOf(srNumber)),
				String.format("Excpected SRNumber %d was not Present in the message '%s'.", srNumber, message));
		
		test.log(Status.PASS, "Staff Request Request created successfully");
	}

	/**
	 * This test will click on create Staff Request link
	 * 
	 * @param userName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = StaffRequestDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void clickOnCreateStaffRequestLink(String userName, String userId) throws Exception {

		StaffRequestTestHelper staffRequestTestHelper = new StaffRequestTestHelper(userName);
		int dataForCreateStaffRequestLink = staffRequestTestHelper
				.getRequiredTestDataForCreateStaffRequestLink(userName, userId);

		// Pass dummy positionRoleId if test data is empty(-1)
		if (dataForCreateStaffRequestLink < 0)
			dataForCreateStaffRequestLink = 166725146;

		Response response = staffRequestTestHelper.clickOnCreateStaffRequestLink(dataForCreateStaffRequestLink);
		validateResponseToContinueTest(response, 200, "Unable to click on staff request link.", true);

		statusCode = (String) restUtils.getValueFromResponse(response, "statusCode");
		status = (String) restUtils.getValueFromResponse(response, "status");
		validData = (boolean) restUtils.getValueFromResponse(response, "validData");

		@SuppressWarnings("unchecked")
		List<Object> detailsArrayFromResponse = (List<Object>) restUtils.getValueFromResponse(response, "$.details[*]");

		Map<Integer, String> queryResponse = staffRequestTestHelper
				.getIdNamesByPositionRoleIdFromDB(dataForCreateStaffRequestLink);

		for (int i = 0; i < detailsArrayFromResponse.size(); i++) {
			int responseId = (int) response.jsonPath().getMap("details[" + i + "]").get("id");
			String responseName = (String) response.jsonPath().getMap("details[" + i + "]").get("name");
			detailsArrayMapFromResponse.put(responseId, responseName);
		}

		assertEquals(statusCode, "OK", "Failed in validating Status Code");
		assertEquals(status, "success", "Failed in validating Status");
		assertEquals(validData, true, "Failed in validating validData");

		assertEquals(detailsArrayMapFromResponse.size(), queryResponse.size(),
				"DB Response not matching with API Response");

		// Check if DB Query results matches with details[] elements returned by API
		for (Map.Entry<Integer, String> entry : queryResponse.entrySet()) {
			assertTrue(detailsArrayMapFromResponse.containsEntry(entry.getKey(), entry.getValue()),
					String.format("Excpected id : '%d' , name : '%s' is not Present in the details[].", entry.getKey(),
							entry.getValue()));
		}

		test.log(Status.PASS, "Create Staff Request link clicked successfully");
	}
	
	/**
	 * This test will edit the Staff Request
	 * 
	 * @param userName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = StaffRequestDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void editStaffRequest(String userName, String userId) throws Exception {
		boolean responseLoadEnabled, responseChangedSrStartDate, responseDeletedPlan;
		int requestPositionId, responsePositionId, srNumberToVerify;
		String requestAssignmentStartDate, requestAssignmentEndDate, responseAssignmentEndDate,
				responseAssignmentStartDate, responseClientName, responseProjectName, responsePosition,
				responseSeniority;

		StaffRequestTestHelper staffRequestTestHelper = new StaffRequestTestHelper(userName);
		List<Object> dataForEditSr = staffRequestTestHelper.getRequiredTestDataForEditingStaffRequest(userName, userId);
		JSONObject requestParams = new StaffRequestPayloadHelper().editStaffRequestPayload(dataForEditSr);
		Response response = staffRequestTestHelper.putStaffRequest(requestParams);
		validateResponseToContinueTest(response, 200, "Staff Request not edited successfully", true);

		// Fetch details for validating response
		statusCode = (String) restUtils.getValueFromResponse(response, "statusCode");
		status = (String) restUtils.getValueFromResponse(response, "status");
		responseLoadEnabled = (boolean) restUtils.getValueFromResponse(response, "$.details.loadEnabled");
		responseChangedSrStartDate = (boolean) restUtils.getValueFromResponse(response, "$.details.changedSRStartDate");
		responseDeletedPlan = (boolean) restUtils.getValueFromResponse(response, "$.details.deletedplan");
		srNumber = (int) restUtils.getValueFromResponse(response, "$.details.srNumber");
		requestPositionId = (int) dataForEditSr.get(21);
		responsePositionId = (int) restUtils.getValueFromResponse(response, "$.details.positionId");
		requestAssignmentStartDate = (String) dataForEditSr.get(12);
		requestAssignmentEndDate = (String) dataForEditSr.get(13);
		responseAssignmentStartDate = (String) restUtils.getValueFromResponse(response,
				"$.details.assignmentStartDate");
		responseAssignmentEndDate = (String) restUtils.getValueFromResponse(response, "$.details.assignmentEndDate");
		responseClientName = (String) restUtils.getValueFromResponse(response, "$.details.clientName");
		responseProjectName = (String) restUtils.getValueFromResponse(response, "$.details.projectName");
		responsePosition = (String) restUtils.getValueFromResponse(response, "$.details.position");
		responseSeniority = (String) restUtils.getValueFromResponse(response, "$.details.seniority");

		// Response assignmentStartDate & assignmentEndDate conversion for validation
		DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

		Date startDate = inputFormat.parse(responseAssignmentStartDate);
		responseAssignmentStartDate = outputFormat.format(startDate);

		Date endDate = inputFormat.parse(responseAssignmentEndDate);
		responseAssignmentEndDate = outputFormat.format(endDate);

		// Pass userId, request Project Id and Position Id to verify response SR No, clientName, projectName
		ArrayList<Object> dataForVerification = staffRequestTestHelper.getPositionDtoDetails(userId, dataForEditSr.get(20),
				dataForEditSr.get(21));
		srNumberToVerify = (int) dataForVerification.get(0);

		@SuppressWarnings("unchecked")
		List<Object> clientProjArray = (List<Object>) dataForVerification.get(1);

		@SuppressWarnings("unchecked")
		List<Object> positionSeniorityArray = (List<Object>) dataForVerification.get(2);

		// Validate Response
		assertEquals(statusCode, "OK", "Failed in validating status code");
		assertEquals(status, "success", "Failed in validating status");
		assertEquals(responseLoadEnabled, false, "Failed in validating load enabled");
		assertEquals(responseChangedSrStartDate, false, "Failed in validating changed SR start date");
		assertEquals(responseDeletedPlan, false, "Failed in validating deleted plan");
		assertEquals(requestPositionId, responsePositionId, "Failed in validating position Id");
		assertEquals(responseAssignmentStartDate, requestAssignmentStartDate,
				"Failed in validating assignment stard Date");
		assertEquals(responseAssignmentEndDate, requestAssignmentEndDate, "Failed in validating assignment end date");
		assertEquals(responseClientName, clientProjArray.get(0), "Failed in validating client name");
		assertEquals(responseProjectName, clientProjArray.get(1), "Failed in validating project name");
		assertEquals(responsePosition, positionSeniorityArray.get(0), "Failed in validating position");
		assertEquals(responseSeniority, positionSeniorityArray.get(1), "Failed in validating seniority");
		assertEquals(srNumber, srNumberToVerify, "Failed in validating srNumber");

		test.log(Status.PASS, "Excpected SRNumber " + srNumberToVerify + " was Present in response details[]");
		test.log(Status.PASS, "Staff Request edited successfully");
	}
}
