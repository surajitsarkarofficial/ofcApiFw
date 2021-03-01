package tests.testcases.submodules.catalogs.features;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import dataproviders.submodules.catalogs.features.VendorDataProviders;
import dto.submodules.catalogs.vendor.VendorResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import tests.testcases.submodules.catalogs.CatalogsBaseTest;
import tests.testhelpers.submodules.catalogs.features.VendorTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class VendorTest extends CatalogsBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("VendorTest");
	}

	/**
	 * Goal: Check that is feasible to perform a GET vendors. 
	 * @param userName
	 * @param storeName
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(groups = {ExeGroups.Sanity}, dataProvider = "vendorDataProvider", dataProviderClass = VendorDataProviders.class)
	public void getVendorTest(String userName, String storeName, String countryName) throws Exception {
		Response response = new VendorTestHelper(userName).getVendors(storeName, countryName);
		validateResponseToContinueTest(response, 200, "Get vendors api call was not successful.", true);
		VendorResponseDTO vendorResponseDTO = response.as(VendorResponseDTO.class, ObjectMapperType.GSON);
		assertEquals(vendorResponseDTO.getStatus(), "OK", "getStatus issue");
		assertEquals(vendorResponseDTO.getMessage(), "OK", "getMessage issue");
		assertNotNull(vendorResponseDTO.getContent(), "getContent issue");
	}
}
