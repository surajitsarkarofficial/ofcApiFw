package tests.testcases.submodules.quotation.features;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.quotation.features.GetQuotationDataProviders;
import dto.submodules.quotation.post.QuotationResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import parameters.submodules.quotation.GetQuotationParameters;
import tests.testcases.submodules.quotation.QuotationBaseTest;
import tests.testhelpers.submodules.quotation.features.GetQuotationDetailsTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class GetQuotationDetailsTest extends QuotationBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("GetQuotationDetailsTest");
	}

	/**
	 * Goal: Check that is feasible to perform a GET quotation details. 
	 * @param parameters
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(dataProvider = "userWithResolvedQuotations", dataProviderClass = GetQuotationDataProviders.class, groups = {ExeGroups.Sanity})
	public void getQuotationDetailsTest(GetQuotationParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		Response response = new GetQuotationDetailsTestHelper(parameters.getUserName()).getQuotationsDetails(parameters);
		validateResponseToContinueTest(response, 200, "Get quotation details api call was not successful.", true);
		QuotationResponseDTO quotation = response.as(QuotationResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(quotation.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(quotation.getMessage(), "Success", "getMessage issue");
		softAssert.assertEquals(quotation.getContent().getNumber(), parameters.getQuotationId(), "getQuotationId issue");
		softAssert.assertAll();
	}
	
}
