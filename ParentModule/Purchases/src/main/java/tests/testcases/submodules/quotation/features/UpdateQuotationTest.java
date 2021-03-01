package tests.testcases.submodules.quotation.features;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.quotation.features.CreateQuotationDataProviders;
import dto.submodules.quotation.post.QuotationResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import parameters.submodules.quotation.QuotationParameters;
import payloads.submodules.quotation.QuotationPayLoadHelper;
import payloads.submodules.quotation.features.UpdateQuotationPayLoadHelper;
import tests.testcases.submodules.quotation.QuotationBaseTest;
import tests.testhelpers.submodules.quotation.features.CreateQuotationTestHelper;
import tests.testhelpers.submodules.quotation.features.PutQuotationTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class UpdateQuotationTest extends QuotationBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("UpdateQuotationTest");
	}

	/**
	 * Goal: Check that is feasible to update a quotation. 
	 * @param parameters
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(dataProvider = "quotationDataProvider", dataProviderClass = CreateQuotationDataProviders.class, groups = {ExeGroups.Sanity})
	public void updateQuotationTest(QuotationParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		QuotationResponseDTO quotation = new CreateQuotationTestHelper(parameters.getUserName()).createQuotationFromScratch(parameters);
		QuotationPayLoadHelper payLoad =  new UpdateQuotationPayLoadHelper().updateQuotationPayLoad(quotation);
		Response response = new PutQuotationTestHelper(parameters.getUserName()).putQuotation(payLoad);
		QuotationResponseDTO updatedQuotation = response.as(QuotationResponseDTO.class, ObjectMapperType.GSON);
		validateResponseToContinueTest(response, 200, "PUT quotation api call was not successful.", true);
		softAssert.assertEquals(updatedQuotation.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(updatedQuotation.getMessage(), "Updated", "getMessage issue");
		softAssert.assertEquals(updatedQuotation.getContent().getTitle(), payLoad.getTitle(), "getTitle issue");
		softAssert.assertEquals(updatedQuotation.getContent().getItems().get(0).getQuantity(), payLoad.getItems().get(0).getQuantity(), "getQuantity issue");
		softAssert.assertAll();
	}

}
