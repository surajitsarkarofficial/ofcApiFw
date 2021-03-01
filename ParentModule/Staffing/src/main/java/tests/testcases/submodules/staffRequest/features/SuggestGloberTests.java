package tests.testcases.submodules.staffRequest.features;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import constants.submodules.staffRequest.StaffRequestConstants;
import dataproviders.submodules.staffRequest.features.CreateNewPositionDataProviders;
import dataproviders.submodules.staffRequest.features.SuggestGloberDataProviders;
import io.restassured.response.Response;
import tests.testcases.submodules.staffRequest.StaffRequestBaseTest;
import tests.testhelpers.submodules.staffRequest.features.SuggestGloberTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;
import utils.RestUtils;

/**
 * 
 * @author akshata.dongare
 *
 */
public class SuggestGloberTests extends StaffRequestBaseTest implements StaffRequestConstants{
	String status,message = null;
	RestUtils restUtils = new RestUtils();
	SoftAssert softAssert = new SoftAssert();

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("Suggest Glober");
	}

	/**
	 * This method will suggest a glober with low plan to a SR
	 * @param userName
	 * @param userRole
	 * @throws Exception
	 * @author akshata.dongare
	 */
	@Test(dataProvider = "CreateSRUsingValidUsers", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, priority = 0, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void suggestGloberWithLowPlan(String userName, String userRole) throws Exception {
		SuggestGloberTestHelper suggestGloberTestHelper = new SuggestGloberTestHelper(userName);
		Response response = suggestGloberTestHelper.suggestGloberToStaffRequest("Glober", "Low");
		
		status = (String) restUtils.getValueFromResponse(response, "status");
		message = (String) restUtils.getValueFromResponse(response, "message");;
		
		if(message.contains("Oh snap!")) {
			validateResponseToContinueTest(response, 400, "Status is not 400 even though glober already has high plan", true);
			StaffRequestBaseTest.test.log(Status.SKIP, "Glober already has high plan somewhere hence can't suggest");
		}else if(message.contains("Sorry, max 3 Suggestions per SR")){
			validateResponseToContinueTest(response, 400, "Status is not 400 even though 3 globers are already suggested", true);
			StaffRequestBaseTest.test.log(Status.SKIP, "Max 3 globers are already suggested to this SR hence can't suggest one more glober");
		}else {
			assertEquals(status, "success", "Status is not success");
			validateResponseToContinueTest(response, 201, "Suggest Glober API call was not successful", true);
		}
		
		test.log(Status.PASS, "You have successfully suggested glober to a Staff Request");
	}

	/**
	 * This method will suggest a glober with medium plan to a SR
	 * @param user
	 * @throws Exception
	 * @author akshata.dongare
	 */
	@Test(dataProvider = "CreateSRUsingValidUsers", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, priority = 0, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void suggestGloberWithMediumPlan(String userName, String userRole) throws Exception {
		SuggestGloberTestHelper suggestGloberTestHelper = new SuggestGloberTestHelper(userName);
		Response response = suggestGloberTestHelper.suggestGloberToStaffRequest("Glober", "Medium");
	
		status = (String) restUtils.getValueFromResponse(response, "status");
		message = (String) restUtils.getValueFromResponse(response, "message");;
		
		if(message.contains("Oh snap!")) {
			validateResponseToContinueTest(response, 400, "Status is not 400 even though glober already has high plan", true);
			StaffRequestBaseTest.test.log(Status.SKIP, "Glober already has high plan somewhere hence can't suggest");
		}else if(message.contains("Sorry, max 3 Suggestions per SR")){
			validateResponseToContinueTest(response, 400, "Status is not 400 even though 3 globers are already suggested", true);
			StaffRequestBaseTest.test.log(Status.SKIP, "Max 3 globers are already suggested to this SR hence can't suggest one more glober");
		}else {
			validateResponseToContinueTest(response, 201, "Unable to suggest glober", true);
			assertEquals(status, "success", "Status is not success");
		}
		test.log(Status.PASS, "You have successfully suggested glober to a Staff Request");
	}

	/**
	 * This method will suggest a glober with high plan to a SR
	 * @param userName
	 * @param userRole
	 * @throws Exception
	 * @author akshata.dongare
	 */
	@Test(dataProvider = "CreateSRUsingValidUsers", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, priority = 0, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void suggestGloberWithHighPlan(String userName, String userRole) throws Exception {
		SuggestGloberTestHelper suggestGloberTestHelper = new SuggestGloberTestHelper(userName);
		Response response = suggestGloberTestHelper.suggestGloberToStaffRequest("Glober", "High");
		
		status = (String) restUtils.getValueFromResponse(response, "status");
		message = (String) restUtils.getValueFromResponse(response, "message");;
		
		if(message.contains("Oh snap!")) {
			validateResponseToContinueTest(response, 400, "Status is not 400 even though glober already has high plan", true);
			StaffRequestBaseTest.test.log(Status.SKIP, "Glober already has high plan somewhere hence can't suggest");
		}else if(message.contains("Sorry, max 3 Suggestions per SR")){
			validateResponseToContinueTest(response, 400, "Status is not 400 even though 3 globers are already suggested", true);
			StaffRequestBaseTest.test.log(Status.SKIP, "Max 3 globers are already suggested to this SR hence can't suggest one more glober");
		}else {
			validateResponseToContinueTest(response, 201, "Unable to suggest glober", true);
			assertEquals(status, "success", "Status is not success");
		}
		test.log(Status.PASS, "You have successfully suggested glober to a Staff Request");
	}

	/**
	 * This method will suggest a Candidate with low plan to a SR
	 * @param user
	 * @throws Exception
	 * @author akshata.dongare
	 */
	@Test(dataProvider = "userListWithTL", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, priority = 1, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void suggestCandidateSTGWithLowPlan(String user) throws Exception {
		SuggestGloberTestHelper suggestGloberTestHelper = new SuggestGloberTestHelper(user);
		Response response = suggestGloberTestHelper.suggestGloberToStaffRequest("Candidate", "Low");
		
		status = (String) restUtils.getValueFromResponse(response, "status");
		message = (String) restUtils.getValueFromResponse(response, "message");;
		
		if(message.contains("Oh snap!")) {
			validateResponseToContinueTest(response, 400, "Status is not 400 even though glober already has high plan", true);
			StaffRequestBaseTest.test.log(Status.SKIP, "Glober already has high plan somewhere hence can't suggest");
		}else if(message.contains("Sorry, max 3 Suggestions per SR")){
			validateResponseToContinueTest(response, 400, "Status is not 400 even though 3 globers are already suggested", true);
			StaffRequestBaseTest.test.log(Status.SKIP, "Max 3 globers are already suggested to this SR hence can't suggest one more glober");
		}else {
			validateResponseToContinueTest(response, 201, "Unable to suggest glober", true);
			assertEquals(status, "success", "Status is not success");
		}
		test.log(Status.PASS, "You have successfully suggested glober to a Staff Request");
	}

	/**
	 * This method will suggest a In pipe with low plan to a SR
	 * @param user
	 * @throws Exception
	 * @author akshata.dongare
	 */
	@Test(dataProvider = "userListWithTL", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, priority = 1, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void suggestInPipeSTGWithLowPlan(String user) throws Exception {
		SuggestGloberTestHelper suggestGloberTestHelper = new SuggestGloberTestHelper(user);
		Response response = suggestGloberTestHelper.suggestGloberToStaffRequest("In Pipe", "Low");
		
		status = (String) restUtils.getValueFromResponse(response, "status");
		message = (String) restUtils.getValueFromResponse(response, "message");;
		
		if(message.contains("Oh snap!")) {
			validateResponseToContinueTest(response, 400, "Status is not 400 even though glober already has high plan", true);
			StaffRequestBaseTest.test.log(Status.SKIP, "Glober already has high plan somewhere hence can't suggest");
		}else if(message.contains("Sorry, max 3 Suggestions per SR")){
			validateResponseToContinueTest(response, 400, "Status is not 400 even though 3 globers are already suggested", true);
			StaffRequestBaseTest.test.log(Status.SKIP, "Max 3 globers are already suggested to this SR hence can't suggest one more glober");
		}else {
			validateResponseToContinueTest(response, 201, "Unable to suggest glober", true);
			assertEquals(status, "success", "Status is not success");
		}
		test.log(Status.PASS, "You have successfully suggested glober to a Staff Request");
	}

	/**
	 * This method will suggest a New Hire with low plan to a SR
	 * @param user
	 * @throws Exception
	 * @author akshata.dongare
	 */
	@Test(dataProvider = "userListWithTL", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, priority = 1, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void suggestNewHireSTGWithLowPlan(String user) throws Exception {
		SuggestGloberTestHelper suggestGloberTestHelper = new SuggestGloberTestHelper(user);
		Response response = suggestGloberTestHelper.suggestGloberToStaffRequest("New Hire", "Low");
		
		status = (String) restUtils.getValueFromResponse(response, "status");
		message = (String) restUtils.getValueFromResponse(response, "message");;
		
		if(message.contains("Oh snap!")) {
			validateResponseToContinueTest(response, 400, "Status is not 400 even though glober already has high plan", true);
			StaffRequestBaseTest.test.log(Status.SKIP, "Glober already has high plan somewhere hence can't suggest");
		}else if(message.contains("Sorry, max 3 Suggestions per SR")){
			validateResponseToContinueTest(response, 400, "Status is not 400 even though 3 globers are already suggested", true);
			StaffRequestBaseTest.test.log(Status.SKIP, "Max 3 globers are already suggested to this SR hence can't suggest one more glober");
		}else {
			validateResponseToContinueTest(response, 201, "Unable to suggest glober", true);
			assertEquals(status, "success", "Status is not success");
		}
		test.log(Status.PASS, "You have successfully suggested glober to a Staff Request");
	}
	
	
	/**
	 * This method will suggest and assign glober to a SR
	 * @param user
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "CreateSRUsingValidUsers", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, priority = 0, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void suggestAndAssignGlober(String user,String userRole) throws Exception {
		SuggestGloberTestHelper suggestGloberTestHelper = new SuggestGloberTestHelper(user);
		Response assignGloberResponse = suggestGloberTestHelper.suggestAndAssignGlober("Glober", "Low");
		
		status = (String) restUtils.getValueFromResponse(assignGloberResponse, "status");
		message = (String) restUtils.getValueFromResponse(assignGloberResponse, "message");;
		
		if(message.contains("Glober is not available in the selected period.")) {
			validateResponseToContinueTest(assignGloberResponse, 400, "Status is not 400 for glober not available", true);
			StaffRequestBaseTest.test.log(Status.SKIP, "Glober is not available hence can not assign to a SR");
		}else if(message.contains("Assignment created successfully.")){
			validateResponseToContinueTest(assignGloberResponse, 201, "Assignment is not created", true);
			assertEquals(status, "success", "status is not success");
		}
		test.log(Status.PASS, "You have successfully assigned glober to a Staff Request");
	}
	
	/**
	 * This method will suggest and assign candidate to a SR
	 * @param user
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "AssignSTGValidUser", dataProviderClass = SuggestGloberDataProviders.class, enabled = true, priority = 0, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void suggestAndAssignCandidate(String user,String userRole) throws Exception {
		boolean pastDate = false;
		SuggestGloberTestHelper suggestGloberTestHelper = new SuggestGloberTestHelper(user);
		Response assignGloberResponse = suggestGloberTestHelper.suggestAndAssignSTG("Candidate", "Low", pastDate);
		
		status = (String) restUtils.getValueFromResponse(assignGloberResponse, "status");
		message = (String) restUtils.getValueFromResponse(assignGloberResponse, "message");;
		
		if(message.contains("Glober is not available in the selected period.")) {
			validateResponseToContinueTest(assignGloberResponse, 400, "Status is not 400 for glober not available", true);
			StaffRequestBaseTest.test.log(Status.SKIP, "Glober is not available hence can not assign to a SR");
		}else if(message.contains("Assignment created successfully.")){
			validateResponseToContinueTest(assignGloberResponse, 201, "Assignment is not created", true);
			assertEquals(status, "success", "status is not success");
		}
		test.log(Status.PASS, "You have successfully assigned candidate to a Staff Request");
	}
	
	/**
	 * This method will suggest and assign STG to a SR
	 * @param user
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "AssignSTGValidUser", dataProviderClass = SuggestGloberDataProviders.class, enabled = true, priority = 0, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void suggestAndAssignInPipe(String user,String userRole) throws Exception {
		boolean pastDate = false;
		SuggestGloberTestHelper suggestGloberTestHelper = new SuggestGloberTestHelper(user);
		Response assignGloberResponse = suggestGloberTestHelper.suggestAndAssignSTG("In Pipe", "Low", pastDate);
		
		status = (String) restUtils.getValueFromResponse(assignGloberResponse, "status");
		message = (String) restUtils.getValueFromResponse(assignGloberResponse, "message");;
		
		if(message.contains("Glober is not available in the selected period.")) {
			validateResponseToContinueTest(assignGloberResponse, 400, "Status is not 400 for glober not available", true);
			StaffRequestBaseTest.test.log(Status.SKIP, "Glober is not available hence can not assign to a SR");
		}else if(message.contains("Assignment created successfully.")){
			validateResponseToContinueTest(assignGloberResponse, 201, "Assignment is not created", true);
			assertEquals(status, "success", "status is not success");
		}
		test.log(Status.PASS, "You have successfully assigned In Pipe to a Staff Request");
	}
	
	/**
	 * This method suggest and assign In pipe having start date less than effective hiring date
	 * @param user
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "AssignSTGValidUser", dataProviderClass = SuggestGloberDataProviders.class, enabled = true, priority = 1, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void suggestAndAssignInPipePastDate(String user,String userRole) throws Exception {
		boolean pastDate = true;
		SuggestGloberTestHelper suggestGloberTestHelper = new SuggestGloberTestHelper(user);
		Response assignGloberResponse = suggestGloberTestHelper.suggestAndAssignSTG("In Pipe", "Low", pastDate);

		status = (String) restUtils.getValueFromResponse(assignGloberResponse, "status");
		message = (String) restUtils.getValueFromResponse(assignGloberResponse, "message");;

		if(message.contains("Glober is not available in the selected period.")) {
			validateResponseToContinueTest(assignGloberResponse, 400, "Status is not 400 for glober not available", true);
			StaffRequestBaseTest.test.log(Status.SKIP, "Glober is not available hence can not assign to a SR");
		}else if(message.contains("Start date cannot")){
			validateResponseToContinueTest(assignGloberResponse, 400, "Assignment is created", true);
			assertEquals(status, "fail", "status is not fail");
			assertEquals(message,"Start date cannot be earlier than effective hiring date."," Wrong message for assigning STG having past date as start date");
		}
		test.log(Status.PASS, "Successfully verified assigning In Pipe having past date");
	}

	/**
	 * This method suggest and assign Candidate having start date less than effective hiring date
	 * @param user
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "AssignSTGValidUser", dataProviderClass = SuggestGloberDataProviders.class, enabled = true, priority = 1, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void suggestAndAssignCandidatePastDate(String user,String userRole) throws Exception {
		boolean pastDate = true;
		SuggestGloberTestHelper suggestGloberTestHelper = new SuggestGloberTestHelper(user);
		Response assignGloberResponse = suggestGloberTestHelper.suggestAndAssignSTG("Candidate", "Low", pastDate);

		status = (String) restUtils.getValueFromResponse(assignGloberResponse, "status");
		message = (String) restUtils.getValueFromResponse(assignGloberResponse, "message");;

		if(message.contains("Glober is not available in the selected period.")) {
			validateResponseToContinueTest(assignGloberResponse, 400, "Status is not 400 for glober not available", true);
			StaffRequestBaseTest.test.log(Status.SKIP, "Glober is not available hence can not assign to a SR");
		}else if(message.contains("Start date cannot")){
			validateResponseToContinueTest(assignGloberResponse, 400, "Assignment is created", true);
			assertEquals(status, "fail", "status is not fail");
			assertEquals(message,"Start date cannot be earlier than effective hiring date."," Wrong message for assigning STG having past date as start date");
		}
		test.log(Status.PASS, "Successfully verified assigning Candidate having past date");
	}
	
	/**
	 * This method will suggest and assign New hire to a SR
	 * @param user
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "CreateSRUsingValidUsers", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, priority = 0, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void suggestAndAssignNewHire(String user,String userRole) throws Exception {
		SuggestGloberTestHelper suggestGloberTestHelper = new SuggestGloberTestHelper(user);
		Response assignGloberResponse = suggestGloberTestHelper.suggestAndAssignGlober("New Hire", "Low");
		
		status = (String) restUtils.getValueFromResponse(assignGloberResponse, "status");
		message = (String) restUtils.getValueFromResponse(assignGloberResponse, "message");;
		
		if(message.contains("Glober is not available in the selected period.")) {
			validateResponseToContinueTest(assignGloberResponse, 400, "Status is not 400 for glober not available", true);
			StaffRequestBaseTest.test.log(Status.SKIP, "Glober is not available hence can not assign to a SR");
		}else if(message.contains("Assignment created successfully.")){
			validateResponseToContinueTest(assignGloberResponse, 201, "Assignment is not created", true);
			assertEquals(status, "success", "status is not success");
		}
		test.log(Status.PASS, "You have successfully assigned New Hire to a Staff Request");
	}
	
	/**
	 * This method will request assignment of a STG candidate for valid user
	 * @param user
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "AssignSTGInvalidUser", dataProviderClass = SuggestGloberDataProviders.class, enabled = true, priority = 0, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void requestAssignmentCandidate(String user,String userRole) throws Exception {
		SuggestGloberTestHelper suggestGloberTestHelper = new SuggestGloberTestHelper(user);
		Response requestAssignmentResponse = suggestGloberTestHelper.requestAssignmentSTG("Candidate", "Low", user);
		
		status = (String) restUtils.getValueFromResponse(requestAssignmentResponse, "status");
		message = (String) restUtils.getValueFromResponse(requestAssignmentResponse, "message");;
		
		if(message.contains("Glober is not available in the selected period.")) {
			validateResponseToContinueTest(requestAssignmentResponse, 400, "Status is not 400 for glober not available", true);
			StaffRequestBaseTest.test.log(Status.SKIP, "Glober is not available hence can not assign to a SR");
		}else if(message.contains("Yippie!")){
			validateResponseToContinueTest(requestAssignmentResponse, 201, "Request Assignment is not created", true);
			assertEquals(status, "success", "status is not success");
			assertEquals(message, "Yippie! User has been Requested for an Assignment.","Request Assignment message is wrong");
			
			String typeJsonPath = "details.type";
			String actualType = restUtils.getValueFromResponse(requestAssignmentResponse, typeJsonPath).toString();
			String expectedType = "Candidate";
			
			assertEquals(actualType, expectedType, String.format("Actual STG type is %s and expected STG type is %s ", actualType,expectedType));
		}
		test.log(Status.PASS, "You have successfully requested assignment for STG candidate to a Staff Request");
	}
	
	/**
	 * This method will request assignment of a STG In Pipe for valid user
	 * @param user
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "AssignSTGInvalidUser", dataProviderClass = SuggestGloberDataProviders.class, enabled = true, priority = 0, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void requestAssignmentInPipe(String user,String userRole) throws Exception {
		SuggestGloberTestHelper suggestGloberTestHelper = new SuggestGloberTestHelper(user);
		Response requestAssignmentResponse = suggestGloberTestHelper.requestAssignmentSTG("In Pipe", "Low", user);
		
		status = (String) restUtils.getValueFromResponse(requestAssignmentResponse, "status");
		message = (String) restUtils.getValueFromResponse(requestAssignmentResponse, "message");;
		
		if(message.contains("Glober is not available in the selected period.")) {
			validateResponseToContinueTest(requestAssignmentResponse, 400, "Status is not 400 for glober not available", true);
			StaffRequestBaseTest.test.log(Status.SKIP, "Glober is not available hence can not assign to a SR");
		}else if(message.contains("Yippie!")){
			validateResponseToContinueTest(requestAssignmentResponse, 201, "Request Assignment is not created", true);
			assertEquals(status, "success", "status is not success");
			assertEquals(message, "Yippie! User has been Requested for an Assignment.","Request Assignment message is wrong");
			
			String typeJsonPath = "details.type";
			String actualType = restUtils.getValueFromResponse(requestAssignmentResponse, typeJsonPath).toString();
			String expectedType = "In Pipe";
			
			assertEquals(actualType, expectedType, String.format("Actual STG type is %s and expected STG type is %s ", actualType,expectedType));
		}
		test.log(Status.PASS, "You have successfully requested assignment for STG In Pipe to a Staff Request");
	}
	
	/**
	 * This method will request assignment of a STG In Pipe for invalid user
	 * @param user
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "AssignSTGValidUser", dataProviderClass = SuggestGloberDataProviders.class, enabled = true, priority = 0, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void requestAssignmentCandidateInvalidUser(String user,String userRole) throws Exception {
		SuggestGloberTestHelper suggestGloberTestHelper = new SuggestGloberTestHelper(user);
		Response requestAssignmentResponse = suggestGloberTestHelper.requestAssignmentSTG("Candidate", "Low", user);
		
		message = (String) restUtils.getValueFromResponse(requestAssignmentResponse, "message");;
		
		if(message.contains("Glober is not available in the selected period.")) {
			validateResponseToContinueTest(requestAssignmentResponse, 400, "Status is not 400 for glober not available", true);
			StaffRequestBaseTest.test.log(Status.SKIP, "Glober is not available hence can not assign to a SR");
		}else if(message.contains("User is not")){
			validateResponseToContinueTest(requestAssignmentResponse, 403, "Adding Client interview feedback call for invalid user is not successful", true);

			int status = (int) restUtils.getValueFromResponse(requestAssignmentResponse, "status");
			message = (String) restUtils.getValueFromResponse(requestAssignmentResponse, "message");
			
			assertEquals(status, 403, String.format("Actual status is %s and Expected status is %s", status, 403));
			assertEquals(message,"User is not having valid permission or role","Incorrect message for invalid role for checking access");
		}
		test.log(Status.PASS, "You have successfully requested assignment for STG Candidate to a Staff Request for invalid user");
	}
	
	/**
	 * This method will request assignment of a STG In Pipe for invalid user
	 * @param user
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "AssignSTGValidUser", dataProviderClass = SuggestGloberDataProviders.class, enabled = true, priority = 0, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void requestAssignmentInPipeInvalidUser(String user,String userRole) throws Exception {
		SuggestGloberTestHelper suggestGloberTestHelper = new SuggestGloberTestHelper(user);
		Response requestAssignmentResponse = suggestGloberTestHelper.requestAssignmentSTG("In Pipe", "Low", user);
		
		message = (String) restUtils.getValueFromResponse(requestAssignmentResponse, "message");;
		
		if(message.contains("Glober is not available in the selected period.")) {
			validateResponseToContinueTest(requestAssignmentResponse, 400, "Status is not 400 for glober not available", true);
			StaffRequestBaseTest.test.log(Status.SKIP, "Glober is not available hence can not assign to a SR");
		}else if(message.contains("User is not")){
			validateResponseToContinueTest(requestAssignmentResponse, 403, "Adding Client interview feedback call for invalid user is not successful", true);

			int status = (int) restUtils.getValueFromResponse(requestAssignmentResponse, "status");
			message = (String) restUtils.getValueFromResponse(requestAssignmentResponse, "message");
			
			assertEquals(status, 403, String.format("Actual status is %s and Expected status is %s", status, 403));
			assertEquals(message,"User is not having valid permission or role","Incorrect message for invalid role for checking access");
		}
		test.log(Status.PASS, "You have successfully requested assignment for STG In pipe to a Staff Request for invalid user");
	}
}