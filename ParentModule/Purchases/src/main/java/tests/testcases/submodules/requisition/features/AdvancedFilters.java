package tests.testcases.submodules.requisition.features;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import dataproviders.submodules.requisition.AdvancedFiltersDataProviders;
import dto.submodules.requisition.get.GetRequisitionResponseDTO;
import parameters.submodules.requisition.GetRequisitionParameters;
import tests.testcases.submodules.requisition.RequisitionBaseTest;
import tests.testhelpers.submodules.requisition.features.GetRequisitionTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class AdvancedFilters extends RequisitionBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("AdvancedFilters");
	}

	/**
	 * Filter by group
	 * @param parameters
	 * @throws Exception
	 */
	@Test(groups = {ExeGroups.Sanity}, dataProvider = "filterByGroup", dataProviderClass = AdvancedFiltersDataProviders.class)
	public void filterByGroup(GetRequisitionParameters parameters) throws Exception {
		GetRequisitionResponseDTO getRequisitionResponseDTO = new GetRequisitionTestHelper(parameters.getUserName()).getRequisitions(parameters);
		assertEquals(getRequisitionResponseDTO.getStatus(), "OK", "getStatus issue");
		assertEquals(getRequisitionResponseDTO.getMessage(), "Success", "getMessage issue");
		assertNotNull(getRequisitionResponseDTO.getContent(), "getContent issue");
	}

	/**
	 * Filter by requester
	 * @param parameters
	 * @throws Exception
	 */
	@Test(groups = {ExeGroups.Sanity}, dataProvider = "filterByRequester", dataProviderClass = AdvancedFiltersDataProviders.class)
	public void filterByRequester(GetRequisitionParameters parameters) throws Exception {
		GetRequisitionResponseDTO getRequisitionResponseDTO = new GetRequisitionTestHelper(parameters.getUserName()).getRequisitions(parameters);
		assertEquals(getRequisitionResponseDTO.getStatus(), "OK", "getStatus issue");
		assertEquals(getRequisitionResponseDTO.getMessage(), "Success", "getMessage issue");
		assertNotNull(getRequisitionResponseDTO.getContent(), "getContent issue");
	}

}
