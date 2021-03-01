package tests.testcases.submodules.catalogs.features;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import dataproviders.PurchasesDataProviders;
import dto.submodules.catalogs.currency.CurrencyResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import tests.testcases.submodules.catalogs.CatalogsBaseTest;
import tests.testhelpers.submodules.catalogs.features.CurrencyTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class CurrencyTest extends CatalogsBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("CurrencyTest");
	}

	/**
	 * Goal: Check that is feasible to perform a GET currencies. 
	 * @param userName
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(groups = {ExeGroups.Sanity}, dataProvider = "serviceDeskUser", dataProviderClass = PurchasesDataProviders.class)
	public void getCurrencyTest(String userName) throws Exception {
		Response response = new CurrencyTestHelper(userName).getCurrencies();
		validateResponseToContinueTest(response, 200, "Get currency api call was not successful.", true);
		CurrencyResponseDTO groupResponseDTO = response.as(CurrencyResponseDTO.class, ObjectMapperType.GSON);
		assertEquals(groupResponseDTO.getStatus(), "OK", "getStatus issue");
		assertEquals(groupResponseDTO.getMessage(), "OK", "getMessage issue");
		assertNotNull(groupResponseDTO.getContent(), "getContent issue");
	}
	
}
