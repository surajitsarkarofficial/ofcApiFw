package tests.testcases.myTeam.features;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import dataproviders.myTeam.features.ElasticSearchDataProviders;
import io.restassured.response.Response;
import tests.testcases.myTeam.MyTeamBaseTest;
import tests.testhelpers.myTeam.features.ElasticSearchTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;
import utils.RestUtils;

/**
 * @author imran.khan
 *
 */

public class ElasticSearchTests extends MyTeamBaseTest {
	String message, status = null;
	String podId;
	String[] data;
	String globerFirstName, globerLastName;
	int globerId;
	Set<String> projectNamesFromApi, podNamesFromApi, senioritysFromApi, srNumberFromApi, globersFromApi;

	RestUtils restUtils = new RestUtils();

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("ElasticSearchTests");
	}

	/**
	 * This test will search by glober name
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(dataProvider = "pmRolDetailsJayantP", dataProviderClass = ElasticSearchDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void searchUsingGloberName(String userName, String userId) throws Exception {

		ElasticSearchTestHelper searchHelper = new ElasticSearchTestHelper(userName);
		String globerName = searchHelper.getGloberFullName(userName, userId);
		Response response = searchHelper.searchGloberByGivenData(globerName);
		List<String> globerNames = (List<String>) restUtils.getValueFromResponse(response, "$.details[*].name");
		List<Integer> globerIds = (List<Integer>) restUtils.getValueFromResponse(response, "$.details[*].id");
		for (int i = 0; i < globerNames.size(); i++) {
			globerName = globerNames.get(i);
			globerId = globerIds.get(i);
			String globerNameFromDB = searchHelper.getGloberNameByDb(Integer.toString(globerId));
			assertEquals(globerName, globerNameFromDB, "Error with user name " + userName);
		}
		test.log(Status.PASS, "glober searched by name successfully");
	}

	/**
	 * This test will search by glober's first name
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(dataProvider = "pmRolDetailsJayantP", dataProviderClass = ElasticSearchDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void searchUsingGloberFirstName(String userName, String userId) throws Exception {

		ElasticSearchTestHelper searchHelper = new ElasticSearchTestHelper(userName);
		String globerName = searchHelper.getGloberFullName(userName, userId);
		data = globerName.split(" ");
		globerFirstName = data[0];
		Response response = searchHelper.searchGloberByGivenData(globerFirstName);
		List<String> globerNames = (List<String>) restUtils.getValueFromResponse(response, "$.details[*].name");
		List<Integer> globerIds = (List<Integer>) restUtils.getValueFromResponse(response, "$.details[*].id");
		for (int i = 0; i < globerNames.size(); i++) {
			globerName = globerNames.get(i);
			globerId = globerIds.get(i);
			String globerNameFromDB = searchHelper.getGloberNameByDb(Integer.toString(globerId));
			assertEquals(globerName, globerNameFromDB, "Error with user name " + userName);
		}
		test.log(Status.PASS, "glober searched by first name successfully");
	}

	/**
	 * This test will search by glober's last name
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(dataProvider = "pmRolDetailsJayantP", dataProviderClass = ElasticSearchDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void searchUsingGloberLastName(String userName, String userId) throws Exception {

		ElasticSearchTestHelper searchHelper = new ElasticSearchTestHelper(userName);
		String globerName = searchHelper.getGloberFullName(userName, userId);
		data = globerName.split(" ");
		globerLastName = data[data.length - 1];
		Response response = searchHelper.searchGloberByGivenData(globerLastName);
		List<String> globerNames = (List<String>) restUtils.getValueFromResponse(response, "$.details[*].name");
		List<Integer> globerIds = (List<Integer>) restUtils.getValueFromResponse(response, "$.details[*].id");
		for (int i = 0; i < globerNames.size(); i++) {
			globerName = globerNames.get(i);
			globerId = globerIds.get(i);
			String globerNameFromDB = searchHelper.getGloberNameByDb(Integer.toString(globerId));
			assertEquals(globerName, globerNameFromDB, "Error with user name " + userName);
		}
		test.log(Status.PASS, "glober searched by last name successfully");
	}

	/**
	 * This test will search by project name
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(dataProvider = "pmRolDetailsJayantP", dataProviderClass = ElasticSearchDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void searchUsingProjectName(String userName, String userId) throws Exception {

		ElasticSearchTestHelper searchHelper = new ElasticSearchTestHelper(userName);
		String projectName = searchHelper.getProjectNameOfGlober(userId);
		String positionName = searchHelper.getPositionNameByLoggedInUserId(userId);
		Response response = searchHelper.searchGloberByGivenData(projectName);
		List<String> projectNames = (List<String>) restUtils.getValueFromResponse(response, "$.details[*].name");
		List<String> typeOfResult = (List<String>) restUtils.getValueFromResponse(response, "$.details[*].type");
		projectNamesFromApi = new HashSet<String>();
		for (int i = 0; i < typeOfResult.size(); i++) {
			if (typeOfResult.get(i).equals("PROJECT")) {
				projectNamesFromApi.add(projectNames.get(i));
			}
		}
		Set<String> projectNamesFromDB = searchHelper.getProjectNameByUserIdFromDb(userId, positionName, projectName);
		assertEquals(projectNamesFromApi, projectNamesFromDB, "Error with user name " + userName);
		test.log(Status.PASS, "searched by project name successfully");
	}

	/**
	 * This test will search by pod name
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(dataProvider = "pmRolDetailsJayantP", dataProviderClass = ElasticSearchDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void searchUsingPodName(String userName, String userId) throws Exception {

		ElasticSearchTestHelper searchHelper = new ElasticSearchTestHelper(userName);
		String podName = searchHelper.getPodName(userName, userId);
		String positionName = searchHelper.getPositionNameByLoggedInUserId(userId);
		Response response = searchHelper.searchGloberByGivenData(podName);
		List<String> podNames = (List<String>) restUtils.getValueFromResponse(response, "$.details[*].name");
		List<String> typeOfResult = (List<String>) restUtils.getValueFromResponse(response, "$.details[*].type");
		podNamesFromApi = new HashSet<String>();
		for (int i = 0; i < typeOfResult.size(); i++) {
			if (typeOfResult.get(i).equals("POD")) {
				podNamesFromApi.add(podNames.get(i));
			}
		}
		Set<String> podNamesFromDB = searchHelper.getPodNameByUserIdFromDb(userId, positionName, podName);
		assertEquals(podNamesFromApi, podNamesFromDB, "Error with user name " + userName);
		test.log(Status.PASS, "searched by pod name successfully");
	}

	/**
	 * This test will search by seniority
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(dataProvider = "pmRolDetailsJayantP", dataProviderClass = ElasticSearchDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void searchUsingSeniority(String userName, String userId) throws Exception {

		ElasticSearchTestHelper searchHelper = new ElasticSearchTestHelper(userName);
		String seniority = searchHelper.getSeniority(userName, userId);
		String positionName = searchHelper.getPositionNameByLoggedInUserId(userId);
		Response response = searchHelper.searchGloberByGivenData(seniority);
		List<String> senioritys = (List<String>) restUtils.getValueFromResponse(response, "$.details[*].srNumber");
		senioritysFromApi = new HashSet<String>();
		for (int i = 0; i < senioritys.size(); i++) {
			senioritysFromApi.add(senioritys.get(i).substring(3));
		}
		Set<String> senioritysFromDB = searchHelper.getSRNumberBySeniority(userId, positionName, seniority);
		assertEquals(senioritysFromApi, senioritysFromDB, "Error with user name " + userName);
		test.log(Status.PASS, "searched by seniority successfully");
	}

	/**
	 * This test will search by positionName
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(dataProvider = "pmRolDetailsJayantP", dataProviderClass = ElasticSearchDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void searchUsingPositionName(String userName, String userId) throws Exception {

		ElasticSearchTestHelper searchHelper = new ElasticSearchTestHelper(userName);
		String position = searchHelper.getPositionName(userName, userId);
		String positionName = searchHelper.getPositionNameByLoggedInUserId(userId);
		Response response = searchHelper.searchGloberByGivenData(position);
		List<String> positionNames = (List<String>) restUtils.getValueFromResponse(response, "$.details[*].srNumber");
		srNumberFromApi = new HashSet<String>();
		for (int i = 0; i < positionNames.size(); i++) {
			srNumberFromApi.add(positionNames.get(i).substring(3));
		}
		Set<String> srNumberFromDb = searchHelper.getSRNumberByPosition(userId, positionName, position);
		assertEquals(srNumberFromApi, srNumberFromDb, "Error with user name " + userId);
		test.log(Status.PASS, "searched by posiition name successfully");
	}

	/**
	 * This test will search by glober key
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(dataProvider = "pmRolDetailsJayantP", dataProviderClass = ElasticSearchDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void searchUsingGloberKeyword(String userName, String userId) throws Exception {

		ElasticSearchTestHelper searchHelper = new ElasticSearchTestHelper(userName);
		String globerName = searchHelper.getGloberFullName(userName, userId);
		String positionName = searchHelper.getPositionNameByLoggedInUserId(userId);
		String keyword = globerName.substring(0, 3);
		Response response = searchHelper.searchGloberByGivenData(keyword);
		List<String> globerNames = (List<String>) restUtils.getValueFromResponse(response, "$.details[*].name");
		List<String> typeOfglobers = (List<String>) restUtils.getValueFromResponse(response, "$.details[*].type");
		globersFromApi = new HashSet<String>();
		for (int i = 0; i < typeOfglobers.size(); i++) {
			if (typeOfglobers.get(i).equals("GLOBER")) {
				globersFromApi.add(globerNames.get(i));
			}
		}
		Set<String> globersFromDb = searchHelper.getGloberNameByKeyFromDb(userId, positionName, keyword);
		assertEquals(globersFromApi, globersFromDb, "Error with user name " + userName);
		test.log(Status.PASS, "searched by glober keyword successfully");
	}

	/**
	 * This test will search by project key
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(dataProvider = "pmRolDetailsJayantP", dataProviderClass = ElasticSearchDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void searchUsingProjectKeyword(String userName, String userId) throws Exception {

		ElasticSearchTestHelper searchHelper = new ElasticSearchTestHelper(userName);
		String projectName = searchHelper.getProjectNameOfGlober(userId);
		String positionName = searchHelper.getPositionNameByLoggedInUserId(userId);
		String keyword = projectName.substring(0, 3);
		Response response = searchHelper.searchGloberByGivenData(keyword);
		List<String> projectNames = (List<String>) restUtils.getValueFromResponse(response, "$.details[*].name");
		List<String> typeOfResult = (List<String>) restUtils.getValueFromResponse(response, "$.details[*].type");
		projectNamesFromApi = new HashSet<String>();
		for (int i = 0; i < typeOfResult.size(); i++) {
			if (typeOfResult.get(i).equals("PROJECT")) {
				projectNamesFromApi.add(projectNames.get(i));
			}
		}
		Set<String> projectNamesFromDB = searchHelper.getProjectNameByKeyFromDb(userId, positionName, keyword);
		assertEquals(projectNamesFromApi, projectNamesFromDB, "Error with user name " + userName);
		test.log(Status.PASS, "searched by project keyword successfully");
	}

	/**
	 * This test will search by pod key
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(dataProvider = "pmRolDetailsJayantP", dataProviderClass = ElasticSearchDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void searchUsingPodKey(String userName, String userId) throws Exception {

		ElasticSearchTestHelper searchHelper = new ElasticSearchTestHelper(userName);
		String podName = searchHelper.getPodName(userName, userId);
		String positionName = searchHelper.getPositionNameByLoggedInUserId(userId);
		String keyword = podName.substring(0, 3);
		Response response = searchHelper.searchGloberByGivenData(keyword);
		List<String> podNames = (List<String>) restUtils.getValueFromResponse(response, "$.details[*].name");
		List<String> typeOfResult = (List<String>) restUtils.getValueFromResponse(response, "$.details[*].type");
		podNamesFromApi = new HashSet<String>();
		for (int i = 0; i < typeOfResult.size(); i++) {
			if (typeOfResult.get(i).equals("POD")) {
				podNamesFromApi.add(podNames.get(i));
			}
		}
		Set<String> podNamesFromDB = searchHelper.getPodNameByUserIdFromDb(userId, positionName, keyword);
		assertEquals(podNamesFromApi, podNamesFromDB, "Error with user name " + userName);
		test.log(Status.PASS, "searched by pod name successfully");
	}

	/**
	 * This test will search by sr number
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(dataProvider = "pmRolDetailsJayantP", dataProviderClass = ElasticSearchDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void searchUsingSrNumber(String userName, String userId) throws Exception {

		ElasticSearchTestHelper searchHelper = new ElasticSearchTestHelper(userName);
		int srNumber = searchHelper.getSrNumberByUserId(userId);
		String positionName = searchHelper.getPositionNameByLoggedInUserId(userId);
		Response response = searchHelper.searchGloberByGivenData(Integer.toString(srNumber));
		List<String> srNumbers = (List<String>) restUtils.getValueFromResponse(response, "$.details[*].srNumber");
		srNumberFromApi = new HashSet<String>();
		for (int i = 0; i < srNumbers.size(); i++) {
			srNumberFromApi.add(srNumbers.get(i).substring(3));
		}
		Set<String> srNumberFromDb = searchHelper.getSRNumberBySeniority(userId, positionName,
				Integer.toString(srNumber));
		assertEquals(srNumberFromApi, srNumberFromDb, "Error with user name " + userName);
		test.log(Status.PASS, "searched by seniority successfully");
	}

	/**
	 * This test will search by wrong data
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(dataProvider = "pmRolDetailsJayantP", dataProviderClass = ElasticSearchDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void searchUsingWrongData(String userName, String userId) throws Exception {

		ElasticSearchTestHelper searchHelper = new ElasticSearchTestHelper(userName);
		Response response = searchHelper.searchGloberByGivenData("srytuyik");
		List<String> listOfresults = (List<String>) restUtils.getValueFromResponse(response, "$.details[*].name");
		assertTrue(listOfresults.size()==0, "Error with users" + userName);
		test.log(Status.PASS, "glober searched by name successfully");
	}
	
	/**
	 * This test will search by white space
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(dataProvider = "pmRolDetailsJayantP", dataProviderClass = ElasticSearchDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void searchUsingWhiteSpace(String userName, String userId) throws Exception {

		ElasticSearchTestHelper searchHelper = new ElasticSearchTestHelper(userName);
		Response response = searchHelper.searchGloberByGivenData(" ");
		List<String> listOfresults = (List<String>) restUtils.getValueFromResponse(response, "$.details[*].name");
		assertTrue(listOfresults.size()==0, "Error with users" + userName);
		test.log(Status.PASS, "glober searched by name successfully");
	}
	
	/**
	 * This test will search by Special Character
	 * 
	 * @param UserName and userId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(dataProvider = "pmRolDetailsJayantP", dataProviderClass = ElasticSearchDataProviders.class, enabled = true, priority = 0, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void searchUsingSpecialCharacter(String userName, String userId) throws Exception {

		ElasticSearchTestHelper searchHelper = new ElasticSearchTestHelper(userName);
		Response response = searchHelper.searchGloberByGivenData("!@#$%");
		List<String> listOfresults = (List<String>) restUtils.getValueFromResponse(response, "$.details[*].name");
		assertTrue(listOfresults.size()==0, "Error with users" + userName);
		test.log(Status.PASS, "glober searched by name successfully");
	}
	
}
