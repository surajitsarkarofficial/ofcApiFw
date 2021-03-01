package tests.testcases.submodules.catalogs.features;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import dataproviders.PurchasesDataProviders;
import dto.submodules.catalogs.country.CountryResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import tests.testcases.submodules.catalogs.CatalogsBaseTest;
import tests.testhelpers.submodules.catalogs.features.CountryTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class CountryTest extends CatalogsBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("CountryTest");
	}
	
	/**
	 * Goal: Check that is feasible to perform a GET countries. 
	 * @param userName
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(groups = {ExeGroups.Sanity}, dataProvider = "serviceDeskUser", dataProviderClass = PurchasesDataProviders.class)
	public void getCountryTest(String userName) throws Exception {
		Response response = new CountryTestHelper(userName).getCountry();
		validateResponseToContinueTest(response, 200, "Get countries api call was not successful.", true);
		CountryResponseDTO countryResponseDTO = response.as(CountryResponseDTO.class, ObjectMapperType.GSON);
		assertEquals(countryResponseDTO.getStatus(), "OK", "getStatus issue");
		assertEquals(countryResponseDTO.getMessage(), "OK", "getMessage issue");
		assertNotNull(countryResponseDTO.getContent(), "getContent issue");
	}
	
}
