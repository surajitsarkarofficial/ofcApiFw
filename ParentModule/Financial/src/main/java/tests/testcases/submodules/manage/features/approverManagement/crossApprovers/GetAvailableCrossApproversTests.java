package tests.testcases.submodules.manage.features.approverManagement.crossApprovers;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.manage.features.CrossApproversDataProviders;
import dto.submodules.manage.approverManagement.crossApprovers.getAvailableCrossApprovers.GetAvailableCrossApproversResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import parameters.submodules.manage.features.approverManagement.crossApprovers.GetAvailableCrossApproversParameters;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.features.approverManagement.crossApprovers.GetAvailableCrossApproversTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class GetAvailableCrossApproversTests extends ManageBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("GetAvailableCrossApproversTests");
	}

	/**
	 * Check that is feasible to perform a GET active approvers available. 
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "getAvailableCrossApprovers", dataProviderClass = CrossApproversDataProviders.class, groups = { ExeGroups.NotAvailableInPreProd })
	public void getAvailableCrossApproversTest(GetAvailableCrossApproversParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		Response response = new GetAvailableCrossApproversTestHelper(parameters.getUsername()).getAvailableCrossApprovers(parameters);
		validateResponseToContinueTest(response, 200, "Get active approvers available api call was not successful.", true);
		GetAvailableCrossApproversResponseDTO crossApprovers = response.as(GetAvailableCrossApproversResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(crossApprovers.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(crossApprovers.getMessage(), "Success", "getMessage issue");
		softAssert.assertEquals(crossApprovers.getContent().getTotalItems(), "5", "getTotalItems issue");
		softAssert.assertTrue(Integer.valueOf(crossApprovers.getContent().getAvailableApprovers().get(0).getId())>0, "getId issue");
		softAssert.assertNotNull(crossApprovers.getContent().getAvailableApprovers().get(0).getCompleteName(), "getCompleteName issue");
		softAssert.assertNotNull(crossApprovers.getContent().getAvailableApprovers().get(0).getUserName(), "getUserName issue");
		softAssert.assertNotNull(crossApprovers.getContent().getAvailableApprovers().get(0).getPosition(), "getPosition issue");
		softAssert.assertNotNull(crossApprovers.getContent().getAvailableApprovers().get(0).getRole(), "getRole issue");
		softAssert.assertTrue(Double.valueOf(crossApprovers.getContent().getAvailableApprovers().get(0).getMaxAmount())>0, "getMaxAmount issue");
		softAssert.assertAll();
	}	

}
