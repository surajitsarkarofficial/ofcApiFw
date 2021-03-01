package tests.testcases.submodules.manage.features.approverManagement;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.manage.features.ApproverManagementDataProviders;
import dataproviders.submodules.manage.features.CecoDataProviders;
import dto.submodules.manage.approverManagement.getCecoDetails.GetCecoDetailsResponseDTO;
import dto.submodules.manage.approverManagement.getCecos.ViewCecoApproverList;
import dto.submodules.manage.approverManagement.postApprover.ApproversResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import parameters.submodules.manage.features.approverManagement.getCecoDetails.GetCecoDetailsParameters;
import parameters.submodules.manage.features.approverManagement.getCecos.GetCecosParameters;
import parameters.submodules.manage.features.approverManagement.putCecoRequiredAllLevels.CecoRequiredAllLevelsParameters;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.features.approverManagement.GetCecoDetailsTestHelper;
import tests.testhelpers.submodules.manage.features.approverManagement.GetCecosTestHelper;
import tests.testhelpers.submodules.manage.features.approverManagement.PostApproverTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class GetCecoDetailsTests extends ManageBaseTest {
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("GetCecoDetailsTests");
	}
	
	/**
	 * Check that is feasible to perform a GET ceco details. 
	 * @param user
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(dataProvider = "randomRol", dataProviderClass = ApproverManagementDataProviders.class, groups = {ExeGroups.Sanity})
	public void getCecoDetailsTest(String user) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		GetCecoDetailsTestHelper helper = new GetCecoDetailsTestHelper(user);
		GetCecoDetailsParameters parameters = new GetCecoDetailsParameters();
		Response response = helper.getCecoDetails(parameters);
		validateResponseToContinueTest(response, 200, "Get ceco details api call was not successful.", true);
		GetCecoDetailsResponseDTO ceco = response.as(GetCecoDetailsResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(ceco.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(ceco.getMessage(), "Success", "getMessage issue");
		softAssert.assertEquals(ceco.getContent().getCecoNumber(), parameters.getCecoNumber(), "getCecoNumber issue");
		softAssert.assertAll();
	}

	/**
	 * Check that is feasible to perform a GET ceco details from a ceco with a new approver. 
	 * @param user
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(dataProvider = "randomRol", dataProviderClass = ApproverManagementDataProviders.class, groups = { ExeGroups.NotAvailableInPreProd })
	public void getCecoDetailsWithNewApproverTest(String user) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		PostApproverTestHelper postHelper = new PostApproverTestHelper(user);
		ApproversResponseDTO approvers = postHelper.postApproverFromScratch();
		GetCecoDetailsTestHelper helper = new GetCecoDetailsTestHelper(user);
		GetCecoDetailsParameters parameters = new GetCecoDetailsParameters(approvers);
		Response response = helper.getCecoDetails(parameters);
		GetCecoDetailsResponseDTO ceco = response.as(GetCecoDetailsResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(ceco.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(ceco.getMessage(), "Success", "getMessage issue");
		softAssert.assertEquals(ceco.getContent().getCecoNumber(), parameters.getCecoNumber(), "getCecoNumber issue");
		softAssert.assertTrue(helper.isApproverPresent(approvers.getNewApproverId(), ceco, "true"), "isApproverPresent issue");
		softAssert.assertAll();
	}
	
	/**
	 * Goal: Check the total approvers quantity by ceco.
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "randomRol", dataProviderClass = ApproverManagementDataProviders.class)
	public void checkTotalApproversQuantityTest(String user) throws Exception {
		GetCecosTestHelper cecosListHelper = new GetCecosTestHelper(user);
		ViewCecoApproverList randomCeco = cecosListHelper.getRandomCeco(new GetCecosParameters());
		GetCecoDetailsTestHelper cecoDetailsHelper = new GetCecoDetailsTestHelper(user);
		GetCecoDetailsResponseDTO cecoDetails = cecoDetailsHelper.getCecoDetailsResponseDTO(new GetCecoDetailsParameters(randomCeco));
		Assert.assertTrue(Long.valueOf(randomCeco.getTotalApprovers())<=Long.valueOf(cecoDetails.getContent().getApprovers().size()), "TotalApprovers issue for ceco number:"+randomCeco.getCecoNumber());
	}
	
	/**
	 * Check that is feasible to perform a GET ceco details from a ceco with a new approver without rol assigned. 
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "randomRol", dataProviderClass = ApproverManagementDataProviders.class, groups = { ExeGroups.NotAvailableInPreProd })
	public void getCecoDetailsWithANewApproverWithoutRolTest(String user) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		PostApproverTestHelper postHelper = new PostApproverTestHelper(user);
		ApproversResponseDTO approvers = postHelper.postApproverWithoutRolFromScratch();
		GetCecoDetailsTestHelper helper = new GetCecoDetailsTestHelper(user);
		GetCecoDetailsParameters parameters = new GetCecoDetailsParameters(approvers);
		Response response = helper.getCecoDetails(parameters);
		GetCecoDetailsResponseDTO ceco = response.as(GetCecoDetailsResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(ceco.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(ceco.getMessage(), "Success", "getMessage issue");
		softAssert.assertEquals(ceco.getContent().getCecoNumber(), parameters.getCecoNumber(), "getCecoNumber issue");
		softAssert.assertTrue(helper.isApproverPresent(approvers.getNewApproverId(), ceco, "true"), "isApproverPresent issue");
		softAssert.assertAll();
	}
	
	/**
	 * Check that is feasible to perform a GET ceco details from a ceco and validate the RequiredAllLevels status. 
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "cecoRequiredAllLevels", dataProviderClass = CecoDataProviders.class
			, groups = { ExeGroups.NotAvailableInPreProd })
	public void getCecoDetailsRequiredAllLevelsTest(CecoRequiredAllLevelsParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		GetCecoDetailsParameters getCecoDetailsParameters = new GetCecoDetailsParameters(parameters);
		Response response = new GetCecoDetailsTestHelper(parameters.getUsername()).getCecoDetails(getCecoDetailsParameters);
		validateResponseToContinueTest(response, 200, "Get ceco details api call was not successful.", true);
		GetCecoDetailsResponseDTO ceco = response.as(GetCecoDetailsResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(ceco.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(ceco.getMessage(), "Success", "getMessage issue");
		softAssert.assertEquals(ceco.getContent().getRequiredAllLevels(), parameters.getCecoRequiredAllLevelsOriginalStatus(), "getRequiredAllLevels issue");
		softAssert.assertEquals(ceco.getContent().getCecoNumber(), parameters.getCecoNumber(), "getCecoNumber issue");
		softAssert.assertAll();
	}
	
	/**
	 * Check that is feasible to perform a GET ceco with cross approver detail. 
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "getCecoWithCrossApproverDetails", dataProviderClass = CecoDataProviders.class
			, groups = { ExeGroups.NotAvailableInPreProd })
	public void getCecoWithCrossApproverDetailsTest(GetCecoDetailsParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		GetCecoDetailsTestHelper helper = new GetCecoDetailsTestHelper(parameters.getUsername());
		Response response = helper.getCecoDetails(parameters);
		validateResponseToContinueTest(response, 200, "Get ceco details api call was not successful.", true);
		GetCecoDetailsResponseDTO ceco = response.as(GetCecoDetailsResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(ceco.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(ceco.getMessage(), "Success", "getMessage issue");
		softAssert.assertEquals(ceco.getContent().getCecoNumber(), parameters.getCecoNumber(), "getCecoNumber issue");
		softAssert.assertTrue(helper.isApproverPresent(parameters.getCrossApproverId(), ceco, "false"), "isApproverPresent issue");
		softAssert.assertTrue(helper.isCrossApproverPresent(parameters, ceco), "isCrossApproverPresent issue");
		softAssert.assertAll();
	}
}
