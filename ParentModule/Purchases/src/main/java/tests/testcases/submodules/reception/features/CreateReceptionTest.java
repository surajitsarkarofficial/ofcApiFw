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
import tests.testhelpers.submodules.reception.features.GetPurchaseOrderTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class CreateReceptionTest extends ReceptionBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("CreateReceptionTest");
	}

	/**
	 * Goal: Check that is feasible to create a reception. 
	 * @param parameters
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(dataProvider = "purchaseOrder", dataProviderClass = PurchaseOrderDataProviders.class, groups = {ExeGroups.Sanity})
	public void createReceptionTest(PurchaseOrderParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		ReceptionPayLoadHelper payload = new ReceptionPayLoadHelper(new GetPurchaseOrderTestHelper(parameters.getUserName()).getRandomPurchaseOrder(parameters));
		Response response = new CreateReceptionTestHelper(parameters.getUserName()).createReception(payload);
		validateResponseToContinueTest(response, 201, "POST reception api call was not successful.", true);
		ReceptionResponseDTO reception = response.as(ReceptionResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(reception.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(reception.getMessage(), "Created", "getMessage issue");
		softAssert.assertEquals(reception.getContent().getReceptionItemResponse().getItemsList().get(0).getItemNumber(), payload.getItemsList().get(0).getItemNumber(), "getItemNumber issue");
		softAssert.assertEquals(reception.getContent().getReceptionItemResponse().getItemsList().get(0).getPoNumber(), payload.getItemsList().get(0).getPoNumber(), "getPoNumber issue");
		softAssert.assertAll();
	}
	
}
