package tests.testcases.submodules.openpositions;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import database.submodules.openpositions.OpenPositionsDBHelper;
import io.restassured.response.Response;
import tests.testhelpers.submodules.openpositions.OpenPositionsTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;
import utils.RestUtils;
import utils.Utilities;

/**
 * @author shadab.waikar
 */
public class OpenPositionsTests extends OpenPositionBaseTest{

	OpenPositionsDBHelper dbHelper;
	RestUtils restUtils = new RestUtils();;
	private String user = null;
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("Open Postions");
	}
	
	/**
	 * This method will validate Total Open Position count
	 * @throws Exception
	 */
	@Test(enabled = true, groups = {ExeGroups.Regression,"Glow-II"})
	public void verifyOpenPositionTotalCount() throws Exception {
		dbHelper = new OpenPositionsDBHelper();
		user = dbHelper.getGloberUsernameByGloberFullName("Shadab", "Waikar");
		String glober = dbHelper.getGloberId(user);
		String globalId = dbHelper.getGlobalIdFromGloberId(glober);
		
		OpenPositionsTestHelper testHelper = new OpenPositionsTestHelper(user);
		Response response = testHelper.getTotalCountOfOpenPositions(user,globalId);
		validateResponseToContinueTest(response, 200, "Unable to Fetch total Count of Open Positions", true);
	    String status = restUtils.getValueFromResponse(response, "status").toString();
		
		Assert.assertEquals(status, "success", "Failed to load Open positions...");
		test.log(Status.PASS, "Open Positions Count Validated.");
	}
	
	/**
	 * This method will validate Total Open Position count with providing wrong global Id
	 * @throws Exception
	 */
	@Test(enabled = true, groups = {ExeGroups.Regression,"Glow-II"})
	public void verifyOpenPositionTotalCountWithWrongGlobalId() throws Exception {
		dbHelper = new OpenPositionsDBHelper();
		user = dbHelper.getGloberUsernameByGloberFullName("Shadab", "Waikar");
		OpenPositionsTestHelper testHelper = new OpenPositionsTestHelper(user);
		Response response = testHelper.getTotalCountOfOpenPositions(user,"12345678");
		validateResponseToContinueTest(response, 200, "Unable to Fetch total Count of Open Positions", true);
	    String size = restUtils.getValueFromResponse(response, "details.size").toString();
		
		Assert.assertEquals(size, "0", "Expected Size : 0 and Actual Size : "+size);
		test.log(Status.PASS, "Open Positions Count validated with wrong globalId.");
	}
	
	/**
	 * This method will validate applied and remaining open position count of glober
	 * @throws Exception
	 */
	@Test(enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void verifyAppliedAndRemainingOpenPositionsCountForGlober() throws Exception {
		dbHelper = new OpenPositionsDBHelper();
		user = dbHelper.getGloberUsernameByGloberFullName("Shadab", "Waikar");
		OpenPositionsTestHelper testHelper = new OpenPositionsTestHelper(user);
		    
		Map<Object, Object> globerAppliedAndRemainingCount = testHelper.getGloberAppliedAndRemainingOpenPositionsCount(user);
		int globerCount = Integer.parseInt(globerAppliedAndRemainingCount.get("globerAppliedCount").toString());
		int countFromDb = Integer.parseInt(globerAppliedAndRemainingCount.get("appliedCountFromDB").toString());
		int remainingCount = Integer.parseInt(globerAppliedAndRemainingCount.get("remainingCount").toString());
		
		Assert.assertEquals(globerCount, countFromDb,"Issue with glober applied open position count.");
		Assert.assertEquals(remainingCount, 2 - countFromDb,"Issue with glober remianing open position count.");
		test.log(Status.PASS, "Applied and Remaining Open Position Count is Validated.");
	}
	
	/**
	 * This method will validate glober position role with by default suggested open positions on landing page
	 * @throws Exception
	 */
	@Test(enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void verifyGloberPositionMatchingOpenPostions() throws Exception {
		dbHelper = new OpenPositionsDBHelper();
		user = dbHelper.getGloberUsernameByGloberFullName("Shadab", "Waikar");
		OpenPositionsTestHelper testHelper = new OpenPositionsTestHelper(user);
		
		Response response = testHelper.getMatchingOpenPositionAccordingToGloberPositionRole(user);
		validateResponseToContinueTest(response, 200, "Unable to Fetch total Count of Open Positions", true);
		
		String globerPositionRoleFromResponse = restUtils.getValueFromResponse(response, "details.list[0].position").toString();
		Assert.assertEquals(globerPositionRoleFromResponse,testHelper.getGloberPositionRoleFromDb(),String.format("Expected positionRole : %s and Actual positionRole : %s", testHelper.getGloberPositionRoleFromDb(),globerPositionRoleFromResponse));
		test.log(Status.PASS, "Glober Position Role with database positionRole is Validated!");
	}
	
	/**
	 * This method will validate order of matching open positions in descending order
	 * @throws Exception
	 */
	@Test(enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void verifyGloberOpenPositionMatchingScoreInDescendingOrder() throws Exception {
		dbHelper = new OpenPositionsDBHelper();
		user = dbHelper.getGloberUsernameByGloberFullName("Shadab", "Waikar");
		OpenPositionsTestHelper testHelper = new OpenPositionsTestHelper(user);		
		boolean isPercentageListIsEqual = testHelper.getGloberPositionsAccordingToPercentage(user);
		Assert.assertTrue(isPercentageListIsEqual, "Order of matching open position percenetage is not in descending order.");
		test.log(Status.PASS, "Order of open position percentages is Validated!");
	}

	/**
	 * This method will validate glober limit to apply to open positions
	 * @throws Exception
	 */
	@Test(enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void verifyErrorMsgWhileReachedOverLimit() throws Exception {
		dbHelper = new OpenPositionsDBHelper();
		user = dbHelper.getGloberUsernameByGloberFullName("Shadab", "Waikar");
		OpenPositionsTestHelper testHelper = new OpenPositionsTestHelper(user);	
		Response openPositionResponse = testHelper.getGloberHavingReachedToApplyigOPLimit(user);
		validateResponseToContinueTest(openPositionResponse, 400, "Able to Apply beyond max. applying OP limit.", true);
		
		String msg = restUtils.getValueFromResponse(openPositionResponse, "message").toString();
		
		Assert.assertEquals(msg,"Sorry! you can apply for a maximum of 2 open positions!",String.format("Expected message : Sorry! you can apply for a maximum of 2 open positions! and Actual message : %s", msg));
		test.log(Status.PASS, "max. apply to open position limit is validated.");
	}
	
	/**
	 * This method will validate apply to open position functionality
	 * @throws Exception
	 */
	@Test(enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void verifyApplyToOpenPosition() throws Exception {
		dbHelper = new OpenPositionsDBHelper();
		user = dbHelper.getGloberUsernameByGloberFullName("Shadab", "Waikar");
		OpenPositionsTestHelper testHelper = new OpenPositionsTestHelper(user);
		
		Response openPositionResponse = testHelper.applyToOpenPosition(user);
		validateResponseToContinueTest(openPositionResponse, 200, "Unable to apply to open position", true);
		String status = restUtils.getValueFromResponse(openPositionResponse, "status").toString();
		String msg = restUtils.getValueFromResponse(openPositionResponse, "message").toString();
		test.log(Status.PASS, "Successfully Apply to open position.");
		
		String planDetails[] = testHelper.getPlanDetails();
		
		Assert.assertEquals(status,"success","Failed to parse Json.");
		Assert.assertEquals(msg,"Applied to the Open Position Successfully!","Failed to parse Json. : "+msg);
		Assert.assertEquals(planDetails[0],"Low",String.format("Expected plan : Low and Actual plan : %s", planDetails[0]));
		Assert.assertEquals(planDetails[1],Utilities.getTodayDate("yyyy-MM-dd"),String.format("Expected Date : %s and Actual plan : %s",Utilities.getTodayDate("yyyy-MM-dd"), planDetails[1]));
		test.log(Status.PASS, "Open position plan details validated.");
	}
	
	/**
	 * This method will validate error message while applying to already applied open position
	 * @throws Exception
	 */
	@Test(enabled = true, groups = {ExeGroups.Regression,"Glow-II"})
	public void verifyErrorMsgWhileReapplyingToAppliedOP() throws Exception {
		
		dbHelper = new OpenPositionsDBHelper();
		user = dbHelper.getGloberUsernameByGloberFullName("Shadab", "Waikar");
		OpenPositionsTestHelper testHelper = new OpenPositionsTestHelper(user);
		
		Response response = testHelper.applyToAppliedOpenPosition(user);
		validateResponseToContinueTest(response, 400, "Issue while reapplying to open position", true);
		String msg = restUtils.getValueFromResponse(response, "message").toString();
		
		Assert.assertEquals(msg,"You are currently being evaluated for this Open Position! Please apply for another one!",String.format("Expected message : You are currently being evaluated for this Open Position! Please apply for another one! and Actual message :  %s",msg));
		test.log(Status.PASS, "Error message while re-applying to applied open position is validated.");
	}
	
	/**
	 * This method will validate position filter result of open positions
	 * @throws Exception
	 */
	@Test(enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void verifyResultsOfPositionFilter() throws Exception {
		
		dbHelper = new OpenPositionsDBHelper();
		user = dbHelper.getGloberUsernameByGloberFullName("Anand", "Datir");
		String positionRoleId = dbHelper.getPositionRoleIdAccordingToPositionRolName("Test Automation Engineer");
		
		OpenPositionsTestHelper testHelper = new OpenPositionsTestHelper(user);
		Response positionFilterResponse = testHelper.applyToOpenPositionFilter(user,"positionRoleIds",positionRoleId);
		String actualPosition = restUtils.getValueFromResponse(positionFilterResponse, "details.list[0].position").toString();
		
		Assert.assertEquals(actualPosition,"Test Automation Engineer",String.format("Expected positionRole : Test Automation Engineer and Actual positionRole:  %s",actualPosition));
		test.log(Status.PASS, "Position Filter results are validated.");
	}
	
	/**
	 * This method will validate Seniority filter result of open positions
	 * @throws Exception
	 */
	@Test(enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void verifyResultsOfSceniorityFilter() throws Exception {
		
		dbHelper = new OpenPositionsDBHelper();
		user = dbHelper.getGloberUsernameByGloberFullName("Anand", "Datir");
		String seniorityId = dbHelper.getSeniorityIdByName("Sr");
		
		OpenPositionsTestHelper testHelper = new OpenPositionsTestHelper(user);
		Response seniorityFilterResponse = testHelper.applyToOpenPositionFilter(user,"seniorityIds",seniorityId);
		String actualSeniority = restUtils.getValueFromResponse(seniorityFilterResponse, "details.list[0].seniority").toString();
		
		Assert.assertEquals(actualSeniority,"Sr",String.format("Expected seniority : Sr and Actual seniority:  %s",actualSeniority));
		test.log(Status.PASS, "Seniority Filter results are validated.");
	}
	
	/**
	 * This method will validate Location filter result of open positions
	 * @throws Exception
	 */
	@Test(enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void verifyResultsOfLocationFilter() throws Exception {
		
		dbHelper = new OpenPositionsDBHelper();
		user = dbHelper.getGloberUsernameByGloberFullName("Anand", "Datir");
		String locationId = dbHelper.getLocationIdFromLocationName("IN/PUNE");
		
		OpenPositionsTestHelper testHelper = new OpenPositionsTestHelper(user);
		Response locationFilterResponse = testHelper.applyToOpenPositionFilter(user,"locationIds",locationId);
		String actualLocation = restUtils.getValueFromResponse(locationFilterResponse, "details.list[0].location").toString();
		
		Assert.assertTrue(actualLocation.contains("IN/PUNE"),String.format("Expected location : IN/PUNE and Actual location:  %s",actualLocation));
		test.log(Status.PASS, "Location Filter results are validated.");
	}
	
	/**
	 * This method will validate Client filter result of open positions
	 * @throws Exception
	 */
	@Test(enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void verifyResultsOfClientFilter() throws Exception {
		
		dbHelper = new OpenPositionsDBHelper();
		user = dbHelper.getGloberUsernameByGloberFullName("Anand", "Datir");
		String clientId = dbHelper.getClientIdFromClientName("Disney DTCI");
		
		OpenPositionsTestHelper testHelper = new OpenPositionsTestHelper(user);
		Response clientFilterResponse = testHelper.applyToOpenPositionFilter(user,"clientIds",clientId);
		String actualClient = restUtils.getValueFromResponse(clientFilterResponse, "details.list[0].clientName").toString();
		
		Assert.assertEquals(actualClient,"Disney DTCI",String.format("Expected client : Disney DTCI and Actual client:  %s",actualClient));
		test.log(Status.PASS, "Client Filter results are validated.");
	}
	
	/**
	 * This method will validate Industry filter result of open positions
	 * @throws Exception
	 */
	@Test(enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void verifyResultsOfIndustryFilter() throws Exception {
		
		dbHelper = new OpenPositionsDBHelper();
		user = dbHelper.getGloberUsernameByGloberFullName("Anand", "Datir");
		
		OpenPositionsTestHelper testHelper = new OpenPositionsTestHelper(user);
		Response clientFilterResponse = testHelper.applyToOpenPositionFilter(user,"industryIds","14");
		String actualIndustry = restUtils.getValueFromResponse(clientFilterResponse, "details.list[0].industryName").toString();
		
		Assert.assertEquals(actualIndustry,"Professional Services",String.format("Expected industry : Professional Services and Actual industry:  %s",actualIndustry));
		test.log(Status.PASS, "Industry Filter results are validated.");
	}
}
