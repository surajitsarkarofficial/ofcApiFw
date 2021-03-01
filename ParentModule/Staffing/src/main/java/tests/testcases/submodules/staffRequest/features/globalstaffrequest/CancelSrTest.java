package tests.testcases.submodules.staffRequest.features.globalstaffrequest;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.google.common.base.CharMatcher;

import database.submodules.staffRequest.features.globalstaffrequest.CancelSrDBHelper;
import dataproviders.submodules.reports.features.handlerandhandlerteam.HandlerAndHandlerTeamDataProvider;
import dataproviders.submodules.staffRequest.features.globalstaffrequest.GlobalStaffRequestDataProvider;
import io.restassured.response.Response;
import tests.testcases.submodules.staffRequest.StaffRequestBaseTest;
import tests.testhelpers.StaffingTestHelper;
import tests.testhelpers.submodules.staffRequest.features.globalstaffrequest.CancelSrTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

public class CancelSrTest extends StaffRequestBaseTest {

	CancelSrTestHelper cancelSr;
	CancelSrDBHelper cancelSrDb;
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("GlobalStaffRequest");
	}
	
	/**
	 * This Test will perform cancel SR via Global Talent Pool
	 * 
	 * @param username
	 * @param globerType
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "globerAssignmnentAccessRoleUsers", dataProviderClass = GlobalStaffRequestDataProvider.class, enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void verifyCancelSrViaGlobalTP(String username,String globerType) throws Exception{
		cancelSr = new CancelSrTestHelper(username);
		cancelSrDb = new CancelSrDBHelper();
		
		Response response = new StaffingTestHelper().getSRDetailsFromGrid(username, globerType);
		validateResponseToContinueTest(response, 200, "Unable to load SR Grid.", true);
		
		Response cancelSrResponse = cancelSr.cancelStaffRequest(response);
		
		if(username.equalsIgnoreCase("anand.datir")) {
			validateResponseToContinueTest(cancelSrResponse, 400, "Issue User Access...User :"+username, true);
			test.log(Status.PASS, "User Role Access is Validated.");
		}
		else {
			validateResponseToContinueTest(cancelSrResponse, 200, "Unable to cancel staff request", true);
			
			String msg = restUtils.getValueFromResponse(cancelSrResponse, "message").toString();
			String actualSrFromResponse = CharMatcher.inRange('0', '9').retainFrom(msg);
			String srState = cancelSrDb.getPositionStateFromPosition(actualSrFromResponse);
			
			Assert.assertTrue(msg.contains("cancelled successfully"), String.format("Actual msg : %s doesn't contain Expected msg : cancelled successfully", msg));
			Assert.assertEquals(srState,"Cancelled", String.format("Actual state : %s and Expected msg : Cancelled", srState));
			test.log(Status.PASS, "Successfully cancelled Staff Request.");
			
			cancelSr.updatePositionState(actualSrFromResponse);
		}	
	}
	
	/**
	 * This Test will perform cancel SR via My Talent Pool with SA and RA
	 * 
	 * @param username
	 * @param globerType
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "changeHandlerViaSARARoleUsers", dataProviderClass = HandlerAndHandlerTeamDataProvider.class, enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void verifyCancelSrViaMyTP(String username,String globerType) throws Exception {
		cancelSr = new CancelSrTestHelper(username);
		cancelSrDb = new CancelSrDBHelper();
		
		Response response = cancelSr.getSRDetailsFromMySrGrid(username, globerType);
		validateResponseToContinueTest(response, 200, "Unable to load SR Grid.", true);
		
		Response cancelSrResponse = cancelSr.cancelStaffRequest(response);
		validateResponseToContinueTest(cancelSrResponse, 200, "Unable to cancel staff request", true);
		
		String msg = restUtils.getValueFromResponse(cancelSrResponse, "message").toString();
		String actualSrFromResponse = CharMatcher.inRange('0', '9').retainFrom(msg);
		String srState = cancelSrDb.getPositionStateFromPosition(actualSrFromResponse);
		
		Assert.assertTrue(msg.contains("cancelled successfully"), String.format("Actual msg : %s doesn't contain Expected msg : cancelled successfully", msg));
		Assert.assertEquals(srState,"Cancelled", String.format("Actual state : %s and Expected msg : Cancelled", srState));
		test.log(Status.PASS, "Successfully cancelled Staff Request.");
		
		cancelSr.updatePositionState(actualSrFromResponse);
	}
	
	/**
	 * This Test will verify cancel Sr reasons
	 * 
	 * @param username
	 * @param globerType
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "changeHandlerViaSARARoleUsers", dataProviderClass = HandlerAndHandlerTeamDataProvider.class, enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void verifyCancelSrReasons(String username,String globerType) throws Exception {
		cancelSr = new CancelSrTestHelper(username);
		cancelSrDb = new CancelSrDBHelper();
		
		Response response = new StaffingTestHelper().getSRDetailsFromGrid(username, globerType);
		validateResponseToContinueTest(response, 200, "Unable to load SR Grid.", true);
		
		Response cancelSrReasons =  cancelSr.getCancelSrReasons();
		validateResponseToContinueTest(cancelSrReasons, 200, "Unable to load SR Grid.", true);
		
		ArrayList<String> reasonsFromDb = cancelSr.getReasonsFromDb(cancelSrReasons);
		ArrayList<String> reasonsFromResponse = cancelSrReasons.jsonPath().get("details.name");
		
		Assert.assertEquals(reasonsFromResponse,reasonsFromDb, String.format("Actual reasons : %s and Expected reasons : %s", reasonsFromResponse,reasonsFromDb));
		test.log(Status.PASS, "Cancel Staff Request Reasons are Validated.");
	}
}
