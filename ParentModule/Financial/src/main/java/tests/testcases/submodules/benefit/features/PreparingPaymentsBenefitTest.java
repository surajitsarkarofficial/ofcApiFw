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
import payloads.submodules.expense.features.expensesPayments.PreparingPayments;
import tests.testcases.submodules.benefit.BenefitBaseTest;
import tests.testhelpers.submodules.benefit.features.ApprovedAsFinanceBenefitTestHelper;
import tests.testhelpers.submodules.expense.features.ExpensesPaymentsTestHelper;

/**
 * @author german.massello
 *
 */
public class PreparingPaymentsBenefitTest extends BenefitBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("ApproveAsFinanceBenefitTest");
	}

	 /**
	 * Check that is feasible to change to preparing payments a benefit. 
	 * @param userName
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(dataProvider = "usersThatHaveABankAccount", dataProviderClass = FinancialDataProviders.class
			, invocationCount = 1)
	public void preparingPaymentsBenefitTest(String userName) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		ReportWithExpenseDTO benefit = new ApprovedAsFinanceBenefitTestHelper(userName).approveAsFinanceBenefitFromScratch();
		ExpensesPaymentsPayload payload = new ExpensesPaymentsPayload(new PreparingPayments(benefit.getContent().getId()));
		String financeUser=new ExpenseDBHelper().getRandomGloberByRol("APAndExpensesAnalyst");
		Response response = new ExpensesPaymentsTestHelper(financeUser).expensesPayments(payload);
		validateResponseToContinueTest(response, 200, "Approve As Finance api call was not successful.", true);
		ApproveAsFinanceResponseDTO preparingPaymentsResponse = response.as(ApproveAsFinanceResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(preparingPaymentsResponse.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(preparingPaymentsResponse.getMessage(), "Updated", "getMessage issue");
		softAssert.assertTrue(preparingPaymentsResponse.getContent().getErrorList().isEmpty(), "getErrorList issue");
		ReportStatus reportStatus = new GetExpenseReportDetailsDBHelper().getReportStatus(benefit);
		softAssert.assertEquals(reportStatus.getStatus(), "preparing_payment", "getReportStatus issue");
		softAssert.assertNull(reportStatus.getComments(), "getComments issue");
		softAssert.assertEquals(reportStatus.getAuthor(), financeUser, "getAuthor issue");		
		test.log(Status.PASS, "the report was change to preparing payments successfully");
		softAssert.assertAll();
	   }

}
