package tests.testcases.submodules.benefit.features;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.FinancialDataProviders;
import dto.submodules.expense.addExpenseToReport.ReportWithExpenseDTO;
import dto.submodules.expense.report.delete.DeleteReportDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import tests.testcases.submodules.benefit.BenefitBaseTest;
import tests.testhelpers.submodules.benefit.features.CreateBenefitWithReceiptTestHelper;
import tests.testhelpers.submodules.expense.features.report.DeleteReportTestHelper;

/**
 * @author german.massello
 *
 */
public class DeleteBenefitReportTest extends BenefitBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("DeleteBenefitReportTest");
	}

	/**
	 * Check that is feasible to delete a benefit report.
	 * @param userName
	 * @throws Exception
	 */
	@Test(dataProvider = "usersThatHaveABankAccount", dataProviderClass = FinancialDataProviders.class
			, invocationCount = 1)
	public void deleteBenefitReportTest(String userName) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		ReportWithExpenseDTO benefit = new CreateBenefitWithReceiptTestHelper(userName).createABenefitWithReceiptFromScratch();
		Response response = new DeleteReportTestHelper(userName).deleteReport(benefit.getContent().getId());
		validateResponseToContinueTest(response, 200, "Delete Report api call was not successful.", true);
		DeleteReportDTO deleteReportDTO = response.as(DeleteReportDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals("Success", deleteReportDTO.getStatus(), "getStatus issue");
		softAssert.assertEquals("OK", deleteReportDTO.getMessage(), "getMessage issue");
		softAssert.assertEquals(benefit.getContent().getId(), deleteReportDTO.getContent(), "getContent issue");
		softAssert.assertAll();
	   }

}
