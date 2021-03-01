package tests.testcases.submodules.expense.features.report;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.FinancialDataProviders;
import dto.submodules.expense.report.delete.DeleteReportDTO;
import dto.submodules.expense.report.post.ReportDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.features.report.CreateReportTestHelper;
import tests.testhelpers.submodules.expense.features.report.DeleteReportTestHelper;
import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

/**
 * @author german.massello
 *
 */
public class DeleteReportTests extends ExpenseBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("DeleteReportTests");
	}

	/**
	 * Goal: Check that is feasible to delete a report.
	 * @param userName
	 * @throws Exception
	 */
	@Test(dataProvider = "usersThatHaveABankAccount", dataProviderClass = FinancialDataProviders.class, groups = {ExeGroups.Sanity})
	public void deleteReportTest(String userName) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		ReportDTO createdReport = new CreateReportTestHelper(userName).createBasicReport();
		Response response = new DeleteReportTestHelper(userName).deleteReport(createdReport.getContent().getId());
		validateResponseToContinueTest(response, 200, "Delete Report api call was not successful.", true);
		DeleteReportDTO deleteReportDTO = response.as(DeleteReportDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals("Success", deleteReportDTO.getStatus(), "getStatus issue");
		softAssert.assertEquals("OK", deleteReportDTO.getMessage(), "getMessage issue");
		softAssert.assertEquals(createdReport.getContent().getId(), deleteReportDTO.getContent(), "getContent issue");
		softAssert.assertAll();
	}
}
