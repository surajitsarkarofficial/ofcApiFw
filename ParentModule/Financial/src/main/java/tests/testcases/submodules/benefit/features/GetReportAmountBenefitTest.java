package tests.testcases.submodules.benefit.features;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.benefit.features.GetReportAmountsBenefitDataProviders;
import dto.submodules.expense.report.getAmounts.GetReportsAmountResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import parameters.submodules.expense.features.getReports.GetExpenseReportParameters;
import tests.testcases.submodules.benefit.BenefitBaseTest;
import tests.testhelpers.submodules.expense.features.report.GetReportAmountTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class GetReportAmountBenefitTest extends BenefitBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("GetReportAmountBenefitTest");
	}

	/**
	 * Check that is feasible to GET approve as fianance report amount benefit.
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "getReportAmountApproveAsFiananceBenefit", dataProviderClass = GetReportAmountsBenefitDataProviders.class
			, groups = {ExeGroups.NotAvailableInPreProd})
	public void getReportAmountApproveAsFiananceBenefitTest(GetExpenseReportParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		Response  response = new GetReportAmountTestHelper(parameters.getUsername()).getExpenseReportAmount(parameters);
		GetReportsAmountResponseDTO fetchedReport = response.as(GetReportsAmountResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals("OK", fetchedReport.getStatus(), "getStatus issue");
		softAssert.assertEquals("Success", fetchedReport.getMessage(), "getMessage issue");
		softAssert.assertEquals(fetchedReport.getContent().getTicketAmountsList().get(0).getId(), parameters.getReportId(), "report id number "+parameters.getReportId()+" is not present");
		softAssert.assertAll();
	}

	/**
	 * Check that is feasible to GET preparing payment report amount benefit.
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "getReportAmountPreparingPaymentBenefit", dataProviderClass = GetReportAmountsBenefitDataProviders.class
			, groups = {ExeGroups.NotAvailableInPreProd})
	public void getReportAmountPreparingPaymentBenefitTest(GetExpenseReportParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		Response  response = new GetReportAmountTestHelper(parameters.getUsername()).getExpenseReportAmount(parameters);
		GetReportsAmountResponseDTO fetchedReport = response.as(GetReportsAmountResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals("OK", fetchedReport.getStatus(), "getStatus issue");
		softAssert.assertEquals("Success", fetchedReport.getMessage(), "getMessage issue");
		softAssert.assertEquals(fetchedReport.getContent().getTicketAmountsList().get(0).getId(), parameters.getReportId(), "report id number "+parameters.getReportId()+" is not present");
		softAssert.assertAll();
	}
	
	/**
	 * Check that is feasible to GET paid report amount benefit.
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "getReportAmountPaidBenefit", dataProviderClass = GetReportAmountsBenefitDataProviders.class
			, groups = {ExeGroups.NotAvailableInPreProd})
	public void getReportAmountPaidBenefitTest(GetExpenseReportParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		Response  response = new GetReportAmountTestHelper(parameters.getUsername()).getExpenseReportAmount(parameters);
		GetReportsAmountResponseDTO fetchedReport = response.as(GetReportsAmountResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals("OK", fetchedReport.getStatus(), "getStatus issue");
		softAssert.assertEquals("Success", fetchedReport.getMessage(), "getMessage issue");
		softAssert.assertEquals(fetchedReport.getContent().getTicketAmountsList().get(0).getId(), parameters.getReportId(), "report id number "+parameters.getReportId()+" is not present");
		softAssert.assertAll();
	}
}

