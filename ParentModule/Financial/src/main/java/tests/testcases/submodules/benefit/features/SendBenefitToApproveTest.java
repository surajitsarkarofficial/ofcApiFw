package tests.testcases.submodules.benefit.features;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import database.submodules.expense.features.GetExpenseReportDetailsDBHelper;
import dataproviders.FinancialDataProviders;
import dto.submodules.expense.addExpenseToReport.ReportWithExpenseDTO;
import dto.submodules.expense.report.ReportStatus;
import dto.submodules.expense.sendToApprove.SendToApproveResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import tests.testcases.submodules.benefit.BenefitBaseTest;
import tests.testhelpers.submodules.benefit.features.CreateBenefitWithReceiptTestHelper;
import tests.testhelpers.submodules.benefit.features.RejectAsFinanceBenefitTestHelper;
import tests.testhelpers.submodules.expense.features.sendToApprove.SendToApproveTestHelper;

public class SendBenefitToApproveTest extends BenefitBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("SendBenefitToApproveTest");
	}

	 /**
	 * Check that is feasible to send to approve a benefit. 
	 * @param userName
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(dataProvider = "usersThatHaveABankAccount", dataProviderClass = FinancialDataProviders.class
			, invocationCount = 1)
	public void sendBenefitToApproveTest(String userName) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		ReportWithExpenseDTO benefit = new CreateBenefitWithReceiptTestHelper(userName).createABenefitWithReceiptFromScratch();
		Response response = new SendToApproveTestHelper(userName).sendToApprove(benefit);
		validateResponseToContinueTest(response, 200, "Send To Approve api call was not successful.", true);
		SendToApproveResponseDTO sendToApproveResponse = response.as(SendToApproveResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(sendToApproveResponse.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(sendToApproveResponse.getMessage(), "Success", "getMessage issue");
		softAssert.assertNull(sendToApproveResponse.getContent().getError(), "getError issue");
		ReportStatus reportStatus = new GetExpenseReportDetailsDBHelper().getReportStatus(benefit);
		softAssert.assertEquals(reportStatus.getStatus(), "approved_by_manager", "getReportStatus issue");
		softAssert.assertEquals(reportStatus.getComments(), "Automatically approved by Manager since it is associated with a benefit to be reimbursed", "getComments issue");
		softAssert.assertEquals(reportStatus.getAuthor(), "Glow.Admin", "getAuthor issue");
		softAssert.assertAll();
	   }

	 /**
	 * Check that is feasible to send to approve a rejected benefit. 
	 * @param userName
	 * @throws Exception
	 * @author german.massello
	 */
	@Test(dataProvider = "usersThatHaveABankAccount", dataProviderClass = FinancialDataProviders.class
			, invocationCount = 1)
	public void sendRejectedBenefitToApproveTest(String userName) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		ReportWithExpenseDTO rejectedBenefit = new RejectAsFinanceBenefitTestHelper(userName).rejectAsFinanceBenefitFromScratch();
		Response response = new SendToApproveTestHelper(userName).sendToApprove(rejectedBenefit);
		validateResponseToContinueTest(response, 200, "Send To Approve api call was not successful.", true);
		SendToApproveResponseDTO sendToApproveResponse = response.as(SendToApproveResponseDTO.class, ObjectMapperType.GSON);
		softAssert.assertEquals(sendToApproveResponse.getStatus(), "OK", "getStatus issue");
		softAssert.assertEquals(sendToApproveResponse.getMessage(), "Success", "getMessage issue");
		softAssert.assertNull(sendToApproveResponse.getContent().getError(), "getError issue");
		ReportStatus reportStatus = new GetExpenseReportDetailsDBHelper().getReportStatus(rejectedBenefit);
		softAssert.assertEquals(reportStatus.getStatus(), "approved_by_manager", "getReportStatus issue");
		softAssert.assertEquals(reportStatus.getComments(), "Automatically approved by Manager since it is associated with a benefit to be reimbursed", "getComments issue");
		softAssert.assertEquals(reportStatus.getAuthor(), "Glow.Admin", "getAuthor issue");
		softAssert.assertAll();
	   }

}
