package tests.testcases.myTeam.features;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import dataproviders.myTeam.features.CheckoutDataProviders;
import endpoints.myTeam.features.MyTeamViewTabforDDEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.myTeam.MyTeamBaseTest;
import tests.testhelpers.myTeam.features.DDAccessLevelHelper;
import utils.ExtentHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;
import utils.RestUtils;

/**
 * @author dnyaneshwar.waghmare
 *
 */

public class MyTeamViewTabforDDTest extends MyTeamBaseTest {
	String message, status = null;
	String podId;

	RestUtils restUtils = new RestUtils();

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("MyTeamViewTabforDDTest");
	}

	/**
	 * This test will get all the clients under DD as per My View Tab
	 * 
	 * @param UserName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "ddRolDetailsWithId", dataProviderClass = CheckoutDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void getClientListMyViewTab(String userName, String userId) throws Exception {

		DDAccessLevelHelper ddAccessLevelHelper = new DDAccessLevelHelper(userName);
		Response response = ddAccessLevelHelper.getClientListUnderDD(userId);
		validateResponseToContinueTest(response, 200, "Client list not returned successfully", true);

		List<Integer> clientIds = response.jsonPath().get("details.id");
		List<String> clientNames = response.jsonPath().get("details.name");
		List<Integer> tagNames = response.jsonPath().get("details.tag");
		List<String> businessUnitIds = response.jsonPath().get("details.businessUnitId");

		Map<String, List<String>> DBclientListForDD = ddAccessLevelHelper.getActiveClientUnderDDFromDB(userId);

		Map<Integer, List<Object>> getClientFromJSON = new HashMap<>();
		List<Object> combineList = null;
		for (int i = 0; i < clientIds.size(); i++) {
			combineList = new ArrayList<>();
			combineList.add(clientNames.get(i));
			combineList.add(tagNames.get(i));
			combineList.add(businessUnitIds.get(i));
			getClientFromJSON.put(clientIds.get(i), combineList);

		}

		for (Entry<Integer, List<Object>> entry : getClientFromJSON.entrySet()) {
			List<String> getValueForKey = DBclientListForDD.get(String.valueOf(entry.getKey()));
			for (String str : getValueForKey) {
				assertTrue(String.valueOf(DBclientListForDD.values()).contains(str));
			}
			assertTrue(DBclientListForDD.get(String.valueOf(entry.getKey())) != null);
			assertTrue(DBclientListForDD.keySet().contains(String.valueOf(entry.getKey())));
		}
	}

	/**
	 * This test will get all the clients under DD as per My View Tab
	 * 
	 * @param UserName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "ddRolDetailsWithId", dataProviderClass = CheckoutDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void getProjectListMyViewTab(String userName, String userId) throws Exception {

		DDAccessLevelHelper ddAccessLevelHelper = new DDAccessLevelHelper(userName);
		Response response = ddAccessLevelHelper.getProjectListUnderDD(userId);
		validateResponseToContinueTest(response, 200, "Client list not returned successfully", true);

		List<Integer> projectIds = response.jsonPath().get("details.projectId");
		List<String> projectNames = response.jsonPath().get("details.projectName");

		Map<String, List<String>> DBprojectListForDD = ddAccessLevelHelper.getActiveProjectUnderDDFromDB(userId);

		Map<Integer, List<Object>> getProjectFromJSON = new HashMap<>();
		List<Object> combineList = null;
		for (int i = 0; i < projectIds.size(); i++) {
			combineList = new ArrayList<>();
			combineList.add(projectNames.get(i));
			getProjectFromJSON.put(projectIds.get(i), combineList);

		}

		for (Entry<Integer, List<Object>> entry : getProjectFromJSON.entrySet()) {
			List<String> getValueForKey = DBprojectListForDD.get(String.valueOf(entry.getKey()));
			for (String str : getValueForKey) {
				assertTrue(String.valueOf(DBprojectListForDD.values()).contains(str));
			}
			assertTrue(DBprojectListForDD.get(String.valueOf(entry.getKey())) != null);
			assertTrue(DBprojectListForDD.keySet().contains(String.valueOf(entry.getKey())));
		}
	}

	/**
	 * This test will get all the clients under DD as per My View Tab
	 * 
	 * @param UserName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "ddRolDetailsWithId", dataProviderClass = CheckoutDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void getClientMembersMyViewTab(String userName, String userId) throws Exception {

		DDAccessLevelHelper ddAccessLevelHelper = new DDAccessLevelHelper(userName);
		Response response = ddAccessLevelHelper.getClientListUnderDD(userId);
		validateResponseToContinueTest(response, 200, "Client list not returned successfully", true);

		List<Integer> clientIds = response.jsonPath().get("details.id");
		int clientId = clientIds.get(new Random().nextInt(clientIds.size()));

		Response getClientListResponse = ddAccessLevelHelper.getClientMemberListUnderDD(clientId);
		validateResponseToContinueTest(getClientListResponse, 200, "Client list not returned successfully", true);
		List<Integer> globerIds = getClientListResponse.jsonPath().get("details.globerId");
		List<String> globerNames = getClientListResponse.jsonPath().get("details.username");

		Map<String, List<String>> dbClientMembersListForDD = ddAccessLevelHelper
				.getActiveClientMembersUnderDDFromDB(userId, clientId);

		Map<Integer, List<Object>> getClientMembersFromJSON = new HashMap<>();
		List<Object> combineList = null;
		for (int i = 0; i < globerIds.size(); i++) {
			combineList = new ArrayList<>();
			combineList.add(globerNames.get(i));
			getClientMembersFromJSON.put(globerIds.get(i), combineList);

		}

		for (Entry<Integer, List<Object>> entry : getClientMembersFromJSON.entrySet()) {
			List<String> getValueForKey = dbClientMembersListForDD.get(String.valueOf(entry.getKey()));
			for (String str : getValueForKey) {
				assertTrue(String.valueOf(dbClientMembersListForDD.values()).contains(str));
			}
			assertTrue(dbClientMembersListForDD.get(String.valueOf(entry.getKey())) != null);
			assertTrue(dbClientMembersListForDD.keySet().contains(String.valueOf(entry.getKey())));
		}
	}

	/**
	 * This test will get all the clients under DD as per My View Tab
	 * 
	 * @param UserName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "ddRolDetailsWithId", dataProviderClass = CheckoutDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void getClientListMyViewTabWrongUserId(String userName, String userId) throws Exception {

		DDAccessLevelHelper ddAccessLevelHelper = new DDAccessLevelHelper(userName);
		Response response = ddAccessLevelHelper.getClientListUnderDD("86");
		validateResponseToContinueTest(response, 200, "The Status code is not valid", true);
		List<Integer> clientIds = response.jsonPath().get("details.id");
		assertTrue(clientIds.size() == 0);
	}

	/**
	 * This test will get all the clients under DD as per My View Tab
	 * 
	 * @param UserName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "ddRolDetailsWithId", dataProviderClass = CheckoutDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void getClientListMyViewTabInvalidUserId(String userName, String userId) throws Exception {
		DDAccessLevelHelper ddAccessLevelHelper = new DDAccessLevelHelper(userName);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl
				+ String.format(MyTeamViewTabforDDEndpoints.getClientListForDD, "hggg", "ON_GOING", "MY_VIEW");
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		validateResponseToContinueTest(response, 500, "The Status code is not valid", true);
		assertTrue(response.getStatusCode() == 500, "The Status code is not valid");
	}

	/**
	 * This test will get all the clients under DD as per My View Tab
	 * 
	 * @param UserName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "ddRolDetailsWithId", dataProviderClass = CheckoutDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void getProjectListMyViewTabWrongUserId(String userName, String userId) throws Exception {
		DDAccessLevelHelper ddAccessLevelHelper = new DDAccessLevelHelper(userName);
		Response response = ddAccessLevelHelper.getProjectListUnderDD("100");
		validateResponseToContinueTest(response, 200, "The Status code is not valid", true);
		List<Integer> clientIds = response.jsonPath().get("details.id");
		assertTrue(clientIds.size() == 0);
	}

	/**
	 * This test will get all the clients under DD as per My View Tab
	 * 
	 * @param UserName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "ddRolDetailsWithId", dataProviderClass = CheckoutDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void getProjectListMyViewTabInvalidUserId(String userName, String userId) throws Exception {
		DDAccessLevelHelper ddAccessLevelHelper = new DDAccessLevelHelper(userName);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(MyTeamBaseTest.sessionId);
		String url = MyTeamBaseTest.baseUrl + String.format(MyTeamViewTabforDDEndpoints.getProjectListForDD, "1", "20",
				"hggg", "ON_GOING", "MY_VIEW");
		ExtentHelper.test.log(Status.INFO, "Request URL : " + url);
		Response response = restUtils.executeGET(requestSpecification, url);
		validateResponseToContinueTest(response, 500, "The Status code is not valid", true);
		assertTrue(response.getStatusCode() == 500, "The Status code is not valid");
	}

}
