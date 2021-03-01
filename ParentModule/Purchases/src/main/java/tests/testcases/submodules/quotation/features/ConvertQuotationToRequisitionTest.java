package tests.testcases.submodules.quotation.features;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.quotation.features.CreateQuotationDataProviders;
import dto.submodules.quotation.post.QuotationResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import parameters.submodules.quotation.QuotationParameters;
import payloads.submodules.quotation.features.ConvertQuotationToRequisitionPayLoadHelper;
import tests.testcases.submodules.quotation.QuotationBaseTest;
import tests.testhelpers.submodules.quotation.features.ResolvedQuotationTestHelper;
import tests.testhelpers.submodules.requisition.features.PostQuotationTestHelper;
import utils.Utilities;
import utils.UtilsBase;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class ConvertQuotationToRequisitionTest extends QuotationBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("ConvertQuotationToRequisitionTest");
	}
	
	/**
	 * Goal: Check that is feasible to convert a quotation to a requisition 
	 * @param parameters
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(dataProvider = "quotationDataProvider", dataProviderClass = CreateQuotationDataProviders.class, groups = {ExeGroups.Sanity})
	public void convertQuotationToRequisitionTest(QuotationParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		QuotationResponseDTO assignedQuotation = new ResolvedQuotationTestHelper(parameters.getUserName()).resolveQuotation(parameters);
		ConvertQuotationToRequisitionPayLoadHelper payload = new ConvertQuotationToRequisitionPayLoadHelper().convertToRequisitionPayLoad(assignedQuotation);
		Response response = new PostQuotationTestHelper(parameters.getUserName()).createQuotation(payload);
		QuotationResponseDTO resolvedQuotation = response.as(QuotationResponseDTO.class, ObjectMapperType.GSON);
		UtilsBase.log("Response: " + Utilities.convertJavaObjectToJson(resolvedQuotation));
		validateResponseToContinueTest(response, 201, "POST requisition api call was not successful.", true);
		softAssert.assertEquals(resolvedQuotation.getStatus(), "OK", "getStatus response issue");
		softAssert.assertEquals(resolvedQuotation.getMessage(), "Created", "getMessage issue");
		softAssert.assertEquals(resolvedQuotation.getContent().getState(), payload.getState(), "getState issue");
		softAssert.assertEquals(resolvedQuotation.getContent().getStatus(), payload.getStatus(), "getStatus quotation issue");
		softAssert.assertAll();
	}
}
