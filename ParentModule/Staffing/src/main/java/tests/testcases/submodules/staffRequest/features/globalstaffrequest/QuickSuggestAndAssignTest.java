package tests.testcases.submodules.staffRequest.features.globalstaffrequest;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import database.GlowDBHelper;
import dataproviders.submodules.staffRequest.features.globalstaffrequest.GlobalStaffRequestDataProvider;
import io.restassured.response.Response;
import tests.testcases.submodules.staffRequest.StaffRequestBaseTest;
import tests.testhelpers.StaffingTestHelper;
import tests.testhelpers.submodules.staffRequest.features.globalstaffrequest.QuickSuggestAndAssignTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;
import utils.RestUtils;

public class QuickSuggestAndAssignTest extends StaffRequestBaseTest {

	Response response;
	RestUtils utils;
	QuickSuggestAndAssignTestHelper suggestAndAssign;
	GlowDBHelper glowDbHelper;
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("GlobalStaffRequest");
	}

	/**
	 * This Test will suggest glober and validate suggestion message
	 * 
	 * @param username
	 * @param globerType
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "globerAssignmnentAccessRoleUsers", dataProviderClass = GlobalStaffRequestDataProvider.class, enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void validateQuickSuggestFunctionalityWithSrValidationMsg(String username, String globerType) throws Exception {

		suggestAndAssign = new QuickSuggestAndAssignTestHelper(username);
		utils = new RestUtils();
		Response response = new StaffingTestHelper().getSRDetailsFromGrid(username, globerType);
		validateResponseToContinueTest(response, 200, "Unable to load SR Grid.", true);
		response = suggestAndAssign.getSuggestGloberWithSrDates(response, username, globerType);
		String status = utils.getValueFromResponse(response, "status").toString();
		String msg = utils.getValueFromResponse(response, "message").toString();
		
		if (status.equalsIgnoreCase("success")) {
			Assert.assertTrue(msg.contains("Success"),
					String.format("Expected status : success and Actual status : %s", status));
			suggestAndAssign
					.updatePlanStatus(utils.getValueFromResponse(response, "details[0].staffPlanId").toString());
			test.log(Status.PASS, "Glober is Suggested Successfully.");
		} else {
			Assert.assertTrue(msg.contains("Oops! Can't suggest."),
					String.format("Expected message Oops! Can't suggest. and Actual message : %s", msg));
			test.log(Status.PASS, "Glober is validated.");
		}
	}
	
	
	/**
	 * This Test will suggest glober twice on same Sr to validate error mesage
	 * 
	 * @param username
	 * @param globerType
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "globerAssignmnentAccessRoleUsers", dataProviderClass = GlobalStaffRequestDataProvider.class, enabled = true, groups = {ExeGroups.Regression,"Glow-II"})
	public void validateQuickSuggestGloberOnSameSr(String username, String globerType) throws Exception {
		suggestAndAssign = new QuickSuggestAndAssignTestHelper(username);
		utils = new RestUtils();
		Response response = new StaffingTestHelper().getSRDetailsFromGrid(username, globerType);
		validateResponseToContinueTest(response, 200, "Unable to load SR Grid.", true);
		
		response = suggestAndAssign.suggestGloberAgainOnSameSr(response, username, globerType);
		String msg = utils.getValueFromResponse(response, "message").toString();
	    
		Assert.assertEquals(msg, "This Position is already suggested to this Glober.", String.format(
				"Expected message This Position is already suggested to this Glober. and Actual message :", msg));
		test.log(Status.PASS, "Glober Plan is validated.");
		
	}
	
	/**
	 * This Test will suggest glober with high plan
	 * 
	 * @param username
	 * @param globerType
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "globerAssignmnentAccessRoleUsers", dataProviderClass = GlobalStaffRequestDataProvider.class, enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void validateSuggestGloberFunctionalityWithHighPlan(String username, String globerType) throws Exception {
		glowDbHelper = new GlowDBHelper();;
		suggestAndAssign = new QuickSuggestAndAssignTestHelper(username);
		utils = new RestUtils();
		Response response = new StaffingTestHelper().getSRDetailsFromGrid(username, globerType);
		validateResponseToContinueTest(response, 200, "Unable to load SR Grid.", true);
		response = suggestAndAssign.suggestGloberWithHighPlan(response, username, globerType);
		String status = utils.getValueFromResponse(response, "status").toString();
		String msg = utils.getValueFromResponse(response, "message").toString();
		
		if(username.equalsIgnoreCase("bernardo.manzella") || username.equalsIgnoreCase("kapil.kanojiya")) {
			Assert.assertEquals(status, "success", "Failed to suggest glober...Actual message is :" + msg);
			test.log(Status.PASS, "Glober is suggested successfully!");
			suggestAndAssign
					.updatePlanStatus(utils.getValueFromResponse(response, "details[0].staffPlanId").toString());
		} else {
			Assert.assertEquals(msg,
					"Oh snap! Looks like the Glober you are trying to suggest, already has a high plan somewhere.",
					String.format("Expected message Oh snap! Looks and Actual message :", msg));
			test.log(Status.PASS, "Glober Plan is validated.");
		}			
	}

	/**
	 * This Test will suggest talent pool glober.
	 * 
	 * @param username
	 * @param globerType
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "globerAssignmnentAccessRoleUsers", dataProviderClass = GlobalStaffRequestDataProvider.class, enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void validateDirectSuggestCreatePlan(String username, String globerType) throws Exception {
		suggestAndAssign = new QuickSuggestAndAssignTestHelper(username);
		utils = new RestUtils();
		Response response = new StaffingTestHelper().getSRDetailsFromGrid(username, globerType);
		validateResponseToContinueTest(response, 200, "Unable to load SR Grid.", true);
		
		response = suggestAndAssign.createPlanByDirectSuggestGlober(response, username, globerType);
		validateResponseToContinueTest(response, 201, "Unable to create new plan.", true);
		
		String status = utils.getValueFromResponse(response, "status").toString();
		String msg = utils.getValueFromResponse(response, "message").toString();
		
		Assert.assertEquals(status,"success","Failed in creating globerplan..Error message : "+msg);
		test.log(Status.PASS, "Glober plan is created successfully!");
		
		suggestAndAssign.updatePlanStatus(utils.getValueFromResponse(response, "details[0].staffPlanId").toString());
	}
	
	/**
	 * This Test will validate error message while suggesting glober with wrong end date
	 * 
	 * @param username
	 * @param globerType
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "globerAssignmnentAccessRoleUsers", dataProviderClass = GlobalStaffRequestDataProvider.class, enabled = true, groups = {ExeGroups.Regression,"Glow-II"})
	public void validateErrorMsgWhileQuickAssignGloberWithWrongEndDt(String username, String globerType) throws Exception {
		suggestAndAssign = new QuickSuggestAndAssignTestHelper(username);
		utils = new RestUtils();
		Response response = new StaffingTestHelper().getSRDetailsFromGrid(username, globerType);
		validateResponseToContinueTest(response, 200, "Unable to load SR Grid.", true);
		
		response = suggestAndAssign.getPayloadWithWrongSrEndDt(response, username, globerType);
		validateResponseToContinueTest(response, 400, "Issue with assignGlober EndDt", true);
		
		String msg = utils.getValueFromResponse(response, "message").toString();
		
		Assert.assertEquals(msg,"End date cannot be earlier than start date.",String.format("Expected msg : End date cannot be earlier than start date and Actual msg : %s", msg));
		test.log(Status.PASS, "Assignment endDt is validated!");
	}
	
	/**
	 * This Test will validate error message while suggesting glober on In progress Sr
	 * 
	 * @param username
	 * @param globerType
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "globerAssignmnentAccessRoleUsers", dataProviderClass = GlobalStaffRequestDataProvider.class, enabled = true, groups = {ExeGroups.Regression,"Glow-II"})
	public void validateErrorMshWhileAssignGloberOnOnGoingAssignment(String username, String globerType) throws Exception {
		suggestAndAssign = new QuickSuggestAndAssignTestHelper(username);
		utils = new RestUtils();
		Response response = new StaffingTestHelper().getSRDetailsFromGrid(username, globerType);
		validateResponseToContinueTest(response, 200, "Unable to load SR Grid.", true);
		
		response = suggestAndAssign.getAssignedGloberToAssignToSr(response, username, globerType);
		validateResponseToContinueTest(response, 400, "Issue with Sr dates", true);
		String msg = utils.getValueFromResponse(response, "message").toString();
		
		if(username.equalsIgnoreCase("bernardo.manzella") || username.equalsIgnoreCase("kapil.kanojiya")) {
			Assert.assertTrue(msg.contains("Glober is not available in the selected period."),String.format("Expected msg : Glober is not available in the selected period. and Actual msg : %s", msg));
			test.log(Status.PASS, "On Going Assinged Glober is Validated!");
		}else {
			Assert.assertTrue(msg.contains("Assignment Start Date should be later than today"),String.format("Expected msg : Assignment Start Date should be later than today. and Actual msg : %s", msg));
			test.log(Status.PASS, "User access oriented validation message is validated!");
		}
	}
	
	/**
	 * This Test will suggest glober on onGoing assignment and validate it by roles.
	 * 
	 * @param username
	 * @param globerType
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "globerAssignmnentAccessRoleUsers", dataProviderClass = GlobalStaffRequestDataProvider.class, enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void validateAssignGloberOnOnGoingProjectSR(String username, String globerType) throws Exception {
		glowDbHelper = new GlowDBHelper();
		suggestAndAssign = new QuickSuggestAndAssignTestHelper(username);
		utils = new RestUtils();
		Response response = new StaffingTestHelper().getSRDetailsFromGrid(username, globerType);
		validateResponseToContinueTest(response, 200, "Unable to load SR Grid.", true);
		
		response = suggestAndAssign.assignGloberOnOnGoingAssignment(response, username, globerType);

		String status = utils.getValueFromResponse(response, "status").toString();
		String msg = utils.getValueFromResponse(response, "message").toString();
		
		if(username.equalsIgnoreCase("bernardo.manzella") || username.equalsIgnoreCase("kapil.kanojiya"))  {
			Assert.assertEquals(msg,"Assignment created successfully.",String.format("Expected msg : Assignment created successfully. and Actual msg : %s", msg));
			test.log(Status.PASS, "Glober is assigned successfully!");
		}else {
			Assert.assertEquals(status,"fail","Issue in user access...Error message is : "+msg);
			test.log(Status.PASS, "User role access is validated!");
		}
	}
	
	/**
	 * This Test will assign glober with selecting past date as assignment start date and validate it 
	 * by user roles
	 * @param username
	 * @param globerType
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "globerAssignmnentAccessRoleUsers", dataProviderClass = GlobalStaffRequestDataProvider.class, enabled = true, groups = {ExeGroups.Regression,"Glow-II"})
	public void validateQuickAssignFunctionalityWithSelectingPastDt(String username, String globerType) throws Exception {
		glowDbHelper = new GlowDBHelper();
		suggestAndAssign = new QuickSuggestAndAssignTestHelper(username);
		utils = new RestUtils();
		Response response = new StaffingTestHelper().getSRDetailsFromGrid(username, globerType);
		validateResponseToContinueTest(response, 200, "Unable to load SR Grid.", true);
		
		response = suggestAndAssign.assignedGloberWithPastDtAsStrtDt(response, username, globerType);
		String status = utils.getValueFromResponse(response, "status").toString();
		String msg = utils.getValueFromResponse(response, "message").toString();
		
        if(username.equalsIgnoreCase("bernardo.manzella") || username.equalsIgnoreCase("kapil.kanojiya")){
			Assert.assertEquals(status,"success","Failed in creating assignment...Error message is : "+msg);
			Assert.assertEquals(msg,"Assignment created successfully.",String.format("Expected msg : Assignment created successfully. and Actual msg : %s", msg));
			test.log(Status.PASS, "Glober is assigned successfully!");
		}else {
			Assert.assertEquals(status,"fail","Issue in access of user : "+username+" and Error message is : "+msg);
			test.log(Status.PASS, "User role access is validated!");
		}
		
	}
	
	/**
	 * This Test will assign STG type glober.
	 * 
	 * @param username
	 * @param globerType
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "stgAssignmnentAccessRoleUsers", dataProviderClass = GlobalStaffRequestDataProvider.class, enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void validateQuickAssignFunctionalityWithSTG(String username, String globerType) throws Exception {
		glowDbHelper = new GlowDBHelper();
		suggestAndAssign = new QuickSuggestAndAssignTestHelper(username);
		utils = new RestUtils();
		Response response = new StaffingTestHelper().getSRDetailsFromGrid(username, globerType);
		validateResponseToContinueTest(response, 200, "Unable to load SR Grid.", true);
		
		response = suggestAndAssign.assignSTGs(response, username, globerType);
		
		String status = utils.getValueFromResponse(response, "status").toString();
		String msg = utils.getValueFromResponse(response, "message").toString();
		
		if(username.equalsIgnoreCase("bernardo.manzella") || username.equalsIgnoreCase("kapil.kanojiya")){
			Assert.assertEquals(status,"success","Failed in creating assignment...Error message is : "+msg);
			Assert.assertEquals(msg,globerType+" has been successfully assigned",String.format("Expected msg : "+globerType+" has been successfully assigned and Actual msg : %s", msg));
			test.log(Status.PASS, globerType+" is assigned successfully!");
		}else {
			if(globerType.equalsIgnoreCase("New Hire")) {
				Assert.assertEquals(status,"success","Failed in assigning glober..Error message is : "+msg);
				Assert.assertEquals(msg,globerType+" has been successfully assigned",String.format("Expected msg : "+globerType+" has been successfully assigned and Actual msg : %s", msg));
				test.log(Status.PASS, globerType+" is assigned successfully!");
			}else {
				Assert.assertEquals(msg, "Yippie! User has been Requested for an Assignment.", String.format("Expected msg : Yippie! User has been Requested for an Assignment. and Actual msg : %s", msg));
				test.log(Status.PASS, globerType+" has been requested successfully!");

			}
		}
		
	}
	
	/**
	 * This Test will assign talent pool glober.
	 * 
	 * @param username
	 * @param globerType
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "globerAssignmnentAccessRoleUsers", dataProviderClass = GlobalStaffRequestDataProvider.class, enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void assignTalentPoolGlober(String username, String globerType) throws Exception {
		glowDbHelper = new GlowDBHelper();
		suggestAndAssign = new QuickSuggestAndAssignTestHelper(username);
		utils = new RestUtils();
		Response response = new StaffingTestHelper().getSRDetailsFromGrid(username, globerType);
		validateResponseToContinueTest(response, 200, "Unable to load SR Grid.", true);
		
		Response assignGloberResponse = suggestAndAssign.assignGlober(response, username, globerType);
		validateResponseToContinueTest(assignGloberResponse, 201, "Unable to Assign Glober.", true);
		
		String msg = utils.getValueFromResponse(assignGloberResponse, "message").toString();
		Assert.assertEquals(msg, "Assignment created successfully.", String.format("Expected msg : Assignment created successfully. and Actual msg : %s", msg));
		test.log(Status.PASS, globerType+" has been assigned successfully!");
	}
}
