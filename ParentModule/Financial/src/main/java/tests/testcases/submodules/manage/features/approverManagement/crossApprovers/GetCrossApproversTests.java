package tests.testcases.submodules.manage.features.approverManagement.crossApprovers;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.manage.features.CrossApproversDataProviders;
import dto.submodules.manage.approverManagement.crossApprovers.getCrossApprovers.GetCrossApproversResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import parameters.submodules.manage.features.approverManagement.crossApprovers.GetCrossApproversParameters;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.features.approverManagement.crossApprovers.GetCrossApproversTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class GetCrossApproversTests extends ManageBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("GetCrossApproversTests");
	}

	/**
	 * Check that is feasible to perform a GET active approvers available. 
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "getCrossApprovers", dataProviderClass = CrossApproversDataProviders.class, groups = { ExeGroups.NotAvailableInPreProd })
	public void getCrossApproversTest(GetCrossApproversParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		Response response = new GetCrossApproversTestHelper(parameters.getUsername()).getCrossApprovers(parameters);
		validateResponseToContinueTest(response, 200, "Get cross approvers available api call was not successful.", true);
		GetCrossApproversResponseDTO crossApprovers = response.as(GetCrossApproversResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(crossApprovers.getStatus(), "Success", "getStatus issue");
		softAssert.assertEquals(crossApprovers.getMessage(), "OK", "getMessage issue");
		softAssert.assertTrue(Integer.valueOf(crossApprovers.getContent().getTotalElements())>0, "getTotalElements issue");
		softAssert.assertTrue(Integer.valueOf(crossApprovers.getContent().getCrossApprovers().get(0).getGloberId())>0, "getGloberId issue");
		softAssert.assertNotNull(crossApprovers.getContent().getCrossApprovers().get(0).getGloberName(), "getGloberName issue");
		softAssert.assertEquals(crossApprovers.getContent().getCrossApprovers().get(0).getActive(), "true", "getActive issue");
		softAssert.assertTrue(Double.valueOf(crossApprovers.getContent().getCrossApprovers().get(0).getMaxAmount())>=0, "getMaxAmount issue");
		softAssert.assertNotNull(crossApprovers.getContent().getCrossApprovers().get(0).getGloberPosition(), "getGloberPosition issue");
		softAssert.assertAll();
	}	

}
