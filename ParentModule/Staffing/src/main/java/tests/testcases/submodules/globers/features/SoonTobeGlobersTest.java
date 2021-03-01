package tests.testcases.submodules.globers.features;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.google.common.base.CharMatcher;
import com.google.gson.Gson;

import dataproviders.StaffingDataProviders;
import dataproviders.submodules.globers.features.SoonTobeGlobersDataProvider;
import dto.submodules.globers.AgeingFilterObjects;
import dto.submodules.globers.CommentsSoonToBeGlobersObjects;
import dto.submodules.globers.GloberAvailabilityObjects;
import dto.submodules.globers.GloberStatusObjects;
import dto.submodules.globers.HandlersObjects;
import dto.submodules.globers.LeadersObjects;
import dto.submodules.globers.LocationsObjects;
import dto.submodules.globers.PositionsObjects;
import dto.submodules.globers.SeniorityFilterSoonToBeGloberObjects;
import dto.submodules.globers.SkillsObjects;
import dto.submodules.globers.SoonToBeGloberDetailsObjects;
import dto.submodules.globers.StatusGridSoonToBeGloberObjects;
import dto.submodules.globers.StudiosObjects;
import dto.submodules.globers.soonTobeGlobers;
import io.restassured.response.Response;
import payloads.submodules.globers.SoonTobePayloadHelper;
import tests.testcases.submodules.globers.GlobersBaseTest;
import tests.testhelpers.submodules.globers.features.SoonTobeGloberTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;
import utils.RestUtils;

/**
 * 
 * @author pornima.chakurkar
 *
 */
public class SoonTobeGlobersTest extends GlobersBaseTest {

