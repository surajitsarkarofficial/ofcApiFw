package tests.testcases.submodules.interview.features;

import java.util.Map;
import java.util.Set;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;
import com.jayway.jsonpath.PathNotFoundException;

import database.submodules.gatekeepers.features.SearchGatekeepersDBHelper;
import dataproviders.submodules.interview.features.InterviewOnHolidayDataProvider;
import dto.submodules.gatekeepers.GatekeepersDTO;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import tests.testcases.submodules.interview.InterviewBaseTest;
import tests.testhelpers.submodules.gatekeepers.features.SearchGatekeepersTestHelper;
import tests.testhelpers.submodules.interview.InterviewTestHelper;
import tests.testhelpers.submodules.interview.features.PastAndFutureInterviewTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;
import utils.StaffingUtilities;

/**
 * 
 * @author deepakkumar.hadiya
 *
 */
public class InterviewOnHolidayTest extends InterviewBaseTest {
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("Recruiter creates an interview on holiday");
	}

	/**
	 * To verify If an interview is scheduled on public holiday for each country , the GK appointed from any country should not have evaluated more than 6 interviews
	 * @param data
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@Test(priority=0, dataProvider ="validTestDataForAllLocations", dataProviderClass = InterviewOnHolidayDataProvider.class, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void verifyInterviewOnPublicHolidayForEachCountry(Map<Object, Object> data) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		InterviewTestHelper testHelper = new InterviewTestHelper(); 
		testHelper.createInterviewRequest(data, softAssert);
		
		SearchGatekeepersTestHelper searchGatekeepersTestHelper = new SearchGatekeepersTestHelper();
		Response apiResponse=searchGatekeepersTestHelper.getGatekeepersList(data);
		validateResponseToContinueTest(apiResponse, 200, "Unable to search gatekeepers with valid test data", true);

		softAssert.assertEquals(restUtils.getValueFromResponse(apiResponse, "message"),"success","Response message is incorrect for valid test data");

		String positionId=data.get(POSITION).toString();
		String seniorityId=data.get(SENIORITY).toString();
		String location=data.get(LOCATION).toString();
		
		SearchGatekeepersDBHelper searchGatekeepersDBHelper=new SearchGatekeepersDBHelper();
		String interviewDay = StaffingUtilities.convertMilliSecondIntoDateWithTimeZone(data.get(INTERVIEW_DATE).toString(), DATABASE_DATE_FORMAT, DATABASE_TIMEZONE);
		System.out.println("InterviewDay - - >"+interviewDay);
		Map<String, GatekeepersDTO> dbResponse = searchGatekeepersDBHelper.getGatekeepersList(positionId, seniorityId, location,interviewDay);
		Set<String> globersFromDBResponse = dbResponse.keySet();
		Reporter.log("Gatekeepers from DB query = "+ globersFromDBResponse.toString(),true);
		JSONArray gatekeepersList = (JSONArray) restUtils.getValueFromResponse(apiResponse, "$.details[*]"); 
		int totakGKInDataBase=globersFromDBResponse.size();
		int totalGKInApiResponse=gatekeepersList.size();
		softAssert.assertEquals(totalGKInApiResponse, totakGKInDataBase,
				"Gatekeepers total count = "+totalGKInApiResponse+" from api response and Gatekeepers count ="+totakGKInDataBase+" from database response : ");
		PastAndFutureInterviewTestHelper pastAndFutureInterviewTestHelper=new PastAndFutureInterviewTestHelper();
		
		for (int i = 0; i < gatekeepersList.size(); i++) {

			Map gkDetail = (Map)gatekeepersList.get(i);
			String globerIdFromApiResponse = gkDetail.get("globarid").toString();

			test.log(Status.INFO,"Verifying evaluated interview count is not more than 6 for a gatekeeper - "+globerIdFromApiResponse);
			Response response = pastAndFutureInterviewTestHelper.getPastAndFutureInterviewDetails(THIN, globerIdFromApiResponse);
			validateResponseToContinueTest(response, 200, "Unable to get evaluated interview requests count for a gatekeeper - "+globerIdFromApiResponse,true);
			int previousAndCurrentWeekEvaluatedCount =0;
			try {
			previousAndCurrentWeekEvaluatedCount = (int) restUtils.getValueFromResponse(response, "$.details[0].previousAndCurrentWeekEvaluatedCount");
			}catch (PathNotFoundException e) {
			}
			softAssert.assertTrue(previousAndCurrentWeekEvaluatedCount<=6,"previousAndCurrentWeekEvaluatedCount is more than 6 expected : [<6] & actual : ["+previousAndCurrentWeekEvaluatedCount+"]");
			test.log(Status.PASS,"Successfully verified evaluated interview requests count is not more than 6 for a gatekeeper - "+globerIdFromApiResponse);
			
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
}
