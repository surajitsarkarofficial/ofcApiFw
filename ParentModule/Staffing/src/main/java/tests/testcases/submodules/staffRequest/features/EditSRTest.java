package tests.testcases.submodules.staffRequest.features;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import dataproviders.StaffingDataProviders;
import io.restassured.response.Response;
import tests.testcases.submodules.staffRequest.StaffRequestBaseTest;
import tests.testhelpers.StaffingTestHelper;
import tests.testhelpers.submodules.staffRequest.features.EditSRTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;
import utils.RestUtils;

public class EditSRTest extends StaffRequestBaseTest {

	String message, status = null;
	RestUtils restUtils = new RestUtils();
	Response response;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		
		featureTest = subModuleTest.createNode("EditSRTest");
	
	}

	/**
	 * This Test will get the SR details from the grid and update the project
	 * details
	 * 
	 * @param name
	 * @param globerType
	 * @throws Exception
	 * @author pornima.chakurkar
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true, groups = ExeGroups.Smoke)
	public void validateProjectDetails(String name, String globerType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		EditSRTestHelper editSRHelper = new EditSRTestHelper(name);
		response = new StaffingTestHelper().getSRDetailsFromGrid(name, globerType);
		response = editSRHelper.getAndUpdateProjectDetails(response, name);

		validateResponseToContinueTest(response, 200, "Unable to update project details.", true);

		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), "success");
		test.log(Status.PASS, "Project Details validated");
	}

	/**
	 * This Test will update project details with null project is and verify the
	 * response
	 * 
	 * @param name
	 * @param globerType
	 * @throws Exception
	 * @author pornima.chakurkar
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true)
	public void validateProjectDetailsWithNullProjectId(String name, String globerType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		EditSRTestHelper editSRHelper = new EditSRTestHelper(name);
		response = new StaffingTestHelper().getSRDetailsFromGrid(name, globerType);
		response = editSRHelper.editProjectDetailsWithNullProjectId(response, name);
		
		validateResponseToContinueTest(response, 400, "unable to edit Project Details with Null ProjectID.", true);
	
		message = (String) restUtils.getValueFromResponse(response, "message");

		softAssert.assertEquals(message, "Please select a project.");
		test.log(Status.PASS, "Expected response " + response);
	}

	/**
	 * This Test will update project details with wrong client id and verify the
	 * response
	 * 
	 * @param name
	 * @param globerType
	 * @throws Exception
	 * @author pornima.chakurkar
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true)
	public void validateProjectDetailsWithWrongClientId(String name, String globerType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		EditSRTestHelper editSRHelper = new EditSRTestHelper(name);
		response = new StaffingTestHelper().getSRDetailsFromGrid(name, globerType);
		response = editSRHelper.editProjectDetailWwrongClientId(response, name);

		validateResponseToContinueTest(response, 400, "unable to edit ProjectDetail with wrong ClientID.", true);

		message = (String) restUtils.getValueFromResponse(response, "message");
		status = (String) restUtils.getValueFromResponse(response, "status");
		
		softAssert.assertEquals(message, "Please select a client.");
		test.log(Status.PASS, "Expected response " + response);
	}

	/**
	 * This Test will update position details and verify the response
	 * 
	 * @param name
	 * @param globerType
	 * @throws Exception
	 * @author pornima.chakurkar
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true, groups = ExeGroups.Smoke)
	public void validatePositionDetails(String name, String globerType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		EditSRTestHelper editSRHelper = new EditSRTestHelper(name);
		response = new StaffingTestHelper().getSRDetailsFromGrid(name, globerType);
		response = editSRHelper.editPositionDetails(response, name);

		validateResponseToContinueTest(response, 200, "able to edit Position Details.", true);
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), "success");
		test.log(Status.PASS, "Expected response " + response);
	}

	/**
	 * This Test will update Position details with wrong date (previous date) and
	 * verify the response
	 * 
	 * @param name
	 * @param globerType
	 * @throws Exception
	 * @author pornima.chakurkar
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true)
	public void validatePositionDetailsWithWrongEndDate(String name, String globerType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		EditSRTestHelper editSRHelper = new EditSRTestHelper(name);
		response = new StaffingTestHelper().getSRDetailsFromGrid(name, globerType);
		response = editSRHelper.editPositionDetailsWwrongEndDateData1(response, name);

		validateResponseToContinueTest(response, 400, "unable to edit Position Details with wrong End Date.", true);

		message = (String) restUtils.getValueFromResponse(response, "message");
				
		softAssert.assertEquals(message, "End Date should be later than today.");
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), "fail");
		test.log(Status.PASS, "Expected response " + response);
	}

	/**
	 * 
	 * This Test will update Position details with wrong end date (future date) and
	 * verify the response
	 * 
	 * @param name
	 * @param globerType
	 * @throws Exception
	 * @author pornima.chakurkar
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true)
	public void validatePositionDetailsWithWrongEndDateData2(String name, String globerType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		EditSRTestHelper editSRHelper = new EditSRTestHelper(name);
		response = new StaffingTestHelper().getSRDetailsFromGrid(name, globerType);
		response = editSRHelper.editPositionDetailsWwrongEndDateData2(response, name);

		validateResponseToContinueTest(response, 400, "unable to edit Position Details with wrong End Date.", true);

		message = (String) restUtils.getValueFromResponse(response, "message");
		
		softAssert.assertEquals(message, "End Date should be later than today.");
		test.log(Status.PASS, "Expected response " + response);
	}

	/**
	 * 
	 * This Test will update Position details with wrong end date and verify the
	 * response
	 * 
	 * @param name
	 * @param globerType
	 * @throws Exception
	 * @author pornima.chakurkar
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true)
	public void validatePositionDetailsWithWrongDateData1(String name, String globerType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		EditSRTestHelper editSRHelper = new EditSRTestHelper(name);
		response = new StaffingTestHelper().getSRDetailsFromGrid(name, globerType);
		response = editSRHelper.editPositionDetailsWithWrongDateData1(response, name);

		validateResponseToContinueTest(response, 400, "unable to edit Position Details With Wrong Date.", true);

		message = (String) restUtils.getValueFromResponse(response, "message");

		softAssert.assertEquals(message, "Start date should be later than today.");
		test.log(Status.PASS, "Expected response " + response);
	}

	/**
	 * 
	 * This Test will update locations and verify the response
	 * 
	 * @param name
	 * @param globerType
	 * @throws Exception
	 * @author pornima.chakurkar
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, groups = ExeGroups.Smoke, enabled = true)
	public void validateLocations(String name, String globerType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		EditSRTestHelper editSRHelper = new EditSRTestHelper(name);
		response = new StaffingTestHelper().getSRDetailsFromGrid(name, globerType);
		response = editSRHelper.editLocations(response, name);

		validateResponseToContinueTest(response, 200, "able to edit Locations successfully.", true);
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), "success");
		test.log(Status.PASS, "Expected response " + response);
	}

	/**
	 * 
	 * This Test will update Locations with As Anywhere and verify the response
	 * 
	 * @param name
	 * @param globerType
	 * @throws Exception
	 * @author pornima.chakurkar
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true)
	public void validateLocationsWithSecondLocationAsAnywhere(String name, String globerType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		EditSRTestHelper editSRHelper = new EditSRTestHelper(name);
		response = new StaffingTestHelper().getSRDetailsFromGrid(name, globerType);
		response = editSRHelper.editLocationsWithSecondLocationAsAnywhere(response, name);

		validateResponseToContinueTest(response, 200, "able to edit Locations with Second Location As Anywhere.", true);
		
		softAssert.assertEquals(restUtils.getValueFromResponse(response, "status"), "success");
		test.log(Status.PASS, "Expected response " + response);
	}

	/**
	 * This Test will update Locations without primary location and verify the
	 * response
	 * 
	 * @param name
	 * @param globerType
	 * @throws Exception
	 * @author pornima.chakurkar
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, groups = ExeGroups.Smoke, enabled = true)
	public void validateLocationsWithoutPrimaryLocation(String name, String globerType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		EditSRTestHelper editSRHelper = new EditSRTestHelper(name);
		response = new StaffingTestHelper().getSRDetailsFromGrid(name, globerType);
		response = editSRHelper.editLocationsWithoutPrimaryLocation(response, name);

		validateResponseToContinueTest(response, 400, "unable to edit Locations without Primary Location.", true);

		message = (String) restUtils.getValueFromResponse(response, "message");

		softAssert.assertEquals(message, "Please select a valid Primary Location.");
		test.log(Status.PASS, "Excpected response " + response);
	}

	/**
	 * This Test will update Locations with primary location as Anywhere value and
	 * verify the response
	 * 
	 * @param name
	 * @param globerType
	 * @throws Exception
	 * @author pornima.chakurkar
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true)
	public void validateLocationsWithPrimayLocationAsAnywhereValue(String name, String globerType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		EditSRTestHelper editSRHelper = new EditSRTestHelper(name);
		response = new StaffingTestHelper().getSRDetailsFromGrid(name, globerType);
		response = editSRHelper.editLocationsWithPrimayLocationAsAnywhereValue(response, name);

		validateResponseToContinueTest(response, 400, "unable to edit Locations with Primay Location As Anywhere Value.", true);

		message = (String) restUtils.getValueFromResponse(response, "message");
		
		softAssert.assertEquals(message, "Please select a valid Primary Location.");
		test.log(Status.PASS, "Expected response " + response);
	}

	/**
	 * This Test will update Locations with same value and verify the response
	 * 
	 * @param name
	 * @param globerType
	 * @throws Exception
	 * @author pornima.chakurkar
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true)
	public void validateLocationsWithBothLocationAsSameValue(String name, String globerType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		EditSRTestHelper editSRHelper = new EditSRTestHelper(name);
		response = new StaffingTestHelper().getSRDetailsFromGrid(name, globerType);
		response = editSRHelper.editLocationsWithBothLocationAsSameValue(response, name);

		validateResponseToContinueTest(response, 400, "unable to edit Locations with both Location As Same Value.", true);

		message = (String) restUtils.getValueFromResponse(response, "message");

		softAssert.assertEquals(message, "Primary and Secondary location can't be same.");
		test.log(Status.PASS, "Expected response " + response);
	}

	/**
	 * This Test will update Locations with Anywhere value and verify the response
	 * 
	 * @param name
	 * @param globerType
	 * @throws Exception
	 * @author pornima.chakurkar
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true)
	public void validateLocationsWithBothHavingAnywhereValue(String name, String globerType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		EditSRTestHelper editSRHelper = new EditSRTestHelper(name);
		response = new StaffingTestHelper().getSRDetailsFromGrid(name, globerType);
		response = editSRHelper.editLocationsWithBothHavingAnywhereValue(response, name);

		validateResponseToContinueTest(response, 400, "unable to edit Locations with both Having Anywhere Value.", true);

		message = (String) restUtils.getValueFromResponse(response, "message");
		
		softAssert.assertEquals(message,
				"When primary location is anywhere then secondary location can't be selected.");
		test.log(Status.PASS, "Expected response " + response);
	}

	/**
	 * This Test will update Load field and verify the response
	 * 
	 * @param name
	 * @param globerType
	 * @throws Exception
	 * @author pornima.chakurkar
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, groups = ExeGroups.Smoke, enabled = true)
	public void validateLoadFeild(String name, String globerType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		EditSRTestHelper editSRHelper = new EditSRTestHelper(name);
		response = new StaffingTestHelper().getSRDetailsFromGrid(name, globerType);
		response = editSRHelper.editLoadField(response, name);

		validateResponseToContinueTest(response, 400, "unable to edit SR Locations Details without load.", true);
		
		message = (String) restUtils.getValueFromResponse(response, "message");

		softAssert.assertEquals(message, "Load field is required.");
		test.log(Status.PASS, "Expected response " + response);
	}

	/**
	 * This Test will update update travel period and verify the response
	 * 
	 * @param name
	 * @param globerType
	 * @throws Exception
	 * @author pornima.chakurkar
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, groups = ExeGroups.Smoke, enabled = true)
	public void validateTravelPeriodLength(String name, String globerType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		EditSRTestHelper editSRHelper = new EditSRTestHelper(name);
		response = new StaffingTestHelper().getSRDetailsFromGrid(name, globerType);
		response = editSRHelper.editTravelPeriodLength(response, name);

		validateResponseToContinueTest(response, 400, "unable to edit travel period.", true);
		
		message = (String) restUtils.getValueFromResponse(response, "message");

		softAssert.assertEquals(message, "Estimated Travel Period field should be an integer.");
		test.log(Status.PASS, "Expected response " + response);
	}

	/**
	 * This Test will update Edit SR skills and verify the response
	 * 
	 * @param name
	 * @param globerType
	 * @throws Exception
	 * @author pornima.chakurkar
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true)
	public void validateEditSRSkills(String name, String globerType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		EditSRTestHelper editSRHelper = new EditSRTestHelper(name);
		response = new StaffingTestHelper().getSRDetailsFromGrid(name, globerType);
		response = editSRHelper.editSRSkills(response, name);

		validateResponseToContinueTest(response, 200, "SR skills edited successfully.", true);
		status = (String) restUtils.getValueFromResponse(response, "status");

		softAssert.assertEquals(status, "success","Failed to edit SR skills.!");
		test.log(Status.PASS, "Expected response " + response);
	}

	/**
	 * This Test will get Statement of work for project and verify response
	 * 
	 * @param name
	 * @param globerType
	 * @throws Exception
	 * @author pornima.chakurkar
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true)
	public void validateEditbutton(String name, String globerType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		EditSRTestHelper editSRHelper = new EditSRTestHelper(name);
		response = new StaffingTestHelper().getSRDetailsFromGrid(name, globerType);
		response = editSRHelper.editButton(response, name);

		validateResponseToContinueTest(response, 200, "able to EDIT for SR Number.", true);
		status = (String) restUtils.getValueFromResponse(response, "status");

		softAssert.assertEquals(status, "success", "Failed to Edit SR..!");
		softAssert.assertTrue(editSRHelper.getEditButtonStatus(), "Failing to EDIT Sr.");
		softAssert.assertAll();
		test.log(Status.PASS, "Expected response " + response);
	}

	/**
	 * This Test will get SR history and verify response
	 * 
	 * @param name
	 * @param globerType
	 * @throws Exception
	 * @author pornima.chakurkar
	 */
	@Test(dataProvider = "usersWithAllRoles", dataProviderClass = StaffingDataProviders.class, enabled = true, groups = ExeGroups.Smoke)
	public void validateEditSRHistory(String name, String globerType) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		EditSRTestHelper editSRHelper = new EditSRTestHelper(name);
		response = new StaffingTestHelper().getSRDetailsFromGrid(name, globerType);
		response = editSRHelper.editSRHistory(response, name);

		validateResponseToContinueTest(response, 200, "able to edit SR history.", true);

		softAssert.assertEquals(status, "success", message);
		test.log(Status.PASS, "Expected response " + response);
	}
}
