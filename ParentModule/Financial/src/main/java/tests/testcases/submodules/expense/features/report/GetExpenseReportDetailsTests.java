package tests.testcases.submodules.expense.features.report;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.expense.features.GetExpenseReportDetailsDataProviders;
import dto.submodules.expense.report.getDetails.GetExpenseReportDetailsResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import parameters.submodules.expense.features.getReports.GetExpenseReportDetailsParameters;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.features.report.GetExpenseReportDetailsTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class GetExpenseReportDetailsTests extends ExpenseBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("GetExpenseReportDetailsTests");
	}

	/**
	 * Check that is feasible to perform a GET expenses report details. 
	 * @param parameters
	 * @throws Exception
	 */
	@Test(dataProvider = "getExpenseReportDetails", dataProviderClass = GetExpenseReportDetailsDataProviders.class, groups = {ExeGroups.Sanity})
	public void getExpenseReportDetailsTest(GetExpenseReportDetailsParameters parameters) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		GetExpenseReportDetailsTestHelper helper = new GetExpenseReportDetailsTestHelper(parameters.getUserName());
		Response response = helper.getExpenseReportDetails(parameters);
		GetExpenseReportDetailsResponseDTO reportDetails = response.as(GetExpenseReportDetailsResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(reportDetails.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(reportDetails.getMessage(), "Success", "getMessage issue");
		softAssert.assertEquals(reportDetails.getContent().getClientName(), parameters.getClientName(), "getClientName issue");
		softAssert.assertAll();
	}
}
