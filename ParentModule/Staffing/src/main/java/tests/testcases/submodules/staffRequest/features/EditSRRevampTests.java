package tests.testcases.submodules.staffRequest.features;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import dataproviders.StaffingDataProviders;
import dataproviders.submodules.staffRequest.features.CreateNewPositionDataProviders;
import io.restassured.response.Response;
import tests.testcases.submodules.staffRequest.StaffRequestBaseTest;
import tests.testhelpers.submodules.staffRequest.StaffRequestTestHelper;
import tests.testhelpers.submodules.staffRequest.features.EditSRRevampTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;
import utils.RestUtils;

/**
 * 
 * @author akshata.dongare
 *
 */
public class EditSRRevampTests extends StaffRequestBaseTest {
	RestUtils restUtils = new RestUtils();
	StaffRequestTestHelper staffRequestTestHelper;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("Edit Sr Revamp");
	}
	
	/**
	 * This test will verify edit sr for project details using new edit revamp
	 * @param userName
	 * @param userRole
	 * @throws Exception
	 */
	
	@Test(dataProvider = "CreateSRUsingValidUsers", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, priority = 0, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void editSRProjectDetails(String userName, String userRole) throws Exception {
		EditSRRevampTestHelper editSRRevampTestHelper = new EditSRRevampTestHelper(userName);
		String globerId = editSRRevampTestHelper.getGloberIdFromGloberName(userName);
		Response srGridResponse = editSRRevampTestHelper.getSRDetailsFromGrid(globerId);
		validateResponseToContinueTest(srGridResponse, 200, "SR Grid API call was not successful", true);
		
		Response editSRResponse = editSRRevampTestHelper.updateEditSRProjectDetails(srGridResponse, userName);
		validateResponseToContinueTest(editSRResponse, 200, "Edit SR project details call was not successful", true);
		assertEquals(restUtils.getValueFromResponse(editSRResponse, "status"), "success", "Status is not success");
		test.log(Status.PASS, "Edit Sr revamp for project details is successful ");
	}

	/**
	 * This test will verify response for varibale load position
	 * @param userName
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "userListWithTL", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, groups = {ExeGroups.Regression, ExeGroups.Sanity}, priority = 0)
	public void verifyEditSrWithVariableLoad(String user) throws Exception {
		EditSRRevampTestHelper editSRRevampTestHelper = new EditSRRevampTestHelper(user);
		String globerId = editSRRevampTestHelper.getGloberIdFromGloberName(user);
		Response srGridResponse = editSRRevampTestHelper.getSRDetailsFromGrid(globerId);
		validateResponseToContinueTest(srGridResponse, 200, "SR Grid API call was not successful", true);
		
		Response editSRResponse = editSRRevampTestHelper.updateEditSRProjectDetailsWithVariableLoad(srGridResponse, user);
		validateResponseToContinueTest(editSRResponse, 200, "Edit SR project details call was not successful", true);
		assertEquals(restUtils.getValueFromResponse(editSRResponse, "status"), "success", "Status is not success");
		test.log(Status.PASS, "Edit Sr revamp for project details is successful ");
	}

	/**
	 * This test will verify client id from the response by providing different client id which is not associated with that position
	 * @param userName
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "CreateSRUsingValidUsers", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, groups = {ExeGroups.Regression}, priority = 1)
	public void verifyEditSrWithDifferentClientId(String userName, String userRole) throws Exception {
		EditSRRevampTestHelper editSRRevampTestHelper = new EditSRRevampTestHelper(userName);
		String globerId = editSRRevampTestHelper.getGloberIdFromGloberName(userName);
		Response srGridResponse = editSRRevampTestHelper.getSRDetailsFromGrid(globerId);
		validateResponseToContinueTest(srGridResponse, 200, "SR Grid API call was not successful", true);
		
		List<String> clientNames = editSRRevampTestHelper.updateEditSRProjectDetailsWithDifferentClientId(srGridResponse, userName);
		assertNotEquals(clientNames.get(0), clientNames.get(1), "Client name from response and payload is same");
		test.log(Status.PASS, "Verification of client id not associated with the position id is successful ");
	}

	/**
	 * This test will verify project id from the response by providing different project id which is not associated with that position
	 * @param userName
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "CreateSRUsingValidUsers", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, groups = {ExeGroups.Regression}, priority = 1)
	public void verifyEditSrWithDifferentProjectId(String userName, String userRole) throws Exception {
		EditSRRevampTestHelper editSRRevampTestHelper = new EditSRRevampTestHelper(userName);
		String globerId = editSRRevampTestHelper.getGloberIdFromGloberName(userName);
		Response srGridResponse = editSRRevampTestHelper.getSRDetailsFromGrid(globerId);
		validateResponseToContinueTest(srGridResponse, 200, "SR Grid API call was not successful", true);
		
		List<String> projectNames = editSRRevampTestHelper.updateEditSRProjectDetailsWithDifferentProjectId(srGridResponse, userName);
		assertNotEquals(projectNames.get(0), projectNames.get(1), "Project name from response and payload is same");
		test.log(Status.PASS, "Verification of project id not associated with the position id is successful ");
	}

	/**
	 * This test will verify response for null client id
	 * @param userName
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "CreateSRUsingValidUsers", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, groups = {ExeGroups.Regression}, priority = 1)
	public void verifyEditSrWithNullClientId(String userName, String userRole) throws Exception {
		EditSRRevampTestHelper editSRRevampTestHelper = new EditSRRevampTestHelper(userName);
		String globerId = editSRRevampTestHelper.getGloberIdFromGloberName(userName);
		Response srGridResponse = editSRRevampTestHelper.getSRDetailsFromGrid(globerId);
		validateResponseToContinueTest(srGridResponse, 200, "SR Grid API call was not successful", true);
		
		Response editSRResponse = editSRRevampTestHelper.updateEditSRProjectDetailsWithNullClientId(srGridResponse, userName);
		validateResponseToContinueTest(editSRResponse, 400, "Edit SR project details call with null client id was not successful", true);
		assertEquals(restUtils.getValueFromResponse(editSRResponse, "status"), "fail", "Status is different than fail");
		assertEquals(restUtils.getValueFromResponse(editSRResponse, "message"), "Please select a client.", "Incorrect message for null client id");
		test.log(Status.PASS, "Edit Sr revamp for project details with null client is successful ");
	}

	/**
	 * This test will verify response for null project id
	 * @param userName
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "CreateSRUsingValidUsers", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, groups = {ExeGroups.Regression}, priority = 1)
	public void verifyEditSrWithNullProjectId(String userName, String userRole) throws Exception {
		EditSRRevampTestHelper editSRRevampTestHelper = new EditSRRevampTestHelper(userName);
		String globerId = editSRRevampTestHelper.getGloberIdFromGloberName(userName);
		Response srGridResponse = editSRRevampTestHelper.getSRDetailsFromGrid(globerId);
		validateResponseToContinueTest(srGridResponse, 200, "SR Grid API call was not successful", true);
		
		Response editSRResponse = editSRRevampTestHelper.updateEditSRProjectDetailsWithNullProjectId(srGridResponse, userName);
		validateResponseToContinueTest(editSRResponse, 400, "Edit SR project details call with null project id was not successful", true);
		assertEquals(restUtils.getValueFromResponse(editSRResponse, "status"), "fail", "Status is not fail");
		assertEquals(restUtils.getValueFromResponse(editSRResponse, "message"), "Please select a project.", "Incorrect message for null project id");
		test.log(Status.PASS, "Edit Sr revamp for project details with null project id is successful ");
	}

	/**
	 * This test will verify response for null position id
	 * @param userName
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "CreateSRUsingValidUsers", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, groups = {ExeGroups.Regression}, priority = 1)
	public void verifyEditSrWithNullPosition(String userName, String userRole) throws Exception {
		EditSRRevampTestHelper editSRRevampTestHelper = new EditSRRevampTestHelper(userName);
		String globerId = editSRRevampTestHelper.getGloberIdFromGloberName(userName);
		Response srGridResponse = editSRRevampTestHelper.getSRDetailsFromGrid(globerId);
		validateResponseToContinueTest(srGridResponse, 200, "SR Grid API call was not successful", true);
		
		Response editSRResponse = editSRRevampTestHelper.updateEditSRProjectDetailsWithNullPosition(srGridResponse, userName);
		validateResponseToContinueTest(editSRResponse, 400, "Edit SR project details call for null position id was not successful", true);
		assertEquals(restUtils.getValueFromResponse(editSRResponse, "status"), "fail", "Status is not fail");
		assertEquals(restUtils.getValueFromResponse(editSRResponse, "message"), "Please select a position.", "Incorrect message for null position id");
		test.log(Status.PASS, "Edit Sr revamp for project details with null position id is successful ");
	}

	/**
	 * This test will verify response for null seniority id
	 * @param userName
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "CreateSRUsingValidUsers", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, groups = {ExeGroups.Regression}, priority = 1)
	public void verifyEditSrWithNullSeniority(String userName, String userRole) throws Exception {
		EditSRRevampTestHelper editSRRevampTestHelper = new EditSRRevampTestHelper(userName);
		String globerId = editSRRevampTestHelper.getGloberIdFromGloberName(userName);
		Response srGridResponse = editSRRevampTestHelper.getSRDetailsFromGrid(globerId);
		validateResponseToContinueTest(srGridResponse, 200, "SR Grid API call was not successful", true);
		
		Response editSRResponse = editSRRevampTestHelper.updateEditSRProjectDetailsWithNullSeniority(srGridResponse, userName);
		validateResponseToContinueTest(editSRResponse, 400, "Edit SR project details call for null seniority id was not successful", true);
		assertEquals(restUtils.getValueFromResponse(editSRResponse, "status"), "fail", "Status is not fail");
		assertEquals(restUtils.getValueFromResponse(editSRResponse, "message"), "Please select a seniority.", "Incorrect message for null seniority id");
		test.log(Status.PASS, "Edit Sr revamp for project details with null seniority id is successful ");
	}

	/**
	 * This test will verify response for null start date
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "userListWithTL", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, groups = {ExeGroups.Regression}, priority = 1)
	public void verifyEditSrWithNullStartDate(String user) throws Exception {
		EditSRRevampTestHelper editSRRevampTestHelper = new EditSRRevampTestHelper(user);
		String globerId = editSRRevampTestHelper.getGloberIdFromGloberName(user);
		Response srGridResponse = editSRRevampTestHelper.getSRDetailsFromGrid(globerId);
		validateResponseToContinueTest(srGridResponse, 200, "SR Grid API call was not successful", true);
		
		Response editSRResponse = editSRRevampTestHelper.updateEditSRProjectDetailsWithNullStartDate(srGridResponse, user);
		validateResponseToContinueTest(editSRResponse, 400, "Edit SR project details call for null start date was not successful", true);
		assertEquals(restUtils.getValueFromResponse(editSRResponse, "status"), "fail", "Status is not fail");
		assertEquals(restUtils.getValueFromResponse(editSRResponse, "message"), "Start date field is required.", "Incorrect message for null start date");
		test.log(Status.PASS, "Edit Sr revamp for project details with null start date is successful ");
	}

	/**
	 * This test will verify response for null end date
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "userListWithTL", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, groups = {ExeGroups.Regression}, priority = 1)
	public void verifyEditSrWithNullEndDate(String user) throws Exception {
		EditSRRevampTestHelper editSRRevampTestHelper = new EditSRRevampTestHelper(user);
		String globerId = editSRRevampTestHelper.getGloberIdFromGloberName(user);
		Response srGridResponse = editSRRevampTestHelper.getSRDetailsFromGrid(globerId);
		validateResponseToContinueTest(srGridResponse, 200, "SR Grid API call was not successful", true);
		
		Response editSRResponse = editSRRevampTestHelper.updateEditSRProjectDetailsWithNullEndDate(srGridResponse, user);
		validateResponseToContinueTest(editSRResponse, 400, "Edit SR project details call for null end date was not successful", true);
		assertEquals(restUtils.getValueFromResponse(editSRResponse, "status"), "fail", "Status is not fail");
		assertEquals(restUtils.getValueFromResponse(editSRResponse, "message"), "End date field is required.", "Incorrect message for null end date");
		test.log(Status.PASS, "Edit Sr revamp for project details with null end date is successful ");
	}

	/**
	 * This test will verify response for past end date
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "userListWithTL", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, groups = {ExeGroups.Regression}, priority = 1)
	public void verifyEditSrWithPastEndDate(String user) throws Exception {
		EditSRRevampTestHelper editSRRevampTestHelper = new EditSRRevampTestHelper(user);
		String globerId = editSRRevampTestHelper.getGloberIdFromGloberName(user);
		Response srGridResponse = editSRRevampTestHelper.getSRDetailsFromGrid(globerId);
		validateResponseToContinueTest(srGridResponse, 200, "SR Grid API call was not successful", true);
		
		Response editSRResponse = editSRRevampTestHelper.updateEditSRProjectDetailsWithPastEndDate(srGridResponse, user);
		validateResponseToContinueTest(editSRResponse, 400, "Edit SR project details call for past end date was not successful", true);
		assertEquals(restUtils.getValueFromResponse(editSRResponse, "status"), "fail", "Status is not fail");
		assertEquals(restUtils.getValueFromResponse(editSRResponse, "message"), "End date cannot be earlier than start date.", "Incorrect message for past end date");
		test.log(Status.PASS, "Edit Sr revamp for project details with past end date is successful ");
	}

	/**
	 * This test will verify response for end date greater than start date
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "userListWithTL", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, groups = {ExeGroups.Regression}, priority = 1)
	public void verifyEditSrWithEndDateGreaterThanStartDate(String user) throws Exception {
		EditSRRevampTestHelper editSRRevampTestHelper = new EditSRRevampTestHelper(user);
		String globerId = editSRRevampTestHelper.getGloberIdFromGloberName(user);
		Response srGridResponse = editSRRevampTestHelper.getSRDetailsFromGrid(globerId);
		validateResponseToContinueTest(srGridResponse, 200, "SR Grid API call was not successful", true);
		
		Response editSRResponse = editSRRevampTestHelper.updateEditSRProjectDetailsWithEndDateGreaterThanStartDate(srGridResponse, user);
		validateResponseToContinueTest(editSRResponse, 400, "Edit SR project details call with end date greater than start date  was not successful", true);
		assertEquals(restUtils.getValueFromResponse(editSRResponse, "status"), "fail", "Status is not fail");
		assertEquals(restUtils.getValueFromResponse(editSRResponse, "message"), "End date cannot be earlier than start date.", "Incorrect message for end date greater than start date");
		test.log(Status.PASS, "Edit Sr revamp for project details for end date greater than start date ");
	}

	/**
	 * This test will verify response for Null Location
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "userListWithTL", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, groups = {ExeGroups.Regression}, priority = 1)
	public void verifyEditSrWithNullLocation(String user) throws Exception {
		EditSRRevampTestHelper editSRRevampTestHelper = new EditSRRevampTestHelper(user);
		String globerId = editSRRevampTestHelper.getGloberIdFromGloberName(user);
		Response srGridResponse = editSRRevampTestHelper.getSRDetailsFromGrid(globerId);
		validateResponseToContinueTest(srGridResponse, 200, "SR Grid API call was not successful", true);
		
		Response editSRResponse = editSRRevampTestHelper.updateEditSRProjectDetailsWithNullPrimaryLocation(srGridResponse, user);
		validateResponseToContinueTest(editSRResponse, 400, "Edit SR project details call for Null Location  was not successful", true);
		assertEquals(restUtils.getValueFromResponse(editSRResponse, "status"), "fail", "Status is not fail");
		assertEquals(restUtils.getValueFromResponse(editSRResponse, "message"), "Please select a valid Primary Location.", "Incorrect message for Null Location");
		test.log(Status.PASS, "Edit Sr revamp for project details for Null Location is successful ");
	}
	
	/**
	 * This test will verify edit sr for assignment details using new edit revamp
	 * @param userName
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "CreateSRUsingValidUsers", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, groups = {ExeGroups.Regression, ExeGroups.Sanity}, priority = 0)
	public void editSRAssignmentDetails(String userName, String userRole) throws Exception {
		EditSRRevampTestHelper editSRRevampTestHelper = new EditSRRevampTestHelper(userName);
		String globerId = editSRRevampTestHelper.getGloberIdFromGloberName(userName);
		Response srGridResponse = editSRRevampTestHelper.getSRDetailsFromGrid(globerId);
		validateResponseToContinueTest(srGridResponse, 200, "SR Grid API call was not successful", true);
		
		Response editSRResponse = editSRRevampTestHelper.updateEditSRAssignmentDetails(srGridResponse, userName);
		validateResponseToContinueTest(editSRResponse, 200, "Edit SR assignment details call was not successful", true);
		assertEquals(restUtils.getValueFromResponse(editSRResponse, "status"), "success", "Status is not success");
		test.log(Status.PASS, "Edit Sr revamp for assignment details is successful ");
	}
	
	/**
	 * This test will verify removal of black list skill associated with SR after editing the SR
	 * @param userName
	 * @throws Exception
	 */
	@Test(dataProvider = "gatekeeperUser", dataProviderClass = StaffingDataProviders.class, enabled = true, groups = {ExeGroups.Regression, ExeGroups.Sanity}, priority = 0)
	public void editSrAndRemoveBlackListSkill(String userName) throws Exception {
		EditSRRevampTestHelper editSRRevampTestHelper = new EditSRRevampTestHelper(userName);
		String globerId = editSRRevampTestHelper.getGloberIdFromGloberName(userName);
		Response srGridResponse = editSRRevampTestHelper.getSRDetailsFromGrid(globerId);
		validateResponseToContinueTest(srGridResponse, 200, "SR Grid API call was not successful", true);
		
		List<String> responseSrNum = editSRRevampTestHelper.updateEditSRProjectDetailsForNewSkill(srGridResponse, userName);
		String responseMsg = responseSrNum.get(0);
		String srNumberFromPositionId = responseSrNum.get(1);
		assertTrue(responseMsg.contains("Skill removed successfully from the position. SR Number# "+srNumberFromPositionId),"Removing black list skill from SR failed");
		test.log(Status.PASS, "Blacklist skill removed successfully. ");
	}
}