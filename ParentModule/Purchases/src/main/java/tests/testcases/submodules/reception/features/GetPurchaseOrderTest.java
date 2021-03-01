package tests.testcases.submodules.reception.features;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.purchaseOrder.PurchaseOrderDataProviders;
import dto.submodules.purchaseOrder.get.PurchaseOrderResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import parameters.submodules.purchaseOrder.PurchaseOrderParameters;
import tests.testcases.submodules.reception.ReceptionBaseTest;
import tests.testhelpers.submodules.reception.features.GetPurchaseOrderTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class GetPurchaseOrderTest extends ReceptionBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("GetPurchaseOrderTest");
	}
	
	/**
	 * Goal: Check that is feasible to perform a GET purchase orders. 
	 * @param parameters
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(dataProvider = "purchaseOrder", dataProviderClass = PurchaseOrderDataProviders.class, groups = {ExeGroups.Sanity})
	public void getPurchaseOrdersTest(PurchaseOrderParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		Response response = new GetPurchaseOrderTestHelper(parameters.getUserName()).getPurchaseOrders(parameters);
		validateResponseToContinueTest(response, 200, "Get purchase orders api call was not successful.", true);
		PurchaseOrderResponseDTO purchase = response.as(PurchaseOrderResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(purchase.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(purchase.getMessage(), "Success", "getMessage issue");
		softAssert.assertNotNull(purchase.getContent(), "getContent issue");
		softAssert.assertAll();
	}
	
}
