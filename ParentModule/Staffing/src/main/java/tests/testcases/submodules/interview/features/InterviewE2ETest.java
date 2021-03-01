package tests.testcases.submodules.interview.features;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import database.submodules.interview.InterviewDBHelper;
import database.submodules.leave.LeaveDBHelper;
import dataproviders.submodules.interview.features.InterviewE2EDataProvider;
import dto.submodules.interview.GatekeepersSearchResultDTO;
import dto.submodules.interview.InterviewDTO;
import dto.submodules.interview.InterviewRequestDTO;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import tests.testcases.submodules.interview.InterviewBaseTest;
import tests.testhelpers.submodules.interview.InterviewTestHelper;
import tests.testhelpers.submodules.interview.features.AcceptOrRejectInterviewTestHelper;
import tests.testhelpers.submodules.interview.features.InterviewRequestsOfGatekeeperTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;
import utils.StaffingUtilities;
import utils.Utilities;

public class InterviewE2ETest extends InterviewBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("End to end tests for interview request");
		LeaveDBHelper leaveDBHelper=new LeaveDBHelper();
		try {
			leaveDBHelper.deleteAllAutomationLeaves();
		}catch (Exception e) {
		}
	}
	
	/**
	 * Re-Run :- This test is to verify that when first gatekeeper rejects the
	 * interview request and remaining gatekeepers ignore the interview request from
	 * current batch then new interview request should be sent to next batch of gatekeepers.
	 * 
	 * Also, verifying that Gatekeeper is ignored in re-run who
	 * have already ignored/rejected the interview request, new Rerun time and "No Gk found" email to recruiter 
	 * 
	 * @param data
	 * @throws Exception
	 * @author deepakkumar.hadiya
	 */
	@SuppressWarnings("unchecked")
	@Test(priority = 1, dataProvider = "valid_TestData_For_Interview_Requests_E2E_Scenario", dataProviderClass = InterviewE2EDataProvider.class, groups = {
			ExeGroups.Regression,ExeGroups.NotAvailableInPreProd })
	public void verify_Rerun_For_First_GK_Rejects_And_Remaining_GK_Ignore_InterviewRequest(
			Map<Object, Object> data) throws Exception {
		
		SoftAssert softAssert = new SoftAssert();
		InterviewTestHelper interviewTestHelper=new InterviewTestHelper();
		interviewTestHelper.updateInterviewDateIfItIsOnHoliday(data);
		String interviewId = interviewTestHelper.createInterviewRequest(data,softAssert);
		List<Integer> gkList =interviewTestHelper.getGatekeepersList(data); 
		int totalGKCount =gkList.size();
		
		InterviewDBHelper dbHelper = new InterviewDBHelper();
		Thread.sleep(WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS);
		String expectedInterviewDate = StaffingUtilities.convertMilliSecondIntoDateWithTimeZone(data.get(INTERVIEW_DATE).toString(), DATABASE_DATE_FORMAT,DATABASE_TIMEZONE);
		InterviewDTO interviewDetails = dbHelper.getInterviewDetails(interviewId);
		softAssert.assertEquals(interviewDetails.getInterviewDate(), expectedInterviewDate, "Interview date is wrong in data base after creating an interview request");
		
		if(totalGKCount>0) {
		
			Map<String, Object> testHelperData = interviewTestHelper.firstGkRejectAndRemainingGkIgnoreInterviewRequest(interviewId, gkList, true, true);
			Map<String,String> actionDetails=(Map<String,String>)testHelperData.get("actionDetails");
			Integer totalRerunCount= (Integer) testHelperData.get("totalRerunCount");
			
			List<InterviewRequestDTO> interviewRequestDetailsList = dbHelper.getInterviewRequestDetails(interviewId);
			test.log(Status.INFO,"Verifying first gatekeeper of each batch rejects the interview requests and remaining gatekeepers of each batch ignore the interview request");
			for(InterviewRequestDTO interviewRequestDetails:interviewRequestDetailsList) {
				String interviewerName = interviewRequestDetails.getInterviewerName();
				String interviewerId = interviewRequestDetails.getInterviewerId();
				softAssert.assertEquals(interviewRequestDetails.getInterviewRequestAction(),actionDetails.get(interviewRequestDetails.getInterviewerId()),
						"Interview request action status is incorrect for gatekeeper = '"+ interviewerName + " - " + interviewerId + "'");
			}
			test.log(Status.PASS,"Successfully verified that first gatekeeper of each batch has rejected the interview requests and remaining gatekeepers of each batch have ignored the interview request");
			
			test.log(Status.INFO,"Verifying re-reun is happening after waiting time");
			int firstGkIndexFromPreviousBatch=0;
			for (int j = 0; j < totalRerunCount-1; j++) {
				String previousBatchRunTime = interviewRequestDetailsList.get(firstGkIndexFromPreviousBatch).getInterviewCreationDate();
				String nextBatchRunTime = interviewRequestDetailsList.get(firstGkIndexFromPreviousBatch+TOTAL_GK_FOR_RE_RUN).getInterviewCreationDate();
				String expectedMinTimeForNextRerun=StaffingUtilities.getFutureDateAndTimeForMentionedUnit(DATABASE_DATE_FORMAT, Calendar.MINUTE, WAIT_TIME_FOR_RERUN_IN_MIN , previousBatchRunTime);
				String expectedMaxTimeForNextRerun=StaffingUtilities.getFutureDateAndTimeForMentionedUnit(DATABASE_DATE_FORMAT, Calendar.MINUTE, WAIT_TIME_FOR_RERUN_IN_MIN+WAIT_TIME_FREQUENCY_FOR_RERUN_IN_MIN+1 , previousBatchRunTime);
				boolean minTimeForNextRerunSatisfied=StaffingUtilities.firstDateIsAfterSecondDate(DATABASE_DATE_FORMAT, nextBatchRunTime, expectedMinTimeForNextRerun);
				boolean maxTimeForNextRerunSatisfied=StaffingUtilities.firstDateIsAfterSecondDate(DATABASE_DATE_FORMAT, expectedMaxTimeForNextRerun,nextBatchRunTime);
				softAssert.assertTrue(minTimeForNextRerunSatisfied, "Rerun for batch no = "+(j+1)+" was not happend after "+(WAIT_TIME_FOR_RERUN_IN_MIN)+" minutes");
				softAssert.assertTrue(maxTimeForNextRerunSatisfied, "Rerun for batch no = "+(j+1)+" was not happend before "+(WAIT_TIME_FOR_RERUN_IN_MIN+WAIT_TIME_FREQUENCY_FOR_RERUN_IN_MIN+1)+" minutes");
				firstGkIndexFromPreviousBatch=firstGkIndexFromPreviousBatch+TOTAL_GK_FOR_RE_RUN;
			}
			test.log(Status.PASS,"Successfully verified that re-reun was happened after waiting time");
			
			
			test.log(Status.INFO,"Verifying next batch of gatekeepers get interview request when previous batch of gatekeepers ignore/reject interview request");
			Set<String> actionsList = actionDetails.keySet();
			for(String interviewers:actionsList) {
				softAssert.assertTrue(gkList.contains(Integer.valueOf(interviewers)), "Gatkekeeper : '"+interviewers+"' is not valid gatekeeper, list of valid gatekeepers : "+gkList.toString());
			}
			softAssert.assertEquals(actionsList.size(), totalGKCount, "Gatekeepers count is incorrect in data base");

			test.log(Status.INFO, "Verifying 'No GK found' email is send to recruiter after last rerun");
			GatekeepersSearchResultDTO gkSearchResult=dbHelper.getGatekeepersSearchResult(interviewId);
			softAssert.assertEquals(gkSearchResult.getEmailSentToRecruiter(), TrueInDBFormat,"'No GK found' email is not sent to recruiter after last rerun");
			test.log(Status.PASS, "Successfully verified that 'No GK found' email is send to recruiter after last rerun");
			
			softAssert.assertAll();
			test.log(Status.PASS,"Successfully verified that next batch of gatekeepers have got interview request when previous batch of gatekeepers ignore/reject interview request");
		}else {
			test.log(Status.INFO, "Verifying 'No GK found' email is send to recruiter in first run");
			GatekeepersSearchResultDTO gkSearchResult=dbHelper.getGatekeepersSearchResult(interviewId);
			Assert.assertEquals(gkSearchResult.getEmailSentToRecruiter(), TrueInDBFormat,"'No GK found' email is not sent to recruiter in first run");
			Assert.assertEquals(gkSearchResult.getOccouredWhile(), FIRST_RUN,"'No GK found' email is not sent to recruiter in first run");
			test.log(Status.PASS, "Successfully verified that 'No GK found' email is send to recruiter in first run");
		}
		Reporter.log("Executed for position = "+data.get(APPLY_POSITION)+", seniority = "+data.get(APPLY_SENIORITY)+" and location = "+data.get(LOCATION),true);
	}

	/**
	 * Re-Run :- This test is to verify that when all gatekeepers of first batch
	 * ignore the interview request then new interview request should be sent to new
	 * gatekeepers
	 * 
	 * Also, verifying that Gatekeeper is ignored in re-run who
	 * have already ignored the interview request, new Rerun time and "No Gk found" email to recruiter
	 * 
	 * @param data
	 * @author deepakkumar.hadiya
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test(priority = 2, dataProvider = "valid_TestData_For_Interview_Requests_E2E_Scenario", dataProviderClass = InterviewE2EDataProvider.class, groups = {
			ExeGroups.Sanity, ExeGroups.Regression, ExeGroups.NotAvailableInPreProd})
	public void verify_Rerun_For_All_GK_Ignore_InterviewRequest(
			Map<Object, Object> data) throws Exception {

		SoftAssert softAssert = new SoftAssert();
		InterviewTestHelper interviewTestHelper=new InterviewTestHelper();
		interviewTestHelper.updateInterviewDateIfItIsOnHoliday(data);
		String interviewId = interviewTestHelper.createInterviewRequest(data,softAssert);
		List<Integer> gkList =interviewTestHelper.getGatekeepersList(data); 
		int totalGKCount =gkList.size();
		
		InterviewDBHelper dbHelper = new InterviewDBHelper();
		Thread.sleep(WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS);
		String expectedInterviewDate = StaffingUtilities.convertMilliSecondIntoDateWithTimeZone(data.get(INTERVIEW_DATE).toString(), DATABASE_DATE_FORMAT,DATABASE_TIMEZONE);
		InterviewDTO interviewDetails = dbHelper.getInterviewDetails(interviewId);
		softAssert.assertEquals(interviewDetails.getInterviewDate(), expectedInterviewDate, "Interview date is wrong in data base after creating an interview request");
		
		if(totalGKCount>0) {
			
			test.log(Status.INFO,"all gatekeepers of each batch ignore the interview request");
			Map<String, Object> testHelperData = interviewTestHelper.ignoreAllInterviewRequest(interviewId, gkList,true,true,false);
			Map<String,String> actionDetails=(Map<String,String>)testHelperData.get("actionDetails");
			Integer totalRerunCount= (Integer) testHelperData.get("totalRerunCount");
			
			List<InterviewRequestDTO> interviewRequestDetailsList = dbHelper.getInterviewRequestDetails(interviewId);
			test.log(Status.INFO,"Verifying all gatekeepers of each batch ignore the interview request");
			for(InterviewRequestDTO interviewRequestDetails:interviewRequestDetailsList) {
				String interviewerName = interviewRequestDetails.getInterviewerName();
				String interviewerId = interviewRequestDetails.getInterviewerId();
				softAssert.assertEquals(interviewRequestDetails.getInterviewRequestAction(),actionDetails.get(interviewRequestDetails.getInterviewerId()),
						"Interview request action status is incorrect for gatekeeper = '"+ interviewerName + " - " + interviewerId + "'");
			}
			test.log(Status.PASS,"Successfully verified that all gatekeepers of each batch have ignored the interview request");
			
			test.log(Status.INFO,"Verifying re-reun is happening after waiting time");
			String DATE_FORMAT_IN_DATABASE = "yyyy-MM-dd HH:mm:ss";
			int firstGkIndexFromPreviousBatch=0;
			for (int j = 0; j < totalRerunCount-1; j++) {
				String previousBatchRunTime = interviewRequestDetailsList.get(firstGkIndexFromPreviousBatch).getInterviewCreationDate();
				String nextBatchRunTime = interviewRequestDetailsList.get(firstGkIndexFromPreviousBatch+TOTAL_GK_FOR_RE_RUN).getInterviewCreationDate();
				String expectedMinTimeForNextRerun=StaffingUtilities.getFutureDateAndTimeForMentionedUnit(DATABASE_DATE_FORMAT, Calendar.MINUTE, WAIT_TIME_FOR_RERUN_IN_MIN , previousBatchRunTime);
				String expectedMaxTimeForNextRerun=StaffingUtilities.getFutureDateAndTimeForMentionedUnit(DATABASE_DATE_FORMAT, Calendar.MINUTE, WAIT_TIME_FOR_RERUN_IN_MIN+WAIT_TIME_FREQUENCY_FOR_RERUN_IN_MIN+1 , previousBatchRunTime);
				boolean minTimeForNextRerunSatisfied=StaffingUtilities.firstDateIsAfterSecondDate(DATE_FORMAT_IN_DATABASE, nextBatchRunTime, expectedMinTimeForNextRerun);
				boolean maxTimeForNextRerunSatisfied=StaffingUtilities.firstDateIsAfterSecondDate(DATE_FORMAT_IN_DATABASE, expectedMaxTimeForNextRerun,nextBatchRunTime);
				softAssert.assertTrue(minTimeForNextRerunSatisfied, "Rerun for batch no = "+(j+1)+" was not happend after "+(WAIT_TIME_FOR_RERUN_IN_MIN)+" minutes");
				softAssert.assertTrue(maxTimeForNextRerunSatisfied, "Rerun for batch no = "+(j+1)+" was not happend before "+(WAIT_TIME_FOR_RERUN_IN_MIN+WAIT_TIME_FREQUENCY_FOR_RERUN_IN_MIN+1)+" minutes");
				firstGkIndexFromPreviousBatch=firstGkIndexFromPreviousBatch+TOTAL_GK_FOR_RE_RUN;
			}
			test.log(Status.PASS,"Successfully verified that re-reun was happened after waiting time");
			
			
			test.log(Status.INFO,"Verifying only next batch of gatekeepers get interview request when previous batch of gatekeepers ignore interview request");
			Set<String> actionsList = actionDetails.keySet();
			for(String interviewers:actionsList) {
				softAssert.assertTrue(gkList.contains(Integer.valueOf(interviewers)), "Gatkekeeper : '"+interviewers+"' is not valid gatekeeper, list of valid gatekeepers : "+gkList.toString());
			}
			softAssert.assertEquals(actionsList.size(), totalGKCount, "Gatekeepers count is incorrect in data base");
			
			test.log(Status.INFO, "Verifying 'No GK found' email is send to recruiter after last rerun");
			GatekeepersSearchResultDTO gkSearchResult=dbHelper.getGatekeepersSearchResult(interviewId);
			softAssert.assertEquals(gkSearchResult.getEmailSentToRecruiter(), TrueInDBFormat,"'No GK found' email is not sent to recruiter after last rerun");
			softAssert.assertEquals(gkSearchResult.getOccouredWhile(), RERUN,"'No GK found' email is not sent to recruiter after last rerun");
			test.log(Status.PASS, "Successfully verified that 'No GK found' email is send to recruiter after last rerun");
			
			softAssert.assertAll();
			test.log(Status.PASS,"Successfully verified that next batch of gatekeepers have got interview request when previous batch of gatekeepers ignore interview request");
		}else {
			test.log(Status.INFO, "Verifying 'No GK found' email is send to recruiter in first run");
			GatekeepersSearchResultDTO gkSearchResult=dbHelper.getGatekeepersSearchResult(interviewId);
			Assert.assertEquals(gkSearchResult.getEmailSentToRecruiter(), TrueInDBFormat,"'No GK found' email is not sent to recruiter in first run");
			Assert.assertEquals(gkSearchResult.getOccouredWhile(), FIRST_RUN,"'No GK found' email is not sent to recruiter in first run");
			test.log(Status.PASS, "Successfully verified that 'No GK found' email is send to recruiter in first run");
		}
		Reporter.log("Executed for position = "+data.get(APPLY_POSITION)+", seniority = "+data.get(APPLY_SENIORITY)+" and location = "+data.get(LOCATION),true);
	
	}

	/**
	 * Re-Run :- This test is to verify that when all gatekeepers rejects the
	 * interview request from current batch then new interview request should be sent to next batch of gatekeepers.
	 * 
	 * Also, verifying that Gatekeeper is ignored in re-run who
	 * have already rejected the interview request, new Rerun time and "No Gk found" email to recruiter
	 * 
	 * @param data
	 * @throws Exception
	 * @author deepakkumar.hadiya
	 */
	@SuppressWarnings("unchecked")
	@Test(priority = 3, dataProvider = "valid_TestData_For_Interview_Requests_E2E_Scenario", dataProviderClass = InterviewE2EDataProvider.class, groups = {
			ExeGroups.Sanity,ExeGroups.Regression })
	public void verify_Rerun_For_All_GK_Reject_InterviewRequest(
			Map<Object, Object> data) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		InterviewTestHelper interviewTestHelper=new InterviewTestHelper();
		interviewTestHelper.updateInterviewDateIfItIsOnHoliday(data);
		String interviewId = interviewTestHelper.createInterviewRequest(data,softAssert);
		List<Integer> gkList =interviewTestHelper.getGatekeepersList(data); 
		int totalGKCount =gkList.size();
			
		InterviewDBHelper dbHelper = new InterviewDBHelper();
		Thread.sleep(WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS);
		String expectedInterviewDate = StaffingUtilities.convertMilliSecondIntoDateWithTimeZone(data.get(INTERVIEW_DATE).toString(), DATABASE_DATE_FORMAT,DATABASE_TIMEZONE);
		InterviewDTO interviewDetails = dbHelper.getInterviewDetails(interviewId);
		softAssert.assertEquals(interviewDetails.getInterviewDate(), expectedInterviewDate, "Interview date is wrong in data base after creating an interview request");
		
		if(totalGKCount>0) {
		
			test.log(Status.INFO,"all gatekeepers of each batch ignore the interview request");
			Map<String, Object> testHelperData = interviewTestHelper.rejectAllInterviewRequest(interviewId, gkList,true,false);
			Map<String,String> actionDetails=(Map<String,String>)testHelperData.get("actionDetails");
			
			List<InterviewRequestDTO> interviewRequestDetailsList = dbHelper.getInterviewRequestDetails(interviewId);
			test.log(Status.INFO,"Verifying all gatekeepers of each batch ignore the interview request");
			for(InterviewRequestDTO interviewRequestDetails:interviewRequestDetailsList) {
				String interviewerName = interviewRequestDetails.getInterviewerName();
				String interviewerId = interviewRequestDetails.getInterviewerId();
				softAssert.assertEquals(interviewRequestDetails.getInterviewRequestAction(),actionDetails.get(interviewRequestDetails.getInterviewerId()),
						"Interview request action status is incorrect for gatekeeper = '"+ interviewerName + " - " + interviewerId + "'");
			}
			test.log(Status.PASS,"Successfully verified that all gatekeepers of each batch have ignored the interview request");
			
			
			test.log(Status.INFO,"Verifying next batch of gatekeepers get interview request when previous batch of gatekeepers reject interview request");
			Set<String> actionsList = actionDetails.keySet();
			for(String interviewers:actionsList) {
				softAssert.assertTrue(gkList.contains(Integer.valueOf(interviewers)), "Gatkekeeper : '"+interviewers+"' is not valid gatekeeper, list of valid gatekeepers : "+gkList.toString());
			}
			interviewDetails = dbHelper.getInterviewDetails(interviewId);
			softAssert.assertEquals(interviewDetails.getInterviewDate(), expectedInterviewDate, "Interview date is wrong in data base after completion of re-running algorithm");
			softAssert.assertEquals(actionsList.size(), totalGKCount, "Gatekeepers count is incorrect in data base");

			test.log(Status.INFO, "Verifying 'No GK found' email is send to recruiter after last rerun");
			GatekeepersSearchResultDTO gkSearchResult=dbHelper.getGatekeepersSearchResult(interviewId);
			softAssert.assertEquals(gkSearchResult.getEmailSentToRecruiter(), TrueInDBFormat,"'No GK found' email is not sent to recruiter after last rerun");
			softAssert.assertEquals(gkSearchResult.getOccouredWhile(), ALL_REJECT,"'No GK found' email is not sent to recruiter after last rerun");
			test.log(Status.PASS, "Successfully verified that 'No GK found' email is send to recruiter after last rerun");
			
			softAssert.assertAll();
			test.log(Status.PASS,"Successfully verified that next batch of gatekeepers have got interview request when previous batch of gatekeepers reject interview request");
		}else {
			test.log(Status.INFO, "Verifying 'No GK found' email is send to recruiter in first run");
			GatekeepersSearchResultDTO gkSearchResult=dbHelper.getGatekeepersSearchResult(interviewId);
			Assert.assertEquals(gkSearchResult.getEmailSentToRecruiter(), TrueInDBFormat,"'No GK found' email is not sent to recruiter in first run");
			Assert.assertEquals(gkSearchResult.getOccouredWhile(), FIRST_RUN,"'No GK found' email is not sent to recruiter in first run");
			test.log(Status.PASS, "Successfully verified that 'No GK found' email is send to recruiter in first run");
		}
		Reporter.log("Executed for position = "+data.get(APPLY_POSITION)+", seniority = "+data.get(APPLY_SENIORITY)+" and location = "+data.get(LOCATION),true);
	}

	/**
	 * Re-Run :- This test is to verify that when one gatekeeper accepts the interview request from   
	 * current batch then new interview request should not be sent to next batch of gatekeepers.
	 * 
	 * @param data
	 * @throws Exception
	 * @author deepakkumar.hadiya
	 */
	@SuppressWarnings("unchecked")
	@Test(priority = 4, dataProvider = "rerun_Scenario_Data_For_Interview_Requests", dataProviderClass = InterviewE2EDataProvider.class, groups = {
			ExeGroups.Regression,ExeGroups.NotAvailableInPreProd })
	public void verify_Rerun_Untill_GK_Accept_InterviewRequest_All_GK_Ignore_Request(
			Map<Object, Object> data) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		InterviewTestHelper interviewTestHelper=new InterviewTestHelper();
		interviewTestHelper.updateInterviewDateIfItIsOnHoliday(data);
		String interviewId = interviewTestHelper.createInterviewRequest(data,softAssert);
		List<Integer> gkList =interviewTestHelper.getGatekeepersList(data); 
		int totalGKCount =gkList.size();
		
		InterviewDBHelper dbHelper = new InterviewDBHelper();
		Thread.sleep(WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS);
		String expectedInterviewDate = StaffingUtilities.convertMilliSecondIntoDateWithTimeZone(data.get(INTERVIEW_DATE).toString(), DATABASE_DATE_FORMAT,DATABASE_TIMEZONE);
		InterviewDTO interviewDetails = dbHelper.getInterviewDetails(interviewId);
		softAssert.assertEquals(interviewDetails.getInterviewDate(), expectedInterviewDate, "Interview date is wrong in data base after creating an interview request");
		
		if(totalGKCount>0) {
			Map<String, Object> testHelperData = interviewTestHelper.ignoreAllInterviewRequest(interviewId, gkList, false, false, true);
			Map<String,String> actionDetails=(Map<String,String>)testHelperData.get("actionDetails");
			Integer gkCountTillAcceptingInterviewRequest=(Integer) testHelperData.get("gkCountTillAcceptingInterviewRequest");
			List<String> gkListWithRequestSentStatus=(List<String>) testHelperData.get("gkListWithRequestSentStatus");
			
			test.log(Status.INFO,"Waiting till rerun time to check re-run is not happening for request_accepted case");
			Thread.sleep(WAIT_TIME_FOR_RERUN_IN_MILLI_SECONDS+WAIT_TIME_FREQUENCY_FOR_RERUN_IN_MILLI_SECONDS+BUFFER_TIME_IN_MILLI_SECONDS);
			List<InterviewRequestDTO> interviewRequestDetailsList = dbHelper.getInterviewRequestDetails(interviewId);	
				
			test.log(Status.INFO,"Waiting till rerun time to check re-run is not happening for request_accepted case");
			Thread.sleep(WAIT_TIME_FOR_RERUN_IN_MILLI_SECONDS+WAIT_TIME_FREQUENCY_FOR_RERUN_IN_MILLI_SECONDS+BUFFER_TIME_IN_MILLI_SECONDS);
			interviewRequestDetailsList = dbHelper.getInterviewRequestDetails(interviewId);
			softAssert.assertEquals(Integer.valueOf(interviewRequestDetailsList.size()), gkCountTillAcceptingInterviewRequest,"Re-run is happend after accepting interview request");
			test.log(Status.PASS,"Successfully verified that next batch of gatekeepers have not got interview request when one gatekeeper from previous batch accepts interview request");
				
			test.log(Status.INFO,"Verifying one gatekeeper has 'request_accepted status and remaining have 'request_ignored' or 'request_sent' status");
			for(int p=0;p<interviewRequestDetailsList.size();p++) {
				String interviewerName = interviewRequestDetailsList.get(p).getInterviewerName();
				String interviewerId = interviewRequestDetailsList.get(p).getInterviewerId();
				if(actionDetails.containsKey(interviewerId)) {
					if(gkListWithRequestSentStatus.contains(interviewerId)) {
						softAssert.assertEquals(interviewRequestDetailsList.get(p).getInterviewRequestAction(),REQUEST_SENT,
								"Interview request action status is incorrect for gatekeeper = '"+ interviewerName + " - " + interviewerId + "'");
					}else {
					softAssert.assertEquals(interviewRequestDetailsList.get(p).getInterviewRequestAction(),actionDetails.get(interviewerId),
							"Interview request action status is incorrect for gatekeeper = '"+ interviewerName + " - " + interviewerId + "'");
					}
				}else {
					softAssert.assertEquals(interviewRequestDetailsList.get(p).getInterviewRequestAction(),REQUEST_SENT,
							"Interview request action status is incorrect for gatekeeper = '"+ interviewerName + " - " + interviewerId + "'");
				}
			}
			test.log(Status.PASS,"Successfully verified that all gatekeepers of each batch have valid action status for the interview request");
			softAssert.assertAll();
				test.log(Status.PASS,"Successfully verified that all gatekeepers of each batch have valid action status for the interview request");
				softAssert.assertAll();
		}else {
			test.log(Status.PASS, "Gatekeepers are not available for position = "+data.get(POSITION)+", seniority = "+data.get(SENIORITY)+" and location = "+data.get(LOCATION));
		}
		Reporter.log("Executed for position = "+data.get(APPLY_POSITION)+", seniority = "+data.get(APPLY_SENIORITY)+" and location = "+data.get(LOCATION),true);
	}

	/**
	 * Re-Run :- This test is to verify that when one gatekeeper accepts the interview request from   
	 * current batch then new interview request should not be sent to next batch of gatekeepers.
	 * 
	 * @param data
	 * @throws Exception
	 * @author deepakkumar.hadiya
	 */
	@SuppressWarnings("unchecked")
	@Test(priority = 5, dataProvider = "rerun_Scenario_Data_For_Interview_Requests", dataProviderClass = InterviewE2EDataProvider.class, groups = {
			ExeGroups.Regression, ExeGroups.NotAvailableInPreProd })
	public void verify_Rerun_Untill_GK_Accept_InterviewRequest_All_GK_Reject_Request(
			Map<Object, Object> data) throws Exception {
		
		SoftAssert softAssert = new SoftAssert();
		InterviewTestHelper interviewTestHelper=new InterviewTestHelper();
		interviewTestHelper.updateInterviewDateIfItIsOnHoliday(data);
		String interviewId = interviewTestHelper.createInterviewRequest(data,softAssert);
		List<Integer> gkList =interviewTestHelper.getGatekeepersList(data); 
		int totalGKCount =gkList.size();
		
		
		InterviewDBHelper dbHelper = new InterviewDBHelper();
		Thread.sleep(WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS);
		String expectedInterviewDate = StaffingUtilities.convertMilliSecondIntoDateWithTimeZone(data.get(INTERVIEW_DATE).toString(), DATABASE_DATE_FORMAT,DATABASE_TIMEZONE);
		InterviewDTO interviewDetails = dbHelper.getInterviewDetails(interviewId);
		softAssert.assertEquals(interviewDetails.getInterviewDate(), expectedInterviewDate, "Interview date is wrong in data base after creating an interview request");
	
		if(totalGKCount>0) {
		
		Map<String, Object> testHelperData = interviewTestHelper.rejectAllInterviewRequest(interviewId, gkList,false,true);
		Map<String,String> actionDetails=(Map<String,String>)testHelperData.get("actionDetails");
		Integer gkCountTillAcceptingInterviewRequest=(Integer) testHelperData.get("gkCountTillAcceptingInterviewRequest");
		
		test.log(Status.INFO,"Waiting till rerun time to check re-run is not happening for request_accepted case");
		Thread.sleep(WAIT_TIME_FOR_RERUN_IN_MILLI_SECONDS+WAIT_TIME_FREQUENCY_FOR_RERUN_IN_MILLI_SECONDS+BUFFER_TIME_IN_MILLI_SECONDS);
		List<InterviewRequestDTO> interviewRequestDetailsList = dbHelper.getInterviewRequestDetails(interviewId);	
		
		int gkCountAfterWaitingForRerunTime=interviewRequestDetailsList.size();
		softAssert.assertEquals(Integer.valueOf(gkCountAfterWaitingForRerunTime), gkCountTillAcceptingInterviewRequest,"Re-run is happend after accepting interview request");
		test.log(Status.PASS,"Successfully verified that next batch of gatekeepers have not got interview request when one gatekeeper from previous batch accepts interview request");
			
		test.log(Status.INFO,"Verifying one gatekeeper has 'request_accepted status and remaining have 'request_rejected' or 'request_sent' status");
			for(int p=0;p<interviewRequestDetailsList.size();p++) {
				String interviewerName = interviewRequestDetailsList.get(p).getInterviewerName();
				String interviewerId = interviewRequestDetailsList.get(p).getInterviewerId();
				if(actionDetails.containsKey(interviewerId)) {
					softAssert.assertEquals(interviewRequestDetailsList.get(p).getInterviewRequestAction(),actionDetails.get(interviewerId),
							"Interview request action status is incorrect for gatekeeper = '"+ interviewerName + " - " + interviewerId + "'");
				}else {
					softAssert.assertEquals(interviewRequestDetailsList.get(p).getInterviewRequestAction(),REQUEST_SENT,
							"Interview request action status is incorrect for gatekeeper = '"+ interviewerName + " - " + interviewerId + "'");
				}
			}
			test.log(Status.PASS,"Successfully verified that all gatekeepers of each batch have valid action status for the interview request");
			softAssert.assertAll();
		}else {
			test.log(Status.PASS, "Gatekeepers are not available for position = "+data.get(POSITION)+", seniority = "+data.get(SENIORITY)+" and location = "+data.get(LOCATION));
		}
		Reporter.log("Executed for position = "+data.get(APPLY_POSITION)+", seniority = "+data.get(APPLY_SENIORITY)+" and location = "+data.get(LOCATION),true);
	}

	
	/**
	 * This test is to validate Re-Run : If all the GK's (set of 5 or less) reject the interview request at any point of time then the system will schedule interview request with the same date or a new date
	 * @param data
	 * @throws Exception
	 * @author deepakkumar.hadiya
	 */
	@Test(priority = 6, dataProvider = "rerun_Scenario_Data_For_Interview_Requests", dataProviderClass = InterviewE2EDataProvider.class, groups = {
			ExeGroups.Regression, ExeGroups.NotAvailableInPreProd })
	public void verify_Interview_Date_For_All_GK_Reject_Interview_Request_At_Any_Given_Time(
			Map<Object, Object> data) throws Exception {
		
		int minutesToAddInCurrentTime = 2;
		String interviewDateWithTime = StaffingUtilities.getFutureDateAndTimeForMentionedUnit(DATABASE_DATE_FORMAT,Calendar.MINUTE, WAIT_TIME_FOR_RERUN_IN_MIN+minutesToAddInCurrentTime);
		data.put(INTERVIEW_DATE,StaffingUtilities.convertDateIntoMilliSecond(interviewDateWithTime, DATABASE_DATE_FORMAT)) ;
		
		SoftAssert softAssert = new SoftAssert();
		InterviewTestHelper interviewTestHelper=new InterviewTestHelper();
		String interviewId = interviewTestHelper.createInterviewRequest(data,softAssert);
		List<Integer> gkList =interviewTestHelper.getGatekeepersList(data); 
		int totalGKCount =gkList.size();
		
		InterviewDBHelper dbHelper = new InterviewDBHelper();
		Thread.sleep(WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS);
		String expectedInterviewDateInDB = StaffingUtilities.convertMilliSecondIntoDateWithTimeZone(data.get(INTERVIEW_DATE).toString(), DATABASE_DATE_FORMAT,"GMT+0");
		
		if(totalGKCount>TOTAL_GK_FOR_RE_RUN) {
			Integer totalRerunCount=(Integer) interviewTestHelper.rejectAllInterviewRequest(interviewId, gkList,false,false,minutesToAddInCurrentTime).get("totalRerunCount");
			List<InterviewRequestDTO> interviewRequestDetailsList = dbHelper.getInterviewRequestDetails(interviewId);
			int lastGkFromEachBatch=TOTAL_GK_FOR_RE_RUN-1;
			for(int i=0;i<totalRerunCount-1;i++) {
				String lastRespondedTime=interviewRequestDetailsList.get(lastGkFromEachBatch+1).getInterviewCreationDate();
				String lasteRespondedTimeWithAddedWaitTime=StaffingUtilities.getFutureDateAndTimeForMentionedUnit(DATABASE_DATE_FORMAT,Calendar.MINUTE, WAIT_TIME_FOR_RERUN_IN_MIN-1,lastRespondedTime);
				String expectedInterviewDateAfterUpdate=StaffingUtilities.getFutureDateAndTimeForMentionedUnit(DATABASE_DATE_FORMAT,Calendar.MINUTE, WAIT_TIME_FOR_RERUN_IN_MIN, expectedInterviewDateInDB);
				String actualInterviewDate=interviewRequestDetailsList.get(lastGkFromEachBatch+1).getInterviewDate();
				
				if(StaffingUtilities.firstDateIsAfterSecondDate(DATABASE_DATE_FORMAT,lasteRespondedTimeWithAddedWaitTime,expectedInterviewDateInDB)){
					test.log(Status.INFO, "Verifying interview date is updated with +"+WAIT_TIME_FOR_RERUN_IN_MIN+" minutes for gatekeepers of batch no "+ (i+1) );
					softAssert.assertEquals(actualInterviewDate, expectedInterviewDateAfterUpdate,"Interview date is not updated with +"+WAIT_TIME_FOR_RERUN_IN_MIN+" minutes for gatekeepers of batch no "+ (i+1));
					test.log(Status.PASS, "Successfully verified that interview date is updated with +"+WAIT_TIME_FOR_RERUN_IN_MIN+" minutes for gatekeepers of batch no "+ (i+1) );
					expectedInterviewDateInDB=actualInterviewDate;
				} else {
					test.log(Status.INFO, "Verifying interview date should not update with +"+WAIT_TIME_FOR_RERUN_IN_MIN+" minutes for gatekeepers of batch no "+ (i+1) );
					softAssert.assertEquals(actualInterviewDate, expectedInterviewDateInDB,"Interview date is updated with +"+WAIT_TIME_FOR_RERUN_IN_MIN+" minutes for gatekeepers of batch no "+ (i+1));
					test.log(Status.PASS, "Successfully verified that interview date is not updated with +"+WAIT_TIME_FOR_RERUN_IN_MIN+" minutes for gatekeepers of batch no "+ (i+1) );
				}
				lastGkFromEachBatch=lastGkFromEachBatch+TOTAL_GK_FOR_RE_RUN;
			} softAssert.assertAll();
		}else {
			test.log(Status.PASS, "More than '"+totalGKCount+"' gatekeepers are not available for position = "+data.get(POSITION)+", seniority = "+data.get(SENIORITY)+" and location = "+data.get(LOCATION));
		}
		Reporter.log("Executed for position = "+data.get(APPLY_POSITION)+", seniority = "+data.get(APPLY_SENIORITY)+" and location = "+data.get(LOCATION),true);
	}
	
	/**
	 * This test is to validate Re-Run : If all the GK's (set of 5 or less) reject the interview request instantly then the system will schedule interview request with the same date or a new date
	 * @param data
	 * @throws Exception
	 * @author deepakkumar.hadiya
	 */
	@Test(priority = 7, dataProvider = "rerun_Scenario_Data_For_Interview_Requests", dataProviderClass = InterviewE2EDataProvider.class, groups = {
			ExeGroups.Regression, ExeGroups.NotAvailableInPreProd })
	public void verify_Interview_Date_For_All_GK_Reject_Interview_Request_Instantly(
			Map<Object, Object> data) throws Exception {
		
		int secondsToAddInCurrentTime = 30;
		String interviewDateWithAddedMin = StaffingUtilities.getFutureDateAndTimeForMentionedUnit(DATABASE_DATE_FORMAT,Calendar.MINUTE, WAIT_TIME_FOR_RERUN_IN_MIN);
		interviewDateWithAddedMin = StaffingUtilities.getFutureDateAndTimeForMentionedUnit(DATABASE_DATE_FORMAT,Calendar.SECOND, secondsToAddInCurrentTime,interviewDateWithAddedMin);
		data.put(INTERVIEW_DATE,StaffingUtilities.convertDateIntoMilliSecond(interviewDateWithAddedMin, DATABASE_DATE_FORMAT)) ;
		
		SoftAssert softAssert = new SoftAssert();
		InterviewTestHelper interviewTestHelper=new InterviewTestHelper();
		String interviewId = interviewTestHelper.createInterviewRequest(data,softAssert);
		List<Integer> gkList =interviewTestHelper.getGatekeepersList(data); 
		int totalGKCount =gkList.size();
		
		
		InterviewDBHelper dbHelper = new InterviewDBHelper();
		Thread.sleep(WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS);
		
		String expectedInterviewDateInDB = StaffingUtilities.convertMilliSecondIntoDateWithTimeZone(data.get(INTERVIEW_DATE).toString(), DATABASE_DATE_FORMAT,"GMT+0");
		
		if(totalGKCount>TOTAL_GK_FOR_RE_RUN) {
			Integer totalRerunCount=(Integer) interviewTestHelper.rejectAllInterviewRequest(interviewId, gkList,false,false).get("totalRerunCount");
			List<InterviewRequestDTO> interviewRequestDetailsList = dbHelper.getInterviewRequestDetails(interviewId);
			int lastGkFromEachBatch=TOTAL_GK_FOR_RE_RUN-1;
			for(int i=0;i<totalRerunCount-1;i++) {
				String lastRespondedTime=interviewRequestDetailsList.get(lastGkFromEachBatch+1).getInterviewCreationDate();
				String lasteRespondedTimeWithAddedWaitTime=StaffingUtilities.getFutureDateAndTimeForMentionedUnit(DATABASE_DATE_FORMAT,Calendar.MINUTE, WAIT_TIME_FOR_RERUN_IN_MIN-1,lastRespondedTime);
				String expectedInterviewDateAfterUpdate=StaffingUtilities.getFutureDateAndTimeForMentionedUnit(DATABASE_DATE_FORMAT,Calendar.MINUTE, WAIT_TIME_FOR_RERUN_IN_MIN, expectedInterviewDateInDB);
				String actualInterviewDate=interviewRequestDetailsList.get(lastGkFromEachBatch+1).getInterviewDate();
				
				if(StaffingUtilities.firstDateIsAfterSecondDate(DATABASE_DATE_FORMAT,lasteRespondedTimeWithAddedWaitTime,expectedInterviewDateInDB)){
					test.log(Status.INFO, "Verifying interview date is updated with +"+WAIT_TIME_FOR_RERUN_IN_MIN+" minutes for gatekeepers of batch no : "+ (i+2) );
					softAssert.assertEquals(actualInterviewDate, expectedInterviewDateAfterUpdate,"Interview date is not updated with +"+WAIT_TIME_FOR_RERUN_IN_MIN+" minutes for gatekeepers of batch no : "+ (i+2));
					test.log(Status.PASS, "Successfully verified that interview date is updated with +"+WAIT_TIME_FOR_RERUN_IN_MIN+" minutes for gatekeepers of batch no : "+ (i+2) );
					expectedInterviewDateInDB=actualInterviewDate;
				} else {
					test.log(Status.INFO, "Verifying interview date should not update with +"+WAIT_TIME_FOR_RERUN_IN_MIN+" minutes for gatekeepers of batch no : "+ (i+2) );
					softAssert.assertEquals(actualInterviewDate, expectedInterviewDateInDB,"Interview date is updated with +"+WAIT_TIME_FOR_RERUN_IN_MIN+" minutes for gatekeepers of batch no : "+ (i+2));
					test.log(Status.PASS, "Successfully verified that interview date is not updated with +"+WAIT_TIME_FOR_RERUN_IN_MIN+" minutes for gatekeepers of batch no : "+ (i+2) );
				}
				lastGkFromEachBatch=lastGkFromEachBatch+TOTAL_GK_FOR_RE_RUN;
			}softAssert.assertAll();
		}else {
			test.log(Status.PASS, "Gatekeepers are not available for position = "+data.get(POSITION)+", seniority = "+data.get(SENIORITY)+" and location = "+data.get(LOCATION));
		}
		Reporter.log("Executed for position = "+data.get(APPLY_POSITION)+", seniority = "+data.get(APPLY_SENIORITY)+" and location = "+data.get(LOCATION),true);
	}

	/**
	 * This test is to validate Re-Run : If all the GK's (set of 5 or less) ignore the interview request then the system will schedule interview request with the same date or a new date
	 * @param data
	 * @throws Exception
	 * @author deepakkumar.hadiya
	 */
	@Test(priority = 8, dataProvider = "rerun_Scenario_Data_For_Interview_Requests", dataProviderClass = InterviewE2EDataProvider.class, groups = {
			ExeGroups.Regression,ExeGroups.NotAvailableInPreProd })
	public void verify_Interview_Date_For_All_GK_Ignore_Interview_Request(
			Map<Object, Object> data) throws Exception {
		
		int minutesToAddInCurrentTime = 5;
		String interviewDateWithTime = StaffingUtilities.getFutureDateAndTimeForMentionedUnit(DATABASE_DATE_FORMAT,Calendar.MINUTE, WAIT_TIME_FOR_RERUN_IN_MIN+minutesToAddInCurrentTime);
		data.put(INTERVIEW_DATE,StaffingUtilities.convertDateIntoMilliSecond(interviewDateWithTime, DATABASE_DATE_FORMAT)) ;
		
		SoftAssert softAssert = new SoftAssert();
		InterviewTestHelper interviewTestHelper=new InterviewTestHelper();
		String interviewId = interviewTestHelper.createInterviewRequest(data,softAssert);
		List<Integer> gkList =interviewTestHelper.getGatekeepersList(data); 
		int totalGKCount =gkList.size();
		
		
		InterviewDBHelper dbHelper = new InterviewDBHelper();
		Thread.sleep(WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS);
		
		String expectedInterviewDateInDB = StaffingUtilities.convertMilliSecondIntoDateWithTimeZone(data.get(INTERVIEW_DATE).toString(), DATABASE_DATE_FORMAT,"GMT+0");
		
		if(totalGKCount>TOTAL_GK_FOR_RE_RUN) {
			Integer totalRerunCount=(Integer) interviewTestHelper.ignoreAllInterviewRequest(interviewId, gkList, false, false, false).get("totalRerunCount");
			List<InterviewRequestDTO> interviewRequestDetailsList = dbHelper.getInterviewRequestDetails(interviewId);
			int lastGkFromEachBatch=TOTAL_GK_FOR_RE_RUN-1;
			for(int i=0;i<totalRerunCount-1;i++) {
				String lastRespondedTime=interviewRequestDetailsList.get(lastGkFromEachBatch+1).getInterviewCreationDate();
				String lasteRespondedTimeWithAddedWaitTime=StaffingUtilities.getFutureDateAndTimeForMentionedUnit(DATABASE_DATE_FORMAT,Calendar.MINUTE, WAIT_TIME_FOR_RERUN_IN_MIN-1,lastRespondedTime);
				String expectedInterviewDateAfterUpdate=StaffingUtilities.getFutureDateAndTimeForMentionedUnit(DATABASE_DATE_FORMAT,Calendar.MINUTE, WAIT_TIME_FOR_RERUN_IN_MIN, expectedInterviewDateInDB);
				String actualInterviewDate=interviewRequestDetailsList.get(lastGkFromEachBatch+1).getInterviewDate();
				
				if(StaffingUtilities.firstDateIsAfterSecondDate(DATABASE_DATE_FORMAT,lasteRespondedTimeWithAddedWaitTime,expectedInterviewDateInDB)){
					test.log(Status.INFO, "Verifying interview date is updated with +"+WAIT_TIME_FOR_RERUN_IN_MIN+" minutes for gatekeepers of batch no : "+ (i+2) );
					softAssert.assertEquals(actualInterviewDate, expectedInterviewDateAfterUpdate,"Interview date is not updated with +"+WAIT_TIME_FOR_RERUN_IN_MIN+" minutes for gatekeepers of batch no : "+ (i+2));
					test.log(Status.PASS, "Successfully verified that interview date is updated with +"+WAIT_TIME_FOR_RERUN_IN_MIN+" minutes for gatekeepers of batch no : "+ (i+2) );
					expectedInterviewDateInDB=actualInterviewDate;
				} else {
					test.log(Status.INFO, "Verifying interview date should not update with +"+WAIT_TIME_FOR_RERUN_IN_MIN+" minutes for gatekeepers of batch no : "+ (i+2) );
					softAssert.assertEquals(actualInterviewDate, expectedInterviewDateInDB,"Interview date is updated with +"+WAIT_TIME_FOR_RERUN_IN_MIN+" minutes for gatekeepers of batch no : "+ (i+2));
					test.log(Status.PASS, "Successfully verified that interview date is not updated with +"+WAIT_TIME_FOR_RERUN_IN_MIN+" minutes for gatekeepers of batch no : "+ (i+2) );
				}
				lastGkFromEachBatch=lastGkFromEachBatch+TOTAL_GK_FOR_RE_RUN;
			}softAssert.assertAll();
		}else {
			test.log(Status.PASS, "Gatekeepers are not available for position = "+data.get(POSITION)+", seniority = "+data.get(SENIORITY)+" and location = "+data.get(LOCATION));
		}
		Reporter.log("Executed for position = "+data.get(APPLY_POSITION)+", seniority = "+data.get(APPLY_SENIORITY)+" and location = "+data.get(LOCATION),true);
	}
	
	/**
	 * To validate Only new GK's are able to accept the interview invitation whenever interview date is changed in the re-run case
	 * Also verify last responded is updated for gatekeeper who accepts the interview request
	 * @param data
	 * @throws Exception
	 * @author deepakkumar.hadiya
	 */
	@Test(priority = 9, dataProvider = "rerun_Scenario_Data_For_Interview_Requests", dataProviderClass = InterviewE2EDataProvider.class, groups = {
			ExeGroups.Regression,ExeGroups.NotAvailableInPreProd })
	public void verify_Only_New_GK_Can_Accept_Interview_Request_When_InterviewDate_Is_Changed(
			Map<Object, Object> data) throws Exception {
		
		int minutesToAddInCurrentTime = 2;
		String interviewDateWithTime = StaffingUtilities.getFutureDateAndTimeForMentionedUnit(DATABASE_DATE_FORMAT,Calendar.MINUTE, WAIT_TIME_FOR_RERUN_IN_MIN+minutesToAddInCurrentTime);
		data.put(INTERVIEW_DATE,StaffingUtilities.convertDateIntoMilliSecond(interviewDateWithTime, DATABASE_DATE_FORMAT)) ;
		
		SoftAssert softAssert = new SoftAssert();
		InterviewTestHelper interviewTestHelper=new InterviewTestHelper();
		String interviewId = interviewTestHelper.createInterviewRequest(data,softAssert);
		List<Integer> gkList =interviewTestHelper.getGatekeepersList(data); 
		int totalGKCount =gkList.size();
		
		if(totalGKCount>0) {
		
		test.log(Status.INFO,"all gatekeepers of each batch ignore the interview request");
		interviewTestHelper.ignoreAllInterviewRequest(interviewId, gkList,false,false,false);
		List<String> gkAfterDateChange=new ArrayList<String>();
		List<String> gkBeforeDateChange=new ArrayList<String>();
		
		InterviewDBHelper dbHelper = new InterviewDBHelper();
		List<InterviewRequestDTO> interviewRequestDetailsList = dbHelper.getInterviewRequestDetails(interviewId);
			
		test.log(Status.INFO,"Verifying last responded is null for all gatekeepers");
			InterviewDTO interviewDetails = dbHelper.getInterviewDetails(interviewId);
			String latestInterviewDate = interviewDetails.getInterviewDate();
			for(InterviewRequestDTO interviewRequestDetail:interviewRequestDetailsList) {
				if(latestInterviewDate.equals(interviewRequestDetail.getInterviewDate())) {
					gkAfterDateChange.add(interviewRequestDetail.getInterviewerId());
				}else {
					gkBeforeDateChange.add(interviewRequestDetail.getInterviewerId());
				}
				softAssert.assertEquals(interviewRequestDetail.getLastResponded(), null,"Last responded value is wrong in database for gatekeeper = "+interviewRequestDetail.getInterviewerId());
			}
		test.log(Status.PASS, "Successfully verified last reponded is null before performing action on interview request = "+interviewId);

			int totalGKForDateChange=gkAfterDateChange.size();
			if(totalGKForDateChange>0) {
				AcceptOrRejectInterviewTestHelper acceptInterviewTestHelper = new AcceptOrRejectInterviewTestHelper();
				test.log(Status.INFO, "Verifying old gatekeepers are not able to accept the interview request having new interview date");
				for(String gkId:gkBeforeDateChange) {
					Response response = acceptInterviewTestHelper.acceptOrRejectInterview(interviewId, gkId, TOKEN, TOKEN_VALUE, true);
					validateResponseToContinueTest(response, 406, "Old gatekeeper = "+gkId+" is able to accept interview request = "+interviewId+" eventhough interview date is changed", true);
					softAssert.assertEquals(restUtils.getValueFromResponse(response, STATUS), "Not Acceptable","Response status is wrong for accepting interview request having new interview date by old gatekeepers");
					softAssert.assertTrue(restUtils.getValueFromResponse(response, MESSAGE).toString().contains("This interview request is not valid."),"Response message is wrong for accepting interview request having new interview date by old gatekeepers");
				}
				test.log(Status.PASS, "Successfully verified that old gatekeepers are not able to accept the interview request having new interview date");
				
				test.log(Status.INFO, "Verifying only new gatekeepers are able to accept the interview request having new interview date");
				int indexOfGkFromTheListOfChangedDate=Utilities.getRandomNumberBetween(0, totalGKForDateChange-1);
				String gkId=gkAfterDateChange.get(indexOfGkFromTheListOfChangedDate);
				String expectedRespondedTime = StaffingUtilities.getCurrentDateWithTimeZone(DATABASE_DATE_FORMAT,DATABASE_TIMEZONE);
				Response response = acceptInterviewTestHelper.acceptOrRejectInterview(interviewId, gkId, TOKEN, TOKEN_VALUE, true);
				validateResponseToContinueTest(response, 200, "Interviewer '"+gkId+"' is not able to accept interview request having new interview date for interview id = "+interviewId, true);
				test.log(Status.INFO, "Successfully verified that only new gatekeepers are not able to accept the interview request having new interview date");
				
				if(interviewRequestDetailsList.size()%3!=1) {
				test.log(Status.INFO, "Waiting till interview date to be passed for last batch after accepting interview request");
				interviewTestHelper.waitTillInterviewDateGetPassed(latestInterviewDate, DATABASE_DATE_FORMAT);
				Thread.sleep(WAIT_TIME_FREQUENCY_FOR_RERUN_IN_MILLI_SECONDS+(WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS*2));
				}
				Thread.sleep(WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS);
				interviewRequestDetailsList = dbHelper.getInterviewRequestDetails(interviewId);
				
				test.log(Status.INFO, "Verifying action of an interview request gets changed once interview date is passed");
				for(InterviewRequestDTO interviewRequestDetail:interviewRequestDetailsList) {
					String gatekeeper=interviewRequestDetail.getInterviewerId();
					if(gatekeeper.equals(gkId)) {
						softAssert.assertEquals(interviewRequestDetail.getInterviewRequestAction(), REQUEST_ACCEPTED,"Interview request action is wrong for accepting interview request by gatekeeper = "+gatekeeper);
					}else {
						softAssert.assertEquals(interviewRequestDetail.getInterviewRequestAction(), REQUEST_IGNORED,"Interview request action is not changed to "+REQUEST_IGNORED+" for interview date is passed of gatekeeper = "+gatekeeper);
					}
				}
				test.log(Status.PASS, "Successfully verifyied that action of an interview request gets changed when interview date is passed");
				
				test.log(Status.INFO, "Verifying last responded is updated after accepting interview request by gatekeeper = "+gkId);
				for(InterviewRequestDTO interviewRequestDetail:interviewRequestDetailsList) {
					if(interviewRequestDetail.getInterviewerId().equalsIgnoreCase(gkId)) {
						String actualRespondedTime = interviewRequestDetail.getLastResponded();
						boolean isLastRepondUpdatedAfterMin = StaffingUtilities.firstDateIsAfterSecondDate(DATABASE_DATE_FORMAT, actualRespondedTime, expectedRespondedTime);
						softAssert.assertTrue(isLastRepondUpdatedAfterMin ,"Last responded is not updated after accepting interview request by gatekeeper = "+gkId);
					}else {
						softAssert.assertEquals(interviewRequestDetail.getLastResponded(), null,"Last responded value is wrong in database for gatekeeper = "+interviewRequestDetail.getInterviewerId());
					}
				}
				test.log(Status.PASS, "Successfully verified last responded is updated after accepting interview request by gatekeeper = "+gkId);
				softAssert.assertAll();
			}else {
				test.log(Status.FAIL, "Date is not changed during all re-reun");	
			}
			softAssert.assertAll();
		}else {
			test.log(Status.PASS, "Gatekeepers are not available for position = "+data.get(POSITION)+", seniority = "+data.get(SENIORITY)+" and location = "+data.get(LOCATION));
		}
		Reporter.log("Executed for position = "+data.get(APPLY_POSITION)+", seniority = "+data.get(APPLY_SENIORITY)+" and location = "+data.get(LOCATION),true);
	}

	/**
	 * To validate old and new GK's are able to accept the interview invitation whenever interview date is not changed in the re-run case
	 * Also verify last responded is updated for gatekeeper who accepts the interview request
	 * @param data
	 * @throws Exception
	 * @author deepakkumar.hadiya
	 */
	@Test(priority = 10, dataProvider = "rerun_Scenario_Data_For_Interview_Requests", dataProviderClass = InterviewE2EDataProvider.class, groups = {
			ExeGroups.Regression, ExeGroups.NotAvailableInPreProd })
	public void verify_All_GK_Can_Accept_Interview_Request_When_InterviewDate_Is_Not_Changed(
			Map<Object, Object> data) throws Exception {
		
		SoftAssert softAssert = new SoftAssert();
		InterviewTestHelper interviewTestHelper=new InterviewTestHelper();
		interviewTestHelper.updateInterviewDateIfItIsOnHoliday(data);
		String interviewId = interviewTestHelper.createInterviewRequest(data,softAssert);
		List<Integer> gkList =interviewTestHelper.getGatekeepersList(data); 
		int totalGKCount =gkList.size();
		
		if(totalGKCount>0) {
		
		test.log(Status.INFO,"all gatekeepers of each batch ignore the interview request");
		interviewTestHelper.ignoreAllInterviewRequest(interviewId, gkList,false,false,false);
		
		InterviewDBHelper dbHelper = new InterviewDBHelper();
		List<InterviewRequestDTO> interviewRequestDetailsList = dbHelper.getInterviewRequestDetails(interviewId);
			
		test.log(Status.INFO,"Verifying last responded is null for all gatekeepers");
			for(InterviewRequestDTO interviewRequestDetail:interviewRequestDetailsList) {
				softAssert.assertEquals(interviewRequestDetail.getLastResponded(), null,"Last responded value is wrong in database for gatekeeper = "+interviewRequestDetail.getInterviewerId());
			}
		test.log(Status.PASS, "Successfully verified last reponded is null before performing action on interview request = "+interviewId);
		
			int totalGK=interviewRequestDetailsList.size();
			if(totalGK>0) {
				test.log(Status.INFO, "Verifying all gatekeepers are able to accept the interview request having old interview date");
				AcceptOrRejectInterviewTestHelper acceptInterviewTestHelper = new AcceptOrRejectInterviewTestHelper();
				int indexOfGkFromTheListOfChangedDate=Utilities.getRandomNumberBetween(0, totalGK-1);
				String gkId=interviewRequestDetailsList.get(indexOfGkFromTheListOfChangedDate).getInterviewerId();
				String expectedRespondedTime = StaffingUtilities.getCurrentDateWithTimeZone(DATABASE_DATE_FORMAT,DATABASE_TIMEZONE);
				Response response = acceptInterviewTestHelper.acceptOrRejectInterview(interviewId, gkId, TOKEN, TOKEN_VALUE, true);
				validateResponseToContinueTest(response, 200, "Interviewer '"+gkId+"' is not able to accept interview request having new interview date for interview id = "+interviewId, true);
				test.log(Status.PASS, "Successfully verified that all gatekeepers are able to accept the interview request having old interview date");
			
				test.log(Status.INFO, "Verifying last responded is updated after accepting interview request by gatekeeper = "+gkId);
				Thread.sleep(WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS);
				interviewRequestDetailsList = dbHelper.getInterviewRequestDetails(interviewId);
				for(InterviewRequestDTO interviewRequestDetail:interviewRequestDetailsList) {
					if(interviewRequestDetail.getInterviewerId().equalsIgnoreCase(gkId)) {
						String actualRespondedTime = interviewRequestDetail.getLastResponded();
						boolean isLastRepondUpdatedAfterMin = StaffingUtilities.firstDateIsAfterSecondDate(DATABASE_DATE_FORMAT, actualRespondedTime, expectedRespondedTime);
						softAssert.assertTrue(isLastRepondUpdatedAfterMin ,"Last responded is not updated after accepting interview request by gatekeeper = "+gkId);
					}else {
						softAssert.assertEquals(interviewRequestDetail.getLastResponded(), null,"Last responded value is wrong in database for gatekeeper = "+interviewRequestDetail.getInterviewerId());
					}
				}
				softAssert.assertAll();
				test.log(Status.PASS, "Successfully verified last responded is updated after accepting interview request by gatekeeper = "+gkId);
			}else {
				test.log(Status.FAIL, "Date is not changed during all re-reun");	
			}
		}else {
			test.log(Status.PASS, "Gatekeepers are not available for position = "+data.get(POSITION)+", seniority = "+data.get(SENIORITY)+" and location = "+data.get(LOCATION));
		}
		Reporter.log("Executed for position = "+data.get(APPLY_POSITION)+", seniority = "+data.get(APPLY_SENIORITY)+" and location = "+data.get(LOCATION),true);
	}
	
	/**
	 * To validate historical data for the accepted/rejected/ignored interview request
	 * @param data
	 * @throws Exception
	 * @author deepakkumar.hadiya
	 */
	@Test(priority = 11, dataProvider = "rerun_Scenario_Data_For_Interview_Requests", dataProviderClass = InterviewE2EDataProvider.class, groups = {
			ExeGroups.Regression,ExeGroups.NotAvailableInPreProd })
	public void verify_Historical_Data_For_Reject_And_Accept_Interview_Request(
			Map<Object, Object> data) throws Exception {
		
		SoftAssert softAssert = new SoftAssert();
		InterviewTestHelper interviewTestHelper=new InterviewTestHelper();
		interviewTestHelper.updateInterviewDateIfItIsOnHoliday(data);
		String interviewId = interviewTestHelper.createInterviewRequest(data,softAssert);
		
		Thread.sleep(WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS);
		InterviewDBHelper dbHelper = new InterviewDBHelper();
		List<InterviewRequestDTO> interviewRequestDetailsList = dbHelper.getInterviewRequestDetails(interviewId);
		if(interviewRequestDetailsList.size()>2) {
			test.log(Status.INFO, "Verifying last reponded is null before performing action on interview request = "+interviewId);
			for(InterviewRequestDTO interviewRequestDetails:interviewRequestDetailsList) {
			softAssert.assertEquals(interviewRequestDetails.getLastResponded(), null,"Last responded value is wrong in database for gatekeeper = "+interviewRequestDetails.getInterviewerId());
			}
			test.log(Status.PASS, "Successfully verified last reponded is null before performing action on interview request = "+interviewId);
			
			String expectedRespondedDateMin = StaffingUtilities.getCurrentDateWithTimeZone(DATABASE_DATE_FORMAT,DATABASE_TIMEZONE);
			test.log(Status.INFO, "One gatekeeper reject the interview request and verifying last reponded is updated in database");
			String gatekeeper=interviewRequestDetailsList.get(0).getInterviewerId();
			String interviewerName=interviewRequestDetailsList.get(0).getInterviewerName();
			interviewTestHelper.interviewRequestAction(interviewId, gatekeeper, interviewerName, REJECT);
			Thread.sleep(WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS);
			interviewRequestDetailsList = dbHelper.getInterviewRequestDetails(interviewId);
			String actualRespondedTime=interviewRequestDetailsList.get(0).getLastResponded();
			boolean isLastRepondUpdatedAfterMin=StaffingUtilities.firstDateIsAfterSecondDate(DATABASE_DATE_FORMAT, actualRespondedTime, expectedRespondedDateMin);
			softAssert.assertTrue(isLastRepondUpdatedAfterMin ,"Last responded is not updated after rejecting interview request by gatekeeper = "+gatekeeper);
			softAssert.assertEquals(interviewRequestDetailsList.get(1).getLastResponded(), null,"Last responded is updated for gatekeeper = "+interviewRequestDetailsList.get(1).getInterviewerName()+" who has not performed any action on interview request");
			softAssert.assertEquals(interviewRequestDetailsList.get(2).getLastResponded(), null,"Last responded is updated for gatekeeper = "+interviewRequestDetailsList.get(2).getInterviewerName()+" who has not performed any action on interview request");
			test.log(Status.PASS, "Successfully verified last responded after rejecting interview request");
			
			test.log(Status.INFO, "Again same gatekeeper reject the interview request and verifying last responded time is not changed");
			AcceptOrRejectInterviewTestHelper testHelper=new AcceptOrRejectInterviewTestHelper();
			testHelper.acceptOrRejectInterview(interviewId, gatekeeper, TOKEN, TOKEN_VALUE, false);
			Thread.sleep(WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS);
			interviewRequestDetailsList = dbHelper.getInterviewRequestDetails(interviewId);
			String actualRespondedTimeAfterAgainRejectingRequest=interviewRequestDetailsList.get(0).getLastResponded();
			softAssert.assertEquals(actualRespondedTimeAfterAgainRejectingRequest, actualRespondedTime,"Responded time is changed for re-rejecting interview request");
			test.log(Status.PASS, "Successfully verified that last responded time is not changed if gatekeeper rejects interview request second time");
			
			expectedRespondedDateMin = StaffingUtilities.getCurrentDateWithTimeZone(DATABASE_DATE_FORMAT,DATABASE_TIMEZONE);
			test.log(Status.INFO, "One gatekeeper accepts the interview request and verifying last reponded is updated in database");
			gatekeeper=interviewRequestDetailsList.get(1).getInterviewerId();
			interviewerName=interviewRequestDetailsList.get(1).getInterviewerName();
			interviewTestHelper.interviewRequestAction(interviewId, gatekeeper, interviewerName, ACCEPT);
			Thread.sleep(WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS);
			interviewRequestDetailsList = dbHelper.getInterviewRequestDetails(interviewId);
			actualRespondedTime=interviewRequestDetailsList.get(1).getLastResponded();
			isLastRepondUpdatedAfterMin=StaffingUtilities.firstDateIsAfterSecondDate(DATABASE_DATE_FORMAT, actualRespondedTime, expectedRespondedDateMin);
			softAssert.assertTrue(isLastRepondUpdatedAfterMin ,"Last responded is not updated after accepting interview request by gatekeeper = "+gatekeeper);
			softAssert.assertEquals(interviewRequestDetailsList.get(2).getLastResponded(), null,"Last responded is updated for gatekeeper = "+interviewRequestDetailsList.get(2).getInterviewerName()+" who has not performed any action on interview request");
			test.log(Status.PASS, "Successfully verified last responded after accepting interview request");
			
			test.log(Status.INFO, "Again same gatekeeper accepts the interview request and verifying last responded time is not changed");
			testHelper.acceptOrRejectInterview(interviewId, gatekeeper, TOKEN, TOKEN_VALUE, true);
			Thread.sleep(WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS);
			interviewRequestDetailsList = dbHelper.getInterviewRequestDetails(interviewId);
			String actualRespondedTimeAfterAgainAcceptingRequest = interviewRequestDetailsList.get(1).getLastResponded();
			softAssert.assertEquals(actualRespondedTimeAfterAgainAcceptingRequest, actualRespondedTime,"Responded time is changed for re-accepting interview request");
			softAssert.assertAll();
			test.log(Status.PASS, "Successfully verified that last responded time is not changed if gatekeeper accepts interview request second time");
			}else {
				Assert.fail("Gatekeeper count is less than 3, Actual gk count : "+interviewRequestDetailsList.size());
		}
	}
	
	/**
	 * This test is to validate If gatekeeper has applied for leave and recruiter creates interview with same date, then gatekeeper will not get interview request 
	 * @param data
	 * @throws Exception
	 * @author deepakkumar.hadiya
	 */
	@SuppressWarnings({ "unchecked" })
	@Test(enabled = false, priority = 12, dataProvider = "valid_TestData_For_Interview_Requests_E2E_Scenario", dataProviderClass = InterviewE2EDataProvider.class, groups = {
			ExeGroups.Sanity,ExeGroups.Regression })
	public void verify_Interview_Request_For_One_Absent_Gatekeeper(
			Map<Object, Object> data) throws Exception {
		
		InterviewTestHelper interviewTestHelper=new InterviewTestHelper();
		List<Integer> gkList =interviewTestHelper.getGatekeepersList(data); 
		int totalGKCount =gkList.size();

		if(totalGKCount>0) {
		
		String interviewDateWithTime = StaffingUtilities.getFutureDateAndTimeForMentionedUnit(DATABASE_DATE_FORMAT,Calendar.DATE, Utilities.getRandomNumberBetween(5, 50));
		data.put(INTERVIEW_DATE,StaffingUtilities.convertDateIntoMilliSecond(interviewDateWithTime, DATABASE_DATE_FORMAT));
		
		String leaveDate=interviewTestHelper.updateInterviewDateIfItIsOnHoliday(data).split(" ")[0];
		gkList =interviewTestHelper.getGatekeepersList(data); 
		totalGKCount =gkList.size();
		
		SoftAssert softAssert = new SoftAssert();

		Map<Integer, String> gatekeepersWithLeaveDetails=new HashMap<>();
		Integer gatekeeper=gkList.get(Utilities.getRandomNumberBetween(0, totalGKCount-1));
		gatekeepersWithLeaveDetails.put(gatekeeper, leaveDate+":"+leaveDate);
		
		interviewTestHelper.applyForLeave(gatekeepersWithLeaveDetails);
		
		String interviewId = interviewTestHelper.createInterviewRequest(data,softAssert);
		Thread.sleep(WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS);
			List<Integer> gatekeepers=interviewTestHelper.rejectAllInterviewRequest(interviewId, gkList);
			interviewTestHelper.cancelLeave(gatekeepersWithLeaveDetails);
			Assert.assertTrue(!gatekeepers.contains(gatekeeper),"Gatekeeper ["+gatekeeper+"] has got an interview request eventhough he is on leave. List of Gks who got interview requests : "+gatekeepers.toString());
			test.log(Status.PASS, "Successfully verified that gatekeeper has not got interview request for applied leave date");
			
			test.log(Status.INFO, "Verifying 'No GK found' email is send to recruiter after last rerun");
			InterviewDBHelper dbHelper=new InterviewDBHelper();
			GatekeepersSearchResultDTO gkSearchResult=dbHelper.getGatekeepersSearchResult(interviewId);
			softAssert.assertEquals(gkSearchResult.getEmailSentToRecruiter(), TrueInDBFormat,"'No GK found' email is not sent to recruiter after last rerun");
			softAssert.assertEquals(gkSearchResult.getOccouredWhile(), ALL_REJECT,"'No GK found' email is not sent to recruiter after last rerun");
			softAssert.assertAll();
			test.log(Status.PASS, "Successfully verified that 'No GK found' email is send to recruiter after last rerun");
		}else {
			test.log(Status.PASS, "Gatekeepers are not available for position = "+data.get(POSITION)+", seniority = "+data.get(SENIORITY)+" and location = "+data.get(LOCATION));
		}
		
		Reporter.log("Executed for position = "+data.get(APPLY_POSITION)+", seniority = "+data.get(APPLY_SENIORITY)+" and location = "+data.get(LOCATION),true);	
	}

	/**
	 * This test is to validate If all gatekeepers have applied for leave and recruiter creates interview with same date, then all gatekeepers will not get interview request
	 * @param data
	 * @throws Exception
	 * @author deepakkumar.hadiya
	 */
	@SuppressWarnings({ "unchecked" })
	@Test(enabled = false, priority = 13, dataProvider = "valid_TestData_For_Interview_Requests_E2E_Scenario", dataProviderClass = InterviewE2EDataProvider.class, groups = {
			ExeGroups.Regression })
	public void verify_Interview_Request_For_All_Absent_Gatekeepers(
			Map<Object, Object> data) throws Exception {
		
		InterviewTestHelper interviewTestHelper=new InterviewTestHelper();
		List<Integer> gkList =interviewTestHelper.getGatekeepersList(data); 
		int totalGKCount =gkList.size();
	
		if(totalGKCount>0) {
		String interviewDateWithTime = StaffingUtilities.getFutureDateAndTimeForMentionedUnit(DATABASE_DATE_FORMAT,Calendar.DATE, Utilities.getRandomNumberBetween(5, 50));
		data.put(INTERVIEW_DATE,StaffingUtilities.convertDateIntoMilliSecond(interviewDateWithTime, DATABASE_DATE_FORMAT)) ;
		
		String leaveDate=interviewTestHelper.updateInterviewDateIfItIsOnHoliday(data).split(" ")[0];
		gkList =interviewTestHelper.getGatekeepersList(data); 
		totalGKCount =gkList.size();
		
		SoftAssert softAssert = new SoftAssert();
	
		Map<Integer, String> gatekeepersWithLeaveDetails=new HashMap<>();
		for(Integer gatekeeper:gkList) {
			gatekeepersWithLeaveDetails.put(gatekeeper, leaveDate+":"+leaveDate);
		}	
		interviewTestHelper.applyForLeave(gatekeepersWithLeaveDetails);
		
		String interviewId = interviewTestHelper.createInterviewRequest(data,softAssert);
		Thread.sleep(WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS);
		List<Integer> gatekeepers=new ArrayList<Integer>();
			try {
				gatekeepers=interviewTestHelper.rejectAllInterviewRequest(interviewId, gkList);
			}catch (Exception e) {
			}
			interviewTestHelper.cancelLeave(gatekeepersWithLeaveDetails);
			for(Integer gatekeeper:gatekeepersWithLeaveDetails.keySet()) {
				softAssert.assertTrue(!gatekeepers.contains(gatekeeper),"Gatekeeper ["+gatekeeper+"] has got an interview request eventhough he is on leave. List of Gks who got interview requests : "+gatekeepers.toString());
			}
			test.log(Status.PASS, "Successfully verified that gatekeeper has not got interview request for applied leave date");
			
			test.log(Status.INFO, "Verifying 'No GK found' email is send to recruiter after last rerun");
			InterviewDBHelper dbHelper=new InterviewDBHelper();
			GatekeepersSearchResultDTO gkSearchResult=dbHelper.getGatekeepersSearchResult(interviewId);
			softAssert.assertEquals(gkSearchResult.getEmailSentToRecruiter(), TrueInDBFormat,"'No GK found' email is not sent to recruiter after last rerun");
			softAssert.assertEquals(gkSearchResult.getOccouredWhile(), FIRST_RUN,"'No GK found' email is not sent to recruiter after last rerun");
			softAssert.assertAll();
			test.log(Status.PASS, "Successfully verified that 'No GK found' email is send to recruiter after first run");
		}else {
			test.log(Status.PASS, "Gatekeepers are not available for position = "+data.get(POSITION)+", seniority = "+data.get(SENIORITY)+" and location = "+data.get(LOCATION));
		}
		
		Reporter.log("Executed for position = "+data.get(APPLY_POSITION)+", seniority = "+data.get(APPLY_SENIORITY)+" and location = "+data.get(LOCATION),true);	
	}

	/**
	 * This test is to validate If One gatekeeper has applied for one day leave, second Gatekeeper has applied for one week leave consisting 
	 * interview date and recruiter creates interview with same date, then these two gatekeepers will not get interview request
	 * @param data
	 * @throws Exception
	 * @author deepakkumar.hadiya
	 */
	@SuppressWarnings({ "unchecked" })
	@Test(enabled = false, priority = 14, dataProvider = "rerun_Scenario_Data_For_Interview_Requests", dataProviderClass = InterviewE2EDataProvider.class, groups = {
			ExeGroups.Regression })
	public void verify_Interview_Request_For_Two_Absent_Gatekeepers(
			Map<Object, Object> data) throws Exception {
		
		InterviewTestHelper interviewTestHelper=new InterviewTestHelper();
		List<Integer> gkList =interviewTestHelper.getGatekeepersList(data); 
		int totalGKCount =gkList.size();
	
		if(totalGKCount>2) {
		String interviewDateWithTime = StaffingUtilities.getFutureDateAndTimeForMentionedUnit(DATABASE_DATE_FORMAT,Calendar.DATE, Utilities.getRandomNumberBetween(5, 50));
		data.put(INTERVIEW_DATE,StaffingUtilities.convertDateIntoMilliSecond(interviewDateWithTime, DATABASE_DATE_FORMAT)) ;
		
		interviewDateWithTime =interviewTestHelper.updateInterviewDateIfItIsOnHoliday(data);
		gkList =interviewTestHelper.getGatekeepersList(data); 
		totalGKCount =gkList.size();
		
		String leaveDateForFirstGk=interviewDateWithTime.split(" ")[0];
		
		String leaveFromDateForSecondGk=StaffingUtilities.getFutureDateAndTimeForMentionedUnit(DATABASE_DATE_FORMAT,Calendar.DATE, -3,interviewDateWithTime).split(" ")[0];
		String leaveToDateForSecondGk=StaffingUtilities.getFutureDateAndTimeForMentionedUnit(DATABASE_DATE_FORMAT,Calendar.DATE, +4,interviewDateWithTime).split(" ")[0];
		
		SoftAssert softAssert = new SoftAssert();
	
		Map<Integer, String> gatekeepersWithLeaveDetails=new HashMap<>();
			Integer gatekeeper1=gkList.get(Utilities.getRandomNumberBetween(0, totalGKCount-1));
			gatekeepersWithLeaveDetails.put(gatekeeper1, leaveDateForFirstGk+":"+leaveDateForFirstGk);
			Integer gatekeeper2=gkList.get(Utilities.getRandomNumberBetween(0, totalGKCount-1));
			gatekeepersWithLeaveDetails.put(gatekeeper2, leaveFromDateForSecondGk+":"+leaveToDateForSecondGk);
			
		interviewTestHelper.applyForLeave(gatekeepersWithLeaveDetails);
		
		String interviewId = interviewTestHelper.createInterviewRequest(data,softAssert);
		Thread.sleep(WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS);
		List<Integer> gatekeepers=new ArrayList<Integer>();
			try {
				gatekeepers=interviewTestHelper.rejectAllInterviewRequest(interviewId, gkList);
			}catch (Exception e) {
			}
			interviewTestHelper.cancelLeave(gatekeepersWithLeaveDetails);
			for(Integer gatekeeper:gatekeepersWithLeaveDetails.keySet()) {
				softAssert.assertTrue(!gatekeepers.contains(gatekeeper),"Gatekeeper ["+gatekeeper+"] has got an interview request eventhough he is on leave. List of Gks who got interview requests : "+gatekeepers.toString());
			}
			test.log(Status.PASS, "Successfully verified that gatekeeper has not got interview request for applied leave date");
			
			test.log(Status.INFO, "Verifying 'No GK found' email is send to recruiter after last rerun");
			InterviewDBHelper dbHelper=new InterviewDBHelper();
			GatekeepersSearchResultDTO gkSearchResult=dbHelper.getGatekeepersSearchResult(interviewId);
			softAssert.assertEquals(gkSearchResult.getEmailSentToRecruiter(), TrueInDBFormat,"'No GK found' email is not sent to recruiter after last rerun");
			softAssert.assertEquals(gkSearchResult.getOccouredWhile(), ALL_REJECT,"'No GK found' email is not sent to recruiter after last rerun");
			softAssert.assertAll();
			test.log(Status.PASS, "Successfully verified that 'No GK found' email is send to recruiter after last run");
		}else {
			test.log(Status.PASS, "Gatekeepers are not available for position = "+data.get(POSITION)+", seniority = "+data.get(SENIORITY)+" and location = "+data.get(LOCATION));
		}
		
		Reporter.log("Executed for position = "+data.get(APPLY_POSITION)+", seniority = "+data.get(APPLY_SENIORITY)+" and location = "+data.get(LOCATION),true);	
	}
	
	/**
	 * This test is to validate If gatekeeper has applied for leave after recruiter creates interview with same date, then that gatekeeper will not get interview request during rerun
	 * Pre-condition - Here select the gatekeeper who has not got interview request in first run 
	 * @param data
	 * @throws Exception
	 * @author deepakkumar.hadiya
	 */
	@SuppressWarnings({ "unchecked" })
	@Test(enabled = false, priority = 15, dataProvider = "valid_TestData_For_Interview_Requests_E2E_Rerun_Scenario", dataProviderClass = InterviewE2EDataProvider.class, groups = {
			ExeGroups.Regression })
	public void verify_Interview_Requests_If_One_GK_Apply_Leave_After_Creating_interview(
			Map<Object, Object> data) throws Exception {
		
		InterviewTestHelper interviewTestHelper=new InterviewTestHelper();
		List<Integer> gkList =interviewTestHelper.getGatekeepersList(data); 
		int totalGKCount =gkList.size();
	
		if(totalGKCount>0) {
		String interviewDateWithTime = StaffingUtilities.getFutureDateAndTimeForMentionedUnit(DATABASE_DATE_FORMAT,Calendar.DATE, Utilities.getRandomNumberBetween(5, 50));
		data.put(INTERVIEW_DATE,StaffingUtilities.convertDateIntoMilliSecond(interviewDateWithTime, DATABASE_DATE_FORMAT)) ;
		
		interviewDateWithTime =interviewTestHelper.updateInterviewDateIfItIsOnHoliday(data);
		String leaveDate=interviewDateWithTime.split(" ")[0];
		gkList =interviewTestHelper.getGatekeepersList(data); 
		totalGKCount =gkList.size();
		
		
		SoftAssert softAssert = new SoftAssert();
	
		String interviewId = interviewTestHelper.createInterviewRequest(data,softAssert);
		Thread.sleep(WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS);
		
		InterviewDBHelper dbHelper=new InterviewDBHelper();
		List<InterviewRequestDTO> interviewDetails = dbHelper.getInterviewRequestDetails(interviewId);
		List<Integer> pendingGk=new ArrayList<>(gkList);
		for(InterviewRequestDTO interview:interviewDetails) {
			Integer interviewerId = Integer.valueOf(interview.getInterviewerId());
			if(gkList.contains(interviewerId)) {
				pendingGk.remove(interviewerId);
			}
		}
		Integer gatekeeper=pendingGk.get(pendingGk.size()-1);
		 
		Map<Integer, String> gatekeepersWithLeaveDetails=new HashMap<>();
		gatekeepersWithLeaveDetails.put(gatekeeper, leaveDate+":"+leaveDate);
		
		interviewTestHelper.applyForLeave(gatekeepersWithLeaveDetails);
		
			List<Integer> gatekeepers=interviewTestHelper.rejectAllInterviewRequest(interviewId, gkList);
			interviewTestHelper.cancelLeave(gatekeepersWithLeaveDetails);
			Assert.assertTrue(!gatekeepers.contains(gatekeeper),"Gatekeeper ["+gatekeeper+"] has got an interview request eventhough he is on leave. List of Gks who got interview requests : "+gatekeepers.toString());
			test.log(Status.PASS, "Successfully verified that gatekeeper has not got interview request for applied leave date");
			
			test.log(Status.INFO, "Verifying 'No GK found' email is send to recruiter after last rerun");
			GatekeepersSearchResultDTO gkSearchResult=dbHelper.getGatekeepersSearchResult(interviewId);
			softAssert.assertEquals(gkSearchResult.getEmailSentToRecruiter(), TrueInDBFormat,"'No GK found' email is not sent to recruiter after last rerun");
			softAssert.assertEquals(gkSearchResult.getOccouredWhile(), ALL_REJECT,"'No GK found' email is not sent to recruiter after last rerun");
			softAssert.assertAll();
			test.log(Status.PASS, "Successfully verified that 'No GK found' email is send to recruiter after last rerun");
		}else {
			test.log(Status.PASS, "Gatekeepers are not available for position = "+data.get(POSITION)+", seniority = "+data.get(SENIORITY)+" and location = "+data.get(LOCATION));
		}
		
		Reporter.log("Executed for position = "+data.get(APPLY_POSITION)+", seniority = "+data.get(APPLY_SENIORITY)+" and location = "+data.get(LOCATION),true);	
	}

	/**
	 * This test is to validate when all gatekeepers cancel the leave of one day as same as interview date from applied one week leave  
	 * and recruiter creates interview with cancelled leave date, then all gatekeepers should get interview request
	 * @param data
	 * @throws Exception
	 * @author deepakkumar.hadiya
	 */
	@SuppressWarnings({ "unchecked" })
	@Test(enabled=false, dataProvider = "rerun_Scenario_Data_For_Interview_Requests", dataProviderClass = InterviewE2EDataProvider.class, groups = {
			ExeGroups.Regression })
	public void verify_Interview_Request_For_All_Gatekeepers(Map<Object, Object> data) throws Exception {
		
		InterviewTestHelper interviewTestHelper=new InterviewTestHelper();
		List<Integer> gkList =interviewTestHelper.getGatekeepersList(data); 
		int totalGKCount =gkList.size();
	
		if(totalGKCount>2) {
		String interviewDateWithTime = StaffingUtilities.getFutureDateAndTimeForMentionedUnit(DATABASE_DATE_FORMAT,Calendar.DATE, Utilities.getRandomNumberBetween(5, 50));
		data.put(INTERVIEW_DATE,StaffingUtilities.convertDateIntoMilliSecond(interviewDateWithTime, DATABASE_DATE_FORMAT)) ;
		String interviewDate=interviewDateWithTime.split(" ")[0];
		String leaveFromDateForAllGk=StaffingUtilities.getFutureDateAndTimeForMentionedUnit(DATABASE_DATE_FORMAT,Calendar.DATE, -3,interviewDateWithTime).split(" ")[0];
		String leaveToDateForAllGk=StaffingUtilities.getFutureDateAndTimeForMentionedUnit(DATABASE_DATE_FORMAT,Calendar.DATE, +4,interviewDateWithTime).split(" ")[0];
		
		SoftAssert softAssert = new SoftAssert();
	
		Map<Integer, String> gatekeepersWithOneWeekLeaveDetails=new HashMap<>();
			for(Integer gatekeeper:gkList) {
			gatekeepersWithOneWeekLeaveDetails.put(gatekeeper, leaveFromDateForAllGk+":"+leaveToDateForAllGk);
			}
		
		Reporter.log("All gatekeepers are applying for one week leave",true);
		interviewTestHelper.applyForLeave(gatekeepersWithOneWeekLeaveDetails);
		
		Reporter.log("all gatekeepers cancel the leave of one day as same as interview date from applied leave",true);
		Map<Integer, String> gatekeepersWithOneDayCancelledLeave=new HashMap<>();
		for(Integer gatekeeper:gkList) {
			gatekeepersWithOneDayCancelledLeave.put(gatekeeper, interviewDate+":"+interviewDate);
		}
		interviewTestHelper.cancelLeave(gatekeepersWithOneDayCancelledLeave);
		
		Reporter.log("Creating an interview request to get all gatekeepers who get an interview request",true);
		String interviewId = interviewTestHelper.createInterviewRequest(data,softAssert);
		Thread.sleep(WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS);
		List<Integer> gatekeepers=new ArrayList<Integer>();
			try {
				gatekeepers=interviewTestHelper.rejectAllInterviewRequest(interviewId, gkList);
			}catch (Exception e) {
			}
			
			test.log(Status.PASS, "Verifying that all gatekeepers have got interview request for cancelled leave date");
			for(Integer gatekeeper:gatekeepersWithOneDayCancelledLeave.keySet()) {
				softAssert.assertTrue(gatekeepers.contains(gatekeeper),"Gatekeeper ["+gatekeeper+"] has not got an interview request eventhough he has cancelled leave. List of Gks who got interview requests : "+gatekeepers.toString());
			}
			test.log(Status.PASS, "Successfully verified that all gatekeeper has got interview request for cancelled leave date");
			
			test.log(Status.INFO, "Verifying 'No GK found' email is send to recruiter after last rerun");
			InterviewDBHelper dbHelper=new InterviewDBHelper();
			GatekeepersSearchResultDTO gkSearchResult=dbHelper.getGatekeepersSearchResult(interviewId);
			softAssert.assertEquals(gkSearchResult.getEmailSentToRecruiter(), TrueInDBFormat,"'No GK found' email is not sent to recruiter after last rerun");
			softAssert.assertEquals(gkSearchResult.getOccouredWhile(), ALL_REJECT,"'No GK found' email is not sent to recruiter after last rerun");
			test.log(Status.PASS, "Successfully verified that 'No GK found' email is send to recruiter after last run");
			
			Reporter.log("All gatekeepers are cancelling leave of one week",true);
			interviewTestHelper.cancelLeave(gatekeepersWithOneWeekLeaveDetails);
			
			softAssert.assertAll();
		}else {
			test.log(Status.PASS, "Gatekeepers are not available for position = "+data.get(POSITION)+", seniority = "+data.get(SENIORITY)+" and location = "+data.get(LOCATION));
		}
		
		Reporter.log("Executed for position = "+data.get(APPLY_POSITION)+", seniority = "+data.get(APPLY_SENIORITY)+" and location = "+data.get(LOCATION),true);	
	}
	
	/**
	 * This test is to validate that Interview date gets updated by -1 hour during day light saving
	 * @param data
	 * @throws Exception
	 * @author deepakkumar.hadiya
	 */
	@SuppressWarnings("rawtypes")
	@Test( dataProvider = "random_TestData_For_Day_Light_Saving", dataProviderClass = InterviewE2EDataProvider.class, groups = {
			ExeGroups.Regression })
	public void verify_Day_Light_Saving(Map<Object, Object> data) throws Exception {
	
		SoftAssert softAssert = new SoftAssert();
		InterviewTestHelper interviewTestHelper=new InterviewTestHelper();
		String interviewId = interviewTestHelper.createInterviewRequest(data,softAssert);
		
		InterviewDBHelper dbHelper = new InterviewDBHelper();
		List<InterviewRequestDTO> interviewRequestDetailsList = dbHelper.getInterviewRequestDetails(interviewId);
		Thread.sleep(WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS);
		String expectedInterviewDate = StaffingUtilities.convertMilliSecondIntoDateWithTimeZone(data.get(INTERVIEW_DATE).toString(), DATABASE_DATE_FORMAT,DATABASE_TIMEZONE);
		InterviewDTO interviewDetails = dbHelper.getInterviewDetails(interviewId);
		softAssert.assertEquals(interviewDetails.getInterviewDate(), expectedInterviewDate, "Interview date is wrong in data base after creating an interview request");
		
		InterviewRequestsOfGatekeeperTestHelper testHelper=new InterviewRequestsOfGatekeeperTestHelper();

		for(InterviewRequestDTO interviewDetail:interviewRequestDetailsList) {
			String interviewerId=interviewDetail.getInterviewerId();
			List<InterviewRequestDTO> interviewRequestsForGk = dbHelper.getAllInterviewRequestsForGatekeeper(interviewerId);

			for(InterviewRequestDTO interviewRequestForGk:interviewRequestsForGk) {
				if(interviewRequestForGk.getInterviewId().equals(interviewId)) {
					test.log(Status.INFO, "Verifying interview date is updated by -1 hour with timezone for interview id = "+interviewId+" gatekeeper id = "+interviewerId);
					Response response = testHelper.interviewRequestsForGk(TOKEN, TOKEN_VALUE, interviewerId);
					validateResponseToContinueTest(response, 200,"Interview requests are not fetched for gatekeeper = "+interviewerId , true);
					JSONArray apriRecords = (JSONArray) restUtils.getValueFromResponse(response, "$.details.interviews[?(@.interviewId=="+interviewId+")]");
					Map actualValue=null;
					try {
						actualValue=(Map)apriRecords.get(0);
					}catch(Exception e) {
						throw new Exception("Interview id = "+interviewId+" is not found in api response");
					}
					softAssert.assertEquals(actualValue.get(TIME_SLOT).toString(), interviewRequestForGk.getTimeSlot(),"Time slot is wrong in api response for interview id "+interviewId+" and Gatekeeper id = "+interviewerId);
					softAssert.assertEquals(actualValue.get(INTERVIEW_DATE_AS_PER_TIME_ZONE).toString(), interviewRequestForGk.getInterviewDateAsPerTimezone(),"Interview date is not as per time zone in api response for interview id "+interviewId+" and Gatekeeper id = "+interviewerId);
					test.log(Status.PASS, "Successfully verified that interview date is updated by -1 hour with timezone for interview id = "+interviewId+" gatekeeper id = "+interviewerId);
				}
			}
			
		}
		softAssert.assertAll();
		test.log(Status.PASS, "Interview date is updated by -1 hour with gatekeeper's timezone");
		
	}
}
