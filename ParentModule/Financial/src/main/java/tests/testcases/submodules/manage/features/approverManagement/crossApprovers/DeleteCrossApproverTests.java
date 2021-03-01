package tests.testcases.submodules.manage.features.approverManagement.crossApprovers;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.manage.features.CrossApproversDataProviders;
import dto.submodules.manage.approverManagement.crossApprovers.deleteCrossApprover.DeleteCrossApproverResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import parameters.submodules.manage.features.approverManagement.crossApprovers.PostCrossApproverParameters;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.features.approverManagement.crossApprovers.DeleteCrossApproverTestHelper;
import tests.testhelpers.submodules.manage.features.approverManagement.crossApprovers.PostCrossApproverTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class DeleteCrossApproverTests extends ManageBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("DeleteCrossApproverTests");
	}

	/**
	 * Check that is feasible to delete a cross approver. 
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "newCrossApprover", dataProviderClass = CrossApproversDataProviders.class, groups = { ExeGroups.NotAvailableInPreProd })
	public void deleteCrossApproverTest(PostCrossApproverParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		PostCrossApproverTestHelper helper = new PostCrossApproverTestHelper(parameters.getUsername());
		String crossApproverId = helper.postCrossApproverFromScratch(parameters);
		Response response = new DeleteCrossApproverTestHelper(parameters.getUsername()).deleteCrossApprover(crossApproverId);
		validateResponseToContinueTest(response, 200, "DELETE cross approver api call was not successful.", true);
		DeleteCrossApproverResponseDTO crossApprover = response.as(DeleteCrossApproverResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(crossApprover.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(crossApprover.getMessage(), "Success", "getMessage issue");
		softAssert.assertEquals(crossApprover.getContent().getGloberId(), crossApproverId, "getGloberId issue");
		softAssert.assertAll();
	}
	
	/**
	 * Check that is not feasible to delete a cross approver twice. 
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "newCrossApprover", dataProviderClass = CrossApproversDataProviders.class, groups = { ExeGroups.NotAvailableInPreProd })
	public void deleteTwiceCrossApproverTest(PostCrossApproverParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		PostCrossApproverTestHelper helper = new PostCrossApproverTestHelper(parameters.getUsername());
		String crossApproverId = helper.postCrossApproverFromScratch(parameters);
		Response response = new DeleteCrossApproverTestHelper(parameters.getUsername()).deleteCrossApprover(crossApproverId);
		validateResponseToContinueTest(response, 200, "DELETE cross approver api call was not successful.", true);
		response = new DeleteCrossApproverTestHelper(parameters.getUsername()).deleteCrossApprover(crossApproverId);
		validateResponseToContinueTest(response, 400, "DELETE cross approver api call was not successful.", true);
		DeleteCrossApproverResponseDTO crossApprover = response.as(DeleteCrossApproverResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(crossApprover.getStatus(), "Bad Request", "getStatus issue");
		softAssert.assertTrue(crossApprover.getMessage().contains("The cross approver does not exist"), "getMessage issue");
		softAssert.assertAll();
	}

}
