package tests.testcases.submodules.staffRequest.features;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import dataproviders.submodules.staffRequest.features.CreateNewPositionDataProviders;
import io.restassured.response.Response;
import tests.testcases.submodules.staffRequest.StaffRequestBaseTest;
import tests.testhelpers.submodules.staffRequest.features.CreateNewPositionsTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;
import utils.RestUtils;
import utils.Utilities;

/**
 * 
 * @author akshata.dongare
 *
 */
public class CreateNewPositionTests extends StaffRequestBaseTest {
	RestUtils restUtils = new RestUtils();
	String positionType= null;
	int load=0;
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("Create New Position");
	}
	
	/**
	 * This test will create shadow position using new SR flow
	 * @param userName
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "CreateSRUsingValidUsers", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true,  groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void createShadowPostionUsingNewFlow(String userName, String userRole) throws Exception {
		positionType = "SHADOW";
		CreateNewPositionsTestHelper createNewPositionsTestHelper = new CreateNewPositionsTestHelper(userName);
		Response createSRResponse = createNewPositionsTestHelper.createSRUsingNewFlow(userName, positionType);
		validateResponseToContinueTest(createSRResponse, 201, "Unable to create Shadow Position", true);
		String message = (String) restUtils.getValueFromResponse(createSRResponse, "message");
		assertTrue(message.contains("Success! You have successfully created Staff Request"),
				"Failed in validating message");
		assertEquals(restUtils.getValueFromResponse(createSRResponse, "status"), "success","Status is not success");
		test.log(Status.PASS, "Shadow Position is created using new flow");
	}

	/**
	 * This test will create shadow position using new SR flow for variable load position
	 * @param userRole
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "CreateSRUsingValidUsers", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void createShadowPostionVaribaleLoadUsingNewFlow(String userName, String userRole) throws Exception {
		positionType = "SHADOW";
		CreateNewPositionsTestHelper createNewPositionsTestHelper = new CreateNewPositionsTestHelper(userName);
		Response createSRResponse = createNewPositionsTestHelper.createSRUsingNewFlowVariableLoad(userName, positionType);
		validateResponseToContinueTest(createSRResponse, 201, "Unable to create Shadow Position", true);
		String message = (String) restUtils.getValueFromResponse(createSRResponse, "message");
		assertTrue(message.contains("Success! You have successfully created Staff Request"),
				"Failed in validating message");
		assertEquals(restUtils.getValueFromResponse(createSRResponse, "status"), "success","Status is not success");
		test.log(Status.PASS, "Shadow Position is created using new flow");
	}


	/**
	 * This test will create globant staff position using new SR flow
	 * @param userName
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "CreateSRUsingValidUsers", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void createGlobantStaffPostionUsingNewFlow(String userName, String userRole) throws Exception {
		positionType = "CONFIRMED";
		CreateNewPositionsTestHelper createNewPositionsTestHelper = new CreateNewPositionsTestHelper(userName);
		Response createSRResponse = createNewPositionsTestHelper.createSRUsingNewFlow(userName, positionType);
		validateResponseToContinueTest(createSRResponse, 201, "Unable to create globant staff Position", true);
		String message = (String) restUtils.getValueFromResponse(createSRResponse, "message");
		assertTrue(message.contains("Success! You have successfully created Staff Request"),
				"Failed in validating message");
		assertEquals(restUtils.getValueFromResponse(createSRResponse, "status"), "success","Status is not success");
		test.log(Status.PASS, "Globant staff Position is created using new flow");
	}

	/**
	 * This test will check creation of SR for invalid position and seniority template match
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "userListWithTL", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, groups = ExeGroups.Regression)
	public void verifyInvalidPositionSeniorityTemplate(String user) throws Exception {
		CreateNewPositionsTestHelper createNewPositionsTestHelper = new CreateNewPositionsTestHelper(user);
		Response response = createNewPositionsTestHelper.selectInvalidPositionSeniorityTemplate(user,"SHADOW");
		validateResponseToContinueTest(response, 201, "Unable to create SR with invalid template", true);
		String message = (String) restUtils.getValueFromResponse(response, "message");
		assertTrue(message.contains("Success! You have successfully created Staff Request"),
				"Failed in validating message");
		assertEquals(restUtils.getValueFromResponse(response, "status"), "success","Status is not success");
		test.log(Status.PASS, "Position is created using new flow for invalid template");
	}

	/**
	 * This test will verify top matching glober count with albertha API
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "userListWithTL", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, groups = ExeGroups.Regression)
	public void verifyTopMatchingGloberCount(String user) throws Exception {
		CreateNewPositionsTestHelper createNewPositionsTestHelper = new CreateNewPositionsTestHelper(user);
		List<Integer> matchingGloberCount = createNewPositionsTestHelper.getAlberthaTopMatchingGloberCount(user);

		assertEquals(matchingGloberCount.get(0), matchingGloberCount.get(1),"Count is different");
		test.log(Status.PASS, "Top matching glober count is verified with albertha API");
	}
	
	/**
	 * This test will verify location name from db and response
	 * @param userName
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "CreateSRUsingValidUsers", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void verifyLocationName(String userName, String userRole) throws Exception {
		CreateNewPositionsTestHelper createNewPositionsTestHelper = new CreateNewPositionsTestHelper(userName);
		Response locationResponse = createNewPositionsTestHelper.getLocationListResponse();
		validateResponseToContinueTest(locationResponse, 200, "Get Project List API call was not successful", true);
		
		int i = Utilities.getRandomDay();
		String locationIdResponse = restUtils.getValueFromResponse(locationResponse, "details.["+i+"].id").toString();
		String locationNameResponse = restUtils.getValueFromResponse(locationResponse, "details.["+i+"].name").toString();
		String locationNameDb = createNewPositionsTestHelper.getLocationNameFromId(locationIdResponse);
		
		assertEquals(locationNameResponse, locationNameDb,"Mismatch in db and response project names");
		test.log(Status.PASS, "Location API working fine");
	}
	
	/**
	 * This test will verify client name from db and response
	 * @param userName
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "CreateSRUsingValidUsers", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void verifyClientName(String userName, String userRole) throws Exception {
		CreateNewPositionsTestHelper createNewPositionsTestHelper = new CreateNewPositionsTestHelper(userName);
		Response clientAPIResponse = createNewPositionsTestHelper.getClientListResponse();
		validateResponseToContinueTest(clientAPIResponse, 200, "Get Client List API call was not successful", true);
	
		int i = Utilities.getRandomDay();
		String clientIdResponse = restUtils.getValueFromResponse(clientAPIResponse, "details.["+i+"].id").toString();
		String clientNameResponse = restUtils.getValueFromResponse(clientAPIResponse, "details.["+i+"].name").toString();
		String clientNameDb = createNewPositionsTestHelper.getClientNameDb(clientIdResponse);
		
		assertEquals(clientNameResponse, clientNameDb,"Mismatch in db and response client names");
		test.log(Status.PASS, "Client API is working fine");
	}
	
	/**
	 * This test will verify project name from db and response
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "userListWithTL", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void verifyProjectName(String user) throws Exception {
		CreateNewPositionsTestHelper createNewPositionsTestHelper = new CreateNewPositionsTestHelper(user);
		Response projectAPIResponse = createNewPositionsTestHelper.getProjectListResponse();
		validateResponseToContinueTest(projectAPIResponse, 200, "Get Project List API call was not successful", true);
	
		String projectIdResponse = restUtils.getValueFromResponse(projectAPIResponse, "details.[0].projectId").toString();
		String projectNameResponse = restUtils.getValueFromResponse(projectAPIResponse, "details.[0].projectName").toString();
		String projectNameDb = createNewPositionsTestHelper.getProjectNameDb(projectIdResponse);
		
		assertEquals(projectNameResponse, projectNameDb,"Mismatch in db and response project names");
		test.log(Status.PASS, "Project API is working fine");
	}
	
	/**
	 * This test will verify position name from db and response
	 * @param userName
	 * @param userRole
	 * @throws Exception
	 */
	@Test(dataProvider = "CreateSRUsingValidUsers", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void verifyPositionName(String userName, String userRole) throws Exception {
		CreateNewPositionsTestHelper createNewPositionsTestHelper = new CreateNewPositionsTestHelper(userName);
		Response positionAPIResponse = createNewPositionsTestHelper.getPositionResponse();
		validateResponseToContinueTest(positionAPIResponse, 200, "Get Position drop down list API call was not successful", true);
		
		int i = Utilities.getRandomDay();
		String positionIdResponse = restUtils.getValueFromResponse(positionAPIResponse, "details.["+i+"].id").toString();
		String positionNameResponse = restUtils.getValueFromResponse(positionAPIResponse, "details.["+i+"].name").toString();
		String positionNameDb = createNewPositionsTestHelper.getPositionNameDb(positionIdResponse);
		
		assertEquals(positionNameResponse, positionNameDb,"Mismatch in db and response position names");
		test.log(Status.PASS, "Position list API is working fine");
	}
	
	/**
	 *  This test will verify sow id and name from db and response
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "CreateSRUsingValidUsers", dataProviderClass = CreateNewPositionDataProviders.class, enabled = true, groups = {ExeGroups.Regression, ExeGroups.Sanity})
	public void verifySowIdName(String userName, String userRole) throws Exception {
		CreateNewPositionsTestHelper createNewPositionsTestHelper = new CreateNewPositionsTestHelper(userName);
		Response sowIdAPIResponse = createNewPositionsTestHelper.getSowIdResponse();
		validateResponseToContinueTest(sowIdAPIResponse, 200, "Get sow id API call was not successful", true);
		
		String projectId = restUtils.getValueFromResponse(sowIdAPIResponse, "details.[0].projectId").toString();
		String sowIdResponse = restUtils.getValueFromResponse(sowIdAPIResponse, "details.[0].id").toString();
		String sowIdDb = createNewPositionsTestHelper.getSowIdAndName(projectId).get(0);
		String sowIdNameResponse = restUtils.getValueFromResponse(sowIdAPIResponse, "details.[0].name").toString();
		String sowIdNameDb = createNewPositionsTestHelper.getSowIdAndName(projectId).get(1);
		
		assertEquals(sowIdResponse, sowIdDb,"Mismatch in db and response sow id");
		assertEquals(sowIdNameResponse, sowIdNameDb,"Mismatch in db and response sow names");
		test.log(Status.PASS, "Sow Id API is working fine");
	}
	
}
