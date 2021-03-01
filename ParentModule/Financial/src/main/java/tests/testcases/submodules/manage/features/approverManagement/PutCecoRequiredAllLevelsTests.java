package tests.testcases.submodules.manage.features.approverManagement;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.manage.features.CecoDataProviders;
import dto.submodules.manage.approverManagement.putCecoRequiredAllLevels.PutCecoRequiredAllLevelsResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import parameters.submodules.manage.features.approverManagement.putCecoRequiredAllLevels.CecoRequiredAllLevelsParameters;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.features.approverManagement.PutCecoRequiredAllLevelsTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class PutCecoRequiredAllLevelsTests extends ManageBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("PutCecoRequiredAllLevelsTests");
	}

	/**
	 * Check that is feasible to update the required all levels field from a ceco.
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "cecoRequiredAllLevels", dataProviderClass = CecoDataProviders.class
			, groups = { ExeGroups.NotAvailableInPreProd })
	public void putCecoRequiredAllLevelsTests(CecoRequiredAllLevelsParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		Response response = new PutCecoRequiredAllLevelsTestHelper(parameters.getUsername()).putCecoRequiredAllLevels(parameters.getCecoNumber(), parameters.getCecoRequiredAllLevelsNewStatus());
		validateResponseToContinueTest(response, 200, "update ceco api call was not successful.", true);
		PutCecoRequiredAllLevelsResponseDTO cecos = response.as(PutCecoRequiredAllLevelsResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(cecos.getStatus(), "Success", "getStatus issue");
		softAssert.assertEquals(cecos.getMessage(), "OK", "getMessage issue");
		softAssert.assertEquals(cecos.getContent().getCecoNumber(), parameters.getCecoNumber(), "getCeconumber issue");
		softAssert.assertEquals(cecos.getContent().getRequiredAllLevels(), parameters.getCecoRequiredAllLevelsNewStatus(), "getCecoRequiredAllLevelsNewStatus issue");
		softAssert.assertAll();
		response = new PutCecoRequiredAllLevelsTestHelper(parameters.getUsername()).putCecoRequiredAllLevels(parameters.getCecoNumber(), parameters.getCecoRequiredAllLevelsOriginalStatus());
		validateResponseToContinueTest(response, 200, "update ceco api call was not successful.", true);
		cecos = response.as(PutCecoRequiredAllLevelsResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(cecos.getStatus(), "Success", "getStatus issue");
		softAssert.assertEquals(cecos.getMessage(), "OK", "getMessage issue");
		softAssert.assertEquals(cecos.getContent().getCecoNumber(), parameters.getCecoNumber(), "getCeconumber issue");
		softAssert.assertEquals(cecos.getContent().getRequiredAllLevels(), parameters.getCecoRequiredAllLevelsOriginalStatus(), "getCecoRequiredAllLevelsOriginalStatus issue");
		softAssert.assertAll();
	}

}
