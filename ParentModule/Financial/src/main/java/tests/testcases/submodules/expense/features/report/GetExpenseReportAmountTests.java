package tests.testcases.submodules.expense.features.report;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.expense.features.GetExpenseReportAmountDataProviders;
import dto.submodules.expense.report.getAmounts.GetReportsAmountResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import parameters.submodules.expense.features.getReports.GetExpenseReportParameters;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.features.report.GetReportAmountTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class GetExpenseReportAmountTests extends ExpenseBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("GetExpenseReportAmountTests");
	}

	/**
	 * Check that is feasible to GET approve as finance report amount.
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "getReportAmountApproveAsFinance", dataProviderClass = GetExpenseReportAmountDataProviders.class, groups = {ExeGroups.Sanity})
	public void getReportAmountApproveAsFiananceTest(GetExpenseReportParameters parameters) throws Exception {
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
	@Test(dataProvider = "getReportAmountPreparingPayment", dataProviderClass = GetExpenseReportAmountDataProviders.class)
	public void getReportAmountPreparingPaymentTest(GetExpenseReportParameters parameters) throws Exception {
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
	@Test(dataProvider = "getReportAmountPaid", dataProviderClass = GetExpenseReportAmountDataProviders.class)
	public void getReportAmountPaidTest(GetExpenseReportParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		Response  response = new GetReportAmountTestHelper(parameters.getUsername()).getExpenseReportAmount(parameters);
		GetReportsAmountResponseDTO fetchedReport = response.as(GetReportsAmountResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals("OK", fetchedReport.getStatus(), "getStatus issue");
		softAssert.assertEquals("Success", fetchedReport.getMessage(), "getMessage issue");
		softAssert.assertEquals(fetchedReport.getContent().getTicketAmountsList().get(0).getId(), parameters.getReportId(), "report id number "+parameters.getReportId()+" is not present");
		softAssert.assertAll();
	}
}
