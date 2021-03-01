package tests.testcases.submodules.benefit.features;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.FinancialDataProviders;
import dto.submodules.expense.addExpenseToReport.ReportWithExpenseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import payloads.submodules.expense.features.addExpenseToReport.AddRemoveExpenseToReportPayload;
import payloads.submodules.expense.features.addExpenseToReport.RemoveBenefit;
import tests.testcases.submodules.benefit.BenefitBaseTest;
import tests.testhelpers.submodules.benefit.features.CreateBenefitWithReceiptTestHelper;
import tests.testhelpers.submodules.expense.features.addExpenseToReport.AddRemoveExpenseToReportTestHelper;

/**
 * @author german.massello
 *
 */
public class DeleteBenefitTest extends BenefitBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("DeleteBenefitTest");
	}

	/**
	 * Check that is feasible to delete a benefit.
	 * @param userName
	 * @throws Exception
	 */
	@Test(dataProvider = "usersThatHaveABankAccount", dataProviderClass = FinancialDataProviders.class
			, invocationCount = 1)
	public void deleteBenefitTest(String userName) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		ReportWithExpenseDTO benefit = new CreateBenefitWithReceiptTestHelper(userName).createABenefitWithReceiptFromScratch();
		AddRemoveExpenseToReportPayload payload = new AddRemoveExpenseToReportPayload(new RemoveBenefit(benefit));
		Response response = new AddRemoveExpenseToReportTestHelper(userName).addRemoveExpenseToReport(payload);
		validateResponseToContinueTest(response, 200, "Add Expense To Report api call was not successful.", true);
		ReportWithExpenseDTO reportWithExpenseDTO = response.as(ReportWithExpenseDTO.class, ObjectMapperType.GSON);
		//Then
		Assert.assertEquals(reportWithExpenseDTO.getStatus(), "OK", " getStatus() issue");
		Assert.assertEquals(reportWithExpenseDTO.getMessage(), "Updated", " getMessage() issue");
		Assert.assertEquals(reportWithExpenseDTO.getContent().getExpenses().get(0).getId(), benefit.getContent().getExpenses().get(1).getId(), " getContent() issue");		
		softAssert.assertAll();
	   }

}
