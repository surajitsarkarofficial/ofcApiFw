package tests.testcases.submodules.quotation.features;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.quotation.features.CreateQuotationDataProviders;
import dto.submodules.quotation.post.QuotationResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import parameters.submodules.quotation.QuotationParameters;
import payloads.submodules.quotation.features.ResolveQuotationPayLoadHelper;
import tests.testcases.submodules.quotation.QuotationBaseTest;
import tests.testhelpers.submodules.quotation.features.AssignQuotationTestHelper;
import tests.testhelpers.submodules.quotation.features.PutQuotationTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class ResolveQuotationTest extends QuotationBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("ResolveQuotationTest");
	}
	
	/**
	 * Goal: Check that is feasible to resolved a quotation. 
	 * @param parameters
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(dataProvider = "quotationDataProvider", dataProviderClass = CreateQuotationDataProviders.class, groups = {ExeGroups.Sanity})
	public void resolveQuotationTest(QuotationParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		QuotationResponseDTO assignedQuotation = new AssignQuotationTestHelper(parameters.getUserName()).assignQuotationFromScratch(parameters);
		ResolveQuotationPayLoadHelper payload = new ResolveQuotationPayLoadHelper().resolveQuotationPayLoad(assignedQuotation);
		Response response = new PutQuotationTestHelper(parameters.getPurchaseUserName()).putQuotation(payload);
		QuotationResponseDTO resolvedQuotation = response.as(QuotationResponseDTO.class, ObjectMapperType.GSON);
		validateResponseToContinueTest(response, 200, "PUT quotation api call was not successful.", true);
		softAssert.assertEquals(resolvedQuotation.getStatus(), "OK", "getStatus response issue");
		softAssert.assertEquals(resolvedQuotation.getMessage(), "Updated", "getMessage issue");
		softAssert.assertEquals(resolvedQuotation.getContent().getState(), payload.getState(), "getState issue");
		softAssert.assertEquals(resolvedQuotation.getContent().getStatus().toLowerCase(), payload.getStatus().replace("_", " ").toLowerCase(), "getStatus quotation issue");
		softAssert.assertAll();
	}
	
}
