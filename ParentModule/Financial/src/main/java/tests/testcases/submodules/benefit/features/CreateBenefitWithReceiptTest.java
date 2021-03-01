package tests.testcases.submodules.benefit.features;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import database.submodules.expense.features.GetExpenseReportDetailsDBHelper;
import dataproviders.FinancialDataProviders;
import dto.submodules.expense.addExpenseToReport.ReportWithExpenseDTO;
import dto.submodules.expense.expense.post.ExpenseDTO;
import dto.submodules.expense.receipt.ReceiptForExpense;
import dto.submodules.expense.receipt.post.ReceiptDTO;
import dto.submodules.expense.receipt.put.EditReceiptResponse;
import dto.submodules.expense.report.ReportStatus;
import payloads.submodules.expense.features.expense.EditReceiptPayload;
import tests.testcases.submodules.benefit.BenefitBaseTest;
import tests.testhelpers.submodules.benefit.features.GetMyBenefitTestHelper;
import tests.testhelpers.submodules.expense.features.expense.EditExpenseTestHelper;
import tests.testhelpers.submodules.expense.features.receipt.EditReceiptTestHelper;
import tests.testhelpers.submodules.expense.features.receipt.GetReceiptTestHelper;
import tests.testhelpers.submodules.expense.features.receipt.UploadReceiptTestHelper;

/**
 * @author german.massello
 *
 */
public class CreateBenefitWithReceiptTest extends BenefitBaseTest {

	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		featureTest=subModuleTest.createNode("UploadReceiptBenefitTest");
	}

	/**
	 * Check that is feasible 
	 * @param userName
	 * @throws Exception
	 */
	@Test(dataProvider = "usersThatHaveABankAccount", dataProviderClass = FinancialDataProviders.class, invocationCount = 1)
	public void createBenefitWithReceiptTest(String userName) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		ReportWithExpenseDTO myBenefit = new GetMyBenefitTestHelper(userName).getMyBenefitFromScratch();
		ReceiptDTO uploadedReceiptOne = new UploadReceiptTestHelper(userName).uploadReceipt();
		ReceiptForExpense receiptOne = new GetReceiptTestHelper(userName).getReceipt(uploadedReceiptOne);
		ExpenseDTO myBenefitWithReceiptOne = new EditExpenseTestHelper(userName).editExpense(new EditReceiptPayload(myBenefit, receiptOne, 0));
		EditReceiptResponse markReceiptAsUsedOne = new EditReceiptTestHelper(userName).markReceiptAsUsed(receiptOne);
		ReceiptDTO uploadedReceiptTwo = new UploadReceiptTestHelper(userName).uploadReceipt();
		ReceiptForExpense receiptTwo = new GetReceiptTestHelper(userName).getReceipt(uploadedReceiptTwo);
		ExpenseDTO myBenefitWithReceiptTwo = new EditExpenseTestHelper(userName).editExpense(new EditReceiptPayload(myBenefit, receiptTwo, 1));
		EditReceiptResponse markReceiptAsUsedTwo = new EditReceiptTestHelper(userName).markReceiptAsUsed(receiptTwo);
		softAssert.assertEquals(myBenefitWithReceiptOne.getContent().getReceiptId(), receiptOne.getReceiptId(), "getReceiptIdOne issue");
		softAssert.assertEquals(markReceiptAsUsedOne.getContent(), receiptOne.getMetadataId(), "getMetadataIdOne issue");
		softAssert.assertEquals(myBenefitWithReceiptTwo.getContent().getReceiptId(), receiptTwo.getReceiptId(), "getReceiptIdTwo issue");
		softAssert.assertEquals(markReceiptAsUsedTwo.getContent(), receiptTwo.getMetadataId(), "getMetadataIdTwo issue");
		ReportStatus reportStatus = new GetExpenseReportDetailsDBHelper().getReportStatus(myBenefit);
		softAssert.assertEquals(reportStatus.getStatus(), "draft", "getReportStatus issue");
		softAssert.assertNull(reportStatus.getComments(), "getComments issue");
		softAssert.assertEquals(reportStatus.getAuthor(), userName, "getAuthor issue");	
		softAssert.assertAll();
	}

}
