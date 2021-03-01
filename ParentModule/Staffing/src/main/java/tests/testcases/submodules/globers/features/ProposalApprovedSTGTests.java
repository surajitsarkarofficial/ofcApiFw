package tests.testcases.submodules.globers.features;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import database.GlowDBHelper;
import dataproviders.submodules.staffRequest.features.globalstaffrequest.GlobalStaffRequestDataProvider;
import io.restassured.response.Response;
import tests.testcases.submodules.globers.GlobersBaseTest;
import tests.testhelpers.StaffingTestHelper;
import tests.testhelpers.submodules.globers.features.ProposalApprovedTesHelper;
import tests.testhelpers.submodules.staffRequest.features.globalstaffrequest.QuickSuggestAndAssignTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;
import utils.RestUtils;

/**
 * 
 * @author shadab.waikar
 *
 */
public class ProposalApprovedSTGTests extends GlobersBaseTest{

	RestUtils utils;
	ArrayList<String> expectedStgCatStatus = new ArrayList<>(Arrays.asList("Hired", "Fit Interview"));
	
	QuickSuggestAndAssignTestHelper suggestAndAssign;
	GlowDBHelper glowDbHelper;
	ProposalApprovedTesHelper stgTestHelper;
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("All Globers-STG");
	}
	
	/**
	 * This test will validate staffPlanfor STGs
	 * @param username
	 * @param globerType
	 * @throws Exception
	 */
	@Test(dataProvider = "stgAssignmnentAccessRoleUsers", dataProviderClass = GlobalStaffRequestDataProvider.class, enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void validateQuickSuggestProposalApprovedGlober(String username, String globerType) throws Exception {
		glowDbHelper = new GlowDBHelper();
		suggestAndAssign = new QuickSuggestAndAssignTestHelper(username);
		utils = new RestUtils();
		stgTestHelper = new ProposalApprovedTesHelper(username);
		Response response = new StaffingTestHelper().getSRDetailsFromGrid(username, globerType);
		validateResponseToContinueTest(response, 200, "Unable to load SR Grid.", true);
		
		Response suggestSTGResposne = stgTestHelper.suggestProposalApprovedSTG(response, username, globerType);
		validateResponseToContinueTest(suggestSTGResposne, 201, "Unable to suggest STGs", true);
		
		String status = utils.getValueFromResponse(suggestSTGResposne, "status").toString();
		String msg = utils.getValueFromResponse(suggestSTGResposne, "message").toString();
		
		Assert.assertEquals(status,"success","Failed in creating STG plan..Error message : "+msg);
		test.log(Status.PASS, "STGs plan is created successfully!");
		
		suggestAndAssign.updatePlanStatus(utils.getValueFromResponse(suggestSTGResposne, "details[0].staffPlanId").toString());
	}
	
	/**
	 * This test will validate filter results of all STGs
	 * @param username
	 * @param globerType
	 * @throws Exception
	 */
	@Test(dataProvider = "stgAssignmnentAccessRoleUsers", dataProviderClass = GlobalStaffRequestDataProvider.class, enabled = true, groups = {ExeGroups.Sanity,ExeGroups.Regression,"Glow-II"})
	public void verifyProposalApprovedSTGsByFilter(String username, String globerType) throws Exception {
		int i = new Random().nextInt(15);
		utils = new RestUtils();
		stgTestHelper = new ProposalApprovedTesHelper(username);
		
		Response filterStgsResponse = stgTestHelper.filterProposalApprovedSTG(username, globerType);
		validateResponseToContinueTest(filterStgsResponse, 200, "Unable to get STGs in filter results.", true);
		
		String status = utils.getValueFromResponse(filterStgsResponse, "status").toString();
		Assert.assertEquals(status,"success","Issue in filter API");
		
		String stgCatsStatus = utils.getValueFromResponse(filterStgsResponse, "details.globerDTOList["+i+"].catsStatus").toString();
		String stgCatsType = utils.getValueFromResponse(filterStgsResponse, "details.globerDTOList["+i+"].type").toString();
		
		if(globerType.equalsIgnoreCase("Proposal Approved"))
			Assert.assertTrue(expectedStgCatStatus.contains(stgCatsStatus),expectedStgCatStatus+" doesn't contain "+stgCatsStatus);
		Assert.assertEquals(stgCatsType,globerType,"Expected Stg type:"+globerType+"Actual Stg type: "+stgCatsType);
		
	}
}


