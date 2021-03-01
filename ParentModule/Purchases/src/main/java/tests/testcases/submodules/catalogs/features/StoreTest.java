package tests.testcases.submodules.catalogs.features;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import dataproviders.submodules.catalogs.features.StoreDataProviders;
import dto.submodules.catalogs.store.StoreResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import tests.testcases.submodules.catalogs.CatalogsBaseTest;
import tests.testhelpers.submodules.catalogs.features.StoreTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class StoreTest extends CatalogsBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("StoreTest");
	}

	/**
	 * Goal: Check that is feasible to perform a GET stores. 
	 * @param userName
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(groups = {ExeGroups.Sanity}, dataProvider = "storeDataProvider", dataProviderClass = StoreDataProviders.class)
	public void getStoreTest(String userName, String countryName) throws Exception {
		Response response = new StoreTestHelper(userName).getStores(countryName);
		validateResponseToContinueTest(response, 200, "Get stores api call was not successful.", true);
		StoreResponseDTO storeResponseDTO = response.as(StoreResponseDTO.class, ObjectMapperType.GSON);
		assertEquals(storeResponseDTO.getStatus(), "OK", "getStatus issue");
		assertEquals(storeResponseDTO.getMessage(), "OK", "getMessage issue");
		assertNotNull(storeResponseDTO.getContent(), "getContent issue");
	}

}
