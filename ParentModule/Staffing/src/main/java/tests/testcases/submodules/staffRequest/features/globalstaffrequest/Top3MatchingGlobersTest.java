package tests.testcases.submodules.staffRequest.features.globalstaffrequest;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import database.submodules.staffRequest.features.globalstaffrequest.Top3MatchingGloberDBHelper;
import dataproviders.submodules.reports.features.handlerandhandlerteam.HandlerAndHandlerTeamDataProvider;
import io.restassured.response.Response;
import tests.testcases.submodules.staffRequest.StaffRequestBaseTest;
import tests.testhelpers.StaffingTestHelper;
import tests.testhelpers.submodules.staffRequest.features.globalstaffrequest.Top3MatchingGlobersTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

public class Top3MatchingGlobersTest extends StaffRequestBaseTest{

	Top3MatchingGlobersTestHelper matchingGloberHelper;
	Top3MatchingGloberDBHelper matchingGloberDb;
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("GlobalStaffRequest");
	}
	
	/**
	 * This Test will verify active plan of top matching glober.
	 * 
	 * @param username
	 * @param globerType
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "changeHandlerViaSARARoleUsers", dataProviderClass = HandlerAndHandlerTeamDataProvider.class, enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void verifyGloberHaveActivePlan(String userName,String globerType) throws Exception {
		
		matchingGloberHelper = new Top3MatchingGlobersTestHelper(userName);
		matchingGloberDb = new Top3MatchingGloberDBHelper();
		
		Response response = new StaffingTestHelper().getSRDetailsFromGrid(userName, globerType);
		validateResponseToContinueTest(response, 200, "Unable to load SR Grid.", true);
		
		Response matchingGloberResponse = matchingGloberHelper.getGloberActivePlan(response);		
		String globerId = restUtils.getValueFromResponse(matchingGloberResponse, "details.asGloberList[0].globerId").toString();	
		boolean isGloberPlanPresent = matchingGloberDb.getTopMatchingGloberPlans(matchingGloberHelper.getPositionId());
	
	    if(isGloberPlanPresent) {
	    	Assert.assertTrue(isGloberPlanPresent,"Glober's Active Plan is present");
	    	ArrayList<Integer> globerIds = matchingGloberDb.getGloberIds(matchingGloberHelper.getPositionId());
	    	Assert.assertFalse(globerIds.contains(Integer.parseInt(globerId)), "Glober's Active Plan is present");
	    }else {
	    	Assert.assertFalse(isGloberPlanPresent,"Glober's Active Plan is not present");
	    }
	    test.log(Status.PASS, "Top Matching Glober is Validated with No Active Plans on same Staff Request.");
	}
	
	/**
	 * This Test will verify glober's other plans on another staff request
	 * 
	 * @param username
	 * @param globerType
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "changeHandlerViaSARARoleUsers", dataProviderClass = HandlerAndHandlerTeamDataProvider.class, enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void verifyGloberOtherPlansOnOtherStaffRequest(String userName,String globerType) throws Exception {
		
		matchingGloberHelper = new Top3MatchingGlobersTestHelper(userName);
		matchingGloberDb = new Top3MatchingGloberDBHelper();
		
		Response response = new StaffingTestHelper().getSRDetailsFromGrid(userName, globerType);
		validateResponseToContinueTest(response, 200, "Unable to load SR Grid.", true);
		
		Response matchingGloberResponse = matchingGloberHelper.getGloberActivePlan(response);
		String globerId = restUtils.getValueFromResponse(matchingGloberResponse, "details.asGloberList[0].globerId").toString();	
		String activePlansCount = restUtils.getValueFromResponse(matchingGloberResponse, "details.asGloberList[0].activePlanCount").toString();	
	
		int globerPlansFromDb = matchingGloberDb.getCountOfGloberActivePlans(globerId);
		
		Assert.assertEquals(Integer.parseInt(activePlansCount),globerPlansFromDb,String.format("Actual plans : %s and Expected plans : %d", activePlansCount,globerPlansFromDb));
		test.log(Status.PASS, "Top Matching Glober's Plans are Validated.");
	}
	
	
}
