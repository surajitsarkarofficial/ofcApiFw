package tests.testcases.submodules.expense.features.report;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.expense.features.GetExpenseReportDataProviders;
import dto.submodules.expense.report.get.GetReportDTOList;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import parameters.submodules.expense.features.getReports.GetExpenseReportParameters;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.features.report.GetReportTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class GetApproveAsFinanceReportTests extends ExpenseBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("GetExpenseReportTests");
	}

	/**
	 * Check that is feasible to perform a GET expenses report list using the filter usdFrom and usdFrom. 
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "getExpensesReportUsdFilter", dataProviderClass = GetExpenseReportDataProviders.class, groups = {ExeGroups.Sanity})
	public void getExpenseReportUsdFilterTest(GetExpenseReportParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		GetReportTestHelper helper = new GetReportTestHelper(parameters.getUsername());
		Response response = helper.getExpenseReport(parameters);
		GetReportDTOList report = response.as(GetReportDTOList.class, ObjectMapperType.GSON);
		softAssert.assertEquals(report.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(report.getMessage(), "Success", "getMessage issue");
		softAssert.assertTrue(helper.isReportIdPresent(report.getContent().getExpensesReportList(), parameters.getReportId()), "report id is not present");
		softAssert.assertAll();
	}

	/**
	 * Check that is feasible to perform a GET expenses report list using the filter is benefit. 
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "getExpensesReportIsBenefit", dataProviderClass = GetExpenseReportDataProviders.class)
	public void getExpenseReportIsBenefitFilterTest(GetExpenseReportParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		GetReportTestHelper helper = new GetReportTestHelper(parameters.getUsername());
		Response response = helper.getExpenseReport(parameters);
		GetReportDTOList report = response.as(GetReportDTOList.class, ObjectMapperType.GSON);
		softAssert.assertEquals(report.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(report.getMessage(), "Success", "getMessage issue");
		softAssert.assertTrue(helper.isReportIdPresent(report.getContent().getExpensesReportList(), parameters.getReportId()), "report id number "+parameters.getReportId()+" is not present");
		softAssert.assertAll();
	}
}
