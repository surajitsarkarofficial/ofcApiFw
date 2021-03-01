package tests.testcases.submodules.globers.features;

import java.io.IOException;
import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import dataproviders.StaffingDataProviders;
import io.restassured.response.Response;
import tests.testcases.submodules.globers.GlobersBaseTest;
import tests.testhelpers.submodules.globers.features.TdcTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;
import utils.RestUtils;
import utils.StaffingEnum.ExecutionSuiteGroups.StaffingGroup;

public class TdcTest extends GlobersBaseTest {
	String statusCode, status = null;
	RestUtils restUtils = new RestUtils();
	Response response;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("TdcTest");
	}

	/**
	 * This test will check TDC Filter on Talent Pool
	 * 
	 * @param name
	 * @param globerType
	 * @throws Exception
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true, groups = {
			StaffingGroup.services, StaffingGroup.stg,ExeGroups.Regression,ExeGroups.Sanity })
	public void validateTdcFilter(String name, String globerType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		TdcTestHelper tdcTestHelper = new TdcTestHelper(name);
		response = tdcTestHelper.getTdcFilter(name, response);

		validateResponseToContinueTest(response, 200, "Unable to get TDC Filters.", true);

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), "success",
				"API is not giving 200 response");
		softAssert.assertAll();
		test.log(Status.PASS, "Validated TDC Filter on Talent Pool");
	}

	/**
	 * This test will check TDC value on Talent Pool
	 * 
	 * @param name
	 * @param globerType
	 * @throws Exception
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true, groups = {
			StaffingGroup.services, StaffingGroup.stg,ExeGroups.Regression })
	public void getAndVerifyTDCValue(String name, String globerType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		TdcTestHelper tdcTestHelper = new TdcTestHelper(name);
		response = tdcTestHelper.getTdcValue(name, response);

		validateResponseToContinueTest(response, 200, "Unable to get TDC Values.", true);

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), "success",
				"API is not giving 200 response");
		softAssert.assertAll();
		test.log(Status.PASS, "Validated TDC Value on Talent Pool");
	}

	/**
	 * This test will check TDC History for each Glober
	 * 
	 * @param name
	 * @param globerType
	 * @throws Exception
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true, groups = {
			StaffingGroup.services, StaffingGroup.stg,ExeGroups.Regression })
	public void getAndVerifyTDCHistory(String name, String globerType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		TdcTestHelper tdcTestHelper = new TdcTestHelper(name);
		response = tdcTestHelper.getTdcHistoryDetails(response);

		validateResponseToContinueTest(response, 200, "Unable to get TDC History.", true);

		status = restUtils.getValueFromResponse(response, "status").toString();

		softAssert.assertEquals(status, "success", "API is not giving 200 response");
		softAssert.assertAll();
		test.log(Status.PASS, "Validated TDC History for each glober");
	}

	/**
	 * This test will check TDC for each Glober based on their location
	 * 
	 * @param name
	 * @param globerType
	 * @throws Exception
	 * @throws IOException
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true, groups = {
			StaffingGroup.services, StaffingGroup.stg,ExeGroups.Regression,ExeGroups.Sanity })
	public void verifyTDCofGloberBasedOnLocation(String name, String globerType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		Response responseTdcGlober, responseAllGlober;
		TdcTestHelper tdcTestHelper = new TdcTestHelper(name);

		responseTdcGlober = tdcTestHelper.getTdcofGloberBasedOnLocation(response, name);
		responseAllGlober = tdcTestHelper.getAllGlobersBasedOnLocation(responseTdcGlober, name);

		String location = String
				.valueOf(restUtils.getValueFromResponse(responseTdcGlober, "details.globerDTOList[0].location"));
		String tdcName = String
				.valueOf(restUtils.getValueFromResponse(responseAllGlober, "details.globerDTOList[0].tdcName"));

		HashMap<String, String> locationMap = tdcTestHelper.readExcel();
		softAssert.assertEquals(locationMap.get(location), tdcName, "TDC name based on Location is not matching");
		softAssert.assertAll();
		test.log(Status.PASS, "Validated TDC for each Glober based on their location");
	}
}
