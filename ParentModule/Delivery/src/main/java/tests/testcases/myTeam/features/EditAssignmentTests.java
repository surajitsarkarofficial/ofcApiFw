package tests.testcases.myTeam.features;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import dataproviders.myTeam.features.EditAssignmentDataProviders;
import io.restassured.response.Response;
import payloads.myTeam.features.EditAssignmentPayloadHelper;
import tests.testcases.myTeam.MyTeamBaseTest;
import tests.testhelpers.myTeam.features.EditAssignmentHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;
import utils.RestUtils;
import utils.Utilities;

/**
 * @author imran.khan
 *
 */

public class EditAssignmentTests extends MyTeamBaseTest {
	String message = null;

	RestUtils restUtils = new RestUtils();

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("EditAssignmentTests");
	}

	/**
	 * This test will edit assignment for today's date
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = EditAssignmentDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void editAssignmentForTodaysDate(String userName, String userId) throws Exception {

		EditAssignmentHelper editAssignmentHelper = new EditAssignmentHelper(userName);
		String todayDate = Utilities.getTodayDate("dd-MM-yyyy");
		List<Object> dataForEditAssignment = editAssignmentHelper.getRequiredTestDataForEditAssignment(userName, userId,
				todayDate);
		JSONObject requestParams = new EditAssignmentPayloadHelper().editAssignmentPayload(dataForEditAssignment);
		Response response = editAssignmentHelper.editAssignment(requestParams);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 201, "Unable to edit assignment.", true);
		message = (String) restUtils.getValueFromResponse(response, "message");

		assertEquals(message, "Assignment end date updated successfully.", "Error with user name " + userName);
		test.log(Status.PASS, "Edit assignment date successfully");
	}

	/**
	 * This test will edit assignment for future date
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = EditAssignmentDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void editAssignmentForFutureDate(String userName, String userId) throws Exception {

		EditAssignmentHelper editAssignmentHelper = new EditAssignmentHelper(userName);
		String futureDate = Utilities.getFutureDate("dd-MM-yyyy", 4);
		List<Object> dataForEditAssignment = editAssignmentHelper.getRequiredTestDataForEditAssignment(userName, userId,
				futureDate);
		JSONObject requestParams = new EditAssignmentPayloadHelper().editAssignmentPayload(dataForEditAssignment);
		Response response = editAssignmentHelper.editAssignment(requestParams);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 201, "Unable to edit assignment.", true);
		message = (String) restUtils.getValueFromResponse(response, "message");

		assertEquals(message, "Assignment end date updated successfully.", "Error with user name " + userName);
		test.log(Status.PASS, "Edit assignment date successfully");
	}

	/**
	 * This test will edit assignment for past date
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = EditAssignmentDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void editAssignmentForPastDate(String userName, String userId) throws Exception {

		EditAssignmentHelper editAssignmentHelper = new EditAssignmentHelper(userName);
		String pastDate = Utilities.getYesterday("dd-MM-yyyy");
		List<Object> dataForEditAssignment = editAssignmentHelper.getRequiredTestDataForEditAssignment(userName, userId,
				pastDate);
		JSONObject requestParams = new EditAssignmentPayloadHelper().editAssignmentPayload(dataForEditAssignment);
		Response response = editAssignmentHelper.editAssignment(requestParams);
		int resultCode = (int) restUtils.getValueFromResponse(response, "resultCode");
		message = (String) restUtils.getValueFromResponse(response, "message");
		assertEquals(resultCode, 400, "The result code is not 400");
		assertEquals(message, "New end date should be greater than today", "Error with user name " + userName);
		test.log(Status.PASS, "New end date should be greater than today error message coming successfully");
	}

	/**
	 * This test will edit assignment for invalid projectId
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = EditAssignmentDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression})
	public void editAssignmentForInvalidProjectId(String userName, String userId) throws Exception {

		EditAssignmentHelper editAssignmentHelper = new EditAssignmentHelper(userName);
		String futureDate = Utilities.getTodayDate("dd-MM-yyyy");
		List<Object> dataForEditAssignment = editAssignmentHelper.getRequiredTestDataForEditAssignment(userName, userId,
				futureDate);
		dataForEditAssignment.set(0, -1);
		JSONObject requestParams = new EditAssignmentPayloadHelper().editAssignmentPayload(dataForEditAssignment);
		Response response = editAssignmentHelper.editAssignment(requestParams);
		int resultCode = (int) restUtils.getValueFromResponse(response, "resultCode");
		message = (String) restUtils.getValueFromResponse(response, "message");
		assertEquals(resultCode, 400, "The result code is not 400");
		assertEquals(message, "Invalid project id", "Error with user name " + userName);
		test.log(Status.PASS, "Invalid project id error message coming successfully");
	}

	/**
	 * This test will edit assignment for invalid globerId
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = EditAssignmentDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression})
	public void editAssignmentForInvalidGloberId(String userName, String userId) throws Exception {

		EditAssignmentHelper editAssignmentHelper = new EditAssignmentHelper(userName);
		String futureDate = Utilities.getTodayDate("dd-MM-yyyy");
		List<Object> dataForEditAssignment = editAssignmentHelper.getRequiredTestDataForEditAssignment(userName, userId,
				futureDate);
		dataForEditAssignment.set(1, -1);
		JSONObject requestParams = new EditAssignmentPayloadHelper().editAssignmentPayload(dataForEditAssignment);
		Response response = editAssignmentHelper.editAssignment(requestParams);
		int resultCode = (int) restUtils.getValueFromResponse(response, "resultCode");
		message = (String) restUtils.getValueFromResponse(response, "message");
		assertEquals(resultCode, 400, "The result code is not 400");
		assertEquals(message, "Invalid glober id", "Error with user name " + userName);
		test.log(Status.PASS, "Invalid glober id error message coming successfully");
	}

	/**
	 * This test will edit assignment for invalid end date
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = EditAssignmentDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression})
	public void editAssignmentForInvalidEndDate(String userName, String userId) throws Exception {

		EditAssignmentHelper editAssignmentHelper = new EditAssignmentHelper(userName);
		String futureDate = Utilities.getTodayDate("dd-MM-yyyy");
		List<Object> dataForEditAssignment = editAssignmentHelper.getRequiredTestDataForEditAssignment(userName, userId,
				futureDate);
		dataForEditAssignment.set(2, -1);
		JSONObject requestParams = new EditAssignmentPayloadHelper().editAssignmentPayload(dataForEditAssignment);
		Response response = editAssignmentHelper.editAssignment(requestParams);
		int resultCode = (int) restUtils.getValueFromResponse(response, "resultCode");
		message = (String) restUtils.getValueFromResponse(response, "message");
		assertEquals(resultCode, 500, "The result code is not 400");
		assertEquals(message, "Unparseable date: \"-1\"", "Error with user name " + userName);
		test.log(Status.PASS, "Unparseable date error message coming successfully");
	}

	/**
	 * This test will edit assignment for no projectId
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = EditAssignmentDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression})
	public void editAssignmentForNoProjectId(String userName, String userId) throws Exception {

		EditAssignmentHelper editAssignmentHelper = new EditAssignmentHelper(userName);
		String futureDate = Utilities.getTodayDate("dd-MM-yyyy");
		List<Object> dataForEditAssignment = editAssignmentHelper.getRequiredTestDataForEditAssignment(userName, userId,
				futureDate);
		dataForEditAssignment.set(0, "");
		JSONObject requestParams = new EditAssignmentPayloadHelper().editAssignmentPayload(dataForEditAssignment);
		Response response = editAssignmentHelper.editAssignment(requestParams);
		int resultCode = (int) restUtils.getValueFromResponse(response, "resultCode");
		message = (String) restUtils.getValueFromResponse(response, "message");
		assertEquals(resultCode, 400, "The result code is not 400");
		assertEquals(message, "Invalid project id", "Error with user name " + userName);
		test.log(Status.PASS, "Invalid project id error message coming successfully");
	}

	/**
	 * This test will edit assignment for no globerId
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = EditAssignmentDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression})
	public void editAssignmentForNoGloberId(String userName, String userId) throws Exception {

		EditAssignmentHelper editAssignmentHelper = new EditAssignmentHelper(userName);
		String futureDate = Utilities.getTodayDate("dd-MM-yyyy");
		List<Object> dataForEditAssignment = editAssignmentHelper.getRequiredTestDataForEditAssignment(userName, userId,
				futureDate);
		dataForEditAssignment.set(1, "");
		JSONObject requestParams = new EditAssignmentPayloadHelper().editAssignmentPayload(dataForEditAssignment);
		Response response = editAssignmentHelper.editAssignment(requestParams);
		int resultCode = (int) restUtils.getValueFromResponse(response, "resultCode");
		message = (String) restUtils.getValueFromResponse(response, "message");
		assertEquals(resultCode, 400, "The result code is not 400");
		assertEquals(message, "Invalid glober id", "Error with user name " + userName);
		test.log(Status.PASS, "Invalid glober id error message coming successfully");
	}

	/**
	 * This test will edit assignment for no end date
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = EditAssignmentDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression})
	public void editAssignmentForNoEndDate(String userName, String userId) throws Exception {

		EditAssignmentHelper editAssignmentHelper = new EditAssignmentHelper(userName);
		String futureDate = Utilities.getTodayDate("dd-MM-yyyy");
		List<Object> dataForEditAssignment = editAssignmentHelper.getRequiredTestDataForEditAssignment(userName, userId,
				futureDate);
		dataForEditAssignment.set(2, "");
		JSONObject requestParams = new EditAssignmentPayloadHelper().editAssignmentPayload(dataForEditAssignment);
		Response response = editAssignmentHelper.editAssignment(requestParams);
		int resultCode = (int) restUtils.getValueFromResponse(response, "resultCode");
		message = (String) restUtils.getValueFromResponse(response, "message");
		assertEquals(resultCode, 400, "The result code is not 400");
		assertEquals(message, "Invalid assignment end date", "Error with user name " + userName);
		test.log(Status.PASS, "Invalid assignment end date error message coming successfully");
	}

	/**
	 * This test will edit assignment for invalid action
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = EditAssignmentDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression})
	public void editAssignmentForInvalidAction(String userName, String userId) throws Exception {

		EditAssignmentHelper editAssignmentHelper = new EditAssignmentHelper(userName);
		String futureDate = Utilities.getTodayDate("dd-MM-yyyy");
		List<Object> dataForEditAssignment = editAssignmentHelper.getRequiredTestDataForEditAssignment(userName, userId,
				futureDate);
		dataForEditAssignment.set(2, "");
		JSONObject requestParams = new EditAssignmentPayloadHelper().editAssignmentPayload(dataForEditAssignment);
		requestParams.put("action", "test");
		Response response = editAssignmentHelper.editAssignment(requestParams);
		int resultCode = (int) restUtils.getValueFromResponse(response, "resultCode");
		message = (String) restUtils.getValueFromResponse(response, "message");
		assertEquals(resultCode, 500, "The result code is not 500");
		assertEquals(message, "Unknown service action: test", "Error with user name " + userName);
		test.log(Status.PASS, "Unknown service action test error message coming successfully");
	}

	/**
	 * This test will edit assignment for no action
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = EditAssignmentDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression})
	public void editAssignmentForNoAction(String userName, String userId) throws Exception {

		EditAssignmentHelper editAssignmentHelper = new EditAssignmentHelper(userName);
		String futureDate = Utilities.getTodayDate("dd-MM-yyyy");
		List<Object> dataForEditAssignment = editAssignmentHelper.getRequiredTestDataForEditAssignment(userName, userId,
				futureDate);
		dataForEditAssignment.set(2, "");
		JSONObject requestParams = new EditAssignmentPayloadHelper().editAssignmentPayload(dataForEditAssignment);
		requestParams.put("action", "");
		Response response = editAssignmentHelper.editAssignment(requestParams);
		int resultCode = (int) restUtils.getValueFromResponse(response, "resultCode");
		message = (String) restUtils.getValueFromResponse(response, "message");
		assertEquals(resultCode, 500, "The result code is not 500");
		assertEquals(message, "Unknown service action: ", "Error with user name " + userName);
		test.log(Status.PASS, "Unknown service action error message coming successfully");
	}
	
	/***
	 * This test case will get the assignments
	 * 
	 * @param userName
	 * @param userId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = EditAssignmentDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression})
	public void getAssignments(String userName, String userId) throws Exception {

		EditAssignmentHelper editAssignmentHelper = new EditAssignmentHelper(userName);
		Response response = editAssignmentHelper.getAssignment();
		validateResponseToContinueTest(response, 200, "Assignments list not returned successfully", true);

		Map<String,String> expectedAssignments=new HashedMap<String, String>();
		expectedAssignments.put("Budget issues", "BUDGET_ISSUES");
		expectedAssignments.put("Position closed", "POSITION_CLOSED");
		expectedAssignments.put("Project ended", "PROJECT_ENDED");
		expectedAssignments.put("Strategic client decision", "STRATEGIC_CLIENT_DECISION");
		expectedAssignments.put("Keep the current value", "KEEP_THE_CURRENT_VALUE");
		
		List<String> assignmentName = (List<String>) restUtils.getValueFromResponse(response,
				"$.details[*].name");
		assertTrue(expectedAssignments.size() == assignmentName.size(),
				"Assignments list does not Match with expected");
		
		for(int iTemp=0;iTemp<assignmentName.size();iTemp++)
		{
			String name=assignmentName.get(iTemp);
			List<String> value = (List<String>) restUtils.getValueFromResponse(response,
					"$.details[?(@.name=='"+name+"')].value");
			String expectedValue=expectedAssignments.get(assignmentName.get(iTemp));
			assertEquals(value.get(0), expectedValue,"Assignment value "+value.get(0)+" does not match with "+expectedValue);
		}
	}
}
