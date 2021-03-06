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
import payloads.submodules.expense.features.approveAsFinance.ApproveAsFinancePayload;
import tests.testcases.submodules.benefit.BenefitBaseTest;
import tests.testhelpers.submodules.benefit.features.SendBenefitToApproveTestHelper;
import tests.testhelpers.submodules.expense.features.approveAsFinance.ApproveAsFinanceTestHelper;

/**
 * @author german.massello
 *
 */
public class ApproveAsFinanceBenefitTest extends BenefitBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("ApproveAsFinanceBenefitTest");
	}

	 /**
	 * Check that is feasible to approve as finance a benefit. 
	 * @param userName
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(dataProvider = "usersThatHaveABankAccount", dataProviderClass = FinancialDataProviders.class
			, invocationCount = 1)
	public void approveAsFinanceBenefitTest(String userName) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		ReportWithExpenseDTO benefit = new SendBenefitToApproveTestHelper(userName).createAndSendBenefitToApproveFromScratch();
		ApproveAsFinancePayload approveAsFinancePayload = new ApproveAsFinancePayload("approve", benefit.getContent().getId(), 
				benefit.getContent().getExpenses().get(0).getId(), benefit.getContent().getExpenses().get(1).getId() );
		String financeUser=new ExpenseDBHelper().getRandomGloberByRol("APAndExpensesAnalyst");
		ApproveAsFinanceTestHelper approveAsFinanceTestHelper = new ApproveAsFinanceTestHelper(financeUser);
		Response response = approveAsFinanceTestHelper.approveAsFinance(approveAsFinancePayload);
		ApproveAsFinanceResponseDTO approveAsFinanceResponse = response.as(ApproveAsFinanceResponseDTO.class, ObjectMapperType.GSON);
		validateResponseToContinueTest(response, 200, "Approve As Finance api call was not successful.", true);
		softAssert.assertEquals(approveAsFinanceResponse.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(approveAsFinanceResponse.getMessage(), "Success", "getMessage issue");
		softAssert.assertTrue(approveAsFinanceResponse.getContent().getErrorList().isEmpty(), "getErrorList issue");
		ReportStatus reportStatus = new GetExpenseReportDetailsDBHelper().getReportStatus(benefit);
		softAssert.assertEquals(reportStatus.getStatus(), "approved_by_finance", "getReportStatus issue");
		softAssert.assertEquals(reportStatus.getComments(), "approve by Finance", "getComments issue");
		softAssert.assertEquals(reportStatus.getAuthor(), financeUser, "getAuthor issue");		
		test.log(Status.PASS, "the report was approved by finance successfully");
		softAssert.assertAll();
	   }

}
