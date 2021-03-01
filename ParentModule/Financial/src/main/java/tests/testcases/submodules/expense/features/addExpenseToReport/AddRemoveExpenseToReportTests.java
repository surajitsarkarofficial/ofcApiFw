package tests.testcases.submodules.expense.features.addExpenseToReport;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import dataproviders.FinancialDataProviders;
import dto.submodules.expense.addExpenseToReport.ReportWithExpenseDTO;
import dto.submodules.expense.expense.post.ExpenseDTO;
import dto.submodules.expense.report.post.ReportDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import payloads.submodules.expense.features.addExpenseToReport.AddRemoveExpenseToReportPayload;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.features.addExpenseToReport.AddRemoveExpenseToReportTestHelper;
import tests.testhelpers.submodules.expense.features.expense.CreateNewExpenseTestHelper;
import tests.testhelpers.submodules.expense.features.report.CreateReportTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class AddRemoveExpenseToReportTests extends ExpenseBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("AddExpenseToReportTests");
	}
	
	/**
	 * Goal: Check that is possible add one expense to a report.
	 * @param userName
	 * @throws Exception
	 */
	@Test(dataProvider = "usersThatHaveABankAccount", dataProviderClass = FinancialDataProviders.class, groups = {ExeGroups.Sanity})
	public void addExpenseToReportTest(String userName) throws Exception {
		AddRemoveExpenseToReportTestHelper addExpenseToReportTestHelper = new AddRemoveExpenseToReportTestHelper(userName);
		//Given
		ExpenseDTO createdExpense = new CreateNewExpenseTestHelper(userName).createBasicExpense();
		ReportDTO createdReport = new CreateReportTestHelper(userName).createBasicReport();
		//When
		AddRemoveExpenseToReportPayload addExpenseToReportPayload = addExpenseToReportTestHelper.createBasicAddExpenseToReportPayload(createdExpense, createdReport);
		Response response = addExpenseToReportTestHelper.addRemoveExpenseToReport(addExpenseToReportPayload);
		validateResponseToContinueTest(response, 200, "Add Expense To Report api call was not successful.", true);
		ReportWithExpenseDTO reportWithExpenseDTO = response.as(ReportWithExpenseDTO.class, ObjectMapperType.GSON);
		//Then
		Assert.assertEquals(reportWithExpenseDTO.getStatus(), "OK", " getStatus() issue");
		Assert.assertEquals(reportWithExpenseDTO.getMessage(), "Updated", " getMessage() issue");
		Assert.assertEquals(reportWithExpenseDTO.getContent().getExpenses().get(0).getId(), createdExpense.getContent().getId(), " getMessage() issue");
	}
	
	/**
	 * Goal: Check that is possible remove one expense from a report.
	 * @param userName
	 * @throws Exception
	 */
	@Test(dataProvider = "usersThatHaveABankAccount", dataProviderClass = FinancialDataProviders.class, groups = {ExeGroups.Sanity})
	public void removeExpenseToReportTest(String userName) throws Exception {
		AddRemoveExpenseToReportTestHelper addExpenseToReportTestHelper = new AddRemoveExpenseToReportTestHelper(userName);
		//Given
		ReportWithExpenseDTO reportWithExpenseDTO = addExpenseToReportTestHelper.createReportWithExpense();
		//When
		AddRemoveExpenseToReportPayload removeExpenseToReportPayload = reportWithExpenseDTO.getAddExpenseToReportPayload();
		removeExpenseToReportPayload.setUpdateAction("1");
		Response response = addExpenseToReportTestHelper.addRemoveExpenseToReport(removeExpenseToReportPayload);
		validateResponseToContinueTest(response, 200, "Add Expense To Report api call was not successful.", true);
		ReportWithExpenseDTO reportWithOutExpenseDTO = response.as(ReportWithExpenseDTO.class, ObjectMapperType.GSON);
		//Then
		Assert.assertEquals(reportWithOutExpenseDTO.getStatus(), "OK", " getStatus() issue");
		Assert.assertEquals(reportWithOutExpenseDTO.getMessage(), "Updated", " getMessage() issue");
		Assert.assertTrue(reportWithOutExpenseDTO.getContent().getExpenses().isEmpty(), " expense was not removed");
	}	
	
}
