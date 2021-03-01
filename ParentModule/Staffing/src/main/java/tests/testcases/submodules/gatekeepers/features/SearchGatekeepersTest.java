package tests.testcases.submodules.gatekeepers.features;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import database.submodules.gatekeepers.features.SearchGatekeepersDBHelper;
import dataproviders.submodules.gatekeepers.features.SearchGatekeepersDataProviders;
import dto.submodules.gatekeepers.GatekeepersDTO;
import endpoints.submodules.gatekeepers.features.SearchGatekeepersEndpoints;
import io.restassured.response.Response;
import listeners.RetryFailedTests;
import net.minidev.json.JSONArray;
import tests.testcases.submodules.gatekeepers.GatekeepersBaseTest;
import tests.testhelpers.submodules.gatekeepers.features.SearchGatekeepersTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author deepakkumar.hadiya
 */

public class SearchGatekeepersTest extends GatekeepersBaseTest{
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("Recruiter searches gatekeepers based on position, seniority and location");
	}

	/**
	 * This test is to verify Search gatekeepers service is returning gatekeepers with details
	 * @param data
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	@Test(priority=0, dataProvider ="validTestData", dataProviderClass = SearchGatekeepersDataProviders.class, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void verifySearchGatekeepersService_PositiveScenario(Map<Object, Object> data) throws Exception {
		
		SearchGatekeepersTestHelper searchGatekeepersTestHelper = new SearchGatekeepersTestHelper();
		Response apiResponse=searchGatekeepersTestHelper.getGatekeepersList(data);
		validateResponseToContinueTest(apiResponse, 200, "Unable to search gatekeepers with valid test data", true);

		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(restUtils.getValueFromResponse(apiResponse, "message"),"success","Response message is incorrect for valid test data");

		String positionId=data.get(POSITION).toString();
		String seniorityId=data.get(SENIORITY).toString();
		String location=data.get(LOCATION).toString();
		
		SearchGatekeepersDBHelper searchGatekeepersDBHelper=new SearchGatekeepersDBHelper();
		Map<String, GatekeepersDTO> dbResponse = searchGatekeepersDBHelper.getGatekeepersList(positionId, seniorityId, location);
		Set<String> globersFromDBResponse = dbResponse.keySet();
		Reporter.log("Gatekeepers from DB query = "+ globersFromDBResponse.toString(),true);
		JSONArray gatekeepersList = (JSONArray) restUtils.getValueFromResponse(apiResponse, "$.details[*]"); 
		int totakGKInDataBase=globersFromDBResponse.size();
		int totalGKInApiResponse=gatekeepersList.size();
		softAssert.assertEquals(totalGKInApiResponse, totakGKInDataBase,
				"Gatekeepers total count = "+totalGKInApiResponse+" from api response and Gatekeepers count ="+totakGKInDataBase+" from database response : ");

		for (int i = 0; i < gatekeepersList.size(); i++) {

			Map gkDetail = (Map)gatekeepersList.get(i);
			String globerIdFromApiResponse = gkDetail.get("globarid").toString();

			boolean fetchedGKFromAPIIsInDB = dbResponse.containsKey(globerIdFromApiResponse);
			softAssert.assertTrue(fetchedGKFromAPIIsInDB, "Gatekeeper id = " + globerIdFromApiResponse
					+ " from API response is not available in the Database");
			GatekeepersDTO gatekeeperDetailsFromDB = dbResponse.get(globerIdFromApiResponse);
			if (fetchedGKFromAPIIsInDB && data.get("dtoType").toString().equals("FAT")) {

				String globerNameFromApiResponse = gkDetail.get("username").toString();
				softAssert.assertEquals(globerNameFromApiResponse, gatekeeperDetailsFromDB.getGloberName(),
						"Name of Gatekeeper for globerID = " + globerIdFromApiResponse+" is not correct in API response");

				String globerPositionFromApiResponse = gkDetail.get("position").toString();
				softAssert.assertEquals(globerPositionFromApiResponse, gatekeeperDetailsFromDB.getGloberPosition(),
						"Position of Gatekeeper for globerID = " + globerIdFromApiResponse+ " is not correct in API response");

				String globerSeniorityFromApiResponse = gkDetail.get("seniority").toString();
				softAssert.assertEquals(globerSeniorityFromApiResponse, gatekeeperDetailsFromDB.getGloberSeniority(),
						"Seniority of Gatekeeper for globerID = " + globerIdFromApiResponse+" is not correct in API response");

				String globerLocationFromApiResponse =gkDetail.get("location").toString();
				softAssert.assertEquals(globerLocationFromApiResponse, gatekeeperDetailsFromDB.getGloberLocation(),
						"Location of Gatekeeper for globerID = " + globerIdFromApiResponse+" is not correct in API response");

				String seniorityLevel =  gkDetail.get("seniorityLevel").toString();
				softAssert.assertEquals(seniorityLevel, gatekeeperDetailsFromDB.getGloberSeniorityLevel(),
						"Seniority level of Gatekeeper for globerID = " + globerIdFromApiResponse+" is not correct in API response");
			}
		}
		softAssert.assertAll();
		test.log(Status.PASS, "Gatekeepers list is fetched verified successfully with all details");

	}

	/**
	 * This test is to verify Search gatekeepers service for project manager ladder
	 * @param data
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(priority=0, dataProvider ="testData_For_PM_Ladder_From_Each_Country", dataProviderClass = SearchGatekeepersDataProviders.class, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void verifySearchGatekeepersService_For_PM_Ladder(Map<Object, Object> data) throws Exception {

		SearchGatekeepersTestHelper searchGatekeepersTestHelper = new SearchGatekeepersTestHelper();
		Response apiResponse=searchGatekeepersTestHelper.getGatekeepersList(data);
		validateResponseToContinueTest(apiResponse, 200, "Unable to search gatekeepers with valid test data", true);

		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(restUtils.getValueFromResponse(apiResponse, "message"),"success","Response message is incorrect for valid test data");

		String positionId=data.get(POSITION).toString();
		String seniorityId=data.get(SENIORITY).toString();
		String location=data.get(LOCATION).toString();
		
		SearchGatekeepersDBHelper searchGatekeepersDBHelper=new SearchGatekeepersDBHelper();
		Map<String, GatekeepersDTO> dbResponse = searchGatekeepersDBHelper.getGatekeepersList(positionId, seniorityId, location);
		Set<String> globersFromDBResponse = dbResponse.keySet();
		Reporter.log("Gatekeepers from DB query = "+ globersFromDBResponse.toString(),true);
		List<Object> gatekeepersList = (List<Object>) restUtils.getValueFromResponse(apiResponse, "$.details[*]");
		int totakGKInDataBase=globersFromDBResponse.size();
		int totalGKInApiResponse=gatekeepersList.size();
		softAssert.assertEquals(totalGKInApiResponse, totakGKInDataBase,
				"Gatekeepers total count = "+totalGKInApiResponse+" from api response and Gatekeepers count ="+totakGKInDataBase+" from database response : ");

		List<Object> globerIdList = (List<Object>) restUtils.getValueFromResponse(apiResponse, "$.details[*].globarid");

		for (int i = 0; i < globerIdList.size(); i++) {

			String globerIdFromApiResponse = restUtils
					.getValueFromResponse(apiResponse, "$.details[" + i + "].globarid").toString();

			boolean fetchedGKFromAPIIsInDB = dbResponse.containsKey(globerIdFromApiResponse);
			softAssert.assertTrue(fetchedGKFromAPIIsInDB, "Gatekeeper id = " + globerIdFromApiResponse
					+ " from API response is not available in the Database");
			GatekeepersDTO gatekeeperDetailsFromDB = dbResponse.get(globerIdFromApiResponse);
			if (fetchedGKFromAPIIsInDB && data.get("dtoType").toString().equals("FAT")) {

				String globerNameFromApiResponse = restUtils
						.getValueFromResponse(apiResponse, "$.details[" + i + "].username").toString();
				softAssert.assertEquals(globerNameFromApiResponse, gatekeeperDetailsFromDB.getGloberName(),
						"Name of Gatekeeper for globerID = " + globerIdFromApiResponse+" is not correct in API response");

				String globerPositionFromApiResponse = restUtils
						.getValueFromResponse(apiResponse, "$.details[" + i + "].position").toString();
				softAssert.assertEquals(globerPositionFromApiResponse, gatekeeperDetailsFromDB.getGloberPosition(),
						"Position of Gatekeeper for globerID = " + globerIdFromApiResponse+ " is not correct in API response");

				String globerSeniorityFromApiResponse = restUtils
						.getValueFromResponse(apiResponse, "$.details[" + i + "].seniority").toString();
				softAssert.assertEquals(globerSeniorityFromApiResponse, gatekeeperDetailsFromDB.getGloberSeniority(),
						"Seniority of Gatekeeper for globerID = " + globerIdFromApiResponse+" is not correct in API response");

				String globerLocationFromApiResponse = restUtils
						.getValueFromResponse(apiResponse, "$.details[" + i + "].location").toString();
				softAssert.assertEquals(globerLocationFromApiResponse, gatekeeperDetailsFromDB.getGloberLocation(),
						"Location of Gatekeeper for globerID = " + globerIdFromApiResponse+" is not correct in API response");

				List<Object> positionLevelList = (List<Object>) restUtils.getValueFromResponse(apiResponse,
						"$.details[*].positionLevel");
				softAssert.assertEquals(positionLevelList.size(), gatekeepersList.size(),
						"Total count of positionLevels is not correct for API response");

				String seniorityLevel =  restUtils.getValueFromResponse(apiResponse,
						"$.details["+i+"].seniorityLevel").toString();
				softAssert.assertEquals(seniorityLevel, gatekeeperDetailsFromDB.getGloberSeniorityLevel(),
						"Seniority level of Gatekeeper for globerID = " + globerIdFromApiResponse+" is not correct in API response");
			}
		}
		softAssert.assertAll();
		test.log(Status.PASS, "Gatekeepers list is fetched verified successfully with all details");

	}

	
	/**
	 * This test is to verify Search gatekeepers service is returning appropriate response for incorrect request method
	 * @param data
	 * @throws Exception
	 */
	@Test(priority=1, dataProvider = "invalid_Url_and_requestMethod", dataProviderClass = SearchGatekeepersDataProviders.class, groups = {
			ExeGroups.Regression})
	public void verifySearchGatekeepersService_with_invalidRequestMethod(Map<Object, Object> data) throws Exception {

		SearchGatekeepersTestHelper searchGatekeepersTestHelper = new SearchGatekeepersTestHelper();
		Response response = searchGatekeepersTestHelper.getGatekeepersListUsingInvalidMethod(data);

		validateResponseToContinueTest(response, 403, "Status code is incorrect for invalid request method", true);

		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(restUtils.getValueFromResponse(response, ERROR), "Forbidden","Response status is incorrect for invalid request method (POST)");
		softAssert.assertEquals(restUtils.getValueFromResponse(response, MESSAGE),
				"User is not having valid permission or role","Response message is incorrect for invalid request method (POST)");
		softAssert.assertEquals(restUtils.getValueFromResponse(response, PATH), SearchGatekeepersEndpoints.searchGatekeeper.split("\\?")[0],"Path in response body is incorrect for invalid request method (POST)");
		softAssert.assertAll();
		test.log(Status.PASS, "Gatekeepers is returning appropriate response for invalid request method (POST)");
	}

	/**
	 * This test is to verify Search gatekeepers service is returning appropriate response for wrong header key
	 * @param data 
	 * @throws Exception
	 */
	@Test(priority=2, dataProvider = "invalidHeaderKey", dataProviderClass = SearchGatekeepersDataProviders.class, groups = {
			ExeGroups.Regression})
	public void verifySearchGatekeepersService_with_invalidHeaderKey(Map<Object, Object> data) throws Exception {

		SearchGatekeepersTestHelper searchGatekeepersTestHelper = new SearchGatekeepersTestHelper();
		Response response =searchGatekeepersTestHelper.getGatekeepersList(data);

		validateResponseToContinueTest(response, 401, "Status code is incorrect for invalid header key", true);

		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(restUtils.getValueFromResponse(response, ERROR), "Unauthorized","Response status is incorrect for invalid header key");
		softAssert.assertEquals(restUtils.getValueFromResponse(response, MESSAGE),
				"Token not found","Response message is incorrect for invalid header key");
		softAssert.assertAll();

		test.log(Status.PASS, "Gatekeepers is returning appropriate response for invalid header key");
	}

	/**
	 * This test is to verify Search gatekeepers service is returning appropriate response for wrong header value
	 * @param data
	 * @throws Exception
	 */
	@Test(priority=3, dataProvider = "invalidHeaderValue", dataProviderClass = SearchGatekeepersDataProviders.class, groups = {
			ExeGroups.Regression})
	public void verifySearchGatekeepersService_with_invalidHeaderValue(Map<Object, Object> data)
			throws Exception {

		SearchGatekeepersTestHelper searchGatekeepersTestHelper = new SearchGatekeepersTestHelper();
		Response response =searchGatekeepersTestHelper.getGatekeepersList(data);

		validateResponseToContinueTest(response, 403, "Status code is incorrect for invalid header value", true);

		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(restUtils.getValueFromResponse(response, ERROR), "Forbidden","Response status is incorrect for invalid header value");
		softAssert.assertEquals(restUtils.getValueFromResponse(response, MESSAGE), "User is not having valid permission or role","Response message is incorrect for invalid header value");
		softAssert.assertEquals(restUtils.getValueFromResponse(response, PATH), SearchGatekeepersEndpoints.searchGatekeeper.split("\\?")[0],"Path in response body is incorrect for invalid header value");
		softAssert.assertAll();

		test.log(Status.PASS, "Gatekeepers is returning appropriate response for invalid header value");
	}

	/**
	 * This test is to verify Search gatekeepers service is returning appropriate response for wrong url
	 * 
	 * @param data
	 * @throws Exception
	 */
	@Test(priority=4, dataProvider = "invalid_Url_and_requestMethod", dataProviderClass = SearchGatekeepersDataProviders.class, groups = {
			ExeGroups.Regression})
	public void verifySearchGatekeepersService_with_invalidUrl(Map<Object, Object> data) throws Exception {

		SearchGatekeepersTestHelper searchGatekeepersTestHelper = new SearchGatekeepersTestHelper();
		Response response = searchGatekeepersTestHelper.getGatekeepersListUsingInvalidUrl(data);

		validateResponseToContinueTest(response, 403, "Status code is incorrect for invalid Url", true);
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(restUtils.getValueFromResponse(response, ERROR), "Forbidden","Response status is incorrect for invalid url");
		softAssert.assertEquals(restUtils.getValueFromResponse(response, MESSAGE), "User is not having valid permission or role","Response message is incorrect for invalid url");
		softAssert.assertEquals(restUtils.getValueFromResponse(response, PATH), "/glow/gatekeeperorchestraservice/gatkeepers","Path in response body is incorrect for invalid url");
		softAssert.assertAll();
		test.log(Status.PASS, "Gatekeepers is returning appropriate response for invalid url");
	}
	
	/**
	 * This test is to verify search gatekeepers service is returning appropriate response for wrong parameter value
	 * @param data 
	 * @throws Exception
	 */
	@Test(priority=5, dataProvider = "invalidParameterValue", dataProviderClass = SearchGatekeepersDataProviders.class, groups = {
			ExeGroups.Regression})
	public void verifySearchGatekeepersService_with_invalidParameterValue(Map<Object, Object> data) throws Exception {
		String parameterName=data.get("parameterKey").toString();
		String message=data.get("message").toString();
		String parameterValue=data.get(parameterName).toString();
		SearchGatekeepersTestHelper searchGatekeepersTestHelper = new SearchGatekeepersTestHelper();
		Response response =searchGatekeepersTestHelper.getGatekeepersList(data);

		validateResponseToContinueTest(response, 400, "Status code is incorrect for invalid '"+parameterName+"'", true);

		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(restUtils.getValueFromResponse(response, STATUS), "Bad Request","Response status is incorrect for invalid '"+parameterName+"'");
		softAssert.assertEquals(restUtils.getValueFromResponse(response, MESSAGE),
				message+" "+parameterValue,"Response message is incorrect for invalid '"+parameterName+"'");
		softAssert.assertAll();

		test.log(Status.PASS, "Gatekeepers is returning appropriate response for '"+parameterName+"'");
	}

	/**
	 * This test is to verify Search gatekeepers service is returning gatekeepers in random orders for same ranked GK
	 * @param data
	 * @throws Exception
	 */
	@Test(enabled=false, priority=6, retryAnalyzer = RetryFailedTests.class,dataProvider ="validTestData_for_random_ordering", dataProviderClass = SearchGatekeepersDataProviders.class, groups = {
			ExeGroups.Regression})
	public void verifySearchGatekeepersService_For_RandomOrdering(Map<Object, Object> data) throws Exception {
		
		SearchGatekeepersTestHelper searchGatekeepersTestHelper = new SearchGatekeepersTestHelper();
		 Map<Integer, List<String>> firstTime = searchGatekeepersTestHelper.getGatekeepersListWithRank(data);
		 Map<Integer, List<String>> secondTime = searchGatekeepersTestHelper.getGatekeepersListWithRank(data);
		 Map<Integer, List<String>> thirdTime = searchGatekeepersTestHelper.getGatekeepersListWithRank(data);
		 SoftAssert softAssert = new SoftAssert();
		 boolean actual=true;
		 for(Integer key:firstTime.keySet()) {
			test.log(Status.INFO, "Verifying random ordering for rank = "+key);
			List<String> firstTimeList = firstTime.get(key);
			List<String> secondTimeList = secondTime.get(key);
			List<String> thirdTimeList = thirdTime.get(key);
			Reporter.log("rank : "+key+" \n"+firstTimeList+" \n"+secondTimeList+" \n"+thirdTimeList,true);
			if(firstTimeList.size()>1) {
				if(!firstTimeList.equals(secondTimeList)) {
					actual=false;
				}else if(!secondTimeList.equals(thirdTimeList)) {
					actual=false;
				}else if(!firstTimeList.equals(thirdTimeList)){
					actual=false;
				}
				softAssert.assertFalse(actual,"Gatekeepers are not in random order for rank = "+key+" List for first time : "+firstTimeList+" second time : "+secondTimeList+" third time : "+thirdTimeList);
			}
		}
		softAssert.assertAll(); 
		test.log(Status.PASS, "Random ordering of gatekeepers for all ranks are verified successfully"); 
	}
	
	/**
	 * This test is to verify Search gatekeepers service is working for all locations
	 * @param data
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(priority=0, dataProvider ="validTestDataForAllLocations", dataProviderClass = SearchGatekeepersDataProviders.class)
	public void verifySearchGatekeepersService_ForAllLocations(Map<Object, Object> data) throws Exception {

		SearchGatekeepersTestHelper searchGatekeepersTestHelper = new SearchGatekeepersTestHelper();
		Response apiResponse=searchGatekeepersTestHelper.getGatekeepersList(data);
		validateResponseToContinueTest(apiResponse, 200, "Unable to search gatekeepers with valid test data", true);

		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(restUtils.getValueFromResponse(apiResponse, "message"),"success","Response message is incorrect for valid test data");

		String positionId=data.get(POSITION).toString();
		String seniorityId=data.get(SENIORITY).toString();
		String location=data.get(LOCATION).toString();
		
		SearchGatekeepersDBHelper searchGatekeepersDBHelper=new SearchGatekeepersDBHelper();
		Map<String, GatekeepersDTO> dbResponse = searchGatekeepersDBHelper.getGatekeepersList(positionId, seniorityId, location);
		Set<String> globersFromDBResponse = dbResponse.keySet();
		Reporter.log("Gatekeepers from DB query = "+ globersFromDBResponse.toString(),true);
		List<Object> gatekeepersList = (List<Object>) restUtils.getValueFromResponse(apiResponse, "$.details[*]");
		int totakGKInDataBase=globersFromDBResponse.size();
		int totalGKInApiResponse=gatekeepersList.size();
		softAssert.assertEquals(totalGKInApiResponse, totakGKInDataBase,
				"Gatekeepers total count = "+totalGKInApiResponse+" from api response is not equal to Gatekeepers total count ="+totakGKInDataBase+" from database response");

		List<Object> globerIdList = (List<Object>) restUtils.getValueFromResponse(apiResponse, "$.details[*].globarid");

		for (int i = 0; i < globerIdList.size(); i++) {

			String globerIdFromApiResponse = restUtils
					.getValueFromResponse(apiResponse, "$.details[" + i + "].globarid").toString();

			boolean fetchedGKFromAPIIsInDB = dbResponse.containsKey(globerIdFromApiResponse);
			softAssert.assertTrue(fetchedGKFromAPIIsInDB, "Gatekeeper id = " + globerIdFromApiResponse
					+ " of API response is not available in the Database");
			GatekeepersDTO gatekeeperDetailsFromDB = dbResponse.get(globerIdFromApiResponse);
			if (fetchedGKFromAPIIsInDB && data.get("dtoType").toString().equals("FAT")) {

				String globerNameFromApiResponse = restUtils
						.getValueFromResponse(apiResponse, "$.details[" + i + "].username").toString();
				softAssert.assertEquals(globerNameFromApiResponse, gatekeeperDetailsFromDB.getGloberName(),
						"Name of Gatekeeper for globerID = " + globerIdFromApiResponse+" is not correct in API response");

				String globerPositionFromApiResponse = restUtils
						.getValueFromResponse(apiResponse, "$.details[" + i + "].position").toString();
				softAssert.assertEquals(globerPositionFromApiResponse, gatekeeperDetailsFromDB.getGloberPosition(),
						"Position of Gatekeeper for globerID = " + globerIdFromApiResponse+ " is not correct in API response");

				String globerSeniorityFromApiResponse = restUtils
						.getValueFromResponse(apiResponse, "$.details[" + i + "].seniority").toString();
				softAssert.assertEquals(globerSeniorityFromApiResponse, gatekeeperDetailsFromDB.getGloberSeniority(),
						"Seniority of Gatekeeper for globerID = " + globerIdFromApiResponse+" is not correct in API response");

				String globerLocationFromApiResponse = restUtils
						.getValueFromResponse(apiResponse, "$.details[" + i + "].location").toString();
				softAssert.assertEquals(globerLocationFromApiResponse, gatekeeperDetailsFromDB.getGloberLocation(),
						"Location of Gatekeeper for globerID = " + globerIdFromApiResponse+" is not correct in API response");

				List<Object> positionLevelList = (List<Object>) restUtils.getValueFromResponse(apiResponse,
						"$.details[*].positionLevel");
				softAssert.assertEquals(positionLevelList.size(), gatekeepersList.size(),
						"Total count of positionLevels is not correct for API response");

				String seniorityLevel =  restUtils.getValueFromResponse(apiResponse,
						"$.details["+i+"].seniorityLevel").toString();
				softAssert.assertEquals(seniorityLevel, gatekeeperDetailsFromDB.getGloberSeniorityLevel(),
						"Seniority level of Gatekeeper for globerID = " + globerIdFromApiResponse+" is not correct in API response");
			}
		}
		softAssert.assertAll();
		test.log(Status.PASS, "Gatekeepers list is fetched verified successfully with all details");

	}

	/**
	 * This test is to verify Search gatekeepers service is working for all positions
	 * @param data
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(priority=0, dataProvider ="validTestDataForAllPositions", dataProviderClass = SearchGatekeepersDataProviders.class)
	public void verifySearchGatekeepersService_ForAllPositions(Map<Object, Object> data) throws Exception {

		SearchGatekeepersTestHelper searchGatekeepersTestHelper = new SearchGatekeepersTestHelper();
		Response apiResponse=searchGatekeepersTestHelper.getGatekeepersList(data);
		validateResponseToContinueTest(apiResponse, 200, "Unable to search gatekeepers with valid test data", true);

		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(restUtils.getValueFromResponse(apiResponse, "message"),"success","Response message is incorrect for valid test data");

		String positionId=data.get(POSITION).toString();
		String seniorityId=data.get(SENIORITY).toString();
		String location=data.get(LOCATION).toString();
		
		SearchGatekeepersDBHelper searchGatekeepersDBHelper=new SearchGatekeepersDBHelper();
		Map<String, GatekeepersDTO> dbResponse = searchGatekeepersDBHelper.getGatekeepersList(positionId, seniorityId, location);
		Set<String> globersFromDBResponse = dbResponse.keySet();
		Reporter.log("Gatekeepers from DB query = "+ globersFromDBResponse.toString(),true);
		List<Object> gatekeepersList = (List<Object>) restUtils.getValueFromResponse(apiResponse, "$.details[*]");
		int totakGKInDataBase=globersFromDBResponse.size();
		int totalGKInApiResponse=gatekeepersList.size();
		softAssert.assertEquals(totalGKInApiResponse, totakGKInDataBase,
				"Gatekeepers total count = "+totalGKInApiResponse+" from api response is not equal to Gatekeepers total count ="+totakGKInDataBase+" from database response");

		List<Object> globerIdList = (List<Object>) restUtils.getValueFromResponse(apiResponse, "$.details[*].globarid");

		for (int i = 0; i < globerIdList.size(); i++) {

			String globerIdFromApiResponse = restUtils
					.getValueFromResponse(apiResponse, "$.details[" + i + "].globarid").toString();

			boolean fetchedGKFromAPIIsInDB = dbResponse.containsKey(globerIdFromApiResponse);
			softAssert.assertTrue(fetchedGKFromAPIIsInDB, "Gatekeeper id = " + globerIdFromApiResponse
					+ " of API response is not available in the Database");
			GatekeepersDTO gatekeeperDetailsFromDB = dbResponse.get(globerIdFromApiResponse);
			if (fetchedGKFromAPIIsInDB && data.get("dtoType").toString().equals("FAT")) {

				String globerNameFromApiResponse = restUtils
						.getValueFromResponse(apiResponse, "$.details[" + i + "].username").toString();
				softAssert.assertEquals(globerNameFromApiResponse, gatekeeperDetailsFromDB.getGloberName(),
						"Name of Gatekeeper for globerID = " + globerIdFromApiResponse+" is not correct in API response");

				String globerPositionFromApiResponse = restUtils
						.getValueFromResponse(apiResponse, "$.details[" + i + "].position").toString();
				softAssert.assertEquals(globerPositionFromApiResponse, gatekeeperDetailsFromDB.getGloberPosition(),
						"Position of Gatekeeper for globerID = " + globerIdFromApiResponse+ " is not correct in API response");

				String globerSeniorityFromApiResponse = restUtils
						.getValueFromResponse(apiResponse, "$.details[" + i + "].seniority").toString();
				softAssert.assertEquals(globerSeniorityFromApiResponse, gatekeeperDetailsFromDB.getGloberSeniority(),
						"Seniority of Gatekeeper for globerID = " + globerIdFromApiResponse+" is not correct in API response");

				String globerLocationFromApiResponse = restUtils
						.getValueFromResponse(apiResponse, "$.details[" + i + "].location").toString();
				softAssert.assertEquals(globerLocationFromApiResponse, gatekeeperDetailsFromDB.getGloberLocation(),
						"Location of Gatekeeper for globerID = " + globerIdFromApiResponse+" is not correct in API response");

				List<Object> positionLevelList = (List<Object>) restUtils.getValueFromResponse(apiResponse,
						"$.details[*].positionLevel");
				softAssert.assertEquals(positionLevelList.size(), gatekeepersList.size(),
						"Total count of positionLevels is not correct for API response");

				String seniorityLevel =  restUtils.getValueFromResponse(apiResponse,
						"$.details["+i+"].seniorityLevel").toString();
				softAssert.assertEquals(seniorityLevel, gatekeeperDetailsFromDB.getGloberSeniorityLevel(),
						"Seniority level of Gatekeeper for globerID = " + globerIdFromApiResponse+" is not correct in API response");
			}
		}
		softAssert.assertAll();
		test.log(Status.PASS, "Gatekeepers list is fetched verified successfully with all details");

	}

}
