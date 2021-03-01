package tests.testcases.submodules.quotation.features;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.quotation.features.CreateQuotationDataProviders;
import dto.submodules.quotation.post.QuotationResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import parameters.submodules.quotation.QuotationParameters;
import payloads.submodules.quotation.features.AssignQuotationPayLoadHelper;
import tests.testcases.submodules.quotation.QuotationBaseTest;
import tests.testhelpers.submodules.quotation.features.CreateQuotationTestHelper;
import tests.testhelpers.submodules.quotation.features.PutQuotationTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class AssignQuotationTest extends QuotationBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("AssignQuotationTest");
	}

	/**
	 * Goal: Check that is feasible to assigned a quotation. 
	 * @param parameters
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(dataProvider = "quotationDataProvider", dataProviderClass = CreateQuotationDataProviders.class, groups = {ExeGroups.Sanity})
	public void assignedQuotationTest(QuotationParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		QuotationResponseDTO quotation = new CreateQuotationTestHelper(parameters.getUserName()).createQuotationFromScratch(parameters);
		AssignQuotationPayLoadHelper payload = new AssignQuotationPayLoadHelper().assignQuotationPayLoad(quotation);
		Response response = new PutQuotationTestHelper(parameters.getPurchaseUserName()).putQuotation(payload);
		QuotationResponseDTO assignedQuotation = response.as(QuotationResponseDTO.class, ObjectMapperType.GSON);
		validateResponseToContinueTest(response, 200, "PUT quotation api call was not successful.", true);
		softAssert.assertEquals(assignedQuotation.getStatus(), "OK", "getStatus response issue");
		softAssert.assertEquals(assignedQuotation.getMessage(), "Updated", "getMessage issue");
		softAssert.assertEquals(assignedQuotation.getContent().getState(), payload.getState(), "getState issue");
		softAssert.assertEquals(assignedQuotation.getContent().getStatus().toLowerCase(), payload.getStatus().replace("_", " ").toLowerCase(), "getStatus quotation issue");
		softAssert.assertAll();
	}
	
}
