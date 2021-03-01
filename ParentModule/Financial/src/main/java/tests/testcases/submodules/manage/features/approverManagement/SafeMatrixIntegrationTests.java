package tests.testcases.submodules.manage.features.approverManagement;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import database.submodules.manage.ManageDBHelper;
import database.submodules.manage.features.ConfigureAmountDBHelper;
import database.submodules.manage.features.approverManagement.CrossApproverDBHelper;
import dataproviders.submodules.manage.features.ApproverManagementDataProviders;
import dataproviders.submodules.manage.features.ConfigureAmountDataProviders;
import dataproviders.submodules.manage.features.CrossApproversDataProviders;
import dto.submodules.manage.approverManagement.getApprovers.GetApproversResponseDTO;
import dto.submodules.manage.approverManagement.postApprover.ApproversResponseDTO;
import dto.submodules.manage.configureAmount.deletePosition.DeletePositionForSafeMatrixRolResponseDTO;
import dto.submodules.manage.configureAmount.getPosition.Content;
import dto.submodules.manage.configureAmount.postPosition.PostPositionForSafeMatrixRolResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import parameters.submodules.manage.features.approverManagement.DeleteApproverParameters;
import parameters.submodules.manage.features.approverManagement.GetApproversParameters;
import parameters.submodules.manage.features.approverManagement.crossApprovers.PostCrossApproverParameters;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.features.approverManagement.DeleteApproverTestHelper;
import tests.testhelpers.submodules.manage.features.approverManagement.GetApproversTestHelper;
import tests.testhelpers.submodules.manage.features.approverManagement.PostApproverTestHelper;
import tests.testhelpers.submodules.manage.features.approverManagement.PutCecoRequiredAllLevelsTestHelper;
import tests.testhelpers.submodules.manage.features.approverManagement.crossApprovers.PostCrossApproverTestHelper;
import tests.testhelpers.submodules.manage.features.configureAmount.DeletePositionForRolTestHelper;
import tests.testhelpers.submodules.manage.features.configureAmount.PostPositionForRolTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class SafeMatrixIntegrationTests extends ManageBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("SafeMatrixIntegrationTests");
	}

	
	/**
	 * Check that is feasible to add an approver and the safe matrix was updated.
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "randomRol", dataProviderClass = ApproverManagementDataProviders.class, groups = { ExeGroups.NotAvailableInPreProd })
	public void addApproverAndCheckSafeMatrixTest(String user) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		PostApproverTestHelper postHelper = new PostApproverTestHelper(user);
		ApproversResponseDTO approver = postHelper.postApproverFromScratch();
		Response response = new PutCecoRequiredAllLevelsTestHelper(user).putCecoRequiredAllLevels(approver.getContent().getCecoNumber(), "true");
		validateResponseToContinueTest(response, 200, "update ceco api call was not successful.", true);
		GetApproversTestHelper helper = new GetApproversTestHelper(user);
		GetApproversParameters parameters = new GetApproversParameters();
		parameters.setProjectId(new ManageDBHelper().getProjectIdByCeco(approver.getContent().getCecoNumber()));
		response = helper.getApprovers(parameters);
		validateResponseToContinueTest(response, 200, "Get approvers api call was not successful.", true);
		GetApproversResponseDTO approvers = response.as(GetApproversResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(approvers.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(approvers.getMessage(), "Success", "getMessage issue");
		softAssert.assertEquals(approvers.getContent().getProjectId(), parameters.getProjectId(), "getProjectId issue");
		softAssert.assertTrue(helper.isApproverPresentInTheSafeMatrix(approvers, approver.getNewApproverId()), "isApproverPresent issue");
		new PutCecoRequiredAllLevelsTestHelper(user).putCecoRequiredAllLevels(approver.getContent().getCecoNumber(), "false");
		DeleteApproverParameters deleteApproverParameters = new DeleteApproverParameters(approver);
		DeleteApproverTestHelper deleteApproverTestHelper = new DeleteApproverTestHelper(user);
		response = deleteApproverTestHelper.deleteApprover(deleteApproverParameters);
		validateResponseToContinueTest(response, 200, "DELETE ceco api call was not successful.", true);
		softAssert.assertAll(); 
	}


	/**
	 * Check that is feasible to add an cross approver and the safe matrix was updated.
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "newCrossApprover", dataProviderClass = CrossApproversDataProviders.class, groups = { ExeGroups.NotAvailableInPreProd })
	public void addCrossApproverAndCheckSafeMatrixForCecoProjectsTest(PostCrossApproverParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		String ceco = new ManageDBHelper().getRandomCeco();
		Response response = new PutCecoRequiredAllLevelsTestHelper(parameters.getUsername()).putCecoRequiredAllLevels(ceco, "true");
		validateResponseToContinueTest(response, 200, "PUT ceco api call was not successful.", true);
		PostCrossApproverTestHelper helper = new PostCrossApproverTestHelper(parameters.getUsername());
		String crossApproverId = helper.postCrossApproverFromScratch(parameters);
		GetApproversTestHelper getApproversTestHelper = new GetApproversTestHelper(parameters.getUsername());
		GetApproversParameters getApproversParameters = new GetApproversParameters();
		getApproversParameters.setProjectId(new ManageDBHelper().getProjectIdByCeco(ceco));
		response = getApproversTestHelper.getApprovers(getApproversParameters);
		validateResponseToContinueTest(response, 200, "Get approvers api call was not successful.", true);
		GetApproversResponseDTO approvers = response.as(GetApproversResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(approvers.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(approvers.getMessage(), "Success", "getMessage issue");
		softAssert.assertEquals(approvers.getContent().getProjectId(), getApproversParameters.getProjectId(), "getProjectId issue");
		softAssert.assertTrue(getApproversTestHelper.isApproverPresentInTheSafeMatrix(approvers, crossApproverId), "isApproverPresent issue");	
		response = new PutCecoRequiredAllLevelsTestHelper(parameters.getUsername()).putCecoRequiredAllLevels(ceco, "false");
		validateResponseToContinueTest(response, 200, "PUT ceco api call was not successful.", true);
		softAssert.assertEquals(new CrossApproverDBHelper().deleteCrossApprover(crossApproverId), 1, "the cross approver can not remove.");
		softAssert.assertAll();
	}

	/**
	 * Check that is feasible to add an cross approver and the safe matrix was updated.
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "newCrossApprover", dataProviderClass = CrossApproversDataProviders.class, groups = { ExeGroups.NotAvailableInPreProd })
	public void addCrossApproverAndCheckSafeMatrixForProductiveProjectsTest(PostCrossApproverParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		String ceco = new ManageDBHelper().getRandomCeco();
		Response response = new PutCecoRequiredAllLevelsTestHelper(parameters.getUsername()).putCecoRequiredAllLevels(ceco, "true");
		validateResponseToContinueTest(response, 200, "PUT ceco api call was not successful.", true);
		PostCrossApproverTestHelper helper = new PostCrossApproverTestHelper(parameters.getUsername());
		String crossApproverId = helper.postCrossApproverFromScratch(parameters);
		GetApproversTestHelper getApproversTestHelper = new GetApproversTestHelper(parameters.getUsername());
		GetApproversParameters getApproversParameters = new GetApproversParameters();
		getApproversParameters.setProjectId(new ManageDBHelper().getProductiveProjectId());
		response = getApproversTestHelper.getApprovers(getApproversParameters);
		validateResponseToContinueTest(response, 200, "Get approvers api call was not successful.", true);
		GetApproversResponseDTO approvers = response.as(GetApproversResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(approvers.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(approvers.getMessage(), "Success", "getMessage issue");
		softAssert.assertEquals(approvers.getContent().getProjectId(), getApproversParameters.getProjectId(), "getProjectId issue");
		softAssert.assertTrue(getApproversTestHelper.isApproverPresentInTheSafeMatrix(approvers, crossApproverId), "isApproverPresent issue");	
		response = new PutCecoRequiredAllLevelsTestHelper(parameters.getUsername()).putCecoRequiredAllLevels(ceco, "false");
		validateResponseToContinueTest(response, 200, "PUT ceco api call was not successful.", true);
		softAssert.assertEquals(new CrossApproverDBHelper().deleteCrossApprover(crossApproverId), 1, "the cross approver can not remove.");
		softAssert.assertAll();
	}

	/**
	 * Goal: Check that is feasible add a position to a rol and then check the safe matrix. 
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "safeMatrixAdminRol", dataProviderClass = ConfigureAmountDataProviders.class, groups = { ExeGroups.NotAvailableInPreProd })
	public void addPositionForRolSafeMatrixRolAndCheckSafeMatrixTest(String user) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		String positionName="Test Automation Engineer";
		PostPositionForSafeMatrixRolResponseDTO positionRol;
		String positionRoleId = new ConfigureAmountDBHelper().positionHasRole(positionName);
		if (positionRoleId.equals("0")) positionRol = new PostPositionForRolTestHelper(user).postPositionFromScratchCore(new Content(positionName));
		else positionRol = new PostPositionForSafeMatrixRolResponseDTO(positionName, positionRoleId);
		
		
		DeletePositionForRolTestHelper deleteHelper = new DeletePositionForRolTestHelper(user);
		Response response = deleteHelper.deletePosition(positionRol);
		validateResponseToContinueTest(response, 200, "Delete position for safe matrix role api call was not successful.", true);
		DeletePositionForSafeMatrixRolResponseDTO deleteResponse = response.as(DeletePositionForSafeMatrixRolResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(deleteResponse.getStatus(), "Success", "getStatus issue");
		softAssert.assertEquals(deleteResponse.getMessage(), "OK", "getMessage issue");
		softAssert.assertEquals(deleteResponse.getContent().getId(), positionRol.getContent().getId(), "getId issue");
		softAssert.assertAll();		
	}

}
