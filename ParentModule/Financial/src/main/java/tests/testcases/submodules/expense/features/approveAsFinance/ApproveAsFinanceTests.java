package tests.testcases.submodules.expense.features.approveAsFinance;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import database.submodules.expense.ExpenseDBHelper;
import dataproviders.FinancialDataProviders;
import dto.submodules.expense.addExpenseToReport.ReportWithExpenseDTO;
import dto.submodules.expense.approveAsFinance.ApproveAsFinanceResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import payloads.submodules.expense.features.approveAsFinance.ApproveAsFinancePayload;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.features.approveAsFinance.ApproveAsFinanceTestHelper;
import tests.testhelpers.submodules.expense.features.approveAsManager.ApproveAsManagerTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;


/**
 * @author german.massello
 *
 */
public class ApproveAsFinanceTests extends ExpenseBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("ApproveAsFinanceTests");
	}
	
	/**
	 * Goal: Check that a finance user can approve a report.
	 * @param userName
	 * @throws Exception
	 */
	@Test(dataProvider = "usersThatHaveABankAccount", dataProviderClass = FinancialDataProviders.class, groups = {ExeGroups.Sanity})
	public void approveAsFinanceTest(String userName) throws Exception {
		ApproveAsManagerTestHelper approveAsManagerTestHelper = new ApproveAsManagerTestHelper(userName);
		ExpenseDBHelper expenseDBHelper = new ExpenseDBHelper();
		//Given
		ReportWithExpenseDTO approveByManagerReport = approveAsManagerTestHelper.approveAsManagerFromScratch();
		ApproveAsFinancePayload approveAsFinancePayload = new ApproveAsFinancePayload("approve", approveByManagerReport.getContent().getId(), 
				approveByManagerReport.getContent().getExpenses().get(0).getId() );
		//When
		ApproveAsFinanceTestHelper approveAsFinanceTestHelper = new ApproveAsFinanceTestHelper(expenseDBHelper.getRandomGloberByRol("APAndExpensesAnalyst"));
		Response response = approveAsFinanceTestHelper.approveAsFinance(approveAsFinancePayload);
		ApproveAsFinanceResponseDTO approveAsFinanceResponse = response.as(ApproveAsFinanceResponseDTO.class, ObjectMapperType.GSON);
		//Then
		new ExpenseBaseTest().validateResponseToContinueTest(response, 200, "Approve As Finance api call was not successful.", true);
		assertEquals(approveAsFinanceResponse.getStatus(), "OK", "getStatus issue");
		assertEquals(approveAsFinanceResponse.getMessage(), "Success", "getMessage issue");
		assertTrue(approveAsFinanceResponse.getContent().getErrorList().isEmpty(), "getErrorList issue");
		test.log(Status.PASS, "the report was approved by finance successfully");
	}	
	
	/**
	 * Goal: Check that a finance user can reject a report.
	 * @param userName
	 * @throws Exception
	 */
	@Test(dataProvider = "usersThatHaveABankAccount", dataProviderClass = FinancialDataProviders.class, groups = {ExeGroups.Sanity})
	public void rejectAsFinanceTest(String userName) throws Exception {
		ApproveAsManagerTestHelper approveAsManagerTestHelper = new ApproveAsManagerTestHelper(userName);
		ExpenseDBHelper expenseDBHelper = new ExpenseDBHelper();
		//Given
		ReportWithExpenseDTO approveByManagerReport = approveAsManagerTestHelper.approveAsManagerFromScratch();
		ApproveAsFinancePayload approveAsFinancePayload = new ApproveAsFinancePayload("reject", approveByManagerReport.getContent().getId(), 
				approveByManagerReport.getContent().getExpenses().get(0).getId() );
		//When
		ApproveAsFinanceTestHelper approveAsFinanceTestHelper = new ApproveAsFinanceTestHelper(expenseDBHelper.getRandomGloberByRol("APAndExpensesAnalyst"));
		Response response = approveAsFinanceTestHelper.approveAsFinance(approveAsFinancePayload);
		ApproveAsFinanceResponseDTO approveAsFinanceResponse = response.as(ApproveAsFinanceResponseDTO.class, ObjectMapperType.GSON);
		//Then
		new ExpenseBaseTest().validateResponseToContinueTest(response, 200, "Reject As Finance api call was not successful.", true);
		assertEquals(approveAsFinanceResponse.getStatus(), "OK", "getStatus issue");
		assertEquals(approveAsFinanceResponse.getMessage(), "Success", "getMessage issue");
		assertTrue(approveAsFinanceResponse.getContent().getErrorList().isEmpty(), "getErrorList issue");
		test.log(Status.PASS, "the report was rejected by finance successfully");
	}	
	
}
