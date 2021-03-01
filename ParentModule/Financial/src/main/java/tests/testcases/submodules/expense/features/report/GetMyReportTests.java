package tests.testcases.submodules.expense.features.report;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import database.GlowDBHelper;
import dataproviders.FinancialDataProviders;
import dto.submodules.expense.addExpenseToReport.ReportWithExpenseDTO;
import dto.submodules.expense.report.post.ReportDTO;
import parameters.submodules.expense.features.getReports.GetExpenseReportParameters;
import parameters.submodules.expense.features.getReports.GetMyReportsStatusFilter;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.features.addExpenseToReport.AddRemoveExpenseToReportTestHelper;
import tests.testhelpers.submodules.expense.features.report.GetMyReportTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class GetMyReportTests extends ExpenseBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("GetMyReportTests");
	}
	
	/**
	 * Goal: Check that is feasible to fetch a draft report.
	 * @param userName
	 * @throws Exception
	 */
	@Test(dataProvider = "usersThatHaveABankAccount", dataProviderClass = FinancialDataProviders.class, groups = {ExeGroups.Sanity})
	public void getDraftReportTest(String userName) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		AddRemoveExpenseToReportTestHelper addRemoveExpenseToReportTestHelper = new AddRemoveExpenseToReportTestHelper(userName);
		GetMyReportTestHelper getMyReportTestHelper = new GetMyReportTestHelper(userName);
		//Given
		ReportWithExpenseDTO reportWithExpenseDTO = addRemoveExpenseToReportTestHelper.createReportWithExpense();
		ReportDTO expectedReport = addRemoveExpenseToReportTestHelper.extractReportDTOFromReportWithExpenseDTO(reportWithExpenseDTO);
		//When
		ReportDTO fetchedReport = getMyReportTestHelper.getReportFromTheReportList(reportWithExpenseDTO, new GetExpenseReportParameters(new GetMyReportsStatusFilter()));
		//Then
		softAssert.assertEquals("OK", fetchedReport.getStatus(), "getStatus issue");
		softAssert.assertEquals("Success", fetchedReport.getMessage(), "getMessage issue");
		softAssert.assertEquals(fetchedReport.getContent().getOriginId(), expectedReport.getContent().getOriginId(), "getOriginId issue");
		softAssert.assertEquals(fetchedReport.getContent().getTitle(), expectedReport.getContent().getTitle(), "getTitle issue");
		softAssert.assertEquals(fetchedReport.getContent().getStatus(), expectedReport.getContent().getStatus(), "getStatus issue");
		softAssert.assertEquals(fetchedReport.getContent().getClientName(), new GlowDBHelper().getClientNameFromProjectId(expectedReport.getContent().getOriginId()), "getClientName issue");
		softAssert.assertAll();
	}
	
	
}
