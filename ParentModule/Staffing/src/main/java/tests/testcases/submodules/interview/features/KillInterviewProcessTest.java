package tests.testcases.submodules.interview.features;

import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import database.submodules.interview.InterviewDBHelper;
import dataproviders.submodules.interview.features.KillInterviewProcessDataProvider;
import dto.submodules.interview.InterviewDTO;
import dto.submodules.interview.InterviewRequestDTO;
import io.restassured.response.Response;
import tests.testcases.submodules.interview.InterviewBaseTest;
import tests.testhelpers.submodules.interview.InterviewTestHelper;
import tests.testhelpers.submodules.interview.features.CreateInterviewTestHelper;
import tests.testhelpers.submodules.interview.features.KillInterviewProcessTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;
import utils.StaffingUtilities;

/**
 * 
 * @author deepakkumar.hadiya
 *
 */

public class KillInterviewProcessTest extends InterviewBaseTest{

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("Recruiter kills interview process");
	}

	/**
	 * This test is to verify recruiter is able to kill an interview process for not accepted interviews
	 * @param data
	 * @throws Exception
	 */
	@Test(dataProvider = "multiTestDataForPositiveScenario", dataProviderClass = KillInterviewProcessDataProvider.class, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void verifyKillInterviewProcessFunctionality(Map<Object, Object> data) throws Exception {

		SoftAssert softAssert = new SoftAssert();
		CreateInterviewTestHelper helper=new CreateInterviewTestHelper();
		String interviewId=helper.createInterviewRequest(data, softAssert);
		Thread.sleep(WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS);
		InterviewDBHelper dbHelper=new InterviewDBHelper();
		
		KillInterviewProcessTestHelper killProcessTestHelper=new KillInterviewProcessTestHelper();
		Response response = killProcessTestHelper.killInterviewProcess(data,interviewId, true, false);
		
		validateResponseToContinueTest(response, 200, "Interview process is not killed for interview id = "+interviewId, true);
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, MESSAGE), "Success","Response message is incorrect for killing interview process");
		
		String discardReason = data.get(DISCARD_REASON).toString();
		
		InterviewDTO interviewDetailsInDB = dbHelper.getInterviewDetails(interviewId);
		softAssert.assertEquals(interviewDetailsInDB.getDiscardReason(), discardReason ,"Discard reason = "+discardReason+" is not updated for the interview id = "+interviewId+" in technical_interviews table");
		softAssert.assertEquals(interviewDetailsInDB.getStatusFK(), "3040", "Interview status_fk is wrong in data base after killing interview process");
		
		test.log(Status.INFO, "Verifying interview status is '"+REQUEST_CANCELLED+"' for all gatekeepers for interview id = "+interviewId);
		List<InterviewRequestDTO> interviewRequestDetailsInDB = dbHelper.getInterviewRequestDetails(interviewId);
		for(InterviewRequestDTO interviewRequestDetail:interviewRequestDetailsInDB) {
			softAssert.assertEquals(interviewRequestDetail.getInterviewRequestAction(), REQUEST_CANCELLED ,"Interview request status is not changed to "+REQUEST_CANCELLED+" after killing interview process for gatekeeper = '"+interviewRequestDetail.getInterviewerName()+"' and interview id = "+interviewId);
		}
		softAssert.assertAll();
		test.log(Status.PASS, "Verifying interview status is '"+REQUEST_CANCELLED+"' for all gatekeepers for interview id = "+interviewId);
	}
	
	/**
	 * This test is to verify recruiter is able to kill an interview process if interview is rejected by one Gk
	 * @param data
	 * @throws Exception
	 */
	@Test(dataProvider = "multiTestDataForPositiveScenario", dataProviderClass = KillInterviewProcessDataProvider.class, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void verifyInterviewProcessGetskilledIfInterviewIsRejectedByonlyOneGk(Map<Object, Object> data) throws Exception {
		
		test.log(Status.INFO, "Creating a new interview request");
		SoftAssert softAssert = new SoftAssert();
		CreateInterviewTestHelper helper=new CreateInterviewTestHelper();
		String interviewId=helper.createInterviewRequest(data, softAssert);
		Thread.sleep(WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS);
		InterviewDBHelper dbHelper=new InterviewDBHelper();
		
		test.log(Status.INFO, "One GK is rejecting an interview request");
		
		List<InterviewRequestDTO> interviewRequestDetails = dbHelper.getInterviewRequestDetails(interviewId);
		InterviewRequestDTO randomInterviewerDetails = interviewRequestDetails.get(StaffingUtilities.getRandomNumberBetween(0, interviewRequestDetails.size()-1));
		String interviewerId=randomInterviewerDetails.getInterviewerId();
		String interviewerName=randomInterviewerDetails.getInterviewerName();
		InterviewTestHelper interviewTestHelper = new InterviewTestHelper();
		interviewTestHelper.interviewRequestAction(interviewId, interviewerId, interviewerName, REJECT);
		
		KillInterviewProcessTestHelper killProcessTestHelper=new KillInterviewProcessTestHelper();
		Response response = killProcessTestHelper.killInterviewProcess(data,interviewId, true, false);
		
		validateResponseToContinueTest(response, 200, "Interview process is not killed for interview id = "+interviewId, true);
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, MESSAGE), "Success","Response message is incorrect for killing interview process");
		
		String discardReason = data.get(DISCARD_REASON).toString();
		
		InterviewDTO interviewDetailsInDB = dbHelper.getInterviewDetails(interviewId);
		softAssert.assertEquals(interviewDetailsInDB.getDiscardReason(), discardReason ,"Discard reason = "+discardReason+" is not updated for the interview id = "+interviewId+" in technical_interviews table");
		softAssert.assertEquals(interviewDetailsInDB.getStatusFK(), "3040", "Interview status_fk is wrong in data base after killing interview process");
		
		test.log(Status.INFO, "Verifying interview status is '"+REQUEST_CANCELLED+"' for all gatekeepers who have not rejected interview request for interview id = "+interviewId);
		List<InterviewRequestDTO> interviewRequestDetailsInDB = dbHelper.getInterviewRequestDetails(interviewId);
		for(InterviewRequestDTO interviewRequestDetail:interviewRequestDetailsInDB) {
			if(interviewRequestDetail.getInterviewerId().equalsIgnoreCase(interviewerId)) {
				softAssert.assertEquals(interviewRequestDetail.getInterviewRequestAction(), REQUEST_REJECTED ,"Interview request status is changed to "+REQUEST_CANCELLED+" after killing interview process even if gatekeeper = '"+interviewRequestDetail.getInterviewerName()+"' has rejected interview = "+interviewId);
			}else {
				softAssert.assertEquals(interviewRequestDetail.getInterviewRequestAction(), REQUEST_CANCELLED ,"Interview request status is not changed to "+REQUEST_CANCELLED+" after killing interview process for gatekeeper = '"+interviewRequestDetail.getInterviewerName()+"' and interview id = "+interviewId);
			}
		}
		softAssert.assertAll();
		test.log(Status.PASS, "Verifying interview status is '"+REQUEST_CANCELLED+"' for all gatekeepers for interview id = "+interviewId);
	}
	
	/**
	 * This test is to verify interview process is not getting killed if interview is accepted by any GK
	 * @param data
	 * @throws Exception
	 */
	@Test(dataProvider = "multiTestDataForPositiveScenario", dataProviderClass = KillInterviewProcessDataProvider.class, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void verifyInterviewProcessDoesNotGetkilledIfInterviewIsAccepted(Map<Object, Object> data) throws Exception {
		
		test.log(Status.INFO, "Creating a new interview request");
		SoftAssert softAssert = new SoftAssert();
		CreateInterviewTestHelper helper=new CreateInterviewTestHelper();
		String interviewId=helper.createInterviewRequest(data, softAssert);
		Thread.sleep(WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS);
		InterviewDBHelper dbHelper=new InterviewDBHelper();
		
		test.log(Status.INFO, "One GK is accepting an interview request");
		
		List<InterviewRequestDTO> interviewRequestDetails = dbHelper.getInterviewRequestDetails(interviewId);
		InterviewRequestDTO randomInterviewerDetails = interviewRequestDetails.get(StaffingUtilities.getRandomNumberBetween(0, interviewRequestDetails.size()-1));
		String interviewerId=randomInterviewerDetails.getInterviewerId();
		String interviewerName=randomInterviewerDetails.getInterviewerName();
		InterviewTestHelper interviewTestHelper = new InterviewTestHelper();
		interviewTestHelper.interviewRequestAction(interviewId, interviewerId, interviewerName, ACCEPT);
		
		KillInterviewProcessTestHelper killProcessTestHelper=new KillInterviewProcessTestHelper();
		Response response = killProcessTestHelper.killInterviewProcess(data,interviewId, true, false);
		
		validateResponseToContinueTest(response, 400, "Interview process is killed for interview id = "+interviewId, true);
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, MESSAGE), "Request has already processed.","Response message is incorrect for killing interview process even if interview is accepted");
		
		InterviewDTO interviewDetailsInDB = dbHelper.getInterviewDetails(interviewId);
		softAssert.assertEquals(interviewDetailsInDB.getDiscardReason(), null ,"Discard reason is updated for the interview id = "+interviewId+" in technical_interviews table");
		softAssert.assertEquals(interviewDetailsInDB.getStatusFK(), "3020", "Interview status_fk is wrong in data base after killing interview process");
		
		test.log(Status.INFO, "Verifying interview status is '"+REQUEST_SENT+"' for all gatekeepers who have not accepted interview request for interview id = "+interviewId);
		List<InterviewRequestDTO> interviewRequestDetailsInDB = dbHelper.getInterviewRequestDetails(interviewId);
		for(InterviewRequestDTO interviewRequestDetail:interviewRequestDetailsInDB) {
			if(interviewRequestDetail.getInterviewerId().equalsIgnoreCase(interviewerId)) {
				softAssert.assertEquals(interviewRequestDetail.getInterviewRequestAction(), REQUEST_ACCEPTED ,"Interview request status is wrong after killing interview process even if gatekeeper = '"+interviewRequestDetail.getInterviewerName()+"' has rejected interview = "+interviewId);
			}else {
				softAssert.assertEquals(interviewRequestDetail.getInterviewRequestAction(), REQUEST_SENT ,"Interview request status is wrong after killing interview process for gatekeeper = '"+interviewRequestDetail.getInterviewerName()+"' and interview id = "+interviewId);
			}
		}
		softAssert.assertAll();
		test.log(Status.PASS, "Successfully verified that interview status remains '"+REQUEST_SENT+"' for all gatekeepers except for one GK who has accepted interview = "+interviewId);
	}
	
	/**
	 * To verify interview is not killed when parameter value of 'cancelInterview' is 'false' 
	 * @param data
	 * @throws Exception
	 */
	@Test(dataProvider = "singleTestData", dataProviderClass = KillInterviewProcessDataProvider.class, groups = {
			ExeGroups.Regression })
	public void verifyInterviewProcessDoesNotGetKilledWhenCancelInterviewParameterIsFalse(Map<Object, Object> data) throws Exception {

		SoftAssert softAssert = new SoftAssert();
		CreateInterviewTestHelper helper=new CreateInterviewTestHelper();
		String interviewId=helper.createInterviewRequest(data, softAssert);
		Thread.sleep(WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS);
		InterviewDBHelper dbHelper=new InterviewDBHelper();
		
		KillInterviewProcessTestHelper killProcessTestHelper=new KillInterviewProcessTestHelper();
		Response response = killProcessTestHelper.killInterviewProcess(data,interviewId, false, false);
		
		validateResponseToContinueTest(response, 200, "Interview process is killed for interview id = "+interviewId, true);
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, MESSAGE), "Success","Response message is incorrect for killing interview process");
		
		InterviewDTO interviewDetailsInDB = dbHelper.getInterviewDetails(interviewId);
		softAssert.assertEquals(interviewDetailsInDB.getDiscardReason(), null ,"Discard reason is updated for the interview id = "+interviewId+" in technical_interviews table");
		softAssert.assertEquals(interviewDetailsInDB.getStatusFK(), "3010", "Interview status_fk is wrong in data base after killing interview process");
		
		test.log(Status.INFO, "Verifying interview status is '"+REQUEST_SENT+"' for all gatekeepers for interview id = "+interviewId);
		List<InterviewRequestDTO> interviewRequestDetailsInDB = dbHelper.getInterviewRequestDetails(interviewId);
		for(InterviewRequestDTO interviewRequestDetail:interviewRequestDetailsInDB) {
			softAssert.assertEquals(interviewRequestDetail.getInterviewRequestAction(), REQUEST_SENT ,"Interview request status is changed to "+interviewRequestDetail.getInterviewRequestAction()+" for gatekeeper = '"+interviewRequestDetail.getInterviewerName()+"' and interview id = "+interviewId);
		}
		softAssert.assertAll();
		test.log(Status.PASS, "Successfully verified that interview status is '"+REQUEST_SENT+"' for all gatekeepers for interview id = "+interviewId);
	}
	
	/**
	 * This test is to verify recruiter is not able to kill an already killed interview process for not accepted interviews
	 * It also includes the verification of parameter - cancelInterview value = "True"
	 * @param data
	 * @throws Exception
	 */
	@Test(dataProvider = "testDataForValidParameterValue", dataProviderClass = KillInterviewProcessDataProvider.class, groups = {
			ExeGroups.Regression })
	public void verifykillInterviewProcessForAlreadyKilledInterviewProcess(Map<Object, Object> data) throws Exception {

		SoftAssert softAssert = new SoftAssert();
		CreateInterviewTestHelper helper=new CreateInterviewTestHelper();
		String interviewId=helper.createInterviewRequest(data, softAssert);
		Thread.sleep(WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS);
		InterviewDBHelper dbHelper=new InterviewDBHelper();
		
		KillInterviewProcessTestHelper killProcessTestHelper=new KillInterviewProcessTestHelper();
		Response response = killProcessTestHelper.killInterviewProcess(data,interviewId, data.get(CANCEL_INTERVIEW), false);
		
		validateResponseToContinueTest(response, 200, "Interview process is not killed for interview id = "+interviewId, true);
		softAssert.assertEquals(restUtils.getValueFromResponse(response, MESSAGE), "Success","Response message is incorrect for killing interview process");
		InterviewDTO interviewDetailsInDB = dbHelper.getInterviewDetails(interviewId);
		String discardReason = data.get(DISCARD_REASON).toString();
		softAssert.assertEquals(interviewDetailsInDB.getDiscardReason(), discardReason ,"Discard reason = "+discardReason+" is not updated for the interview id = "+interviewId+" in technical_interviews table");
		softAssert.assertEquals(interviewDetailsInDB.getStatusFK(), "3040", "Interview status_fk is wrong in data base after killing interview process");
		
		test.log(Status.INFO, "Killing the already killed interview = "+interviewId+" by hitting the same API request");
		response = killProcessTestHelper.killInterviewProcess(data,interviewId, data.get(CANCEL_INTERVIEW), false);
		validateResponseToContinueTest(response, 400, "Interview process is not killed for interview id = "+interviewId, true);
		softAssert.assertEquals(restUtils.getValueFromResponse(response, MESSAGE), "This interview has already been cancelled.","Response message is incorrect for killing already killed interview process");
		softAssert.assertAll();
		test.log(Status.PASS, "Api response for Killing already killed interview process is successfully verified for interviewId  = "+interviewId);

	}
	
	/**
	 * To verify interview kill process api response for blank discard reason
	 * @param data
	 * @throws Exception
	 */
	@Test(dataProvider = "singleTestData", dataProviderClass = KillInterviewProcessDataProvider.class, groups = {
			ExeGroups.Regression })
	public void verifyKillInterviewProcessResponseForBlankDiscardReason(Map<Object, Object> data) throws Exception {

		SoftAssert softAssert = new SoftAssert();
		CreateInterviewTestHelper helper=new CreateInterviewTestHelper();
		String interviewId=helper.createInterviewRequest(data, softAssert);
		Thread.sleep(WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS);
		
		KillInterviewProcessTestHelper killProcessTestHelper=new KillInterviewProcessTestHelper();
		Response response = killProcessTestHelper.killInterviewProcessWithBlankBody(data, interviewId, true, false);
		
		validateResponseToContinueTest(response, 400, "Incorrect response status for blank discard reason of interview id = "+interviewId, true);
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, MESSAGE), "Field 'discardReason cannot be null.","Response message is incorrect for blank discard reason");
		
		softAssert.assertAll();
		test.log(Status.PASS, "Successfully verified that response of interview kill process api is correct for blank discard reason of interviewId  = "+interviewId);

	}
	
	/**
	 * To verify interview kill process api response for invalid parameter value
	 * @param data
	 * @throws Exception
	 */
	@Test(dataProvider = "testDataForInValidParameterValue", dataProviderClass = KillInterviewProcessDataProvider.class, groups = {
			ExeGroups.Regression })
	public void verifyKillInterviewProcessResponseForInvalidParameterValue(Map<Object, Object> data) throws Exception {

		String parameterName=data.get("parameterKey").toString();
		
		SoftAssert softAssert = new SoftAssert();
		CreateInterviewTestHelper helper=new CreateInterviewTestHelper();
		String interviewId=helper.createInterviewRequest(data, softAssert);
		Thread.sleep(WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS);
		
		KillInterviewProcessTestHelper killProcessTestHelper=new KillInterviewProcessTestHelper();
		Response response = killProcessTestHelper.killInterviewProcessForInvalidParameterValue(data, interviewId);
		
		validateResponseToContinueTest(response, 400, "Incorrect response status for invalid parameter '"+parameterName+" = "+data.get(parameterName)+"' for interview id = "+interviewId, true);
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, MESSAGE), data.get(MESSAGE),"Response message is incorrect for invalid parameter '"+parameterName+" = "+data.get(parameterName)+"' for interview id = "+interviewId);
		
		softAssert.assertAll();
		test.log(Status.PASS, "Successfully verified that response of interview kill process api is correct for invalid parameter value of interviewId  = "+interviewId);

	}
	
	/**
	 * To verify interview kill process api response for invalid token value
	 * @param data
	 * @throws Exception
	 */
	@Test(dataProvider = "singleTestData", dataProviderClass = KillInterviewProcessDataProvider.class, groups = {
			ExeGroups.Regression })
	public void verifyKillInterviewProcessForInvalidTokenValue(Map<Object, Object> data) throws Exception {

		SoftAssert softAssert = new SoftAssert();
		CreateInterviewTestHelper helper=new CreateInterviewTestHelper();
		String interviewId=helper.createInterviewRequest(data, softAssert);
		Thread.sleep(WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS);
		
		String headerValue = data.get(HEADER_VALUE).toString();
		data.put(HEADER_VALUE, headerValue.substring(0, headerValue.length()-1));
		
		KillInterviewProcessTestHelper killProcessTestHelper=new KillInterviewProcessTestHelper();
		Response response = killProcessTestHelper.killInterviewProcessForInvalidParameterValue(data, interviewId);
		
		validateResponseToContinueTest(response, 403, "Incorrect response status for invalid header value for interview id = "+interviewId, true);
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, MESSAGE), "User is not having valid permission or role","Response message is incorrect for invalid header token value for interview id = "+interviewId);
		softAssert.assertAll();
		test.log(Status.PASS, "Successfully verified that response of interview kill process api is correct for invalid header token value of interviewId  = "+interviewId);

	}
	
	
	/**
	 * To verify interview kill process api response for invalid Header(token) value
	 * @param data
	 * @throws Exception
	 */
	@Test(dataProvider = "singleTestData", dataProviderClass = KillInterviewProcessDataProvider.class, groups = {
			ExeGroups.Regression })
	public void verifyKillInterviewProcessForInvalidUrl(Map<Object, Object> data) throws Exception {

		SoftAssert softAssert = new SoftAssert();
		CreateInterviewTestHelper helper=new CreateInterviewTestHelper();
		String interviewId=helper.createInterviewRequest(data, softAssert);
		Thread.sleep(WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS);
		
		KillInterviewProcessTestHelper killProcessTestHelper=new KillInterviewProcessTestHelper();
		Response response = killProcessTestHelper.killInterviewProcessForInvalidUrl(data, interviewId);
		
		validateResponseToContinueTest(response, 403, "Incorrect response status for invalid header value for interview id = "+interviewId, true);
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, MESSAGE), "User is not having valid permission or role","Response message is incorrect for invalid header token value for interview id = "+interviewId);
		softAssert.assertAll();
		test.log(Status.PASS, "Successfully verified that response of interview kill process api is correct for invalid header token value of interviewId  = "+interviewId);

	}
	
}
