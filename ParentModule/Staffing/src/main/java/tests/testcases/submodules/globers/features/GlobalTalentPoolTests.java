package tests.testcases.submodules.globers.features;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import dataproviders.submodules.staffRequest.features.CreateNewPositionDataProviders;
import io.restassured.response.Response;
import tests.testcases.submodules.globers.GlobersBaseTest;
import tests.testhelpers.submodules.globers.features.GlobalTalentPoolTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;
import utils.RestUtils;

public class GlobalTalentPoolTests extends GlobersBaseTest{

	RestUtils restUtils = new RestUtils();
	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("GlobalTalentPoolTests");
	}

	/**
	 * This method will verify total glober talent pool count
	 * @param userName
	 * @param userRole
	 * @throws Exception
	 */

	@Test(dataProvider = "CreateSRUsingValidUsers", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, groups = {ExeGroups.Regression,ExeGroups.Sanity})
	public void getGlobalTalentPoolCount(String userName,String userRole) throws Exception {
		int globalTPCount = 2000;
		GlobalTalentPoolTestHelper globalTalentPoolTestHelper = new GlobalTalentPoolTestHelper(userName);
		Response globalTalentPoolCountAPIResponse = globalTalentPoolTestHelper.getGlobalTalentPoolCountRespone(userName);
		validateResponseToContinueTest(globalTalentPoolCountAPIResponse, 200, "Global Talent pool API is not working", true);

		int globalTPCountFromAPI = (int) restUtils.getValueFromResponse(globalTalentPoolCountAPIResponse, "details.totalCount");
		assertTrue(globalTPCountFromAPI > globalTPCount, "Global Talent pool response is not greater than mentioned TP count");
		test.log(Status.PASS, "Glober Talent Pool count is same from API and DB");
	}
}