package tests.testcases.submodules.interview.features;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import database.submodules.interview.InterviewDBHelper;
import dataproviders.submodules.interview.features.AcceptOrRejectInterviewDataProvider;
import dataproviders.submodules.interview.features.InterviewE2EDataProvider;
import dto.submodules.interview.InterviewDTO;
import dto.submodules.interview.InterviewRequestDTO;
import endpoints.submodules.interview.features.AcceptOrRejectInterviewEndpoints;
import io.restassured.response.Response;
import tests.testcases.submodules.interview.InterviewBaseTest;
import tests.testhelpers.submodules.interview.features.AcceptOrRejectInterviewTestHelper;
import tests.testhelpers.submodules.interview.features.CreateInterviewTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;
import utils.StaffingUtilities;
import utils.Utilities;

/**
 * 
 * @author deepakkumar.hadiya
 *
 */

public class RejectInterviewTest extends InterviewBaseTest{

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("Gatekeeper rejects an interview request");
	}

	/**
	 * This test is to verify gatekeeper is able to reject an interview request
	 * 
	 * @param data
	 * @throws Exception
	 */
	@Test(priority = 0, dataProvider = "valid_TestData_For_Interview_Requests_E2E_Scenario", dataProviderClass = InterviewE2EDataProvider.class, groups = {
			ExeGroups.Regression, ExeGroups.Sanity })
	public void rejectInterviewRequest_PositiveScenario(Map<Object, Object> data) throws Exception {

		SoftAssert softAssert = new SoftAssert();
		CreateInterviewTestHelper testHelper=new CreateInterviewTestHelper();
		String interviewId=testHelper.createInterviewRequest(data, softAssert);
		Thread.sleep(WAIT_TIME_FOR_DATABASE_TO_UPDATE_IN_MILLI_SECONDS);
		InterviewDBHelper helper=new InterviewDBHelper();
		
		List<InterviewRequestDTO> interviewRequests = helper.getInterviewRequestDetails(interviewId);
		String interviewerId=interviewRequests.get(StaffingUtilities.getRandomNumberBetween(0, interviewRequests.size()-1)).getInterviewerId();
		AcceptOrRejectInterviewTestHelper acceptInterviewTestHelper = new AcceptOrRejectInterviewTestHelper();
		
		String discardReason=RandomStringUtils.random(Utilities.getRandomNumberBetween(8,30),true, false);
		Response response = acceptInterviewTestHelper.acceptOrRejectInterview(interviewId, interviewerId, TOKEN,
				TOKEN_VALUE, false,discardReason);

		validateResponseToContinueTest(response, 200, "Interviewer '" + interviewerId
				+ "' is not able to reject interview request with valid test data for interview id = " + interviewId,
				true);

		softAssert.assertEquals(restUtils.getValueFromResponse(response, MESSAGE), "Request Successfully Processed",
				"Response message is incorrect for rejecting an interview request");

		
		InterviewDTO interviewDetailsInDB = helper.getInterviewDetails(interviewId);
		softAssert.assertEquals(interviewDetailsInDB.getInterviewerId(), null, "Interviewer id = " + interviewerId
				+ " is not null for the interview id = " + interviewId + " in technical_interviews table");
		InterviewRequestDTO interviewRequestDetailsInDB = helper.getInterviewRequestDetails(interviewId, interviewerId);
		softAssert.assertEquals(interviewRequestDetailsInDB.getInterviewRequestAction(), "request_rejected",
				"Interviewer action is not updated for Interviewer id = " + interviewerId + " and interview id = "
						+ interviewId + " in technical_interview_requests table");
		softAssert.assertEquals(interviewRequestDetailsInDB.getDiscardReason(), discardReason,
				"Interviewer discard_reason is not valid for Interviewer id = " + interviewerId + " and interview id = "
						+ interviewId + " in technical_interview_requests table");
		softAssert.assertAll();
		test.log(Status.PASS,
				"Interviewer '" + interviewerId + "' has successfully rejected interviewId  = " + interviewId+" with discard reason = "+discardReason);

	}

	/**
	 * This test is to verify reject interview request is returning appropriate
	 * response on entering wrong url
	 * 
	 * @param data
	 * @throws Exception
	 */
	@Test(priority = 1, dataProvider = "validTestdataForNegativeScenario", dataProviderClass = AcceptOrRejectInterviewDataProvider.class, groups = {
			ExeGroups.Regression })
	public void rejectInterviewRequest_With_InvalidUrl(Map<Object, Object> data) throws Exception {

		String interviewId = data.get(INTERVIEW_ID).toString();
		String interviewerId = data.get(INTERVIEWER_ID).toString();
		AcceptOrRejectInterviewTestHelper acceptInterviewTestHelper = new AcceptOrRejectInterviewTestHelper();
		Response response = acceptInterviewTestHelper.acceptOrRejectInterview_With_InvalidUrl(interviewId,
				interviewerId, TOKEN, TOKEN_VALUE, false);

		validateResponseToContinueTest(response, 403,
				"Reject interview request api is returning incorrect status code for invalid Url", true);

		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(restUtils.getValueFromResponse(response, ERROR), "Forbidden",
				"Response error is incorrect for rejecting interview request with invalid url");
		softAssert.assertEquals(restUtils.getValueFromResponse(response, MESSAGE),
				"User is not having valid permission or role",
				"Response message is incorrect for rejecting interview request with invalid url");
		softAssert.assertEquals(restUtils.getValueFromResponse(response, PATH),
				"/glow/interviewservice/technicalinterviewrequest/acceptace",
				"Path in response body is incorrect for rejecting interview request with invalid url");
		softAssert.assertAll();
		test.log(Status.PASS, "Response is successfully verified for accepting interview request with invalid Url");

	}

	/**
	 * This test is to verify reject interview request is returning appropriate
	 * response for invalid header key
	 * 
	 * @param data
	 * @throws Exception
	 */
	@Test(priority = 3, dataProvider = "validTestdataForNegativeScenario", dataProviderClass = AcceptOrRejectInterviewDataProvider.class, groups = {
			ExeGroups.Regression })
	public void rejectInterviewRequest_with_invalidHeaderKey(Map<Object, Object> data) throws Exception {

		String interviewId = data.get(INTERVIEW_ID).toString();
		String interviewerId = data.get(INTERVIEWER_ID).toString();
		AcceptOrRejectInterviewTestHelper acceptInterviewTestHelper = new AcceptOrRejectInterviewTestHelper();
		Response response = acceptInterviewTestHelper.acceptOrRejectInterview(interviewId, interviewerId, "toke",
				TOKEN_VALUE, true);

		validateResponseToContinueTest(response, 401,
				"Reject interview request api is returning incorrect status code for invalid header key", false);

		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(restUtils.getValueFromResponse(response, ERROR), "Unauthorized",
				"Response status is incorrect for rejecting interview request with invalid header key");
		softAssert.assertEquals(restUtils.getValueFromResponse(response, MESSAGE), "Token not found",
				"Response message is incorrect for rejecting interview request with invalid header key");
		softAssert.assertEquals(restUtils.getValueFromResponse(response, PATH),
				AcceptOrRejectInterviewEndpoints.acceptOrRejectInterview.split("\\?")[0],
				"Path in response body is incorrect for rejecting interview request with invalid header key");
		softAssert.assertAll();

		test.log(Status.PASS, "Reject interview request is returning appropriate response for invalid header key");
	}

	/**
	 * This test is to verify reject interview request is returning appropriate
	 * response for invalid header value
	 * 
	 * @param data
	 * @throws Exception
	 */
	@Test(priority = 4, dataProvider = "validTestdataForNegativeScenario", dataProviderClass = AcceptOrRejectInterviewDataProvider.class, groups = {
			ExeGroups.Regression })
	public void rejectInterviewRequest_with_invalidHeaderValue(Map<Object, Object> data) throws Exception {

		String interviewId = data.get(INTERVIEW_ID).toString();
		String interviewerId = data.get(INTERVIEWER_ID).toString();
		AcceptOrRejectInterviewTestHelper acceptInterviewTestHelper = new AcceptOrRejectInterviewTestHelper();
		Response response = acceptInterviewTestHelper.acceptOrRejectInterview(interviewId, interviewerId, TOKEN,
				"343434", true);

		validateResponseToContinueTest(response, 403,
				"Reject interview request api is returning incorrect status code for invalid header value", false);

		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(restUtils.getValueFromResponse(response, ERROR), "Forbidden",
				"Response status is incorrect for rejecting interview request with invalid header value");
		softAssert.assertEquals(restUtils.getValueFromResponse(response, MESSAGE),
				"User is not having valid permission or role",
				"Response message is incorrect for rejecting interview request with invalid header value");
		softAssert.assertEquals(restUtils.getValueFromResponse(response, PATH),
				AcceptOrRejectInterviewEndpoints.acceptOrRejectInterview.split("\\?")[0],
				"Path in response body is incorrect for rejecting interview request with invalid header value");
		softAssert.assertAll();

		test.log(Status.PASS, "Reject interview request is returning appropriate response for invalid header value");
	}

	/**
	 * This test is to verify reject interview request is returning appropriate
	 * response for invalid request method
	 * 
	 * @param data
	 * @throws Exception
	 */
	@Test(priority = 5, dataProvider = "validTestdataForNegativeScenario", dataProviderClass = AcceptOrRejectInterviewDataProvider.class, groups = {
			ExeGroups.Regression })
	public void rejectInterviewRequest_with_invalidRequestMethod(Map<Object, Object> data) throws Exception {

		String interviewId = data.get(INTERVIEW_ID).toString();
		String interviewerId = data.get(INTERVIEWER_ID).toString();
		AcceptOrRejectInterviewTestHelper acceptInterviewTestHelper = new AcceptOrRejectInterviewTestHelper();
		Response response = acceptInterviewTestHelper.acceptOrRejectInterview_With_InvalidRequestMethod(interviewId,
				interviewerId, TOKEN, TOKEN_VALUE, true);

		validateResponseToContinueTest(response, 403,
				"Reject interview request api is returning incorrect status code for invalid request method", false);

		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(restUtils.getValueFromResponse(response, ERROR), "Forbidden",
				"Response status is incorrect for rejecting interview request with invalid request method (GET)");
		softAssert.assertEquals(restUtils.getValueFromResponse(response, MESSAGE),
				"User is not having valid permission or role",
				"Response message is incorrect for rejecting interview request with invalid request method (GET)");
		softAssert.assertEquals(restUtils.getValueFromResponse(response, PATH),
				AcceptOrRejectInterviewEndpoints.acceptOrRejectInterview.split("\\?")[0],
				"Path in response body is incorrect for rejecting interview request with invalid request method (GET)");
		softAssert.assertAll();
		test.log(Status.PASS,
				"Reject interview request is returning appropriate response for invalid request method (GET)");
	}
}
