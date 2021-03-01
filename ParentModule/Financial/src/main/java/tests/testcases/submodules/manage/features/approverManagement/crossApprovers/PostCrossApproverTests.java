package tests.testcases.submodules.manage.features.approverManagement.crossApprovers;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.manage.features.CrossApproversDataProviders;
import dto.submodules.manage.approverManagement.crossApprovers.postCrossApprover.PostCrossApproverResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import parameters.submodules.manage.features.approverManagement.crossApprovers.PostCrossApproverParameters;
import payloads.submodules.manage.features.approverManagement.crossApprover.CrossApproverPayLoadHelper;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.features.approverManagement.crossApprovers.PostCrossApproverTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class PostCrossApproverTests extends ManageBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("PostCrossApproverTests");
	}

	/**
	 * Check that is feasible to create a new cross approver. 
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "newCrossApprover", dataProviderClass = CrossApproversDataProviders.class, groups = { ExeGroups.NotAvailableInPreProd })
	public void createCrossApproverTest(PostCrossApproverParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		CrossApproverPayLoadHelper payload = new CrossApproverPayLoadHelper(parameters);
		Response response = new PostCrossApproverTestHelper(parameters.getUsername()).postCrossApprover(payload);
		validateResponseToContinueTest(response, 200, "POST cross approver api call was not successful.", true);
		PostCrossApproverResponseDTO crossApprover = response.as(PostCrossApproverResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(crossApprover.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(crossApprover.getMessage(), "Success", "getMessage issue");
		softAssert.assertEquals(crossApprover.getContent().getTotalItems(), "1", "getTotalItems issue");
		softAssert.assertTrue(Long.valueOf(crossApprover.getContent().getApprovers().get(0).getId())>0, "getId issue");
		softAssert.assertTrue(Long.valueOf(crossApprover.getContent().getApprovers().get(0).getGloberId())>0, "getGloberId issue");
		softAssert.assertNotNull(crossApprover.getContent().getApprovers().get(0).getPosition(), "getPosition issue");
		softAssert.assertNotNull(crossApprover.getContent().getApprovers().get(0).getRole(), "getRole issue");
		softAssert.assertTrue(Double.valueOf(crossApprover.getContent().getApprovers().get(0).getMaxAmount())>=0, "getGloberId issue");
		softAssert.assertEquals(crossApprover.getContent().getApprovers().get(0).getActive(), "true", "getActive issue");
		softAssert.assertEquals(crossApprover.getContent().getApprovers().get(0).getResponseCode(), "0", "getResponseCode issue");
		softAssert.assertEquals(crossApprover.getContent().getApprovers().get(0).getResponseDescription(), "Created", "getResponseDescription issue");
		softAssert.assertAll();
	}

}
