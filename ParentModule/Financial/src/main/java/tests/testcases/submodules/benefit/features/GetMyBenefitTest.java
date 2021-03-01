package tests.testcases.submodules.benefit.features;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.benefit.features.GetMyBenefitDataProviders;
import dto.submodules.expense.addExpenseToReport.ReportWithExpenseDTO;
import dto.submodules.expense.report.post.ReportDTO;
import parameters.submodules.expense.features.getReports.GetExpenseReportParameters;
import tests.testcases.submodules.benefit.BenefitBaseTest;
import tests.testhelpers.submodules.expense.ExpenseTestHelper;
import tests.testhelpers.submodules.expense.features.report.GetMyReportTestHelper;

/**
 * @author german.massello
 *
 */
public class GetMyBenefitTest extends BenefitBaseTest{

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("GetMyBenefitTests");
	}

	/**
	 * Check that is feasible to GET my draft benefit.
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "getMyDraftReport", dataProviderClass = GetMyBenefitDataProviders.class)
	public void getMyDraftBenefitTest(GetExpenseReportParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		ReportWithExpenseDTO reportWithExpenseDTO = new ReportWithExpenseDTO(parameters);
		ReportDTO fetchedReport = new GetMyReportTestHelper(parameters.getUsername()).getReportFromTheReportList(reportWithExpenseDTO, parameters);
		softAssert.assertEquals("OK", fetchedReport.getStatus(), "getStatus issue");
		softAssert.assertEquals("Success", fetchedReport.getMessage(), "getMessage issue");
		softAssert.assertTrue(new ExpenseTestHelper(parameters.getUsername()).isReportIdPresent(fetchedReport.getGetReportDTOList().getContent().getExpensesReportList(), parameters.getReportId()), "report id number "+parameters.getReportId()+" is not present");
		softAssert.assertAll();
	}

	/**
	 * Check that is feasible to GET my approved benefit.
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "getMyApprovedByManagerReport", dataProviderClass = GetMyBenefitDataProviders.class)
	public void getMyApprovedByManagerReportTest(GetExpenseReportParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		ReportWithExpenseDTO reportWithExpenseDTO = new ReportWithExpenseDTO(parameters);
		ReportDTO fetchedReport = new GetMyReportTestHelper(parameters.getUsername()).getReportFromTheReportList(reportWithExpenseDTO, parameters);
		softAssert.assertEquals("OK", fetchedReport.getStatus(), "getStatus issue");
		softAssert.assertEquals("Success", fetchedReport.getMessage(), "getMessage issue");
		softAssert.assertTrue(new ExpenseTestHelper(parameters.getUsername()).isReportIdPresent(fetchedReport.getGetReportDTOList().getContent().getExpensesReportList(), parameters.getReportId()), "report id number "+parameters.getReportId()+" is not present");
		softAssert.assertAll();
	}
	
	/**
	 * Check that is feasible to GET my approved by finance.
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "getMyApprovedByFinanceReport", dataProviderClass = GetMyBenefitDataProviders.class)
	public void getMyApprovedByFinanceReportTest(GetExpenseReportParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		ReportWithExpenseDTO reportWithExpenseDTO = new ReportWithExpenseDTO(parameters);
		ReportDTO fetchedReport = new GetMyReportTestHelper(parameters.getUsername()).getReportFromTheReportList(reportWithExpenseDTO, parameters);
		softAssert.assertEquals("OK", fetchedReport.getStatus(), "getStatus issue");
		softAssert.assertEquals("Success", fetchedReport.getMessage(), "getMessage issue");
		softAssert.assertAll();
	}

	/**
	 * Check that is feasible to GET my rejected by finance.
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "getMyRejectedByFinanceReport", dataProviderClass = GetMyBenefitDataProviders.class)
	public void getMyRejectedByFinanceReportTest(GetExpenseReportParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		ReportWithExpenseDTO reportWithExpenseDTO = new ReportWithExpenseDTO(parameters);
		ReportDTO fetchedReport = new GetMyReportTestHelper(parameters.getUsername()).getReportFromTheReportList(reportWithExpenseDTO, parameters);
		softAssert.assertEquals("OK", fetchedReport.getStatus(), "getStatus issue");
		softAssert.assertEquals("Success", fetchedReport.getMessage(), "getMessage issue");
		softAssert.assertAll();
	}

}
