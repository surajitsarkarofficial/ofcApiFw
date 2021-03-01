package tests.testcases.submodules.benefit.features;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import database.submodules.expense.ExpenseDBHelper;
import database.submodules.expense.features.GetExpenseReportDetailsDBHelper;
import dataproviders.FinancialDataProviders;
import dto.submodules.expense.addExpenseToReport.ReportWithExpenseDTO;
import dto.submodules.expense.approveAsFinance.ApproveAsFinanceResponseDTO;
import dto.submodules.expense.report.ReportStatus;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import payloads.submodules.expense.features.expensesPayments.ExpensesPaymentsPayload;
import payloads.submodules.expense.features.expensesPayments.Paid;
import tests.testcases.submodules.benefit.BenefitBaseTest;
import tests.testhelpers.submodules.benefit.features.ApprovedAsFinanceBenefitTestHelper;
import tests.testhelpers.submodules.benefit.features.PreparingPaymentsBenefitTestHelper;
import tests.testhelpers.submodules.expense.features.ExpensesPaymentsTestHelper;

/**
 * @author german.massello
 *
 */
public class PaidBenefitTest extends BenefitBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("ApproveAsFinanceBenefitTest");
	}

	 /**
	 * Check that is feasible to change to paid a approve as finance benefit. 
	 * @param userName
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(dataProvider = "usersThatHaveABankAccount", dataProviderClass = FinancialDataProviders.class
			, invocationCount = 1)
	public void paidBenefitTest(String userName) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		ReportWithExpenseDTO benefit = new ApprovedAsFinanceBenefitTestHelper(userName).approveAsFinanceBenefitFromScratch();
		ExpensesPaymentsPayload payload = new ExpensesPaymentsPayload(new Paid(benefit.getContent().getId()));
		String financeUser=new ExpenseDBHelper().getRandomGloberByRol("APAndExpensesAnalyst");
		Response response = new ExpensesPaymentsTestHelper(financeUser).expensesPayments(payload);
		ApproveAsFinanceResponseDTO preparingPaymentsResponse = response.as(ApproveAsFinanceResponseDTO.class, ObjectMapperType.GSON);
		validateResponseToContinueTest(response, 200, "Paid api call was not successful.", true);
		softAssert.assertEquals(preparingPaymentsResponse.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(preparingPaymentsResponse.getMessage(), "Updated", "getMessage issue");
		softAssert.assertTrue(preparingPaymentsResponse.getContent().getErrorList().isEmpty(), "getErrorList issue");
		ReportStatus reportStatus = new GetExpenseReportDetailsDBHelper().getReportStatus(benefit);
		softAssert.assertEquals(reportStatus.getStatus(), "paid", "getReportStatus issue");
		softAssert.assertNull(reportStatus.getComments(), "getComments issue");
		softAssert.assertEquals(reportStatus.getAuthor(), financeUser, "getAuthor issue");		
		test.log(Status.PASS, "the report was change to paid successfully");
		softAssert.assertAll();
	   }

	 /**
	 * Check that is feasible to change to paid a preparing payments benefit. 
	 * @param userName
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(dataProvider = "usersThatHaveABankAccount", dataProviderClass = FinancialDataProviders.class
			, invocationCount = 1)
	public void paidAPreparingPaymentsBenefitTest(String userName) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		ReportWithExpenseDTO benefit = new PreparingPaymentsBenefitTestHelper(userName).preparingPaymentsBenefitFromScratch();
		ExpensesPaymentsPayload payload = new ExpensesPaymentsPayload(new Paid(benefit.getContent().getId()));
		String financeUser=new ExpenseDBHelper().getRandomGloberByRol("APAndExpensesAnalyst");
		Response response = new ExpensesPaymentsTestHelper(financeUser).expensesPayments(payload);
		ApproveAsFinanceResponseDTO preparingPaymentsResponse = response.as(ApproveAsFinanceResponseDTO.class, ObjectMapperType.GSON);
		validateResponseToContinueTest(response, 200, "Paid api call was not successful.", true);
		softAssert.assertEquals(preparingPaymentsResponse.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(preparingPaymentsResponse.getMessage(), "Updated", "getMessage issue");
		softAssert.assertTrue(preparingPaymentsResponse.getContent().getErrorList().isEmpty(), "getErrorList issue");
		ReportStatus reportStatus = new GetExpenseReportDetailsDBHelper().getReportStatus(benefit);
		softAssert.assertEquals(reportStatus.getStatus(), "paid", "getReportStatus issue");
		softAssert.assertNull(reportStatus.getComments(), "getComments issue");
		softAssert.assertEquals(reportStatus.getAuthor(), financeUser, "getAuthor issue");		
		test.log(Status.PASS, "the report was change to paid successfully");
		softAssert.assertAll();
	   }

}
