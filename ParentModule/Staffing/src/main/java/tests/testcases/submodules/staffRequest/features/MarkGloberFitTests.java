package tests.testcases.submodules.staffRequest.features;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import constants.submodules.staffRequest.StaffRequestConstants;
import dataproviders.submodules.staffRequest.features.ClientInterviewFeedbackDataProvider;
import io.restassured.response.Response;
import tests.testcases.submodules.staffRequest.StaffRequestBaseTest;
import tests.testhelpers.submodules.staffRequest.features.MarkGloberFitTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;
import utils.RestUtils;

/**
 * 
 * @author akshata.dongare
 *
 */
public class MarkGloberFitTests extends StaffRequestBaseTest implements StaffRequestConstants{
	RestUtils restUtils = new RestUtils();
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("Save feedback/Reject Glober through Fit Interview");
	}
	
	/**
	 * This method will verify mark glober fit and view fit interview feedback
	 * @param userName
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "clientInterviewFeedbackValidUser", dataProviderClass = ClientInterviewFeedbackDataProvider.class, priority = 0, groups = {ExeGroups.Regression,ExeGroups.Sanity})
	public void markGloberFitInterviewConductedYes(String userName, String userRole) throws Exception {
		String interviewConducted = "true";
		MarkGloberFitTestHelper  markGloberFitTestHelper = new MarkGloberFitTestHelper(userName);
		Response markGloberFitResponse = markGloberFitTestHelper.markFitUnfitGlober(userName, "FIT", "Glober", interviewConducted);
		
		if(markGloberFitResponse.getStatusCode()==400) {
			StaffRequestBaseTest.test.log(Status.SKIP, "Invalid staff plan id so can't mark glober as fit");
		}else {
			validateResponseToContinueTest(markGloberFitResponse, 201, "Mark fit glober call was not successful", true);
			assertEquals(restUtils.getValueFromResponse(markGloberFitResponse, "status"), "success", "Status is not success");
			
			String actualMsg = (String) restUtils.getValueFromResponse(markGloberFitResponse, "message");
			String expectedMsg = "Feedback for the Glober saved successfully.";
			assertEquals(actualMsg, expectedMsg, String.format("Actual message was %s and expected message is %s ", actualMsg, expectedMsg));
			
			String fitInterviewStatusJsonPath = "details.fitInterviewStatus";
			String actualFitInterviewStatus = restUtils.getValueFromResponse(markGloberFitResponse, fitInterviewStatusJsonPath).toString();
			assertEquals(actualFitInterviewStatus, "FIT", String.format("Actual fit Interview Status was %s and expected fit interview status is %s ", actualFitInterviewStatus, "FIT"));
			test.log(Status.PASS, "Mark glober as fit is successful ");
			
			String staffPlanIdJsonPath = "details.staffPlanId";
			String staffPlanId = restUtils.getValueFromResponse(markGloberFitResponse, staffPlanIdJsonPath).toString();
			
			Response viewFitInterviewFeedbackResponse = markGloberFitTestHelper.getViewFitInterviewFeedback(staffPlanId, "Glober");
			validateResponseToContinueTest(viewFitInterviewFeedbackResponse, 200, "View fit interview feedback call was not successful", true);
			assertEquals(restUtils.getValueFromResponse(viewFitInterviewFeedbackResponse, "status"), "success", "Status is not success");
			
			String fitInterviewReasonJsonPath = "details..fitInterviewReason";
			String expectedFitInterviewReason = restUtils.getValueFromResponse(markGloberFitResponse, fitInterviewReasonJsonPath).toString();
			String actualFitInterviewReason = restUtils.getValueFromResponse(viewFitInterviewFeedbackResponse, fitInterviewReasonJsonPath).toString();;
			assertEquals(actualFitInterviewReason, expectedFitInterviewReason, String.format("Actual fit Interview reason was %s and expected fit interview reason is %s ", actualFitInterviewReason, expectedFitInterviewReason));
			test.log(Status.PASS, "View Fit interview feedback is successful ");
		}
	}
	
	/**
	 * This method will verify mark glober fit and view fit interview feedback
	 * @param userName
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "clientInterviewFeedbackValidUser", dataProviderClass = ClientInterviewFeedbackDataProvider.class, priority = 0, groups = {ExeGroups.Regression,ExeGroups.Sanity})
	public void markGloberFitInterviewConductedNo(String userName, String userRole) throws Exception {
		String interviewConducted = "false";
		MarkGloberFitTestHelper  markGloberFitTestHelper = new MarkGloberFitTestHelper(userName);
		Response markGloberFitResponse = markGloberFitTestHelper.markFitUnfitGlober(userName, "FIT", "Glober", interviewConducted);
		
		if(markGloberFitResponse.getStatusCode()==400) {
			StaffRequestBaseTest.test.log(Status.SKIP, "Invalid staff plan id so can't mark glober as fit");
		}else {
			validateResponseToContinueTest(markGloberFitResponse, 201, "Mark fit glober call was not successful", true);
			assertEquals(restUtils.getValueFromResponse(markGloberFitResponse, "status"), "success", "Status is not success");
			
			String actualMsg = (String) restUtils.getValueFromResponse(markGloberFitResponse, "message");
			String expectedMsg = "Feedback for the Glober saved successfully.";
			assertEquals(actualMsg, expectedMsg, String.format("Actual message was %s and expected message is %s ", actualMsg, expectedMsg));
			
			String fitInterviewStatusJsonPath = "details.fitInterviewStatus";
			String actualFitInterviewStatus = restUtils.getValueFromResponse(markGloberFitResponse, fitInterviewStatusJsonPath).toString();
			assertEquals(actualFitInterviewStatus, "FIT", String.format("Actual fit Interview Status was %s and expected fit interview status is %s ", actualFitInterviewStatus, "FIT"));
			test.log(Status.PASS, "Mark glober as fit is successful ");
			
			String staffPlanIdJsonPath = "details.staffPlanId";
			String staffPlanId = restUtils.getValueFromResponse(markGloberFitResponse, staffPlanIdJsonPath).toString();
			
			Response viewFitInterviewFeedbackResponse = markGloberFitTestHelper.getViewFitInterviewFeedback(staffPlanId, "Glober");
			validateResponseToContinueTest(viewFitInterviewFeedbackResponse, 200, "View fit interview feedback call was not successful", true);
			assertEquals(restUtils.getValueFromResponse(viewFitInterviewFeedbackResponse, "status"), "success", "Status is not success");
			
			String fitInterviewReasonJsonPath = "details..fitInterviewReason";
			String expectedFitInterviewReason = restUtils.getValueFromResponse(markGloberFitResponse, fitInterviewReasonJsonPath).toString();
			String actualFitInterviewReason = restUtils.getValueFromResponse(viewFitInterviewFeedbackResponse, fitInterviewReasonJsonPath).toString();;
			assertEquals(actualFitInterviewReason, expectedFitInterviewReason, String.format("Actual fit Interview reason was %s and expected fit interview reason is %s ", actualFitInterviewReason, expectedFitInterviewReason));
			test.log(Status.PASS, "View Fit interview feedback is successful ");
		}
	}
	
	/**
	 * This method will verify mark fit interview for new hire
	 * @param userName 
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "clientInterviewFeedbackValidUser", dataProviderClass = ClientInterviewFeedbackDataProvider.class, enabled = true, priority = 0, groups = {ExeGroups.Regression,ExeGroups.Sanity})
	public void markSTGNewHireFitInterviewConductedYes(String userName, String userRole) throws Exception {
		String interviewConducted = "true";
		String globerType = "New Hire";
		MarkGloberFitTestHelper  markGloberFitTestHelper = new MarkGloberFitTestHelper(userName);
		Response markGloberFitResponse = markGloberFitTestHelper.markFitUnfitGlober(userName, "FIT", globerType, interviewConducted);
		
		if(markGloberFitResponse.getStatusCode()==400) {
			StaffRequestBaseTest.test.log(Status.SKIP, "Invalid staff plan id so can't mark glober as fit");
		}else {
			validateResponseToContinueTest(markGloberFitResponse, 201, "Mark fit glober call was not successful", true);
			assertEquals(restUtils.getValueFromResponse(markGloberFitResponse, "status"), "success", "Status is not success");
			
			String actualMsg = (String) restUtils.getValueFromResponse(markGloberFitResponse, "message");
			String expectedMsg = "Feedback for the Glober saved successfully.";
			assertEquals(actualMsg, expectedMsg, String.format("Actual message was %s and expected message is %s ", actualMsg, expectedMsg));
			
			String fitInterviewStatusJsonPath = "details.fitInterviewStatus";
			String actualFitInterviewStatus = restUtils.getValueFromResponse(markGloberFitResponse, fitInterviewStatusJsonPath).toString();
			assertEquals(actualFitInterviewStatus, "FIT", String.format("Actual fit Interview Status was %s and expected fit interview status is %s ", actualFitInterviewStatus, "FIT"));
			test.log(Status.PASS, "Mark New Hire as fit is successful ");
			
			String staffPlanIdJsonPath = "details.staffPlanId";
			String staffPlanId = restUtils.getValueFromResponse(markGloberFitResponse, staffPlanIdJsonPath).toString();
			
			Response viewFitInterviewFeedbackResponse = markGloberFitTestHelper.getViewFitInterviewFeedback(staffPlanId, globerType);
			validateResponseToContinueTest(viewFitInterviewFeedbackResponse, 200, "View fit interview feedback call was not successful", true);
			assertEquals(restUtils.getValueFromResponse(viewFitInterviewFeedbackResponse, "status"), "success", "Status is not success");
			
			String fitInterviewReasonJsonPath = "details..fitInterviewReason";
			String expectedFitInterviewReason = restUtils.getValueFromResponse(markGloberFitResponse, fitInterviewReasonJsonPath).toString();
			String actualFitInterviewReason = restUtils.getValueFromResponse(viewFitInterviewFeedbackResponse, fitInterviewReasonJsonPath).toString();;
			assertEquals(actualFitInterviewReason, expectedFitInterviewReason, String.format("Actual fit Interview reason was %s and expected fit interview reason is %s ", actualFitInterviewReason, expectedFitInterviewReason));
			test.log(Status.PASS, "View Fit interview feedback for New Hire is successful ");
		}
	}
	
	/**
	 * This method will verify mark fit interview for new hire
	 * @param userName 
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "clientInterviewFeedbackValidUser", dataProviderClass = ClientInterviewFeedbackDataProvider.class, priority = 0, groups = {ExeGroups.Regression,ExeGroups.Sanity})
	public void markSTGNewHireFitInterviewConductedNo(String userName, String userRole) throws Exception {
		String interviewConducted = "false";
		String globerType = "New Hire";
		MarkGloberFitTestHelper  markGloberFitTestHelper = new MarkGloberFitTestHelper(userName);
		Response markGloberFitResponse = markGloberFitTestHelper.markFitUnfitGlober(userName, "FIT", globerType, interviewConducted);
		
		if(markGloberFitResponse.getStatusCode()==400) {
			StaffRequestBaseTest.test.log(Status.SKIP, "Invalid staff plan id so can't mark glober as fit");
		}else {
			validateResponseToContinueTest(markGloberFitResponse, 201, "Mark fit glober call was not successful", true);
			assertEquals(restUtils.getValueFromResponse(markGloberFitResponse, "status"), "success", "Status is not success");
			
			String actualMsg = (String) restUtils.getValueFromResponse(markGloberFitResponse, "message");
			String expectedMsg = "Feedback for the Glober saved successfully.";
			assertEquals(actualMsg, expectedMsg, String.format("Actual message was %s and expected message is %s ", actualMsg, expectedMsg));
			
			String fitInterviewStatusJsonPath = "details.fitInterviewStatus";
			String actualFitInterviewStatus = restUtils.getValueFromResponse(markGloberFitResponse, fitInterviewStatusJsonPath).toString();
			assertEquals(actualFitInterviewStatus, "FIT", String.format("Actual fit Interview Status was %s and expected fit interview status is %s ", actualFitInterviewStatus, "FIT"));
			test.log(Status.PASS, "Mark New Hire as fit is successful ");
			
			String staffPlanIdJsonPath = "details.staffPlanId";
			String staffPlanId = restUtils.getValueFromResponse(markGloberFitResponse, staffPlanIdJsonPath).toString();
			
			Response viewFitInterviewFeedbackResponse = markGloberFitTestHelper.getViewFitInterviewFeedback(staffPlanId, globerType);
			validateResponseToContinueTest(viewFitInterviewFeedbackResponse, 200, "View fit interview feedback call was not successful", true);
			assertEquals(restUtils.getValueFromResponse(viewFitInterviewFeedbackResponse, "status"), "success", "Status is not success");
			
			String fitInterviewReasonJsonPath = "details..fitInterviewReason";
			String expectedFitInterviewReason = restUtils.getValueFromResponse(markGloberFitResponse, fitInterviewReasonJsonPath).toString();
			String actualFitInterviewReason = restUtils.getValueFromResponse(viewFitInterviewFeedbackResponse, fitInterviewReasonJsonPath).toString();;
			assertEquals(actualFitInterviewReason, expectedFitInterviewReason, String.format("Actual fit Interview reason was %s and expected fit interview reason is %s ", actualFitInterviewReason, expectedFitInterviewReason));
			test.log(Status.PASS, "View Fit interview feedback for New Hire is successful ");
		}
	}
	
	/**
	 * This method will verify save feedback through fit interview for in pipe
	 * @param userName
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "clientInterviewFeedbackValidUser", dataProviderClass = ClientInterviewFeedbackDataProvider.class, priority = 0, groups = {ExeGroups.Regression,ExeGroups.Sanity})
	public void markSTGInPipeFitInterviewConductedYes(String userName, String userRole) throws Exception {
		String interviewConducted = "true";
		String globerType = "In Pipe";
		MarkGloberFitTestHelper  markGloberFitTestHelper = new MarkGloberFitTestHelper(userName);
		Response markGloberFitResponse = markGloberFitTestHelper.markFitUnfitGlober(userName, "FIT", globerType, interviewConducted);
		
		if(markGloberFitResponse.getStatusCode()==400) {
			StaffRequestBaseTest.test.log(Status.SKIP, "Invalid staff plan id so can't mark glober as fit");
		}else {
			validateResponseToContinueTest(markGloberFitResponse, 201, "Mark fit glober call was not successful", true);
			assertEquals(restUtils.getValueFromResponse(markGloberFitResponse, "status"), "success", "Status is not success");
			
			String actualMsg = (String) restUtils.getValueFromResponse(markGloberFitResponse, "message");
			String expectedMsg = "Feedback for the Glober saved successfully.";
			assertEquals(actualMsg, expectedMsg, String.format("Actual message was %s and expected message is %s ", actualMsg, expectedMsg));
			
			String fitInterviewStatusJsonPath = "details.fitInterviewStatus";
			String actualFitInterviewStatus = restUtils.getValueFromResponse(markGloberFitResponse, fitInterviewStatusJsonPath).toString();
			assertEquals(actualFitInterviewStatus, "FIT", String.format("Actual fit Interview Status was %s and expected fit interview status is %s ", actualFitInterviewStatus, "FIT"));
			test.log(Status.PASS, "Mark In Pipe STG as fit is successful ");
			
			String staffPlanIdJsonPath = "details.staffPlanId";
			String staffPlanId = restUtils.getValueFromResponse(markGloberFitResponse, staffPlanIdJsonPath).toString();
			
			Response viewFitInterviewFeedbackResponse = markGloberFitTestHelper.getViewFitInterviewFeedback(staffPlanId, globerType);
			validateResponseToContinueTest(viewFitInterviewFeedbackResponse, 200, "View fit interview feedback call was not successful", true);
			assertEquals(restUtils.getValueFromResponse(viewFitInterviewFeedbackResponse, "status"), "success", "Status is not success");
			
			String fitInterviewReasonJsonPath = "details..fitInterviewReason";
			String expectedFitInterviewReason = restUtils.getValueFromResponse(markGloberFitResponse, fitInterviewReasonJsonPath).toString();
			String actualFitInterviewReason = restUtils.getValueFromResponse(viewFitInterviewFeedbackResponse, fitInterviewReasonJsonPath).toString();;
			assertEquals(actualFitInterviewReason, expectedFitInterviewReason, String.format("Actual fit Interview reason was %s and expected fit interview reason is %s ", actualFitInterviewReason, expectedFitInterviewReason));
			test.log(Status.PASS, "View Fit interview feedback for In Pipe is successful ");
		}
	}
	
	/**
	 * This method will verify save feedback through fit interview for candidate
	 * @param userName
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "clientInterviewFeedbackValidUser", dataProviderClass = ClientInterviewFeedbackDataProvider.class, priority = 0, groups = {ExeGroups.Regression,ExeGroups.Sanity})
	public void markSTGCandidateFitInterviewConductedYes(String userName, String userRole) throws Exception {
		String interviewConducted = "true";
		String globerType = "Candidate";
		MarkGloberFitTestHelper  markGloberFitTestHelper = new MarkGloberFitTestHelper(userName);
		Response markGloberFitResponse = markGloberFitTestHelper.markFitUnfitGlober(userName, "FIT", globerType, interviewConducted);
		
		if(markGloberFitResponse.getStatusCode()==400) {
			StaffRequestBaseTest.test.log(Status.SKIP, "Invalid staff plan id so can't mark glober as fit");
		}else {
			validateResponseToContinueTest(markGloberFitResponse, 201, "Mark fit glober call was not successful", true);
			assertEquals(restUtils.getValueFromResponse(markGloberFitResponse, "status"), "success", "Status is not success");
			
			String actualMsg = (String) restUtils.getValueFromResponse(markGloberFitResponse, "message");
			String expectedMsg = "Feedback for the Glober saved successfully.";
			assertEquals(actualMsg, expectedMsg, String.format("Actual message was %s and expected message is %s ", actualMsg, expectedMsg));
			
			String fitInterviewStatusJsonPath = "details.fitInterviewStatus";
			String actualFitInterviewStatus = restUtils.getValueFromResponse(markGloberFitResponse, fitInterviewStatusJsonPath).toString();
			assertEquals(actualFitInterviewStatus, "FIT", String.format("Actual fit Interview Status was %s and expected fit interview status is %s ", actualFitInterviewStatus, "FIT"));
			test.log(Status.PASS, "Mark Candidate as fit is successful ");
			
			String staffPlanIdJsonPath = "details.staffPlanId";
			String staffPlanId = restUtils.getValueFromResponse(markGloberFitResponse, staffPlanIdJsonPath).toString();
			
			Response viewFitInterviewFeedbackResponse = markGloberFitTestHelper.getViewFitInterviewFeedback(staffPlanId, globerType);
			validateResponseToContinueTest(viewFitInterviewFeedbackResponse, 200, "View fit interview feedback call was not successful", true);
			assertEquals(restUtils.getValueFromResponse(viewFitInterviewFeedbackResponse, "status"), "success", "Status is not success");
			
			String fitInterviewReasonJsonPath = "details..fitInterviewReason";
			String expectedFitInterviewReason = restUtils.getValueFromResponse(markGloberFitResponse, fitInterviewReasonJsonPath).toString();
			String actualFitInterviewReason = restUtils.getValueFromResponse(viewFitInterviewFeedbackResponse, fitInterviewReasonJsonPath).toString();;
			assertEquals(actualFitInterviewReason, expectedFitInterviewReason, String.format("Actual fit Interview reason was %s and expected fit interview reason is %s ", actualFitInterviewReason, expectedFitInterviewReason));
			test.log(Status.PASS, "View Fit interview feedback for Candidate is successful ");
		}
	}

	/**
	 * This method will verify fit interview confirmation as yes
	 * @param userName
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "clientInterviewFeedbackValidUser", dataProviderClass = ClientInterviewFeedbackDataProvider.class, priority = 0, groups = ExeGroups.Regression)
	public void fitInterviewConfirmationTrue(String userName, String userRole) throws Exception {
		String interviewConducted = "true";
		MarkGloberFitTestHelper  markGloberFitTestHelper = new MarkGloberFitTestHelper(userName);
		Response markGloberFitResponse = markGloberFitTestHelper.markFitUnfitGlober(userName, "FIT", "Glober", interviewConducted);

		if(markGloberFitResponse.getStatusCode()==400) {
			StaffRequestBaseTest.test.log(Status.SKIP, "Invalid staff plan id so can't mark glober as fit");
		}else {
			validateResponseToContinueTest(markGloberFitResponse, 201, "Mark fit glober call was not successful", true);
			assertEquals(restUtils.getValueFromResponse(markGloberFitResponse, "status"), "success", "Status is not success");
			
			String actualMsg = (String) restUtils.getValueFromResponse(markGloberFitResponse, "message");
			String expectedMsg = "Feedback for the Glober saved successfully.";
			assertEquals(actualMsg, expectedMsg, String.format("Actual message was %s and expected message is %s ", actualMsg, expectedMsg));
			
			String staffPlanIdJsonPath = "details.staffPlanId";
			String staffPlanId = restUtils.getValueFromResponse(markGloberFitResponse, staffPlanIdJsonPath).toString();
			test.log(Status.PASS, "Mark glober as fit is successful ");
			
			Response fitInterviewConfirmationResponse = markGloberFitTestHelper.confirmFitInterview(staffPlanId, "true");
			validateResponseToContinueTest(fitInterviewConfirmationResponse, 200, "Fit interview confirmation API call was not successful", true);

			String actualFitInterviewConfirmationResponseDB = markGloberFitTestHelper.getFitInterviewConfirmationFromDB(staffPlanId);
			assertEquals(actualFitInterviewConfirmationResponseDB, "Yes", String.format("Actual fit interview confirmation response from DB is %s and expected fit interview confirmation response from DB is %s", actualFitInterviewConfirmationResponseDB,"Yes"));
			test.log(Status.PASS, "Fit interview confirmation as yes is successful ");
			
			Response fitInterviewConfirmationSecondResponse = markGloberFitTestHelper.confirmFitInterview(staffPlanId, "true");
			validateResponseToContinueTest(fitInterviewConfirmationSecondResponse, 208, "Fit interview confirmation API call for the second time for the same staffplan id was not successful", true);
			String actualStatusCode = (String) restUtils.getValueFromResponse(fitInterviewConfirmationSecondResponse, "statusCode");
			assertEquals(actualStatusCode, "ALREADY_REPORTED", String.format("Actual fit interview confirmation status code of API response value for the same staff Plan id is %s and expected fit interview confirmation status code for the same staff Plan id is %s", actualStatusCode, "ALREADY_REPORTED"));
			test.log(Status.PASS, "Fit interview confirmation as yes for the second time for the same staff plan id is successful ");
		}
	}
	
	/**
	 * This method will verify fit interview confirmation as no
	 * @param userName
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "clientInterviewFeedbackValidUser", dataProviderClass = ClientInterviewFeedbackDataProvider.class, priority = 0, groups = ExeGroups.Regression)
	public void fitInterviewConfirmationFalse(String userName, String userRole) throws Exception {
		String interviewConducted = "true";
		MarkGloberFitTestHelper  markGloberFitTestHelper = new MarkGloberFitTestHelper(userName);
		Response markGloberFitResponse = markGloberFitTestHelper.markFitUnfitGlober(userName, "FIT", "Glober", interviewConducted);

		if(markGloberFitResponse.getStatusCode()==400) {
			StaffRequestBaseTest.test.log(Status.SKIP, "Invalid staff plan id so can't mark glober as fit");
		}else {
			validateResponseToContinueTest(markGloberFitResponse, 201, "Mark fit glober call was not successful", true);
			assertEquals(restUtils.getValueFromResponse(markGloberFitResponse, "status"), "success", "Status is not success");
			
			String actualMsg = (String) restUtils.getValueFromResponse(markGloberFitResponse, "message");
			String expectedMsg = "Feedback for the Glober saved successfully.";
			assertEquals(actualMsg, expectedMsg, String.format("Actual message was %s and expected message is %s ", actualMsg, expectedMsg));
			
			String staffPlanIdJsonPath = "details.staffPlanId";
			String staffPlanId = restUtils.getValueFromResponse(markGloberFitResponse, staffPlanIdJsonPath).toString();
			
			Response fitInterviewConfirmationResponse = markGloberFitTestHelper.confirmFitInterview(staffPlanId, "false");
			validateResponseToContinueTest(fitInterviewConfirmationResponse, 200, "Fit interview confirmation API call was not successful", true);
			String actualFitInterviewConfirmationMsg = (String) restUtils.getValueFromResponse(fitInterviewConfirmationResponse, "message");
			assertEquals(actualFitInterviewConfirmationMsg, "success", String.format("Actual fit interview confirmation message was %s and expected fit interview confirmation message is %s ", actualFitInterviewConfirmationMsg, "success"));
			
			String actualFitInterviewConfirmationResponseDB = markGloberFitTestHelper.getFitInterviewConfirmationFromDB(staffPlanId);
			assertEquals(actualFitInterviewConfirmationResponseDB, "No", String.format("Actual fit interview confirmation response from DB is %s and expected fit interview confirmation response from DB is %s", actualFitInterviewConfirmationResponseDB,"No"));
			test.log(Status.PASS, "Fit interview confirmation as no is successful ");
			
			Response fitInterviewConfirmationSecondResponse = markGloberFitTestHelper.confirmFitInterview(staffPlanId, "false");
			validateResponseToContinueTest(fitInterviewConfirmationSecondResponse, 208, "Fit interview confirmation API call for the second time for the same staffplan id was not successful", true);
			String actualStatusCode = (String) restUtils.getValueFromResponse(fitInterviewConfirmationSecondResponse, "statusCode");
			assertEquals(actualStatusCode, "ALREADY_REPORTED", String.format("Actual fit interview confirmation status code of API response value for the same staff Plan id is %s and expected fit interview confirmation status code for the same staff Plan id is %s", actualStatusCode, "ALREADY_REPORTED"));
			test.log(Status.PASS, "Fit interview confirmation as no for the second time for the same staff plan id is successful ");
		}
	}
	
	/**
	 * This method will verify mark glober fit for invalid user
	 * @param userName
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "clientInterviewFeedbackInvalidUser", dataProviderClass = ClientInterviewFeedbackDataProvider.class, priority = 1, groups = ExeGroups.Regression)
	public void markGloberFitInvalidUser(String userName, String userRole) throws Exception {
		String interviewConducted = "true";
		MarkGloberFitTestHelper  markGloberFitTestHelper = new MarkGloberFitTestHelper(userName);
		Response markGloberFitResponse = markGloberFitTestHelper.markFitUnfitGlober(userName, "FIT", "Glober", interviewConducted);
		validateResponseToContinueTest(markGloberFitResponse, 403, "Mark glober fit call was not successful for recruiting role", true);
		
		int status = (int) restUtils.getValueFromResponse(markGloberFitResponse, "status");
		String msg = (String) restUtils.getValueFromResponse(markGloberFitResponse, "message");
		
		assertEquals(status, 403, String.format("Actual status is %s and Expected status is %s", status, 403));
		assertEquals(msg,"User is not having valid permission or role","Incorrect message for recruting role for checking access");
		test.log(Status.PASS, "Mark glober as fit for invalid user is successful");
	}
	
	/**
	 * This method will verify reject glober through fit intervew
	 * @param userName
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "clientInterviewFeedbackValidUser", dataProviderClass = ClientInterviewFeedbackDataProvider.class, priority = 0, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void rejectGloberThroughFitInterviewConductedYes(String userName, String userRole) throws Exception {
		String interviewConducted = "true";
		MarkGloberFitTestHelper  markGloberFitTestHelper = new MarkGloberFitTestHelper(userName);
		Response markGloberFitResponse = markGloberFitTestHelper.markFitUnfitGlober(userName, "UNFIT", "Glober", interviewConducted);
		
		if(markGloberFitResponse.getStatusCode()==400) {
			StaffRequestBaseTest.test.log(Status.SKIP, "Invalid staff plan id so can't mark glober as unfit");
		}else {
			validateResponseToContinueTest(markGloberFitResponse, 201, "Reject glober through fit interview call was not successful", true);
			assertEquals(restUtils.getValueFromResponse(markGloberFitResponse, "status"), "success", "Status is not success");
			
			String actualMsg = (String) restUtils.getValueFromResponse(markGloberFitResponse, "message");
			String expectedMsg = "Glober rejected! <br/> Interview feedback submitted successfully.";
			assertEquals(actualMsg, expectedMsg, String.format("Actual message was %s and expected message is %s ", actualMsg, expectedMsg));
			
			String fitInterviewStatusJsonPath = "details.fitInterviewStatus";
			String actualFitInterviewStatus = restUtils.getValueFromResponse(markGloberFitResponse, fitInterviewStatusJsonPath).toString();
			assertEquals(actualFitInterviewStatus, "UNFIT", String.format("Actual fit Interview Status was %s and expected fit interview status is %s ", actualFitInterviewStatus, "UNFIT"));
			test.log(Status.PASS, "Reject Glober through fit interview is successful");	
		}
	}
	
	/**
	 * This method will verify reject glober through fit intervew
	 * @param userName
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "clientInterviewFeedbackValidUser", dataProviderClass = ClientInterviewFeedbackDataProvider.class, priority = 0, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void rejectGloberThroughFitInterviewConductedNo(String userName, String userRole) throws Exception {
		String interviewConducted = "false";
		MarkGloberFitTestHelper  markGloberFitTestHelper = new MarkGloberFitTestHelper(userName);
		Response markGloberFitResponse = markGloberFitTestHelper.markFitUnfitGlober(userName, "UNFIT", "Glober", interviewConducted);
		
		if(markGloberFitResponse.getStatusCode()==400) {
			StaffRequestBaseTest.test.log(Status.SKIP, "Invalid staff plan id so can't mark glober as unfit");
		}else {
			validateResponseToContinueTest(markGloberFitResponse, 201, "Reject glober through fit interview call was not successful", true);
			assertEquals(restUtils.getValueFromResponse(markGloberFitResponse, "status"), "success", "Status is not success");
			
			String actualMsg = (String) restUtils.getValueFromResponse(markGloberFitResponse, "message");
			String expectedMsg = "Glober rejected! <br/> Interview feedback submitted successfully.";
			assertEquals(actualMsg, expectedMsg, String.format("Actual message was %s and expected message is %s ", actualMsg, expectedMsg));
			
			String fitInterviewStatusJsonPath = "details.fitInterviewStatus";
			String actualFitInterviewStatus = restUtils.getValueFromResponse(markGloberFitResponse, fitInterviewStatusJsonPath).toString();
			assertEquals(actualFitInterviewStatus, "UNFIT", String.format("Actual fit Interview Status was %s and expected fit interview status is %s ", actualFitInterviewStatus, "UNFIT"));
			test.log(Status.PASS, "Reject Glober through fit interview is successful");	
		}
	}
	
	/**
	 * This method will verify reject glober by position through fit interview
	 * @param userName
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "clientInterviewFeedbackValidUser", dataProviderClass = ClientInterviewFeedbackDataProvider.class, priority = 0, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void rejectByPositionThroughFitInterviewConductedYes(String userName, String userRole) throws Exception {
		String interviewConducted = "true";
		MarkGloberFitTestHelper  markGloberFitTestHelper = new MarkGloberFitTestHelper(userName);
		Response markGloberFitResponse = markGloberFitTestHelper.rejectByPositionAndLocation(userName, "Glober", REJECTBYPOSITION, "UNFIT", interviewConducted);
		
		if(markGloberFitResponse.getStatusCode()==400) {
			StaffRequestBaseTest.test.log(Status.SKIP, "Invalid staff plan id so can't mark glober as unfit");
		}else {
			validateResponseToContinueTest(markGloberFitResponse, 201, "Reject glober by position through fit interview was not successful", true);
			assertEquals(restUtils.getValueFromResponse(markGloberFitResponse, "status"), "success", "Status is not success");
			
			String actualMsg = (String) restUtils.getValueFromResponse(markGloberFitResponse, "message");
			String expectedMsg = "Glober rejected! <br/> Interview feedback submitted successfully.";
			assertEquals(actualMsg, expectedMsg, String.format("Actual message was %s and expected message is %s ", actualMsg, expectedMsg));
			
			String fitInterviewStatusJsonPath = "details.fitInterviewStatus";
			String actualFitInterviewStatus = restUtils.getValueFromResponse(markGloberFitResponse, fitInterviewStatusJsonPath).toString();
			assertEquals(actualFitInterviewStatus, "UNFIT", String.format("Actual fit Interview Status was %s and expected fit interview status is %s ", actualFitInterviewStatus, "UNFIT"));
			test.log(Status.PASS, "Reject glober by position through fit interview is successful");	
		}
	}
	
	/**
	 * This method will verify reject glober by location through fit interview
	 * @param userName
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "clientInterviewFeedbackValidUser", dataProviderClass = ClientInterviewFeedbackDataProvider.class, priority = 0, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void rejectByLocationInterviewConductedNo(String userName, String userRole) throws Exception {
		String interviewConducted = "false";
		MarkGloberFitTestHelper  markGloberFitTestHelper = new MarkGloberFitTestHelper(userName);
		Response markGloberFitResponse = markGloberFitTestHelper.rejectByPositionAndLocation(userName, "Glober", REJECTBYLOCATION, "UNFIT", interviewConducted);
		
		if(markGloberFitResponse.getStatusCode()==400) {
			StaffRequestBaseTest.test.log(Status.SKIP, "Invalid staff plan id so can't mark glober as unfit");
		}else {
			validateResponseToContinueTest(markGloberFitResponse, 201, "Reject glober by location through fit interview was not successful", true);
			assertEquals(restUtils.getValueFromResponse(markGloberFitResponse, "status"), "success", "Status is not success");
			
			String actualMsg = (String) restUtils.getValueFromResponse(markGloberFitResponse, "message");
			String expectedMsg = "Glober rejected! <br/> Interview feedback submitted successfully.";
			assertEquals(actualMsg, expectedMsg, String.format("Actual message was %s and expected message is %s ", actualMsg, expectedMsg));
			
			String fitInterviewStatusJsonPath = "details.fitInterviewStatus";
			String actualFitInterviewStatus = restUtils.getValueFromResponse(markGloberFitResponse, fitInterviewStatusJsonPath).toString();
			assertEquals(actualFitInterviewStatus, "UNFIT", String.format("Actual fit Interview Status was %s and expected fit interview status is %s ", actualFitInterviewStatus, "UNFIT"));
			test.log(Status.PASS, "Reject glober by location through fit interview is successful");	
		}
	}
	
	/**
	 * This method will verify mark glober as unfit for invalid user
	 * @param userName
	 * @param userRole	
	 * @throws Exception
	 */
	@Test(dataProvider = "clientInterviewFeedbackInvalidUser", dataProviderClass = ClientInterviewFeedbackDataProvider.class, priority = 1, groups = ExeGroups.Regression)
	public void rejectGloberThroughFitInterviewInvalidUser(String userName, String userRole) throws Exception {
		String interviewConducted = "true";
		MarkGloberFitTestHelper  markGloberFitTestHelper = new MarkGloberFitTestHelper(userName);
		Response markGloberFitResponse = markGloberFitTestHelper.markFitUnfitGlober(userName, "UNFIT", "Glober", interviewConducted);
		validateResponseToContinueTest(markGloberFitResponse, 403, "Mark glober unfit call was successful for recruiting role", true);
		
		int status = (int) restUtils.getValueFromResponse(markGloberFitResponse, "status");
		String msg = (String) restUtils.getValueFromResponse(markGloberFitResponse, "message");
		
		assertEquals(status, 403, String.format("Actual status is %s and Expected status is %s", status, "403"));
		assertEquals(msg,"User is not having valid permission or role","Incorrect message for recruting role for checking access");
		test.log(Status.PASS, "Mark glober as unfit for invalid user is successful");	
	}
}
