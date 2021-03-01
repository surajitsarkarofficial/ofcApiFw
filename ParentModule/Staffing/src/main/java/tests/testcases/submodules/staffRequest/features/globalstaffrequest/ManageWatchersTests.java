package tests.testcases.submodules.staffRequest.features.globalstaffrequest;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import dataproviders.submodules.reports.features.handlerandhandlerteam.HandlerAndHandlerTeamDataProvider;
import io.restassured.response.Response;
import tests.testcases.submodules.staffRequest.StaffRequestBaseTest;
import tests.testhelpers.StaffingTestHelper;
import tests.testhelpers.submodules.staffRequest.features.globalstaffrequest.CancelSrTestHelper;
import tests.testhelpers.submodules.staffRequest.features.globalstaffrequest.ManageWatcherTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

public class ManageWatchersTests extends StaffRequestBaseTest{
	
	ManageWatcherTestHelper managerWatcherHelper;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("GlobalStaffRequest");
	}
	
	/**
	 * This Test will perform manage watcher of SR via Global Talent Pool with SA and RA
	 * 
	 * @param username
	 * @param globerType
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "changeHandlerViaSARARoleUsers", dataProviderClass = HandlerAndHandlerTeamDataProvider.class, enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void verifyManagerWatcherViaGlobalTP(String username,String globerType) throws Exception {
		
		managerWatcherHelper = new ManageWatcherTestHelper(username);
		
		Response response = new StaffingTestHelper().getSRDetailsFromGrid(username, globerType);
		validateResponseToContinueTest(response, 200, "Unable to load SR Grid.", true);
		
		Response manageWatcherResponse = managerWatcherHelper.manageWatcher(response);
		validateResponseToContinueTest(manageWatcherResponse, 201, "Unable to add new watcher to staff request", true);
	
	    String msg = restUtils.getValueFromResponse(manageWatcherResponse, "message").toString();
	    Assert.assertEquals(msg,"Watchers added successfully", String.format("Actual msg : %s and Expected msg : Watchers added successfully", msg));
		test.log(Status.PASS, "Successfully add Watcher to the Staff Request.");
		
		ArrayList<String> watcherFromDb = managerWatcherHelper.fetchWatchers();
		
		Assert.assertTrue(watcherFromDb.contains(managerWatcherHelper.getWatcherId()), String.format("Actual watcherList : %s and Expected watcher : %s", watcherFromDb,managerWatcherHelper.getWatcherId()));
		test.log(Status.PASS, "Watcher is Validated Successfully.");
		
	}
	
	/**
	 * This Test will perform manage watcher of SR via My Talent Pool with SA and RA
	 * 
	 * @param username
	 * @param globerType
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "changeHandlerViaSARARoleUsers", dataProviderClass = HandlerAndHandlerTeamDataProvider.class, enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void verifyManagerWatcherViaMyTP(String username,String globerType) throws Exception {
		
		managerWatcherHelper = new ManageWatcherTestHelper(username);
		
		Response response = new CancelSrTestHelper(username).getSRDetailsFromMySrGrid(username, globerType);
		validateResponseToContinueTest(response, 200, "Unable to load SR Grid.", true);
		
		Response manageWatcherResponse = managerWatcherHelper.manageWatcher(response);
		validateResponseToContinueTest(manageWatcherResponse, 201, "Unable to add new watcher to staff request", true);
	
	    String msg = restUtils.getValueFromResponse(manageWatcherResponse, "message").toString();
	    Assert.assertEquals(msg,"Watchers added successfully", String.format("Actual msg : %s and Expected msg : Watchers added successfully", msg));
		test.log(Status.PASS, "Successfully add Watcher to the Staff Request.");
		
		ArrayList<String> watcherFromDb = managerWatcherHelper.fetchWatchers();
		
		Assert.assertTrue(watcherFromDb.contains(managerWatcherHelper.getWatcherId()), String.format("Actual watcherList : %s and Expected watcher : %s", watcherFromDb,managerWatcherHelper.getWatcherId()));
		test.log(Status.PASS, "Watcher is Validated Successfully.");
		
	}
	
	/**
	 * This Test will verify error message by adding already added watcher on same staff request
	 * 
	 * @param username
	 * @param globerType
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "changeHandlerViaSARARoleUsers", dataProviderClass = HandlerAndHandlerTeamDataProvider.class, enabled = true, groups = {ExeGroups.Regression,"Glow-II"})
	public void verifyWatcherByAddingSameWatcherOnPosition(String username,String globerType) throws Exception {
		managerWatcherHelper = new ManageWatcherTestHelper(username);
		Response response = new StaffingTestHelper().getSRDetailsFromGrid(username, globerType);
		validateResponseToContinueTest(response, 200, "Unable to load SR Grid.", true);
		
		Response manageWactherResponse = managerWatcherHelper.addAlreadyAddedWatcherOnPositions(response);
		validateResponseToContinueTest(manageWactherResponse, 400, "Able to add watcher.", true);
		
		String msg = restUtils.getValueFromResponse(manageWactherResponse, "message").toString();
		Assert.assertTrue(msg.contains("Some watchers from the list are already added. Please remove them and then save."), "Issue with Watcher Validation Message.");
		test.log(Status.PASS, "Watcher is Validated.");
	}
}
