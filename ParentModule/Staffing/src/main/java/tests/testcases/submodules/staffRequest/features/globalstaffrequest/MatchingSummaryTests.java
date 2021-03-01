package tests.testcases.submodules.staffRequest.features.globalstaffrequest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import dataproviders.submodules.staffRequest.features.CreateNewPositionDataProviders;
import io.restassured.response.Response;
import tests.testcases.submodules.staffRequest.StaffRequestBaseTest;
import tests.testhelpers.submodules.staffRequest.features.globalstaffrequest.MatchingSummaryTestHelper;
import utils.RestUtils;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * 
 * @author akshata.dongare
 *
 */
public class MatchingSummaryTests extends StaffRequestBaseTest {
RestUtils restUtils = new RestUtils();
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("Matching Summary");
	}
	
	/**
	 * This method will verify available/matching glober column name
	 * @param userName
	 * @throws Exception
	 */
	@Test(dataProvider = "CreateSRUsingValidUsers", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, groups = {ExeGroups.Regression, ExeGroups.Sanity}, priority = 0)
	public void gridColumnName(String userName, String userRole) throws Exception {
		MatchingSummaryTestHelper matchingSummaryTestHelper = new MatchingSummaryTestHelper(userName);
		Response gridColumnNameResponse = matchingSummaryTestHelper.gridColumnName();
		validateResponseToContinueTest(gridColumnNameResponse, 200, "Grid column name API call was not successful", true);

		String availableMatchingGloberColNameJsonPath = null;
		if(userRole == "staffingRolUser" || userRole == "recruitingRolUser") {
			availableMatchingGloberColNameJsonPath = "details.[33].name";
		}else {
			availableMatchingGloberColNameJsonPath = "details.[32].name";
		}
		
		String actualAvailableMatchingGloberColName = (String) restUtils.getValueFromResponse(gridColumnNameResponse, availableMatchingGloberColNameJsonPath);
		String expectedAvailableMatchingGloberColName = "Available/ Matching Globers,";
		assertTrue(actualAvailableMatchingGloberColName.contains(expectedAvailableMatchingGloberColName), "Available and matching glober column name is different");
		test.log(Status.PASS, "View Fit interview feedback is successful ");
	}
	
	/**
	 * This method will verify SR with NA record
	 * @param userName
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "CreateSRUsingValidUsers", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, groups = {ExeGroups.Regression, ExeGroups.Sanity}, priority = 0)
	public void checkNAGridSR(String userName, String userRole) throws Exception {
		MatchingSummaryTestHelper matchingSummaryTestHelper = new MatchingSummaryTestHelper(userName);
		String gridNASr = matchingSummaryTestHelper.checkNAResponse(userName);
		
		if(gridNASr==null) {
			Reporter.log("Don't have SR with NA record in the grid", true);
		}else {
			String gridNASrDb = matchingSummaryTestHelper.gridNASrFromDb(gridNASr);
			assertEquals(gridNASrDb, null, "Grid SR having NA is existed in the table");
		}
	}
}
