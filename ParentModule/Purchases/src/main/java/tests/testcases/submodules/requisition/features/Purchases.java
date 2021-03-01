package tests.testcases.submodules.requisition.features;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import dataproviders.submodules.requisition.ForPurchasesDataProviders;
import dto.submodules.requisition.get.GetRequisitionResponseDTO;
import parameters.submodules.requisition.GetRequisitionParameters;
import tests.testcases.submodules.requisition.RequisitionBaseTest;
import tests.testhelpers.submodules.requisition.features.GetRequisitionTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class Purchases extends RequisitionBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("Purchases");
	}

	/**
	 * GET rejected by purchaser requisitions
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "rejectedByPurchaser", dataProviderClass = ForPurchasesDataProviders.class)
	public void getRejectedByPurchaser(GetRequisitionParameters parameters) throws Exception {
		GetRequisitionResponseDTO getRequisitionResponseDTO = new GetRequisitionTestHelper(parameters.getUserName()).getRequisitions(parameters);
		assertEquals(getRequisitionResponseDTO.getStatus(), "OK", "getStatus issue");
		assertEquals(getRequisitionResponseDTO.getMessage(), "Success", "getMessage issue");
		assertNotNull(getRequisitionResponseDTO.getContent(), "getContent issue");
	}

	/**
	 * GET requisitions with purchase order created  
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "purchaseOrderCreated", dataProviderClass = ForPurchasesDataProviders.class)
	public void getPurchaseOrderCreated(GetRequisitionParameters parameters) throws Exception {
		GetRequisitionResponseDTO getRequisitionResponseDTO = new GetRequisitionTestHelper(parameters.getUserName()).getRequisitions(parameters);
		assertEquals(getRequisitionResponseDTO.getStatus(), "OK", "getStatus issue");
		assertEquals(getRequisitionResponseDTO.getMessage(), "Success", "getMessage issue");
		assertNotNull(getRequisitionResponseDTO.getContent(), "getContent issue");
	}

	/**
	 * GET requisitions with reception pending
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "receptionPending", dataProviderClass = ForPurchasesDataProviders.class)
	public void getReceptionPending(GetRequisitionParameters parameters) throws Exception {
		GetRequisitionResponseDTO getRequisitionResponseDTO = new GetRequisitionTestHelper(parameters.getUserName()).getRequisitions(parameters);
		assertEquals(getRequisitionResponseDTO.getStatus(), "OK", "getStatus issue");
		assertEquals(getRequisitionResponseDTO.getMessage(), "Success", "getMessage issue");
		assertNotNull(getRequisitionResponseDTO.getContent(), "getContent issue");
	}
	
	/**
	 * GET requisitions with reception pending
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "receptionCompleted", dataProviderClass = ForPurchasesDataProviders.class)
	public void getReceptionCompleted(GetRequisitionParameters parameters) throws Exception {
		GetRequisitionResponseDTO getRequisitionResponseDTO = new GetRequisitionTestHelper(parameters.getUserName()).getRequisitions(parameters);
		assertEquals(getRequisitionResponseDTO.getStatus(), "OK", "getStatus issue");
		assertEquals(getRequisitionResponseDTO.getMessage(), "Success", "getMessage issue");
		assertNotNull(getRequisitionResponseDTO.getContent(), "getContent issue");
	}

	/**
	 * GET requisitions with reception pending
	 * @param parameters
	 * @throws Exception
	 */
	@Test(groups = {ExeGroups.Sanity}, dataProvider = "globalView", dataProviderClass = ForPurchasesDataProviders.class)
	public void getGlobalView(GetRequisitionParameters parameters) throws Exception {
		GetRequisitionResponseDTO getRequisitionResponseDTO = new GetRequisitionTestHelper(parameters.getUserName()).getRequisitions(parameters);
		assertEquals(getRequisitionResponseDTO.getStatus(), "OK", "getStatus issue");
		assertEquals(getRequisitionResponseDTO.getMessage(), "Success", "getMessage issue");
		assertNotNull(getRequisitionResponseDTO.getContent(), "getContent issue");
	}

}
