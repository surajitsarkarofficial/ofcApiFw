package tests.testcases.submodules.staffRequest.features;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import dataproviders.submodules.staffRequest.features.ClientInterviewFeedbackDataProvider;
import io.restassured.response.Response;
import tests.testcases.submodules.staffRequest.StaffRequestBaseTest;
import tests.testhelpers.submodules.staffRequest.features.ClientInterviewFeedbackHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;
import utils.RestUtils;

public class ClientInterviewFeedbackTests extends StaffRequestBaseTest{
	String status,message, clientInterviewComment, clientInterviewstatus = null;
	RestUtils restUtils = new RestUtils();
	SoftAssert softAssert = new SoftAssert();

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("Client Interview Feedback");
	}

	/**
	 * This method is used to provide client interview feedback for accept
	 * @param userName
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "clientInterviewFeedbackValidUser", dataProviderClass = ClientInterviewFeedbackDataProvider.class, enabled = true, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void clientInterviewAcceptFeedback(String userName, String userRole) throws Exception {
		clientInterviewComment = "Glober is accepted by client";
		clientInterviewstatus = "ACCEPT"; 
		ClientInterviewFeedbackHelper clientInterviewFeedbackHelper = new ClientInterviewFeedbackHelper(userName);
		Response postClientInterviewFeedbackresponse = clientInterviewFeedbackHelper.postClientInterviewFeedback(clientInterviewstatus, clientInterviewComment, "Glober");
		validateResponseToContinueTest(postClientInterviewFeedbackresponse, 201, "Adding Client interview feedback call was not successful", true);

		status = (String) restUtils.getValueFromResponse(postClientInterviewFeedbackresponse, "status");
		softAssert.assertEquals(status, "success", "Status is not success");

		softAssert.assertAll();
		test.log(Status.PASS, "You have successfully provided client interview feedback");
	}
	
	/**
	 * This test will verify client interview feedback for invalid user
	 * @param userName
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "clientInterviewFeedbackInvalidUser", dataProviderClass = ClientInterviewFeedbackDataProvider.class, enabled = true, groups = ExeGroups.Regression)
	public void clientInterviewFeedbackInvalidUser(String userName,String userRole) throws Exception {
		clientInterviewComment = "Glober is accepted by client";
		clientInterviewstatus = "ACCEPT"; 
		ClientInterviewFeedbackHelper clientInterviewFeedbackHelper = new ClientInterviewFeedbackHelper(userName);
		Response postClientInterviewFeedbackresponse = clientInterviewFeedbackHelper.postClientInterviewFeedback(clientInterviewstatus, clientInterviewComment, "Glober");
		validateResponseToContinueTest(postClientInterviewFeedbackresponse, 403, "Adding Client interview feedback call for invalid user is not successful", true);

		int status = (int) restUtils.getValueFromResponse(postClientInterviewFeedbackresponse, "status");
		message = (String) restUtils.getValueFromResponse(postClientInterviewFeedbackresponse, "message");
		
		assertEquals(status, 403, String.format("Actual status is %s and Expected status is %s", status, 403));
		assertEquals(message,"User is not having valid permission or role","Incorrect message for invalid role for checking access");
		test.log(Status.PASS, "You have successfully verified client interview feedback for invalid user");
	}

}