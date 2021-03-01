package tests.testcases.submodules.catalogs.features;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import dataproviders.submodules.catalogs.features.MaterialDataProviders;
import dto.submodules.catalogs.material.MaterialResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import tests.testcases.submodules.catalogs.CatalogsBaseTest;
import tests.testhelpers.submodules.catalogs.features.MaterialTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class MaterialTest extends CatalogsBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("MaterialTest");
	}

	/**
	 * Goal: Check that is feasible to perform a GET materials. 
	 * @param userName
	 * @param groupName
	 * @param storeName
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(groups = {ExeGroups.Sanity}, dataProvider = "materialDataProvider", dataProviderClass = MaterialDataProviders.class)
	public void getMaterialTest(String userName, String groupName, String countryName) throws Exception {
		Response response = new MaterialTestHelper(userName).getMaterials(groupName, countryName);
		validateResponseToContinueTest(response, 200, "Get materials api call was not successful.", true);
		MaterialResponseDTO materialResponseDTO = response.as(MaterialResponseDTO.class, ObjectMapperType.GSON);
		assertEquals(materialResponseDTO.getStatus(), "OK", "getStatus issue");
		assertEquals(materialResponseDTO.getMessage(), "OK", "getMessage issue");
		assertNotNull(materialResponseDTO.getContent(), "getContent issue");
	}
	
}
