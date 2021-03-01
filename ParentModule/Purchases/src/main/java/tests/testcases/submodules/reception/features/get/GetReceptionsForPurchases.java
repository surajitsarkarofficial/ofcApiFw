package tests.testcases.submodules.reception.features.get;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import dataproviders.submodules.reception.ReceptionsForPurchaserDataProviders;
import dto.submodules.reception.get.GetReceptionResponseDTO;
import parameters.submodules.reception.GetReceptionParameters;
import tests.testcases.submodules.reception.ReceptionBaseTest;
import tests.testhelpers.submodules.reception.features.GetReceptionTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class GetReceptionsForPurchases extends ReceptionBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("GetReceptionsForPurchases");
	}

	/**
	 * Get global view
	 * @param parameters
	 * @throws Exception
	 */
	@Test(groups = {ExeGroups.Sanity}, dataProvider = "globalView", dataProviderClass = ReceptionsForPurchaserDataProviders.class)
	public void getGlobalView(GetReceptionParameters parameters) throws Exception {
		GetReceptionResponseDTO responseDTO = new GetReceptionTestHelper(parameters.getUserName()).getReception(parameters);
		assertEquals(responseDTO.getStatus(), "OK", "getStatus issue");
		assertEquals(responseDTO.getMessage(), "Success", "getMessage issue");
		assertNotNull(responseDTO.getContent(), "getContent issue");
	}

	/**
	 * Get completed
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "completed", dataProviderClass = ReceptionsForPurchaserDataProviders.class)
	public void getCompleted(GetReceptionParameters parameters) throws Exception {
		GetReceptionResponseDTO responseDTO = new GetReceptionTestHelper(parameters.getUserName()).getReception(parameters);
		assertEquals(responseDTO.getStatus(), "OK", "getStatus issue");
		assertEquals(responseDTO.getMessage(), "Success", "getMessage issue");
		assertNotNull(responseDTO.getContent(), "getContent issue");
	}

	/**
	 * Get in progress
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "inProgress", dataProviderClass = ReceptionsForPurchaserDataProviders.class)
	public void getInProgress(GetReceptionParameters parameters) throws Exception {
		GetReceptionResponseDTO responseDTO = new GetReceptionTestHelper(parameters.getUserName()).getReception(parameters);
		assertEquals(responseDTO.getStatus(), "OK", "getStatus issue");
		assertEquals(responseDTO.getMessage(), "Success", "getMessage issue");
		assertNotNull(responseDTO.getContent(), "getContent issue");
	}

	/**
	 * Get my receptions
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "myReceptions", dataProviderClass = ReceptionsForPurchaserDataProviders.class)
	public void getMyReceptions(GetReceptionParameters parameters) throws Exception {
		GetReceptionResponseDTO responseDTO = new GetReceptionTestHelper(parameters.getUserName()).getReception(parameters);
		assertEquals(responseDTO.getStatus(), "OK", "getStatus issue");
		assertEquals(responseDTO.getMessage(), "Success", "getMessage issue");
		assertNotNull(responseDTO.getContent(), "getContent issue");
	}


}
