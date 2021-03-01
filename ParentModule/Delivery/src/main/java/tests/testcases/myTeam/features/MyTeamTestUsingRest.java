package tests.testcases.myTeam.features;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.json.JSONObject;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import dataproviders.myTeam.MyTeamDataProviders;
import dataproviders.myTeam.features.UserPreferencesDataProviders;
import dto.myTeam.features.FilterObject;
import dto.myTeam.features.UserPreferencesDtoList;
import endpoints.myTeam.MyTeamEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.myTeam.features.MyTeamPayloadHelper;
import tests.testcases.myTeam.MyTeamBaseTest;
import tests.testhelpers.myTeam.features.DDAccessLevelHelper;
import tests.testhelpers.myTeam.features.MyTeamClientHelper;
import tests.testhelpers.myTeam.features.MyTeamHelper;
import tests.testhelpers.myTeam.features.MyTeamPodHelper;
import tests.testhelpers.myTeam.features.MyTeamProjectHelper;
import tests.testhelpers.myTeam.features.MyTeamProjectMembersHelper;
import tests.testhelpers.myTeam.features.MyTeamStudioHelper;
import tests.testhelpers.myTeam.features.UserPreferencesHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;
import utils.RestUtils;
import utils.Utilities;

/**
 * @author dnyaneshwar.waghmare
 *
 */

public class MyTeamTestUsingRest extends MyTeamBaseTest {
	String message, status, componentKey, value = null;
	String podId;
	boolean active;

