package tests.testcases.submodules.interview.features;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import database.submodules.interview.InterviewDBHelper;
import dataproviders.submodules.interview.features.InterviewE2EDataProvider;
import dto.submodules.interview.InterviewDTO;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import tests.testcases.submodules.interview.InterviewBaseTest;
import tests.testhelpers.submodules.gatekeepers.features.SearchGatekeepersTestHelper;
import tests.testhelpers.submodules.interview.InterviewTestHelper;
import tests.testhelpers.submodules.interview.features.PastAndFutureInterviewTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;
import utils.StaffingUtilities;
import utils.Utilities;

/**
 * 
 * @author deepakkumar.hadiya
 *
 */

public class PastAndFutureInterviewTest extends InterviewBaseTest{

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("Past and future interview test");
	}

	/**
	 * This test is to verify count of evaluated interview requests in current and previous week for a specific gatekeeper
	 * Also verifying if count of evaluated interview requests in current and previous week > 5 then Gk gets moved to last in queue;
	 * @param data
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test(priority = 0, dataProvider = "valid_TestData_For_Interview_Requests_E2E_Scenario", dataProviderClass = InterviewE2EDataProvider.class, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void verify_Previous_Week_Evaluated_Interviews_Count(Map<Object, Object> data) throws Exception {
		
		String applyPosition = data.get(APPLY_POSITION).toString();
		
		test.log(Status.INFO, "Updating all interview's date to previous month for position = "+applyPosition);
		InterviewDBHelper dbHelper = new InterviewDBHelper();
		List<String> interviews=new ArrayList<String>(); 
		InterviewTestHelper testHelper=new InterviewTestHelper();
		dbHelper.updateInterviewDateForPosition(applyPosition, testHelper.getDateInMilliSecForDay(-20));
		test.log(Status.PASS, "Interview date is updated to previous month for position = "+applyPosition);
		
		try {
			SoftAssert softAssert = new SoftAssert();
			int days = 2;
			test.log(Status.INFO,"Creating interview date for after "+days+" day's date");
			String interviewDateWithTime = StaffingUtilities.getFutureDateAndTimeForMentionedUnit(DATABASE_DATE_FORMAT,Calendar.DATE,days);
			data.put(INTERVIEW_DATE,StaffingUtilities.convertDateIntoMilliSecond(interviewDateWithTime, DATABASE_DATE_FORMAT));
			test.log(Status.PASS,"Successfully creating interview date for after "+days+" day's date");
			
			test.log(Status.INFO,"Identifying gatekeeper for evaluating interview request");
			List<Integer> gkList =testHelper.getGatekeepersList(data);
			String interviewerId=gkList.get(Utilities.getRandomNumberBetween(0, gkList.size()-1)).toString();
			String candidateName=(String) data.get(CANDIDATE_NAME);
			int previousWeekEvaluatedCount=7;
			test.log(Status.PASS,"Successfully identified gatekeeper "+interviewerId +" for evaluating total "+previousWeekEvaluatedCount+" interview request");
			
			for(int i=1;i<=previousWeekEvaluatedCount;i++) {
		
				data.put(CANDIDATE_NAME, candidateName+i);
				String interviewId=testHelper.createInterviewRequest(data, softAssert);
				interviews.add(interviewId);
				
				test.log(Status.INFO,"Gatekeeper - "+interviewerId+" is accepting interview request");
				testHelper.acceptInterviewRequest(interviewId, interviewerId, gkList);
				Thread.sleep(WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS);
				
				test.log(Status.INFO, "Updating interview date randomly between monday of previous week and current day for interview - "+interviewId);
				int randomDay = Utilities.getRandomNumberBetween(MONDAY_OF_PREVIOUS_WEEK, testHelper.getCurrentDay());
				dbHelper.updateInterviewDate(interviewId,testHelper.getDateInMilliSecForDay(randomDay));				
				testHelper.evaluateInterview(interviewId, interviewerId);
				Thread.sleep(WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS);
				InterviewDTO interviewDetails = dbHelper.getInterviewDetails(interviewId);
				Assert.assertEquals(interviewDetails.getStatusFK(), "3030", "Interview status_fk is wrong in data base after evaluating an interview request");
				
				test.log(Status.INFO,"Verifying evaluated interview requests count for a gatekeeper - "+interviewerId);
				PastAndFutureInterviewTestHelper pastAndFutureInterviewTestHelper=new PastAndFutureInterviewTestHelper();
				Response response = pastAndFutureInterviewTestHelper.getPastAndFutureInterviewDetails(THIN, interviewerId);
				validateResponseToContinueTest(response, 200, "Unable to get past evaluated interview requests count for a gatekeeper - "+interviewerId,true);
				int previousAndCurrentWeekEvaluatedCount = (int) restUtils.getValueFromResponse(response, "$.details[0].previousAndCurrentWeekEvaluatedCount");
				softAssert.assertEquals(previousAndCurrentWeekEvaluatedCount, i,"previousAndCurrentWeekEvaluatedCount is not matching");
				test.log(Status.PASS,"Successfully verified evaluated interview requests count = "+i+" for a gatekeeper - "+interviewerId);

				SearchGatekeepersTestHelper searchGatekeepersTestHelper = new SearchGatekeepersTestHelper();
				Response apiResponse=searchGatekeepersTestHelper.getGatekeepersList(data);
				validateResponseToContinueTest(apiResponse, 200, "Unable to get gatekeepers list", true);

				JSONArray gatekeepersList = (JSONArray) restUtils.getValueFromResponse(apiResponse, "$.details[*]");
				List<Integer> seniorityLevels= (List<Integer>) restUtils.getValueFromResponse(apiResponse, "$..details[*].seniorityLevel");
				Integer lastSeniorityLevel = new TreeSet<Integer>(seniorityLevels).last();
				Map lastGKDetail = (Map)gatekeepersList.get(gatekeepersList.size()-1);
				String lastGKIdFromAPI = lastGKDetail.get("globarid").toString();
				Integer selectedGKSeniorityLevel = ((List<Integer>) restUtils.getValueFromResponse(apiResponse, "$..details[?(@.globarid=="+interviewerId+")].seniorityLevel")).get(0);
				
				if(i<6) {
					// Condition : If selected interviewer is already in last due to higher seniority level then no need to assert his movement to last 
					if(!(lastGKIdFromAPI.equals(interviewerId) && lastSeniorityLevel==selectedGKSeniorityLevel)) {
						test.log(Status.INFO, "Verifying the gatkeeper = "+interviewerId+" is not moved to last as his evaluated interview requests count = "+i);
						if(!lastGKIdFromAPI.equals(interviewerId)) {
							test.log(Status.PASS, "Successfully verified that the gatkeeper = "+interviewerId+" is not moved to last as his evaluated interview requests count = "+i);
						}else {
							test.log(Status.FAIL, "Gatkeeper = "+interviewerId+" is moved to last eventhogh his evaluated interview requests count = "+i);
						}
						softAssert.assertNotEquals(lastGKIdFromAPI, interviewerId,"Gatekeeper = "+interviewerId+" is not moved to last in the gatekeeper queue");
					}
					
				}else {
					test.log(Status.INFO, "Verifying the gatkeeper = "+interviewerId+" is moved to last as his evaluated interview requests count = "+i);
					if(lastGKIdFromAPI.equals(interviewerId)) {
						test.log(Status.PASS, "Successfully verified that the gatkeeper = "+interviewerId+" is moved to last as his evaluated interview requests count = "+i);
					}else {
						test.log(Status.FAIL, "Gatkeeper = "+interviewerId+" is not moved to last eventhogh his evaluated interview requests count = "+i);
					}
					softAssert.assertEquals(lastGKIdFromAPI, interviewerId,"Gatekeeper = "+interviewerId+" is not moved to last in the gatekeeper queue");
					
				}
			}
			softAssert.assertAll();		
		}finally {
			for(String interview:interviews) {
				Reporter.log("Updating interview date to previous month for all created interview", true);
				dbHelper.updateInterviewDate(interview,testHelper.getDateInMilliSecForDay(-20));
			}
			
		}
	}
	
	/**
	 * This test is to verify count of accepted interview requests in current and next week for a specific gatekeeper
	 * Also verifying if count of accepted interview requests in current and next week > 5 then Gk gets moved to last in queue;
	 * @param data
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test(priority = 0, dataProvider = "valid_TestData_For_Interview_Requests_E2E_Scenario", dataProviderClass = InterviewE2EDataProvider.class, groups = {
			ExeGroups.Regression})
	public void verify_Current_And_Next_Week_Accepted_Interviews_Count(Map<Object, Object> data) throws Exception {
		
		String applyPosition = data.get(APPLY_POSITION).toString();
		
		test.log(Status.INFO, "Updating all interview's date to previous month for position = "+applyPosition);
		InterviewDBHelper dbHelper = new InterviewDBHelper();
		List<String> interviews=new ArrayList<String>(); 
		InterviewTestHelper testHelper=new InterviewTestHelper();
		dbHelper.updateInterviewDateForPosition(applyPosition, testHelper.getDateInMilliSecForDay(-20));
		test.log(Status.PASS, "Interview date is updated to previous month for position = "+applyPosition);
		
		try {
			SoftAssert softAssert = new SoftAssert();
			int days = 2;
			test.log(Status.INFO,"Creating interview date for after "+days+" day's date");
			String interviewDateWithTime = StaffingUtilities.getFutureDateAndTimeForMentionedUnit(DATABASE_DATE_FORMAT,Calendar.DATE,days);
			data.put(INTERVIEW_DATE,StaffingUtilities.convertDateIntoMilliSecond(interviewDateWithTime, DATABASE_DATE_FORMAT));
			test.log(Status.PASS,"Successfully creating interview date for after "+days+" day's date");
			
			test.log(Status.INFO,"Identifying gatekeeper for accepting interview request");
			List<Integer> gkList =testHelper.getGatekeepersList(data);
			String interviewerId=gkList.get(Utilities.getRandomNumberBetween(0, gkList.size()-1)).toString();
			String candidateName=(String) data.get(CANDIDATE_NAME);
			int acceptedInterviewCount=7;
			test.log(Status.PASS,"Successfully identified gatekeeper "+interviewerId +" for accepting total "+acceptedInterviewCount+" interview request");
			
			for(int i=1;i<=acceptedInterviewCount;i++) {
		
				data.put(CANDIDATE_NAME, candidateName+i);
				String interviewId=testHelper.createInterviewRequest(data, softAssert);
				interviews.add(interviewId);
				
				test.log(Status.INFO,"Gatekeeper - "+interviewerId+" is accepting interview request");
				testHelper.acceptInterviewRequest(interviewId, interviewerId, gkList);
				Thread.sleep(WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS);
				
				test.log(Status.INFO, "Updating interview date randomly between monday of current week and sunday of next week for interview - "+interviewId);
				int randomDay = Utilities.getRandomNumberBetween(MONDAY_OF_CURRENT_WEEK, SUNDAY_OF_NEXT_WEEK);
				dbHelper.updateInterviewDate(interviewId,testHelper.getDateInMilliSecForDay(randomDay));				
				
				test.log(Status.INFO,"Verifying accepted interview requests count for a gatekeeper - "+interviewerId);
				PastAndFutureInterviewTestHelper pastAndFutureInterviewTestHelper=new PastAndFutureInterviewTestHelper();
				Response response = pastAndFutureInterviewTestHelper.getPastAndFutureInterviewDetails(THIN, interviewerId);
				validateResponseToContinueTest(response, 200, "Unable to get past evaluated interview requests count for a gatekeeper - "+interviewerId,true);
				
				int currentAndNextWeekAcceptedCount = (int) restUtils.getValueFromResponse(response, "$.details[0].currentAndNextWeekAcceptedCount");
				softAssert.assertEquals(currentAndNextWeekAcceptedCount, i,"currentAndNextWeekAcceptedCount is not matching");
				test.log(Status.PASS,"Successfully verified accepted interview requests count = "+i+" for a gatekeeper - "+interviewerId);

				SearchGatekeepersTestHelper searchGatekeepersTestHelper = new SearchGatekeepersTestHelper();
				Response apiResponse=searchGatekeepersTestHelper.getGatekeepersList(data);
				validateResponseToContinueTest(apiResponse, 200, "Unable to get gatekeepers list", true);

				JSONArray gatekeepersList = (JSONArray) restUtils.getValueFromResponse(apiResponse, "$.details[*]");
				List<Integer> seniorityLevels= (List<Integer>) restUtils.getValueFromResponse(apiResponse, "$..details[*].seniorityLevel");
				Integer lastSeniorityLevel = new TreeSet<Integer>(seniorityLevels).last();
				Map lastGKDetail = (Map)gatekeepersList.get(gatekeepersList.size()-1);
				String lastGKIdFromAPI = lastGKDetail.get("globarid").toString();
				Integer selectedGKSeniorityLevel = ((List<Integer>) restUtils.getValueFromResponse(apiResponse, "$..details[?(@.globarid=="+interviewerId+")].seniorityLevel")).get(0);
				
				if(i<6) {
					// Condition : If selected interviewer is already in last due to higher seniority level then no need to assert his movement to last 
					if(!(lastGKIdFromAPI.equals(interviewerId) && lastSeniorityLevel==selectedGKSeniorityLevel)) {
						test.log(Status.INFO, "Verifying the gatkeeper = "+interviewerId+" is not moved to last as his accepted interview requests count = "+i);
						if(!lastGKIdFromAPI.equals(interviewerId)) {
							test.log(Status.PASS, "Successfully verified that the gatkeeper = "+interviewerId+" is not moved to last as his accepted interview requests count = "+i);
						}else {
							test.log(Status.FAIL, "Gatkeeper = "+interviewerId+" is moved to last eventhogh his accepted interview requests count = "+i);
						}
						softAssert.assertNotEquals(lastGKIdFromAPI, interviewerId,"Gatekeeper = "+interviewerId+" is not moved to last in the gatekeeper queue");
					}
					
				}else {
					test.log(Status.INFO, "Verifying the gatkeeper = "+interviewerId+" is moved to last as his accepted interview requests count = "+i);
					if(lastGKIdFromAPI.equals(interviewerId)) {
						test.log(Status.PASS, "Successfully verified that the gatkeeper = "+interviewerId+" is moved to last as his accepted interview requests count = "+i);
					}else {
						test.log(Status.FAIL, "Gatkeeper = "+interviewerId+" is not moved to last eventhogh his accepted interview requests count = "+i);
					}
					softAssert.assertEquals(lastGKIdFromAPI, interviewerId,"Gatekeeper = "+interviewerId+" is not moved to last in the gatekeeper queue");
					
				}
			}
			softAssert.assertAll();		
		}finally {
			for(String interview:interviews) {
				Reporter.log("Updating interview date to previous month for all created interview", true);
				dbHelper.updateInterviewDate(interview,testHelper.getDateInMilliSecForDay(-20));
			}
			
		}
	}
}
