package tests.testcases.submodules.reception.features.get;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import dataproviders.submodules.reception.ReceptionsForRequesterDataProviders;
import dto.submodules.reception.get.GetReceptionResponseDTO;
import parameters.submodules.reception.GetReceptionParameters;
import tests.testcases.submodules.reception.ReceptionBaseTest;
import tests.testhelpers.submodules.reception.features.GetReceptionTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class GetReceptionsForRequester extends ReceptionBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("GetReceptionsForRequester");
	}

	/**
	 * Get global view
	 * @param parameters
	 * @throws Exception
	 */
	@Test(groups = {ExeGroups.Sanity}, dataProvider = "globalView", dataProviderClass = ReceptionsForRequesterDataProviders.class)
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
	@Test(dataProvider = "completed", dataProviderClass = ReceptionsForRequesterDataProviders.class)
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
	@Test(dataProvider = "inProgress", dataProviderClass = ReceptionsForRequesterDataProviders.class)
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
	@Test(dataProvider = "myReceptions", dataProviderClass = ReceptionsForRequesterDataProviders.class)
	public void getMyReceptions(GetReceptionParameters parameters) throws Exception {
		GetReceptionResponseDTO responseDTO = new GetReceptionTestHelper(parameters.getUserName()).getReception(parameters);
		assertEquals(responseDTO.getStatus(), "OK", "getStatus issue");
		assertEquals(responseDTO.getMessage(), "Success", "getMessage issue");
		assertNotNull(responseDTO.getContent(), "getContent issue");
	}

}