	RestUtils restUtils = new RestUtils();

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("MyTeamTestUsingRest");
	}

	/**
	 * This test will get the list of Clients for DD
	 * 
	 * @param UserName as String
	 * @param userId   as String
	 * @throws Exception
	 */
	@Test(dataProvider = "ddRolDetailsWithId", dataProviderClass = MyTeamDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void getClientListForDD(String userName, String userId) throws Exception {

		MyTeamClientHelper myTeamClientHelper = new MyTeamClientHelper(userName);
		Response response = myTeamClientHelper.getClientListForUser(userId);
		validateResponseToContinueTest(response, 200, "Client list not returned successfully", true);

		List<Integer> clientIds = response.jsonPath().get("details.id");
		List<String> clientNames = response.jsonPath().get("details.name");

		Map<String, List<String>> DBclientListForDD = myTeamClientHelper.getClientDetailsForGlober(userId);
		assertEquals(clientIds.size(), DBclientListForDD.size(), "Error: response and query have different size");

		Map<Integer, List<Object>> getClientFromJSON = new HashMap<>();
		List<Object> combineList = null;
		for (int i = 0; i < clientIds.size(); i++) {
			combineList = new ArrayList<>();
			combineList.add(clientNames.get(i));
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
	 * This test will get List of Studios for DD
	 * 
	 * @param UserName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "ddRolDetailsWithId", dataProviderClass = MyTeamDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void getListOfStudiosForDD(String userName, String userId) throws Exception {

		MyTeamStudioHelper myTeamStudioHelper = new MyTeamStudioHelper(userName);
		Response response = myTeamStudioHelper.getStudioListForUser(userId);
		validateResponseToContinueTest(response, 200, "Studio list not returned successfully", true);

		List<List<String>> studioNames = response.jsonPath().get("details.businessUnits.name");
		Set<String> studioNamesByAPI = new HashSet<String>();
		studioNamesByAPI.add(studioNames.get(0).get(0));

		Map<String, List<String>> DBStudioListForDD = myTeamStudioHelper.getStudiosListForGlober(userId);
		assertEquals(studioNames.size(), DBStudioListForDD.size(), "Error: response and query have different size");

		Set<String> studioNamesByDB = new HashSet<String>();
		DBStudioListForDD.forEach((key, value) -> {
			studioNamesByDB.add(value.get(0));
		});
		assertEquals(studioNamesByAPI, studioNamesByDB, "Actual studioName and expected studioName are different");

	}

	/**
	 * This test will return list of Studios for PM
	 * 
	 * @param UserName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = MyTeamDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void getListOfStudiosForPM(String userName, String userId) throws Exception {

		MyTeamStudioHelper myTeamStudioHelper = new MyTeamStudioHelper(userName);
		Response response = myTeamStudioHelper.getStudioListForUser(userId);
		validateResponseToContinueTest(response, 200, "Studio list not returned successfully", true);

		List<List<String>> studioNames = response.jsonPath().get("details.businessUnits.name");
		Set<String> studioNamesByAPI = new HashSet<String>();
		studioNamesByAPI.add(studioNames.get(0).get(0));

		Map<String, List<String>> DBStudioListForDD = myTeamStudioHelper.getStudiosListForGlober(userId);
		assertEquals(studioNames.size(), DBStudioListForDD.size(), "Error: response and query have different size");

		Set<String> studioNamesByDB = new HashSet<String>();
		DBStudioListForDD.forEach((key, value) -> {
			studioNamesByDB.add(value.get(0));
		});

		assertEquals(studioNamesByAPI, studioNamesByDB, "Actual studioName and expected studioName are different");
	}

	/**
	 * This test will return list of Studios For Invalid Glober Id
	 * 
	 * @param UserName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = MyTeamDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression })
	public void getListOfStudiosForInvalidGloberId(String userName, String userId) throws Exception {

		MyTeamStudioHelper myTeamStudioHelper = new MyTeamStudioHelper(userName);
		Response response = myTeamStudioHelper.getStudioListForInvalidUser("66");
		List<List<String>> studioNames = response.jsonPath().get("details.businessUnits");
		assertTrue(studioNames.get(0).isEmpty(), "Issue with Glober Id 66");
	}

	/**
	 * This test will verify the response for get List of Studios for blank Glober
	 * Id
	 * 
	 * @param UserName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = MyTeamDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression})
	public void getListOfStudiosForNoGloberId(String userName, String userId) throws Exception {

		MyTeamStudioHelper myTeamStudioHelper = new MyTeamStudioHelper(userName);
		Response response = myTeamStudioHelper.getStudioListForInvalidUser("");
		message = (String) restUtils.getValueFromResponse(response, "message");
		status = (String) restUtils.getValueFromResponse(response, "status");
		assertEquals(message, "Missing Glober Id", "The error message is not correct");
		assertEquals(status, "fail", "Failed in validating Status");
	}

	/**
	 * This test will get list of Project States
	 * 
	 * @param UserName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = MyTeamDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void getListOfProjectStates(String userName, String userId) throws Exception {

		MyTeamProjectHelper myTeamProjectHelper = new MyTeamProjectHelper(userName);
		Response response = myTeamProjectHelper.getProjectStatesListForUser(userId);
		validateResponseToContinueTest(response, 200, "Project state list not returned successfully", true);

		List<List<String>> projectStates = response.jsonPath().get("details.projectStates.id");
		Set<String> projectStatesByAPI = new HashSet<String>();
		for (int iTemp = 0; iTemp < projectStates.get(0).size(); iTemp++) {
			projectStatesByAPI.add(projectStates.get(0).get(iTemp));
		}

		Map<String, List<String>> DBProjectStatesList = myTeamProjectHelper.getProjectStatesListForGlober(userId);
		assertEquals(projectStates.get(0).size(), DBProjectStatesList.size(),
				"Error: response and query have different size");

		Set<String> projectStateNameByDB = new HashSet<String>();
		DBProjectStatesList.forEach((key, value) -> {
			projectStateNameByDB.add(value.get(0));
		});

		assertEquals(projectStatesByAPI, projectStateNameByDB,
				"Actual project states and expected project states are different");
	}

	/**
	 * This test will get the Project States with blank Glober Id
	 * 
	 * @param UserName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = MyTeamDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression })
	public void getListOfProjectStatesForNoGloberId(String userName, String userId) throws Exception {

		MyTeamProjectHelper myTeamProjectHelper = new MyTeamProjectHelper(userName);
		Response response = myTeamProjectHelper.getProjectStatesListForInvalidUser("");
		message = (String) restUtils.getValueFromResponse(response, "message");
		status = (String) restUtils.getValueFromResponse(response, "status");
		assertEquals(status, "fail", "Failed in validating Status");
		assertEquals(message, "Missing Glober Id", "The error message is not correct");
	}

	/**
	 * This test will get user preferences for PM
	 * 
	 * @param UserName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = UserPreferencesDataProviders.class, enabled = true, priority = 5, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void getDetailsOfUserPereferencesForPM(String userName, String userId) throws Exception {

		UserPreferencesHelper userPreferencesHelper = new UserPreferencesHelper(userName);
		Response response = userPreferencesHelper.getUserPreferencesForUser(userId);
		new MyTeamBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch user preferences.", true);

		List<UserPreferencesDtoList> userPreferencesFromDb = userPreferencesHelper
				.getUserPreferencesFromDatabase(userId);
		active = (boolean) restUtils.getValueFromResponse(response, "details.active");
		componentKey = (String) restUtils.getValueFromResponse(response, "details.componentKey");
		value = (String) restUtils.getValueFromResponse(response, "details.value");

		assertEquals(active, userPreferencesFromDb.get(0).isActive(), "Error with user name " + userName);
		assertEquals(componentKey, userPreferencesFromDb.get(0).getComponentKey(), "Error with user name " + userName);
		assertEquals(value, userPreferencesFromDb.get(0).getValue(), "Error with user name " + userName);
		test.log(Status.PASS, "User preferences retrieved successfully");
	}

	/**
	 * This test will get user preferences for PM and DD
	 * 
	 * @param UserName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = UserPreferencesDataProviders.class, enabled = true, priority = 5, groups = {
			ExeGroups.Regression })
	public void getDetailsOfUserPereferencesForInvalidGloberId(String userName, String userId) throws Exception {

		UserPreferencesHelper userPreferencesHelper = new UserPreferencesHelper(userName);
		Response response = userPreferencesHelper.getUserPreferencesForUser("66");

		message = (String) restUtils.getValueFromResponse(response, "message");
		status = (String) restUtils.getValueFromResponse(response, "status");

		assertEquals(status, "fail", "Failed in validating Status");
		assertEquals(message, "Glober Does Not Exist ", "The error message is not correct");
	}

	/**
	 * This test will get user preferences for PM and DD
	 * 
	 * @param UserName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = UserPreferencesDataProviders.class, enabled = true, priority = 5, groups = {
			ExeGroups.Regression })
	public void getDetailsOfUserPereferencesForWrongGloberId(String userName, String userId) throws Exception {

		UserPreferencesHelper userPreferencesHelper = new UserPreferencesHelper(userName);
		Response response = userPreferencesHelper.getUserPreferencesForUser("@$");
		message = (String) restUtils.getValueFromResponse(response, "message");
		int status = (Integer) restUtils.getValueFromResponse(response, "status");

		assertEquals(status, 500, "Failed in validating Status");
		assertTrue(message.contains("Request processing failed; nested exception is java.lang.NumberFormatException"),
				"The error message is not correct");
	}

	/**
	 * This test will get user preferences for PM and DD
	 * 
	 * @param UserName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = UserPreferencesDataProviders.class, enabled = true, priority = 5, groups = {
			ExeGroups.Regression })
	public void getDetailsOfUserPereferencesForNoGloberId(String userName, String userId) throws Exception {

		UserPreferencesHelper userPreferencesHelper = new UserPreferencesHelper(userName);
		Response response = userPreferencesHelper.getUserPreferencesForUser("");
		message = (String) restUtils.getValueFromResponse(response, "message");
		status = (restUtils.getValueFromResponse(response, "status")).toString();

		assertEquals(status, "500", "Failed in validating Status");
		assertEquals(message,
				"Request processing failed; nested exception is java.lang.NumberFormatException: For input string: \"\"",
				"The error message is not correct");
	}

	/**
	 * This test will get POD List For Given Project Id
	 * 
	 * @param UserName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsRajatMittal", dataProviderClass = MyTeamDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	@SuppressWarnings("unchecked")
	public void getPODListForGivenProjectId(String userName, String userId) throws Exception {

		MyTeamHelper myTeamHelper = new MyTeamHelper(userName);
		Response response = myTeamHelper.getListOfGloberDetails(userId);
		validateResponseToContinueTest(response, 200, "Project Details list not returned successfully", true);

		List<Integer> projectIds = response.jsonPath().get("projectIds");

		if (projectIds.size() == 0) {
			throw new SkipException(
					"THERE IS NO ANY PROJECT ASSIGNED FOR THIS GLOBER '" + userName + "' TO GET POD DETAILS ");
		}

		int projectId = projectIds.get(new Random().nextInt(projectIds.size()));
		if (projectId == 1000) {
			projectId = projectIds.get(new Random().nextInt(projectIds.size()));
		}

		MyTeamPodHelper myTeamPodHelper = new MyTeamPodHelper(userName);
		Response podDetailsResponse = myTeamPodHelper.getPODListForProject(projectId);
		List<Integer> podIds = (List<Integer>) restUtils.getValueFromResponse(podDetailsResponse,
				"$[?(@.podId != 0)].podId");
		List<Integer> projectsIds = (List<Integer>) restUtils.getValueFromResponse(podDetailsResponse,
				"$[?(@.podId != 0)].projectId");
		List<Integer> clientIds = (List<Integer>) restUtils.getValueFromResponse(podDetailsResponse,
				"$[?(@.podId != 0)].clientId");
		List<String> projectNames = (List<String>) restUtils.getValueFromResponse(podDetailsResponse,
				"$[?(@.podId != 0)].projectName");
		List<String> clientNames = (List<String>) restUtils.getValueFromResponse(podDetailsResponse,
				"$[?(@.podId != 0)].clientName");
		List<String> podNames = (List<String>) restUtils.getValueFromResponse(podDetailsResponse,
				"$[?(@.podId != 0)].podName");
		List<FilterObject> dbPODListForProject = myTeamPodHelper.getPODDetailsForProject(projectId);

		Set<FilterObject> uniqueDataFromDB = new LinkedHashSet<FilterObject>();
		for (int iTemp = 0; iTemp < dbPODListForProject.size(); iTemp++) {
			uniqueDataFromDB.add(dbPODListForProject.get(iTemp));
		}

		assertEquals(podIds.size(), uniqueDataFromDB.size(),
				"FAILED AS THERE IS MISMATCH IN API AND DATABASE RESPONSE");
		Iterator<FilterObject> itr = uniqueDataFromDB.iterator();
		int i = 0;

		while (itr.hasNext()) {
			FilterObject dbElement = itr.next();
			assertTrue(podIds.get(i) == Integer.parseInt(dbElement.getPodId().toString()),
					"Actual podId does not matches with expected podId");
			assertTrue(projectsIds.get(i).equals(Integer.parseInt(dbElement.getProjectId().toString())),
					"Actual projectId does not matches with expected projectId");
			assertTrue(clientIds.get(i).equals(Integer.parseInt(dbElement.getClientId().toString())),
					"Actual clientId does not matches with expected clientId");
			assertTrue(projectNames.get(i).equals(dbElement.getProjectName()),
					"Actual projectName does not matches with expected projectName");
			assertTrue(clientNames.get(i).equals(dbElement.getClientName()),
					"Actual clientName does not matches with expected clientName");
			assertTrue(podNames.get(i).equals(dbElement.getPodName()),
					"Actual podName does not matches with expected podName");
			i++;
		}
	}

	/**
	 * This test will get POD List For Blank Project Id
	 * 
	 * @param UserName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = MyTeamDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression })
	public void getPODListForGivenBlankProjectId(String userName, String userId) throws Exception {

		MyTeamPodHelper myTeamPodHelper = new MyTeamPodHelper(userName);
		Response response = myTeamPodHelper.getPODListForIncorrectProject("");
		status = (restUtils.getValueFromResponse(response, "status")).toString();
		assertEquals(status, "fail", "Failed in validating Status");
	}

	/**
	 * This test will create a Feedback for a glober
	 * 
	 * @param UserName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = MyTeamDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression })
	public void getPODListForGivenInvalidProjectId(String userName, String userId) throws Exception {

		MyTeamPodHelper myTeamPodHelper = new MyTeamPodHelper(userName);
		Response response = myTeamPodHelper.getPODListForIncorrectProject("@$*");
		validateResponseToContinueTest(response, 403, "POD list returned successfully", true);
		status = (restUtils.getValueFromResponse(response, "status")).toString();
		assertEquals(status, "403", "Failed in validating Status");
	}

	/**
	 * This test will verify Newly Added Field For Get Project Members
	 * 
	 * @param UserName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = MyTeamDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void verifyNewlyAddedFieldForGetProjectMembers(String userName, String userId) throws Exception {

		MyTeamHelper myTeamHelper = new MyTeamHelper(userName);
		Response response = myTeamHelper.getListOfGloberDetails(userId);
		validateResponseToContinueTest(response, 200, "Project Details list not returned successfully", true);

		List<Integer> projectIds = response.jsonPath().get("projectIds");
		if (projectIds.size() == 0) {
			throw new SkipException(
					" THERE IS NO ANY PROJECT ASSIGNED TO GLOBER: '" + userName + "' TO GET PROJECT DETAILS");
		}

		int projectId = projectIds.get(0);
		MyTeamProjectMembersHelper myTeamProjectMembersHelper = new MyTeamProjectMembersHelper(userName);
		Response projectMemberList = myTeamProjectMembersHelper.getProjectMembersListForProject(projectId);
		List<String> positionAssignmentTypes = projectMemberList.jsonPath().get("positionAssignmentType");

		for (String positionAssignmentType : positionAssignmentTypes)
			assertTrue(positionAssignmentType != null, "positionAssignmentType field can not be null");

	}

	/**
	 * This test will verify Newly Added Field For Get POD Members
	 * 
	 * @param UserName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = MyTeamDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void verifyNewlyAddedFieldForGetPODMembers(String userName, String userId) throws Exception {

		MyTeamHelper myTeamHelper = new MyTeamHelper(userName);
		Response response = myTeamHelper.getListOfGloberDetails(userId);
		validateResponseToContinueTest(response, 200, "Project Details list not returned successfully", true);
		List<Integer> projectIds = response.jsonPath().get("projectIds");
		List<Integer> podIds = response.jsonPath().get("podIds");

		if (projectIds.size() == 0) {
			throw new SkipException(
					" THERE IS NO ANY PROJECT ASSIGNED TO GLOBER: '" + userName + "' TO GET PROJECT DETAILS");
		}

		int projectId = projectIds.get(0);
		int podId = 0;
		for (int pod : podIds) {
			if (pod != 0) {
				podId = pod;
				break;
			}
		}

		MyTeamProjectMembersHelper myTeamProjectMembersHelper = new MyTeamProjectMembersHelper(userName);
		Response projectMembersForPOD = myTeamProjectMembersHelper.getProjectMembersForPOD(podId, projectId);

		List<String> positionAssignmentTypes = projectMembersForPOD.jsonPath().get("positionAssignmentType");

		for (String positionAssignmentType : positionAssignmentTypes)
			assertTrue(positionAssignmentType != null, "positionAssignmentType field can not be null");

	}

	/**
	 * This test will get All POD Types
	 * 
	 * @param UserName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = MyTeamDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void getAllPODTypes(String userName, String userId) throws Exception {

		MyTeamPodHelper myTeamPodHelper = new MyTeamPodHelper(userName);
		Response response = myTeamPodHelper.getAllPODTypes();
		validateResponseToContinueTest(response, 200, "Project Details list not returned successfully", true);

		List<Integer> podTypeId = response.jsonPath().get("id");
		List<String> podTypeName = response.jsonPath().get("name");
		List<FilterObject> allPodListFromDB = myTeamPodHelper.getAllTypesOfPODSFromDB();

		for (int i = 0; i < allPodListFromDB.size(); i++) {
			assertEquals(podTypeId.get(i), allPodListFromDB.get(i).getPodId());
			assertEquals(podTypeName.get(i), allPodListFromDB.get(i).getPodName());
		}
	}

	/**
	 * This test will get All POD Under Project With Flag
	 * 
	 * @param UserName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = MyTeamDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression })
	public void getAllPODUnderProjectWithFlag(String userName, String userId) throws Exception {

		MyTeamHelper myTeamHelper = new MyTeamHelper(userName);
		Response response = myTeamHelper.getListOfGloberDetails(userId);
		validateResponseToContinueTest(response, 200, "Project Details list not returned successfully", true);

		List<Integer> projectIds = response.jsonPath().get("projectIds");

		if (projectIds.size() == 0) {
			throw new SkipException(
					" THERE IS NO ANY POD ASSIGNED TO GLOBER: '" + userName + "' TO GET PROJECT MEMBERS DETAILS");
		}

		int projectId = projectIds.get(new Random().nextInt(projectIds.size()));
		if (projectId == 1000) {
			projectId = projectIds.get(new Random().nextInt(projectIds.size()));
		}

		MyTeamPodHelper myTeamPodHelper = new MyTeamPodHelper(userName);
		Response emptyPODs = myTeamPodHelper.getAllEmptyPODs(projectId);
		List<String> podNames = emptyPODs.jsonPath().get("podName");
		Collections.sort(podNames);

		for (int i = 0; i < podNames.size(); i++) {
			if (podNames.get(i).equals("Default")) {
				podNames.remove(podNames.get(i));
			}
		}

		List<String> podMaturityId = emptyPODs.jsonPath().get("podMaturityId");
		List<String> podMaturityValue = emptyPODs.jsonPath().get("podMaturityValue");
		assertTrue(podMaturityId.size() >= 0, "podMaturuityId field is not present");
		assertTrue(podMaturityValue.size() >= 0, "podMaturityValue field is not present");

		List<FilterObject> podNamesFromDB = myTeamPodHelper.getAllEmptyPODsUnderProject(projectId);
		for (int i = 0; i < podNamesFromDB.size() - 1; i++) {
			assertTrue(podNamesFromDB.get(i).getPodName().contains(podNames.get(i)));
		}
	}

	/**
	 * This test will create POD Under Project
	 * 
	 * @param UserName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = MyTeamDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void createPODUnderProject(String userName, String userId) throws Exception {

		MyTeamPodHelper myTeamPodHelper = new MyTeamPodHelper(userName);
		List<Object> dataForCreatePOD = myTeamPodHelper.getRequiredTestDataForPOD(userName, userId);
		JSONObject requestParams = new MyTeamPayloadHelper().createPODPayload(dataForCreatePOD);
		Response response = myTeamPodHelper.createPOD(requestParams);
		validateResponseToContinueTest(response, 201,
				"POD " + requestParams.getString("podName") + "created successfully", true);

		message = (String) restUtils.getValueFromResponse(response, "message");
		status = (String) restUtils.getValueFromResponse(response, "statusCode");
		int projectId = requestParams.getInt("projectId");
		List<FilterObject> podDetailsFromDB = myTeamPodHelper.getPODDetailsForProject(projectId,
				requestParams.getString("podName"));

		Response emptyPODs = myTeamPodHelper.getAllEmptyPODs(projectId);
		int iTemp = 0;
		String podNames = (String) response.jsonPath().get("details.podName");
		int podIds = (Integer) response.jsonPath().get("details.podId");
		int podTypeIds = (Integer) response.jsonPath().get("details.podTypeId");
		String podTypeNames = (String) response.jsonPath().get("details.podTypeName");
		int podMaturityId = (Integer) response.jsonPath().get("details.podMaturityId");
		String podMaturityValue = (String) response.jsonPath().get("details.podMaturityValue");
		List<String> emptyPODNames = emptyPODs.jsonPath().get("podName");
		List<String> emptyPODMaturityId = emptyPODs.jsonPath().get("podMaturityId");
		List<String> emptyPODMaturityValue = emptyPODs.jsonPath().get("podMaturityValue");
		assertTrue(emptyPODNames.contains(podNames));
		assertTrue(emptyPODMaturityId.size() >= 0, "podMaturuityId field is not present");
		assertTrue(emptyPODMaturityValue.size() >= 0, "podMaturityValue field is not present");
		assertTrue(message.contains("Pod Successfully Created"), "Failed in POD creation");
		assertTrue(status.contains("CREATED"), "Failed in POD creation");
		assertEquals(podIds, Integer.parseInt(podDetailsFromDB.get(iTemp).getPodId().toString()),
				"Actual podId and expected podId are different");
		assertEquals(podNames, podDetailsFromDB.get(iTemp).getPodName(),
				"Actual podName and expected podName are different");
		assertEquals(podTypeIds, Integer.parseInt(podDetailsFromDB.get(iTemp).getPodType().toString()),
				"Actual podTypeId and expected podTypeId are different");
		assertEquals(podTypeNames, podDetailsFromDB.get(iTemp).getPodTypeName(),
				"Actual podType and expected podType are different");
		assertEquals(podMaturityId, Integer.parseInt(podDetailsFromDB.get(iTemp).getPodMaturityId().toString()),
				"Actual podMaturityId and expected podMaturityId are different");
		assertEquals(podMaturityValue, podDetailsFromDB.get(iTemp).getPodMaturityValue(),
				"Actual podMaturityValue and expected podMaturityValue are different");
		myTeamPodHelper.deletePOD(podIds);
	}

	/**
	 * This test will edit POD Under Project
	 * 
	 * @param UserName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = MyTeamDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void editPODUnderProject(String userName, String userId) throws Exception {

		MyTeamPodHelper myTeamPodHelper = new MyTeamPodHelper(userName);
		List<Object> dataForCreatePOD = myTeamPodHelper.getRequiredTestDataForPOD(userName, userId);
		JSONObject requestParams = new MyTeamPayloadHelper().createPODPayload(dataForCreatePOD);
		Response response = myTeamPodHelper.createPOD(requestParams);
		validateResponseToContinueTest(response, 201,
				"POD " + requestParams.getString("podName") + "created successfully", true);

		int podId = response.jsonPath().get("details.podId");
		List<Object> dataForUpdatePOD = myTeamPodHelper.getRequiredTestDataForUpdatePOD(userName, userId, podId);
		JSONObject updatePODRequestParams = new MyTeamPayloadHelper().updatePODPayload(dataForUpdatePOD);
		Response updatedPODresponse = myTeamPodHelper.updatePOD(updatePODRequestParams, podId);
		message = (String) restUtils.getValueFromResponse(updatedPODresponse, "message");
		String podName = (String) restUtils.getValueFromResponse(updatedPODresponse, "details.podName");
		int podTypeId = (Integer) restUtils.getValueFromResponse(updatedPODresponse, "details.podTypeId");
		String purpose = (String) restUtils.getValueFromResponse(updatedPODresponse, "details.purpose");
		assertTrue(message.contains("Pod Details Updated Successfully"), "Failed to edit a pod");
		assertEquals(podName, updatePODRequestParams.getString("podName"));
		assertEquals(podTypeId, Integer.parseInt(updatePODRequestParams.getString("podTypeId")));
		assertEquals(purpose, updatePODRequestParams.getString("purpose"));
		myTeamPodHelper.deletePOD(updatedPODresponse.jsonPath().get("details.podId"));
	}

	/**
	 * This test will delete Pod
	 * 
	 * @param UserName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = MyTeamDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void deletePod(String userName, String userId) throws Exception {

		MyTeamPodHelper myTeamPodHelper = new MyTeamPodHelper(userName);
		List<Object> dataForCreatePOD = myTeamPodHelper.getRequiredTestDataForPOD(userName, userId);
		JSONObject requestParams = new MyTeamPayloadHelper().createPODPayload(dataForCreatePOD);
		Response response = myTeamPodHelper.createPOD(requestParams);
		validateResponseToContinueTest(response, 201,
				"POD " + requestParams.getString("podName") + "created successfully", true);

		int podId = (Integer) response.jsonPath().get("details.podId");
		Response deleteResponse = myTeamPodHelper.deletePOD(podId);
		status = (String) restUtils.getValueFromResponse(deleteResponse, "status");
		assertTrue(myTeamPodHelper.isPODDeletedFromDB(Integer.toString(podId)), "Data Available in DB");
		assertEquals(status, "success");
	}

	/**
	 * This test will create a Feedback for a glober
	 * 
	 * @param UserName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = MyTeamDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void getProjectDataAsperGivenFilterValue(String userName, String userId) throws Exception {

		MyTeamProjectHelper myTeamProjectHelper = new MyTeamProjectHelper(userName);
		Response response = myTeamProjectHelper.getProjectDetailsAsPerFilter(userId);
		validateResponseToContinueTest(response, 200, "pods for Glober list not returned successfully", true);

		List<FilterObject> projectDataFromDB = myTeamProjectHelper.getProjectDataAsPerFilter(userName);

		for (int i = 0; i < projectDataFromDB.size(); i++) {
			assertTrue(
					response.jsonPath().get("details.projectId").toString()
							.contains(projectDataFromDB.get(i).getProjectId()),
					"projectId is different in JSON Reposonse and DB response");
			assertTrue(
					response.jsonPath().get("details.projectName").toString()
							.contains(projectDataFromDB.get(i).getProjectName()),
					"ProjectName is different in JSON response and DB response ");
		}
	}

	/**
	 * This test will Manages POD by moving to same POD
	 * 
	 * @param UserName
	 * @param userId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = MyTeamDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void verifyPostManagePodMoveToSamePOD(String userName, String userId) throws Exception {

		MyTeamPodHelper myTeamPodHelper = new MyTeamPodHelper(userName);
		Response response = myTeamPodHelper.getAllPODsForGlober(userId);
		validateResponseToContinueTest(response, 200, "POD Details list not returned successfully", true);

		List<Object> dataForManagePOD = myTeamPodHelper.getRequiredTestDataForManagePOD(userName, userId);
		JSONObject requestParams = new MyTeamPayloadHelper().managePODPayload(dataForManagePOD);

		Response projectMemberDetails = myTeamPodHelper.getProjectMemberDetailsWithPagination(userName,
				Integer.parseInt(dataForManagePOD.get(0).toString()));
		List<Integer> globerIds = (List<Integer>) restUtils.getValueFromResponse(projectMemberDetails,
				"$.details[*].globerId");
		int globerId = Utilities.getRandomIdFromList(globerIds);
		String jsonPathForPositionId = "$.[*].[?(@.globerId== '" + globerId + "')].positionId";
		List<Integer> positionIds = (List<Integer>) restUtils.getValueFromResponse(projectMemberDetails,
				jsonPathForPositionId);
		int positionId = positionIds.get(0);

		Map<String, Object> globerPositionIds = new HashMap<String, Object>();
		globerPositionIds.put("globerId", userId);
		globerPositionIds.put("positionId", positionId);
		JSONObject josnGloberPosition = new JSONObject(globerPositionIds);
		List<JSONObject> jsonObjectList = new ArrayList<JSONObject>();
		jsonObjectList.add(josnGloberPosition);
		requestParams.put("globerPositionIds", jsonObjectList);

		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(MyTeamBaseTest.sessionId).contentType(ContentType.JSON).body(requestParams.toString());
		String url = MyTeamBaseTest.baseUrl + MyTeamEndpoints.postManagePOD;
		Response managePODResponse = restUtils.executePOST(requestSpecification, url);
		message = (String) restUtils.getValueFromResponse(managePODResponse, "message");
		status = (String) restUtils.getValueFromResponse(managePODResponse, "status");
		assertTrue(message.contains("Invalid Pod Id"));
		assertEquals(status, "fail");
	}

	/***
	 * This test will verify How It Works Link
	 * 
	 * @param userName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = MyTeamDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void getHowItWorksLink(String userName, String userId) throws Exception {

		MyTeamHelper myTeamHelper = new MyTeamHelper(userName);
		Response response = myTeamHelper.getHowItWorks();
		validateResponseToContinueTest(response, 200, "How It Works Link details not returned successfully", true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		String details = (String) restUtils.getValueFromResponse(response, "details");
		assertEquals(message, "All");
		assertTrue(details.contains("See how this works"));
		assertTrue(details.contains("How this works"));
	}

	/***
	 * This test will get Brief About My Team Module
	 * 
	 * @param userName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsJayantP", dataProviderClass = MyTeamDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void getBriefAboutMYTeamModule(String userName, String userId) throws Exception {

		MyTeamHelper myTeamHelper = new MyTeamHelper(userName);
		Response response = myTeamHelper.getBriefAboutMYTeamModule(userName, userId);
		validateResponseToContinueTest(response, 200, "User is (PM/PA) not able to get the brief about MY Team Module",
				true);
		message = (String) restUtils.getValueFromResponse(response, "message");
		status = (String) restUtils.getValueFromResponse(response, "status");
		assertEquals(message, "User preferences retrieved");
		assertEquals(status, "success");
	}

	/***
	 * This test will verify if the user is able to search
	 * 
	 * @param userName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = MyTeamDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression })
	public void verifyUserIsAbleToSearch(String userName, String userId) throws Exception {

		MyTeamHelper myTeamHelper = new MyTeamHelper(userName);
		Response response = myTeamHelper.getSearchResult();
		validateResponseToContinueTest(response, 200, "User is (PM/PA) not able to Search", true);
		status = (String) restUtils.getValueFromResponse(response, "status");
		assertEquals(status, "SUCCESS");
	}

	/***
	 * This test will Verify if the User is (PM/PA) is able to get the Projects
	 * 
	 * @param userName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsJayantP", dataProviderClass = MyTeamDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void getProjectListForPM(String userName, String userId) throws Exception {

		MyTeamProjectHelper myTeamProjectHelper = new MyTeamProjectHelper(userName);
		Response response = myTeamProjectHelper.getProjectListUnderPM(userId);
		validateResponseToContinueTest(response, 200, "Project list not returned successfully", true);

		List<Integer> projectIds = response.jsonPath().get("details.projectId");
		List<String> projectNames = response.jsonPath().get("details.projectName");

		DDAccessLevelHelper helper = new DDAccessLevelHelper(userName);
		Map<String, List<String>> DBprojectListForDD = helper.getActiveProjectUnderDDFromDB(userId);

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
	 * This test will Verify if the Useris (PM/PA) is able to get the Menu List
	 * 
	 * @param UserName
	 * @param userId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(dataProvider = "pmRolDetailsJayantP", dataProviderClass = MyTeamDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void getMenuListForUser(String userName, String userId) throws Exception {

		MyTeamHelper myTeamHelper = new MyTeamHelper(userName);
		Response response = myTeamHelper.getMenuListForUser();
		validateResponseToContinueTest(response, 200, "Menu list not returned successfully", true);

		List<List<String>> projectMenus = (List<List<String>>) restUtils.getValueFromResponse(response,
				"$.[?(@.menuType=='projectglobercard')].menus");
		List<List<String>> podMenus = (List<List<String>>) restUtils.getValueFromResponse(response,
				"$.[?(@.menuType=='podgloberscard')].menus");
		List<List<String>> viewMenus = (List<List<String>>) restUtils.getValueFromResponse(response,
				"$.[?(@.menuType=='view')].menus");

		List<String> expectedProjectMenus = new ArrayList<String>();
		expectedProjectMenus.add("ASSIGN AS MAIN PM");
		expectedProjectMenus.add("CONFIRM SHADOW");
		expectedProjectMenus.add("ASSIGN AS TL");
		expectedProjectMenus.add("EDIT TL ASSIGNMENT");
		expectedProjectMenus.add("REPLACE");
		expectedProjectMenus.add("ROTATE POSITION");
		List<String> expectedPodMenus = new ArrayList<String>();
		expectedPodMenus.add("ASSIGN AS MAIN PM");
		expectedPodMenus.add("CONFIRM SHADOW");
		expectedPodMenus.add("ASSIGN AS TL");
		expectedPodMenus.add("EDIT TL ASSIGNMENT");
		expectedPodMenus.add("MOVE TO POD");
		expectedPodMenus.add("ADD TO POD");
		expectedPodMenus.add("REPLACE");
		expectedPodMenus.add("ROTATE POSITION");

		assertTrue(projectMenus.get(0).size() == expectedProjectMenus.size(),
				"Project Menus Does not Match with expected");
		assertTrue(podMenus.get(0).size() == expectedPodMenus.size(), "Pod Menus Does not Match with expected");

		for (int iTemp = 0; iTemp < expectedProjectMenus.size(); iTemp++) {
			String projectMenu = projectMenus.get(0).get(iTemp);
			String expectedProjectMenu = expectedProjectMenus.get(iTemp);
			assertEquals(projectMenu, expectedProjectMenu,
					"Project Menu " + projectMenu + " does not match with " + expectedProjectMenu);
		}

		for (int iTemp = 0; iTemp < expectedPodMenus.size(); iTemp++) {
			String podMenu = podMenus.get(0).get(iTemp);
			String expectedPodMenu = expectedPodMenus.get(iTemp);
			assertEquals(podMenu, expectedPodMenu, "Pod Menu " + podMenu + " does not match with " + expectedPodMenu);
		}

	}

	/***
	 * This test will verify if User Is Able To Get Filters For Projects
	 * 
	 * @param userName
	 * @param userId
	 * @throws Exception
	 */
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = MyTeamDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression })
	public void verifyUserIsAbleToGetFiltersForProjects(String userName, String userId) throws Exception {

		MyTeamProjectHelper myTeamProjectHelper = new MyTeamProjectHelper(userName);
		Response response = myTeamProjectHelper.getUserFilterForProject(userId);
		validateResponseToContinueTest(response, 204, "User is (PM/PA) able to return result for Filter for Project",
				true);
		try {
			status = (String) restUtils.getValueFromResponse(response, "status");
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(status, null, "Response is not blank");
	}

	/***
	 * This test will Get POD details
	 * 
	 * @param userName
	 * @param userId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(dataProvider = "pmRolDetailsRajatMittal", dataProviderClass = MyTeamDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void getPODDetails(String userName, String userId) throws Exception {

		MyTeamPodHelper myTeamPodHelper = new MyTeamPodHelper(userName);
		Response response = myTeamPodHelper.getPODDetails();
		validateResponseToContinueTest(response, 200, "User is (PM/PA) is not able to get POD Details", true);
		List<String> podDetails = (List<String>) restUtils.getValueFromResponse(response, "$.[*].name");
		List<String> expectedPODTypes = new ArrayList<String>();
		expectedPODTypes.add("Delivery POD");
		expectedPODTypes.add("Governance POD");
		expectedPODTypes.add("Sub Team");

		assertTrue(podDetails.size() == expectedPODTypes.size(), "Pod Details Does not Match with expected");

		for (int iTemp = 0; iTemp < expectedPODTypes.size(); iTemp++) {
			String podName = podDetails.get(iTemp);
			String expectedPodName = expectedPODTypes.get(iTemp);
			assertEquals(podName, expectedPodName,
					"Project Menu " + podName + " does not match with " + expectedPodName);
		}
	}

	/***
	 * This test will Get POD details
	 * 
	 * @param userName
	 * @param userId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(dataProvider = "pmRolDetailsRajatMittal", dataProviderClass = MyTeamDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void verifyGloberHasExistingRepalcement(String userName, String userId) throws Exception {

		MyTeamPodHelper myTeamPodHelper = new MyTeamPodHelper(userName);
		int projectId = myTeamPodHelper.getProjectIdOfGlober(userName, userId);
		Response projectMemberDetails = myTeamPodHelper.getProjectMemberDetailsWithPagination(userName, projectId);
		String jsonPathForPositionId = "$.[*].[?(@.globerId== '" + userId + "')].positionId";
		List<Integer> positionIds = (List<Integer>) restUtils.getValueFromResponse(projectMemberDetails,
				jsonPathForPositionId);
		int positionId = positionIds.get(0);

		Response response = myTeamPodHelper.getGloberHasExistingReplacement(userId, positionId, projectId);
		validateResponseToContinueTest(response, 200, "Glober does not have Existing Repalcement", true);
		status = (String) restUtils.getValueFromResponse(response, "status");
		String statusCode = (String) restUtils.getValueFromResponse(response, "statusCode");
		assertEquals(status, "success");
		assertEquals(statusCode, "OK");
	}

	/***
	 * This test will Get POD Assessment
	 * 
	 * @param userName
	 * @param userId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(dataProvider = "pmRolDetailsRajatMittal", dataProviderClass = MyTeamDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void getPODAssessment(String userName, String userId) throws Exception {

		MyTeamPodHelper myTeamPodHelper = new MyTeamPodHelper(userName);
		Response response = myTeamPodHelper.getPODAssessment();
		validateResponseToContinueTest(response, 200, "POD assessment does not success", true);
		status = (String) restUtils.getValueFromResponse(response, "status");
		assertEquals(status, "success");
	}

	/**
	 * This test will add Glober to POD
	 * 
	 * @param UserName
	 * @param userId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = MyTeamDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void addToPOD(String userName, String userId) throws Exception {

		MyTeamPodHelper myTeamPodHelper = new MyTeamPodHelper(userName);
		int projectId = myTeamPodHelper.getProjectIdOfGlober(userName, userId);
		Response response = myTeamPodHelper.getAllEmptyPODs(projectId);

		List<Integer> podIds = response.jsonPath().get("podId");
		int sourcePodId = podIds.stream().filter(pod -> pod != 0).findFirst().get();
		int globerId = myTeamPodHelper.getPodMemberIdByPodId(projectId, sourcePodId);

		List<Object> dataForCreatePOD = Arrays.asList("autoPod", "1", "Created for automation Testing", projectId);
		JSONObject requestParams = new MyTeamPayloadHelper().createPODPayload(dataForCreatePOD);
		Response responsePODCreate = myTeamPodHelper.createPOD(requestParams);
		int targetPODId = (Integer) responsePODCreate.jsonPath().get("details.podId");
		List<Object> dataForManagePOD = Arrays.asList(projectId, sourcePodId, targetPODId, "true", globerId);
		JSONObject requestParamsAddToPOD = new MyTeamPayloadHelper().managePODPayload(dataForManagePOD);

		Response projectMemberDetails = myTeamPodHelper.getProjectMemberDetailsWithPagination(userName,
				Integer.parseInt(dataForManagePOD.get(0).toString()));

		String jsonPathForPositionId = "$.[*].[?(@.globerId== '" + globerId + "')].positionId";
		List<Integer> positionIds = (List<Integer>) restUtils.getValueFromResponse(projectMemberDetails,
				jsonPathForPositionId);
		int positionId = positionIds.get(0);

		Map<String, Object> globerPositionIds = new HashMap<String, Object>();
		globerPositionIds.put("globerId", globerId);
		globerPositionIds.put("positionId", positionId);
		JSONObject josnGloberPosition = new JSONObject(globerPositionIds);
		List<JSONObject> jsonObjectList = new ArrayList<JSONObject>();
		jsonObjectList.add(josnGloberPosition);
		requestParamsAddToPOD.put("globerPositionIds", jsonObjectList);

		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(MyTeamBaseTest.sessionId).contentType(ContentType.JSON)
				.body(requestParamsAddToPOD.toString());
		String url = MyTeamBaseTest.baseUrl + MyTeamEndpoints.postManagePOD;
		Response managePODResponse = restUtils.executePOST(requestSpecification, url);
		message = (String) restUtils.getValueFromResponse(managePODResponse, "message");
		status = (String) restUtils.getValueFromResponse(managePODResponse, "status");
		assertTrue(message.contains("Has Been Duplicated To autoPod"));
		assertEquals(status, "success", "Response status is not success");
		myTeamPodHelper.deletePOD(targetPODId);
	}

	/**
	 * This test will Move Glober to POD
	 * 
	 * @param UserName
	 * @param userId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = MyTeamDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void moveToPOD(String userName, String userId) throws Exception {

		MyTeamPodHelper myTeamPodHelper = new MyTeamPodHelper(userName);
		int projectId = myTeamPodHelper.getProjectIdOfGlober(userName, userId);
		Response response = myTeamPodHelper.getAllEmptyPODs(projectId);

		List<Integer> podIds = response.jsonPath().get("podId");
		int sourcePodId = podIds.stream().filter(pod -> pod != 0).findFirst().get();
		int globerId = myTeamPodHelper.getPodMemberIdByPodId(projectId, sourcePodId);

		List<Object> dataForCreatePOD = Arrays.asList("autoPod", "1", "Created for automation Testing", projectId);
		JSONObject requestParams = new MyTeamPayloadHelper().createPODPayload(dataForCreatePOD);
		Response responsePODCreate = myTeamPodHelper.createPOD(requestParams);
		int targetPODId = (Integer) responsePODCreate.jsonPath().get("details.podId");
		List<Object> dataForManagePOD = Arrays.asList(projectId, sourcePodId, targetPODId, "false", globerId);
		JSONObject requestParamsAddToPOD = new MyTeamPayloadHelper().managePODPayload(dataForManagePOD);

		Response projectMemberDetails = myTeamPodHelper.getProjectMemberDetailsWithPagination(userName,
				Integer.parseInt(dataForManagePOD.get(0).toString()));

		String jsonPathForPositionId = "$.[*].[?(@.globerId== '" + globerId + "')].positionId";
		List<Integer> positionIds = (List<Integer>) restUtils.getValueFromResponse(projectMemberDetails,
				jsonPathForPositionId);
		int positionId = positionIds.get(0);

		Map<String, Object> globerPositionIds = new HashMap<String, Object>();
		globerPositionIds.put("globerId", globerId);
		globerPositionIds.put("positionId", positionId);
		JSONObject josnGloberPosition = new JSONObject(globerPositionIds);
		List<JSONObject> jsonObjectList = new ArrayList<JSONObject>();
		jsonObjectList.add(josnGloberPosition);
		requestParamsAddToPOD.put("globerPositionIds", jsonObjectList);

		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(MyTeamBaseTest.sessionId).contentType(ContentType.JSON)
				.body(requestParamsAddToPOD.toString());
		String url = MyTeamBaseTest.baseUrl + MyTeamEndpoints.postManagePOD;
		Response managePODResponse = restUtils.executePOST(requestSpecification, url);
		message = (String) restUtils.getValueFromResponse(managePODResponse, "message");
		status = (String) restUtils.getValueFromResponse(managePODResponse, "status");
		assertTrue(message.contains("Has Been Moved To autoPod"));
		assertEquals(status, "success","Response status is not success");

		// POst Condition
		List<Object> dataForMoveGloberBack = Arrays.asList(projectId, targetPODId, sourcePodId, "false", globerId);
		JSONObject requestParamsMoveBack = new MyTeamPayloadHelper().managePODPayload(dataForMoveGloberBack);
		requestParamsMoveBack.put("globerPositionIds", jsonObjectList);
		RequestSpecification requestSpecificationToMoveBack = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(MyTeamBaseTest.sessionId).contentType(ContentType.JSON)
				.body(requestParamsMoveBack.toString());
		restUtils.executePOST(requestSpecificationToMoveBack, url);
		myTeamPodHelper.deletePOD(targetPODId);
	}

	/***
	 * This test will Get Filter Project Status
	 * 
	 * @param userName
	 * @param userId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = MyTeamDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void getFilterProjectStatus(String userName, String userId) throws Exception {

		MyTeamProjectHelper myTeamProjectHelper = new MyTeamProjectHelper(userName);
		Response response = myTeamProjectHelper.getProjectStatusFilter(userId);
		validateResponseToContinueTest(response, 200, "Project status filter returned successfully", true);
		
		Map<String, List<String>> projectStatesList = myTeamProjectHelper.getProjectStates();
		List<String> expectedProjectStates=projectStatesList.get("project_state");
		Collections.sort(expectedProjectStates);

		String jsonPath = "$.details[0].projectStates[*].id";
		List<String> projectStates = (List<String>) restUtils.getValueFromResponse(response, jsonPath);
		Collections.sort(projectStates);
		
		assertTrue(expectedProjectStates.size()==projectStates.size(),"API response does not match with the DB result");
		
		for (int iTemp = 0; iTemp < expectedProjectStates.size(); iTemp++) {
			assertEquals(expectedProjectStates.get(iTemp), projectStates.get(iTemp), "Project Status "
					+ expectedProjectStates.get(iTemp) + " does not match with " + projectStates.get(iTemp));
		}
	}

	/***
	 * This test will Get Assignments Of Glober Or POD
	 * 
	 * @param userName
	 * @param userId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = MyTeamDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void getAssignmentsOfGloberOrPOD(String userName, String userId) throws Exception {

		MyTeamPodHelper myTeamPodHelper = new MyTeamPodHelper(userName);
		Response response = myTeamPodHelper.getAssignmentOfGloberOrPOD(userId);
		validateResponseToContinueTest(response, 200, "Assignment of Glober or POD return successfully", true);

		List<Integer> projectIds = (List<Integer>) restUtils.getValueFromResponse(response, "$.[*].projectId");
		List<String> projectNames = (List<String>) restUtils.getValueFromResponse(response, "$.[*].projectName");
		List<Integer> clientIds = (List<Integer>) restUtils.getValueFromResponse(response, "$.[*].clientId");
		List<String> clientNames = (List<String>) restUtils.getValueFromResponse(response, "$.[*].clientName");
		List<Integer> podIds = (List<Integer>) restUtils.getValueFromResponse(response, "$.[*].podId");
		List<String> podNames = (List<String>) restUtils.getValueFromResponse(response, "$.[*].podName");

		List<FilterObject> projectAndClient = myTeamPodHelper.getProjectAndClientForGlober(userId);
		List<FilterObject> podsForGlober =myTeamPodHelper.getPODsForGlober(userId);

		Iterator<FilterObject> itr = projectAndClient.iterator();
		while (itr.hasNext()) {
			FilterObject dbElement = itr.next();
			assertTrue(projectIds.contains(Integer.parseInt(dbElement.getProjectId().toString())),
					"Actual projectId does not matches with expected projectId");
			assertTrue(projectNames.contains(dbElement.getProjectName()),
					"Actual projectNames does not matches with expected projectNames");
			assertTrue(clientIds.contains(Integer.parseInt(dbElement.getClientId().toString())),
					"Actual clientId does not matches with expected clientId");
			assertTrue(clientNames.contains(dbElement.getClientName()),
					"Actual clientName does not matches with expected clientName");
		}
		
		Iterator<FilterObject> podItr = podsForGlober.iterator();
		while (podItr.hasNext()) {
			FilterObject dbElement = podItr.next();
			assertTrue(podIds.contains(Integer.parseInt(dbElement.getPodId().toString())),
					"Actual podId does not matches with expected podId");
			assertTrue(podNames.contains(dbElement.getPodName()),
					"Actual podNames does not matches with expected podNames");
		}
	}

	/***
	 * This test will Get Project Members
	 * 
	 * @param userName
	 * @param userId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(dataProvider = "pmRolDetailsWithId", dataProviderClass = MyTeamDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void getProjectMembers(String userName, String userId) throws Exception {

		MyTeamProjectHelper myTeamProjectHelper = new MyTeamProjectHelper(userName);
		Response response = myTeamProjectHelper.getProjectListUnderPM(userId);
		validateResponseToContinueTest(response, 200, "Project list not returned successfully", true);

		List<Integer> projectIds = response.jsonPath().get("details.projectId");

		int projectId = projectIds.get(0);
		Response projectMembers = myTeamProjectHelper.getProjectMembersForUser(Integer.toString(projectId));
		validateResponseToContinueTest(projectMembers, 200, "Project Members return successfully", true);

		List<String> firstName = (List<String>) restUtils.getValueFromResponse(projectMembers,
				"$.details[*].firstName");
		List<String> lastName = (List<String>) restUtils.getValueFromResponse(projectMembers,
				"$.details[*].lastName");
		List<String> email = (List<String>) restUtils.getValueFromResponse(projectMembers,
				"$.details[*].email");
		List<String> gender = (List<String>) restUtils.getValueFromResponse(projectMembers,
				"$.details[*].gender");
		List<String> projectPosition = (List<String>) restUtils.getValueFromResponse(projectMembers,
				"$.details[*].projectPosition");
		List<String> city = (List<String>) restUtils.getValueFromResponse(projectMembers,
				"$.details[*].city");
		List<String> country = (List<String>) restUtils.getValueFromResponse(projectMembers,
				"$.details[*].country");

		MyTeamProjectMembersHelper myTeamProjectMembersHelper = new MyTeamProjectMembersHelper(userName);
		List<FilterObject> projectAndClient = myTeamProjectMembersHelper.getProjectMembersDetailsForPOD(projectId);

		assertTrue(projectAndClient.size() == firstName.size(), "API response does not match with DB result");

		for (int iTemp = 0; iTemp < projectAndClient.size(); iTemp++) {
			assertTrue(projectAndClient.get(iTemp).getFirstName().equals(firstName.get(iTemp)), "Project member in DB "
					+ projectAndClient.get(iTemp) + " does not match with api response " + firstName.get(iTemp));
			assertTrue(projectAndClient.get(iTemp).getLastName().equals(lastName.get(iTemp)), "Project member in DB "
					+ projectAndClient.get(iTemp).getLastName() + " does not match with api response " + lastName.get(iTemp));
			assertTrue(projectAndClient.get(iTemp).getEmail().equals(email.get(iTemp)), "Project member in DB "
					+ projectAndClient.get(iTemp).getEmail() + " does not match with api response " + email.get(iTemp));
			assertTrue(projectAndClient.get(iTemp).getGender().equals(gender.get(iTemp)), "Project member in DB "
					+ projectAndClient.get(iTemp).getGender() + " does not match with api response " + gender.get(iTemp));
			assertTrue(projectAndClient.get(iTemp).getProjectPosition().equals(projectPosition.get(iTemp)), "Project member in DB "
					+ projectAndClient.get(iTemp).getProjectPosition() + " does not match with api response " + projectPosition.get(iTemp));
			assertTrue(projectAndClient.get(iTemp).getCity().equals(city.get(iTemp)), "Project member in DB "
					+ projectAndClient.get(iTemp).getCity() + " does not match with api response " + city.get(iTemp));
			assertTrue(projectAndClient.get(iTemp).getCountry().equals(country.get(iTemp)), "Project member in DB "
					+ projectAndClient.get(iTemp).getCountry() + " does not match with api response " + country.get(iTemp));
		}
	}
}
