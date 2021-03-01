package tests.testcases.submodules.manage.features.approverManagement;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.manage.features.ApproverManagementDataProviders;
import dto.submodules.manage.approverManagement.getActiveApproversAvailable.GetActiveApproversAvailableResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import parameters.submodules.manage.features.approverManagement.GetActiveApproversAvailabletParameters;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.features.approverManagement.GetActiveApproversAvailableTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class GetActiveApproversAvailableTests extends ManageBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("GetActiveApproversAvailableTests");
	}

	/**
	 * Check that is feasible to perform a GET active approvers available. 
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "activeApproversAvailable", dataProviderClass = ApproverManagementDataProviders.class
			, groups = { ExeGroups.NotAvailableInPreProd })
	public void getActiveApproversAvailableTest(GetActiveApproversAvailabletParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		Response response = new GetActiveApproversAvailableTestHelper(parameters.getUsername()).getActiveApproversAvailable(parameters);
		validateResponseToContinueTest(response, 200, "Get active approvers available api call was not successful.", true);
		GetActiveApproversAvailableResponseDTO approvers = response.as(GetActiveApproversAvailableResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(approvers.getStatus(), "Success", "getStatus issue");
		softAssert.assertEquals(approvers.getMessage(), "OK", "getMessage issue");
		softAssert.assertTrue(Integer.valueOf(approvers.getContent().getTotalApprovers())>0, "getTotalApprovers issue");
		softAssert.assertTrue(Integer.valueOf(approvers.getContent().getActiveApprovers().get(0).getGloberId())>0, "getGloberId issue");
		softAssert.assertNotNull(approvers.getContent().getActiveApprovers().get(0).getGloberName(), "getGloberName issue");
		softAssert.assertTrue(Double.valueOf(approvers.getContent().getActiveApprovers().get(0).getMaxAmount())>0, "getMaxAmount issue");
		softAssert.assertNotNull(approvers.getContent().getActiveApprovers().get(0).getGloberPosition(), "getGloberPosition issue");
		softAssert.assertNotNull(approvers.getContent().getActiveApprovers().get(0).getRole(), "getRole issue");
		softAssert.assertTrue(approvers.getContent().getActiveApprovers().size()==1, "approvers quantity issue");
		softAssert.assertAll();
	}
	
	/**
	 * Check status 204 for inexistent approver.
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "activeApproversAvailable", dataProviderClass = ApproverManagementDataProviders.class
			, groups = { ExeGroups.NotAvailableInPreProd })
	public void getInexistentActiveApproversAvailableTest(GetActiveApproversAvailabletParameters parameters) throws Exception {
		parameters.setSearchValue("InexistentGlober");
		Response response = new GetActiveApproversAvailableTestHelper(parameters.getUsername()).getActiveApproversAvailable(parameters);
		validateResponseToContinueTest(response, 204, "Get active approvers available api call was not successful.", true);
	}
}
