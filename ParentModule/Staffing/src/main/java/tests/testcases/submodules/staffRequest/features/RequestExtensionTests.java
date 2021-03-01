package tests.testcases.submodules.staffRequest.features;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import dataproviders.submodules.staffRequest.features.CreateNewPositionDataProviders;
import io.restassured.response.Response;
import tests.testcases.submodules.staffRequest.StaffRequestBaseTest;
import tests.testhelpers.submodules.staffRequest.features.RequestExtensionTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;
import utils.RestUtils;

public class RequestExtensionTests extends StaffRequestBaseTest{
RestUtils restUtils = new RestUtils();
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("Request Extension of Glober");
	}
	
	/**
	 * This method will verify mark glober as fit
	 * @param userName
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "CreateSRUsingValidUsers", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, priority = 0, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void requestExtension(String userName, String userRole) throws Exception {
		RequestExtensionTestHelper requestExtensionTestHelper = new RequestExtensionTestHelper(userName);
		Response reqExtentionResponse = requestExtensionTestHelper.putRequestExtension("Glober");
		
		if(reqExtentionResponse.getStatusCode()==400) {
			StaffRequestBaseTest.test.log(Status.SKIP, "Invalid staff plan id so can't request extension");
		}else {
			validateResponseToContinueTest(reqExtentionResponse, 200, "Request Extension call was not successful", true);
			assertEquals(restUtils.getValueFromResponse(reqExtentionResponse, "status"), "success", "Status is not success");
			assertTrue(restUtils.getValueFromResponse(reqExtentionResponse, "message").toString().contains("Request sent successfully"));
			
			String reqExtesnionFlowJsonPath = "details.requestExtensionFlow";
			String actualReqExtesnionFlow = restUtils.getValueFromResponse(reqExtentionResponse, reqExtesnionFlowJsonPath).toString();
			
			assertEquals(actualReqExtesnionFlow, "EDITED", String.format("Actual fit Interview Status was %s and expected fit interview status is %s ", actualReqExtesnionFlow, "EDITED"));
			
			assertTrue(restUtils.getValueFromResponse(reqExtentionResponse, "details.requestExtensionDto.comment").toString().contains("Request extension done"));
			
			test.log(Status.PASS, "Mark glober as fit is successful ");
		}
	}
	
	/**
	 * This method will verify assigning mentor to mentee
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "userListWithTL", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, priority = 0, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void assignMentorToMentee(String user) throws Exception {
		RequestExtensionTestHelper requestExtensionTestHelper = new RequestExtensionTestHelper(user);
		
		String menteeId = requestExtensionTestHelper.assignMentorToMentee(user);
		String actualMentorGloberId = requestExtensionTestHelper.getGloberId(user);
		List<String> expectedMentorGloberId = requestExtensionTestHelper.getMentorGloberId(menteeId);
		String expectedMentorIdFromGloberView = requestExtensionTestHelper.getMentorIdFromGloberView(menteeId);
		assertEquals(actualMentorGloberId, expectedMentorGloberId.get(0), "Mentor glober id is not matching with saved db glober id from mentor table");
		assertEquals(actualMentorGloberId, expectedMentorGloberId.get(1), "Mentor glober id is not matching with saved db glober id from mentor_leader table");
		assertEquals(actualMentorGloberId, expectedMentorIdFromGloberView, "Mentor glober id is not matching with saved db glober id from glober_view table");
		
		test.log(Status.PASS, "Asssiging mentor to mentee is successful");
	
	}
	
	/**
	 * This method will verify assigning mentor to invalid mentee
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "userListWithTL", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, priority = 1, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void assignMentorToNullMentee(String user) throws Exception {
		RequestExtensionTestHelper requestExtensionTestHelper = new RequestExtensionTestHelper(user);
		Response invalidAssignMentorToMenteeResponse = requestExtensionTestHelper.assignMentorToNullMentee(user);
		validateResponseToContinueTest(invalidAssignMentorToMenteeResponse, 500, "Assigning mentor to null mentee value API call was not successful", true);
		String actualMsg = restUtils.getValueFromResponse(invalidAssignMentorToMenteeResponse, "message").toString();
		String expectedMsg = "menteeId is zero";
		assertEquals(actualMsg, expectedMsg, "Msg mismatch for assigning mentor to invalid mentee");
		test.log(Status.PASS, "Asssiging mentor to invalid mentee is successful");
	}
	
	/**
	 * This method will verify assigning Invalid mentor to mentee
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "userListWithTL", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, priority = 1, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void assignNullMentorToMentee(String user) throws Exception {
		RequestExtensionTestHelper requestExtensionTestHelper = new RequestExtensionTestHelper(user);
		Response invalidAssignMentorToMenteeResponse = requestExtensionTestHelper.assignNullMentorToMentee(user);
		validateResponseToContinueTest(invalidAssignMentorToMenteeResponse, 500, "Assigning null mentor to mentee value API call was not successful", true);
		String actualMsg = restUtils.getValueFromResponse(invalidAssignMentorToMenteeResponse, "message").toString();
		String expectedMsg = "mentorId is zero";
		assertEquals(actualMsg, expectedMsg, "Msg mismatch for assigning mentor to invalid mentee");
		test.log(Status.PASS, "Asssiging invalid mentor to mentee is successful");
	}
	
	/**
	 * This method will verify assign mentor to mentee already having mentor
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "userListWithTL", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, priority = 1, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void assignMentorToMenteeHavingMentor(String user) throws Exception {
		RequestExtensionTestHelper requestExtensionTestHelper = new RequestExtensionTestHelper(user);
		Response assignMentorToMenteeHavingMentor = requestExtensionTestHelper.assignMentorToMenteeHavingMentor(user);
		validateResponseToContinueTest(assignMentorToMenteeHavingMentor, 201, "Assigning mentor to mentee already having mentor value API call was not successful", true);
		String actualStatusCode = restUtils.getValueFromResponse(assignMentorToMenteeHavingMentor, "statusCode").toString();
		String actualMsg = restUtils.getValueFromResponse(assignMentorToMenteeHavingMentor, "message").toString();
		String expectedMsg = "The Glober already has an assigned Mentor";
		assertEquals(actualStatusCode, "400" , "Mismatch in status code");
		assertEquals(actualMsg, expectedMsg, "Msg mismatch for assigning mentor to mentee already having mentor");
		test.log(Status.PASS, "Assigning mentor to mentee already having mentor is successful");
	}
	
	/**
	 * This method will verify assign mentor to mentee already having mentor
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "userListWithTL", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, priority = 1, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void assignMentorToMenteeSameIds(String user) throws Exception {
		RequestExtensionTestHelper requestExtensionTestHelper = new RequestExtensionTestHelper(user);
		Response assignMentorToMenteeSameIdResponse = requestExtensionTestHelper.assignMentorToMenteeSameIds(user);
		validateResponseToContinueTest(assignMentorToMenteeSameIdResponse, 201, "Assigning mentor to mentee same ids API call was not successful", true);
		String actualStatusCode = restUtils.getValueFromResponse(assignMentorToMenteeSameIdResponse, "statusCode").toString();
		String actualMsg = restUtils.getValueFromResponse(assignMentorToMenteeSameIdResponse, "message").toString();
		String expectedMsg = "Mentor is same as mentee";
		assertEquals(actualStatusCode, "400" , "Mismatch in status code");
		assertEquals(actualMsg, expectedMsg, "Msg mismatch for assigning mentor to mentee same ids");
		test.log(Status.PASS, "Assigning mentor to mentee same ids is successful");
	}
	
	/**
	 * This method will verify assign inactive mentor to mentee
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "userListWithTL", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, priority = 1, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void assignInactiveMentorToMentee(String user) throws Exception {
		RequestExtensionTestHelper requestExtensionTestHelper = new RequestExtensionTestHelper(user);
		Response assignMentorToMenteeSameIdResponse = requestExtensionTestHelper.assignInactiveMentorToMentee(user);
		validateResponseToContinueTest(assignMentorToMenteeSameIdResponse, 201, "Assigning inactive mentor to mentee API call was not successful", true);
		String actualStatusCode = restUtils.getValueFromResponse(assignMentorToMenteeSameIdResponse, "statusCode").toString();
		String actualMsg = restUtils.getValueFromResponse(assignMentorToMenteeSameIdResponse, "message").toString();
		String expectedMsg = "Suggested mentor has inactive status";
		assertEquals(actualStatusCode, "400" , "Mismatch in status code");
		assertEquals(actualMsg, expectedMsg, "Msg mismatch for assigning inactive mentor to mentee");
		test.log(Status.PASS, "Assigning inactive mentor to mentee is successful");
	}	
}
