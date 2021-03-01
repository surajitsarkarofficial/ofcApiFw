package tests.testcases.submodules.reception.features;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.purchaseOrder.PurchaseOrderDataProviders;
import dto.submodules.reception.post.ReceptionResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import parameters.submodules.purchaseOrder.PurchaseOrderParameters;
import payloads.submodules.reception.ReceptionPayLoadHelper;
import tests.testcases.submodules.reception.ReceptionBaseTest;
import tests.testhelpers.submodules.reception.features.CreateReceptionTestHelper;
import tests.testhelpers.submodules.reception.features.UpdateReceptionTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class UpdateReceptionTest extends ReceptionBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("UpdateReceptionTest");
	}

	/**
	 * Goal: Check that is feasible to update a reception. 
	 * @param parameters
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(dataProvider = "purchaseOrder", dataProviderClass = PurchaseOrderDataProviders.class, groups = {ExeGroups.Sanity})
	public void updateReceptionTest(PurchaseOrderParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		ReceptionResponseDTO reception = new CreateReceptionTestHelper(parameters.getUserName()).createReceptionFromScratch(parameters);
		ReceptionPayLoadHelper payload = new ReceptionPayLoadHelper(reception);
		Response response = new UpdateReceptionTestHelper(parameters.getUserName()).updateReception(payload);
		validateResponseToContinueTest(response, 200, "PUT reception api call was not successful.", true);
		ReceptionResponseDTO updatedReception = response.as(ReceptionResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(updatedReception.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(updatedReception.getMessage(), "Updated", "getMessage issue");
		softAssert.assertAll();
	}
	
}
