package tests.testcases.submodules.requisition.features;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import dataproviders.submodules.requisition.RequesterPUMADataProviders;
import dto.submodules.requisition.get.GetRequisitionResponseDTO;
import parameters.submodules.requisition.GetRequisitionParameters;
import tests.testcases.submodules.requisition.RequisitionBaseTest;
import tests.testhelpers.submodules.requisition.features.GetRequisitionTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class RequesterPUMA extends RequisitionBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("RequesterPUMA");
	}

	/**
	 * GET requisition in reception completed state. 
	 * @param GetRequisitionParameters
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(groups = {ExeGroups.Sanity}, dataProvider = "receptionCompleted", dataProviderClass = RequesterPUMADataProviders.class)
	public void getReceptionCompleted(GetRequisitionParameters parameters) throws Exception {
		GetRequisitionResponseDTO getRequisitionResponseDTO = new GetRequisitionTestHelper(parameters.getUserName()).getRequisitions(parameters);
		assertEquals(getRequisitionResponseDTO.getStatus(), "OK", "getStatus issue");
		assertEquals(getRequisitionResponseDTO.getMessage(), "Success", "getMessage issue");
		assertNotNull(getRequisitionResponseDTO.getContent(), "getContent issue");
	}
	
	/**
	 * GET requisition in draft state. 
	 * @param parameters
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(dataProvider = "draftRequisitions", dataProviderClass = RequesterPUMADataProviders.class)
	public void getDraft(GetRequisitionParameters parameters) throws Exception {
		GetRequisitionResponseDTO getRequisitionResponseDTO = new GetRequisitionTestHelper(parameters.getUserName()).getRequisitions(parameters);
		assertEquals(getRequisitionResponseDTO.getStatus(), "OK", "getStatus issue");
		assertEquals(getRequisitionResponseDTO.getMessage(), "Success", "getMessage issue");
		assertNotNull(getRequisitionResponseDTO.getContent(), "getContent issue");
	}
	
	/**
	 * GET partially pending requisitions test
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "partiallyPending", dataProviderClass = RequesterPUMADataProviders.class)
	public void getPartiallyPending(GetRequisitionParameters parameters) throws Exception {
		GetRequisitionResponseDTO getRequisitionResponseDTO = new GetRequisitionTestHelper(parameters.getUserName()).getRequisitions(parameters);
		assertEquals(getRequisitionResponseDTO.getStatus(), "OK", "getStatus issue");
		assertEquals(getRequisitionResponseDTO.getMessage(), "Success", "getMessage issue");
		assertNotNull(getRequisitionResponseDTO.getContent(), "getContent issue");
	}
	
	/**
	 * GET partially pending draft requisitions
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "partiallyPendingDraft", dataProviderClass = RequesterPUMADataProviders.class)
	public void getPartiallyPendingDraft(GetRequisitionParameters parameters) throws Exception {
		GetRequisitionResponseDTO getRequisitionResponseDTO = new GetRequisitionTestHelper(parameters.getUserName()).getRequisitions(parameters);
		assertEquals(getRequisitionResponseDTO.getStatus(), "OK", "getStatus issue");
		assertEquals(getRequisitionResponseDTO.getMessage(), "Success", "getMessage issue");
		assertNotNull(getRequisitionResponseDTO.getContent(), "getContent issue");
	}
	
	/**
	 * GET pending approval requisitions
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "pendingApproval", dataProviderClass = RequesterPUMADataProviders.class)
	public void getPendingApproval(GetRequisitionParameters parameters) throws Exception {
		GetRequisitionResponseDTO getRequisitionResponseDTO = new GetRequisitionTestHelper(parameters.getUserName()).getRequisitions(parameters);
		assertEquals(getRequisitionResponseDTO.getStatus(), "OK", "getStatus issue");
		assertEquals(getRequisitionResponseDTO.getMessage(), "Success", "getMessage issue");
		assertNotNull(getRequisitionResponseDTO.getContent(), "getContent issue");
	}
	
	/**
	 * GET approved requisitions
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "approved", dataProviderClass = RequesterPUMADataProviders.class)
	public void getApproved(GetRequisitionParameters parameters) throws Exception {
		GetRequisitionResponseDTO getRequisitionResponseDTO = new GetRequisitionTestHelper(parameters.getUserName()).getRequisitions(parameters);
		assertEquals(getRequisitionResponseDTO.getStatus(), "OK", "getStatus issue");
		assertEquals(getRequisitionResponseDTO.getMessage(), "Success", "getMessage issue");
		assertNotNull(getRequisitionResponseDTO.getContent(), "getContent issue");
	}
	
	/**
	 * GET partially rejected requisitions
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "partiallyRejected", dataProviderClass = RequesterPUMADataProviders.class)
	public void getPartiallyRejected(GetRequisitionParameters parameters) throws Exception {
		GetRequisitionResponseDTO getRequisitionResponseDTO = new GetRequisitionTestHelper(parameters.getUserName()).getRequisitions(parameters);
		assertEquals(getRequisitionResponseDTO.getStatus(), "OK", "getStatus issue");
		assertEquals(getRequisitionResponseDTO.getMessage(), "Success", "getMessage issue");
		assertNotNull(getRequisitionResponseDTO.getContent(), "getContent issue");
	}
	
	/**
	 * GET rejected by approver requisitions
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "rejectedByApprover", dataProviderClass = RequesterPUMADataProviders.class)
	public void getRejectedByApprover(GetRequisitionParameters parameters) throws Exception {
		GetRequisitionResponseDTO getRequisitionResponseDTO = new GetRequisitionTestHelper(parameters.getUserName()).getRequisitions(parameters);
		assertEquals(getRequisitionResponseDTO.getStatus(), "OK", "getStatus issue");
		assertEquals(getRequisitionResponseDTO.getMessage(), "Success", "getMessage issue");
		assertNotNull(getRequisitionResponseDTO.getContent(), "getContent issue");
	}
	
	/**
	 * GET rejected by purchaser requisitions
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "rejectedByPurchaser", dataProviderClass = RequesterPUMADataProviders.class)
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
	@Test(dataProvider = "purchaseOrderCreated", dataProviderClass = RequesterPUMADataProviders.class)
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
	@Test(dataProvider = "receptionPending", dataProviderClass = RequesterPUMADataProviders.class)
	public void getReceptionPending(GetRequisitionParameters parameters) throws Exception {
		GetRequisitionResponseDTO getRequisitionResponseDTO = new GetRequisitionTestHelper(parameters.getUserName()).getRequisitions(parameters);
		assertEquals(getRequisitionResponseDTO.getStatus(), "OK", "getStatus issue");
		assertEquals(getRequisitionResponseDTO.getMessage(), "Success", "getMessage issue");
		assertNotNull(getRequisitionResponseDTO.getContent(), "getContent issue");
	}

	/**
	 * GET requisitions global view
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "globalView", dataProviderClass = RequesterPUMADataProviders.class)
	public void getGlobalView(GetRequisitionParameters parameters) throws Exception {
		GetRequisitionResponseDTO getRequisitionResponseDTO = new GetRequisitionTestHelper(parameters.getUserName()).getRequisitions(parameters);
		assertEquals(getRequisitionResponseDTO.getStatus(), "OK", "getStatus issue");
		assertEquals(getRequisitionResponseDTO.getMessage(), "Success", "getMessage issue");
		assertNotNull(getRequisitionResponseDTO.getContent(), "getContent issue");
	}

}