	String statusCode, status = null, message;
	RestUtils restUtils = new RestUtils();
	Response response;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("Soon To be Globers");
	}

	/**
	 * This test will verify list of globers type in filters based on talent pool
	 * data
	 * 
	 * @param name
	 * @param STGType
	 * @throws Exception
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true, groups = {
			ExeGroups.Regression, "Glow-II" })
	public void getFiltersSoonToBeGlobersTalenPoolGrids(String name, String stgType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		List<soonTobeGlobers> listOfSoonToBeGloberTypeDb = null;
		List<soonTobeGlobers> listOfsoonToBeGloberTypeJson = null;

		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);
		response = stgTestHelper.getFiltersSoonToBeGlobersTalenPoolGrids(response, name);

		validateResponseToContinueTest(response, 200, "Unable to get globers list from talent pool.", true);

		listOfsoonToBeGloberTypeJson = response.jsonPath().get("details.id");

		listOfSoonToBeGloberTypeDb = stgTestHelper.getListOfSoonToBeGloberTypeDb();

		softAssert.assertEquals(listOfsoonToBeGloberTypeJson.size(), listOfSoonToBeGloberTypeDb.size(),
				"Error: response and query have different size");

		softAssert.assertAll();
	}

	/**
	 * This test will verify talent pool grid data after applying the position
	 * filter
	 * 
	 * @param name
	 * @param STGType
	 * @throws Exception
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true, groups = {
			ExeGroups.Regression, "Glow-II" })
	public void getSoonToBeGloberDataFilter_Position_Staffing(String name, String stgType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		List<PositionsObjects> listOfGlobersForPositionFilterDb = null;
		List<PositionsObjects> listOfGlobersForPositionFilter = null;

		// First Response Get list of positions available based on glober tp grid data
		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);

		response = stgTestHelper.applySoonToBeGloberFilter(response, name);
		validateResponseToContinueTest(response, 200, "Unable to fetch Replacements Reason.", true);

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), "success",
				"API is not giving 200 response");

		response = stgTestHelper.getSoonToBeGloberDataFilterPosition(response, name);

		validateResponseToContinueTest(response, 200, "Unable to get globers data after applying the position filter.",
				true);

		String positionName = String.valueOf(restUtils.getValueFromResponse(response, "details[2].name"));

		response = stgTestHelper.getListofGlobersPositionFliter(response, positionName, name);
		validateResponseToContinueTest(response, 200, "Unable to get position filter name.", true);

		listOfGlobersForPositionFilter = response.jsonPath().getList("details.globerDTOList.candidateId");

		// Get Glober list based on Position filter from DB
		listOfGlobersForPositionFilterDb = stgTestHelper.getSoonToBeGloberPositionsDb(positionName);

		softAssert.assertEquals(listOfGlobersForPositionFilter.size(), listOfGlobersForPositionFilterDb.size(),
				" JSON and Database list size differs " + positionName);
		softAssert.assertAll();
	}

	/**
	 * This test will verify talent pool grid data after applying the location
	 * filter for using Role: SA,TD,RA,PM and DD
	 * 
	 * @param name
	 * @param STGType
	 * @throws Exception
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true, groups = {
			ExeGroups.Sanity, ExeGroups.Regression, "Glow-II" })
	public void getSoonToBeGloberDataFilter_Location_Staffing(String name, String stgType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		List<LocationsObjects> locationsList = null;
		List<String> listOfGlobersForLocationFilter = null;
		List<LocationsObjects> listOfGlobersForLocationFilterDb = null;

		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);

		response = stgTestHelper.applySoonToBeGloberFilter(response, name);
		validateResponseToContinueTest(response, 200, "Unable to fetch Replacements Reason.", true);

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), "success",
				"API is not giving 200 response");
		response = stgTestHelper.getSoonToBeGloberDataFilterLocation(response, name);

		validateResponseToContinueTest(response, 200, "Unable to get globers data after applying the Location filter.",
				true);

		locationsList = response.jsonPath().getList("details", LocationsObjects.class);

		// Get random location name from the list
		Random random = new Random();

		String locationId = locationsList.get(random.nextInt(locationsList.size())).getId();

		String locationName = stgTestHelper.getSiteNameByLocationId(locationId);

		response = stgTestHelper.getSoonToBeGloberFilterApplyLocation(response, name, locationId);

		listOfGlobersForLocationFilter = response.jsonPath().getList("details.globerDTOList.candidateId");

		// Get Glober list based on Locations filter from DB
		listOfGlobersForLocationFilterDb = stgTestHelper.getSoonToBeGloberLocationFilterDb(locationId);

		softAssert.assertEquals(listOfGlobersForLocationFilter.size(), listOfGlobersForLocationFilterDb.size(),
				" JSON and Database list size differs for Location " + locationName);
		softAssert.assertAll();
	}

	/**
	 * This test will verify talent pool grid data after applying the seniority
	 * filter using Role : Technical Director
	 * 
	 * @param name
	 * @param STGType
	 * @throws Exception
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true, groups = {
			ExeGroups.Regression, "Glow-II" })
	public void getSoonToBeGloberDataFilter_Seniority_TechDirector(String name, String stgType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		List<SeniorityFilterSoonToBeGloberObjects> seniorityList = null;
		List<String> listOfGlobersForSeniorityFilter = null;
		List<SeniorityFilterSoonToBeGloberObjects> listOfGlobersForSeniorityFilterDb = null;

		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);

		response = stgTestHelper.applySoonToBeGloberFilter(response, name);
		validateResponseToContinueTest(response, 200, "Unable to fetch Replacements Reason.", true);

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), "success",
				"API is not giving 200 response");

		response = stgTestHelper.getSoonToBeGloberDataFilterSeniority(response, name);

		validateResponseToContinueTest(response, 200, "Unable to get globers data after applying the Seniority filter.",
				true);

		seniorityList = response.jsonPath().getList("details", SeniorityFilterSoonToBeGloberObjects.class);

		Random random = new Random();
		String seniorityNameDb = seniorityList.get(random.nextInt(seniorityList.size())).getName();
		String seniorityNameApi = seniorityNameDb.replaceAll("\\s", "%20").replaceAll("\\&", "%26").replaceAll("\\++",
				"%2B%2B");

		response = stgTestHelper.getSoonToBeGloberFilterApplySeniority(response, name, seniorityNameApi);

		listOfGlobersForSeniorityFilter = response.jsonPath().getList("details.globerDTOList.candidateId");

		listOfGlobersForSeniorityFilterDb = stgTestHelper.getSoonToBeGloberSeniorityFilterDb(seniorityNameDb);

		// Validate list of soon to be globers who are on talent pool based on seniority
		// filter applied
		softAssert.assertEquals(listOfGlobersForSeniorityFilter.size(), listOfGlobersForSeniorityFilterDb.size(),
				" JSON and Database list size differs " + seniorityNameDb);
		softAssert.assertAll();
	}

	/**
	 * This test will verify talent pool grid data after applying the handler filter
	 * for using Role: SA,TD,RA,PM and DD
	 * 
	 * @param name
	 * @param STGType
	 * @throws Exception
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true, groups = {
			ExeGroups.Sanity, ExeGroups.Regression, "Glow-II" })
	public void getSoonToBeGloberDataFilter_Handler_Staffing(String name, String stgType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		List<HandlersObjects> handlerList = null;
		List<HandlersObjects> listOfGlobersForHandlerFilter = null;
		List<HandlersObjects> listOfGlobersForHandlerFilterDb = null;

		// Get list of handlers available based on glober talent pool grid data
		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);

		response = stgTestHelper.applySoonToBeGloberFilter(response, name);
		validateResponseToContinueTest(response, 200, "Unable to fetch Replacements Reason.", true);

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), "success",
				"API is not giving 200 response");

		response = stgTestHelper.getSoonToBeGloberDataFilterHandler(response, name);

		validateResponseToContinueTest(response, 200, "Unable to get globers data after applying the Handler filter.",
				true);
		handlerList = response.jsonPath().getList("details", HandlersObjects.class);

		// Get random handler name from the list
		Random random = new Random();
		String handlerId = handlerList.get(random.nextInt(handlerList.size())).getId();

		String handlerName = stgTestHelper.getHandlerNameDb(handlerId);

		// get list of Globers For Handler Filter from API
		response = stgTestHelper.getSoonToBeGloberFilterApplyHandler(response, name, handlerId);
		listOfGlobersForHandlerFilter = response.jsonPath().getList("details.globerDTOList.candidateId");

		// get list of Globers For Handler Filter from Db
		listOfGlobersForHandlerFilterDb = stgTestHelper.getSoonToBeGloberHandlerFilterDb(handlerId);

		// Validate list of soon to be globers who are on talent pool based on handler
		// filter applied
		softAssert.assertEquals(listOfGlobersForHandlerFilter.size(), listOfGlobersForHandlerFilterDb.size(),
				" JSON and Database list size differs for Handler " + handlerName);
		softAssert.assertAll();
	}

	/**
	 * This test will verify talent pool grid data after applying the leader filter
	 * for using Role: SA,TD,RA,PM and DD
	 * 
	 * @param name
	 * @param STGType
	 * @throws Exception
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true, groups = {
			ExeGroups.Regression, "Glow-II" })
	public void getSoonToBeGloberDataFilter_Leader(String name, String stgType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		List<LeadersObjects> leadersList = null;
		List<LeadersObjects> listOfLeadersObjectDb = null;

		// Get list of seniorities available based on glober tp grid data
		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);

		response = stgTestHelper.applySoonToBeGloberFilter(response, name);
		validateResponseToContinueTest(response, 200, "Unable to fetch Replacements Reason.", true);

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), "success",
				"API is not giving 200 response");
		response = stgTestHelper.getSoonToBeGloberDataFilterLeader(response, name);

		validateResponseToContinueTest(response, 200, "Unable to get globers data after applying the Leader filter.",
				true);

		leadersList = response.jsonPath().getList("details", LeadersObjects.class);

		listOfLeadersObjectDb = stgTestHelper.getSoonToBeGloberLeadersDb();

		// Validate list of leaders based on global talent pool data
		softAssert.assertEquals(leadersList.size(), listOfLeadersObjectDb.size(),
				" JSON and Database list size differs ");
		softAssert.assertAll();
	}

	/**
	 * This test will verify talent pool grid data after applying the availability
	 * filter using Role: SA,TD,RA,PM and DD
	 * 
	 * @param name
	 * @param STGType
	 * @throws Exception
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true, groups = {
			ExeGroups.Sanity, ExeGroups.Regression, "Glow-II" })
	public void getSoonToBeGloberDataFilter_Availability_Staffing(String name, String stgType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		List<GloberAvailabilityObjects> listOfGlobersForAvailabilityFilter = null;
		List<GloberAvailabilityObjects> listOfGlobersForAvailabilityFilterDb = null;

		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);

		response = stgTestHelper.applySoonToBeGloberFilter(response, name);
		validateResponseToContinueTest(response, 200, "Unable to fetch Replacements Reason.", true);

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), "success",
				"API is not giving 200 response");
		response = stgTestHelper.getSoonToBeGloberDataFilterAvailability(response, name);

		validateResponseToContinueTest(response, 200,
				"Unable to get globers data after applying the Availability filter.", true);

		listOfGlobersForAvailabilityFilter = response.jsonPath().getList("details.globerDTOList.candidateId");

		listOfGlobersForAvailabilityFilterDb = stgTestHelper.getSoonToBeGloberAvailabilityFilterDb();

		// Validate list of soon to be globers who are on talent pool based on
		// availability filter applied within range 0 to 100
		softAssert.assertEquals(listOfGlobersForAvailabilityFilter.size(), listOfGlobersForAvailabilityFilterDb.size(),
				" JSON and Database list size differs");
	}

	/**
	 * This test will verify talent pool grid data after applying the ageing filter
	 * using Role: SA,TD,RA,PM and DD
	 * 
	 * @param name
	 * @param STGType
	 * @throws Exception
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true, groups = {
			ExeGroups.Regression, "Glow-II" })
	public void getSoonToBeGloberDataFilter_Ageing(String name, String stgType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		List<AgeingFilterObjects> listOfGlobersForAgeingFilter = null;
		List<AgeingFilterObjects> listOfGlobersForAgeingFilterDb = null;

		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);

		response = stgTestHelper.applySoonToBeGloberFilter(response, name);
		validateResponseToContinueTest(response, 200, "Unable to fetch Replacements Reason.", true);

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), "success",
				"API is not giving 200 response");
		response = stgTestHelper.getSoonToBeGloberDataFilterAgeing(response, name);

		validateResponseToContinueTest(response, 200, "Unable to get globers data after applying the Ageing filter.",
				true);

		listOfGlobersForAgeingFilter = response.jsonPath().getList("details.globerDTOList.candidateId");

		listOfGlobersForAgeingFilterDb = stgTestHelper.getSoonToBeGloberAgingFilterDb();

		// Validate list of soon to be globers who are on talent pool based on ageing
		// filter applied within range 0 to 100
		softAssert.assertEquals(listOfGlobersForAgeingFilter.size(), listOfGlobersForAgeingFilterDb.size(),
				" JSON and Database list size differs");
		softAssert.assertAll();
	}

	/**
	 * This test will verify talent pool grid data after applying the Glober Studio
	 * filter using Role: SA,TD,RA,PM and DD
	 * 
	 * @param name
	 * @param STGType
	 * @throws Exception
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true, groups = {
			ExeGroups.Regression, "Glow-II" })
	public void getSoonToBeGloberDataFilter_GloberStudio(String name, String stgType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		List<StudiosObjects> globerStudioList = null;
		List<StudiosObjects> listOfGlobersForGloberStudioFilter = null;
		List<StudiosObjects> listOfGlobersForGloberStudioFilterDb = null;

		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);

		response = stgTestHelper.applySoonToBeGloberFilter(response, name);
		validateResponseToContinueTest(response, 200, "Unable to fetch Replacements Reason.", true);

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), "success",
				"API is not giving 200 response");
		response = stgTestHelper.getSoonToBeGloberDataFilterGloberStudio(response, name);

		validateResponseToContinueTest(response, 200,
				"Unable to get globers data after applying the Glober studio filter.", true);

		globerStudioList = response.jsonPath().getList("details", StudiosObjects.class);

		// Get random glober studio Id from the list
		Random random = new Random();
		String studioId = globerStudioList.get(random.nextInt(globerStudioList.size())).getId();

		String studioName = stgTestHelper.getStudioName(studioId);

		response = stgTestHelper.getSoonToBeGloberFilterApplyStudio(response, name, studioId);

		validateResponseToContinueTest(response, 200, "Unable to get studio filter data.", true);

		listOfGlobersForGloberStudioFilter = response.jsonPath().getList("details.globerDTOList.candidateId");

		listOfGlobersForGloberStudioFilterDb = stgTestHelper.getSoonToBeGloberStudioFilterDb(studioId);

		// Validate list of soon to be globers who are on talent pool based on seniority
		// filter applied
		softAssert.assertEquals(listOfGlobersForGloberStudioFilter.size(), listOfGlobersForGloberStudioFilterDb.size(),
				" JSON and Database list size differs " + studioName);
		softAssert.assertAll();
	}

	/**
	 * This test will verify skills filter after soon to be glober filter using
	 * Role: SA,TD,RA,PM and DD
	 * 
	 * @param name
	 * @param STGType
	 * @throws Exception
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true, groups = {
			ExeGroups.Sanity, ExeGroups.Regression, "Glow-II" })
	public void getSoonToBeGloberDataFilter_Skills(String name, String stgType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		List<SkillsObjects> listOfSkillsFilter = null;

		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);

		response = stgTestHelper.applySoonToBeGloberFilter(response, name);
		validateResponseToContinueTest(response, 200, "Unable to fetch Replacements Reason.", true);

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), "success",
				"API is not giving 200 response");
		response = stgTestHelper.getSoonToBeGloberDataFilterSkills(response, name);

		validateResponseToContinueTest(response, 200,
				"Unable to get globers data after applying the Glober Skills filter.", true);

		// Get list of available skills based on glober tp grid data
		listOfSkillsFilter = response.jsonPath().getList("details");

		softAssert.assertEquals(listOfSkillsFilter.size(), 0);
		softAssert.assertAll();
	}

	/**
	 * 
	 * @param name
	 * @param STGType
	 * @throws Exception
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true, groups = {
			ExeGroups.Regression, "Glow-II" })
	public void getSoonToBeGloberDataFilter_Status_Staffing(String name, String stgType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		List<GloberStatusObjects> listOfStatusesFilter = null;
		List<GloberStatusObjects> listOfGlobersForStatusFilter = null;
		List<StatusGridSoonToBeGloberObjects> listOfGlobersForStatusFilterDb = null;
		String statusId = null;

		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);

		response = stgTestHelper.applySoonToBeGloberFilter(response, name);
		validateResponseToContinueTest(response, 200, "Unable to fetch Replacements Reason.", true);

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), "success",
				"API is not giving 200 response");
		response = stgTestHelper.getSoonToBeGloberDataFilterStatus(response, name);

		validateResponseToContinueTest(response, 200,
				"Unable to get globers data after applying the Glober Staus filter.", true);

		listOfStatusesFilter = response.jsonPath().getList("details", GloberStatusObjects.class);

		// get random status from the list
		Random random = new Random();
		do {
			statusId = listOfStatusesFilter.get(random.nextInt(listOfStatusesFilter.size())).getId();
			if (statusId.equals("-2")) {
				statusId = listOfStatusesFilter.get(random.nextInt(listOfStatusesFilter.size())).getId();
			}
		} while (statusId.equals("-2"));

		String statusName = stgTestHelper.getStatusNameDb(statusId);

		response = stgTestHelper.getSoonToBeGloberFilterApplyStatus(response, name, statusId);

		listOfGlobersForStatusFilter = response.jsonPath().getList("details.globerDTOList.candidateId");

		listOfGlobersForStatusFilterDb = stgTestHelper.getGloberStatusFilterDb(statusName);

		// Validate list of soon to be globers who are on talent pool based on status
		// filter applied
		softAssert.assertEquals(listOfGlobersForStatusFilter.size(), listOfGlobersForStatusFilterDb.size(),
				" JSON and Database list size differs " + statusName);
		softAssert.assertAll();
	}

	/**
	 * This test will get the CATS status for STG who's status is In Pipe for Role:
	 * SA,TD,RA,PM and DD
	 * 
	 * @param name
	 * @param STGType
	 * @throws Exception
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true, groups = {
			ExeGroups.Regression, "Glow-II" })
	public void getCatsStatusSoonToBeGloberInPipe(String name, String stgType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		String globerId = null;
		String type = "In%20Pipe";

		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);

		globerId = stgTestHelper.getGloberIdwithCatagoryDb(stgType);

		response = stgTestHelper.getCatsStatusSoonToBeGloberWithType(response, globerId, type);

		validateResponseToContinueTest(response, 200, "Unable to get Cats Status for STG.", true);

		String catsStatusDb = stgTestHelper.getCatsStatusForSoonToBeGloberDb(globerId);

		softAssert.assertTrue(response.jsonPath().getString("details.globerDTOList.catsStatus").contains(catsStatusDb));
		softAssert.assertAll();
	}

	/**
	 * This test will get the CATS status for STG whos status is Candidate for Role:
	 * SA,TD,RA,PM and DD
	 * 
	 * @param name
	 * @param STGType
	 * @throws Exception
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true, groups = {
			ExeGroups.Regression, "Glow-II" })
	public void getCatsStatusSoonToBeGloberCandidate(String name, String stgType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		String globerId = null;
		String type = "Candidate";

		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);

		globerId = stgTestHelper.getGloberIdwithCatagoryDb(stgType);

		response = stgTestHelper.getCatsStatusSoonToBeGloberWithType(response, globerId, type);

		validateResponseToContinueTest(response, 200, "Unable to get Cats Status for STG Candidate.", true);

		String catsStatusDb = stgTestHelper.getCatsStatusForSoonToBeGloberDb(globerId);

		softAssert.assertTrue(response.jsonPath().getString("details.globerDTOList.catsStatus").contains(catsStatusDb));
		softAssert.assertAll();
	}

	/**
	 * This test will get the CATS status for STG whos status is New Hire for Role:
	 * SA,TD,RA,PM and DD
	 * 
	 * @param name
	 * @param STGType
	 * @throws Exception
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true, groups = {
			ExeGroups.Regression, "Glow-II" })
	public void getCatsStatusSoonToBeGloberNewHire(String name, String stgType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		String globerId = null;
		String type = "New%20Hire";

		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);
		globerId = stgTestHelper.getGloberIdwithCatagoryDb(stgType);
		response = stgTestHelper.getCatsStatusSoonToBeGloberWithType(response, globerId, type);

		validateResponseToContinueTest(response, 200, "Unable to get Cats Status for STG New hire.", true);

		String catsStatusDatabase = stgTestHelper.getCatsStatusForSoonToBeGloberDb(globerId);
		softAssert.assertTrue(
				response.jsonPath().getString("details.globerDTOList.catsStatus").contains(catsStatusDatabase));
		softAssert.assertAll();
	}

	/**
	 * This test will get the CATS location for STG for Role: SA,TD,RA,PM and DD
	 * 
	 * @param name
	 * @param STGType
	 * @throws Exception
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true, groups = {
			ExeGroups.Regression, "Glow-II" })
	public void getCatsLocationSoonToBeGlober(String name, String stgType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		String globerId = null;
		String catsLocationDb = null;
		String type = "In%20Pipe";

		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);
		globerId = stgTestHelper.getGloberIdwithCatagoryDb(stgType);
		response = stgTestHelper.getCatsLocationSoonToBeGloberWithType(response, globerId, type);

		validateResponseToContinueTest(response, 200, "Unable to get Cats Location for STG In Pipe.", true);

		catsLocationDb = stgTestHelper.getCatsLocationForSoonToBeGloberDb(globerId);
		softAssert.assertTrue(response.jsonPath().getString("details.globerDTOList.location").contains(catsLocationDb));
		softAssert.assertAll();
	}

	/**
	 * This test will get the CATS location for STG for Role: SA,TD,RA,PM and DD
	 * 
	 * @param name
	 * @param STGType
	 * @throws Exception
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true, groups = {
			ExeGroups.Regression, "Glow-II" })
	public void getCatsLocationSoonToBeGloberCandidate(String name, String stgType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);

		String globerId = stgTestHelper.getGloberIdwithCatagoryDb(stgType);

		String catsLocationDb = stgTestHelper.getCatsLocationForSoonToBeGloberDb(globerId);

		response = stgTestHelper.getCatsLocationSoonToBeGloberCandidate(response, globerId);

		validateResponseToContinueTest(response, 200, "Unable to get Location for STG Candidate.", true);

		softAssert.assertTrue(response.jsonPath().getString("details.globerDTOList.location").contains(catsLocationDb));
		softAssert.assertAll();
	}

	/**
	 * This test will get the CATS location for STG for Role: SA,TD,RA,PM and DD
	 * 
	 * @param name
	 * @param STGType
	 * @throws Exception
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true, groups = {
			ExeGroups.Regression, "Glow-II" })
	public void getCatsLocationSoonToBeGloberNewHire(String name, String stgType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		String globerId = null;
		String catsLocationDb = null;
		String type = "New%20Hire";

		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);
		globerId = stgTestHelper.getGloberIdwithCatagoryDb(stgType);
		response = stgTestHelper.getCatsLocationSoonToBeGloberWithType(response, globerId, type);

		validateResponseToContinueTest(response, 200, "Unable to get Cats Location for STG New Hire.", true);

		catsLocationDb = stgTestHelper.getCatsLocationForSoonToBeGloberDb(globerId);
		softAssert.assertTrue(response.jsonPath().getString("details.globerDTOList.location").contains(catsLocationDb));
		softAssert.assertAll();
	}

	/**
	 * This test will get the In Pipe STG Basic Information from STG detaill page
	 * for Role: SA,TD,RA,PM and DD
	 * 
	 * @param name
	 * @param STGType
	 * @throws Exception
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true, groups = {
			ExeGroups.Sanity, "Glow-II" })
	public void getInPipeSoonToBeGloberDetailsBasicInformation_Staffing(String name, String stgType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		String globerId = null;
		String type = "In%20Pipe";

		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);
		globerId = stgTestHelper.getGloberIdwithCatagoryDb(type.replace("%20", " "));
		List<SoonToBeGloberDetailsObjects> listOfStgBasicInfoDetailsObjects = stgTestHelper
				.getSoonToBeGloberBasicInformationDb(globerId);

		response = stgTestHelper.getCatsLocationSoonToBeGloberWithType(response, globerId, type);

		validateResponseToContinueTest(response, 200, "Unable to get basic information details for STG In Pipe.", true);

		for (int i = 0; i < listOfStgBasicInfoDetailsObjects.size(); i++) {

			softAssert.assertTrue(response.jsonPath().getString("details.globerDTOList.catsStatus")
					.contains(listOfStgBasicInfoDetailsObjects.get(i).getCatsStatus()));
			softAssert.assertTrue(response.jsonPath().getString("details.globerDTOList.catsStatus")
					.contains(listOfStgBasicInfoDetailsObjects.get(i).getCatsStatus()));
			softAssert.assertTrue(response.jsonPath().getString("details.globerDTOList.location")
					.contains(listOfStgBasicInfoDetailsObjects.get(i).getLocation()));
			softAssert.assertTrue(response.jsonPath().getString("details.globerDTOList.englishLevel")
					.contains(listOfStgBasicInfoDetailsObjects.get(i).getEnglishLevel()));
			softAssert.assertTrue(response.jsonPath().getString("details.globerDTOList.catsActivity")
					.contains(listOfStgBasicInfoDetailsObjects.get(i).getCatsActivity()));
			softAssert.assertTrue(response.jsonPath().getString("details.globerDTOList.entryDate")
					.contains(listOfStgBasicInfoDetailsObjects.get(i).getEntryDate()));
		}
		softAssert.assertAll();
	}

	/**
	 * This test will get the Candidate STG Basic Information from STG detaill page
	 * for Role: SA,TD,RA,PM and DD
	 * 
	 * @param name
	 * @param STGType
	 * @throws Exception
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true, groups = {
			ExeGroups.Sanity, "Glow-II" })
	public void getCandidateStgDetailsBasicInformation_Staffing(String name, String stgType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		String globerId = null;

		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);
		globerId = stgTestHelper.getGloberIdwithCatagoryDb(stgType);
		List<SoonToBeGloberDetailsObjects> listOfStgBasicInfoDetailsObjects = stgTestHelper
				.getSoonToBeGloberBasicInformationDb(globerId);
		response = stgTestHelper.getCatsLocationSoonToBeGloberCandidate(response, globerId);

		validateResponseToContinueTest(response, 200, "Unable to get basic information details for STG Candidate.",
				true);

		for (int i = 0; i < listOfStgBasicInfoDetailsObjects.size(); i++) {

			softAssert.assertTrue(response.jsonPath().getString("details.globerDTOList.catsStatus")
					.contains(listOfStgBasicInfoDetailsObjects.get(i).getCatsStatus()));
			softAssert.assertTrue(response.jsonPath().getString("details.globerDTOList.location")
					.contains(listOfStgBasicInfoDetailsObjects.get(i).getLocation()));
			softAssert.assertTrue(response.jsonPath().getString("details.globerDTOList.englishLevel")
					.contains(listOfStgBasicInfoDetailsObjects.get(i).getEnglishLevel()));
			softAssert.assertTrue(response.jsonPath().getString("details.globerDTOList.catsActivity")
					.contains(listOfStgBasicInfoDetailsObjects.get(i).getCatsActivity()));
			softAssert.assertTrue(response.jsonPath().getString("details.globerDTOList.entryDate")
					.contains(listOfStgBasicInfoDetailsObjects.get(i).getEntryDate()));
		}
		softAssert.assertAll();
	}

	/**
	 * This test will get the New Hire STG Basic Information from STG detaill page
	 * for Role: SA,TD,RA,PM and DD
	 * 
	 * @param name
	 * @param STGType
	 * @throws Exception
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true, groups = {
			ExeGroups.Sanity, "Glow-II" })
	public void getNewHireSoonToBeGloberDetailsBasicInformation_Staffing(String name, String stgType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		String globerId = null;
		String type = "New%20Hire";

		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);
		globerId = stgTestHelper.getGloberIdwithCatagoryDb(type.replace("%20", " "));
		List<SoonToBeGloberDetailsObjects> listOfStgBasicInfoDetailsObjects = stgTestHelper
				.getSoonToBeGloberBasicInformationDb(globerId);
		response = stgTestHelper.getCatsLocationSoonToBeGloberWithType(response, globerId, type);

		validateResponseToContinueTest(response, 200, "Unable to get basic information details for STG Candidate.",
				true);

		for (int i = 0; i < listOfStgBasicInfoDetailsObjects.size(); i++) {
			softAssert.assertTrue(response.jsonPath().getString("details.globerDTOList.catsStatus")
					.contains(listOfStgBasicInfoDetailsObjects.get(i).getCatsStatus()));
			softAssert.assertTrue(response.jsonPath().getString("details.globerDTOList.location")
					.contains(listOfStgBasicInfoDetailsObjects.get(i).getLocation()));
			softAssert.assertTrue(response.jsonPath().getString("details.globerDTOList.englishLevel")
					.contains(listOfStgBasicInfoDetailsObjects.get(i).getEnglishLevel()));
			softAssert.assertTrue(response.jsonPath().getString("details.globerDTOList.catsActivity")
					.contains(listOfStgBasicInfoDetailsObjects.get(i).getCatsActivity()));
			softAssert.assertTrue(response.jsonPath().getString("details.globerDTOList.entryDate")
					.contains(listOfStgBasicInfoDetailsObjects.get(i).getEntryDate()));
		}
		softAssert.assertAll();
	}

	/**
	 * This test will get the Candidate STG Recruitment Feedback from STG detail
	 * page for Role: SA,TD,RA,PM and DD
	 * 
	 * @param name
	 * @param STGType
	 * @throws Exception
	 */
	@Test(dataProvider = "userListWithStgCatagory", dataProviderClass = SoonTobeGlobersDataProvider.class, enabled = true, groups = {
			ExeGroups.Regression, "Glow-II" })
	public void SoonToBeGloberDetailsRecruitmentFeedback(String name, String stgType) throws Exception {
		SoftAssert softAssert = new SoftAssert();

		String actualYearsOfExperience = null;
		String actualEnglishLevel = null;
		String actualEvaluatedSeniority = null;

		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);

		String candidateId = stgTestHelper.getStgCandidateIdDb(stgType);

		String interviewId = stgTestHelper.getCandidateInterviewIdDb(candidateId);

		response = stgTestHelper.getStgDetailsRecruitmentFeedback(response, interviewId);

		validateResponseToContinueTest(response, 200, "Unable to get STG Recruitement Feedback details.", true);

		actualYearsOfExperience = restUtils.getValueFromResponse(response, "details.yearsOfExperience").toString()
				.replaceAll("\\[|\\]", "");
		actualEnglishLevel = restUtils.getValueFromResponse(response, "details.technicalEnglishLevel").toString()
				.replaceAll("\\[|\\]", "");
		actualEvaluatedSeniority = restUtils.getValueFromResponse(response, "details.evaluatedSeniority").toString()
				.replaceAll("\\[|\\]", "");

		ArrayList<String> techSkillsList = stgTestHelper.getTechnicalInterviewSkillsDb(candidateId);
		softAssert.assertEquals(actualYearsOfExperience, techSkillsList.get(1));
		softAssert.assertEquals(actualEnglishLevel, techSkillsList.get(0));
		softAssert.assertEquals(actualEvaluatedSeniority, techSkillsList.get(2));

		softAssert.assertAll();
	}

	/**
	 * This test will add a new comment for Candidate type STG on talen pool grid
	 * for Role: SA,TD,RA,PM and DD
	 * 
	 * @param name
	 * @param stgType
	 * @throws Exception
	 */
	@Test(dataProvider = "userListWithStgCatagory", dataProviderClass = SoonTobeGlobersDataProvider.class, enabled = true, groups = {
			ExeGroups.Sanity, ExeGroups.Regression, "Glow-II" })
	public void postAddCommentSoonToBeGloberCandidate_Staffing(String name, String stgType) throws Exception {
		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);
		String globalId = null;
		String comment = null;

		Gson gson = new Gson();
		Map<?, ?> map = new HashMap<Object, Object>();

		globalId = stgTestHelper.getGlobalIdForAddingCommentDb(stgType);

		String requestParams = new SoonTobePayloadHelper().addCommentJson("Staffing", stgType, globalId);

		map = (Map<?, ?>) gson.fromJson(requestParams, map.getClass());

		globalId = (String) map.get("globalId");
		comment = (String) map.get("comments");

		response = stgTestHelper.postAddCommentSoonToBeGlober(response, requestParams, name, globalId, comment);

		if (name.equals("hazel.fernandes") || name.equals("kapil.kanojiya")) {
			String commentsApi = (String) restUtils.getValueFromResponse(response, "details[0].comments");

			Assert.assertTrue(commentsApi.contains(comment));
		} else {
			String message = (String) restUtils.getValueFromResponse(response, "message");

			Assert.assertEquals(message, "User is not having valid permission or role");
		}
	}

	/**
	 * This test will fetch the comments for STG(In Pipe,New Hire) on talen pool
	 * grid for Role :Staffing Analyst,Recruiting Analyst
	 * 
	 * @param name
	 * @param role
	 * @param type
	 * @throws Exception
	 */
	@Test(dataProvider = "userListWithRoles", dataProviderClass = SoonTobeGlobersDataProvider.class, enabled = true, groups = {
			ExeGroups.Regression, "Glow-II" })
	public void getCommentsSoonToBeGlober_StaffingRecruiting(String name, String role, String type) throws Exception {
		Gson gson = new Gson();
		SoftAssert softAssert = new SoftAssert();

		List<CommentsSoonToBeGlobersObjects> listOfCommentsDb = null;
		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);

		String globerId = stgTestHelper.getGloberIdwithCatagoryDb(type);

		listOfCommentsDb = stgTestHelper.getSoonToBeGloberFeedbackCommentsDb(globerId);
		String requestParams = new SoonTobePayloadHelper().addCommentJson(role, type, globerId);

		Map<?, ?> map = new HashMap<Object, Object>();
		map = (Map<?, ?>) gson.fromJson(requestParams, map.getClass());

		globerId = (String) map.get("globerId");
		response = stgTestHelper.postToGetAddCommentSoonToBeGlober(response, requestParams, type, globerId);

		validateResponseToContinueTest(response, 200, "Unable to search candidate type glober", true);

		for (int i = 0; i < listOfCommentsDb.size(); i++) {
			softAssert.assertTrue(response.jsonPath().getString("details[" + i + "].comments")
					.contains(listOfCommentsDb.get(i).getComment()));
		}
		softAssert.assertAll();
	}

	/**
	 * This test will get the full name of candidate type soon to be glober for Role
	 * :Staffing Analyst
	 * 
	 * @param name
	 * @param stgType
	 * @throws Exception
	 */
	@Test(dataProvider = "userListWithStgCatagory", dataProviderClass = SoonTobeGlobersDataProvider.class, enabled = true, groups = {
			ExeGroups.Regression, "Glow-II" })
	public void getSoonToBeGloberFullName_Candidate_Staffing(String name, String stgType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		String globerId = null;

		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);

		if (stgType.equals("Candidate")) {
			globerId = stgTestHelper.getGloberIdwithCatagoryDb(stgType);
		} else if (stgType.equals("In Pipe")) {
			globerId = stgTestHelper.getGloberIdwithCatagoryDb(stgType);
		} else {
			globerId = stgTestHelper.getGloberIdwithCatagoryDb(stgType);
		}

		if (name.equals("hazel.fernandes")) {
			response = stgTestHelper.getSoonToBeGloberFullName(response, name, stgType, globerId);

			validateResponseToContinueTest(response, 200,
					"Unable to get the full name of candidate type soon to be glober for SA role", true);

			String stgFullNameDb = stgTestHelper.getSoonToBeGloberFullNameDb(globerId);

			softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), "success",
					"API is not successful");
			softAssert.assertTrue(
					response.jsonPath().getString("details.globerDTOList.givenName").contains(stgFullNameDb));
			softAssert.assertAll();
		}
	}

	/**
	 * This test will check if the candidate STG populates after a user search for a
	 * respected STG in talent pool grid for Role :Staffing Analyst
	 * 
	 * @param name
	 * @param stgType
	 * @throws Exception
	 */
	@Test(dataProvider = "userListWithStgCatagory", dataProviderClass = SoonTobeGlobersDataProvider.class, enabled = true, groups = {
			ExeGroups.Sanity, ExeGroups.Regression, "Glow-II" })
	public void getSearchResultsForSoonToBeGlober_Candidate_Staffing(String name, String stgType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);
		String globerName = null, globerNameDb = null;

		if (stgType.equals("Candidate")) {
			globerName = stgTestHelper.getFirstNameForStgwithType("Candidate").replaceAll(" ", "%20");
			globerNameDb = globerName.replaceAll("%20", " ");
		} else if (stgType.equals("In Pipe")) {
			globerName = stgTestHelper.getFirstNameForStgwithType("In Pipe").replaceAll(" ", "%20");
			globerNameDb = globerName.replaceAll("%20", " ");
		} else {
			globerName = stgTestHelper.getFirstNameForStgwithType("New Hire").replaceAll(" ", "%20");
			globerNameDb = globerName.replaceAll("%20", " ");
		}

		response = stgTestHelper.getSearchResultsForSoonToBeGlober(response, name, globerName);

		validateResponseToContinueTest(response, 200, "Unable to search candidate type glober for SA role", true);

		io.restassured.path.json.JsonPath js = response.jsonPath();
		ArrayList<Integer> globalIds = js.get("globers.globalId");
		String expectedId = stgTestHelper.getSTGsGlobalIdByFirstName(globerNameDb);
		Assert.assertTrue(globalIds.contains(Integer.parseInt(expectedId)),
				"Actual ids: " + globalIds + " Expected id:" + expectedId);
		softAssert.assertAll();
	}

	/**
	 * This test will check if the candidate STG populates after a user search for a
	 * respected STG in talent pool grid for Role :Staffing Analyst
	 * 
	 * @param name
	 * @param STGType
	 * @throws Exception
	 */
	@Test(dataProvider = "userListWithStgCatagory", dataProviderClass = SoonTobeGlobersDataProvider.class, enabled = true, groups = {
			ExeGroups.Regression, "Glow-II" })
	public void getSearchResultsForSoonToBeGloberSuggestDirectly_Candidate_Staffing(String name, String stgType)
			throws Exception {
		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);

		response = stgTestHelper.getSearchResultsForSoonToBeGlober_SuggestDirectly(response, name, stgType);

		validateResponseToContinueTest(response, 200, "Unable to search candidate type glober", true);

		String type = (String) restUtils.getValueFromResponse(response, "globers[0].type");

		Assert.assertEquals(type, stgType, "Unable to search of type " + stgType + " Glober");
	}

	/**
	 * This test will create new staff request for Role :Staffing Analyst
	 * 
	 * @param name
	 * @param STGType
	 * @throws Exception
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = SoonTobeGlobersDataProvider.class, enabled = true, groups = {
			ExeGroups.Regression, "Glow-II" })
	public void postcreateNewPosition(String name, String STGType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);

		response = stgTestHelper.getNewPostionSkills(response);

		String payLoadBody = stgTestHelper.getPositionPayloadSR(response);

		Response positionJsonPath = stgTestHelper.getPostPositionRequest(payLoadBody);

		String srNumber = CharMatcher.inRange('0', '9').retainFrom(positionJsonPath.jsonPath().getString("message"));

		Response srDetailsPath = stgTestHelper.getSrDetailsPath(srNumber);

		validateResponseToContinueTest(response, 200, "Unable to get SR Details.", true);

		status = (String) restUtils.getValueFromResponse(srDetailsPath, "status");

		softAssert.assertEquals(status, "success", "API is not giving 200 response");

		softAssert.assertAll();
	}

	/**
	 * This test will create new staff request for Role :Staffing Analyst
	 * 
	 * @param name
	 * @param STGType
	 * @throws Exception
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = SoonTobeGlobersDataProvider.class, enabled = true, groups = {
			ExeGroups.Regression, "Glow-II" })
	public void verifyErrorMsgWhilepostcreateNewPositionWithWrongSRStartDate(String name, String STGType)
			throws Exception {
		SoftAssert softAssert = new SoftAssert();
		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);

		response = stgTestHelper.getNewPostionSkills(response);

		String jsonBody = stgTestHelper.getPositionPayloadSRWithWrongSRStartDate(response);

		Response positionJsonPath = stgTestHelper.getPostPositionRequest(jsonBody);

		validateResponseToContinueTest(response, 200,
				"Unable to create new staff request for SA role with Wrong SR Start Date.", true);

		message = (String) restUtils.getValueFromResponse(positionJsonPath, "message");

		softAssert.assertEquals(message, "Start date should be later than today.",
				"Start date is not later than today");

		softAssert.assertAll();
	}

	/**
	 * This test will create new staff request for Role :Staffing Analyst
	 * 
	 * @param name
	 * @param STGType
	 * @throws Exception
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = SoonTobeGlobersDataProvider.class, enabled = true, groups = {
			ExeGroups.Regression, "Glow-II" })
	public void verifyErrorMsgWhilepostcreateNewPositionWithInvalidPositionType(String name, String STGType)
			throws Exception {
		SoftAssert softAssert = new SoftAssert();
		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);

		response = stgTestHelper.getNewPostionSkills(response);

		String jsonBody = stgTestHelper.getPositionPayloadSRWithInvalidPositionType(response);

		Response positionJsonPath = stgTestHelper.getPostPositionRequest(jsonBody);

		validateResponseToContinueTest(response, 200,
				"Unable to create new staff request for SA role with Invalid Position Type.", true);

		message = (String) restUtils.getValueFromResponse(positionJsonPath, "message");

		softAssert.assertEquals(message, "Type of position cannot be blank.", "Type of position is blank.");

		softAssert.assertAll();
	}

	/**
	 * This test will check First & Last Name as per their STG status
	 * 
	 * @param name
	 * @param globerType
	 * @throws Exception
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = SoonTobeGlobersDataProvider.class, enabled = true, groups = {
			ExeGroups.Regression, "Glow-II" })
	public void getPartialNameForSTG(String name, String globerType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);
		String stgNameWithNewHire;
		ArrayList<String> arrCandidatName = new ArrayList<String>();

		response = stgTestHelper.getPartialNameForStg(response, name);

		validateResponseToContinueTest(response, 200, "Unable to get partial name for stg.", true);

		stgNameWithNewHire = (String) restUtils.getValueFromResponse(response, "details.globerDTOList.givenName");

		String[] stgName = stgNameWithNewHire.split(",");

		for (String sCandidtName : stgName)
			arrCandidatName.add(sCandidtName.trim());

		ArrayList<String> stgNameDb = stgTestHelper.getNameofStgWithNewHireStatusDb();

		for (String sDatabaseName : stgNameDb) {
			if (arrCandidatName.contains(sDatabaseName.trim())) {
				softAssert.assertFalse(arrCandidatName.contains(sDatabaseName));
			} else {
				softAssert.assertTrue(arrCandidatName.contains(sDatabaseName));
			}
			softAssert.assertAll();
		}
	}

	/**
	 * This test will check Assignment via SA
	 * 
	 * @param name
	 * @param globertype
	 * @throws Exception
	 */
	@Test(dataProvider = "userListWithStgCatagory", dataProviderClass = SoonTobeGlobersDataProvider.class, enabled = true, groups = {
			ExeGroups.Regression, "Glow-II" })
	public void postAssignStgViaSA(String name, String globerType) throws Exception {
		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);

		if (name.equals("hazel.fernandes") || name.equals("kapil.kanojiya")) {

			ArrayList<String> stgDetails = stgTestHelper.getDetailsWithSuggestionStg(response, name, globerType);
			String positionId = stgTestHelper.getPositionIdDb(stgDetails.get(0));

			String jsonBody = new SoonTobePayloadHelper().assignStg(globerType, positionId);

			response = stgTestHelper.postAssignViaSA(response, jsonBody, positionId);

			validateResponseToContinueTest(response, 200, "Invalid Staff Request.", true);
		}
	}

	/**
	 * This test will verify technical skills for STG using Role: SA,TD,RA,PM and DD
	 * 
	 * @param name
	 * @param STGType
	 * @throws Exception
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = SoonTobeGlobersDataProvider.class, enabled = true, groups = {
			ExeGroups.Sanity, ExeGroups.Regression, "Glow-II" })
	public void showSkillsForSoonToBeGlobers(String name, String stgType) throws Exception {
		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);
		String candidateId = stgTestHelper.getCandidateIdDb();
		response = stgTestHelper.showSkillsForSoonToBeGlobers(response, name, candidateId);
		validateResponseToContinueTest(response, 200, "Invalid Staff Request.", true);
	}

	/**
	 * This test will check Notification while changing Handler for STG
	 * 
	 * @param name
	 * @param globerType
	 * @throws Exception
	 */
	@Test(dataProvider = "userListWithStgCatagory", dataProviderClass = SoonTobeGlobersDataProvider.class, enabled = true, groups = {
			ExeGroups.Sanity, ExeGroups.Regression, "Glow-II" })
	public void postChangeHandler(String name, String globerType) throws Exception {
		if (name.equals("hazel.fernandes") || name.equals("kapil.kanojiya")) {
			SoftAssert softAssert = new SoftAssert();
			SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);

			String jsonBody = new SoonTobePayloadHelper().postChangeHandler(globerType);

			response = stgTestHelper.postNotification(response, jsonBody);
			validateResponseToContinueTest(response, 200, "Invalid Staff Request.", true);

			message = (String) restUtils.getValueFromResponse(response, "message");

			softAssert.assertEquals(message,
					"We are processing the request, will notify in mail once updates are completed",
					"API is not successful");
			softAssert.assertAll();
		}
	}

	/**
	 * This test will check PM should not be able to suggest twice same STG
	 * 
	 * @param name
	 * @param globerType
	 * @throws Exception
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true, groups = {
			ExeGroups.Regression, "Glow-II" })
	public void verifyNewSuggestViaPM(String name, String globerType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);

		for (int i = 0; i <= 1; i++) {
			String jsonBody = stgTestHelper.getPayloadNewSuggestViaPM(globerType, "High");

			response = stgTestHelper.postSuggestStgNotMoreThanOnceViaPMandTDWithValidations(response, jsonBody, i,
					name);

			validateResponseToContinueTest(response, 201, "Unable to suggest new glober by PM.", true);

			status = (String) restUtils.getValueFromResponse(response, "status");
			statusCode = (String) restUtils.getValueFromResponse(response, "statusCode");

			softAssert.assertEquals(status, "success", "API is not successful");
			softAssert.assertEquals(statusCode, "CREATED", "Glober is unable to be suggested by PM");
			softAssert.assertAll();
		}
	}

	/**
	 * This test will check PM should not be able to suggest twice same STG
	 * 
	 * @param name
	 * @param globerType
	 * @throws Exception
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true, groups = {
			ExeGroups.Regression, "Glow-II" })
	public void validateErrorMsgWhilepostSuggestedSTGviaPMWithInvalidPlanStartDate(String name, String globerType)
			throws Exception {
		SoftAssert softAssert = new SoftAssert();
		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);

		for (int i = 0; i <= 1; i++) {
			String jsonBody = stgTestHelper.getPayloadNewSuggestViaPMWithInvalidStDt(globerType, "High");

			response = stgTestHelper.postSuggestStgNotMoreThanOnceViaPMandTDWithValidations(response, jsonBody, i,
					name);

			validateResponseToContinueTest(response, 400, "Invalid Staff Request.", true);

			message = (String) restUtils.getValueFromResponse(response, "message");

			softAssert.assertEquals(message, "Date should be later than today.", "Date is not later than today.");
			softAssert.assertAll();
		}
	}

	/**
	 * This test will check PM should not be able to suggest twice same STG
	 * 
	 * @param name
	 * @param globerType
	 * @throws Exception
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true, groups = {
			ExeGroups.Regression, "Glow-II" })
	public void validateErrorMsgWhilepostSuggestedSTGviaPMWithNullSrId(String name, String globerType)
			throws Exception {
		SoftAssert softAssert = new SoftAssert();
		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);

		for (int i = 0; i <= 1; i++) {
			String jsonBody = stgTestHelper.newSuggestViaPMWithNullSrId(globerType, "High");

			response = stgTestHelper.postSuggestStgNotMoreThanOnceViaPMandTDWithValidations(response, jsonBody, i,
					name);

			validateResponseToContinueTest(response, 400, "Invalid Staff Request.", true);

			message = (String) restUtils.getValueFromResponse(response, "message");

			softAssert.assertEquals(message, "Staff Request ID cannot be empty.", "Staff Request ID is empty.");
			softAssert.assertAll();
		}
	}

	/**
	 * This test will check PM should not be able to suggest twice same STG
	 * 
	 * @param name
	 * @param globerType
	 * @throws Exception
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true, groups = {
			ExeGroups.Regression, "Glow-II" })
	public void validateErrorMsgWhilepostSuggestedStgviaPMWithWrongSrId(String name, String globerType)
			throws Exception {
		SoftAssert softAssert = new SoftAssert();
		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);

		for (int i = 0; i <= 1; i++) {
			String jsonBody = stgTestHelper.newSuggestViaPMwithWrongSrId(globerType, "High");

			response = stgTestHelper.postSuggestStgNotMoreThanOnceViaPMandTDWithValidations(response, jsonBody, i,
					name);

			validateResponseToContinueTest(response, 400, "Invalid Staff Request.", true);

			message = (String) restUtils.getValueFromResponse(response, "message");

			softAssert.assertEquals(message, "Staff Request ID not Valid.",
					"PM is not be able to suggest twice same STG.");
			softAssert.assertAll();
		}
	}

	/**
	 * This will get STG details from gatekeep
	 * 
	 * @param name
	 * @param StgType
	 * @throws Exception
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true, groups = {
			ExeGroups.Regression, "Glow-II" })
	public void getSTGDetailsFromGateKeeper(String name, String StgType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);

		String globerId = stgTestHelper.getGloberIdFromGlobalIdDb(StgType);
		response = stgTestHelper.getDetailsOfStg(response, StgType, name);

		validateResponseToContinueTest(response, 200, "Unable to get STG Details while suggestion.", true);

		String stgNameDb = stgTestHelper.getSoonToBeGloberFullNameDb(globerId);

		if (name.equals("hazel.fernandes") || name.equals("kapil.kanojiya")) {
			String stgName = ((String) restUtils.getValueFromResponse(response, "details.globerDTOList[0].givenName"))
					.replaceAll("\\[|\\]", "");
			softAssert.assertEquals(stgNameDb, stgName, "API is not working successfully.");
		}

		softAssert.assertEquals(stgTestHelper.getSeniorityofStgDb(stgNameDb),
				((String) restUtils.getValueFromResponse(response, "details.globerDTOList[0].seniority"))
						.replaceAll("\\[|\\]", ""),
				"API is not working successfully.");

		softAssert.assertEquals(stgTestHelper.getPositionofStgDb(globerId),
				((String) restUtils.getValueFromResponse(response, "details.globerDTOList[0].position"))
						.replaceAll("\\[|\\]", ""),
				"API is not working successfully.");

		softAssert.assertEquals(stgTestHelper.getHandlernameofStgDb(globerId),
				((String) restUtils.getValueFromResponse(response, "details.globerDTOList[0].handler.handlerName"))
						.replaceAll("\\[|\\]", ""),
				"API is not working successfully.");
		softAssert.assertAll();
	}

	/**
	 * This will check position, seniority, location and joining date of STG
	 * 
	 * @param name
	 * @param STGType
	 * @throws Exception
	 */
	@Test(dataProvider = "userListWithStgCatagory", dataProviderClass = SoonTobeGlobersDataProvider.class, enabled = true, groups = {
			ExeGroups.Regression, "Glow-II" })
	public void getSTGDetailsWhileSuggestion(String name, String StgType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);

		response = stgTestHelper.getDetailsOfStg(response, StgType, name);

		validateResponseToContinueTest(response, 200, "Unable to get STG Details while suggestion.", true);

		status = (String) restUtils.getValueFromResponse(response, "status");

		softAssert.assertEquals(status, "success", "API is not giving 200 response");
		softAssert.assertAll();
	}

	/**
	 * This will check Recruiting Status of STG
	 * 
	 * @param name
	 * @param STGType
	 * @throws Exception
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = SoonTobeGlobersDataProvider.class, enabled = true, groups = {
			ExeGroups.Regression, "Glow-II" })
	public void getrecruitingStatusOfStg(String name, String STGType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);

		ArrayList<String> candidateNamesFromDb = stgTestHelper.getStgNameWithoutProcessStatusDb();

		response = stgTestHelper.getRecrutingStatusOfStg(response, STGType);

		validateResponseToContinueTest(response, 200, "Unable to check Recruiting Status of STG.", true);

		status = (String) restUtils.getValueFromResponse(response, "status");

		String status = ((String) restUtils.getValueFromResponse(response, "details.globerDTOList.givenName"))
				.replaceAll("\\[|\\]", "");

		String[] arrStatus = status.split(",");
		for (String str : arrStatus) {
			if (!candidateNamesFromDb.contains(str)) {
				softAssert.assertFalse(candidateNamesFromDb.contains(str));
			} else {
				softAssert.assertTrue(candidateNamesFromDb.contains(str));
			}
		}
		softAssert.assertEquals(status, "success", "API status is not successful.");
		softAssert.assertAll();
	}

	/**
	 * This will check STG on Talent Pool
	 * 
	 * @param name
	 * @param STGType
	 * @throws Exception
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = SoonTobeGlobersDataProvider.class, enabled = true, groups = {
			ExeGroups.Regression, "Glow-II" })
	public void STGTalentPoolGrid(String name, String STGType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		int i = 0;
		ArrayList<String> candidateNameList = new ArrayList<String>();
		ArrayList<String> candidateNameAllGloberList = new ArrayList<String>();

		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);

		ArrayList<String> stgCandidateNamesDb = stgTestHelper.getStgDetailDb();

		response = stgTestHelper.getPartialNameForStg(response, name);

		Integer count = (Integer) restUtils.getValueFromResponse(response, "details.globerDTOList.size()"); // 919

		for (i = 0; i < count; i++) {
			String CandidtName = ((String) restUtils.getValueFromResponse(response,
					"details.globerDTOList[" + i + "].givenName")).replaceAll("\\[|\\]", "");
			candidateNameList.add(CandidtName);
		}

		response = stgTestHelper.getPartialNameForStgForAllGloberPage(response, name);

		validateResponseToContinueTest(response, 200, "Unable to check STG on Talent Pool.", true);

		int allGloberSTGPageCount = (Integer) restUtils.getValueFromResponse(response, "details.globerDTOList.size()"); // 951

		for (i = 0; i < allGloberSTGPageCount; i++) {
			String CandidtName = ((String) restUtils.getValueFromResponse(response,
					"details.globerDTOList[" + i + "].givenName")).replaceAll("\\[|\\]", "");
			candidateNameAllGloberList.add(CandidtName);
		}

		for (String str : stgCandidateNamesDb) {
			String finalName = str.trim();
			softAssert.assertTrue(
					candidateNameAllGloberList.contains(finalName) || candidateNameList.contains(finalName));

			i++;
		}
		softAssert.assertAll();
	}

	/**
	 * This will check stg gatekeeper and recruiter info on Talent pool
	 * 
	 * @param name
	 * @param STGType
	 * @throws Exception
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = SoonTobeGlobersDataProvider.class, enabled = true, groups = {
			ExeGroups.Regression, "Glow-II" })
	public void stgGateKeeperAndRecruiterInfo_WhileSuggestion(String name, String stgType) throws Exception {
		SoftAssert softAssert = new SoftAssert();

		SoonTobeGloberTestHelper stgTestHelper = new SoonTobeGloberTestHelper(name);

		String globerId = stgTestHelper.getGloberIdwithCatagoryDb(stgType);
		String candidateId = stgTestHelper.getCandidateIdFromGloberIdDb(globerId);

		String sDbGateKeeperName = stgTestHelper.getGateKeeperNameDb(candidateId);
		String sDbRecruiterName = stgTestHelper.getRecruiterNameDb(candidateId);

		response = stgTestHelper.getGateKeeperNameAndRecruiter(response, candidateId);

		validateResponseToContinueTest(response, 200,
				"Unable to check stg gatekeeper and recruiter info on Talent pool.", true);

		String sGatekeeperName = ((String) restUtils.getValueFromResponse(response,
				"details[0].gatekeeper.completeName")).replaceAll("\\[|\\]", "");

		String sRecruiteName = ((String) restUtils.getValueFromResponse(response, "details[0].recruiter.completeName"))
				.replaceAll("\\[|\\]", "");

		softAssert.assertEquals(sDbGateKeeperName, sGatekeeperName,
				"Gatekeeper name from API is different from DB GateKeeper name.");
		softAssert.assertEquals(sDbRecruiterName, sRecruiteName,
				"Recruiter name from API is different from DB Recruiter name.");
		softAssert.assertAll();
	}
}
