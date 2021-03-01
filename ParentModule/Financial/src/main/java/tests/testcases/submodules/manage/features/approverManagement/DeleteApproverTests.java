package tests.testcases.submodules.manage.features.approverManagement;



import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.manage.features.ApproverManagementDataProviders;
import dto.submodules.manage.approverManagement.postApprover.ApproversResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import parameters.submodules.manage.features.approverManagement.DeleteApproverParameters;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.features.approverManagement.DeleteApproverTestHelper;
import tests.testhelpers.submodules.manage.features.approverManagement.PostApproverTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class DeleteApproverTests extends ManageBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("DeleteApproverTests");
	}

	/**
	 * Check that is feasible to perform a DELETE approve from a ceco.
	 * @param user
	 * @throws Exception
	 */
	@Test(dataProvider = "randomRol", dataProviderClass = ApproverManagementDataProviders.class, groups = {ExeGroups.Sanity})
	public void deleteApproverTest(String user) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		PostApproverTestHelper postHelper = new PostApproverTestHelper(user);
		ApproversResponseDTO approver = postHelper.postApproverFromScratch();
		DeleteApproverParameters parameters = new DeleteApproverParameters(approver);
		DeleteApproverTestHelper helper = new DeleteApproverTestHelper(user);
		Response response = helper.deleteApprover(parameters);
		validateResponseToContinueTest(response, 200, "Get ceco details api call was not successful.", true);
		ApproversResponseDTO approvers = response.as(ApproversResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(approvers.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(approvers.getMessage(), "Success", "getMessage issue");
		softAssert.assertEquals(approvers.getContent().getCecoNumber(), parameters.getCecoNumber(), "getCecoNumber issue");
		softAssert.assertFalse(postHelper.isApproverPresent(parameters.getIdApprover(), approvers.getContent().getApprovers()), "approver still present");
		softAssert.assertAll(); 
	}
	
}
