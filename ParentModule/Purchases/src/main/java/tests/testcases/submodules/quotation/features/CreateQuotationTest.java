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
import tests.testcases.submodules.quotation.QuotationBaseTest;
import tests.testhelpers.submodules.quotation.features.CreateQuotationTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class CreateQuotationTest extends QuotationBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("CreateQuotationTest");
	}

	/**
	 * Goal: Check that is feasible to create a quotation. 
	 * @param parameters
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(dataProvider = "quotationDataProvider", dataProviderClass = CreateQuotationDataProviders.class, groups = {ExeGroups.Sanity})
	public void createQuotationTest(QuotationParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		QuotationPayLoadHelper payLoad = new QuotationPayLoadHelper(parameters.getUserName(), parameters.getCountryName(), parameters.getGroupName());
		Response response = new CreateQuotationTestHelper(parameters.getUserName()).createQuotation(payLoad);
		validateResponseToContinueTest(response, 201, "POST quotation api call was not successful.", true);
		QuotationResponseDTO postQuotationResponseDTO = response.as(QuotationResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(postQuotationResponseDTO.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(postQuotationResponseDTO.getMessage(), "Created", "getMessage issue");
		softAssert.assertEquals(postQuotationResponseDTO.getContent().getTitle(), payLoad.getTitle(), "getTitle issue");
		softAssert.assertEquals(postQuotationResponseDTO.getContent().getBaseProjectId(), payLoad.getBaseProjectId(), "getBaseProjectId issue");
		softAssert.assertEquals(postQuotationResponseDTO.getContent().getCountryId(), payLoad.getCountryId(), "getCountryId issue");
		softAssert.assertEquals(postQuotationResponseDTO.getContent().getStatus().toLowerCase(), payLoad.getStatus().toLowerCase(), "getStatus quotation issue");
		softAssert.assertEquals(postQuotationResponseDTO.getContent().getState().toLowerCase(), payLoad.getState().toLowerCase(), "getState quotation issue");
		softAssert.assertEquals(postQuotationResponseDTO.getContent().getRequesterId(), payLoad.getRequesterId(), "getRequesterId quotation issue");
		softAssert.assertEquals(postQuotationResponseDTO.getContent().getType(), payLoad.getType(), "getType quotation issue");
		softAssert.assertEquals(postQuotationResponseDTO.getContent().getSymbolized(), payLoad.getSymbolized(), "getSymbolized quotation issue");
		softAssert.assertEquals(postQuotationResponseDTO.getContent().getItems().get(0).getProjectName(), payLoad.getItems().get(0).getProjectName(), "getProjectName item quotation issue");
		softAssert.assertEquals(postQuotationResponseDTO.getContent().getItems().get(0).getTechnicalSpecifications(), payLoad.getItems().get(0).getTechnicalSpecifications(), "getTechnicalSpecifications item quotation issue");
		softAssert.assertEquals(postQuotationResponseDTO.getContent().getItems().get(0).getSupplyId(), payLoad.getItems().get(0).getSupplyId(), "getSupplyId item quotation issue");
		softAssert.assertAll();
	}

	/**
	 * Goal: Check that is feasible to create a draft quotation. 
	 * @param parameters
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(dataProvider = "quotationDataProvider", dataProviderClass = CreateQuotationDataProviders.class)
	public void createDraftQuotationTest(QuotationParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		QuotationPayLoadHelper payLoad = new QuotationPayLoadHelper(parameters.getUserName(), parameters.getCountryName(), parameters.getGroupName());
		payLoad.setState("QUOTATION_DRAFT").setStatus("DRAFT");
		Response response = new CreateQuotationTestHelper(parameters.getUserName()).createQuotation(payLoad);
		validateResponseToContinueTest(response, 201, "POST quotation api call was not successful.", true);
		QuotationResponseDTO postQuotationResponseDTO = response.as(QuotationResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(postQuotationResponseDTO.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(postQuotationResponseDTO.getMessage(), "Created", "getMessage issue");
		softAssert.assertEquals(postQuotationResponseDTO.getContent().getStatus().toLowerCase(), payLoad.getStatus().toLowerCase(), "getStatus quotation issue");
		softAssert.assertEquals(postQuotationResponseDTO.getContent().getState().toLowerCase(), payLoad.getState().toLowerCase(), "getState quotation issue");
		softAssert.assertAll();
	}
}
