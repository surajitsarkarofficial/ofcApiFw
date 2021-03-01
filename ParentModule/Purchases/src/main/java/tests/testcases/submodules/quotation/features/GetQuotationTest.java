package tests.testcases.submodules.quotation.features;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.PurchasesDataProviders;
import dataproviders.submodules.quotation.features.GetQuotationDataProviders;
import dto.submodules.quotation.get.GetQuotationResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import parameters.submodules.quotation.GetQuotationParameters;
import tests.testcases.submodules.quotation.QuotationBaseTest;
import tests.testhelpers.submodules.quotation.features.GetQuotationTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class GetQuotationTest extends QuotationBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("GetQuotationsTest");
	}

	/**
	 * Goal: Check that is feasible to perform a GET resolved quotations. 
	 * @param parameters
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(dataProvider = "userWithResolvedQuotations", dataProviderClass = GetQuotationDataProviders.class, groups = {ExeGroups.Sanity})
	public void getResolvedQuotationsTest(GetQuotationParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		Response response = new GetQuotationTestHelper(parameters.getUserName()).getQuotations(parameters);
		validateResponseToContinueTest(response, 200, "Get quotations api call was not successful.", true);
		GetQuotationResponseDTO quotation = response.as(GetQuotationResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(quotation.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(quotation.getMessage(), "Success", "getMessage issue");
		softAssert.assertEquals(quotation.getContent().getQuotationList().get(0).getStatus().toLowerCase(), parameters.getStatus().toLowerCase(), "getStatus quotation issue");
		softAssert.assertAll();
	}
	
	/**
	 * Goal: Check that is feasible to perform a GET draft quotations. 
	 * @param parameters
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(dataProvider = "userWithDraftQuotations", dataProviderClass = GetQuotationDataProviders.class, groups = {ExeGroups.Sanity})
	public void getDraftQuotationsTest(GetQuotationParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		Response response = new GetQuotationTestHelper(parameters.getUserName()).getQuotations(parameters);
		validateResponseToContinueTest(response, 200, "Get quotations api call was not successful.", true);
		GetQuotationResponseDTO quotation = response.as(GetQuotationResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(quotation.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(quotation.getMessage(), "Success", "getMessage issue");
		softAssert.assertEquals(quotation.getContent().getQuotationList().get(0).getState().toLowerCase(), parameters.getState().toLowerCase(), "getState quotation issue");
		softAssert.assertAll();
	}
	
	/**
	 * Goal: Check that is feasible to perform a GET unassigned quotations. 
	 * @param parameters
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(dataProvider = "userWithUnassignedQuotations", dataProviderClass = GetQuotationDataProviders.class, groups = {ExeGroups.Sanity})
	public void getUnassignedQuotationsTest(GetQuotationParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		Response response = new GetQuotationTestHelper(parameters.getUserName()).getQuotations(parameters);
		validateResponseToContinueTest(response, 200, "Get quotations api call was not successful.", true);
		GetQuotationResponseDTO quotation = response.as(GetQuotationResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(quotation.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(quotation.getMessage(), "Success", "getMessage issue");
		softAssert.assertEquals(quotation.getContent().getQuotationList().get(0).getStatus().toLowerCase(), parameters.getStatus().toLowerCase(), "getStatus quotation issue");
		softAssert.assertAll();
	}
	
	/**
	 * Goal: Check that is feasible to perform a GET quotations, using the global view. 
	 * @param parameters
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(dataProvider = "facilitiesUser", dataProviderClass = PurchasesDataProviders.class, groups = {ExeGroups.Sanity})
	public void getQuotationsGlobalViewTest(GetQuotationParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		Response response = new GetQuotationTestHelper(parameters.getUserName()).getQuotations(parameters);
		validateResponseToContinueTest(response, 200, "Get quotations api call was not successful.", true);
		GetQuotationResponseDTO quotation = response.as(GetQuotationResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(quotation.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(quotation.getMessage(), "Success", "getMessage issue");
		softAssert.assertNotNull(quotation.getContent(), "getContent issue");
		softAssert.assertAll();
	}
}
