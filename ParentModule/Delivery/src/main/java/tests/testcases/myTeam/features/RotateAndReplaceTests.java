package tests.testcases.myTeam.features;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import dataproviders.myTeam.features.RotateAndReplaceDataProviders;
import io.restassured.response.Response;
import payloads.myTeam.features.RotateAndReplacePayloadHelper;
import tests.testcases.myTeam.MyTeamBaseTest;
import tests.testhelpers.myTeam.features.RotateAndReplaceTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;
import utils.RestUtils;

/**
 * @author imran.khan
 *
 */

public class RotateAndReplaceTests extends MyTeamBaseTest {
	String message, status = null;
	int resultCode;
	RestUtils restUtils = new RestUtils();

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("RotateAndReplaceTests");
	}

	/**
	 * This test will get replacement reasons
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = RotateAndReplaceDataProviders.class, enabled = true, priority = 0, groups = ExeGroups.Regression)
	public void getReplacementReasons(String userName, String userId) throws Exception {

		RotateAndReplaceTestHelper rotateAndReplaceHelper = new RotateAndReplaceTestHelper(userName);
		Map<String, Object> replacementReasonFromAPI = rotateAndReplaceHelper.getAllReplacementReasons();
		Map<String, Object> replacementReasonFromDB = rotateAndReplaceHelper.getReplacementReasonFromDB();

		assertEquals(replacementReasonFromDB.toString(), replacementReasonFromAPI.toString(),
				"Failed in getting replacement reasons");
		test.log(Status.PASS, "Replacement Reasons are same from API and Database");

	}

	/**
	 * This test will create replacement
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = RotateAndReplaceDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void createReplacementTest(String userName, String userId) throws Exception {

		RotateAndReplaceTestHelper rotateAndReplaceHelper = new RotateAndReplaceTestHelper(userName);
		List<Object> dataForReplaceGlober = rotateAndReplaceHelper.getReplaceGloberTestData(userName, userId);
		JSONObject requestParams = new RotateAndReplacePayloadHelper().createReplacePayload(dataForReplaceGlober);
		Response response = rotateAndReplaceHelper.postReplaceGlober(requestParams);
		message = (String) restUtils.getValueFromResponse(response, "message");
		status = (String) restUtils.getValueFromResponse(response, "status");

		assertTrue(message.contains("Success! You have successfully created Staff Request"),
				"Failed in validating message");
		assertEquals(status, "success", "Failed in validating Status");
		test.log(Status.PASS, "You have successfully created Staff Request");

		int srNumberToVerify = rotateAndReplaceHelper.verifyCreatedSRForReplacedGlober(requestParams.get("globerId"),
				requestParams.get("projectId"));

		assertTrue(message.contains(String.valueOf(srNumberToVerify)),
				String.format("Excpected SRNumber %d was not Present in the message '%s'.", srNumberToVerify, message));
		test.log(Status.PASS, "Excpected SRNumber " + srNumberToVerify + " was Present in the message");
	}

	/**
	 * This test will create replacement with shadow glober
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = RotateAndReplaceDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void createReplacementTestWithShadowGlober(String userName, String userId) throws Exception {

		RotateAndReplaceTestHelper rotateAndReplaceHelper = new RotateAndReplaceTestHelper(userName);
		List<Object> dataForReplaceGlober = rotateAndReplaceHelper.getReplaceGloberTestDataForShadow(userName, userId);
		JSONObject requestParams = new RotateAndReplacePayloadHelper().createReplacePayload(dataForReplaceGlober);
		Response response = rotateAndReplaceHelper.postReplaceGlober(requestParams);
		message = (String) restUtils.getValueFromResponse(response, "message");
		status = (String) restUtils.getValueFromResponse(response, "status");

		assertTrue(message.contains("Success! You have successfully created Staff Request"),
				"Failed in validating message");
		assertEquals(status, "success", "Failed in validating Status");
		test.log(Status.PASS, "You have successfully created Staff Request");

		int srNumberToVerify = rotateAndReplaceHelper.verifyCreatedSRForReplacedGlober(requestParams.get("globerId"),
				requestParams.get("projectId"));

		assertTrue(message.contains(String.valueOf(srNumberToVerify)),
				String.format("Excpected SRNumber %d was not Present in the message '%s'.", srNumberToVerify, message));
		test.log(Status.PASS, "Excpected SRNumber " + srNumberToVerify + " was Present in the message");

	}

	/**
	 * This test will create replacement with invalid reason
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = RotateAndReplaceDataProviders.class, enabled = true, priority = 0, groups = ExeGroups.Regression)
	public void createReplacementWithInvalidReason(String userName, String userId) throws Exception {

		RotateAndReplaceTestHelper rotateAndReplaceHelper = new RotateAndReplaceTestHelper(userName);
		List<Object> dataForReplaceGlober = rotateAndReplaceHelper.getReplaceGloberTestData(userName, userId);
		dataForReplaceGlober.set(6, -1);
		JSONObject requestParams = new RotateAndReplacePayloadHelper().createReplacePayload(dataForReplaceGlober);
		Response response = rotateAndReplaceHelper.postReplaceGloberInvalidData(requestParams);
		message = (String) restUtils.getValueFromResponse(response, "message");
		status = (String) restUtils.getValueFromResponse(response, "status");

		assertTrue(status.equalsIgnoreCase("fail"), "status should be fail");
		assertTrue(message.equalsIgnoreCase("Invalid Replacement Reason."), "The error message is not correct");
		test.log(Status.PASS, "Invalid Replacement Reason message is coming as expected");
	}

	/**
	 * This test will create replacement with invalid glober id
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = RotateAndReplaceDataProviders.class, enabled = true, priority = 0, groups = ExeGroups.Regression)
	public void createReplacementWithInvalidGloberId(String userName, String userId) throws Exception {

		RotateAndReplaceTestHelper rotateAndReplaceHelper = new RotateAndReplaceTestHelper(userName);
		List<Object> dataForReplaceGlober = rotateAndReplaceHelper.getReplaceGloberTestData(userName, userId);
		dataForReplaceGlober.set(1, 15);
		JSONObject requestParams = new RotateAndReplacePayloadHelper().createReplacePayload(dataForReplaceGlober);
		Response response = rotateAndReplaceHelper.postReplaceGloberInvalidData(requestParams);
		message = (String) restUtils.getValueFromResponse(response, "message");
		status = (String) restUtils.getValueFromResponse(response, "status");

		assertTrue(status.equalsIgnoreCase("fail"), "status should be fail");
		assertTrue(message.equalsIgnoreCase("No Assignment Found For Glober"), "The error message is not correct");
		test.log(Status.PASS, "No Assignment Found For Glober message is coming as expected");
	}

	/**
	 * This test will create replacement with invalid position id
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = RotateAndReplaceDataProviders.class, enabled = true, priority = 0, groups = ExeGroups.Regression)
	public void createReplacementWithInvalidPositionId(String userName, String userId) throws Exception {

		RotateAndReplaceTestHelper rotateAndReplaceHelper = new RotateAndReplaceTestHelper(userName);
		List<Object> dataForReplaceGlober = rotateAndReplaceHelper.getReplaceGloberTestData(userName, userId);
		dataForReplaceGlober.set(3, 15);
		JSONObject requestParams = new RotateAndReplacePayloadHelper().createReplacePayload(dataForReplaceGlober);
		Response response = rotateAndReplaceHelper.postReplaceGloberInvalidData(requestParams);
		message = (String) restUtils.getValueFromResponse(response, "message");
		status = (String) restUtils.getValueFromResponse(response, "status");

		assertTrue(status.equalsIgnoreCase("fail"), "status should be fail");
		assertTrue(message.equalsIgnoreCase("No Assignment Found For Glober"), "The error message is not correct");
		test.log(Status.PASS, "No Assignment Found For Glober message is coming as expected");
	}

	/**
	 * This test will create replacement with invalid position id
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = RotateAndReplaceDataProviders.class, enabled = true, priority = 0, groups = ExeGroups.Regression)
	public void createReplacementWithInvalidProjectId(String userName, String userId) throws Exception {

		RotateAndReplaceTestHelper rotateAndReplaceHelper = new RotateAndReplaceTestHelper(userName);
		List<Object> dataForReplaceGlober = rotateAndReplaceHelper.getReplaceGloberTestData(userName, userId);
		dataForReplaceGlober.set(5, 15);
		JSONObject requestParams = new RotateAndReplacePayloadHelper().createReplacePayload(dataForReplaceGlober);
		Response response = rotateAndReplaceHelper.postReplaceGloberInvalidData(requestParams);
		message = (String) restUtils.getValueFromResponse(response, "message");
		status = (String) restUtils.getValueFromResponse(response, "status");

		assertTrue(status.equalsIgnoreCase("fail"), "status should be fail");
		assertTrue(message.equalsIgnoreCase("No Assignment Found For Glober"), "The error message is not correct");
		test.log(Status.PASS, "No Assignment Found For Glober message is coming as expected");
	}

	/**
	 * This test will create replacement with invalid start date
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = RotateAndReplaceDataProviders.class, enabled = true, priority = 0, groups = ExeGroups.Regression)
	public void createReplacementWithInvalidStartDate(String userName, String userId) throws Exception {

		RotateAndReplaceTestHelper rotateAndReplaceHelper = new RotateAndReplaceTestHelper(userName);
		List<Object> dataForReplaceGlober = rotateAndReplaceHelper.getReplaceGloberTestData(userName, userId);
		dataForReplaceGlober.set(7, 15);
		JSONObject requestParams = new RotateAndReplacePayloadHelper().createReplacePayload(dataForReplaceGlober);
		Response response = rotateAndReplaceHelper.postReplaceGloberInvalidData(requestParams);
		message = (String) restUtils.getValueFromResponse(response, "statusCode");
		status = (String) restUtils.getValueFromResponse(response, "status");

		assertTrue(status.equalsIgnoreCase("fail"), "status should be fail");
		assertTrue(message.equalsIgnoreCase("INTERNAL_SERVER_ERROR"), "The error message is not correct");
		test.log(Status.PASS, "INTERNAL_SERVER_ERROR message is coming as expected");
	}

	/**
	 * This test will rotate glober for future date
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = RotateAndReplaceDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void createRotateTestWithFutureDate(String userName, String userId) throws Exception {

		RotateAndReplaceTestHelper rotateAndReplaceHelper = new RotateAndReplaceTestHelper(userName);
		List<Object> dataForRotateGlober = rotateAndReplaceHelper.getRequiredTestDataForRotate(userName, userId,
				"futureDate");
		JSONObject requestParams = new RotateAndReplacePayloadHelper().createRotatePayload(dataForRotateGlober);
		Response response = rotateAndReplaceHelper.postRotateGlober(requestParams);
		validateResponseToContinueTest(response, 201, "Rotated position not sucessful", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		resultCode = (Integer) restUtils.getValueFromResponse(response, "resultCode");
		String rotationStartDate = (String) dataForRotateGlober.get(3);
		
		assertEquals(resultCode, 201, "Failed in validating resultCode");
		assertEquals(message, "Rotated position sucessfully", "Failed in validating message");
		test.log(Status.PASS, "You have Rotated position sucessfully");

		String endDate = rotateAndReplaceHelper.verifyEndDateOfRotatedGlober(userName, dataForRotateGlober.get(5),
				dataForRotateGlober.get(6), dataForRotateGlober.get(3));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(endDate));
		c.add(Calendar.DATE, 1);
		String finaldt = sdf.format(c.getTime());
		assertEquals(finaldt, rotationStartDate, "End Date passed in request payload is not same for rotated glober");
		test.log(Status.PASS, "Rotated glober end date is verified successfully");
	}

	/**
	 * This test will rotate glober for past date
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = RotateAndReplaceDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression })
	public void createRotateTestWithPastDate(String userName, String userId) throws Exception {

		RotateAndReplaceTestHelper rotateAndReplaceHelper = new RotateAndReplaceTestHelper(userName);
		List<Object> dataForRotateGlober = rotateAndReplaceHelper.getRequiredTestDataForRotate(userName, userId,
				"pastDate");
		JSONObject requestParams = new RotateAndReplacePayloadHelper().createRotatePayload(dataForRotateGlober);
		Response response = rotateAndReplaceHelper.postRotateGloberForInvalidInput(requestParams);
		validateResponseToContinueTest(response, 400, "Rotated position sucessful", true);
		resultCode = (Integer) restUtils.getValueFromResponse(response, "resultCode");
		message = (String) restUtils.getValueFromResponse(response, "message");

		assertEquals(resultCode, 400, "Failed in validating resultCode");
		assertEquals(message, "Start date should be greater than today", "Failed in validating message");
		test.log(Status.PASS, "Start date should be greater than today message is coming as expected");
	}

	/**
	 * This test will rotate glober for today date
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = RotateAndReplaceDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression })
	public void createRotateTestWithTodaysDate(String userName, String userId) throws Exception {

		RotateAndReplaceTestHelper rotateAndReplaceHelper = new RotateAndReplaceTestHelper(userName);
		List<Object> dataForRotateGlober = rotateAndReplaceHelper.getRequiredTestDataForRotate(userName, userId,
				"todayDate");
		JSONObject requestParams = new RotateAndReplacePayloadHelper().createRotatePayload(dataForRotateGlober);
		Response response = rotateAndReplaceHelper.postRotateGloberForInvalidInput(requestParams);
		validateResponseToContinueTest(response, 400, "Rotate glober sucessful", true);

		resultCode = (Integer) restUtils.getValueFromResponse(response, "resultCode");
		message = (String) restUtils.getValueFromResponse(response, "message");

		assertEquals(resultCode, 400, "Failed in validating resultCode");
		assertEquals(message, "Start date should be greater than today", "Failed in validating message");
		test.log(Status.PASS, "Start date should be greater than today message is coming as expected");
	}

	/**
	 * This test will rotate glober for already rotated glober
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = RotateAndReplaceDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression })
	public void testRotateGloberForRotatedGlober(String userName, String userId) throws Exception {

		RotateAndReplaceTestHelper rotateAndReplaceHelper = new RotateAndReplaceTestHelper(userName);
		List<Object> dataForRotateGlober = rotateAndReplaceHelper.getRequiredTestDataForRotate(userName, userId,
				"futureDate");
		JSONObject requestParams = new RotateAndReplacePayloadHelper().createRotatePayload(dataForRotateGlober);
		Response response = rotateAndReplaceHelper.postRotateGlober(requestParams);

		resultCode = (Integer) restUtils.getValueFromResponse(response, "resultCode");
		message = (String) restUtils.getValueFromResponse(response, "message");

		assertEquals(resultCode, 201, "Failed in validating resultCode");
		assertEquals(message, "Rotated position sucessfully", "Failed in validating message");
		test.log(Status.PASS, "You have Rotated position sucessfully");

		response = rotateAndReplaceHelper.postRotateGloberForInvalidInput(requestParams);

		resultCode = (Integer) restUtils.getValueFromResponse(response, "resultCode");
		message = (String) restUtils.getValueFromResponse(response, "message");

		assertEquals(resultCode, 400, "Failed in validating resultCode");
		assertEquals(message, "Start date should be less that current assignment end date",
				"Failed in validating message");
		test.log(Status.PASS, "Rotate Glober for already rotated test executed successfully");

	}

	/**
	 * This test will rotate glober for invalid date
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = RotateAndReplaceDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression })
	public void createRotateTestWithInvalidDate(String userName, String userId) throws Exception {

		RotateAndReplaceTestHelper rotateAndReplaceHelper = new RotateAndReplaceTestHelper(userName);
		List<Object> dataForRotateGlober = rotateAndReplaceHelper.getRequiredTestDataForRotate(userName, userId,
				"pastDate");
		dataForRotateGlober.set(3, 79);
		JSONObject requestParams = new RotateAndReplacePayloadHelper().createRotatePayload(dataForRotateGlober);
		Response response = rotateAndReplaceHelper.postRotateGloberForInvalidInput(requestParams);

		resultCode = (Integer) restUtils.getValueFromResponse(response, "resultCode");
		message = (String) restUtils.getValueFromResponse(response, "message");

		assertEquals(resultCode, 500, "Failed in validating resultCode");
		assertEquals(message, "Unparseable date: \"79\"", "Failed in validating message");
		test.log(Status.PASS, "Unparseable date error message is coming as expected");
	}

	/**
	 * This test will rotate glober for invalid globerId
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = RotateAndReplaceDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression })
	public void createRotateTestWithInvalidGloberId(String userName, String userId) throws Exception {

		RotateAndReplaceTestHelper rotateAndReplaceHelper = new RotateAndReplaceTestHelper(userName);
		List<Object> dataForRotateGlober = rotateAndReplaceHelper.getRequiredTestDataForRotate(userName, userId,
				"pastDate");
		dataForRotateGlober.set(5, 79);
		JSONObject requestParams = new RotateAndReplacePayloadHelper().createRotatePayload(dataForRotateGlober);
		Response response = rotateAndReplaceHelper.postRotateGloberForInvalidInput(requestParams);

		resultCode = (Integer) restUtils.getValueFromResponse(response, "resultCode");
		message = (String) restUtils.getValueFromResponse(response, "message");

		assertEquals(resultCode, 400, "Failed in validating resultCode");
		assertEquals(message, "Glober do not exist", "Failed in validating message");
		test.log(Status.PASS, "Glober do not exist message is coming as expected");
	}

	/**
	 * This test will rotate glober for invalid projectId
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = RotateAndReplaceDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression })
	public void createRotateTestWithInvalidProjectId(String userName, String userId) throws Exception {

		RotateAndReplaceTestHelper rotateAndReplaceHelper = new RotateAndReplaceTestHelper(userName);
		List<Object> dataForRotateGlober = rotateAndReplaceHelper.getRequiredTestDataForRotate(userName, userId,
				"pastDate");
		dataForRotateGlober.set(6, 79);
		JSONObject requestParams = new RotateAndReplacePayloadHelper().createRotatePayload(dataForRotateGlober);
		Response response = rotateAndReplaceHelper.postRotateGloberForInvalidInput(requestParams);
		validateResponseToContinueTest(response, 400, "Rotate glober sucessful", true);

		resultCode = (Integer) restUtils.getValueFromResponse(response, "resultCode");
		message = (String) restUtils.getValueFromResponse(response, "message");

		assertEquals(resultCode, 400, "Failed in validating resultCode");
		assertEquals(message, "Sow donot exist", "Failed in validating message");
		test.log(Status.PASS, "Sow donot exist message is coming as expected");
	}

	/**
	 * This test will rotate glober for invalid sowId
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = RotateAndReplaceDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression })
	public void createRotateTestWithInvalidSowId(String userName, String userId) throws Exception {

		RotateAndReplaceTestHelper rotateAndReplaceHelper = new RotateAndReplaceTestHelper(userName);
		List<Object> dataForRotateGlober = rotateAndReplaceHelper.getRequiredTestDataForRotate(userName, userId,
				"pastDate");
		dataForRotateGlober.set(4, 15);
		JSONObject requestParams = new RotateAndReplacePayloadHelper().createRotatePayload(dataForRotateGlober);
		Response response = rotateAndReplaceHelper.postRotateGloberForInvalidInput(requestParams);

		resultCode = (Integer) restUtils.getValueFromResponse(response, "resultCode");
		message = (String) restUtils.getValueFromResponse(response, "message");

		assertEquals(resultCode, 400, "Failed in validating resultCode");
		assertEquals(message, "Sow donot exist", "Failed in validating message");
		test.log(Status.PASS, "Sow donot exist message is coming as expected");
	}

	/**
	 * This test will rotate glober for invalid positionId
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = RotateAndReplaceDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression })
	public void createRotateTestWithInvalidPositionId(String userName, String userId) throws Exception {

		RotateAndReplaceTestHelper rotateAndReplaceHelper = new RotateAndReplaceTestHelper(userName);
		List<Object> dataForRotateGlober = rotateAndReplaceHelper.getRequiredTestDataForRotate(userName, userId,
				"pastDate");
		dataForRotateGlober.set(2, 15);
		JSONObject requestParams = new RotateAndReplacePayloadHelper().createRotatePayload(dataForRotateGlober);
		Response response = rotateAndReplaceHelper.postRotateGloberForInvalidInput(requestParams);

		resultCode = (Integer) restUtils.getValueFromResponse(response, "resultCode");
		message = (String) restUtils.getValueFromResponse(response, "message");

		assertEquals(resultCode, 400, "Failed in validating resultCode");
		assertEquals(message, "SR do not exist", "Failed in validating message");
		test.log(Status.PASS, "SR do not exist message is coming as expected");
	}

	/**
	 * This test will rotate glober for invalid load
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = RotateAndReplaceDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression })
	public void createRotateTestWithInvalidLoad(String userName, String userId) throws Exception {

		RotateAndReplaceTestHelper rotateAndReplaceHelper = new RotateAndReplaceTestHelper(userName);
		List<Object> dataForRotateGlober = rotateAndReplaceHelper.getRequiredTestDataForRotate(userName, userId,
				"pastDate");
		dataForRotateGlober.set(1, 215);
		JSONObject requestParams = new RotateAndReplacePayloadHelper().createRotatePayload(dataForRotateGlober);
		Response response = rotateAndReplaceHelper.postRotateGloberForInvalidInput(requestParams);

		resultCode = (Integer) restUtils.getValueFromResponse(response, "resultCode");
		message = (String) restUtils.getValueFromResponse(response, "message");

		assertEquals(resultCode, 400, "Failed in validating resultCode");
		assertEquals(message, "Invalid load", "Failed in validating message");
		test.log(Status.PASS, "Invalid load message is coming as expected");
	}

	/**
	 * This test will rotate glober for invalid reason
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = RotateAndReplaceDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression })
	public void createRotateTestWithInvalidReason(String userName, String userId) throws Exception {

		RotateAndReplaceTestHelper rotateAndReplaceHelper = new RotateAndReplaceTestHelper(userName);
		List<Object> dataForRotateGlober = rotateAndReplaceHelper.getRequiredTestDataForRotate(userName, userId,
				"pastDate");
		dataForRotateGlober.set(0, "$%^");
		JSONObject requestParams = new RotateAndReplacePayloadHelper().createRotatePayload(dataForRotateGlober);
		Response response = rotateAndReplaceHelper.postRotateGloberForInvalidInput(requestParams);

		resultCode = (Integer) restUtils.getValueFromResponse(response, "resultCode");
		message = (String) restUtils.getValueFromResponse(response, "message");

		assertEquals(resultCode, 400, "Failed in validating resultCode");
		assertEquals(message, "Start date should be greater than today", "Failed in validating message");
		test.log(Status.PASS, "Start date should be greater than today message is coming as expected");
	}

	/**
	 * This test will create replacement with SR
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = RotateAndReplaceDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void createReplacementTestWithSR(String userName, String userId) throws Exception {

		RotateAndReplaceTestHelper rotateAndReplaceHelper = new RotateAndReplaceTestHelper(userName);
		List<Object> dataForReplaceGlober = rotateAndReplaceHelper.getReplaceGloberWithSRTestData(userName, userId);
		JSONObject requestParams = new RotateAndReplacePayloadHelper().createReplaceWithSRPayload(dataForReplaceGlober);
		Response response = rotateAndReplaceHelper.postReplaceGlober(requestParams);
		message = (String) restUtils.getValueFromResponse(response, "message");
		status = (String) restUtils.getValueFromResponse(response, "status");

		assertTrue(message.contains("Success! You have successfully created Staff Request"),
				"Failed in validating message");
		assertEquals(status, "success", "Failed in validating Status");
		test.log(Status.PASS, "You have successfully created Staff Request");

		int srNumberToVerify = rotateAndReplaceHelper.verifyCreatedSRForReplacedGlober(requestParams.get("globerId"),
				requestParams.get("projectId"));

		assertTrue(message.contains(String.valueOf(srNumberToVerify)),
				String.format("Excpected SRNumber %d was not Present in the message '%s'.", srNumberToVerify, message));
		test.log(Status.PASS, "Excpected SRNumber " + srNumberToVerify + " was Present in the message");
	}

	/**
	 * This test will create replacement with shadow SR
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = RotateAndReplaceDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void createReplacementTestShadowWithSR(String userName, String userId) throws Exception {

		RotateAndReplaceTestHelper rotateAndReplaceHelper = new RotateAndReplaceTestHelper(userName);
		List<Object> dataForReplaceGlober = rotateAndReplaceHelper.getReplaceGloberTestDataForShadowWithSR(userName,
				userId);
		JSONObject requestParams = new RotateAndReplacePayloadHelper().createReplaceWithSRPayload(dataForReplaceGlober);
		Response response = rotateAndReplaceHelper.postReplaceGlober(requestParams);
		message = (String) restUtils.getValueFromResponse(response, "message");
		status = (String) restUtils.getValueFromResponse(response, "status");

		assertTrue(message.contains("Success! You have successfully created Staff Request"),
				"Failed in validating message");
		assertEquals(status, "success", "Failed in validating Status");
		test.log(Status.PASS, "You have successfully created Staff Request");

		int srNumberToVerify = rotateAndReplaceHelper.verifyCreatedSRForReplacedGlober(requestParams.get("globerId"),
				requestParams.get("projectId"));

		assertTrue(message.contains(String.valueOf(srNumberToVerify)),
				String.format("Excpected SRNumber %d was not Present in the message '%s'.", srNumberToVerify, message));
		test.log(Status.PASS, "Excpected SRNumber " + srNumberToVerify + " was Present in the message");
	}

	/**
	 * This test will create replacement with SR for invalid globerId
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = RotateAndReplaceDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void createReplacementTestWithSRForInvalidGloberId(String userName, String userId) throws Exception {

		RotateAndReplaceTestHelper rotateAndReplaceHelper = new RotateAndReplaceTestHelper(userName);
		List<Object> dataForReplaceGlober = rotateAndReplaceHelper.getReplaceGloberWithSRTestData(userName, userId);
		dataForReplaceGlober.set(1, 15);
		JSONObject requestParams = new RotateAndReplacePayloadHelper().createReplaceWithSRPayload(dataForReplaceGlober);

		Response response = rotateAndReplaceHelper.postReplaceGloberInvalidData(requestParams);
		message = (String) restUtils.getValueFromResponse(response, "message");
		status = (String) restUtils.getValueFromResponse(response, "status");

		assertTrue(status.equalsIgnoreCase("fail"), "status should be fail");
		assertTrue(message.equalsIgnoreCase("No Assignment Found For Glober"), "The error message is not correct");
		test.log(Status.PASS, "No Assignment Found For Glober message is coming as expected");
	}

	/**
	 * This test will create replacement with SR for invalid positionId
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = RotateAndReplaceDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void createReplacementTestWithSRForInvalidPositionId(String userName, String userId) throws Exception {

		RotateAndReplaceTestHelper rotateAndReplaceHelper = new RotateAndReplaceTestHelper(userName);
		List<Object> dataForReplaceGlober = rotateAndReplaceHelper.getReplaceGloberWithSRTestData(userName, userId);
		dataForReplaceGlober.set(3, 15);
		JSONObject requestParams = new RotateAndReplacePayloadHelper().createReplaceWithSRPayload(dataForReplaceGlober);
		
		Response response = rotateAndReplaceHelper.postReplaceGloberInvalidData(requestParams);
		validateResponseToContinueTest(response, 400, "Repalce glober sucessful", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		status = (String) restUtils.getValueFromResponse(response, "status");

		assertTrue(status.equalsIgnoreCase("fail"), "status should be fail");
		assertTrue(message.equalsIgnoreCase("No Assignment Found For Glober"), "The error message is not correct");
		test.log(Status.PASS, "No Assignment Found For Glober message is coming as expected");
	}

	/**
	 * This test will create replacement with SR for invalid projectId
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = RotateAndReplaceDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void createReplacementTestWithSRForInvalidProjectId(String userName, String userId) throws Exception {

		RotateAndReplaceTestHelper rotateAndReplaceHelper = new RotateAndReplaceTestHelper(userName);
		List<Object> dataForReplaceGlober = rotateAndReplaceHelper.getReplaceGloberWithSRTestData(userName, userId);
		dataForReplaceGlober.set(3, 15);
		JSONObject requestParams = new RotateAndReplacePayloadHelper().createReplaceWithSRPayload(dataForReplaceGlober);
		
		Response response = rotateAndReplaceHelper.postReplaceGloberInvalidData(requestParams);
		validateResponseToContinueTest(response, 400, "Replace Position sucessful", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		status = (String) restUtils.getValueFromResponse(response, "status");

		assertTrue(status.equalsIgnoreCase("fail"), "status should be fail");
		assertTrue(message.equalsIgnoreCase("No Assignment Found For Glober"), "The error message is not correct");
		test.log(Status.PASS, "No Assignment Found For Glober message is coming as expected");
	}

	/**
	 * This test will create replacement with SR for invalid start date
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = RotateAndReplaceDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void createReplacementTestWithSRForInvalidStartDate(String userName, String userId) throws Exception {

		RotateAndReplaceTestHelper rotateAndReplaceHelper = new RotateAndReplaceTestHelper(userName);
		List<Object> dataForReplaceGlober = rotateAndReplaceHelper.getReplaceGloberWithSRTestData(userName, userId);
		dataForReplaceGlober.set(7, 15);
		JSONObject requestParams = new RotateAndReplacePayloadHelper().createReplaceWithSRPayload(dataForReplaceGlober);
		Response response = rotateAndReplaceHelper.postReplaceGloberInvalidData(requestParams);
		validateResponseToContinueTest(response, 500, "Replace glober sucessful", true);
		
		message = (String) restUtils.getValueFromResponse(response, "statusCode");
		status = (String) restUtils.getValueFromResponse(response, "status");

		assertTrue(status.equalsIgnoreCase("fail"), "status should be fail");
		assertTrue(message.equalsIgnoreCase("INTERNAL_SERVER_ERROR"), "The error message is not correct");
		test.log(Status.PASS, "INTERNAL_SERVER_ERROR message is coming as expected");
	}

	/**
	 * This test will create replacement with SR for invalid replacement reason
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = RotateAndReplaceDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void createReplacementTestWithSRForInvalidReplacementReason(String userName, String userId)
			throws Exception {

		RotateAndReplaceTestHelper rotateAndReplaceHelper = new RotateAndReplaceTestHelper(userName);
		List<Object> dataForReplaceGlober = rotateAndReplaceHelper.getReplaceGloberWithSRTestData(userName, userId);
		dataForReplaceGlober.set(6, -1);
		JSONObject requestParams = new RotateAndReplacePayloadHelper().createReplaceWithSRPayload(dataForReplaceGlober);

		Response response = rotateAndReplaceHelper.postReplaceGloberInvalidData(requestParams);
		message = (String) restUtils.getValueFromResponse(response, "message");
		status = (String) restUtils.getValueFromResponse(response, "status");

		assertTrue(status.equalsIgnoreCase("fail"), "status should be fail");
		assertTrue(message.equalsIgnoreCase("Invalid Replacement Reason."), "The error message is not correct");
		test.log(Status.PASS, "Invalid Replacement Reason message is coming as expected");
	}
	
	
	/**
	 * This test will create replacement on Last Day
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = RotateAndReplaceDataProviders.class, enabled = true, priority = 0, groups = ExeGroups.Regression)
	public void createReplacementOnLastDay(String userName, String userId) throws Exception {

		RotateAndReplaceTestHelper rotateAndReplaceHelper = new RotateAndReplaceTestHelper(userName);
		List<Object> dataForReplaceGlober = rotateAndReplaceHelper.getReplaceGloberTestData(userName, userId);
		JSONObject requestParams = new RotateAndReplacePayloadHelper().createReplacePayload(dataForReplaceGlober);
		requestParams.put("newAssignmentEndDate", requestParams.get("startDate"));
		
		Response response = rotateAndReplaceHelper.postReplaceGlober(requestParams);
		validateResponseToContinueTest(response, 201, "Created replacement on last day", true);
		
		message = (String) restUtils.getValueFromResponse(response, "message");
		status = (String) restUtils.getValueFromResponse(response, "status");
		String statusCode = (String) restUtils.getValueFromResponse(response, "statusCode");

		assertTrue(status.equals("success"), "status should be success");
		assertTrue(message.contains("Success! You have successfully created Staff Request"), "The replacement is not created");
		assertTrue(statusCode.equals("CREATED"), "status should be success");
		test.log(Status.PASS, "Created Replacement on Last Day");
	}
	
	
	/**
	 * This test will create replacement
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = RotateAndReplaceDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void createSubsequentReplacementTest(String userName, String userId) throws Exception {

		RotateAndReplaceTestHelper rotateAndReplaceHelper = new RotateAndReplaceTestHelper(userName);
		List<Object> dataForReplaceGlober = rotateAndReplaceHelper.getReplaceGloberTestData(userName, userId);
		JSONObject requestParams = new RotateAndReplacePayloadHelper().createReplacePayload(dataForReplaceGlober);
		Response response = rotateAndReplaceHelper.postReplaceGlober(requestParams);
		message = (String) restUtils.getValueFromResponse(response, "message");
		status = (String) restUtils.getValueFromResponse(response, "status");

		assertTrue(message.contains("Success! You have successfully created Staff Request"),
				"Failed in validating message");
		assertEquals(status, "success", "Failed in validating Status");
		test.log(Status.PASS, "You have successfully created Staff Request");
		
		response = rotateAndReplaceHelper.postReplaceGlober(requestParams);

		message = (String) restUtils.getValueFromResponse(response, "message");
		status = (String) restUtils.getValueFromResponse(response, "status");
		int srNumberToVerify = rotateAndReplaceHelper.verifyCreatedSRForReplacedGlober(requestParams.get("globerId"),
				requestParams.get("projectId"));

		assertTrue(message.contains(String.valueOf(srNumberToVerify)),
				String.format("Excpected SRNumber %d was not Present in the message '%s'.", srNumberToVerify, message));
		test.log(Status.PASS, "Excpected SRNumber " + srNumberToVerify + " was Present in the message");
	}

}
