package tests.testcases.submodules.manage.features.approverManagement;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.manage.features.CecoDataProviders;
import dto.submodules.manage.approverManagement.getCecoByApprover.GetCecoByApproverResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import parameters.submodules.manage.features.approverManagement.GetActiveApproversAvailabletParameters;
import parameters.submodules.manage.features.approverManagement.getCecoByApproverParameters.GetCecoByApproverParameters;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.features.approverManagement.GetActiveApproversAvailableTestHelper;
import tests.testhelpers.submodules.manage.features.approverManagement.GetCecoByApproverTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class GetCecoByApproverTests extends ManageBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("GetCecoByApproverTests");
	}
	
	/**
	 * Check that is feasible to perform a GET cecos by approver. 
	 * @param parameters
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(dataProvider = "cecoByApproverParameters", dataProviderClass = CecoDataProviders.class, groups = { ExeGroups.NotAvailableInPreProd })
	public void getCecoByApproverTest(GetCecoByApproverParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		parameters.setApproverId(new GetActiveApproversAvailableTestHelper(parameters.getUsername()).getActiveApproversAvailableFromScratch(new GetActiveApproversAvailabletParameters())
				.getContent().getActiveApprovers().get(0).getGloberId());
		Response response = new GetCecoByApproverTestHelper(parameters.getUsername()).getCecoByApprover(parameters);
		validateResponseToContinueTest(response, 200, "Get ceco by approver api call was not successful.", true);
		GetCecoByApproverResponseDTO ceco = response.as(GetCecoByApproverResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(ceco.getStatus(), "Success", "getStatus issue");
		softAssert.assertEquals(ceco.getMessage(), "OK", "getMessage issue");
		softAssert.assertTrue(Integer.valueOf(ceco.getContent().getTotalElements())>0, "getTotalElements issue");
		softAssert.assertEquals(ceco.getContent().getGlober().getGloberId(), parameters.getApproverId(), "getApproverId issue");
		softAssert.assertNotNull(ceco.getContent().getGlober().getGloberName(), "getGloberName issue");
		softAssert.assertNotNull(ceco.getContent().getGlober().getGloberPosition(), "getGloberPosition issue");
		softAssert.assertNotNull(ceco.getContent().getGlober().getMaxAmount(), "getMaxAmount issue");
		softAssert.assertNotNull(ceco.getContent().getGlober().getRole(), "getRole issue");
		softAssert.assertTrue(Integer.valueOf(ceco.getContent().getGlober().getCecos().size())>0, "getCecos size issue");
		softAssert.assertAll();
	}

}
