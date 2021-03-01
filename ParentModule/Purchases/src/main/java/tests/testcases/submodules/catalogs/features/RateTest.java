package tests.testcases.submodules.catalogs.features;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import dataproviders.PurchasesDataProviders;
import dto.submodules.catalogs.rate.RateResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import tests.testcases.submodules.catalogs.CatalogsBaseTest;
import tests.testhelpers.submodules.catalogs.features.RateTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class RateTest extends CatalogsBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("RateTest");
	}

	/**
	 * Goal: Check that is feasible to perform a GET rate. 
	 * @param userName
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(groups = {ExeGroups.Sanity}, dataProvider = "serviceDeskUser", dataProviderClass = PurchasesDataProviders.class)
	public void getRateTest(String userName) throws Exception {
		Response response = new RateTestHelper(userName).getRate();
		validateResponseToContinueTest(response, 200, "Get rate api call was not successful.", true);
		RateResponseDTO rateResponseDTO = response.as(RateResponseDTO.class, ObjectMapperType.GSON);
		assertEquals(rateResponseDTO.getStatus(), "OK", "getStatus issue");
		assertEquals(rateResponseDTO.getMessage(), "OK", "getMessage issue");
		assertEquals(rateResponseDTO.getContent().getFactor(), "1", "getFactor issue");
	}
	
}
