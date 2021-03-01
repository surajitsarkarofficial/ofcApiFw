package tests.testcases.myTeam.features;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import dataproviders.myTeam.features.UserPreferencesDataProviders;
import dto.myTeam.features.UserPreferencesDtoList;
import io.restassured.response.Response;
import payloads.myTeam.features.UserPreferencesPayloadHelper;
import tests.testcases.myTeam.MyTeamBaseTest;
import tests.testhelpers.myTeam.features.UserPreferencesHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;
import utils.RestUtils;

/**
 * @author imran.khan
 *
 */

public class UserPreferencesTests extends MyTeamBaseTest {

	String message, status, componentKey, value = null;
	boolean active;
	RestUtils restUtils = new RestUtils();

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("UserPreferencesTests");
	}

	/**
	 * This test will get user preferences for PM and DD
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = UserPreferencesDataProviders.class, enabled = true, priority = 5, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void getUserPreferences(String userName, String userId) throws Exception {

		UserPreferencesHelper userPreferencesHelper = new UserPreferencesHelper(userName);
		Response response = userPreferencesHelper.getUserPreferencesForUser(userId);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch user preferences.", true);
		active = (boolean) restUtils.getValueFromResponse(response, "details.active");
		componentKey = (String) restUtils.getValueFromResponse(response, "details.componentKey");
		value = (String) restUtils.getValueFromResponse(response, "details.value");
		List<UserPreferencesDtoList> userPreferencesFromDb = userPreferencesHelper
				.getUserPreferencesFromDatabase(userId);

		assertEquals(active, userPreferencesFromDb.get(0).isActive(), "Error with user name " + userName);
		assertEquals(componentKey, userPreferencesFromDb.get(0).getComponentKey(), "Error with user name " + userName);
		assertEquals(value, userPreferencesFromDb.get(0).getValue(), "Error with user name " + userName);
		test.log(Status.PASS, "User preferences retrieved successfully");
	}

	/**
	 * This test will get user preferences for invalid glober Id
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "ddRolDetailsWithId", dataProviderClass = UserPreferencesDataProviders.class, enabled = true, priority = 3, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void getUserPreferencesForInvalidGloberId(String userName, String userId) throws Exception {

		UserPreferencesHelper userPreferencesHelper = new UserPreferencesHelper(userName);
		Response response = userPreferencesHelper.getUserPreferencesForUser("@$$@-1");
		new MyTeamBaseTest().validateResponseToContinueTest(response, 500, "Unable to fetch user preferences.", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		int status = (Integer) restUtils.getValueFromResponse(response, "status");

		assertEquals(status, 500, "Failed in validating Status");
		assertTrue(message.contains("Request processing failed; nested exception is java.lang.NumberFormatException"),
				"NumberFormatException message is not coming");
		test.log(Status.PASS, "NumberFormatException message coming successfully");
	}

	/**
	 * This test will get user preferences for invalid glober Id
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "ddRolDetailsWithId", dataProviderClass = UserPreferencesDataProviders.class, enabled = true, priority = 3, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void getUserPreferencesForWrongGloberId(String userName, String userId) throws Exception {

		UserPreferencesHelper userPreferencesHelper = new UserPreferencesHelper(userName);
		Response response = userPreferencesHelper.getUserPreferencesForUser("-1");
		new MyTeamBaseTest().validateResponseToContinueTest(response, 400, "Unable to fetch user preferences.", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		status = (String) restUtils.getValueFromResponse(response, "status");

		assertEquals(status, "fail", "Failed in validating Status");
		assertTrue(message.contains("Glober Does Not Exist "), "Failed in validating message");
		test.log(Status.PASS, "Glober Does Not Exist message coming successfully");
	}

	/**
	 * This test will get user preferences for no glober Id
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "ddRolDetailsWithId", dataProviderClass = UserPreferencesDataProviders.class, enabled = true, priority = 3, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void getUserPreferencesForNoGloberId(String userName, String userId) throws Exception {

		UserPreferencesHelper userPreferencesHelper = new UserPreferencesHelper(userName);
		Response response = userPreferencesHelper.getUserPreferencesForUser("");
		new MyTeamBaseTest().validateResponseToContinueTest(response, 500, "Unable to fetch user preferences.", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		int status = (Integer) restUtils.getValueFromResponse(response, "status");

		assertEquals(status, 500, "Failed in validating Status");
		assertTrue(message.contains("Request processing failed; nested exception is java.lang.NumberFormatException"),
				"NumberFormatException message is not coming");
		test.log(Status.PASS, "NumberFormatException message coming successfully");
	}

	/**
	 * This test will get create user preferences for PM and DD
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmDdRolDetailsWithId", dataProviderClass = UserPreferencesDataProviders.class, enabled = true, priority = 1, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void postUserPreferences(String userName, String userId) throws Exception {

		UserPreferencesHelper userPreferencesHelper = new UserPreferencesHelper(userName);
		List<Object> dataForUserPreferences = userPreferencesHelper.geUserPreferencesTestData(userId);
		JSONObject requestParams = new UserPreferencesPayloadHelper().userPreferencePayload(dataForUserPreferences);
		Response response = userPreferencesHelper.postUserPreferencesForUser(userId, requestParams);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Unable to create user preferences.", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		String statusCode = (String) restUtils.getValueFromResponse(response, "statusCode");

		assertEquals(statusCode, "CREATED", "status code is not created");
		assertTrue(message.contains("User preferences created"), "User preferences not created successfully");
		test.log(Status.PASS, "User preferences created successfully");
	}

	/**
	 * This test will get create user preferences for invalid glober Id
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "ddRolDetailsWithId", dataProviderClass = UserPreferencesDataProviders.class, enabled = true, priority = 1, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void postUserPreferencesForInvalidGloberId(String userName, String userId) throws Exception {

		UserPreferencesHelper userPreferencesHelper = new UserPreferencesHelper(userName);
		List<Object> dataForUserPreferences = userPreferencesHelper.geUserPreferencesTestData(userId);
		dataForUserPreferences.set(0, "%^&*");
		JSONObject requestParams = new UserPreferencesPayloadHelper().userPreferencePayload(dataForUserPreferences);
		Response response = userPreferencesHelper.postUserPreferencesForUser(userId, requestParams);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 400, "Unable to create user preferences.", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		int status = (Integer) restUtils.getValueFromResponse(response, "status");

		assertEquals(status, 400, "status code is not created");
		assertEquals(message, "Bad Request", "Bad Request message should come");
		test.log(Status.PASS, "Bad Request message coming successfully");
	}

	/**
	 * This test will get create user preferences for wrong glober Id
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "ddRolDetailsWithId", dataProviderClass = UserPreferencesDataProviders.class, enabled = true, priority = 1, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void postUserPreferencesForWrongGloberId(String userName, String userId) throws Exception {

		UserPreferencesHelper userPreferencesHelper = new UserPreferencesHelper(userName);
		List<Object> dataForUserPreferences = userPreferencesHelper.geUserPreferencesTestData(userId);
		dataForUserPreferences.set(0, "-1");
		JSONObject requestParams = new UserPreferencesPayloadHelper().userPreferencePayload(dataForUserPreferences);
		Response response = userPreferencesHelper.postUserPreferencesForUser(userId, requestParams);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 400, "Unable to create user preferences.", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		status = (String) restUtils.getValueFromResponse(response, "status");

		assertEquals(status, "fail", "status code is not created");
		assertEquals(message, "Glober Does Not Exist ", "Glober Does Not Exist message is not coming");
		test.log(Status.PASS, "Glober Does Not Exist message coming successfully");
	}

	/**
	 * This test will get create user preferences for no glober Id
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "ddRolDetailsWithId", dataProviderClass = UserPreferencesDataProviders.class, enabled = true, priority = 1, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void postUserPreferencesForNoGloberId(String userName, String userId) throws Exception {

		UserPreferencesHelper userPreferencesHelper = new UserPreferencesHelper(userName);
		List<Object> dataForUserPreferences = userPreferencesHelper.geUserPreferencesTestData(userId);
		dataForUserPreferences.set(0, "");
		JSONObject requestParams = new UserPreferencesPayloadHelper().userPreferencePayload(dataForUserPreferences);
		Response response = userPreferencesHelper.postUserPreferencesForUser(userId, requestParams);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 400, "Unable to create user preferences.", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		status = (String) restUtils.getValueFromResponse(response, "status");

		assertEquals(status, "fail", "status code is not created");
		assertEquals(message, "Glober Does Not Exist ", "Glober Does Not Exist message is not coming");
		test.log(Status.PASS, "Glober Does Not Exist message coming successfully");
	}

	/**
	 * This test will get create user preferences for invalid value
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "ddRolDetailsWithId", dataProviderClass = UserPreferencesDataProviders.class, enabled = true, priority = 1, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void postUserPreferencesForInvalidActive(String userName, String userId) throws Exception {

		UserPreferencesHelper userPreferencesHelper = new UserPreferencesHelper(userName);
		List<Object> dataForUserPreferences = userPreferencesHelper.geUserPreferencesTestData(userId);
		dataForUserPreferences.set(3, "89");
		JSONObject requestParams = new UserPreferencesPayloadHelper().userPreferencePayload(dataForUserPreferences);
		Response response = userPreferencesHelper.postUserPreferencesForUser(userId, requestParams);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 400, "Unable to create user preferences.", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		int status = (Integer) restUtils.getValueFromResponse(response, "status");

		assertEquals(status, 400, "status code is not created");
		assertEquals(message, "Bad Request", "Bad Request message should come");
		test.log(Status.PASS, "Bad Request message coming successfully");
	}

	/**
	 * This test will get modify user preferences for PM and DD
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = UserPreferencesDataProviders.class, enabled = true, priority = 2, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void putUserPreferences(String userName, String userId) throws Exception {

		UserPreferencesHelper userPreferencesHelper = new UserPreferencesHelper(userName);
		List<Object> dataForUserPreferences = userPreferencesHelper.geUserPreferencesTestData(userId);
		JSONObject requestParams = new UserPreferencesPayloadHelper().userPreferencePayload(dataForUserPreferences);
		Response response = userPreferencesHelper.putUserPreferencesForUser(userId, requestParams);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Unable to create user preferences.", true);
		message = (String) restUtils.getValueFromResponse(response, "message");

		assertEquals(message, "User preferences modified", "User preferences not modified successfully");
		test.log(Status.PASS, "User preferences modified successfully");
	}

	/**
	 * This test will get modify user preferences for invalid glober Id
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "ddRolDetailsWithId", dataProviderClass = UserPreferencesDataProviders.class, enabled = true, priority = 3, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void putUserPreferencesForInvalidGloberId(String userName, String userId) throws Exception {

		UserPreferencesHelper userPreferencesHelper = new UserPreferencesHelper(userName);
		List<Object> dataForUserPreferences = userPreferencesHelper.geUserPreferencesTestData(userId);
		dataForUserPreferences.set(0, "%^&*");
		JSONObject requestParams = new UserPreferencesPayloadHelper().userPreferencePayload(dataForUserPreferences);
		Response response = userPreferencesHelper.putUserPreferencesForUser(userId, requestParams);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 400, "Unable to create user preferences.", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		int status = (Integer) restUtils.getValueFromResponse(response, "status");

		assertEquals(status, 400, "status code is not created");
		assertEquals(message, "Bad Request", "Bad Request message should come");
		test.log(Status.PASS, "Bad Request message coming successfully");
	}

	/**
	 * This test will get modify user preferences for wrong glober Id
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "ddRolDetailsWithId", dataProviderClass = UserPreferencesDataProviders.class, enabled = true, priority = 3, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void putUserPreferencesForWrongGloberId(String userName, String userId) throws Exception {

		UserPreferencesHelper userPreferencesHelper = new UserPreferencesHelper(userName);
		List<Object> dataForUserPreferences = userPreferencesHelper.geUserPreferencesTestData(userId);
		dataForUserPreferences.set(0, "-1");
		JSONObject requestParams = new UserPreferencesPayloadHelper().userPreferencePayload(dataForUserPreferences);
		Response response = userPreferencesHelper.putUserPreferencesForUser(userId, requestParams);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 400, "Unable to create user preferences.", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		status = (String) restUtils.getValueFromResponse(response, "status");

		assertEquals(status, "fail", "status code is not created");
		assertEquals(message, "Glober Does Not Exist ", "Glober Does Not Exist message is not coming");
		test.log(Status.PASS, "Glober Does Not Exist message coming successfully");
	}

	/**
	 * This test will get modify user preferences for no glober Id
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "ddRolDetailsWithId", dataProviderClass = UserPreferencesDataProviders.class, enabled = true, priority = 3, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void putUserPreferencesForNoGloberId(String userName, String userId) throws Exception {

		UserPreferencesHelper userPreferencesHelper = new UserPreferencesHelper(userName);
		List<Object> dataForUserPreferences = userPreferencesHelper.geUserPreferencesTestData(userId);
		dataForUserPreferences.set(0, "");
		JSONObject requestParams = new UserPreferencesPayloadHelper().userPreferencePayload(dataForUserPreferences);
		Response response = userPreferencesHelper.putUserPreferencesForUser(userId, requestParams);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 400, "Unable to create user preferences.", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		status = (String) restUtils.getValueFromResponse(response, "status");

		assertEquals(status, "fail", "status code is not created");
		assertEquals(message, "Glober Does Not Exist ", "Glober Does Not Exist message is not coming");
		test.log(Status.PASS, "Glober Does Not Exist message coming successfully");
	}

	/**
	 * This test will get modify user preferences for invalid value
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@Test(dataProvider = "ddRolDetailsWithId", dataProviderClass = UserPreferencesDataProviders.class, enabled = true, priority = 3, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void putUserPreferencesForInvalidActive(String userName, String userId) throws Exception {

		UserPreferencesHelper userPreferencesHelper = new UserPreferencesHelper(userName);
		List<Object> dataForUserPreferences = userPreferencesHelper.geUserPreferencesTestData(userId);
		dataForUserPreferences.set(3, "89");
		JSONObject requestParams = new UserPreferencesPayloadHelper().userPreferencePayload(dataForUserPreferences);
		Response response = userPreferencesHelper.putUserPreferencesForUser(userId, requestParams);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 400, "Unable to create user preferences.", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		int status = (Integer) restUtils.getValueFromResponse(response, "status");

		assertEquals(status, 400, "status code is not created");
		assertEquals(message, "Bad Request", "Bad Request message should come");
		test.log(Status.PASS, "Bad Request message coming successfully");
	}
}
