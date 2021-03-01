package tests.testcases.submodules.globers.features;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import dataproviders.submodules.reports.features.handlerandhandlerteam.HandlerAndHandlerTeamDataProvider;
import io.restassured.response.Response;
import tests.testcases.submodules.globers.GlobersBaseTest;
import tests.testhelpers.submodules.globers.features.TBDGloberTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

public class TBDGloberTest extends GlobersBaseTest{

	TBDGloberTestHelper tbdGloberHelper;
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("TBD Glober");
	}
	
	/**
	 * This Test will validate tdb Glober while suggesting via PM and TD
	 * 
	 * @param userName
	 * @param type
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "changeHandlerViaPMTDRoleUsers", dataProviderClass = HandlerAndHandlerTeamDataProvider.class, enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void verifyTbdGloberWhileSuggestingWithPMTD(String userName,String type) throws Exception {
		tbdGloberHelper = new TBDGloberTestHelper(userName);

		Response tbdGloberResponse = tbdGloberHelper.suggestTBDGloberWithPMTD(userName);
		validateResponseToContinueTest(tbdGloberResponse, 200, "Issue with searching TBD glober", true);
		List<String> globers = tbdGloberResponse.jsonPath().get("globers");
		Assert.assertEquals(globers.size(), 0, "Expected Count : 0 and Actual Count : "+globers.size());
		test.log(Status.PASS, "TBD Glober Search is Validated.");
	}
	
	/**
	 * This Test will validate tdb Glober visible in dynamic search via PMTD
	 * 
	 * @param userName
	 * @param type
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "changeHandlerViaPMTDRoleUsers", dataProviderClass = HandlerAndHandlerTeamDataProvider.class, enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void verifyTbdGloberInDynamicSerachViaPMTD(String userName,String type) throws Exception {
		tbdGloberHelper = new TBDGloberTestHelper(userName);
		Response tbdGloberDynamcSearchResponse = tbdGloberHelper.validateTbdGloberInDynamicSearch();
		validateResponseToContinueTest(tbdGloberDynamcSearchResponse, 200, "Issue in Dynamic Search", true);
		List<String> globerIds = tbdGloberDynamcSearchResponse.jsonPath().get("results.glober.id");
		Assert.assertFalse(globerIds.contains(tbdGloberHelper.getActiveTbdGlober()),"TBD Glober is present in Dynamic Search.");
		test.log(Status.PASS, "TBD Glober in Dynamic search is Validated.");
	}
	
	/**
	 * This Test will validate tdb Glober visible in reverse search via PMTD
	 * 
	 * @param userName
	 * @param type
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "changeHandlerViaPMTDRoleUsers", dataProviderClass = HandlerAndHandlerTeamDataProvider.class, enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void verifyTbdGloberInReverseSeacrhViaPMTD(String userName,String type) throws Exception {
		tbdGloberHelper = new TBDGloberTestHelper(userName);
		Response reverseSearchResponse = tbdGloberHelper.validateTbdGloberResultInReverseSearch();
		validateResponseToContinueTest(reverseSearchResponse, 200, "Issue in Reverse Search", true);
		List<String> globerData = reverseSearchResponse.jsonPath().get("details");
		Assert.assertEquals(globerData.size(), 0, "Expected Count : 0 and Actual Count : "+globerData.size());
		test.log(Status.PASS, "TBD Glober in Reverse Search is Validated.");	
	}
	
	/**
	 * This Test will validate tdb Glober visible in search result in timeline page via PMTD
	 * 
	 * @param userName
	 * @param type
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "changeHandlerViaPMTDRoleUsers", dataProviderClass = HandlerAndHandlerTeamDataProvider.class, enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void verifyTbdGloberInTimelinePageViaPMTD(String userName,String type) throws Exception {
		tbdGloberHelper = new TBDGloberTestHelper(userName);
		Response tbdGloberTimelineSearchResponse = tbdGloberHelper.validateTbdGloberIntimelineSearch(userName);
		validateResponseToContinueTest(tbdGloberTimelineSearchResponse, 200, "Issue with Timeline searching TBD glober", true);
		List<String> globers = tbdGloberTimelineSearchResponse.jsonPath().get("globers");
		Assert.assertEquals(globers.size(), 0, "Expected Count : 0 and Actual Count : "+globers.size());
		test.log(Status.PASS, "TBD Glober Search Via Timeline Search is Validated.");
	}
	
	/**
	 * This Test will validate warning message while suggesting Tbd Glober via suggesting From srGrid
	 * 
	 * @param userName
	 * @param type
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "changeHandlerViaSARARoleUsers", dataProviderClass = HandlerAndHandlerTeamDataProvider.class, enabled = true, groups = {ExeGroups.Regression,"Glow-II"})
	public void verifySuggestingTbdGloberViaSrGridWithSARA(String userName,String type) throws Exception {
		tbdGloberHelper = new TBDGloberTestHelper(userName);
		Response tbdGloberSuggestResponse = tbdGloberHelper.suggestTbdGloberFromSrGrid();
		validateResponseToContinueTest(tbdGloberSuggestResponse, 200, "Issue with TBD Glober", true);
		String msg = restUtils.getValueFromResponse(tbdGloberSuggestResponse, "message").toString();
		Assert.assertEquals(msg, "You are trying to suggest a TBD Glober!", "Expected msg : You are trying to suggest a TBD Glober! and Actual Count : "+msg);
		test.log(Status.PASS, "Warning Message while suggesting TBD Globeris Validated.");
	}
	
	/**
	 * This Test will validate warning message while suggesting Tbd Glober via suggesting From TimelinePage
	 * 
	 * @param userName
	 * @param type
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "changeHandlerViaSARARoleUsers", dataProviderClass = HandlerAndHandlerTeamDataProvider.class, enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void verifyTbdGloberWarningMsgWhileSuggestingFromTimelinePage(String userName,String type) throws Exception {
		tbdGloberHelper = new TBDGloberTestHelper(userName);
		Response tbdGloberSuggestResponse = tbdGloberHelper.suggestTbdGloberFromSrGrid();
		validateResponseToContinueTest(tbdGloberSuggestResponse, 200, "Issue with TBD Glober", true);
		String msg = restUtils.getValueFromResponse(tbdGloberSuggestResponse, "message").toString();
		Assert.assertEquals(msg, "You are trying to suggest a TBD Glober!", "Expected msg : You are trying to suggest a TBD Glober! and Actual Count : "+msg);
		test.log(Status.PASS, "Warning Message while suggesting TBD Glober From Timeline Page is Validated.");
	}
	
	/**
	 * This Test will validate warning message while suggesting Tbd Glober via suggesting From Glober Details Page
	 * 
	 * @param userName
	 * @param type
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "changeHandlerViaSARARoleUsers", dataProviderClass = HandlerAndHandlerTeamDataProvider.class, enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void verifyTbdGloberWarningMshWhileSuggestingFromGloberDetailsPage(String userName,String type) throws Exception {
		tbdGloberHelper = new TBDGloberTestHelper(userName);
		Response tbdGloberSuggestResponse = tbdGloberHelper.suggestTbdGloberFromSrGrid();
		validateResponseToContinueTest(tbdGloberSuggestResponse, 200, "Issue with TBD Glober", true);
		String msg = restUtils.getValueFromResponse(tbdGloberSuggestResponse, "message").toString();
		Assert.assertEquals(msg, "You are trying to suggest a TBD Glober!", "Expected msg : You are trying to suggest a TBD Glober! and Actual Count : "+msg);
		test.log(Status.PASS, "Warning Message while suggesting TBD Glober From Timeline Page is Validated.");
	}
	
	/**
	 * This Test will verify weather glober mark as TBD glober via SA and RA
	 * 
	 * @param userName
	 * @param type
	 * @throws Exception
	 * @author shadab.waikar
	 */
	@Test(dataProvider = "changeHandlerViaSARARoleUsers", dataProviderClass = HandlerAndHandlerTeamDataProvider.class, enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void verifyMarkingGloberAsTbd(String userName,String type) throws Exception {
		tbdGloberHelper = new TBDGloberTestHelper(userName);
		Response markGloberResponse = tbdGloberHelper.markGloberAsTbd(userName);
		validateResponseToContinueTest(markGloberResponse, 201, "Unable to mark Glober as TBD.", true);
		String msg = restUtils.getValueFromResponse(markGloberResponse, "message").toString();
		tbdGloberHelper.updateTbdGloberStatus();
		Assert.assertEquals(msg, "Glober status saved successfully.", String.format("Actual msg : %s and Expected Msg : Glober status saved successfully.",msg));
		test.log(Status.PASS, "Active Glober is Marked as TBD Glober Successfully.");
	}
}
